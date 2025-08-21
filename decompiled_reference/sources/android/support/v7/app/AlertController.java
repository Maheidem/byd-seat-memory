package android.support.v7.app;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.appcompat.R;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewStub;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import java.lang.ref.WeakReference;

/* loaded from: classes.dex */
class AlertController {
    ListAdapter mAdapter;
    private int mAlertDialogLayout;
    Button mButtonNegative;
    Message mButtonNegativeMessage;
    private CharSequence mButtonNegativeText;
    Button mButtonNeutral;
    Message mButtonNeutralMessage;
    private CharSequence mButtonNeutralText;
    private int mButtonPanelSideLayout;
    Button mButtonPositive;
    Message mButtonPositiveMessage;
    private CharSequence mButtonPositiveText;
    private final Context mContext;
    private View mCustomTitleView;
    final AppCompatDialog mDialog;
    Handler mHandler;
    private Drawable mIcon;
    private ImageView mIconView;
    int mListItemLayout;
    int mListLayout;
    ListView mListView;
    private CharSequence mMessage;
    private TextView mMessageView;
    int mMultiChoiceItemLayout;
    NestedScrollView mScrollView;
    private boolean mShowTitle;
    int mSingleChoiceItemLayout;
    private CharSequence mTitle;
    private TextView mTitleView;
    private View mView;
    private int mViewLayoutResId;
    private int mViewSpacingBottom;
    private int mViewSpacingLeft;
    private int mViewSpacingRight;
    private int mViewSpacingTop;
    private final Window mWindow;
    private boolean mViewSpacingSpecified = false;
    private int mIconId = 0;
    int mCheckedItem = -1;
    private int mButtonPanelLayoutHint = 0;
    private final View.OnClickListener mButtonHandler = new View.OnClickListener() { // from class: android.support.v7.app.AlertController.1
        /* JADX WARN: Removed duplicated region for block: B:20:0x003a  */
        @Override // android.view.View.OnClickListener
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public void onClick(android.view.View r2) {
            /*
                r1 = this;
                android.support.v7.app.AlertController r0 = android.support.v7.app.AlertController.this
                android.widget.Button r0 = r0.mButtonPositive
                if (r2 != r0) goto L15
                android.support.v7.app.AlertController r0 = android.support.v7.app.AlertController.this
                android.os.Message r0 = r0.mButtonPositiveMessage
                if (r0 == 0) goto L15
                android.support.v7.app.AlertController r2 = android.support.v7.app.AlertController.this
                android.os.Message r2 = r2.mButtonPositiveMessage
            L10:
                android.os.Message r2 = android.os.Message.obtain(r2)
                goto L38
            L15:
                android.support.v7.app.AlertController r0 = android.support.v7.app.AlertController.this
                android.widget.Button r0 = r0.mButtonNegative
                if (r2 != r0) goto L26
                android.support.v7.app.AlertController r0 = android.support.v7.app.AlertController.this
                android.os.Message r0 = r0.mButtonNegativeMessage
                if (r0 == 0) goto L26
                android.support.v7.app.AlertController r2 = android.support.v7.app.AlertController.this
                android.os.Message r2 = r2.mButtonNegativeMessage
                goto L10
            L26:
                android.support.v7.app.AlertController r0 = android.support.v7.app.AlertController.this
                android.widget.Button r0 = r0.mButtonNeutral
                if (r2 != r0) goto L37
                android.support.v7.app.AlertController r2 = android.support.v7.app.AlertController.this
                android.os.Message r2 = r2.mButtonNeutralMessage
                if (r2 == 0) goto L37
                android.support.v7.app.AlertController r2 = android.support.v7.app.AlertController.this
                android.os.Message r2 = r2.mButtonNeutralMessage
                goto L10
            L37:
                r2 = 0
            L38:
                if (r2 == 0) goto L3d
                r2.sendToTarget()
            L3d:
                android.support.v7.app.AlertController r2 = android.support.v7.app.AlertController.this
                android.os.Handler r2 = r2.mHandler
                r0 = 1
                android.support.v7.app.AlertController r1 = android.support.v7.app.AlertController.this
                android.support.v7.app.AppCompatDialog r1 = r1.mDialog
                android.os.Message r1 = r2.obtainMessage(r0, r1)
                r1.sendToTarget()
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: android.support.v7.app.AlertController.AnonymousClass1.onClick(android.view.View):void");
        }
    };

    public static class AlertParams {
        public ListAdapter mAdapter;
        public boolean[] mCheckedItems;
        public final Context mContext;
        public Cursor mCursor;
        public View mCustomTitleView;
        public boolean mForceInverseBackground;
        public Drawable mIcon;
        public final LayoutInflater mInflater;
        public String mIsCheckedColumn;
        public boolean mIsMultiChoice;
        public boolean mIsSingleChoice;
        public CharSequence[] mItems;
        public String mLabelColumn;
        public CharSequence mMessage;
        public DialogInterface.OnClickListener mNegativeButtonListener;
        public CharSequence mNegativeButtonText;
        public DialogInterface.OnClickListener mNeutralButtonListener;
        public CharSequence mNeutralButtonText;
        public DialogInterface.OnCancelListener mOnCancelListener;
        public DialogInterface.OnMultiChoiceClickListener mOnCheckboxClickListener;
        public DialogInterface.OnClickListener mOnClickListener;
        public DialogInterface.OnDismissListener mOnDismissListener;
        public AdapterView.OnItemSelectedListener mOnItemSelectedListener;
        public DialogInterface.OnKeyListener mOnKeyListener;
        public OnPrepareListViewListener mOnPrepareListViewListener;
        public DialogInterface.OnClickListener mPositiveButtonListener;
        public CharSequence mPositiveButtonText;
        public CharSequence mTitle;
        public View mView;
        public int mViewLayoutResId;
        public int mViewSpacingBottom;
        public int mViewSpacingLeft;
        public int mViewSpacingRight;
        public int mViewSpacingTop;
        public int mIconId = 0;
        public int mIconAttrId = 0;
        public boolean mViewSpacingSpecified = false;
        public int mCheckedItem = -1;
        public boolean mRecycleOnMeasure = true;
        public boolean mCancelable = true;

        public interface OnPrepareListViewListener {
            void onPrepareListView(ListView listView);
        }

        public AlertParams(Context context) {
            this.mContext = context;
            this.mInflater = (LayoutInflater) context.getSystemService("layout_inflater");
        }

        /* JADX WARN: Removed duplicated region for block: B:32:0x0098  */
        /* JADX WARN: Removed duplicated region for block: B:35:0x00a1  */
        /* JADX WARN: Removed duplicated region for block: B:36:0x00a5  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        private void createListView(final android.support.v7.app.AlertController r11) {
            /*
                r10 = this;
                android.view.LayoutInflater r0 = r10.mInflater
                int r1 = r11.mListLayout
                r2 = 0
                android.view.View r0 = r0.inflate(r1, r2)
                android.support.v7.app.AlertController$RecycleListView r0 = (android.support.v7.app.AlertController.RecycleListView) r0
                boolean r1 = r10.mIsMultiChoice
                r8 = 1
                if (r1 == 0) goto L35
                android.database.Cursor r1 = r10.mCursor
                if (r1 != 0) goto L26
                android.support.v7.app.AlertController$AlertParams$1 r9 = new android.support.v7.app.AlertController$AlertParams$1
                android.content.Context r3 = r10.mContext
                int r4 = r11.mMultiChoiceItemLayout
                r5 = 16908308(0x1020014, float:2.3877285E-38)
                java.lang.CharSequence[] r6 = r10.mItems
                r1 = r9
                r2 = r10
                r7 = r0
                r1.<init>(r3, r4, r5, r6)
                goto L6e
            L26:
                android.support.v7.app.AlertController$AlertParams$2 r9 = new android.support.v7.app.AlertController$AlertParams$2
                android.content.Context r3 = r10.mContext
                android.database.Cursor r4 = r10.mCursor
                r5 = 0
                r1 = r9
                r2 = r10
                r6 = r0
                r7 = r11
                r1.<init>(r3, r4, r5)
                goto L6e
            L35:
                boolean r1 = r10.mIsSingleChoice
                if (r1 == 0) goto L3d
                int r1 = r11.mSingleChoiceItemLayout
            L3b:
                r4 = r1
                goto L40
            L3d:
                int r1 = r11.mListItemLayout
                goto L3b
            L40:
                android.database.Cursor r1 = r10.mCursor
                r2 = 16908308(0x1020014, float:2.3877285E-38)
                if (r1 == 0) goto L5e
                android.widget.SimpleCursorAdapter r1 = new android.widget.SimpleCursorAdapter
                android.content.Context r3 = r10.mContext
                android.database.Cursor r5 = r10.mCursor
                java.lang.String[] r6 = new java.lang.String[r8]
                java.lang.String r7 = r10.mLabelColumn
                r9 = 0
                r6[r9] = r7
                int[] r7 = new int[r8]
                r7[r9] = r2
                r2 = r1
                r2.<init>(r3, r4, r5, r6, r7)
                r9 = r1
                goto L6e
            L5e:
                android.widget.ListAdapter r1 = r10.mAdapter
                if (r1 == 0) goto L65
                android.widget.ListAdapter r9 = r10.mAdapter
                goto L6e
            L65:
                android.support.v7.app.AlertController$CheckedItemAdapter r9 = new android.support.v7.app.AlertController$CheckedItemAdapter
                android.content.Context r1 = r10.mContext
                java.lang.CharSequence[] r3 = r10.mItems
                r9.<init>(r1, r4, r2, r3)
            L6e:
                android.support.v7.app.AlertController$AlertParams$OnPrepareListViewListener r1 = r10.mOnPrepareListViewListener
                if (r1 == 0) goto L77
                android.support.v7.app.AlertController$AlertParams$OnPrepareListViewListener r1 = r10.mOnPrepareListViewListener
                r1.onPrepareListView(r0)
            L77:
                r11.mAdapter = r9
                int r1 = r10.mCheckedItem
                r11.mCheckedItem = r1
                android.content.DialogInterface$OnClickListener r1 = r10.mOnClickListener
                if (r1 == 0) goto L8a
                android.support.v7.app.AlertController$AlertParams$3 r1 = new android.support.v7.app.AlertController$AlertParams$3
                r1.<init>()
            L86:
                r0.setOnItemClickListener(r1)
                goto L94
            L8a:
                android.content.DialogInterface$OnMultiChoiceClickListener r1 = r10.mOnCheckboxClickListener
                if (r1 == 0) goto L94
                android.support.v7.app.AlertController$AlertParams$4 r1 = new android.support.v7.app.AlertController$AlertParams$4
                r1.<init>()
                goto L86
            L94:
                android.widget.AdapterView$OnItemSelectedListener r1 = r10.mOnItemSelectedListener
                if (r1 == 0) goto L9d
                android.widget.AdapterView$OnItemSelectedListener r1 = r10.mOnItemSelectedListener
                r0.setOnItemSelectedListener(r1)
            L9d:
                boolean r1 = r10.mIsSingleChoice
                if (r1 == 0) goto La5
                r0.setChoiceMode(r8)
                goto Lad
            La5:
                boolean r10 = r10.mIsMultiChoice
                if (r10 == 0) goto Lad
                r10 = 2
                r0.setChoiceMode(r10)
            Lad:
                r11.mListView = r0
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: android.support.v7.app.AlertController.AlertParams.createListView(android.support.v7.app.AlertController):void");
        }

        public void apply(AlertController alertController) {
            if (this.mCustomTitleView != null) {
                alertController.setCustomTitle(this.mCustomTitleView);
            } else {
                if (this.mTitle != null) {
                    alertController.setTitle(this.mTitle);
                }
                if (this.mIcon != null) {
                    alertController.setIcon(this.mIcon);
                }
                if (this.mIconId != 0) {
                    alertController.setIcon(this.mIconId);
                }
                if (this.mIconAttrId != 0) {
                    alertController.setIcon(alertController.getIconAttributeResId(this.mIconAttrId));
                }
            }
            if (this.mMessage != null) {
                alertController.setMessage(this.mMessage);
            }
            if (this.mPositiveButtonText != null) {
                alertController.setButton(-1, this.mPositiveButtonText, this.mPositiveButtonListener, null);
            }
            if (this.mNegativeButtonText != null) {
                alertController.setButton(-2, this.mNegativeButtonText, this.mNegativeButtonListener, null);
            }
            if (this.mNeutralButtonText != null) {
                alertController.setButton(-3, this.mNeutralButtonText, this.mNeutralButtonListener, null);
            }
            if (this.mItems != null || this.mCursor != null || this.mAdapter != null) {
                createListView(alertController);
            }
            if (this.mView == null) {
                if (this.mViewLayoutResId != 0) {
                    alertController.setView(this.mViewLayoutResId);
                }
            } else if (this.mViewSpacingSpecified) {
                alertController.setView(this.mView, this.mViewSpacingLeft, this.mViewSpacingTop, this.mViewSpacingRight, this.mViewSpacingBottom);
            } else {
                alertController.setView(this.mView);
            }
        }
    }

    private static final class ButtonHandler extends Handler {
        private static final int MSG_DISMISS_DIALOG = 1;
        private WeakReference<DialogInterface> mDialog;

        public ButtonHandler(DialogInterface dialogInterface) {
            this.mDialog = new WeakReference<>(dialogInterface);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            int i = message.what;
            if (i == 1) {
                ((DialogInterface) message.obj).dismiss();
                return;
            }
            switch (i) {
                case -3:
                case -2:
                case -1:
                    ((DialogInterface.OnClickListener) message.obj).onClick(this.mDialog.get(), message.what);
                    break;
            }
        }
    }

    private static class CheckedItemAdapter extends ArrayAdapter<CharSequence> {
        public CheckedItemAdapter(Context context, int i, int i2, CharSequence[] charSequenceArr) {
            super(context, i, i2, charSequenceArr);
        }

        @Override // android.widget.ArrayAdapter, android.widget.Adapter
        public long getItemId(int i) {
            return i;
        }

        @Override // android.widget.BaseAdapter, android.widget.Adapter
        public boolean hasStableIds() {
            return true;
        }
    }

    public static class RecycleListView extends ListView {
        private final int mPaddingBottomNoButtons;
        private final int mPaddingTopNoTitle;

        public RecycleListView(Context context) {
            this(context, null);
        }

        public RecycleListView(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.RecycleListView);
            this.mPaddingBottomNoButtons = typedArrayObtainStyledAttributes.getDimensionPixelOffset(R.styleable.RecycleListView_paddingBottomNoButtons, -1);
            this.mPaddingTopNoTitle = typedArrayObtainStyledAttributes.getDimensionPixelOffset(R.styleable.RecycleListView_paddingTopNoTitle, -1);
        }

        public void setHasDecor(boolean z, boolean z2) {
            if (z2 && z) {
                return;
            }
            setPadding(getPaddingLeft(), z ? getPaddingTop() : this.mPaddingTopNoTitle, getPaddingRight(), z2 ? getPaddingBottom() : this.mPaddingBottomNoButtons);
        }
    }

    public AlertController(Context context, AppCompatDialog appCompatDialog, Window window) {
        this.mContext = context;
        this.mDialog = appCompatDialog;
        this.mWindow = window;
        this.mHandler = new ButtonHandler(appCompatDialog);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(null, R.styleable.AlertDialog, R.attr.alertDialogStyle, 0);
        this.mAlertDialogLayout = typedArrayObtainStyledAttributes.getResourceId(R.styleable.AlertDialog_android_layout, 0);
        this.mButtonPanelSideLayout = typedArrayObtainStyledAttributes.getResourceId(R.styleable.AlertDialog_buttonPanelSideLayout, 0);
        this.mListLayout = typedArrayObtainStyledAttributes.getResourceId(R.styleable.AlertDialog_listLayout, 0);
        this.mMultiChoiceItemLayout = typedArrayObtainStyledAttributes.getResourceId(R.styleable.AlertDialog_multiChoiceItemLayout, 0);
        this.mSingleChoiceItemLayout = typedArrayObtainStyledAttributes.getResourceId(R.styleable.AlertDialog_singleChoiceItemLayout, 0);
        this.mListItemLayout = typedArrayObtainStyledAttributes.getResourceId(R.styleable.AlertDialog_listItemLayout, 0);
        this.mShowTitle = typedArrayObtainStyledAttributes.getBoolean(R.styleable.AlertDialog_showTitle, true);
        typedArrayObtainStyledAttributes.recycle();
        appCompatDialog.supportRequestWindowFeature(1);
    }

    static boolean canTextInput(View view) {
        if (view.onCheckIsTextEditor()) {
            return true;
        }
        if (!(view instanceof ViewGroup)) {
            return false;
        }
        ViewGroup viewGroup = (ViewGroup) view;
        int childCount = viewGroup.getChildCount();
        while (childCount > 0) {
            childCount--;
            if (canTextInput(viewGroup.getChildAt(childCount))) {
                return true;
            }
        }
        return false;
    }

    private void centerButton(Button button) {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) button.getLayoutParams();
        layoutParams.gravity = 1;
        layoutParams.weight = 0.5f;
        button.setLayoutParams(layoutParams);
    }

    static void manageScrollIndicators(View view, View view2, View view3) {
        if (view2 != null) {
            view2.setVisibility(ViewCompat.canScrollVertically(view, -1) ? 0 : 4);
        }
        if (view3 != null) {
            view3.setVisibility(ViewCompat.canScrollVertically(view, 1) ? 0 : 4);
        }
    }

    @Nullable
    private ViewGroup resolvePanel(@Nullable View view, @Nullable View view2) {
        if (view == null) {
            if (view2 instanceof ViewStub) {
                view2 = ((ViewStub) view2).inflate();
            }
            return (ViewGroup) view2;
        }
        if (view2 != null) {
            ViewParent parent = view2.getParent();
            if (parent instanceof ViewGroup) {
                ((ViewGroup) parent).removeView(view2);
            }
        }
        if (view instanceof ViewStub) {
            view = ((ViewStub) view).inflate();
        }
        return (ViewGroup) view;
    }

    private int selectContentView() {
        if (this.mButtonPanelSideLayout != 0 && this.mButtonPanelLayoutHint == 1) {
            return this.mButtonPanelSideLayout;
        }
        return this.mAlertDialogLayout;
    }

    private void setScrollIndicators(ViewGroup viewGroup, View view, int i, int i2) {
        final View viewFindViewById = this.mWindow.findViewById(R.id.scrollIndicatorUp);
        View viewFindViewById2 = this.mWindow.findViewById(R.id.scrollIndicatorDown);
        if (Build.VERSION.SDK_INT >= 23) {
            ViewCompat.setScrollIndicators(view, i, i2);
            if (viewFindViewById != null) {
                viewGroup.removeView(viewFindViewById);
            }
            if (viewFindViewById2 != null) {
                viewGroup.removeView(viewFindViewById2);
                return;
            }
            return;
        }
        final View view2 = null;
        if (viewFindViewById != null && (i & 1) == 0) {
            viewGroup.removeView(viewFindViewById);
            viewFindViewById = null;
        }
        if (viewFindViewById2 == null || (i & 2) != 0) {
            view2 = viewFindViewById2;
        } else {
            viewGroup.removeView(viewFindViewById2);
        }
        if (viewFindViewById == null && view2 == null) {
            return;
        }
        if (this.mMessage != null) {
            this.mScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() { // from class: android.support.v7.app.AlertController.2
                @Override // android.support.v4.widget.NestedScrollView.OnScrollChangeListener
                public void onScrollChange(NestedScrollView nestedScrollView, int i3, int i4, int i5, int i6) {
                    AlertController.manageScrollIndicators(nestedScrollView, viewFindViewById, view2);
                }
            });
            this.mScrollView.post(new Runnable() { // from class: android.support.v7.app.AlertController.3
                @Override // java.lang.Runnable
                public void run() {
                    AlertController.manageScrollIndicators(AlertController.this.mScrollView, viewFindViewById, view2);
                }
            });
        } else {
            if (this.mListView != null) {
                this.mListView.setOnScrollListener(new AbsListView.OnScrollListener() { // from class: android.support.v7.app.AlertController.4
                    @Override // android.widget.AbsListView.OnScrollListener
                    public void onScroll(AbsListView absListView, int i3, int i4, int i5) {
                        AlertController.manageScrollIndicators(absListView, viewFindViewById, view2);
                    }

                    @Override // android.widget.AbsListView.OnScrollListener
                    public void onScrollStateChanged(AbsListView absListView, int i3) {
                    }
                });
                this.mListView.post(new Runnable() { // from class: android.support.v7.app.AlertController.5
                    @Override // java.lang.Runnable
                    public void run() {
                        AlertController.manageScrollIndicators(AlertController.this.mListView, viewFindViewById, view2);
                    }
                });
                return;
            }
            if (viewFindViewById != null) {
                viewGroup.removeView(viewFindViewById);
            }
            if (view2 != null) {
                viewGroup.removeView(view2);
            }
        }
    }

    private void setupButtons(ViewGroup viewGroup) {
        int i;
        Button button;
        this.mButtonPositive = (Button) viewGroup.findViewById(android.R.id.button1);
        this.mButtonPositive.setOnClickListener(this.mButtonHandler);
        if (TextUtils.isEmpty(this.mButtonPositiveText)) {
            this.mButtonPositive.setVisibility(8);
            i = 0;
        } else {
            this.mButtonPositive.setText(this.mButtonPositiveText);
            this.mButtonPositive.setVisibility(0);
            i = 1;
        }
        this.mButtonNegative = (Button) viewGroup.findViewById(android.R.id.button2);
        this.mButtonNegative.setOnClickListener(this.mButtonHandler);
        if (TextUtils.isEmpty(this.mButtonNegativeText)) {
            this.mButtonNegative.setVisibility(8);
        } else {
            this.mButtonNegative.setText(this.mButtonNegativeText);
            this.mButtonNegative.setVisibility(0);
            i |= 2;
        }
        this.mButtonNeutral = (Button) viewGroup.findViewById(android.R.id.button3);
        this.mButtonNeutral.setOnClickListener(this.mButtonHandler);
        if (TextUtils.isEmpty(this.mButtonNeutralText)) {
            this.mButtonNeutral.setVisibility(8);
        } else {
            this.mButtonNeutral.setText(this.mButtonNeutralText);
            this.mButtonNeutral.setVisibility(0);
            i |= 4;
        }
        if (shouldCenterSingleButton(this.mContext)) {
            if (i == 1) {
                button = this.mButtonPositive;
            } else if (i == 2) {
                button = this.mButtonNegative;
            } else if (i == 4) {
                button = this.mButtonNeutral;
            }
            centerButton(button);
        }
        if (i != 0) {
            return;
        }
        viewGroup.setVisibility(8);
    }

    private void setupContent(ViewGroup viewGroup) {
        this.mScrollView = (NestedScrollView) this.mWindow.findViewById(R.id.scrollView);
        this.mScrollView.setFocusable(false);
        this.mScrollView.setNestedScrollingEnabled(false);
        this.mMessageView = (TextView) viewGroup.findViewById(android.R.id.message);
        if (this.mMessageView == null) {
            return;
        }
        if (this.mMessage != null) {
            this.mMessageView.setText(this.mMessage);
            return;
        }
        this.mMessageView.setVisibility(8);
        this.mScrollView.removeView(this.mMessageView);
        if (this.mListView == null) {
            viewGroup.setVisibility(8);
            return;
        }
        ViewGroup viewGroup2 = (ViewGroup) this.mScrollView.getParent();
        int iIndexOfChild = viewGroup2.indexOfChild(this.mScrollView);
        viewGroup2.removeViewAt(iIndexOfChild);
        viewGroup2.addView(this.mListView, iIndexOfChild, new ViewGroup.LayoutParams(-1, -1));
    }

    private void setupCustomContent(ViewGroup viewGroup) {
        View viewInflate = this.mView != null ? this.mView : this.mViewLayoutResId != 0 ? LayoutInflater.from(this.mContext).inflate(this.mViewLayoutResId, viewGroup, false) : null;
        boolean z = viewInflate != null;
        if (!z || !canTextInput(viewInflate)) {
            this.mWindow.setFlags(131072, 131072);
        }
        if (!z) {
            viewGroup.setVisibility(8);
            return;
        }
        FrameLayout frameLayout = (FrameLayout) this.mWindow.findViewById(R.id.custom);
        frameLayout.addView(viewInflate, new ViewGroup.LayoutParams(-1, -1));
        if (this.mViewSpacingSpecified) {
            frameLayout.setPadding(this.mViewSpacingLeft, this.mViewSpacingTop, this.mViewSpacingRight, this.mViewSpacingBottom);
        }
        if (this.mListView != null) {
            ((LinearLayout.LayoutParams) viewGroup.getLayoutParams()).weight = 0.0f;
        }
    }

    private void setupTitle(ViewGroup viewGroup) {
        if (this.mCustomTitleView != null) {
            viewGroup.addView(this.mCustomTitleView, 0, new ViewGroup.LayoutParams(-1, -2));
            this.mWindow.findViewById(R.id.title_template).setVisibility(8);
            return;
        }
        this.mIconView = (ImageView) this.mWindow.findViewById(android.R.id.icon);
        if (!(!TextUtils.isEmpty(this.mTitle)) || !this.mShowTitle) {
            this.mWindow.findViewById(R.id.title_template).setVisibility(8);
            this.mIconView.setVisibility(8);
            viewGroup.setVisibility(8);
            return;
        }
        this.mTitleView = (TextView) this.mWindow.findViewById(R.id.alertTitle);
        this.mTitleView.setText(this.mTitle);
        if (this.mIconId != 0) {
            this.mIconView.setImageResource(this.mIconId);
        } else if (this.mIcon != null) {
            this.mIconView.setImageDrawable(this.mIcon);
        } else {
            this.mTitleView.setPadding(this.mIconView.getPaddingLeft(), this.mIconView.getPaddingTop(), this.mIconView.getPaddingRight(), this.mIconView.getPaddingBottom());
            this.mIconView.setVisibility(8);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void setupView() {
        View viewFindViewById;
        View viewFindViewById2;
        View viewFindViewById3 = this.mWindow.findViewById(R.id.parentPanel);
        View viewFindViewById4 = viewFindViewById3.findViewById(R.id.topPanel);
        View viewFindViewById5 = viewFindViewById3.findViewById(R.id.contentPanel);
        View viewFindViewById6 = viewFindViewById3.findViewById(R.id.buttonPanel);
        ViewGroup viewGroup = (ViewGroup) viewFindViewById3.findViewById(R.id.customPanel);
        setupCustomContent(viewGroup);
        View viewFindViewById7 = viewGroup.findViewById(R.id.topPanel);
        View viewFindViewById8 = viewGroup.findViewById(R.id.contentPanel);
        View viewFindViewById9 = viewGroup.findViewById(R.id.buttonPanel);
        ViewGroup viewGroupResolvePanel = resolvePanel(viewFindViewById7, viewFindViewById4);
        ViewGroup viewGroupResolvePanel2 = resolvePanel(viewFindViewById8, viewFindViewById5);
        ViewGroup viewGroupResolvePanel3 = resolvePanel(viewFindViewById9, viewFindViewById6);
        setupContent(viewGroupResolvePanel2);
        setupButtons(viewGroupResolvePanel3);
        setupTitle(viewGroupResolvePanel);
        boolean z = (viewGroup == null || viewGroup.getVisibility() == 8) ? false : true;
        boolean z2 = (viewGroupResolvePanel == null || viewGroupResolvePanel.getVisibility() == 8) ? 0 : 1;
        boolean z3 = (viewGroupResolvePanel3 == null || viewGroupResolvePanel3.getVisibility() == 8) ? false : true;
        if (!z3 && viewGroupResolvePanel2 != null && (viewFindViewById2 = viewGroupResolvePanel2.findViewById(R.id.textSpacerNoButtons)) != null) {
            viewFindViewById2.setVisibility(0);
        }
        if (z2 != 0) {
            if (this.mScrollView != null) {
                this.mScrollView.setClipToPadding(true);
            }
            View viewFindViewById10 = null;
            if ((this.mMessage != null || this.mListView != null || z) && !z) {
                viewFindViewById10 = viewGroupResolvePanel.findViewById(R.id.titleDividerNoCustom);
            }
            if (viewFindViewById10 != null) {
                viewFindViewById10.setVisibility(0);
            }
        } else if (viewGroupResolvePanel2 != null && (viewFindViewById = viewGroupResolvePanel2.findViewById(R.id.textSpacerNoTitle)) != null) {
            viewFindViewById.setVisibility(0);
        }
        if (this.mListView instanceof RecycleListView) {
            ((RecycleListView) this.mListView).setHasDecor(z2, z3);
        }
        if (!z) {
            View view = this.mListView != null ? this.mListView : this.mScrollView;
            if (view != null) {
                setScrollIndicators(viewGroupResolvePanel2, view, z2 | (z3 ? 2 : 0), 3);
            }
        }
        ListView listView = this.mListView;
        if (listView == null || this.mAdapter == null) {
            return;
        }
        listView.setAdapter(this.mAdapter);
        int i = this.mCheckedItem;
        if (i > -1) {
            listView.setItemChecked(i, true);
            listView.setSelection(i);
        }
    }

    private static boolean shouldCenterSingleButton(Context context) {
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.alertDialogCenterButtons, typedValue, true);
        return typedValue.data != 0;
    }

    public Button getButton(int i) {
        switch (i) {
            case -3:
                return this.mButtonNeutral;
            case -2:
                return this.mButtonNegative;
            case -1:
                return this.mButtonPositive;
            default:
                return null;
        }
    }

    public int getIconAttributeResId(int i) {
        TypedValue typedValue = new TypedValue();
        this.mContext.getTheme().resolveAttribute(i, typedValue, true);
        return typedValue.resourceId;
    }

    public ListView getListView() {
        return this.mListView;
    }

    public void installContent() {
        this.mDialog.setContentView(selectContentView());
        setupView();
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        return this.mScrollView != null && this.mScrollView.executeKeyEvent(keyEvent);
    }

    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        return this.mScrollView != null && this.mScrollView.executeKeyEvent(keyEvent);
    }

    public void setButton(int i, CharSequence charSequence, DialogInterface.OnClickListener onClickListener, Message message) {
        if (message == null && onClickListener != null) {
            message = this.mHandler.obtainMessage(i, onClickListener);
        }
        switch (i) {
            case -3:
                this.mButtonNeutralText = charSequence;
                this.mButtonNeutralMessage = message;
                return;
            case -2:
                this.mButtonNegativeText = charSequence;
                this.mButtonNegativeMessage = message;
                return;
            case -1:
                this.mButtonPositiveText = charSequence;
                this.mButtonPositiveMessage = message;
                return;
            default:
                throw new IllegalArgumentException("Button does not exist");
        }
    }

    public void setButtonPanelLayoutHint(int i) {
        this.mButtonPanelLayoutHint = i;
    }

    public void setCustomTitle(View view) {
        this.mCustomTitleView = view;
    }

    public void setIcon(int i) {
        this.mIcon = null;
        this.mIconId = i;
        if (this.mIconView != null) {
            if (i == 0) {
                this.mIconView.setVisibility(8);
            } else {
                this.mIconView.setVisibility(0);
                this.mIconView.setImageResource(this.mIconId);
            }
        }
    }

    public void setIcon(Drawable drawable) {
        this.mIcon = drawable;
        this.mIconId = 0;
        if (this.mIconView != null) {
            if (drawable == null) {
                this.mIconView.setVisibility(8);
            } else {
                this.mIconView.setVisibility(0);
                this.mIconView.setImageDrawable(drawable);
            }
        }
    }

    public void setMessage(CharSequence charSequence) {
        this.mMessage = charSequence;
        if (this.mMessageView != null) {
            this.mMessageView.setText(charSequence);
        }
    }

    public void setTitle(CharSequence charSequence) {
        this.mTitle = charSequence;
        if (this.mTitleView != null) {
            this.mTitleView.setText(charSequence);
        }
    }

    public void setView(int i) {
        this.mView = null;
        this.mViewLayoutResId = i;
        this.mViewSpacingSpecified = false;
    }

    public void setView(View view) {
        this.mView = view;
        this.mViewLayoutResId = 0;
        this.mViewSpacingSpecified = false;
    }

    public void setView(View view, int i, int i2, int i3, int i4) {
        this.mView = view;
        this.mViewLayoutResId = 0;
        this.mViewSpacingSpecified = true;
        this.mViewSpacingLeft = i;
        this.mViewSpacingTop = i2;
        this.mViewSpacingRight = i3;
        this.mViewSpacingBottom = i4;
    }
}
