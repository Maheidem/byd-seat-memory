package android.support.constraint;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.Constraints;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseIntArray;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public class ConstraintSet {
    private static final int ALPHA = 43;
    private static final int BARRIER_TYPE = 1;
    public static final int BASELINE = 5;
    private static final int BASELINE_TO_BASELINE = 1;
    public static final int BOTTOM = 4;
    private static final int BOTTOM_MARGIN = 2;
    private static final int BOTTOM_TO_BOTTOM = 3;
    private static final int BOTTOM_TO_TOP = 4;
    public static final int CHAIN_PACKED = 2;
    public static final int CHAIN_SPREAD = 0;
    public static final int CHAIN_SPREAD_INSIDE = 1;
    private static final int CIRCLE = 61;
    private static final int CIRCLE_ANGLE = 63;
    private static final int CIRCLE_RADIUS = 62;
    private static final boolean DEBUG = false;
    private static final int DIMENSION_RATIO = 5;
    private static final int EDITOR_ABSOLUTE_X = 6;
    private static final int EDITOR_ABSOLUTE_Y = 7;
    private static final int ELEVATION = 44;
    public static final int END = 7;
    private static final int END_MARGIN = 8;
    private static final int END_TO_END = 9;
    private static final int END_TO_START = 10;
    public static final int GONE = 8;
    private static final int GONE_BOTTOM_MARGIN = 11;
    private static final int GONE_END_MARGIN = 12;
    private static final int GONE_LEFT_MARGIN = 13;
    private static final int GONE_RIGHT_MARGIN = 14;
    private static final int GONE_START_MARGIN = 15;
    private static final int GONE_TOP_MARGIN = 16;
    private static final int GUIDE_BEGIN = 17;
    private static final int GUIDE_END = 18;
    private static final int GUIDE_PERCENT = 19;
    private static final int HEIGHT_DEFAULT = 55;
    private static final int HEIGHT_MAX = 57;
    private static final int HEIGHT_MIN = 59;
    public static final int HORIZONTAL = 0;
    private static final int HORIZONTAL_BIAS = 20;
    public static final int HORIZONTAL_GUIDELINE = 0;
    private static final int HORIZONTAL_STYLE = 41;
    private static final int HORIZONTAL_WEIGHT = 39;
    public static final int INVISIBLE = 4;
    private static final int LAYOUT_HEIGHT = 21;
    private static final int LAYOUT_VISIBILITY = 22;
    private static final int LAYOUT_WIDTH = 23;
    public static final int LEFT = 1;
    private static final int LEFT_MARGIN = 24;
    private static final int LEFT_TO_LEFT = 25;
    private static final int LEFT_TO_RIGHT = 26;
    public static final int MATCH_CONSTRAINT = 0;
    public static final int MATCH_CONSTRAINT_SPREAD = 0;
    public static final int MATCH_CONSTRAINT_WRAP = 1;
    private static final int ORIENTATION = 27;
    public static final int PARENT_ID = 0;
    public static final int RIGHT = 2;
    private static final int RIGHT_MARGIN = 28;
    private static final int RIGHT_TO_LEFT = 29;
    private static final int RIGHT_TO_RIGHT = 30;
    private static final int ROTATION = 60;
    private static final int ROTATION_X = 45;
    private static final int ROTATION_Y = 46;
    private static final int SCALE_X = 47;
    private static final int SCALE_Y = 48;
    public static final int START = 6;
    private static final int START_MARGIN = 31;
    private static final int START_TO_END = 32;
    private static final int START_TO_START = 33;
    private static final String TAG = "ConstraintSet";
    public static final int TOP = 3;
    private static final int TOP_MARGIN = 34;
    private static final int TOP_TO_BOTTOM = 35;
    private static final int TOP_TO_TOP = 36;
    private static final int TRANSFORM_PIVOT_X = 49;
    private static final int TRANSFORM_PIVOT_Y = 50;
    private static final int TRANSLATION_X = 51;
    private static final int TRANSLATION_Y = 52;
    private static final int TRANSLATION_Z = 53;
    public static final int UNSET = -1;
    private static final int UNUSED = 64;
    public static final int VERTICAL = 1;
    private static final int VERTICAL_BIAS = 37;
    public static final int VERTICAL_GUIDELINE = 1;
    private static final int VERTICAL_STYLE = 42;
    private static final int VERTICAL_WEIGHT = 40;
    private static final int VIEW_ID = 38;
    public static final int VISIBLE = 0;
    private static final int WIDTH_DEFAULT = 54;
    private static final int WIDTH_MAX = 56;
    private static final int WIDTH_MIN = 58;
    public static final int WRAP_CONTENT = -2;
    private HashMap<Integer, Constraint> mConstraints = new HashMap<>();
    private static final int[] VISIBILITY_FLAGS = {0, 4, 8};
    private static SparseIntArray mapToConstant = new SparseIntArray();

    private static class Constraint {
        static final int UNSET = -1;
        public float alpha;
        public boolean applyElevation;
        public int baselineToBaseline;
        public int bottomMargin;
        public int bottomToBottom;
        public int bottomToTop;
        public float circleAngle;
        public int circleConstraint;
        public int circleRadius;
        public boolean constrainedHeight;
        public boolean constrainedWidth;
        public String dimensionRatio;
        public int editorAbsoluteX;
        public int editorAbsoluteY;
        public float elevation;
        public int endMargin;
        public int endToEnd;
        public int endToStart;
        public int goneBottomMargin;
        public int goneEndMargin;
        public int goneLeftMargin;
        public int goneRightMargin;
        public int goneStartMargin;
        public int goneTopMargin;
        public int guideBegin;
        public int guideEnd;
        public float guidePercent;
        public int heightDefault;
        public int heightMax;
        public int heightMin;
        public float heightPercent;
        public float horizontalBias;
        public int horizontalChainStyle;
        public float horizontalWeight;
        public int leftMargin;
        public int leftToLeft;
        public int leftToRight;
        public int mBarrierDirection;
        public int mHeight;
        public int mHelperType;
        boolean mIsGuideline;
        public int[] mReferenceIds;
        int mViewId;
        public int mWidth;
        public int orientation;
        public int rightMargin;
        public int rightToLeft;
        public int rightToRight;
        public float rotation;
        public float rotationX;
        public float rotationY;
        public float scaleX;
        public float scaleY;
        public int startMargin;
        public int startToEnd;
        public int startToStart;
        public int topMargin;
        public int topToBottom;
        public int topToTop;
        public float transformPivotX;
        public float transformPivotY;
        public float translationX;
        public float translationY;
        public float translationZ;
        public float verticalBias;
        public int verticalChainStyle;
        public float verticalWeight;
        public int visibility;
        public int widthDefault;
        public int widthMax;
        public int widthMin;
        public float widthPercent;

        private Constraint() {
            this.mIsGuideline = false;
            this.guideBegin = -1;
            this.guideEnd = -1;
            this.guidePercent = -1.0f;
            this.leftToLeft = -1;
            this.leftToRight = -1;
            this.rightToLeft = -1;
            this.rightToRight = -1;
            this.topToTop = -1;
            this.topToBottom = -1;
            this.bottomToTop = -1;
            this.bottomToBottom = -1;
            this.baselineToBaseline = -1;
            this.startToEnd = -1;
            this.startToStart = -1;
            this.endToStart = -1;
            this.endToEnd = -1;
            this.horizontalBias = 0.5f;
            this.verticalBias = 0.5f;
            this.dimensionRatio = null;
            this.circleConstraint = -1;
            this.circleRadius = 0;
            this.circleAngle = 0.0f;
            this.editorAbsoluteX = -1;
            this.editorAbsoluteY = -1;
            this.orientation = -1;
            this.leftMargin = -1;
            this.rightMargin = -1;
            this.topMargin = -1;
            this.bottomMargin = -1;
            this.endMargin = -1;
            this.startMargin = -1;
            this.visibility = 0;
            this.goneLeftMargin = -1;
            this.goneTopMargin = -1;
            this.goneRightMargin = -1;
            this.goneBottomMargin = -1;
            this.goneEndMargin = -1;
            this.goneStartMargin = -1;
            this.verticalWeight = 0.0f;
            this.horizontalWeight = 0.0f;
            this.horizontalChainStyle = 0;
            this.verticalChainStyle = 0;
            this.alpha = 1.0f;
            this.applyElevation = false;
            this.elevation = 0.0f;
            this.rotation = 0.0f;
            this.rotationX = 0.0f;
            this.rotationY = 0.0f;
            this.scaleX = 1.0f;
            this.scaleY = 1.0f;
            this.transformPivotX = Float.NaN;
            this.transformPivotY = Float.NaN;
            this.translationX = 0.0f;
            this.translationY = 0.0f;
            this.translationZ = 0.0f;
            this.constrainedWidth = false;
            this.constrainedHeight = false;
            this.widthDefault = -1;
            this.heightDefault = -1;
            this.widthMax = -1;
            this.heightMax = -1;
            this.widthMin = -1;
            this.heightMin = -1;
            this.widthPercent = 1.0f;
            this.heightPercent = 1.0f;
            this.mBarrierDirection = -1;
            this.mHelperType = -1;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void fillFrom(int i, ConstraintLayout.LayoutParams layoutParams) {
            this.mViewId = i;
            this.leftToLeft = layoutParams.leftToLeft;
            this.leftToRight = layoutParams.leftToRight;
            this.rightToLeft = layoutParams.rightToLeft;
            this.rightToRight = layoutParams.rightToRight;
            this.topToTop = layoutParams.topToTop;
            this.topToBottom = layoutParams.topToBottom;
            this.bottomToTop = layoutParams.bottomToTop;
            this.bottomToBottom = layoutParams.bottomToBottom;
            this.baselineToBaseline = layoutParams.baselineToBaseline;
            this.startToEnd = layoutParams.startToEnd;
            this.startToStart = layoutParams.startToStart;
            this.endToStart = layoutParams.endToStart;
            this.endToEnd = layoutParams.endToEnd;
            this.horizontalBias = layoutParams.horizontalBias;
            this.verticalBias = layoutParams.verticalBias;
            this.dimensionRatio = layoutParams.dimensionRatio;
            this.circleConstraint = layoutParams.circleConstraint;
            this.circleRadius = layoutParams.circleRadius;
            this.circleAngle = layoutParams.circleAngle;
            this.editorAbsoluteX = layoutParams.editorAbsoluteX;
            this.editorAbsoluteY = layoutParams.editorAbsoluteY;
            this.orientation = layoutParams.orientation;
            this.guidePercent = layoutParams.guidePercent;
            this.guideBegin = layoutParams.guideBegin;
            this.guideEnd = layoutParams.guideEnd;
            this.mWidth = layoutParams.width;
            this.mHeight = layoutParams.height;
            this.leftMargin = layoutParams.leftMargin;
            this.rightMargin = layoutParams.rightMargin;
            this.topMargin = layoutParams.topMargin;
            this.bottomMargin = layoutParams.bottomMargin;
            this.verticalWeight = layoutParams.verticalWeight;
            this.horizontalWeight = layoutParams.horizontalWeight;
            this.verticalChainStyle = layoutParams.verticalChainStyle;
            this.horizontalChainStyle = layoutParams.horizontalChainStyle;
            this.constrainedWidth = layoutParams.constrainedWidth;
            this.constrainedHeight = layoutParams.constrainedHeight;
            this.widthDefault = layoutParams.matchConstraintDefaultWidth;
            this.heightDefault = layoutParams.matchConstraintDefaultHeight;
            this.constrainedWidth = layoutParams.constrainedWidth;
            this.widthMax = layoutParams.matchConstraintMaxWidth;
            this.heightMax = layoutParams.matchConstraintMaxHeight;
            this.widthMin = layoutParams.matchConstraintMinWidth;
            this.heightMin = layoutParams.matchConstraintMinHeight;
            this.widthPercent = layoutParams.matchConstraintPercentWidth;
            this.heightPercent = layoutParams.matchConstraintPercentHeight;
            if (Build.VERSION.SDK_INT >= 17) {
                this.endMargin = layoutParams.getMarginEnd();
                this.startMargin = layoutParams.getMarginStart();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void fillFromConstraints(int i, Constraints.LayoutParams layoutParams) {
            fillFrom(i, layoutParams);
            this.alpha = layoutParams.alpha;
            this.rotation = layoutParams.rotation;
            this.rotationX = layoutParams.rotationX;
            this.rotationY = layoutParams.rotationY;
            this.scaleX = layoutParams.scaleX;
            this.scaleY = layoutParams.scaleY;
            this.transformPivotX = layoutParams.transformPivotX;
            this.transformPivotY = layoutParams.transformPivotY;
            this.translationX = layoutParams.translationX;
            this.translationY = layoutParams.translationY;
            this.translationZ = layoutParams.translationZ;
            this.elevation = layoutParams.elevation;
            this.applyElevation = layoutParams.applyElevation;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void fillFromConstraints(ConstraintHelper constraintHelper, int i, Constraints.LayoutParams layoutParams) {
            fillFromConstraints(i, layoutParams);
            if (constraintHelper instanceof Barrier) {
                this.mHelperType = 1;
                Barrier barrier = (Barrier) constraintHelper;
                this.mBarrierDirection = barrier.getType();
                this.mReferenceIds = barrier.getReferencedIds();
            }
        }

        public void applyTo(ConstraintLayout.LayoutParams layoutParams) {
            layoutParams.leftToLeft = this.leftToLeft;
            layoutParams.leftToRight = this.leftToRight;
            layoutParams.rightToLeft = this.rightToLeft;
            layoutParams.rightToRight = this.rightToRight;
            layoutParams.topToTop = this.topToTop;
            layoutParams.topToBottom = this.topToBottom;
            layoutParams.bottomToTop = this.bottomToTop;
            layoutParams.bottomToBottom = this.bottomToBottom;
            layoutParams.baselineToBaseline = this.baselineToBaseline;
            layoutParams.startToEnd = this.startToEnd;
            layoutParams.startToStart = this.startToStart;
            layoutParams.endToStart = this.endToStart;
            layoutParams.endToEnd = this.endToEnd;
            layoutParams.leftMargin = this.leftMargin;
            layoutParams.rightMargin = this.rightMargin;
            layoutParams.topMargin = this.topMargin;
            layoutParams.bottomMargin = this.bottomMargin;
            layoutParams.goneStartMargin = this.goneStartMargin;
            layoutParams.goneEndMargin = this.goneEndMargin;
            layoutParams.horizontalBias = this.horizontalBias;
            layoutParams.verticalBias = this.verticalBias;
            layoutParams.circleConstraint = this.circleConstraint;
            layoutParams.circleRadius = this.circleRadius;
            layoutParams.circleAngle = this.circleAngle;
            layoutParams.dimensionRatio = this.dimensionRatio;
            layoutParams.editorAbsoluteX = this.editorAbsoluteX;
            layoutParams.editorAbsoluteY = this.editorAbsoluteY;
            layoutParams.verticalWeight = this.verticalWeight;
            layoutParams.horizontalWeight = this.horizontalWeight;
            layoutParams.verticalChainStyle = this.verticalChainStyle;
            layoutParams.horizontalChainStyle = this.horizontalChainStyle;
            layoutParams.constrainedWidth = this.constrainedWidth;
            layoutParams.constrainedHeight = this.constrainedHeight;
            layoutParams.matchConstraintDefaultWidth = this.widthDefault;
            layoutParams.matchConstraintDefaultHeight = this.heightDefault;
            layoutParams.matchConstraintMaxWidth = this.widthMax;
            layoutParams.matchConstraintMaxHeight = this.heightMax;
            layoutParams.matchConstraintMinWidth = this.widthMin;
            layoutParams.matchConstraintMinHeight = this.heightMin;
            layoutParams.matchConstraintPercentWidth = this.widthPercent;
            layoutParams.matchConstraintPercentHeight = this.heightPercent;
            layoutParams.orientation = this.orientation;
            layoutParams.guidePercent = this.guidePercent;
            layoutParams.guideBegin = this.guideBegin;
            layoutParams.guideEnd = this.guideEnd;
            layoutParams.width = this.mWidth;
            layoutParams.height = this.mHeight;
            if (Build.VERSION.SDK_INT >= 17) {
                layoutParams.setMarginStart(this.startMargin);
                layoutParams.setMarginEnd(this.endMargin);
            }
            layoutParams.validate();
        }

        /* renamed from: clone, reason: merged with bridge method [inline-methods] */
        public Constraint m0clone() {
            Constraint constraint = new Constraint();
            constraint.mIsGuideline = this.mIsGuideline;
            constraint.mWidth = this.mWidth;
            constraint.mHeight = this.mHeight;
            constraint.guideBegin = this.guideBegin;
            constraint.guideEnd = this.guideEnd;
            constraint.guidePercent = this.guidePercent;
            constraint.leftToLeft = this.leftToLeft;
            constraint.leftToRight = this.leftToRight;
            constraint.rightToLeft = this.rightToLeft;
            constraint.rightToRight = this.rightToRight;
            constraint.topToTop = this.topToTop;
            constraint.topToBottom = this.topToBottom;
            constraint.bottomToTop = this.bottomToTop;
            constraint.bottomToBottom = this.bottomToBottom;
            constraint.baselineToBaseline = this.baselineToBaseline;
            constraint.startToEnd = this.startToEnd;
            constraint.startToStart = this.startToStart;
            constraint.endToStart = this.endToStart;
            constraint.endToEnd = this.endToEnd;
            constraint.horizontalBias = this.horizontalBias;
            constraint.verticalBias = this.verticalBias;
            constraint.dimensionRatio = this.dimensionRatio;
            constraint.editorAbsoluteX = this.editorAbsoluteX;
            constraint.editorAbsoluteY = this.editorAbsoluteY;
            constraint.horizontalBias = this.horizontalBias;
            constraint.horizontalBias = this.horizontalBias;
            constraint.horizontalBias = this.horizontalBias;
            constraint.horizontalBias = this.horizontalBias;
            constraint.horizontalBias = this.horizontalBias;
            constraint.orientation = this.orientation;
            constraint.leftMargin = this.leftMargin;
            constraint.rightMargin = this.rightMargin;
            constraint.topMargin = this.topMargin;
            constraint.bottomMargin = this.bottomMargin;
            constraint.endMargin = this.endMargin;
            constraint.startMargin = this.startMargin;
            constraint.visibility = this.visibility;
            constraint.goneLeftMargin = this.goneLeftMargin;
            constraint.goneTopMargin = this.goneTopMargin;
            constraint.goneRightMargin = this.goneRightMargin;
            constraint.goneBottomMargin = this.goneBottomMargin;
            constraint.goneEndMargin = this.goneEndMargin;
            constraint.goneStartMargin = this.goneStartMargin;
            constraint.verticalWeight = this.verticalWeight;
            constraint.horizontalWeight = this.horizontalWeight;
            constraint.horizontalChainStyle = this.horizontalChainStyle;
            constraint.verticalChainStyle = this.verticalChainStyle;
            constraint.alpha = this.alpha;
            constraint.applyElevation = this.applyElevation;
            constraint.elevation = this.elevation;
            constraint.rotation = this.rotation;
            constraint.rotationX = this.rotationX;
            constraint.rotationY = this.rotationY;
            constraint.scaleX = this.scaleX;
            constraint.scaleY = this.scaleY;
            constraint.transformPivotX = this.transformPivotX;
            constraint.transformPivotY = this.transformPivotY;
            constraint.translationX = this.translationX;
            constraint.translationY = this.translationY;
            constraint.translationZ = this.translationZ;
            constraint.constrainedWidth = this.constrainedWidth;
            constraint.constrainedHeight = this.constrainedHeight;
            constraint.widthDefault = this.widthDefault;
            constraint.heightDefault = this.heightDefault;
            constraint.widthMax = this.widthMax;
            constraint.heightMax = this.heightMax;
            constraint.widthMin = this.widthMin;
            constraint.heightMin = this.heightMin;
            constraint.widthPercent = this.widthPercent;
            constraint.heightPercent = this.heightPercent;
            constraint.mBarrierDirection = this.mBarrierDirection;
            constraint.mHelperType = this.mHelperType;
            if (this.mReferenceIds != null) {
                constraint.mReferenceIds = Arrays.copyOf(this.mReferenceIds, this.mReferenceIds.length);
            }
            constraint.circleConstraint = this.circleConstraint;
            constraint.circleRadius = this.circleRadius;
            constraint.circleAngle = this.circleAngle;
            return constraint;
        }
    }

    static {
        mapToConstant.append(R.styleable.ConstraintSet_layout_constraintLeft_toLeftOf, 25);
        mapToConstant.append(R.styleable.ConstraintSet_layout_constraintLeft_toRightOf, 26);
        mapToConstant.append(R.styleable.ConstraintSet_layout_constraintRight_toLeftOf, 29);
        mapToConstant.append(R.styleable.ConstraintSet_layout_constraintRight_toRightOf, 30);
        mapToConstant.append(R.styleable.ConstraintSet_layout_constraintTop_toTopOf, 36);
        mapToConstant.append(R.styleable.ConstraintSet_layout_constraintTop_toBottomOf, 35);
        mapToConstant.append(R.styleable.ConstraintSet_layout_constraintBottom_toTopOf, 4);
        mapToConstant.append(R.styleable.ConstraintSet_layout_constraintBottom_toBottomOf, 3);
        mapToConstant.append(R.styleable.ConstraintSet_layout_constraintBaseline_toBaselineOf, 1);
        mapToConstant.append(R.styleable.ConstraintSet_layout_editor_absoluteX, 6);
        mapToConstant.append(R.styleable.ConstraintSet_layout_editor_absoluteY, 7);
        mapToConstant.append(R.styleable.ConstraintSet_layout_constraintGuide_begin, 17);
        mapToConstant.append(R.styleable.ConstraintSet_layout_constraintGuide_end, 18);
        mapToConstant.append(R.styleable.ConstraintSet_layout_constraintGuide_percent, 19);
        mapToConstant.append(R.styleable.ConstraintSet_android_orientation, 27);
        mapToConstant.append(R.styleable.ConstraintSet_layout_constraintStart_toEndOf, 32);
        mapToConstant.append(R.styleable.ConstraintSet_layout_constraintStart_toStartOf, 33);
        mapToConstant.append(R.styleable.ConstraintSet_layout_constraintEnd_toStartOf, 10);
        mapToConstant.append(R.styleable.ConstraintSet_layout_constraintEnd_toEndOf, 9);
        mapToConstant.append(R.styleable.ConstraintSet_layout_goneMarginLeft, 13);
        mapToConstant.append(R.styleable.ConstraintSet_layout_goneMarginTop, 16);
        mapToConstant.append(R.styleable.ConstraintSet_layout_goneMarginRight, 14);
        mapToConstant.append(R.styleable.ConstraintSet_layout_goneMarginBottom, 11);
        mapToConstant.append(R.styleable.ConstraintSet_layout_goneMarginStart, 15);
        mapToConstant.append(R.styleable.ConstraintSet_layout_goneMarginEnd, 12);
        mapToConstant.append(R.styleable.ConstraintSet_layout_constraintVertical_weight, 40);
        mapToConstant.append(R.styleable.ConstraintSet_layout_constraintHorizontal_weight, 39);
        mapToConstant.append(R.styleable.ConstraintSet_layout_constraintHorizontal_chainStyle, 41);
        mapToConstant.append(R.styleable.ConstraintSet_layout_constraintVertical_chainStyle, 42);
        mapToConstant.append(R.styleable.ConstraintSet_layout_constraintHorizontal_bias, 20);
        mapToConstant.append(R.styleable.ConstraintSet_layout_constraintVertical_bias, 37);
        mapToConstant.append(R.styleable.ConstraintSet_layout_constraintDimensionRatio, 5);
        mapToConstant.append(R.styleable.ConstraintSet_layout_constraintLeft_creator, 64);
        mapToConstant.append(R.styleable.ConstraintSet_layout_constraintTop_creator, 64);
        mapToConstant.append(R.styleable.ConstraintSet_layout_constraintRight_creator, 64);
        mapToConstant.append(R.styleable.ConstraintSet_layout_constraintBottom_creator, 64);
        mapToConstant.append(R.styleable.ConstraintSet_layout_constraintBaseline_creator, 64);
        mapToConstant.append(R.styleable.ConstraintSet_android_layout_marginLeft, 24);
        mapToConstant.append(R.styleable.ConstraintSet_android_layout_marginRight, 28);
        mapToConstant.append(R.styleable.ConstraintSet_android_layout_marginStart, 31);
        mapToConstant.append(R.styleable.ConstraintSet_android_layout_marginEnd, 8);
        mapToConstant.append(R.styleable.ConstraintSet_android_layout_marginTop, 34);
        mapToConstant.append(R.styleable.ConstraintSet_android_layout_marginBottom, 2);
        mapToConstant.append(R.styleable.ConstraintSet_android_layout_width, 23);
        mapToConstant.append(R.styleable.ConstraintSet_android_layout_height, 21);
        mapToConstant.append(R.styleable.ConstraintSet_android_visibility, 22);
        mapToConstant.append(R.styleable.ConstraintSet_android_alpha, 43);
        mapToConstant.append(R.styleable.ConstraintSet_android_elevation, 44);
        mapToConstant.append(R.styleable.ConstraintSet_android_rotationX, 45);
        mapToConstant.append(R.styleable.ConstraintSet_android_rotationY, 46);
        mapToConstant.append(R.styleable.ConstraintSet_android_rotation, 60);
        mapToConstant.append(R.styleable.ConstraintSet_android_scaleX, 47);
        mapToConstant.append(R.styleable.ConstraintSet_android_scaleY, 48);
        mapToConstant.append(R.styleable.ConstraintSet_android_transformPivotX, 49);
        mapToConstant.append(R.styleable.ConstraintSet_android_transformPivotY, 50);
        mapToConstant.append(R.styleable.ConstraintSet_android_translationX, 51);
        mapToConstant.append(R.styleable.ConstraintSet_android_translationY, 52);
        mapToConstant.append(R.styleable.ConstraintSet_android_translationZ, 53);
        mapToConstant.append(R.styleable.ConstraintSet_layout_constraintWidth_default, 54);
        mapToConstant.append(R.styleable.ConstraintSet_layout_constraintHeight_default, 55);
        mapToConstant.append(R.styleable.ConstraintSet_layout_constraintWidth_max, 56);
        mapToConstant.append(R.styleable.ConstraintSet_layout_constraintHeight_max, 57);
        mapToConstant.append(R.styleable.ConstraintSet_layout_constraintWidth_min, 58);
        mapToConstant.append(R.styleable.ConstraintSet_layout_constraintHeight_min, 59);
        mapToConstant.append(R.styleable.ConstraintSet_layout_constraintCircle, 61);
        mapToConstant.append(R.styleable.ConstraintSet_layout_constraintCircleRadius, 62);
        mapToConstant.append(R.styleable.ConstraintSet_layout_constraintCircleAngle, 63);
        mapToConstant.append(R.styleable.ConstraintSet_android_id, 38);
    }

    private void createHorizontalChain(int i, int i2, int i3, int i4, int[] iArr, float[] fArr, int i5, int i6, int i7) {
        if (iArr.length < 2) {
            throw new IllegalArgumentException("must have 2 or more widgets in a chain");
        }
        if (fArr != null && fArr.length != iArr.length) {
            throw new IllegalArgumentException("must have 2 or more widgets in a chain");
        }
        if (fArr != null) {
            get(iArr[0]).horizontalWeight = fArr[0];
        }
        get(iArr[0]).horizontalChainStyle = i5;
        connect(iArr[0], i6, i, i2, -1);
        for (int i8 = 1; i8 < iArr.length; i8++) {
            int i9 = iArr[i8];
            int i10 = i8 - 1;
            connect(iArr[i8], i6, iArr[i10], i7, -1);
            connect(iArr[i10], i7, iArr[i8], i6, -1);
            if (fArr != null) {
                get(iArr[i8]).horizontalWeight = fArr[i8];
            }
        }
        connect(iArr[iArr.length - 1], i7, i3, i4, -1);
    }

    private Constraint fillFromAttributeList(Context context, AttributeSet attributeSet) {
        Constraint constraint = new Constraint();
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.ConstraintSet);
        populateConstraint(constraint, typedArrayObtainStyledAttributes);
        typedArrayObtainStyledAttributes.recycle();
        return constraint;
    }

    private Constraint get(int i) {
        if (!this.mConstraints.containsKey(Integer.valueOf(i))) {
            this.mConstraints.put(Integer.valueOf(i), new Constraint());
        }
        return this.mConstraints.get(Integer.valueOf(i));
    }

    private static int lookupID(TypedArray typedArray, int i, int i2) {
        int resourceId = typedArray.getResourceId(i, i2);
        return resourceId == -1 ? typedArray.getInt(i, -1) : resourceId;
    }

    private void populateConstraint(Constraint constraint, TypedArray typedArray) {
        String str;
        StringBuilder sb;
        String str2;
        int indexCount = typedArray.getIndexCount();
        for (int i = 0; i < indexCount; i++) {
            int index = typedArray.getIndex(i);
            int i2 = mapToConstant.get(index);
            switch (i2) {
                case 1:
                    constraint.baselineToBaseline = lookupID(typedArray, index, constraint.baselineToBaseline);
                    break;
                case 2:
                    constraint.bottomMargin = typedArray.getDimensionPixelSize(index, constraint.bottomMargin);
                    break;
                case 3:
                    constraint.bottomToBottom = lookupID(typedArray, index, constraint.bottomToBottom);
                    break;
                case 4:
                    constraint.bottomToTop = lookupID(typedArray, index, constraint.bottomToTop);
                    break;
                case 5:
                    constraint.dimensionRatio = typedArray.getString(index);
                    break;
                case 6:
                    constraint.editorAbsoluteX = typedArray.getDimensionPixelOffset(index, constraint.editorAbsoluteX);
                    break;
                case 7:
                    constraint.editorAbsoluteY = typedArray.getDimensionPixelOffset(index, constraint.editorAbsoluteY);
                    break;
                case 8:
                    constraint.endMargin = typedArray.getDimensionPixelSize(index, constraint.endMargin);
                    break;
                case 9:
                    constraint.endToEnd = lookupID(typedArray, index, constraint.endToEnd);
                    break;
                case 10:
                    constraint.endToStart = lookupID(typedArray, index, constraint.endToStart);
                    break;
                case 11:
                    constraint.goneBottomMargin = typedArray.getDimensionPixelSize(index, constraint.goneBottomMargin);
                    break;
                case 12:
                    constraint.goneEndMargin = typedArray.getDimensionPixelSize(index, constraint.goneEndMargin);
                    break;
                case 13:
                    constraint.goneLeftMargin = typedArray.getDimensionPixelSize(index, constraint.goneLeftMargin);
                    break;
                case 14:
                    constraint.goneRightMargin = typedArray.getDimensionPixelSize(index, constraint.goneRightMargin);
                    break;
                case 15:
                    constraint.goneStartMargin = typedArray.getDimensionPixelSize(index, constraint.goneStartMargin);
                    break;
                case 16:
                    constraint.goneTopMargin = typedArray.getDimensionPixelSize(index, constraint.goneTopMargin);
                    break;
                case 17:
                    constraint.guideBegin = typedArray.getDimensionPixelOffset(index, constraint.guideBegin);
                    break;
                case 18:
                    constraint.guideEnd = typedArray.getDimensionPixelOffset(index, constraint.guideEnd);
                    break;
                case 19:
                    constraint.guidePercent = typedArray.getFloat(index, constraint.guidePercent);
                    break;
                case 20:
                    constraint.horizontalBias = typedArray.getFloat(index, constraint.horizontalBias);
                    break;
                case 21:
                    constraint.mHeight = typedArray.getLayoutDimension(index, constraint.mHeight);
                    break;
                case 22:
                    constraint.visibility = typedArray.getInt(index, constraint.visibility);
                    constraint.visibility = VISIBILITY_FLAGS[constraint.visibility];
                    break;
                case 23:
                    constraint.mWidth = typedArray.getLayoutDimension(index, constraint.mWidth);
                    break;
                case 24:
                    constraint.leftMargin = typedArray.getDimensionPixelSize(index, constraint.leftMargin);
                    break;
                case 25:
                    constraint.leftToLeft = lookupID(typedArray, index, constraint.leftToLeft);
                    break;
                case 26:
                    constraint.leftToRight = lookupID(typedArray, index, constraint.leftToRight);
                    break;
                case 27:
                    constraint.orientation = typedArray.getInt(index, constraint.orientation);
                    break;
                case 28:
                    constraint.rightMargin = typedArray.getDimensionPixelSize(index, constraint.rightMargin);
                    break;
                case 29:
                    constraint.rightToLeft = lookupID(typedArray, index, constraint.rightToLeft);
                    break;
                case 30:
                    constraint.rightToRight = lookupID(typedArray, index, constraint.rightToRight);
                    break;
                case 31:
                    constraint.startMargin = typedArray.getDimensionPixelSize(index, constraint.startMargin);
                    break;
                case 32:
                    constraint.startToEnd = lookupID(typedArray, index, constraint.startToEnd);
                    break;
                case 33:
                    constraint.startToStart = lookupID(typedArray, index, constraint.startToStart);
                    break;
                case 34:
                    constraint.topMargin = typedArray.getDimensionPixelSize(index, constraint.topMargin);
                    break;
                case 35:
                    constraint.topToBottom = lookupID(typedArray, index, constraint.topToBottom);
                    break;
                case 36:
                    constraint.topToTop = lookupID(typedArray, index, constraint.topToTop);
                    break;
                case 37:
                    constraint.verticalBias = typedArray.getFloat(index, constraint.verticalBias);
                    break;
                case 38:
                    constraint.mViewId = typedArray.getResourceId(index, constraint.mViewId);
                    break;
                case 39:
                    constraint.horizontalWeight = typedArray.getFloat(index, constraint.horizontalWeight);
                    break;
                case 40:
                    constraint.verticalWeight = typedArray.getFloat(index, constraint.verticalWeight);
                    break;
                case 41:
                    constraint.horizontalChainStyle = typedArray.getInt(index, constraint.horizontalChainStyle);
                    break;
                case 42:
                    constraint.verticalChainStyle = typedArray.getInt(index, constraint.verticalChainStyle);
                    break;
                case 43:
                    constraint.alpha = typedArray.getFloat(index, constraint.alpha);
                    break;
                case 44:
                    constraint.applyElevation = true;
                    constraint.elevation = typedArray.getDimension(index, constraint.elevation);
                    break;
                case 45:
                    constraint.rotationX = typedArray.getFloat(index, constraint.rotationX);
                    break;
                case 46:
                    constraint.rotationY = typedArray.getFloat(index, constraint.rotationY);
                    break;
                case 47:
                    constraint.scaleX = typedArray.getFloat(index, constraint.scaleX);
                    break;
                case 48:
                    constraint.scaleY = typedArray.getFloat(index, constraint.scaleY);
                    break;
                case 49:
                    constraint.transformPivotX = typedArray.getFloat(index, constraint.transformPivotX);
                    break;
                case 50:
                    constraint.transformPivotY = typedArray.getFloat(index, constraint.transformPivotY);
                    break;
                case 51:
                    constraint.translationX = typedArray.getDimension(index, constraint.translationX);
                    break;
                case 52:
                    constraint.translationY = typedArray.getDimension(index, constraint.translationY);
                    break;
                case 53:
                    constraint.translationZ = typedArray.getDimension(index, constraint.translationZ);
                    break;
                default:
                    switch (i2) {
                        case 60:
                            constraint.rotation = typedArray.getFloat(index, constraint.rotation);
                            continue;
                        case 61:
                            constraint.circleConstraint = lookupID(typedArray, index, constraint.circleConstraint);
                            continue;
                        case 62:
                            constraint.circleRadius = typedArray.getDimensionPixelSize(index, constraint.circleRadius);
                            continue;
                        case 63:
                            constraint.circleAngle = typedArray.getFloat(index, constraint.circleAngle);
                            continue;
                        case 64:
                            str = TAG;
                            sb = new StringBuilder();
                            str2 = "unused attribute 0x";
                            break;
                        default:
                            str = TAG;
                            sb = new StringBuilder();
                            str2 = "Unknown attribute 0x";
                            break;
                    }
                    sb.append(str2);
                    sb.append(Integer.toHexString(index));
                    sb.append("   ");
                    sb.append(mapToConstant.get(index));
                    Log.w(str, sb.toString());
                    break;
            }
        }
    }

    private String sideToString(int i) {
        switch (i) {
            case 1:
                return "left";
            case 2:
                return "right";
            case 3:
                return "top";
            case 4:
                return "bottom";
            case 5:
                return "baseline";
            case 6:
                return "start";
            case 7:
                return "end";
            default:
                return "undefined";
        }
    }

    public void addToHorizontalChain(int i, int i2, int i3) {
        connect(i, 1, i2, i2 == 0 ? 1 : 2, 0);
        connect(i, 2, i3, i3 == 0 ? 2 : 1, 0);
        if (i2 != 0) {
            connect(i2, 2, i, 1, 0);
        }
        if (i3 != 0) {
            connect(i3, 1, i, 2, 0);
        }
    }

    public void addToHorizontalChainRTL(int i, int i2, int i3) {
        connect(i, 6, i2, i2 == 0 ? 6 : 7, 0);
        connect(i, 7, i3, i3 == 0 ? 7 : 6, 0);
        if (i2 != 0) {
            connect(i2, 7, i, 6, 0);
        }
        if (i3 != 0) {
            connect(i3, 6, i, 7, 0);
        }
    }

    public void addToVerticalChain(int i, int i2, int i3) {
        connect(i, 3, i2, i2 == 0 ? 3 : 4, 0);
        connect(i, 4, i3, i3 == 0 ? 4 : 3, 0);
        if (i2 != 0) {
            connect(i2, 4, i, 3, 0);
        }
        if (i2 != 0) {
            connect(i3, 3, i, 4, 0);
        }
    }

    public void applyTo(ConstraintLayout constraintLayout) {
        applyToInternal(constraintLayout);
        constraintLayout.setConstraintSet(null);
    }

    void applyToInternal(ConstraintLayout constraintLayout) {
        int childCount = constraintLayout.getChildCount();
        HashSet hashSet = new HashSet(this.mConstraints.keySet());
        for (int i = 0; i < childCount; i++) {
            View childAt = constraintLayout.getChildAt(i);
            int id = childAt.getId();
            if (id == -1) {
                throw new RuntimeException("All children of ConstraintLayout must have ids to use ConstraintSet");
            }
            if (this.mConstraints.containsKey(Integer.valueOf(id))) {
                hashSet.remove(Integer.valueOf(id));
                Constraint constraint = this.mConstraints.get(Integer.valueOf(id));
                if (constraint.mHelperType != -1 && constraint.mHelperType == 1) {
                    Barrier barrier = (Barrier) childAt;
                    barrier.setId(id);
                    barrier.setReferencedIds(constraint.mReferenceIds);
                    barrier.setType(constraint.mBarrierDirection);
                    constraint.applyTo(constraintLayout.generateDefaultLayoutParams());
                }
                ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) childAt.getLayoutParams();
                constraint.applyTo(layoutParams);
                childAt.setLayoutParams(layoutParams);
                childAt.setVisibility(constraint.visibility);
                if (Build.VERSION.SDK_INT >= 17) {
                    childAt.setAlpha(constraint.alpha);
                    childAt.setRotation(constraint.rotation);
                    childAt.setRotationX(constraint.rotationX);
                    childAt.setRotationY(constraint.rotationY);
                    childAt.setScaleX(constraint.scaleX);
                    childAt.setScaleY(constraint.scaleY);
                    if (!Float.isNaN(constraint.transformPivotX)) {
                        childAt.setPivotX(constraint.transformPivotX);
                    }
                    if (!Float.isNaN(constraint.transformPivotY)) {
                        childAt.setPivotY(constraint.transformPivotY);
                    }
                    childAt.setTranslationX(constraint.translationX);
                    childAt.setTranslationY(constraint.translationY);
                    if (Build.VERSION.SDK_INT >= 21) {
                        childAt.setTranslationZ(constraint.translationZ);
                        if (constraint.applyElevation) {
                            childAt.setElevation(constraint.elevation);
                        }
                    }
                }
            }
        }
        Iterator it = hashSet.iterator();
        while (it.hasNext()) {
            Integer num = (Integer) it.next();
            Constraint constraint2 = this.mConstraints.get(num);
            if (constraint2.mHelperType != -1 && constraint2.mHelperType == 1) {
                Barrier barrier2 = new Barrier(constraintLayout.getContext());
                barrier2.setId(num.intValue());
                barrier2.setReferencedIds(constraint2.mReferenceIds);
                barrier2.setType(constraint2.mBarrierDirection);
                ConstraintLayout.LayoutParams layoutParamsGenerateDefaultLayoutParams = constraintLayout.generateDefaultLayoutParams();
                constraint2.applyTo(layoutParamsGenerateDefaultLayoutParams);
                constraintLayout.addView(barrier2, layoutParamsGenerateDefaultLayoutParams);
            }
            if (constraint2.mIsGuideline) {
                Guideline guideline = new Guideline(constraintLayout.getContext());
                guideline.setId(num.intValue());
                ConstraintLayout.LayoutParams layoutParamsGenerateDefaultLayoutParams2 = constraintLayout.generateDefaultLayoutParams();
                constraint2.applyTo(layoutParamsGenerateDefaultLayoutParams2);
                constraintLayout.addView(guideline, layoutParamsGenerateDefaultLayoutParams2);
            }
        }
    }

    public void center(int i, int i2, int i3, int i4, int i5, int i6, int i7, float f) {
        ConstraintSet constraintSet;
        int i8;
        int i9;
        if (i4 < 0) {
            throw new IllegalArgumentException("margin must be > 0");
        }
        if (i7 < 0) {
            throw new IllegalArgumentException("margin must be > 0");
        }
        if (f <= 0.0f || f > 1.0f) {
            throw new IllegalArgumentException("bias must be between 0 and 1 inclusive");
        }
        if (i3 == 1 || i3 == 2) {
            constraintSet = this;
            i8 = i;
            constraintSet.connect(i8, 1, i2, i3, i4);
            i9 = 2;
        } else if (i3 != 6 && i3 != 7) {
            connect(i, 3, i2, i3, i4);
            connect(i, 4, i5, i6, i7);
            this.mConstraints.get(Integer.valueOf(i)).verticalBias = f;
            return;
        } else {
            constraintSet = this;
            i8 = i;
            constraintSet.connect(i8, 6, i2, i3, i4);
            i9 = 7;
        }
        constraintSet.connect(i8, i9, i5, i6, i7);
        this.mConstraints.get(Integer.valueOf(i)).horizontalBias = f;
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public void centerHorizontally(int i, int i2) {
        int i3;
        int i4;
        int i5;
        int i6;
        float f;
        ConstraintSet constraintSet;
        int i7;
        int i8;
        int i9;
        if (i2 == 0) {
            i8 = 0;
            i3 = 1;
            i4 = 0;
            i9 = 0;
            i5 = 2;
            i6 = 0;
            f = 0.5f;
            constraintSet = this;
            i7 = i;
        } else {
            i3 = 2;
            i4 = 0;
            i5 = 1;
            i6 = 0;
            f = 0.5f;
            constraintSet = this;
            i7 = i;
            i8 = i2;
            i9 = i2;
        }
        constraintSet.center(i7, i8, i3, i4, i9, i5, i6, f);
    }

    public void centerHorizontally(int i, int i2, int i3, int i4, int i5, int i6, int i7, float f) {
        connect(i, 1, i2, i3, i4);
        connect(i, 2, i5, i6, i7);
        this.mConstraints.get(Integer.valueOf(i)).horizontalBias = f;
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public void centerHorizontallyRtl(int i, int i2) {
        int i3;
        int i4;
        int i5;
        int i6;
        float f;
        ConstraintSet constraintSet;
        int i7;
        int i8;
        int i9;
        if (i2 == 0) {
            i8 = 0;
            i3 = 6;
            i4 = 0;
            i9 = 0;
            i5 = 7;
            i6 = 0;
            f = 0.5f;
            constraintSet = this;
            i7 = i;
        } else {
            i3 = 7;
            i4 = 0;
            i5 = 6;
            i6 = 0;
            f = 0.5f;
            constraintSet = this;
            i7 = i;
            i8 = i2;
            i9 = i2;
        }
        constraintSet.center(i7, i8, i3, i4, i9, i5, i6, f);
    }

    public void centerHorizontallyRtl(int i, int i2, int i3, int i4, int i5, int i6, int i7, float f) {
        connect(i, 6, i2, i3, i4);
        connect(i, 7, i5, i6, i7);
        this.mConstraints.get(Integer.valueOf(i)).horizontalBias = f;
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public void centerVertically(int i, int i2) {
        int i3;
        int i4;
        int i5;
        int i6;
        float f;
        ConstraintSet constraintSet;
        int i7;
        int i8;
        int i9;
        if (i2 == 0) {
            i8 = 0;
            i3 = 3;
            i4 = 0;
            i9 = 0;
            i5 = 4;
            i6 = 0;
            f = 0.5f;
            constraintSet = this;
            i7 = i;
        } else {
            i3 = 4;
            i4 = 0;
            i5 = 3;
            i6 = 0;
            f = 0.5f;
            constraintSet = this;
            i7 = i;
            i8 = i2;
            i9 = i2;
        }
        constraintSet.center(i7, i8, i3, i4, i9, i5, i6, f);
    }

    public void centerVertically(int i, int i2, int i3, int i4, int i5, int i6, int i7, float f) {
        connect(i, 3, i2, i3, i4);
        connect(i, 4, i5, i6, i7);
        this.mConstraints.get(Integer.valueOf(i)).verticalBias = f;
    }

    public void clear(int i) {
        this.mConstraints.remove(Integer.valueOf(i));
    }

    public void clear(int i, int i2) {
        if (this.mConstraints.containsKey(Integer.valueOf(i))) {
            Constraint constraint = this.mConstraints.get(Integer.valueOf(i));
            switch (i2) {
                case 1:
                    constraint.leftToRight = -1;
                    constraint.leftToLeft = -1;
                    constraint.leftMargin = -1;
                    constraint.goneLeftMargin = -1;
                    return;
                case 2:
                    constraint.rightToRight = -1;
                    constraint.rightToLeft = -1;
                    constraint.rightMargin = -1;
                    constraint.goneRightMargin = -1;
                    return;
                case 3:
                    constraint.topToBottom = -1;
                    constraint.topToTop = -1;
                    constraint.topMargin = -1;
                    constraint.goneTopMargin = -1;
                    return;
                case 4:
                    constraint.bottomToTop = -1;
                    constraint.bottomToBottom = -1;
                    constraint.bottomMargin = -1;
                    constraint.goneBottomMargin = -1;
                    return;
                case 5:
                    constraint.baselineToBaseline = -1;
                    return;
                case 6:
                    constraint.startToEnd = -1;
                    constraint.startToStart = -1;
                    constraint.startMargin = -1;
                    constraint.goneStartMargin = -1;
                    return;
                case 7:
                    constraint.endToStart = -1;
                    constraint.endToEnd = -1;
                    constraint.endMargin = -1;
                    constraint.goneEndMargin = -1;
                    return;
                default:
                    throw new IllegalArgumentException("unknown constraint");
            }
        }
    }

    public void clone(Context context, int i) {
        clone((ConstraintLayout) LayoutInflater.from(context).inflate(i, (ViewGroup) null));
    }

    public void clone(ConstraintLayout constraintLayout) {
        int childCount = constraintLayout.getChildCount();
        this.mConstraints.clear();
        for (int i = 0; i < childCount; i++) {
            View childAt = constraintLayout.getChildAt(i);
            ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) childAt.getLayoutParams();
            int id = childAt.getId();
            if (id == -1) {
                throw new RuntimeException("All children of ConstraintLayout must have ids to use ConstraintSet");
            }
            if (!this.mConstraints.containsKey(Integer.valueOf(id))) {
                this.mConstraints.put(Integer.valueOf(id), new Constraint());
            }
            Constraint constraint = this.mConstraints.get(Integer.valueOf(id));
            constraint.fillFrom(id, layoutParams);
            constraint.visibility = childAt.getVisibility();
            if (Build.VERSION.SDK_INT >= 17) {
                constraint.alpha = childAt.getAlpha();
                constraint.rotation = childAt.getRotation();
                constraint.rotationX = childAt.getRotationX();
                constraint.rotationY = childAt.getRotationY();
                constraint.scaleX = childAt.getScaleX();
                constraint.scaleY = childAt.getScaleY();
                float pivotX = childAt.getPivotX();
                float pivotY = childAt.getPivotY();
                if (pivotX != 0.0d || pivotY != 0.0d) {
                    constraint.transformPivotX = pivotX;
                    constraint.transformPivotY = pivotY;
                }
                constraint.translationX = childAt.getTranslationX();
                constraint.translationY = childAt.getTranslationY();
                if (Build.VERSION.SDK_INT >= 21) {
                    constraint.translationZ = childAt.getTranslationZ();
                    if (constraint.applyElevation) {
                        constraint.elevation = childAt.getElevation();
                    }
                }
            }
        }
    }

    public void clone(ConstraintSet constraintSet) {
        this.mConstraints.clear();
        for (Integer num : constraintSet.mConstraints.keySet()) {
            this.mConstraints.put(num, constraintSet.mConstraints.get(num).m0clone());
        }
    }

    public void clone(Constraints constraints) {
        int childCount = constraints.getChildCount();
        this.mConstraints.clear();
        for (int i = 0; i < childCount; i++) {
            View childAt = constraints.getChildAt(i);
            Constraints.LayoutParams layoutParams = (Constraints.LayoutParams) childAt.getLayoutParams();
            int id = childAt.getId();
            if (id == -1) {
                throw new RuntimeException("All children of ConstraintLayout must have ids to use ConstraintSet");
            }
            if (!this.mConstraints.containsKey(Integer.valueOf(id))) {
                this.mConstraints.put(Integer.valueOf(id), new Constraint());
            }
            Constraint constraint = this.mConstraints.get(Integer.valueOf(id));
            if (childAt instanceof ConstraintHelper) {
                constraint.fillFromConstraints((ConstraintHelper) childAt, id, layoutParams);
            }
            constraint.fillFromConstraints(id, layoutParams);
        }
    }

    public void connect(int i, int i2, int i3, int i4) {
        if (!this.mConstraints.containsKey(Integer.valueOf(i))) {
            this.mConstraints.put(Integer.valueOf(i), new Constraint());
        }
        Constraint constraint = this.mConstraints.get(Integer.valueOf(i));
        switch (i2) {
            case 1:
                if (i4 == 1) {
                    constraint.leftToLeft = i3;
                    constraint.leftToRight = -1;
                    return;
                } else if (i4 == 2) {
                    constraint.leftToRight = i3;
                    constraint.leftToLeft = -1;
                    return;
                } else {
                    throw new IllegalArgumentException("left to " + sideToString(i4) + " undefined");
                }
            case 2:
                if (i4 == 1) {
                    constraint.rightToLeft = i3;
                    constraint.rightToRight = -1;
                    return;
                } else if (i4 == 2) {
                    constraint.rightToRight = i3;
                    constraint.rightToLeft = -1;
                    return;
                } else {
                    throw new IllegalArgumentException("right to " + sideToString(i4) + " undefined");
                }
            case 3:
                if (i4 == 3) {
                    constraint.topToTop = i3;
                    constraint.topToBottom = -1;
                    break;
                } else {
                    if (i4 != 4) {
                        throw new IllegalArgumentException("right to " + sideToString(i4) + " undefined");
                    }
                    constraint.topToBottom = i3;
                    constraint.topToTop = -1;
                    break;
                }
            case 4:
                if (i4 == 4) {
                    constraint.bottomToBottom = i3;
                    constraint.bottomToTop = -1;
                    break;
                } else {
                    if (i4 != 3) {
                        throw new IllegalArgumentException("right to " + sideToString(i4) + " undefined");
                    }
                    constraint.bottomToTop = i3;
                    constraint.bottomToBottom = -1;
                    break;
                }
            case 5:
                if (i4 != 5) {
                    throw new IllegalArgumentException("right to " + sideToString(i4) + " undefined");
                }
                constraint.baselineToBaseline = i3;
                constraint.bottomToBottom = -1;
                constraint.bottomToTop = -1;
                constraint.topToTop = -1;
                constraint.topToBottom = -1;
                return;
            case 6:
                if (i4 == 6) {
                    constraint.startToStart = i3;
                    constraint.startToEnd = -1;
                    return;
                } else if (i4 == 7) {
                    constraint.startToEnd = i3;
                    constraint.startToStart = -1;
                    return;
                } else {
                    throw new IllegalArgumentException("right to " + sideToString(i4) + " undefined");
                }
            case 7:
                if (i4 == 7) {
                    constraint.endToEnd = i3;
                    constraint.endToStart = -1;
                    return;
                } else if (i4 == 6) {
                    constraint.endToStart = i3;
                    constraint.endToEnd = -1;
                    return;
                } else {
                    throw new IllegalArgumentException("right to " + sideToString(i4) + " undefined");
                }
            default:
                throw new IllegalArgumentException(sideToString(i2) + " to " + sideToString(i4) + " unknown");
        }
        constraint.baselineToBaseline = -1;
    }

    public void connect(int i, int i2, int i3, int i4, int i5) {
        if (!this.mConstraints.containsKey(Integer.valueOf(i))) {
            this.mConstraints.put(Integer.valueOf(i), new Constraint());
        }
        Constraint constraint = this.mConstraints.get(Integer.valueOf(i));
        switch (i2) {
            case 1:
                if (i4 == 1) {
                    constraint.leftToLeft = i3;
                    constraint.leftToRight = -1;
                } else {
                    if (i4 != 2) {
                        throw new IllegalArgumentException("Left to " + sideToString(i4) + " undefined");
                    }
                    constraint.leftToRight = i3;
                    constraint.leftToLeft = -1;
                }
                constraint.leftMargin = i5;
                return;
            case 2:
                if (i4 == 1) {
                    constraint.rightToLeft = i3;
                    constraint.rightToRight = -1;
                } else {
                    if (i4 != 2) {
                        throw new IllegalArgumentException("right to " + sideToString(i4) + " undefined");
                    }
                    constraint.rightToRight = i3;
                    constraint.rightToLeft = -1;
                }
                constraint.rightMargin = i5;
                return;
            case 3:
                if (i4 == 3) {
                    constraint.topToTop = i3;
                    constraint.topToBottom = -1;
                } else {
                    if (i4 != 4) {
                        throw new IllegalArgumentException("right to " + sideToString(i4) + " undefined");
                    }
                    constraint.topToBottom = i3;
                    constraint.topToTop = -1;
                }
                constraint.baselineToBaseline = -1;
                constraint.topMargin = i5;
                return;
            case 4:
                if (i4 == 4) {
                    constraint.bottomToBottom = i3;
                    constraint.bottomToTop = -1;
                } else {
                    if (i4 != 3) {
                        throw new IllegalArgumentException("right to " + sideToString(i4) + " undefined");
                    }
                    constraint.bottomToTop = i3;
                    constraint.bottomToBottom = -1;
                }
                constraint.baselineToBaseline = -1;
                constraint.bottomMargin = i5;
                return;
            case 5:
                if (i4 != 5) {
                    throw new IllegalArgumentException("right to " + sideToString(i4) + " undefined");
                }
                constraint.baselineToBaseline = i3;
                constraint.bottomToBottom = -1;
                constraint.bottomToTop = -1;
                constraint.topToTop = -1;
                constraint.topToBottom = -1;
                return;
            case 6:
                if (i4 == 6) {
                    constraint.startToStart = i3;
                    constraint.startToEnd = -1;
                } else {
                    if (i4 != 7) {
                        throw new IllegalArgumentException("right to " + sideToString(i4) + " undefined");
                    }
                    constraint.startToEnd = i3;
                    constraint.startToStart = -1;
                }
                constraint.startMargin = i5;
                return;
            case 7:
                if (i4 == 7) {
                    constraint.endToEnd = i3;
                    constraint.endToStart = -1;
                } else {
                    if (i4 != 6) {
                        throw new IllegalArgumentException("right to " + sideToString(i4) + " undefined");
                    }
                    constraint.endToStart = i3;
                    constraint.endToEnd = -1;
                }
                constraint.endMargin = i5;
                return;
            default:
                throw new IllegalArgumentException(sideToString(i2) + " to " + sideToString(i4) + " unknown");
        }
    }

    public void constrainCircle(int i, int i2, int i3, float f) {
        Constraint constraint = get(i);
        constraint.circleConstraint = i2;
        constraint.circleRadius = i3;
        constraint.circleAngle = f;
    }

    public void constrainDefaultHeight(int i, int i2) {
        get(i).heightDefault = i2;
    }

    public void constrainDefaultWidth(int i, int i2) {
        get(i).widthDefault = i2;
    }

    public void constrainHeight(int i, int i2) {
        get(i).mHeight = i2;
    }

    public void constrainMaxHeight(int i, int i2) {
        get(i).heightMax = i2;
    }

    public void constrainMaxWidth(int i, int i2) {
        get(i).widthMax = i2;
    }

    public void constrainMinHeight(int i, int i2) {
        get(i).heightMin = i2;
    }

    public void constrainMinWidth(int i, int i2) {
        get(i).widthMin = i2;
    }

    public void constrainPercentHeight(int i, float f) {
        get(i).heightPercent = f;
    }

    public void constrainPercentWidth(int i, float f) {
        get(i).widthPercent = f;
    }

    public void constrainWidth(int i, int i2) {
        get(i).mWidth = i2;
    }

    public void create(int i, int i2) {
        Constraint constraint = get(i);
        constraint.mIsGuideline = true;
        constraint.orientation = i2;
    }

    public void createBarrier(int i, int i2, int... iArr) {
        Constraint constraint = get(i);
        constraint.mHelperType = 1;
        constraint.mBarrierDirection = i2;
        constraint.mIsGuideline = false;
        constraint.mReferenceIds = iArr;
    }

    public void createHorizontalChain(int i, int i2, int i3, int i4, int[] iArr, float[] fArr, int i5) {
        createHorizontalChain(i, i2, i3, i4, iArr, fArr, i5, 1, 2);
    }

    public void createHorizontalChainRtl(int i, int i2, int i3, int i4, int[] iArr, float[] fArr, int i5) {
        createHorizontalChain(i, i2, i3, i4, iArr, fArr, i5, 6, 7);
    }

    public void createVerticalChain(int i, int i2, int i3, int i4, int[] iArr, float[] fArr, int i5) {
        if (iArr.length < 2) {
            throw new IllegalArgumentException("must have 2 or more widgets in a chain");
        }
        if (fArr != null && fArr.length != iArr.length) {
            throw new IllegalArgumentException("must have 2 or more widgets in a chain");
        }
        if (fArr != null) {
            get(iArr[0]).verticalWeight = fArr[0];
        }
        get(iArr[0]).verticalChainStyle = i5;
        connect(iArr[0], 3, i, i2, 0);
        for (int i6 = 1; i6 < iArr.length; i6++) {
            int i7 = iArr[i6];
            int i8 = i6 - 1;
            connect(iArr[i6], 3, iArr[i8], 4, 0);
            connect(iArr[i8], 4, iArr[i6], 3, 0);
            if (fArr != null) {
                get(iArr[i6]).verticalWeight = fArr[i6];
            }
        }
        connect(iArr[iArr.length - 1], 4, i3, i4, 0);
    }

    public boolean getApplyElevation(int i) {
        return get(i).applyElevation;
    }

    public void load(Context context, int i) throws XmlPullParserException, Resources.NotFoundException, IOException {
        int eventType;
        XmlResourceParser xml = context.getResources().getXml(i);
        try {
        } catch (IOException e) {
            e.printStackTrace();
            return;
        } catch (XmlPullParserException e2) {
            e2.printStackTrace();
            return;
        }
        for (eventType = xml.getEventType(); eventType != 1; eventType = xml.next()) {
            if (eventType != 0) {
                switch (eventType) {
                    case 2:
                        String name = xml.getName();
                        Constraint constraintFillFromAttributeList = fillFromAttributeList(context, Xml.asAttributeSet(xml));
                        if (name.equalsIgnoreCase("Guideline")) {
                            constraintFillFromAttributeList.mIsGuideline = true;
                        }
                        this.mConstraints.put(Integer.valueOf(constraintFillFromAttributeList.mViewId), constraintFillFromAttributeList);
                        continue;
                    case 3:
                        continue;
                    default:
                        continue;
                }
                e.printStackTrace();
                return;
            }
            xml.getName();
        }
    }

    public void removeFromHorizontalChain(int i) {
        int i2;
        int i3;
        int i4;
        int i5;
        ConstraintSet constraintSet;
        int i6;
        int i7;
        int i8;
        int i9;
        int i10;
        int i11;
        ConstraintSet constraintSet2;
        int i12;
        if (this.mConstraints.containsKey(Integer.valueOf(i))) {
            Constraint constraint = this.mConstraints.get(Integer.valueOf(i));
            int i13 = constraint.leftToRight;
            int i14 = constraint.rightToLeft;
            if (i13 == -1 && i14 == -1) {
                int i15 = constraint.startToEnd;
                int i16 = constraint.endToStart;
                if (i15 != -1 || i16 != -1) {
                    if (i15 != -1 && i16 != -1) {
                        i11 = 0;
                        constraintSet2 = this;
                        constraintSet2.connect(i15, 7, i16, 6, 0);
                        i8 = 6;
                        i10 = 7;
                        i12 = i16;
                        i9 = i13;
                    } else if (i13 != -1 || i16 != -1) {
                        if (constraint.rightToRight != -1) {
                            i8 = 7;
                            i9 = constraint.rightToRight;
                            i10 = 7;
                            i11 = 0;
                            constraintSet2 = this;
                            i12 = i13;
                        } else if (constraint.leftToLeft != -1) {
                            i8 = 6;
                            i9 = constraint.leftToLeft;
                            i10 = 6;
                            i11 = 0;
                            constraintSet2 = this;
                            i12 = i16;
                        }
                    }
                    constraintSet2.connect(i12, i8, i9, i10, i11);
                }
                clear(i, 6);
                i7 = 7;
            } else {
                if (i13 == -1 || i14 == -1) {
                    if (i13 != -1 || i14 != -1) {
                        if (constraint.rightToRight != -1) {
                            i2 = 2;
                            i3 = constraint.rightToRight;
                            i4 = 2;
                            i5 = 0;
                            constraintSet = this;
                            i6 = i13;
                        } else if (constraint.leftToLeft != -1) {
                            i2 = 1;
                            i3 = constraint.leftToLeft;
                            i4 = 1;
                            i5 = 0;
                            constraintSet = this;
                            i6 = i14;
                        }
                    }
                    clear(i, 1);
                    i7 = 2;
                } else {
                    i5 = 0;
                    constraintSet = this;
                    constraintSet.connect(i13, 2, i14, 1, 0);
                    i2 = 1;
                    i4 = 2;
                    i6 = i14;
                    i3 = i13;
                }
                constraintSet.connect(i6, i2, i3, i4, i5);
                clear(i, 1);
                i7 = 2;
            }
            clear(i, i7);
        }
    }

    public void removeFromVerticalChain(int i) {
        int i2;
        int i3;
        int i4;
        int i5;
        ConstraintSet constraintSet;
        int i6;
        if (this.mConstraints.containsKey(Integer.valueOf(i))) {
            Constraint constraint = this.mConstraints.get(Integer.valueOf(i));
            int i7 = constraint.topToBottom;
            int i8 = constraint.bottomToTop;
            if (i7 != -1 || i8 != -1) {
                if (i7 != -1 && i8 != -1) {
                    i5 = 0;
                    constraintSet = this;
                    constraintSet.connect(i7, 4, i8, 3, 0);
                    i2 = 3;
                    i4 = 4;
                    i6 = i8;
                    i3 = i7;
                } else if (i7 != -1 || i8 != -1) {
                    if (constraint.bottomToBottom != -1) {
                        i2 = 4;
                        i3 = constraint.bottomToBottom;
                        i4 = 4;
                        i5 = 0;
                        constraintSet = this;
                        i6 = i7;
                    } else if (constraint.topToTop != -1) {
                        i2 = 3;
                        i3 = constraint.topToTop;
                        i4 = 3;
                        i5 = 0;
                        constraintSet = this;
                        i6 = i8;
                    }
                }
                constraintSet.connect(i6, i2, i3, i4, i5);
            }
        }
        clear(i, 3);
        clear(i, 4);
    }

    public void setAlpha(int i, float f) {
        get(i).alpha = f;
    }

    public void setApplyElevation(int i, boolean z) {
        get(i).applyElevation = z;
    }

    public void setBarrierType(int i, int i2) {
    }

    public void setDimensionRatio(int i, String str) {
        get(i).dimensionRatio = str;
    }

    public void setElevation(int i, float f) {
        get(i).elevation = f;
        get(i).applyElevation = true;
    }

    public void setGoneMargin(int i, int i2, int i3) {
        Constraint constraint = get(i);
        switch (i2) {
            case 1:
                constraint.goneLeftMargin = i3;
                return;
            case 2:
                constraint.goneRightMargin = i3;
                return;
            case 3:
                constraint.goneTopMargin = i3;
                return;
            case 4:
                constraint.goneBottomMargin = i3;
                return;
            case 5:
                throw new IllegalArgumentException("baseline does not support margins");
            case 6:
                constraint.goneStartMargin = i3;
                return;
            case 7:
                constraint.goneEndMargin = i3;
                return;
            default:
                throw new IllegalArgumentException("unknown constraint");
        }
    }

    public void setGuidelineBegin(int i, int i2) {
        get(i).guideBegin = i2;
        get(i).guideEnd = -1;
        get(i).guidePercent = -1.0f;
    }

    public void setGuidelineEnd(int i, int i2) {
        get(i).guideEnd = i2;
        get(i).guideBegin = -1;
        get(i).guidePercent = -1.0f;
    }

    public void setGuidelinePercent(int i, float f) {
        get(i).guidePercent = f;
        get(i).guideEnd = -1;
        get(i).guideBegin = -1;
    }

    public void setHorizontalBias(int i, float f) {
        get(i).horizontalBias = f;
    }

    public void setHorizontalChainStyle(int i, int i2) {
        get(i).horizontalChainStyle = i2;
    }

    public void setHorizontalWeight(int i, float f) {
        get(i).horizontalWeight = f;
    }

    public void setMargin(int i, int i2, int i3) {
        Constraint constraint = get(i);
        switch (i2) {
            case 1:
                constraint.leftMargin = i3;
                return;
            case 2:
                constraint.rightMargin = i3;
                return;
            case 3:
                constraint.topMargin = i3;
                return;
            case 4:
                constraint.bottomMargin = i3;
                return;
            case 5:
                throw new IllegalArgumentException("baseline does not support margins");
            case 6:
                constraint.startMargin = i3;
                return;
            case 7:
                constraint.endMargin = i3;
                return;
            default:
                throw new IllegalArgumentException("unknown constraint");
        }
    }

    public void setRotation(int i, float f) {
        get(i).rotation = f;
    }

    public void setRotationX(int i, float f) {
        get(i).rotationX = f;
    }

    public void setRotationY(int i, float f) {
        get(i).rotationY = f;
    }

    public void setScaleX(int i, float f) {
        get(i).scaleX = f;
    }

    public void setScaleY(int i, float f) {
        get(i).scaleY = f;
    }

    public void setTransformPivot(int i, float f, float f2) {
        Constraint constraint = get(i);
        constraint.transformPivotY = f2;
        constraint.transformPivotX = f;
    }

    public void setTransformPivotX(int i, float f) {
        get(i).transformPivotX = f;
    }

    public void setTransformPivotY(int i, float f) {
        get(i).transformPivotY = f;
    }

    public void setTranslation(int i, float f, float f2) {
        Constraint constraint = get(i);
        constraint.translationX = f;
        constraint.translationY = f2;
    }

    public void setTranslationX(int i, float f) {
        get(i).translationX = f;
    }

    public void setTranslationY(int i, float f) {
        get(i).translationY = f;
    }

    public void setTranslationZ(int i, float f) {
        get(i).translationZ = f;
    }

    public void setVerticalBias(int i, float f) {
        get(i).verticalBias = f;
    }

    public void setVerticalChainStyle(int i, int i2) {
        get(i).verticalChainStyle = i2;
    }

    public void setVerticalWeight(int i, float f) {
        get(i).verticalWeight = f;
    }

    public void setVisibility(int i, int i2) {
        get(i).visibility = i2;
    }
}
