package org.webrtc;

import android.content.Context;
import android.os.SystemClock;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public class FileVideoCapturer implements VideoCapturer {
    private static final String TAG = "FileVideoCapturer";
    private CapturerObserver capturerObserver;
    private final VideoReader videoReader;
    private final Timer timer = new Timer();
    private final TimerTask tickTask = new TimerTask() { // from class: org.webrtc.FileVideoCapturer.1
        @Override // java.util.TimerTask, java.lang.Runnable
        public void run() {
            FileVideoCapturer.this.tick();
        }
    };

    private interface VideoReader {
        void close();

        VideoFrame getNextFrame();
    }

    private static class VideoReaderY4M implements VideoReader {
        private static final String TAG = "VideoReaderY4M";
        private final int frameHeight;
        private final int frameWidth;
        private final RandomAccessFile mediaFile;
        private final FileChannel mediaFileChannel;
        private final long videoStart;
        private static final String Y4M_FRAME_DELIMETER = "FRAME";
        private static final int FRAME_DELIMETER_LENGTH = Y4M_FRAME_DELIMETER.length() + 1;

        public VideoReaderY4M(String str) throws IOException, NumberFormatException {
            this.mediaFile = new RandomAccessFile(str, "r");
            this.mediaFileChannel = this.mediaFile.getChannel();
            StringBuilder sb = new StringBuilder();
            while (true) {
                int i = this.mediaFile.read();
                if (i == -1) {
                    throw new RuntimeException("Found end of file before end of header for file: " + str);
                }
                if (i == 10) {
                    this.videoStart = this.mediaFileChannel.position();
                    String[] strArrSplit = sb.toString().split("[ ]");
                    String strSubstring = "";
                    int i2 = 0;
                    int i3 = 0;
                    for (String str2 : strArrSplit) {
                        char cCharAt = str2.charAt(0);
                        if (cCharAt == 'C') {
                            strSubstring = str2.substring(1);
                        } else if (cCharAt == 'H') {
                            i3 = Integer.parseInt(str2.substring(1));
                        } else if (cCharAt == 'W') {
                            i2 = Integer.parseInt(str2.substring(1));
                        }
                    }
                    Logging.d(TAG, "Color space: " + strSubstring);
                    if (!strSubstring.equals("420") && !strSubstring.equals("420mpeg2")) {
                        throw new IllegalArgumentException("Does not support any other color space than I420 or I420mpeg2");
                    }
                    if (i2 % 2 == 1 || i3 % 2 == 1) {
                        throw new IllegalArgumentException("Does not support odd width or height");
                    }
                    this.frameWidth = i2;
                    this.frameHeight = i3;
                    Logging.d(TAG, "frame dim: (" + i2 + ", " + i3 + ")");
                    return;
                }
                sb.append((char) i);
            }
        }

        @Override // org.webrtc.FileVideoCapturer.VideoReader
        public void close() throws IOException {
            try {
                this.mediaFile.close();
            } catch (IOException e) {
                Logging.e(TAG, "Problem closing file", e);
            }
        }

        @Override // org.webrtc.FileVideoCapturer.VideoReader
        public VideoFrame getNextFrame() throws IOException {
            long nanos = TimeUnit.MILLISECONDS.toNanos(SystemClock.elapsedRealtime());
            JavaI420Buffer javaI420BufferAllocate = JavaI420Buffer.allocate(this.frameWidth, this.frameHeight);
            ByteBuffer dataY = javaI420BufferAllocate.getDataY();
            ByteBuffer dataU = javaI420BufferAllocate.getDataU();
            ByteBuffer dataV = javaI420BufferAllocate.getDataV();
            int i = (this.frameHeight + 1) / 2;
            int i2 = this.frameHeight;
            javaI420BufferAllocate.getStrideY();
            javaI420BufferAllocate.getStrideU();
            javaI420BufferAllocate.getStrideV();
            try {
                ByteBuffer byteBufferAllocate = ByteBuffer.allocate(FRAME_DELIMETER_LENGTH);
                if (this.mediaFileChannel.read(byteBufferAllocate) < FRAME_DELIMETER_LENGTH) {
                    this.mediaFileChannel.position(this.videoStart);
                    if (this.mediaFileChannel.read(byteBufferAllocate) < FRAME_DELIMETER_LENGTH) {
                        throw new RuntimeException("Error looping video");
                    }
                }
                String str = new String(byteBufferAllocate.array(), Charset.forName("US-ASCII"));
                if (str.equals("FRAME\n")) {
                    this.mediaFileChannel.read(dataY);
                    this.mediaFileChannel.read(dataU);
                    this.mediaFileChannel.read(dataV);
                    return new VideoFrame(javaI420BufferAllocate, 0, nanos);
                }
                throw new RuntimeException("Frames should be delimited by FRAME plus newline, found delimter was: '" + str + "'");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public FileVideoCapturer(String str) throws IOException {
        try {
            this.videoReader = new VideoReaderY4M(str);
        } catch (IOException e) {
            Logging.d(TAG, "Could not open video file: " + str);
            throw e;
        }
    }

    @Override // org.webrtc.VideoCapturer
    public void changeCaptureFormat(int i, int i2, int i3) {
    }

    @Override // org.webrtc.VideoCapturer
    public void dispose() {
        this.videoReader.close();
    }

    @Override // org.webrtc.VideoCapturer
    public void initialize(SurfaceTextureHelper surfaceTextureHelper, Context context, CapturerObserver capturerObserver) {
        this.capturerObserver = capturerObserver;
    }

    @Override // org.webrtc.VideoCapturer
    public boolean isScreencast() {
        return false;
    }

    @Override // org.webrtc.VideoCapturer
    public void startCapture(int i, int i2, int i3) {
        this.timer.schedule(this.tickTask, 0L, 1000 / i3);
    }

    @Override // org.webrtc.VideoCapturer
    public void stopCapture() {
        this.timer.cancel();
    }

    public void tick() {
        VideoFrame nextFrame = this.videoReader.getNextFrame();
        this.capturerObserver.onFrameCaptured(nextFrame);
        nextFrame.release();
    }
}
