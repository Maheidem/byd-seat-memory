package android.support.v4.media;

import android.os.SystemClock;
import android.view.KeyEvent;

/* loaded from: classes.dex */
public abstract class TransportPerformer {
    static final int AUDIOFOCUS_GAIN = 1;
    static final int AUDIOFOCUS_GAIN_TRANSIENT = 2;
    static final int AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK = 3;
    static final int AUDIOFOCUS_LOSS = -1;
    static final int AUDIOFOCUS_LOSS_TRANSIENT = -2;
    static final int AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK = -3;

    public void onAudioFocusChange(int i) {
        int i2 = i != -1 ? 0 : TransportMediator.KEYCODE_MEDIA_PAUSE;
        if (i2 != 0) {
            long jUptimeMillis = SystemClock.uptimeMillis();
            int i3 = i2;
            onMediaButtonDown(i2, new KeyEvent(jUptimeMillis, jUptimeMillis, 0, i3, 0));
            onMediaButtonUp(i2, new KeyEvent(jUptimeMillis, jUptimeMillis, 1, i3, 0));
        }
    }

    public int onGetBufferPercentage() {
        return 100;
    }

    public abstract long onGetCurrentPosition();

    public abstract long onGetDuration();

    public int onGetTransportControlFlags() {
        return 60;
    }

    public abstract boolean onIsPlaying();

    public boolean onMediaButtonDown(int i, KeyEvent keyEvent) {
        switch (i) {
            case 79:
            case 85:
                if (!onIsPlaying()) {
                    onStart();
                    break;
                } else {
                    onPause();
                    break;
                }
            case 86:
                onStop();
                break;
            case TransportMediator.KEYCODE_MEDIA_PLAY /* 126 */:
                onStart();
                break;
            case TransportMediator.KEYCODE_MEDIA_PAUSE /* 127 */:
                onPause();
                break;
        }
        return true;
    }

    public boolean onMediaButtonUp(int i, KeyEvent keyEvent) {
        return true;
    }

    public abstract void onPause();

    public abstract void onSeekTo(long j);

    public abstract void onStart();

    public abstract void onStop();
}
