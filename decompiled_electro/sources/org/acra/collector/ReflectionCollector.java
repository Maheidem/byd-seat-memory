package org.acra.collector;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* loaded from: classes.dex */
final class ReflectionCollector {
    ReflectionCollector() {
    }

    public static String collectConstants(Class<?> cls) {
        return collectConstants(cls, "");
    }

    public static String collectConstants(Class<?> cls, String str) throws IllegalAccessException, SecurityException, IllegalArgumentException {
        StringBuilder sb = new StringBuilder();
        for (Field field : cls.getFields()) {
            if (str != null && str.length() > 0) {
                sb.append(str);
                sb.append('.');
            }
            sb.append(field.getName());
            sb.append("=");
            try {
                Object obj = field.get(null);
                if (obj != null) {
                    sb.append(obj.toString());
                }
            } catch (IllegalAccessException | IllegalArgumentException unused) {
                sb.append("N/A");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public static String collectStaticGettersResults(Class<?> cls) throws SecurityException {
        StringBuilder sb = new StringBuilder();
        for (Method method : cls.getMethods()) {
            if (method.getParameterTypes().length == 0 && ((method.getName().startsWith("get") || method.getName().startsWith("is")) && !method.getName().equals("getClass"))) {
                try {
                    sb.append(method.getName());
                    sb.append('=');
                    sb.append(method.invoke(null, (Object[]) null));
                    sb.append("\n");
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException unused) {
                }
            }
        }
        return sb.toString();
    }
}
