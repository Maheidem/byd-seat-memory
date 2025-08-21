package org.webrtc;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.SurfaceTexture;
import android.opengl.GLES20;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.view.Surface;
import androidx.annotation.Nullable;
import java.nio.ByteBuffer;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import org.webrtc.EglBase;
import org.webrtc.EglRenderer;
import org.webrtc.GlUtil;
import org.webrtc.RendererCommon;

/* loaded from: classes.dex */
public class EglRenderer implements VideoSink {
    private static final long LOG_INTERVAL_SEC = 4;
    private static final String TAG = "EglRenderer";
    private final GlTextureFrameBuffer bitmapTextureFramebuffer;
    private final Matrix drawMatrix;

    @Nullable
    private RendererCommon.GlDrawer drawer;

    @Nullable
    private EglBase eglBase;
    private final EglSurfaceCreation eglSurfaceCreationRunnable;
    private volatile ErrorCallback errorCallback;
    private final Object fpsReductionLock;
    private final VideoFrameDrawer frameDrawer;
    private final ArrayList<FrameListenerAndParams> frameListeners;
    private final Object frameLock;
    private int framesDropped;
    private int framesReceived;
    private int framesRendered;
    private final Object handlerLock;
    private float layoutAspectRatio;
    private final Object layoutLock;
    private final Runnable logStatisticsRunnable;
    private long minRenderPeriodNs;
    private boolean mirrorHorizontally;
    private boolean mirrorVertically;
    protected final String name;
    private long nextFrameTimeNs;

    @Nullable
    private VideoFrame pendingFrame;
    private long renderSwapBufferTimeNs;

    @Nullable
    private Handler renderThreadHandler;
    private long renderTimeNs;
    private final Object statisticsLock;
    private long statisticsStartTimeNs;
    private boolean usePresentationTimeStamp;

    private class EglSurfaceCreation implements Runnable {
        private Object surface;

        private EglSurfaceCreation() {
        }

        @Override // java.lang.Runnable
        public synchronized void run() {
            if (this.surface != null && EglRenderer.this.eglBase != null && !EglRenderer.this.eglBase.hasSurface()) {
                if (this.surface instanceof Surface) {
                    EglRenderer.this.eglBase.createSurface((Surface) this.surface);
                } else {
                    if (!(this.surface instanceof SurfaceTexture)) {
                        throw new IllegalStateException("Invalid surface: " + this.surface);
                    }
                    EglRenderer.this.eglBase.createSurface((SurfaceTexture) this.surface);
                }
                EglRenderer.this.eglBase.makeCurrent();
                GLES20.glPixelStorei(3317, 1);
            }
        }

        public synchronized void setSurface(Object obj) {
            this.surface = obj;
        }
    }

    public interface ErrorCallback {
        void onGlOutOfMemory();
    }

    public interface FrameListener {
        void onFrame(Bitmap bitmap);
    }

    private static class FrameListenerAndParams {
        public final boolean applyFpsReduction;
        public final RendererCommon.GlDrawer drawer;
        public final FrameListener listener;
        public final float scale;

        public FrameListenerAndParams(FrameListener frameListener, float f, RendererCommon.GlDrawer glDrawer, boolean z) {
            this.listener = frameListener;
            this.scale = f;
            this.drawer = glDrawer;
            this.applyFpsReduction = z;
        }
    }

    private static class HandlerWithExceptionCallback extends Handler {
        private final Runnable exceptionCallback;

        public HandlerWithExceptionCallback(Looper looper, Runnable runnable) {
            super(looper);
            this.exceptionCallback = runnable;
        }

        @Override // android.os.Handler
        public void dispatchMessage(Message message) throws Exception {
            try {
                super.dispatchMessage(message);
            } catch (Exception e) {
                Logging.e(EglRenderer.TAG, "Exception on EglRenderer thread", e);
                this.exceptionCallback.run();
                throw e;
            }
        }
    }

    public EglRenderer(String str) {
        this(str, new VideoFrameDrawer());
    }

    public EglRenderer(String str, VideoFrameDrawer videoFrameDrawer) {
        this.handlerLock = new Object();
        this.frameListeners = new ArrayList<>();
        this.fpsReductionLock = new Object();
        this.drawMatrix = new Matrix();
        this.frameLock = new Object();
        this.layoutLock = new Object();
        this.statisticsLock = new Object();
        this.bitmapTextureFramebuffer = new GlTextureFrameBuffer(6408);
        this.logStatisticsRunnable = new Runnable() { // from class: org.webrtc.EglRenderer.1
            @Override // java.lang.Runnable
            public void run() {
                EglRenderer.this.logStatistics();
                synchronized (EglRenderer.this.handlerLock) {
                    if (EglRenderer.this.renderThreadHandler != null) {
                        EglRenderer.this.renderThreadHandler.removeCallbacks(EglRenderer.this.logStatisticsRunnable);
                        EglRenderer.this.renderThreadHandler.postDelayed(EglRenderer.this.logStatisticsRunnable, TimeUnit.SECONDS.toMillis(4L));
                    }
                }
            }
        };
        this.eglSurfaceCreationRunnable = new EglSurfaceCreation();
        this.name = str;
        this.frameDrawer = videoFrameDrawer;
    }

    private String averageTimeAsString(long j, int i) {
        if (i <= 0) {
            return "NA";
        }
        return TimeUnit.NANOSECONDS.toMicros(j / i) + " us";
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: clearSurfaceOnRenderThread, reason: merged with bridge method [inline-methods] */
    public void lambda$clearImage$6$EglRenderer(float f, float f2, float f3, float f4) {
        if (this.eglBase == null || !this.eglBase.hasSurface()) {
            return;
        }
        logD("clearSurface");
        GLES20.glClearColor(f, f2, f3, f4);
        GLES20.glClear(16384);
        this.eglBase.swapBuffers();
    }

    private void createEglSurfaceInternal(Object obj) {
        this.eglSurfaceCreationRunnable.setSurface(obj);
        postToRenderThread(this.eglSurfaceCreationRunnable);
    }

    private void logD(String str) {
        Logging.d(TAG, this.name + str);
    }

    private void logE(String str, Throwable th) {
        Logging.e(TAG, this.name + str, th);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void logStatistics() {
        DecimalFormat decimalFormat = new DecimalFormat("#.0");
        long jNanoTime = System.nanoTime();
        synchronized (this.statisticsLock) {
            long j = jNanoTime - this.statisticsStartTimeNs;
            if (j > 0 && (this.minRenderPeriodNs != Long.MAX_VALUE || this.framesReceived != 0)) {
                logD("Duration: " + TimeUnit.NANOSECONDS.toMillis(j) + " ms. Frames received: " + this.framesReceived + ". Dropped: " + this.framesDropped + ". Rendered: " + this.framesRendered + ". Render fps: " + decimalFormat.format((this.framesRendered * TimeUnit.SECONDS.toNanos(1L)) / j) + ". Average render time: " + averageTimeAsString(this.renderTimeNs, this.framesRendered) + ". Average swapBuffer time: " + averageTimeAsString(this.renderSwapBufferTimeNs, this.framesRendered) + ".");
                resetStatistics(jNanoTime);
            }
        }
    }

    private void logW(String str) {
        Logging.w(TAG, this.name + str);
    }

    private void notifyCallbacks(VideoFrame videoFrame, boolean z) {
        FrameListener frameListener;
        Bitmap bitmapCreateBitmap;
        if (this.frameListeners.isEmpty()) {
            return;
        }
        this.drawMatrix.reset();
        this.drawMatrix.preTranslate(0.5f, 0.5f);
        this.drawMatrix.preScale(this.mirrorHorizontally ? -1.0f : 1.0f, this.mirrorVertically ? -1.0f : 1.0f);
        this.drawMatrix.preScale(1.0f, -1.0f);
        this.drawMatrix.preTranslate(-0.5f, -0.5f);
        Iterator<FrameListenerAndParams> it = this.frameListeners.iterator();
        while (it.hasNext()) {
            FrameListenerAndParams next = it.next();
            if (z || !next.applyFpsReduction) {
                it.remove();
                int rotatedWidth = (int) (next.scale * videoFrame.getRotatedWidth());
                int rotatedHeight = (int) (next.scale * videoFrame.getRotatedHeight());
                if (rotatedWidth == 0 || rotatedHeight == 0) {
                    frameListener = next.listener;
                    bitmapCreateBitmap = null;
                } else {
                    this.bitmapTextureFramebuffer.setSize(rotatedWidth, rotatedHeight);
                    GLES20.glBindFramebuffer(36160, this.bitmapTextureFramebuffer.getFrameBufferId());
                    GLES20.glFramebufferTexture2D(36160, 36064, 3553, this.bitmapTextureFramebuffer.getTextureId(), 0);
                    GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
                    GLES20.glClear(16384);
                    this.frameDrawer.drawFrame(videoFrame, next.drawer, this.drawMatrix, 0, 0, rotatedWidth, rotatedHeight);
                    ByteBuffer byteBufferAllocateDirect = ByteBuffer.allocateDirect(rotatedWidth * rotatedHeight * 4);
                    GLES20.glViewport(0, 0, rotatedWidth, rotatedHeight);
                    GLES20.glReadPixels(0, 0, rotatedWidth, rotatedHeight, 6408, 5121, byteBufferAllocateDirect);
                    GLES20.glBindFramebuffer(36160, 0);
                    GlUtil.checkNoGLES2Error("EglRenderer.notifyCallbacks");
                    bitmapCreateBitmap = Bitmap.createBitmap(rotatedWidth, rotatedHeight, Bitmap.Config.ARGB_8888);
                    bitmapCreateBitmap.copyPixelsFromBuffer(byteBufferAllocateDirect);
                    frameListener = next.listener;
                }
                frameListener.onFrame(bitmapCreateBitmap);
            }
        }
    }

    private void postToRenderThread(Runnable runnable) {
        synchronized (this.handlerLock) {
            if (this.renderThreadHandler != null) {
                this.renderThreadHandler.post(runnable);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    /* renamed from: renderFrameOnRenderThread, reason: merged with bridge method [inline-methods] */
    public void bridge$lambda$0$EglRenderer() {
        boolean z;
        float f;
        float f2;
        float f3;
        synchronized (this.frameLock) {
            if (this.pendingFrame == null) {
                return;
            }
            VideoFrame videoFrame = this.pendingFrame;
            this.pendingFrame = null;
            if (this.eglBase == null || !this.eglBase.hasSurface()) {
                logD("Dropping frame - No surface");
                videoFrame.release();
                return;
            }
            synchronized (this.fpsReductionLock) {
                if (this.minRenderPeriodNs != Long.MAX_VALUE) {
                    if (this.minRenderPeriodNs > 0) {
                        long jNanoTime = System.nanoTime();
                        if (jNanoTime < this.nextFrameTimeNs) {
                            logD("Skipping frame rendering - fps reduction is active.");
                            z = false;
                        } else {
                            this.nextFrameTimeNs += this.minRenderPeriodNs;
                            this.nextFrameTimeNs = Math.max(this.nextFrameTimeNs, jNanoTime);
                        }
                    }
                    z = true;
                } else {
                    z = false;
                }
            }
            long jNanoTime2 = System.nanoTime();
            float rotatedWidth = videoFrame.getRotatedWidth() / videoFrame.getRotatedHeight();
            synchronized (this.layoutLock) {
                f = this.layoutAspectRatio != 0.0f ? this.layoutAspectRatio : rotatedWidth;
            }
            if (rotatedWidth > f) {
                f3 = f / rotatedWidth;
                f2 = 1.0f;
            } else {
                f2 = rotatedWidth / f;
                f3 = 1.0f;
            }
            this.drawMatrix.reset();
            this.drawMatrix.preTranslate(0.5f, 0.5f);
            this.drawMatrix.preScale(this.mirrorHorizontally ? -1.0f : 1.0f, this.mirrorVertically ? -1.0f : 1.0f);
            this.drawMatrix.preScale(f3, f2);
            this.drawMatrix.preTranslate(-0.5f, -0.5f);
            try {
                if (z) {
                    try {
                        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
                        GLES20.glClear(16384);
                        this.frameDrawer.drawFrame(videoFrame, this.drawer, this.drawMatrix, 0, 0, this.eglBase.surfaceWidth(), this.eglBase.surfaceHeight());
                        long jNanoTime3 = System.nanoTime();
                        if (this.usePresentationTimeStamp) {
                            this.eglBase.swapBuffers(videoFrame.getTimestampNs());
                        } else {
                            this.eglBase.swapBuffers();
                        }
                        long jNanoTime4 = System.nanoTime();
                        synchronized (this.statisticsLock) {
                            this.framesRendered++;
                            this.renderTimeNs += jNanoTime4 - jNanoTime2;
                            this.renderSwapBufferTimeNs += jNanoTime4 - jNanoTime3;
                        }
                        notifyCallbacks(videoFrame, z);
                    } catch (GlUtil.GlOutOfMemoryException e) {
                        logE("Error while drawing frame", e);
                        ErrorCallback errorCallback = this.errorCallback;
                        if (errorCallback != null) {
                            errorCallback.onGlOutOfMemory();
                        }
                        this.drawer.release();
                        this.frameDrawer.release();
                        this.bitmapTextureFramebuffer.release();
                    }
                } else {
                    notifyCallbacks(videoFrame, z);
                }
                videoFrame.release();
            } catch (Throwable th) {
                videoFrame.release();
                throw th;
            }
        }
    }

    private void resetStatistics(long j) {
        synchronized (this.statisticsLock) {
            this.statisticsStartTimeNs = j;
            this.framesReceived = 0;
            this.framesDropped = 0;
            this.framesRendered = 0;
            this.renderTimeNs = 0L;
            this.renderSwapBufferTimeNs = 0L;
        }
    }

    public void addFrameListener(FrameListener frameListener, float f) {
        addFrameListener(frameListener, f, null, false);
    }

    public void addFrameListener(FrameListener frameListener, float f, RendererCommon.GlDrawer glDrawer) {
        addFrameListener(frameListener, f, glDrawer, false);
    }

    public void addFrameListener(final FrameListener frameListener, final float f, @Nullable final RendererCommon.GlDrawer glDrawer, final boolean z) {
        postToRenderThread(new Runnable(this, glDrawer, frameListener, f, z) { // from class: org.webrtc.EglRenderer$$Lambda$3
            private final EglRenderer arg$1;
            private final RendererCommon.GlDrawer arg$2;
            private final EglRenderer.FrameListener arg$3;
            private final float arg$4;
            private final boolean arg$5;

            {
                this.arg$1 = this;
                this.arg$2 = glDrawer;
                this.arg$3 = frameListener;
                this.arg$4 = f;
                this.arg$5 = z;
            }

            @Override // java.lang.Runnable
            public void run() {
                this.arg$1.lambda$addFrameListener$3$EglRenderer(this.arg$2, this.arg$3, this.arg$4, this.arg$5);
            }
        });
    }

    public void clearImage() {
        clearImage(0.0f, 0.0f, 0.0f, 0.0f);
    }

    public void clearImage(final float f, final float f2, final float f3, final float f4) {
        synchronized (this.handlerLock) {
            if (this.renderThreadHandler == null) {
                return;
            }
            this.renderThreadHandler.postAtFrontOfQueue(new Runnable(this, f, f2, f3, f4) { // from class: org.webrtc.EglRenderer$$Lambda$7
                private final EglRenderer arg$1;
                private final float arg$2;
                private final float arg$3;
                private final float arg$4;
                private final float arg$5;

                {
                    this.arg$1 = this;
                    this.arg$2 = f;
                    this.arg$3 = f2;
                    this.arg$4 = f3;
                    this.arg$5 = f4;
                }

                @Override // java.lang.Runnable
                public void run() {
                    this.arg$1.lambda$clearImage$6$EglRenderer(this.arg$2, this.arg$3, this.arg$4, this.arg$5);
                }
            });
        }
    }

    public void createEglSurface(SurfaceTexture surfaceTexture) {
        createEglSurfaceInternal(surfaceTexture);
    }

    public void createEglSurface(Surface surface) {
        createEglSurfaceInternal(surface);
    }

    public void disableFpsReduction() {
        setFpsReduction(Float.POSITIVE_INFINITY);
    }

    public void init(@Nullable EglBase.Context context, int[] iArr, RendererCommon.GlDrawer glDrawer) {
        init(context, iArr, glDrawer, false);
    }

    public void init(@Nullable final EglBase.Context context, final int[] iArr, RendererCommon.GlDrawer glDrawer, boolean z) {
        synchronized (this.handlerLock) {
            if (this.renderThreadHandler != null) {
                throw new IllegalStateException(this.name + "Already initialized");
            }
            logD("Initializing EglRenderer");
            this.drawer = glDrawer;
            this.usePresentationTimeStamp = z;
            HandlerThread handlerThread = new HandlerThread(this.name + TAG);
            handlerThread.start();
            this.renderThreadHandler = new HandlerWithExceptionCallback(handlerThread.getLooper(), new Runnable() { // from class: org.webrtc.EglRenderer.2
                @Override // java.lang.Runnable
                public void run() {
                    synchronized (EglRenderer.this.handlerLock) {
                        EglRenderer.this.renderThreadHandler = null;
                    }
                }
            });
            ThreadUtils.invokeAtFrontUninterruptibly(this.renderThreadHandler, new Runnable(this, context, iArr) { // from class: org.webrtc.EglRenderer$$Lambda$0
                private final EglRenderer arg$1;
                private final EglBase.Context arg$2;
                private final int[] arg$3;

                {
                    this.arg$1 = this;
                    this.arg$2 = context;
                    this.arg$3 = iArr;
                }

                @Override // java.lang.Runnable
                public void run() {
                    this.arg$1.lambda$init$0$EglRenderer(this.arg$2, this.arg$3);
                }
            });
            this.renderThreadHandler.post(this.eglSurfaceCreationRunnable);
            resetStatistics(System.nanoTime());
            this.renderThreadHandler.postDelayed(this.logStatisticsRunnable, TimeUnit.SECONDS.toMillis(4L));
        }
    }

    final /* synthetic */ void lambda$addFrameListener$3$EglRenderer(RendererCommon.GlDrawer glDrawer, FrameListener frameListener, float f, boolean z) {
        if (glDrawer == null) {
            glDrawer = this.drawer;
        }
        this.frameListeners.add(new FrameListenerAndParams(frameListener, f, glDrawer, z));
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    final /* synthetic */ void lambda$init$0$EglRenderer(EglBase.Context context, int[] iArr) {
        EglBase eglBaseCreate;
        if (context == null) {
            logD("EglBase10.create context");
            eglBaseCreate = EglBase.createEgl10(iArr);
        } else {
            logD("EglBase.create shared context");
            eglBaseCreate = EglBase.create(context, iArr);
        }
        this.eglBase = eglBaseCreate;
    }

    final /* synthetic */ void lambda$release$1$EglRenderer(CountDownLatch countDownLatch) {
        synchronized (EglBase.lock) {
            GLES20.glUseProgram(0);
        }
        if (this.drawer != null) {
            this.drawer.release();
            this.drawer = null;
        }
        this.frameDrawer.release();
        this.bitmapTextureFramebuffer.release();
        if (this.eglBase != null) {
            logD("eglBase detach and release.");
            this.eglBase.detachCurrent();
            this.eglBase.release();
            this.eglBase = null;
        }
        this.frameListeners.clear();
        countDownLatch.countDown();
    }

    final /* synthetic */ void lambda$release$2$EglRenderer(Looper looper) {
        logD("Quitting render thread.");
        looper.quit();
    }

    final /* synthetic */ void lambda$releaseEglSurface$5$EglRenderer(Runnable runnable) {
        if (this.eglBase != null) {
            this.eglBase.detachCurrent();
            this.eglBase.releaseSurface();
        }
        runnable.run();
    }

    final /* synthetic */ void lambda$removeFrameListener$4$EglRenderer(CountDownLatch countDownLatch, FrameListener frameListener) {
        countDownLatch.countDown();
        Iterator<FrameListenerAndParams> it = this.frameListeners.iterator();
        while (it.hasNext()) {
            if (it.next().listener == frameListener) {
                it.remove();
            }
        }
    }

    @Override // org.webrtc.VideoSink
    public void onFrame(VideoFrame videoFrame) {
        boolean z;
        synchronized (this.statisticsLock) {
            this.framesReceived++;
        }
        synchronized (this.handlerLock) {
            if (this.renderThreadHandler == null) {
                logD("Dropping frame - Not initialized or already released.");
                return;
            }
            synchronized (this.frameLock) {
                z = this.pendingFrame != null;
                if (z) {
                    this.pendingFrame.release();
                }
                this.pendingFrame = videoFrame;
                this.pendingFrame.retain();
                this.renderThreadHandler.post(new Runnable(this) { // from class: org.webrtc.EglRenderer$$Lambda$5
                    private final EglRenderer arg$1;

                    {
                        this.arg$1 = this;
                    }

                    @Override // java.lang.Runnable
                    public void run() {
                        this.arg$1.bridge$lambda$0$EglRenderer();
                    }
                });
            }
            if (z) {
                synchronized (this.statisticsLock) {
                    this.framesDropped++;
                }
            }
        }
    }

    public void pauseVideo() {
        setFpsReduction(0.0f);
    }

    public void printStackTrace() {
        synchronized (this.handlerLock) {
            Thread thread = this.renderThreadHandler == null ? null : this.renderThreadHandler.getLooper().getThread();
            if (thread != null) {
                StackTraceElement[] stackTrace = thread.getStackTrace();
                if (stackTrace.length > 0) {
                    logW("EglRenderer stack trace:");
                    for (StackTraceElement stackTraceElement : stackTrace) {
                        logW(stackTraceElement.toString());
                    }
                }
            }
        }
    }

    public void release() {
        logD("Releasing.");
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        synchronized (this.handlerLock) {
            if (this.renderThreadHandler == null) {
                logD("Already released");
                return;
            }
            this.renderThreadHandler.removeCallbacks(this.logStatisticsRunnable);
            this.renderThreadHandler.postAtFrontOfQueue(new Runnable(this, countDownLatch) { // from class: org.webrtc.EglRenderer$$Lambda$1
                private final EglRenderer arg$1;
                private final CountDownLatch arg$2;

                {
                    this.arg$1 = this;
                    this.arg$2 = countDownLatch;
                }

                @Override // java.lang.Runnable
                public void run() {
                    this.arg$1.lambda$release$1$EglRenderer(this.arg$2);
                }
            });
            final Looper looper = this.renderThreadHandler.getLooper();
            this.renderThreadHandler.post(new Runnable(this, looper) { // from class: org.webrtc.EglRenderer$$Lambda$2
                private final EglRenderer arg$1;
                private final Looper arg$2;

                {
                    this.arg$1 = this;
                    this.arg$2 = looper;
                }

                @Override // java.lang.Runnable
                public void run() {
                    this.arg$1.lambda$release$2$EglRenderer(this.arg$2);
                }
            });
            this.renderThreadHandler = null;
            ThreadUtils.awaitUninterruptibly(countDownLatch);
            synchronized (this.frameLock) {
                if (this.pendingFrame != null) {
                    this.pendingFrame.release();
                    this.pendingFrame = null;
                }
            }
            logD("Releasing done.");
        }
    }

    public void releaseEglSurface(final Runnable runnable) {
        this.eglSurfaceCreationRunnable.setSurface(null);
        synchronized (this.handlerLock) {
            if (this.renderThreadHandler == null) {
                runnable.run();
            } else {
                this.renderThreadHandler.removeCallbacks(this.eglSurfaceCreationRunnable);
                this.renderThreadHandler.postAtFrontOfQueue(new Runnable(this, runnable) { // from class: org.webrtc.EglRenderer$$Lambda$6
                    private final EglRenderer arg$1;
                    private final Runnable arg$2;

                    {
                        this.arg$1 = this;
                        this.arg$2 = runnable;
                    }

                    @Override // java.lang.Runnable
                    public void run() {
                        this.arg$1.lambda$releaseEglSurface$5$EglRenderer(this.arg$2);
                    }
                });
            }
        }
    }

    public void removeFrameListener(final FrameListener frameListener) {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        synchronized (this.handlerLock) {
            if (this.renderThreadHandler == null) {
                return;
            }
            if (Thread.currentThread() == this.renderThreadHandler.getLooper().getThread()) {
                throw new RuntimeException("removeFrameListener must not be called on the render thread.");
            }
            postToRenderThread(new Runnable(this, countDownLatch, frameListener) { // from class: org.webrtc.EglRenderer$$Lambda$4
                private final EglRenderer arg$1;
                private final CountDownLatch arg$2;
                private final EglRenderer.FrameListener arg$3;

                {
                    this.arg$1 = this;
                    this.arg$2 = countDownLatch;
                    this.arg$3 = frameListener;
                }

                @Override // java.lang.Runnable
                public void run() {
                    this.arg$1.lambda$removeFrameListener$4$EglRenderer(this.arg$2, this.arg$3);
                }
            });
            ThreadUtils.awaitUninterruptibly(countDownLatch);
        }
    }

    public void setErrorCallback(ErrorCallback errorCallback) {
        this.errorCallback = errorCallback;
    }

    public void setFpsReduction(float f) {
        logD("setFpsReduction: " + f);
        synchronized (this.fpsReductionLock) {
            long j = this.minRenderPeriodNs;
            if (f <= 0.0f) {
                this.minRenderPeriodNs = Long.MAX_VALUE;
            } else {
                this.minRenderPeriodNs = (long) (TimeUnit.SECONDS.toNanos(1L) / f);
            }
            if (this.minRenderPeriodNs != j) {
                this.nextFrameTimeNs = System.nanoTime();
            }
        }
    }

    public void setLayoutAspectRatio(float f) {
        logD("setLayoutAspectRatio: " + f);
        synchronized (this.layoutLock) {
            this.layoutAspectRatio = f;
        }
    }

    public void setMirror(boolean z) {
        logD("setMirrorHorizontally: " + z);
        synchronized (this.layoutLock) {
            this.mirrorHorizontally = z;
        }
    }

    public void setMirrorVertically(boolean z) {
        logD("setMirrorVertically: " + z);
        synchronized (this.layoutLock) {
            this.mirrorVertically = z;
        }
    }
}
