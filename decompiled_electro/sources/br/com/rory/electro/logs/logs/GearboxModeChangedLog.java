package br.com.rory.electro.logs.logs;

import br.com.rory.electro.logs.a;
import com.rory.electro.NativeLoader;

/* loaded from: classes.dex */
public class GearboxModeChangedLog extends a {
    private boolean isDriving;

    static {
        NativeLoader.classesInit0(239);
    }

    public GearboxModeChangedLog(boolean z) {
        this.isDriving = z;
    }

    @Override // br.com.rory.electro.logs.a
    public native int getType();
}
