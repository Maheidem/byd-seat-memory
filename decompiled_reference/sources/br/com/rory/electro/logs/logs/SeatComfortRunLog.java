package br.com.rory.electro.logs.logs;

import br.com.rory.electro.logs.a;
import com.rory.electro.NativeLoader;

/* loaded from: classes.dex */
public class SeatComfortRunLog extends a {
    private int driverComfortStage;
    private int passengerComfortStage;

    static {
        NativeLoader.classesInit0(215);
    }

    public SeatComfortRunLog(int i, int i2) {
        this.driverComfortStage = i;
        this.passengerComfortStage = i2;
    }

    @Override // br.com.rory.electro.logs.a
    public native int getType();
}
