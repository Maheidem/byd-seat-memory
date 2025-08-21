package android.support.v4.media.session;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.content.Context;
import android.media.AudioManager;
import android.media.RemoteControlClient;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import org.webrtc.MediaStreamTrack;

@RequiresApi(14)
@TargetApi(14)
/* loaded from: classes.dex */
class MediaSessionCompatApi14 {
    private static final long ACTION_FAST_FORWARD = 64;
    private static final long ACTION_PAUSE = 2;
    private static final long ACTION_PLAY = 4;
    private static final long ACTION_PLAY_PAUSE = 512;
    private static final long ACTION_REWIND = 8;
    private static final long ACTION_SKIP_TO_NEXT = 32;
    private static final long ACTION_SKIP_TO_PREVIOUS = 16;
    private static final long ACTION_STOP = 1;
    private static final String METADATA_KEY_ALBUM = "android.media.metadata.ALBUM";
    private static final String METADATA_KEY_ALBUM_ART = "android.media.metadata.ALBUM_ART";
    private static final String METADATA_KEY_ALBUM_ARTIST = "android.media.metadata.ALBUM_ARTIST";
    private static final String METADATA_KEY_ART = "android.media.metadata.ART";
    private static final String METADATA_KEY_ARTIST = "android.media.metadata.ARTIST";
    private static final String METADATA_KEY_AUTHOR = "android.media.metadata.AUTHOR";
    private static final String METADATA_KEY_COMPILATION = "android.media.metadata.COMPILATION";
    private static final String METADATA_KEY_COMPOSER = "android.media.metadata.COMPOSER";
    private static final String METADATA_KEY_DATE = "android.media.metadata.DATE";
    private static final String METADATA_KEY_DISC_NUMBER = "android.media.metadata.DISC_NUMBER";
    private static final String METADATA_KEY_DURATION = "android.media.metadata.DURATION";
    private static final String METADATA_KEY_GENRE = "android.media.metadata.GENRE";
    private static final String METADATA_KEY_TITLE = "android.media.metadata.TITLE";
    private static final String METADATA_KEY_TRACK_NUMBER = "android.media.metadata.TRACK_NUMBER";
    private static final String METADATA_KEY_WRITER = "android.media.metadata.WRITER";
    static final int RCC_PLAYSTATE_NONE = 0;
    static final int STATE_BUFFERING = 6;
    static final int STATE_CONNECTING = 8;
    static final int STATE_ERROR = 7;
    static final int STATE_FAST_FORWARDING = 4;
    static final int STATE_NONE = 0;
    static final int STATE_PAUSED = 2;
    static final int STATE_PLAYING = 3;
    static final int STATE_REWINDING = 5;
    static final int STATE_SKIPPING_TO_NEXT = 10;
    static final int STATE_SKIPPING_TO_PREVIOUS = 9;
    static final int STATE_SKIPPING_TO_QUEUE_ITEM = 11;
    static final int STATE_STOPPED = 1;

    MediaSessionCompatApi14() {
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x002c  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x003e  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0051  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0063  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0075  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0088  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x009a  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x00ac  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00bf  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x00d2  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x00e4  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x00f6  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x0108  */
    /* JADX WARN: Removed duplicated region for block: B:51:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    static void buildOldMetadata(android.os.Bundle r3, android.media.RemoteControlClient.MetadataEditor r4) throws java.lang.IllegalArgumentException {
        /*
            Method dump skipped, instructions count: 276
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.media.session.MediaSessionCompatApi14.buildOldMetadata(android.os.Bundle, android.media.RemoteControlClient$MetadataEditor):void");
    }

    public static Object createRemoteControlClient(PendingIntent pendingIntent) {
        return new RemoteControlClient(pendingIntent);
    }

    static int getRccStateFromState(int i) {
        switch (i) {
            case 0:
                return 0;
            case 1:
                return 1;
            case 2:
                return 2;
            case 3:
                return 3;
            case 4:
                return 4;
            case 5:
                return 5;
            case 6:
            case 8:
                return 8;
            case 7:
                return 9;
            case 9:
                return 7;
            case 10:
            case 11:
                return 6;
            default:
                return -1;
        }
    }

    static int getRccTransportControlFlagsFromActions(long j) {
        int i = (1 & j) != 0 ? 32 : 0;
        if ((2 & j) != 0) {
            i |= 16;
        }
        if ((4 & j) != 0) {
            i |= 4;
        }
        if ((8 & j) != 0) {
            i |= 2;
        }
        if ((16 & j) != 0) {
            i |= 1;
        }
        if ((32 & j) != 0) {
            i |= 128;
        }
        if ((64 & j) != 0) {
            i |= 64;
        }
        return (j & 512) != 0 ? i | 8 : i;
    }

    public static void registerRemoteControlClient(Context context, Object obj) {
        ((AudioManager) context.getSystemService(MediaStreamTrack.AUDIO_TRACK_KIND)).registerRemoteControlClient((RemoteControlClient) obj);
    }

    public static void setMetadata(Object obj, Bundle bundle) throws IllegalArgumentException {
        RemoteControlClient.MetadataEditor metadataEditorEditMetadata = ((RemoteControlClient) obj).editMetadata(true);
        buildOldMetadata(bundle, metadataEditorEditMetadata);
        metadataEditorEditMetadata.apply();
    }

    public static void setState(Object obj, int i) {
        ((RemoteControlClient) obj).setPlaybackState(getRccStateFromState(i));
    }

    public static void setTransportControlFlags(Object obj, long j) {
        ((RemoteControlClient) obj).setTransportControlFlags(getRccTransportControlFlagsFromActions(j));
    }

    public static void unregisterRemoteControlClient(Context context, Object obj) {
        ((AudioManager) context.getSystemService(MediaStreamTrack.AUDIO_TRACK_KIND)).unregisterRemoteControlClient((RemoteControlClient) obj);
    }
}
