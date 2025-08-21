package br.com.rory.electro.logs.logs;

import br.com.rory.electro.logs.a;
import com.rory.electro.NativeLoader;

/* loaded from: classes.dex */
public class DoorStateChangedLog extends a {
    private int area;
    private int state;

    static {
        NativeLoader.classesInit0(147);
    }

    public DoorStateChangedLog(int i, int i2) {
        this.state = i2;
        this.area = i;
    }

    @Override // br.com.rory.electro.logs.a
    public native int getType();
}
