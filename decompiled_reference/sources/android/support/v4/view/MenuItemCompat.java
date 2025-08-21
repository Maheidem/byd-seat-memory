package android.support.v4.view;

import android.os.Build;
import android.support.v4.internal.view.SupportMenuItem;
import android.support.v4.view.MenuItemCompatIcs;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

/* loaded from: classes.dex */
public final class MenuItemCompat {
    static final MenuVersionImpl IMPL;
    public static final int SHOW_AS_ACTION_ALWAYS = 2;
    public static final int SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW = 8;
    public static final int SHOW_AS_ACTION_IF_ROOM = 1;
    public static final int SHOW_AS_ACTION_NEVER = 0;
    public static final int SHOW_AS_ACTION_WITH_TEXT = 4;
    private static final String TAG = "MenuItemCompat";

    static class BaseMenuVersionImpl implements MenuVersionImpl {
        BaseMenuVersionImpl() {
        }

        @Override // android.support.v4.view.MenuItemCompat.MenuVersionImpl
        public boolean collapseActionView(MenuItem menuItem) {
            return false;
        }

        @Override // android.support.v4.view.MenuItemCompat.MenuVersionImpl
        public boolean expandActionView(MenuItem menuItem) {
            return false;
        }

        @Override // android.support.v4.view.MenuItemCompat.MenuVersionImpl
        public View getActionView(MenuItem menuItem) {
            return null;
        }

        @Override // android.support.v4.view.MenuItemCompat.MenuVersionImpl
        public boolean isActionViewExpanded(MenuItem menuItem) {
            return false;
        }

        @Override // android.support.v4.view.MenuItemCompat.MenuVersionImpl
        public MenuItem setActionView(MenuItem menuItem, int i) {
            return menuItem;
        }

        @Override // android.support.v4.view.MenuItemCompat.MenuVersionImpl
        public MenuItem setActionView(MenuItem menuItem, View view) {
            return menuItem;
        }

        @Override // android.support.v4.view.MenuItemCompat.MenuVersionImpl
        public MenuItem setOnActionExpandListener(MenuItem menuItem, OnActionExpandListener onActionExpandListener) {
            return menuItem;
        }

        @Override // android.support.v4.view.MenuItemCompat.MenuVersionImpl
        public void setShowAsAction(MenuItem menuItem, int i) {
        }
    }

    static class HoneycombMenuVersionImpl implements MenuVersionImpl {
        HoneycombMenuVersionImpl() {
        }

        @Override // android.support.v4.view.MenuItemCompat.MenuVersionImpl
        public boolean collapseActionView(MenuItem menuItem) {
            return false;
        }

        @Override // android.support.v4.view.MenuItemCompat.MenuVersionImpl
        public boolean expandActionView(MenuItem menuItem) {
            return false;
        }

        @Override // android.support.v4.view.MenuItemCompat.MenuVersionImpl
        public View getActionView(MenuItem menuItem) {
            return MenuItemCompatHoneycomb.getActionView(menuItem);
        }

        @Override // android.support.v4.view.MenuItemCompat.MenuVersionImpl
        public boolean isActionViewExpanded(MenuItem menuItem) {
            return false;
        }

        @Override // android.support.v4.view.MenuItemCompat.MenuVersionImpl
        public MenuItem setActionView(MenuItem menuItem, int i) {
            return MenuItemCompatHoneycomb.setActionView(menuItem, i);
        }

        @Override // android.support.v4.view.MenuItemCompat.MenuVersionImpl
        public MenuItem setActionView(MenuItem menuItem, View view) {
            return MenuItemCompatHoneycomb.setActionView(menuItem, view);
        }

        @Override // android.support.v4.view.MenuItemCompat.MenuVersionImpl
        public MenuItem setOnActionExpandListener(MenuItem menuItem, OnActionExpandListener onActionExpandListener) {
            return menuItem;
        }

        @Override // android.support.v4.view.MenuItemCompat.MenuVersionImpl
        public void setShowAsAction(MenuItem menuItem, int i) {
            MenuItemCompatHoneycomb.setShowAsAction(menuItem, i);
        }
    }

    static class IcsMenuVersionImpl extends HoneycombMenuVersionImpl {
        IcsMenuVersionImpl() {
        }

        @Override // android.support.v4.view.MenuItemCompat.HoneycombMenuVersionImpl, android.support.v4.view.MenuItemCompat.MenuVersionImpl
        public boolean collapseActionView(MenuItem menuItem) {
            return MenuItemCompatIcs.collapseActionView(menuItem);
        }

        @Override // android.support.v4.view.MenuItemCompat.HoneycombMenuVersionImpl, android.support.v4.view.MenuItemCompat.MenuVersionImpl
        public boolean expandActionView(MenuItem menuItem) {
            return MenuItemCompatIcs.expandActionView(menuItem);
        }

        @Override // android.support.v4.view.MenuItemCompat.HoneycombMenuVersionImpl, android.support.v4.view.MenuItemCompat.MenuVersionImpl
        public boolean isActionViewExpanded(MenuItem menuItem) {
            return MenuItemCompatIcs.isActionViewExpanded(menuItem);
        }

        @Override // android.support.v4.view.MenuItemCompat.HoneycombMenuVersionImpl, android.support.v4.view.MenuItemCompat.MenuVersionImpl
        public MenuItem setOnActionExpandListener(MenuItem menuItem, final OnActionExpandListener onActionExpandListener) {
            return onActionExpandListener == null ? MenuItemCompatIcs.setOnActionExpandListener(menuItem, null) : MenuItemCompatIcs.setOnActionExpandListener(menuItem, new MenuItemCompatIcs.SupportActionExpandProxy() { // from class: android.support.v4.view.MenuItemCompat.IcsMenuVersionImpl.1
                @Override // android.support.v4.view.MenuItemCompatIcs.SupportActionExpandProxy
                public boolean onMenuItemActionCollapse(MenuItem menuItem2) {
                    return onActionExpandListener.onMenuItemActionCollapse(menuItem2);
                }

                @Override // android.support.v4.view.MenuItemCompatIcs.SupportActionExpandProxy
                public boolean onMenuItemActionExpand(MenuItem menuItem2) {
                    return onActionExpandListener.onMenuItemActionExpand(menuItem2);
                }
            });
        }
    }

    interface MenuVersionImpl {
        boolean collapseActionView(MenuItem menuItem);

        boolean expandActionView(MenuItem menuItem);

        View getActionView(MenuItem menuItem);

        boolean isActionViewExpanded(MenuItem menuItem);

        MenuItem setActionView(MenuItem menuItem, int i);

        MenuItem setActionView(MenuItem menuItem, View view);

        MenuItem setOnActionExpandListener(MenuItem menuItem, OnActionExpandListener onActionExpandListener);

        void setShowAsAction(MenuItem menuItem, int i);
    }

    public interface OnActionExpandListener {
        boolean onMenuItemActionCollapse(MenuItem menuItem);

        boolean onMenuItemActionExpand(MenuItem menuItem);
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    static {
        IMPL = Build.VERSION.SDK_INT >= 14 ? new IcsMenuVersionImpl() : Build.VERSION.SDK_INT >= 11 ? new HoneycombMenuVersionImpl() : new BaseMenuVersionImpl();
    }

    private MenuItemCompat() {
    }

    public static boolean collapseActionView(MenuItem menuItem) {
        return menuItem instanceof SupportMenuItem ? ((SupportMenuItem) menuItem).collapseActionView() : IMPL.collapseActionView(menuItem);
    }

    public static boolean expandActionView(MenuItem menuItem) {
        return menuItem instanceof SupportMenuItem ? ((SupportMenuItem) menuItem).expandActionView() : IMPL.expandActionView(menuItem);
    }

    public static ActionProvider getActionProvider(MenuItem menuItem) {
        if (menuItem instanceof SupportMenuItem) {
            return ((SupportMenuItem) menuItem).getSupportActionProvider();
        }
        Log.w(TAG, "getActionProvider: item does not implement SupportMenuItem; returning null");
        return null;
    }

    public static View getActionView(MenuItem menuItem) {
        return menuItem instanceof SupportMenuItem ? ((SupportMenuItem) menuItem).getActionView() : IMPL.getActionView(menuItem);
    }

    public static boolean isActionViewExpanded(MenuItem menuItem) {
        return menuItem instanceof SupportMenuItem ? ((SupportMenuItem) menuItem).isActionViewExpanded() : IMPL.isActionViewExpanded(menuItem);
    }

    public static MenuItem setActionProvider(MenuItem menuItem, ActionProvider actionProvider) {
        if (menuItem instanceof SupportMenuItem) {
            return ((SupportMenuItem) menuItem).setSupportActionProvider(actionProvider);
        }
        Log.w(TAG, "setActionProvider: item does not implement SupportMenuItem; ignoring");
        return menuItem;
    }

    public static MenuItem setActionView(MenuItem menuItem, int i) {
        return menuItem instanceof SupportMenuItem ? ((SupportMenuItem) menuItem).setActionView(i) : IMPL.setActionView(menuItem, i);
    }

    public static MenuItem setActionView(MenuItem menuItem, View view) {
        return menuItem instanceof SupportMenuItem ? ((SupportMenuItem) menuItem).setActionView(view) : IMPL.setActionView(menuItem, view);
    }

    public static MenuItem setOnActionExpandListener(MenuItem menuItem, OnActionExpandListener onActionExpandListener) {
        return menuItem instanceof SupportMenuItem ? ((SupportMenuItem) menuItem).setSupportOnActionExpandListener(onActionExpandListener) : IMPL.setOnActionExpandListener(menuItem, onActionExpandListener);
    }

    public static void setShowAsAction(MenuItem menuItem, int i) {
        if (menuItem instanceof SupportMenuItem) {
            ((SupportMenuItem) menuItem).setShowAsAction(i);
        } else {
            IMPL.setShowAsAction(menuItem, i);
        }
    }
}
