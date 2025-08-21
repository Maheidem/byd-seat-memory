package io.socket.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.logging.Level;
import java.util.logging.Logger;

/* loaded from: classes.dex */
public class EventThread extends Thread {
    private static int counter;
    private static ExecutorService service;
    private static EventThread thread;
    private static final Logger logger = Logger.getLogger(EventThread.class.getName());
    private static final ThreadFactory THREAD_FACTORY = new ThreadFactory() { // from class: io.socket.thread.EventThread.1
        @Override // java.util.concurrent.ThreadFactory
        public Thread newThread(Runnable runnable) {
            EventThread unused = EventThread.thread = new EventThread(runnable);
            EventThread.thread.setName("EventThread");
            EventThread.thread.setDaemon(Thread.currentThread().isDaemon());
            return EventThread.thread;
        }
    };

    private EventThread(Runnable runnable) {
        super(runnable);
    }

    static /* synthetic */ int access$310() {
        int i = counter;
        counter = i - 1;
        return i;
    }

    public static void exec(Runnable runnable) {
        if (isCurrent()) {
            runnable.run();
        } else {
            nextTick(runnable);
        }
    }

    public static boolean isCurrent() {
        return currentThread() == thread;
    }

    public static void nextTick(final Runnable runnable) {
        ExecutorService executorService;
        synchronized (EventThread.class) {
            counter++;
            if (service == null) {
                service = Executors.newSingleThreadExecutor(THREAD_FACTORY);
            }
            executorService = service;
        }
        executorService.execute(new Runnable() { // from class: io.socket.thread.EventThread.2
            @Override // java.lang.Runnable
            public void run() {
                try {
                    try {
                        runnable.run();
                        synchronized (EventThread.class) {
                            EventThread.access$310();
                            if (EventThread.counter == 0) {
                                EventThread.service.shutdown();
                                ExecutorService unused = EventThread.service = null;
                                EventThread unused2 = EventThread.thread = null;
                            }
                        }
                    } catch (Throwable th) {
                        EventThread.logger.log(Level.SEVERE, "Task threw exception", th);
                        throw th;
                    }
                } catch (Throwable th2) {
                    synchronized (EventThread.class) {
                        EventThread.access$310();
                        if (EventThread.counter == 0) {
                            EventThread.service.shutdown();
                            ExecutorService unused3 = EventThread.service = null;
                            EventThread unused4 = EventThread.thread = null;
                        }
                        throw th2;
                    }
                }
            }
        });
    }
}
