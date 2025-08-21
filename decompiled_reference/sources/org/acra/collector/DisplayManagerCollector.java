package org.acra.collector;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.SparseArray;
import android.view.Display;
import android.view.WindowManager;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.acra.ACRA;

/* loaded from: classes.dex */
final class DisplayManagerCollector {
    static final SparseArray<String> mFlagsNames = new SparseArray<>();
    static final SparseArray<String> mDensities = new SparseArray<>();

    DisplayManagerCollector() {
    }

    private static String activeFlags(SparseArray<String> sparseArray, int i) {
        StringBuilder sb = new StringBuilder();
        for (int i2 = 0; i2 < sparseArray.size(); i2++) {
            int iKeyAt = sparseArray.keyAt(i2) & i;
            if (iKeyAt > 0) {
                if (sb.length() > 0) {
                    sb.append('+');
                }
                sb.append(sparseArray.get(iKeyAt));
            }
        }
        return sb.toString();
    }

    private static String collectCurrentSizeRange(Display display) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        StringBuilder sb = new StringBuilder();
        try {
            Method method = display.getClass().getMethod("getCurrentSizeRange", Point.class, Point.class);
            Point point = new Point();
            Point point2 = new Point();
            method.invoke(display, point, point2);
            sb.append(display.getDisplayId());
            sb.append(".currentSizeRange.smallest=[");
            sb.append(point.x);
            sb.append(',');
            sb.append(point.y);
            sb.append(']');
            sb.append('\n');
            sb.append(display.getDisplayId());
            sb.append(".currentSizeRange.largest=[");
            sb.append(point2.x);
            sb.append(',');
            sb.append(point2.y);
            sb.append(']');
            sb.append('\n');
        } catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException unused) {
        }
        return sb.toString();
    }

    private static Object collectDisplayData(Display display) {
        display.getMetrics(new DisplayMetrics());
        return collectCurrentSizeRange(display) + collectFlags(display) + display.getDisplayId() + ".height=" + display.getHeight() + '\n' + collectMetrics(display, "getMetrics") + collectName(display) + display.getDisplayId() + ".orientation=" + display.getOrientation() + '\n' + display.getDisplayId() + ".pixelFormat=" + display.getPixelFormat() + '\n' + collectMetrics(display, "getRealMetrics") + collectSize(display, "getRealSize") + collectRectSize(display) + display.getDisplayId() + ".refreshRate=" + display.getRefreshRate() + '\n' + collectRotation(display) + collectSize(display, "getSize") + display.getDisplayId() + ".width=" + display.getWidth() + '\n' + collectIsValid(display);
    }

    public static String collectDisplays(Context context) {
        StringBuilder sb = new StringBuilder();
        Display[] displayArr = null;
        if (Compatibility.getAPILevel() < 17) {
            displayArr = new Display[]{((WindowManager) context.getSystemService("window")).getDefaultDisplay()};
        } else {
            try {
                Object systemService = context.getSystemService((String) context.getClass().getField("DISPLAY_SERVICE").get(null));
                displayArr = (Display[]) systemService.getClass().getMethod("getDisplays", new Class[0]).invoke(systemService, new Object[0]);
            } catch (IllegalAccessException | IllegalArgumentException | NoSuchFieldException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
                ACRA.log.w(ACRA.LOG_TAG, "Error while collecting DisplayManager data: ", e);
            }
        }
        for (Display display : displayArr) {
            sb.append(collectDisplayData(display));
        }
        return sb.toString();
    }

    private static String collectFlags(Display display) throws SecurityException {
        StringBuilder sb = new StringBuilder();
        try {
            int iIntValue = ((Integer) display.getClass().getMethod("getFlags", new Class[0]).invoke(display, new Object[0])).intValue();
            for (Field field : display.getClass().getFields()) {
                if (field.getName().startsWith("FLAG_")) {
                    mFlagsNames.put(field.getInt(null), field.getName());
                }
            }
            sb.append(display.getDisplayId());
            sb.append(".flags=");
            sb.append(activeFlags(mFlagsNames, iIntValue));
            sb.append('\n');
        } catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException unused) {
        }
        return sb.toString();
    }

    private static Object collectIsValid(Display display) {
        StringBuilder sb = new StringBuilder();
        try {
            Boolean bool = (Boolean) display.getClass().getMethod("isValid", new Class[0]).invoke(display, new Object[0]);
            sb.append(display.getDisplayId());
            sb.append(".isValid=");
            sb.append(bool);
            sb.append('\n');
        } catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException unused) {
        }
        return sb.toString();
    }

    private static Object collectMetrics(Display display, String str) throws SecurityException {
        StringBuilder sb = new StringBuilder();
        try {
            DisplayMetrics displayMetrics = (DisplayMetrics) display.getClass().getMethod(str, new Class[0]).invoke(display, new Object[0]);
            for (Field field : DisplayMetrics.class.getFields()) {
                if (field.getType().equals(Integer.class) && field.getName().startsWith("DENSITY_") && !field.getName().equals("DENSITY_DEFAULT")) {
                    mDensities.put(field.getInt(null), field.getName());
                }
            }
            sb.append(display.getDisplayId());
            sb.append('.');
            sb.append(str);
            sb.append(".density=");
            sb.append(displayMetrics.density);
            sb.append('\n');
            sb.append(display.getDisplayId());
            sb.append('.');
            sb.append(str);
            sb.append(".densityDpi=");
            sb.append(displayMetrics.getClass().getField("densityDpi"));
            sb.append('\n');
            sb.append(display.getDisplayId());
            sb.append('.');
            sb.append(str);
            sb.append("scaledDensity=x");
            sb.append(displayMetrics.scaledDensity);
            sb.append('\n');
            sb.append(display.getDisplayId());
            sb.append('.');
            sb.append(str);
            sb.append(".widthPixels=");
            sb.append(displayMetrics.widthPixels);
            sb.append('\n');
            sb.append(display.getDisplayId());
            sb.append('.');
            sb.append(str);
            sb.append(".heightPixels=");
            sb.append(displayMetrics.heightPixels);
            sb.append('\n');
            sb.append(display.getDisplayId());
            sb.append('.');
            sb.append(str);
            sb.append(".xdpi=");
            sb.append(displayMetrics.xdpi);
            sb.append('\n');
            sb.append(display.getDisplayId());
            sb.append('.');
            sb.append(str);
            sb.append(".ydpi=");
            sb.append(displayMetrics.ydpi);
            sb.append('\n');
        } catch (IllegalAccessException | IllegalArgumentException | NoSuchFieldException | NoSuchMethodException | SecurityException | InvocationTargetException unused) {
        }
        return sb.toString();
    }

    private static String collectName(Display display) {
        StringBuilder sb = new StringBuilder();
        try {
            String str = (String) display.getClass().getMethod("getName", new Class[0]).invoke(display, new Object[0]);
            sb.append(display.getDisplayId());
            sb.append(".name=");
            sb.append(str);
            sb.append('\n');
        } catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException unused) {
        }
        return sb.toString();
    }

    private static Object collectRectSize(Display display) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        StringBuilder sb = new StringBuilder();
        try {
            Method method = display.getClass().getMethod("getRectSize", Rect.class);
            Rect rect = new Rect();
            method.invoke(display, rect);
            sb.append(display.getDisplayId());
            sb.append(".rectSize=[");
            sb.append(rect.top);
            sb.append(',');
            sb.append(rect.left);
            sb.append(',');
            sb.append(rect.width());
            sb.append(',');
            sb.append(rect.height());
            sb.append(']');
            sb.append('\n');
        } catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException unused) {
        }
        return sb.toString();
    }

    private static Object collectRotation(Display display) {
        String str;
        StringBuilder sb = new StringBuilder();
        try {
            int iIntValue = ((Integer) display.getClass().getMethod("getRotation", new Class[0]).invoke(display, new Object[0])).intValue();
            sb.append(display.getDisplayId());
            sb.append(".rotation=");
            switch (iIntValue) {
                case 0:
                    str = "ROTATION_0";
                    sb.append(str);
                    break;
                case 1:
                    str = "ROTATION_90";
                    sb.append(str);
                    break;
                case 2:
                    str = "ROTATION_180";
                    sb.append(str);
                    break;
                case 3:
                    str = "ROTATION_270";
                    sb.append(str);
                    break;
                default:
                    sb.append(iIntValue);
                    break;
            }
            sb.append('\n');
        } catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException unused) {
        }
        return sb.toString();
    }

    private static Object collectSize(Display display, String str) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        StringBuilder sb = new StringBuilder();
        try {
            Method method = display.getClass().getMethod(str, Point.class);
            Point point = new Point();
            method.invoke(display, point);
            sb.append(display.getDisplayId());
            sb.append('.');
            sb.append(str);
            sb.append("=[");
            sb.append(point.x);
            sb.append(',');
            sb.append(point.y);
            sb.append(']');
            sb.append('\n');
        } catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException unused) {
        }
        return sb.toString();
    }
}
