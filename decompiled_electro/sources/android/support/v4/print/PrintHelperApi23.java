package android.support.v4.print;

import android.annotation.TargetApi;
import android.content.Context;
import android.print.PrintAttributes;
import android.support.annotation.RequiresApi;

@RequiresApi(23)
@TargetApi(23)
/* loaded from: classes.dex */
class PrintHelperApi23 extends PrintHelperApi20 {
    PrintHelperApi23(Context context) {
        super(context);
        this.mIsMinMarginsHandlingCorrect = false;
    }

    @Override // android.support.v4.print.PrintHelperKitkat
    protected PrintAttributes.Builder copyAttributes(PrintAttributes printAttributes) {
        PrintAttributes.Builder builderCopyAttributes = super.copyAttributes(printAttributes);
        if (printAttributes.getDuplexMode() != 0) {
            builderCopyAttributes.setDuplexMode(printAttributes.getDuplexMode());
        }
        return builderCopyAttributes;
    }
}
