package android.support.v7.widget.helper;

import android.graphics.Canvas;
import android.support.v4.view.ViewCompat;
import android.support.v7.recyclerview.R;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/* loaded from: classes.dex */
class ItemTouchUIUtilImpl {

    static class Gingerbread implements ItemTouchUIUtil {
        Gingerbread() {
        }

        private void draw(Canvas canvas, RecyclerView recyclerView, View view, float f, float f2) {
            canvas.save();
            canvas.translate(f, f2);
            recyclerView.drawChild(canvas, view, 0L);
            canvas.restore();
        }

        @Override // android.support.v7.widget.helper.ItemTouchUIUtil
        public void clearView(View view) {
            view.setVisibility(0);
        }

        @Override // android.support.v7.widget.helper.ItemTouchUIUtil
        public void onDraw(Canvas canvas, RecyclerView recyclerView, View view, float f, float f2, int i, boolean z) {
            if (i != 2) {
                draw(canvas, recyclerView, view, f, f2);
            }
        }

        @Override // android.support.v7.widget.helper.ItemTouchUIUtil
        public void onDrawOver(Canvas canvas, RecyclerView recyclerView, View view, float f, float f2, int i, boolean z) {
            if (i == 2) {
                draw(canvas, recyclerView, view, f, f2);
            }
        }

        @Override // android.support.v7.widget.helper.ItemTouchUIUtil
        public void onSelected(View view) {
            view.setVisibility(4);
        }
    }

    static class Honeycomb implements ItemTouchUIUtil {
        Honeycomb() {
        }

        @Override // android.support.v7.widget.helper.ItemTouchUIUtil
        public void clearView(View view) {
            ViewCompat.setTranslationX(view, 0.0f);
            ViewCompat.setTranslationY(view, 0.0f);
        }

        @Override // android.support.v7.widget.helper.ItemTouchUIUtil
        public void onDraw(Canvas canvas, RecyclerView recyclerView, View view, float f, float f2, int i, boolean z) {
            ViewCompat.setTranslationX(view, f);
            ViewCompat.setTranslationY(view, f2);
        }

        @Override // android.support.v7.widget.helper.ItemTouchUIUtil
        public void onDrawOver(Canvas canvas, RecyclerView recyclerView, View view, float f, float f2, int i, boolean z) {
        }

        @Override // android.support.v7.widget.helper.ItemTouchUIUtil
        public void onSelected(View view) {
        }
    }

    static class Lollipop extends Honeycomb {
        Lollipop() {
        }

        private float findMaxElevation(RecyclerView recyclerView, View view) {
            int childCount = recyclerView.getChildCount();
            float f = 0.0f;
            for (int i = 0; i < childCount; i++) {
                View childAt = recyclerView.getChildAt(i);
                if (childAt != view) {
                    float elevation = ViewCompat.getElevation(childAt);
                    if (elevation > f) {
                        f = elevation;
                    }
                }
            }
            return f;
        }

        @Override // android.support.v7.widget.helper.ItemTouchUIUtilImpl.Honeycomb, android.support.v7.widget.helper.ItemTouchUIUtil
        public void clearView(View view) {
            Object tag = view.getTag(R.id.item_touch_helper_previous_elevation);
            if (tag != null && (tag instanceof Float)) {
                ViewCompat.setElevation(view, ((Float) tag).floatValue());
            }
            view.setTag(R.id.item_touch_helper_previous_elevation, null);
            super.clearView(view);
        }

        @Override // android.support.v7.widget.helper.ItemTouchUIUtilImpl.Honeycomb, android.support.v7.widget.helper.ItemTouchUIUtil
        public void onDraw(Canvas canvas, RecyclerView recyclerView, View view, float f, float f2, int i, boolean z) {
            if (z && view.getTag(R.id.item_touch_helper_previous_elevation) == null) {
                Float fValueOf = Float.valueOf(ViewCompat.getElevation(view));
                ViewCompat.setElevation(view, 1.0f + findMaxElevation(recyclerView, view));
                view.setTag(R.id.item_touch_helper_previous_elevation, fValueOf);
            }
            super.onDraw(canvas, recyclerView, view, f, f2, i, z);
        }
    }

    ItemTouchUIUtilImpl() {
    }
}
