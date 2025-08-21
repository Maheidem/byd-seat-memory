package org.acra.collector;

import android.util.SparseArray;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;

/* loaded from: classes.dex */
public class MediaCodecListCollector {
    private static final String COLOR_FORMAT_PREFIX = "COLOR_";
    private static Class<?> codecCapabilitiesClass;
    private static Field colorFormatsField;
    private static Method getCapabilitiesForTypeMethod;
    private static Method getCodecInfoAtMethod;
    private static Method getNameMethod;
    private static Method getSupportedTypesMethod;
    private static Method isEncoderMethod;
    private static Field levelField;
    private static Class<?> mediaCodecInfoClass;
    private static Class<?> mediaCodecListClass;
    private static Field profileField;
    private static Field profileLevelsField;
    private static final String[] MPEG4_TYPES = {"mp4", "mpeg4", "MP4", "MPEG4"};
    private static final String[] AVC_TYPES = {"avc", "h264", "AVC", "H264"};
    private static final String[] H263_TYPES = {"h263", "H263"};
    private static final String[] AAC_TYPES = {"aac", "AAC"};
    private static SparseArray<String> mColorFormatValues = new SparseArray<>();
    private static SparseArray<String> mAVCLevelValues = new SparseArray<>();
    private static SparseArray<String> mAVCProfileValues = new SparseArray<>();
    private static SparseArray<String> mH263LevelValues = new SparseArray<>();
    private static SparseArray<String> mH263ProfileValues = new SparseArray<>();
    private static SparseArray<String> mMPEG4LevelValues = new SparseArray<>();
    private static SparseArray<String> mMPEG4ProfileValues = new SparseArray<>();
    private static SparseArray<String> mAACProfileValues = new SparseArray<>();

    private enum CodecType {
        AVC,
        H263,
        MPEG4,
        AAC
    }

    static {
        SparseArray<String> sparseArray;
        int i;
        try {
            mediaCodecListClass = Class.forName("android.media.MediaCodecList");
            getCodecInfoAtMethod = mediaCodecListClass.getMethod("getCodecInfoAt", Integer.TYPE);
            mediaCodecInfoClass = Class.forName("android.media.MediaCodecInfo");
            getNameMethod = mediaCodecInfoClass.getMethod("getName", new Class[0]);
            isEncoderMethod = mediaCodecInfoClass.getMethod("isEncoder", new Class[0]);
            getSupportedTypesMethod = mediaCodecInfoClass.getMethod("getSupportedTypes", new Class[0]);
            getCapabilitiesForTypeMethod = mediaCodecInfoClass.getMethod("getCapabilitiesForType", String.class);
            codecCapabilitiesClass = Class.forName("android.media.MediaCodecInfo$CodecCapabilities");
            colorFormatsField = codecCapabilitiesClass.getField("colorFormats");
            profileLevelsField = codecCapabilitiesClass.getField("profileLevels");
            for (Field field : codecCapabilitiesClass.getFields()) {
                if (Modifier.isStatic(field.getModifiers()) && Modifier.isFinal(field.getModifiers()) && field.getName().startsWith(COLOR_FORMAT_PREFIX)) {
                    mColorFormatValues.put(field.getInt(null), field.getName());
                }
            }
            Class<?> cls = Class.forName("android.media.MediaCodecInfo$CodecProfileLevel");
            for (Field field2 : cls.getFields()) {
                if (Modifier.isStatic(field2.getModifiers()) && Modifier.isFinal(field2.getModifiers())) {
                    if (field2.getName().startsWith("AVCLevel")) {
                        sparseArray = mAVCLevelValues;
                        i = field2.getInt(null);
                    } else if (field2.getName().startsWith("AVCProfile")) {
                        sparseArray = mAVCProfileValues;
                        i = field2.getInt(null);
                    } else if (field2.getName().startsWith("H263Level")) {
                        sparseArray = mH263LevelValues;
                        i = field2.getInt(null);
                    } else if (field2.getName().startsWith("H263Profile")) {
                        sparseArray = mH263ProfileValues;
                        i = field2.getInt(null);
                    } else if (field2.getName().startsWith("MPEG4Level")) {
                        sparseArray = mMPEG4LevelValues;
                        i = field2.getInt(null);
                    } else if (field2.getName().startsWith("MPEG4Profile")) {
                        sparseArray = mMPEG4ProfileValues;
                        i = field2.getInt(null);
                    } else if (field2.getName().startsWith("AAC")) {
                        sparseArray = mAACProfileValues;
                        i = field2.getInt(null);
                    }
                    sparseArray.put(i, field2.getName());
                }
            }
            profileField = cls.getField("profile");
            levelField = cls.getField("level");
        } catch (ClassNotFoundException | IllegalAccessException | IllegalArgumentException | NoSuchFieldException | NoSuchMethodException | SecurityException unused) {
        }
    }

    public static String collecMediaCodecList() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        StringBuilder sb = new StringBuilder();
        if (mediaCodecListClass != null && mediaCodecInfoClass != null) {
            try {
                int iIntValue = ((Integer) mediaCodecListClass.getMethod("getCodecCount", new Class[0]).invoke(null, new Object[0])).intValue();
                for (int i = 0; i < iIntValue; i++) {
                    sb.append("\n");
                    Object objInvoke = getCodecInfoAtMethod.invoke(null, Integer.valueOf(i));
                    sb.append(i);
                    sb.append(": ");
                    sb.append(getNameMethod.invoke(objInvoke, new Object[0]));
                    sb.append("\n");
                    sb.append("isEncoder: ");
                    sb.append(isEncoderMethod.invoke(objInvoke, new Object[0]));
                    sb.append("\n");
                    String[] strArr = (String[]) getSupportedTypesMethod.invoke(objInvoke, new Object[0]);
                    sb.append("Supported types: ");
                    sb.append(Arrays.toString(strArr));
                    sb.append("\n");
                    for (String str : strArr) {
                        sb.append(collectCapabilitiesForType(objInvoke, str));
                    }
                    sb.append("\n");
                }
            } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException unused) {
            }
        }
        return sb.toString();
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x00d2  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x00d5 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private static java.lang.String collectCapabilitiesForType(java.lang.Object r9, java.lang.String r10) throws java.lang.IllegalAccessException, java.lang.IllegalArgumentException, java.lang.reflect.InvocationTargetException {
        /*
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.reflect.Method r1 = org.acra.collector.MediaCodecListCollector.getCapabilitiesForTypeMethod
            r2 = 1
            java.lang.Object[] r3 = new java.lang.Object[r2]
            r4 = 0
            r3[r4] = r10
            java.lang.Object r1 = r1.invoke(r9, r3)
            java.lang.reflect.Field r3 = org.acra.collector.MediaCodecListCollector.colorFormatsField
            java.lang.Object r3 = r3.get(r1)
            int[] r3 = (int[]) r3
            int r5 = r3.length
            r6 = 44
            if (r5 <= 0) goto L46
            r0.append(r10)
            java.lang.String r5 = " color formats:"
            r0.append(r5)
            r5 = r4
        L27:
            int r7 = r3.length
            if (r5 >= r7) goto L41
            android.util.SparseArray<java.lang.String> r7 = org.acra.collector.MediaCodecListCollector.mColorFormatValues
            r8 = r3[r5]
            java.lang.Object r7 = r7.get(r8)
            java.lang.String r7 = (java.lang.String) r7
            r0.append(r7)
            int r7 = r3.length
            int r7 = r7 - r2
            if (r5 >= r7) goto L3e
            r0.append(r6)
        L3e:
            int r5 = r5 + 1
            goto L27
        L41:
            java.lang.String r3 = "\n"
            r0.append(r3)
        L46:
            java.lang.reflect.Field r3 = org.acra.collector.MediaCodecListCollector.profileLevelsField
            java.lang.Object r1 = r3.get(r1)
            java.lang.Object[] r1 = (java.lang.Object[]) r1
            int r3 = r1.length
            if (r3 <= 0) goto Ldd
            r0.append(r10)
            java.lang.String r10 = " profile levels:"
            r0.append(r10)
        L59:
            int r10 = r1.length
            if (r4 >= r10) goto Ld8
            org.acra.collector.MediaCodecListCollector$CodecType r10 = identifyCodecType(r9)
            java.lang.reflect.Field r3 = org.acra.collector.MediaCodecListCollector.profileField
            r5 = r1[r4]
            int r3 = r3.getInt(r5)
            java.lang.reflect.Field r5 = org.acra.collector.MediaCodecListCollector.levelField
            r7 = r1[r4]
            int r5 = r5.getInt(r7)
            r7 = 45
            if (r10 != 0) goto L7d
            r0.append(r3)
            r0.append(r7)
            r0.append(r5)
        L7d:
            int[] r8 = org.acra.collector.MediaCodecListCollector.AnonymousClass1.$SwitchMap$org$acra$collector$MediaCodecListCollector$CodecType
            int r10 = r10.ordinal()
            r10 = r8[r10]
            switch(r10) {
                case 1: goto Lb2;
                case 2: goto La1;
                case 3: goto L90;
                case 4: goto L89;
                default: goto L88;
            }
        L88:
            goto Lce
        L89:
            android.util.SparseArray<java.lang.String> r10 = org.acra.collector.MediaCodecListCollector.mAACProfileValues
            java.lang.Object r10 = r10.get(r3)
            goto Lc9
        L90:
            android.util.SparseArray<java.lang.String> r10 = org.acra.collector.MediaCodecListCollector.mMPEG4ProfileValues
            java.lang.Object r10 = r10.get(r3)
            java.lang.String r10 = (java.lang.String) r10
            r0.append(r10)
            r0.append(r7)
            android.util.SparseArray<java.lang.String> r10 = org.acra.collector.MediaCodecListCollector.mMPEG4LevelValues
            goto Lc5
        La1:
            android.util.SparseArray<java.lang.String> r10 = org.acra.collector.MediaCodecListCollector.mH263ProfileValues
            java.lang.Object r10 = r10.get(r3)
            java.lang.String r10 = (java.lang.String) r10
            r0.append(r10)
            r0.append(r7)
            android.util.SparseArray<java.lang.String> r10 = org.acra.collector.MediaCodecListCollector.mH263LevelValues
            goto Lc5
        Lb2:
            r0.append(r3)
            android.util.SparseArray<java.lang.String> r10 = org.acra.collector.MediaCodecListCollector.mAVCProfileValues
            java.lang.Object r10 = r10.get(r3)
            java.lang.String r10 = (java.lang.String) r10
            r0.append(r10)
            r0.append(r7)
            android.util.SparseArray<java.lang.String> r10 = org.acra.collector.MediaCodecListCollector.mAVCLevelValues
        Lc5:
            java.lang.Object r10 = r10.get(r5)
        Lc9:
            java.lang.String r10 = (java.lang.String) r10
            r0.append(r10)
        Lce:
            int r10 = r1.length
            int r10 = r10 - r2
            if (r4 >= r10) goto Ld5
            r0.append(r6)
        Ld5:
            int r4 = r4 + 1
            goto L59
        Ld8:
            java.lang.String r9 = "\n"
            r0.append(r9)
        Ldd:
            java.lang.String r9 = "\n"
            r0.append(r9)
            java.lang.String r9 = r0.toString()
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: org.acra.collector.MediaCodecListCollector.collectCapabilitiesForType(java.lang.Object, java.lang.String):java.lang.String");
    }

    private static CodecType identifyCodecType(Object obj) {
        String str = (String) getNameMethod.invoke(obj, new Object[0]);
        for (String str2 : AVC_TYPES) {
            if (str.contains(str2)) {
                return CodecType.AVC;
            }
        }
        for (String str3 : H263_TYPES) {
            if (str.contains(str3)) {
                return CodecType.H263;
            }
        }
        for (String str4 : MPEG4_TYPES) {
            if (str.contains(str4)) {
                return CodecType.MPEG4;
            }
        }
        for (String str5 : AAC_TYPES) {
            if (str.contains(str5)) {
                return CodecType.AAC;
            }
        }
        return null;
    }
}
