package org.webrtc;

import android.annotation.TargetApi;
import android.media.MediaCodecInfo;
import android.os.Build;
import androidx.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes.dex */
class MediaCodecUtils {
    static final String EXYNOS_PREFIX = "OMX.Exynos.";
    static final String INTEL_PREFIX = "OMX.Intel.";
    static final String NVIDIA_PREFIX = "OMX.Nvidia.";
    static final String QCOM_PREFIX = "OMX.qcom.";
    private static final String TAG = "MediaCodecUtils";
    static final String[] SOFTWARE_IMPLEMENTATION_PREFIXES = {"OMX.google.", "OMX.SEC.", "c2.android"};
    static final int COLOR_QCOM_FORMATYVU420PackedSemiPlanar32m4ka = 2141391873;
    static final int COLOR_QCOM_FORMATYVU420PackedSemiPlanar16m4ka = 2141391874;
    static final int COLOR_QCOM_FORMATYVU420PackedSemiPlanar64x32Tile2m8ka = 2141391875;
    static final int COLOR_QCOM_FORMATYUV420PackedSemiPlanar32m = 2141391876;
    static final int[] DECODER_COLOR_FORMATS = {19, 21, 2141391872, COLOR_QCOM_FORMATYVU420PackedSemiPlanar32m4ka, COLOR_QCOM_FORMATYVU420PackedSemiPlanar16m4ka, COLOR_QCOM_FORMATYVU420PackedSemiPlanar64x32Tile2m8ka, COLOR_QCOM_FORMATYUV420PackedSemiPlanar32m};
    static final int[] ENCODER_COLOR_FORMATS = {19, 21, 2141391872, COLOR_QCOM_FORMATYUV420PackedSemiPlanar32m};
    static final int[] TEXTURE_COLOR_FORMATS = getTextureColorFormats();

    private MediaCodecUtils() {
    }

    static boolean codecSupportsType(MediaCodecInfo mediaCodecInfo, VideoCodecMimeType videoCodecMimeType) {
        for (String str : mediaCodecInfo.getSupportedTypes()) {
            if (videoCodecMimeType.mimeType().equals(str)) {
                return true;
            }
        }
        return false;
    }

    static Map<String, String> getCodecProperties(VideoCodecMimeType videoCodecMimeType, boolean z) {
        switch (videoCodecMimeType) {
            case VP8:
            case VP9:
            case AV1:
                return new HashMap();
            case H264:
                return H264Utils.getDefaultH264Params(z);
            default:
                throw new IllegalArgumentException("Unsupported codec: " + videoCodecMimeType);
        }
    }

    private static int[] getTextureColorFormats() {
        return Build.VERSION.SDK_INT >= 18 ? new int[]{2130708361} : new int[0];
    }

    static boolean isHardwareAccelerated(MediaCodecInfo mediaCodecInfo) {
        return Build.VERSION.SDK_INT >= 29 ? isHardwareAcceleratedQOrHigher(mediaCodecInfo) : !isSoftwareOnly(mediaCodecInfo);
    }

    @TargetApi(29)
    private static boolean isHardwareAcceleratedQOrHigher(MediaCodecInfo mediaCodecInfo) {
        return mediaCodecInfo.isHardwareAccelerated();
    }

    static boolean isSoftwareOnly(MediaCodecInfo mediaCodecInfo) {
        if (Build.VERSION.SDK_INT >= 29) {
            return isSoftwareOnlyQOrHigher(mediaCodecInfo);
        }
        String name = mediaCodecInfo.getName();
        for (String str : SOFTWARE_IMPLEMENTATION_PREFIXES) {
            if (name.startsWith(str)) {
                return true;
            }
        }
        return false;
    }

    @TargetApi(29)
    private static boolean isSoftwareOnlyQOrHigher(MediaCodecInfo mediaCodecInfo) {
        return mediaCodecInfo.isSoftwareOnly();
    }

    @Nullable
    static Integer selectColorFormat(int[] iArr, MediaCodecInfo.CodecCapabilities codecCapabilities) {
        for (int i : iArr) {
            for (int i2 : codecCapabilities.colorFormats) {
                if (i2 == i) {
                    return Integer.valueOf(i2);
                }
            }
        }
        return null;
    }
}
