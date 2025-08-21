package org.acra.util;

/* loaded from: classes.dex */
public final class ReflectionHelper {
    public Object create(String str) throws ReflectionException {
        try {
            return Class.forName(str).newInstance();
        } catch (ClassNotFoundException e) {
            throw new ReflectionException("Could not find class : " + str, e);
        } catch (IllegalAccessException e2) {
            throw new ReflectionException("Could not access class : " + str, e2);
        } catch (InstantiationException e3) {
            throw new ReflectionException("Could not instantiate class : " + str, e3);
        }
    }
}
