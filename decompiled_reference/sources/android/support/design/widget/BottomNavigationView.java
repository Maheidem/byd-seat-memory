package android.support.design.widget;

import android.R;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.internal.BottomNavigationMenu;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.internal.BottomNavigationPresenter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.view.SupportMenuInflater;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.TintTypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

/* loaded from: classes.dex */
public class BottomNavigationView extends FrameLayout {
    private static final int[] CHECKED_STATE_SET = {R.attr.state_checked};
    private static final int[] DISABLED_STATE_SET = {-16842910};
    private OnNavigationItemSelectedListener mListener;
    private final MenuBuilder mMenu;
    private MenuInflater mMenuInflater;
    private final BottomNavigationMenuView mMenuView;
    private final BottomNavigationPresenter mPresenter;

    public interface OnNavigationItemSelectedListener {
        boolean onNavigationItemSelected(@NonNull MenuItem menuItem);
    }

    public BottomNavigationView(Context context) {
        this(context, null);
    }

    public BottomNavigationView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public BottomNavigationView(Context context, AttributeSet attributeSet, int i) throws Resources.NotFoundException {
        BottomNavigationMenuView bottomNavigationMenuView;
        ColorStateList colorStateListCreateDefaultColorStateList;
        BottomNavigationMenuView bottomNavigationMenuView2;
        ColorStateList colorStateListCreateDefaultColorStateList2;
        super(context, attributeSet, i);
        this.mPresenter = new BottomNavigationPresenter();
        ThemeUtils.checkAppCompatTheme(context);
        this.mMenu = new BottomNavigationMenu(context);
        this.mMenuView = new BottomNavigationMenuView(context);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-2, -2);
        layoutParams.gravity = 17;
        this.mMenuView.setLayoutParams(layoutParams);
        this.mPresenter.setBottomNavigationMenuView(this.mMenuView);
        this.mMenuView.setPresenter(this.mPresenter);
        this.mMenu.addMenuPresenter(this.mPresenter);
        this.mPresenter.initForMenu(getContext(), this.mMenu);
        TintTypedArray tintTypedArrayObtainStyledAttributes = TintTypedArray.obtainStyledAttributes(context, attributeSet, android.support.design.R.styleable.BottomNavigationView, i, android.support.design.R.style.Widget_Design_BottomNavigationView);
        if (tintTypedArrayObtainStyledAttributes.hasValue(android.support.design.R.styleable.BottomNavigationView_itemIconTint)) {
            bottomNavigationMenuView = this.mMenuView;
            colorStateListCreateDefaultColorStateList = tintTypedArrayObtainStyledAttributes.getColorStateList(android.support.design.R.styleable.BottomNavigationView_itemIconTint);
        } else {
            bottomNavigationMenuView = this.mMenuView;
            colorStateListCreateDefaultColorStateList = createDefaultColorStateList(R.attr.textColorSecondary);
        }
        bottomNavigationMenuView.setIconTintList(colorStateListCreateDefaultColorStateList);
        if (tintTypedArrayObtainStyledAttributes.hasValue(android.support.design.R.styleable.BottomNavigationView_itemTextColor)) {
            bottomNavigationMenuView2 = this.mMenuView;
            colorStateListCreateDefaultColorStateList2 = tintTypedArrayObtainStyledAttributes.getColorStateList(android.support.design.R.styleable.BottomNavigationView_itemTextColor);
        } else {
            bottomNavigationMenuView2 = this.mMenuView;
            colorStateListCreateDefaultColorStateList2 = createDefaultColorStateList(R.attr.textColorSecondary);
        }
        bottomNavigationMenuView2.setItemTextColor(colorStateListCreateDefaultColorStateList2);
        if (tintTypedArrayObtainStyledAttributes.hasValue(android.support.design.R.styleable.BottomNavigationView_elevation)) {
            ViewCompat.setElevation(this, tintTypedArrayObtainStyledAttributes.getDimensionPixelSize(android.support.design.R.styleable.BottomNavigationView_elevation, 0));
        }
        this.mMenuView.setItemBackgroundRes(tintTypedArrayObtainStyledAttributes.getResourceId(android.support.design.R.styleable.BottomNavigationView_itemBackground, 0));
        if (tintTypedArrayObtainStyledAttributes.hasValue(android.support.design.R.styleable.BottomNavigationView_menu)) {
            inflateMenu(tintTypedArrayObtainStyledAttributes.getResourceId(android.support.design.R.styleable.BottomNavigationView_menu, 0));
        }
        tintTypedArrayObtainStyledAttributes.recycle();
        addView(this.mMenuView, layoutParams);
        if (Build.VERSION.SDK_INT < 21) {
            addCompatibilityTopDivider(context);
        }
        this.mMenu.setCallback(new MenuBuilder.Callback() { // from class: android.support.design.widget.BottomNavigationView.1
            @Override // android.support.v7.view.menu.MenuBuilder.Callback
            public boolean onMenuItemSelected(MenuBuilder menuBuilder, MenuItem menuItem) {
                return (BottomNavigationView.this.mListener == null || BottomNavigationView.this.mListener.onNavigationItemSelected(menuItem)) ? false : true;
            }

            @Override // android.support.v7.view.menu.MenuBuilder.Callback
            public void onMenuModeChange(MenuBuilder menuBuilder) {
            }
        });
    }

    private void addCompatibilityTopDivider(Context context) {
        View view = new View(context);
        view.setBackgroundColor(ContextCompat.getColor(context, android.support.design.R.color.design_bottom_navigation_shadow_color));
        view.setLayoutParams(new FrameLayout.LayoutParams(-1, getResources().getDimensionPixelSize(android.support.design.R.dimen.design_bottom_navigation_shadow_height)));
        addView(view);
    }

    private ColorStateList createDefaultColorStateList(int i) throws Resources.NotFoundException {
        TypedValue typedValue = new TypedValue();
        if (!getContext().getTheme().resolveAttribute(i, typedValue, true)) {
            return null;
        }
        ColorStateList colorStateList = AppCompatResources.getColorStateList(getContext(), typedValue.resourceId);
        if (!getContext().getTheme().resolveAttribute(android.support.v7.appcompat.R.attr.colorPrimary, typedValue, true)) {
            return null;
        }
        int i2 = typedValue.data;
        int defaultColor = colorStateList.getDefaultColor();
        return new ColorStateList(new int[][]{DISABLED_STATE_SET, CHECKED_STATE_SET, EMPTY_STATE_SET}, new int[]{colorStateList.getColorForState(DISABLED_STATE_SET, defaultColor), i2, defaultColor});
    }

    private MenuInflater getMenuInflater() {
        if (this.mMenuInflater == null) {
            this.mMenuInflater = new SupportMenuInflater(getContext());
        }
        return this.mMenuInflater;
    }

    @DrawableRes
    public int getItemBackgroundResource() {
        return this.mMenuView.getItemBackgroundRes();
    }

    @Nullable
    public ColorStateList getItemIconTintList() {
        return this.mMenuView.getIconTintList();
    }

    @Nullable
    public ColorStateList getItemTextColor() {
        return this.mMenuView.getItemTextColor();
    }

    public int getMaxItemCount() {
        return 5;
    }

    @NonNull
    public Menu getMenu() {
        return this.mMenu;
    }

    public void inflateMenu(int i) {
        this.mPresenter.setUpdateSuspended(true);
        getMenuInflater().inflate(i, this.mMenu);
        this.mPresenter.setUpdateSuspended(false);
        this.mPresenter.updateMenuView(true);
    }

    public void setItemBackgroundResource(@DrawableRes int i) {
        this.mMenuView.setItemBackgroundRes(i);
    }

    public void setItemIconTintList(@Nullable ColorStateList colorStateList) {
        this.mMenuView.setIconTintList(colorStateList);
    }

    public void setItemTextColor(@Nullable ColorStateList colorStateList) {
        this.mMenuView.setItemTextColor(colorStateList);
    }

    public void setOnNavigationItemSelectedListener(@Nullable OnNavigationItemSelectedListener onNavigationItemSelectedListener) {
        this.mListener = onNavigationItemSelectedListener;
    }
}
