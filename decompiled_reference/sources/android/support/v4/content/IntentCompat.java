package android.support.v4.content;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;

/* loaded from: classes.dex */
public final class IntentCompat {
    public static final String ACTION_EXTERNAL_APPLICATIONS_AVAILABLE = "android.intent.action.EXTERNAL_APPLICATIONS_AVAILABLE";
    public static final String ACTION_EXTERNAL_APPLICATIONS_UNAVAILABLE = "android.intent.action.EXTERNAL_APPLICATIONS_UNAVAILABLE";
    public static final String CATEGORY_LEANBACK_LAUNCHER = "android.intent.category.LEANBACK_LAUNCHER";
    public static final String EXTRA_CHANGED_PACKAGE_LIST = "android.intent.extra.changed_package_list";
    public static final String EXTRA_CHANGED_UID_LIST = "android.intent.extra.changed_uid_list";
    public static final String EXTRA_HTML_TEXT = "android.intent.extra.HTML_TEXT";
    public static final int FLAG_ACTIVITY_CLEAR_TASK = 32768;
    public static final int FLAG_ACTIVITY_TASK_ON_HOME = 16384;
    private static final IntentCompatImpl IMPL;

    interface IntentCompatImpl {
        Intent makeMainActivity(ComponentName componentName);

        Intent makeMainSelectorActivity(String str, String str2);

        Intent makeRestartActivityTask(ComponentName componentName);
    }

    static class IntentCompatImplBase implements IntentCompatImpl {
        IntentCompatImplBase() {
        }

        @Override // android.support.v4.content.IntentCompat.IntentCompatImpl
        public Intent makeMainActivity(ComponentName componentName) {
            Intent intent = new Intent("android.intent.action.MAIN");
            intent.setComponent(componentName);
            intent.addCategory("android.intent.category.LAUNCHER");
            return intent;
        }

        @Override // android.support.v4.content.IntentCompat.IntentCompatImpl
        public Intent makeMainSelectorActivity(String str, String str2) {
            Intent intent = new Intent(str);
            intent.addCategory(str2);
            return intent;
        }

        @Override // android.support.v4.content.IntentCompat.IntentCompatImpl
        public Intent makeRestartActivityTask(ComponentName componentName) {
            Intent intentMakeMainActivity = makeMainActivity(componentName);
            intentMakeMainActivity.addFlags(268468224);
            return intentMakeMainActivity;
        }
    }

    static class IntentCompatImplHC extends IntentCompatImplBase {
        IntentCompatImplHC() {
        }

        @Override // android.support.v4.content.IntentCompat.IntentCompatImplBase, android.support.v4.content.IntentCompat.IntentCompatImpl
        public Intent makeMainActivity(ComponentName componentName) {
            return IntentCompatHoneycomb.makeMainActivity(componentName);
        }

        @Override // android.support.v4.content.IntentCompat.IntentCompatImplBase, android.support.v4.content.IntentCompat.IntentCompatImpl
        public Intent makeRestartActivityTask(ComponentName componentName) {
            return IntentCompatHoneycomb.makeRestartActivityTask(componentName);
        }
    }

    static class IntentCompatImplIcsMr1 extends IntentCompatImplHC {
        IntentCompatImplIcsMr1() {
        }

        @Override // android.support.v4.content.IntentCompat.IntentCompatImplBase, android.support.v4.content.IntentCompat.IntentCompatImpl
        public Intent makeMainSelectorActivity(String str, String str2) {
            return IntentCompatIcsMr1.makeMainSelectorActivity(str, str2);
        }
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    static {
        int i = Build.VERSION.SDK_INT;
        IMPL = i >= 15 ? new IntentCompatImplIcsMr1() : i >= 11 ? new IntentCompatImplHC() : new IntentCompatImplBase();
    }

    private IntentCompat() {
    }

    public static Intent makeMainActivity(ComponentName componentName) {
        return IMPL.makeMainActivity(componentName);
    }

    public static Intent makeMainSelectorActivity(String str, String str2) {
        return IMPL.makeMainSelectorActivity(str, str2);
    }

    public static Intent makeRestartActivityTask(ComponentName componentName) {
        return IMPL.makeRestartActivityTask(componentName);
    }
}
