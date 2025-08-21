package android.support.v7.app;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationBuilderWithBuilderAccessor;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompatBase;
import android.support.v7.appcompat.R;
import android.widget.RemoteViews;
import java.util.ArrayList;
import java.util.List;

@RequiresApi(9)
@TargetApi(9)
/* loaded from: classes.dex */
class NotificationCompatImplBase {
    private static final int MAX_ACTION_BUTTONS = 3;
    static final int MAX_MEDIA_BUTTONS = 5;
    static final int MAX_MEDIA_BUTTONS_IN_COMPACT = 3;

    NotificationCompatImplBase() {
    }

    /* JADX WARN: Removed duplicated region for block: B:58:0x015f  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x0164  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x016c  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x018b  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x01c2 A[PHI: r14
      0x01c2: PHI (r14v8 boolean) = (r14v7 boolean), (r14v11 boolean) binds: [B:67:0x0189, B:71:0x0193] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:76:0x01c7  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x01c9  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x01d2  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static android.widget.RemoteViews applyStandardTemplate(android.content.Context r17, java.lang.CharSequence r18, java.lang.CharSequence r19, java.lang.CharSequence r20, int r21, int r22, android.graphics.Bitmap r23, java.lang.CharSequence r24, boolean r25, long r26, int r28, int r29, int r30, boolean r31) throws android.content.res.Resources.NotFoundException {
        /*
            Method dump skipped, instructions count: 471
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.app.NotificationCompatImplBase.applyStandardTemplate(android.content.Context, java.lang.CharSequence, java.lang.CharSequence, java.lang.CharSequence, int, int, android.graphics.Bitmap, java.lang.CharSequence, boolean, long, int, int, int, boolean):android.widget.RemoteViews");
    }

    public static RemoteViews applyStandardTemplateWithActions(Context context, CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, int i, int i2, Bitmap bitmap, CharSequence charSequence4, boolean z, long j, int i3, int i4, int i5, boolean z2, ArrayList<NotificationCompat.Action> arrayList) throws Resources.NotFoundException {
        boolean z3;
        int size;
        RemoteViews remoteViewsApplyStandardTemplate = applyStandardTemplate(context, charSequence, charSequence2, charSequence3, i, i2, bitmap, charSequence4, z, j, i3, i4, i5, z2);
        remoteViewsApplyStandardTemplate.removeAllViews(R.id.actions);
        if (arrayList == null || (size = arrayList.size()) <= 0) {
            z3 = false;
        } else {
            if (size > 3) {
                size = 3;
            }
            for (int i6 = 0; i6 < size; i6++) {
                remoteViewsApplyStandardTemplate.addView(R.id.actions, generateActionButton(context, arrayList.get(i6)));
            }
            z3 = true;
        }
        int i7 = z3 ? 0 : 8;
        remoteViewsApplyStandardTemplate.setViewVisibility(R.id.actions, i7);
        remoteViewsApplyStandardTemplate.setViewVisibility(R.id.action_divider, i7);
        return remoteViewsApplyStandardTemplate;
    }

    public static void buildIntoRemoteViews(Context context, RemoteViews remoteViews, RemoteViews remoteViews2) {
        hideNormalContent(remoteViews);
        remoteViews.removeAllViews(R.id.notification_main_column);
        remoteViews.addView(R.id.notification_main_column, remoteViews2.clone());
        remoteViews.setViewVisibility(R.id.notification_main_column, 0);
        if (Build.VERSION.SDK_INT >= 21) {
            remoteViews.setViewPadding(R.id.notification_main_column_container, 0, calculateTopPadding(context), 0, 0);
        }
    }

    public static int calculateTopPadding(Context context) throws Resources.NotFoundException {
        int dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.notification_top_pad);
        int dimensionPixelSize2 = context.getResources().getDimensionPixelSize(R.dimen.notification_top_pad_large_text);
        float fConstrain = (constrain(context.getResources().getConfiguration().fontScale, 1.0f, 1.3f) - 1.0f) / 0.29999995f;
        return Math.round(((1.0f - fConstrain) * dimensionPixelSize) + (fConstrain * dimensionPixelSize2));
    }

    public static float constrain(float f, float f2, float f3) {
        return f < f2 ? f2 : f > f3 ? f3 : f;
    }

    private static Bitmap createColoredBitmap(Context context, int i, int i2) {
        return createColoredBitmap(context, i, i2, 0);
    }

    private static Bitmap createColoredBitmap(Context context, int i, int i2, int i3) throws Resources.NotFoundException {
        Drawable drawable = context.getResources().getDrawable(i);
        int intrinsicWidth = i3 == 0 ? drawable.getIntrinsicWidth() : i3;
        if (i3 == 0) {
            i3 = drawable.getIntrinsicHeight();
        }
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(intrinsicWidth, i3, Bitmap.Config.ARGB_8888);
        drawable.setBounds(0, 0, intrinsicWidth, i3);
        if (i2 != 0) {
            drawable.mutate().setColorFilter(new PorterDuffColorFilter(i2, PorterDuff.Mode.SRC_IN));
        }
        drawable.draw(new Canvas(bitmapCreateBitmap));
        return bitmapCreateBitmap;
    }

    public static Bitmap createIconWithBackground(Context context, int i, int i2, int i3, int i4) throws Resources.NotFoundException {
        int i5 = R.drawable.notification_icon_background;
        if (i4 == 0) {
            i4 = 0;
        }
        Bitmap bitmapCreateColoredBitmap = createColoredBitmap(context, i5, i4, i2);
        Canvas canvas = new Canvas(bitmapCreateColoredBitmap);
        Drawable drawableMutate = context.getResources().getDrawable(i).mutate();
        drawableMutate.setFilterBitmap(true);
        int i6 = (i2 - i3) / 2;
        int i7 = i3 + i6;
        drawableMutate.setBounds(i6, i6, i7, i7);
        drawableMutate.setColorFilter(new PorterDuffColorFilter(-1, PorterDuff.Mode.SRC_ATOP));
        drawableMutate.draw(canvas);
        return bitmapCreateColoredBitmap;
    }

    private static RemoteViews generateActionButton(Context context, NotificationCompat.Action action) {
        boolean z = action.actionIntent == null;
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), z ? getActionTombstoneLayoutResource() : getActionLayoutResource());
        remoteViews.setImageViewBitmap(R.id.action_image, createColoredBitmap(context, action.getIcon(), context.getResources().getColor(R.color.notification_action_color_filter)));
        remoteViews.setTextViewText(R.id.action_text, action.title);
        if (!z) {
            remoteViews.setOnClickPendingIntent(R.id.action_container, action.actionIntent);
        }
        if (Build.VERSION.SDK_INT >= 15) {
            remoteViews.setContentDescription(R.id.action_container, action.title);
        }
        return remoteViews;
    }

    @RequiresApi(11)
    @TargetApi(11)
    private static <T extends NotificationCompatBase.Action> RemoteViews generateContentViewMedia(Context context, CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, int i, Bitmap bitmap, CharSequence charSequence4, boolean z, long j, int i2, List<T> list, int[] iArr, boolean z2, PendingIntent pendingIntent, boolean z3) throws Resources.NotFoundException {
        RemoteViews remoteViewsApplyStandardTemplate = applyStandardTemplate(context, charSequence, charSequence2, charSequence3, i, 0, bitmap, charSequence4, z, j, i2, 0, z3 ? R.layout.notification_template_media_custom : R.layout.notification_template_media, true);
        int size = list.size();
        int iMin = iArr == null ? 0 : Math.min(iArr.length, 3);
        remoteViewsApplyStandardTemplate.removeAllViews(R.id.media_actions);
        if (iMin > 0) {
            for (int i3 = 0; i3 < iMin; i3++) {
                if (i3 >= size) {
                    throw new IllegalArgumentException(String.format("setShowActionsInCompactView: action %d out of bounds (max %d)", Integer.valueOf(i3), Integer.valueOf(size - 1)));
                }
                remoteViewsApplyStandardTemplate.addView(R.id.media_actions, generateMediaActionButton(context, list.get(iArr[i3])));
            }
        }
        if (!z2) {
            remoteViewsApplyStandardTemplate.setViewVisibility(R.id.end_padder, 0);
            remoteViewsApplyStandardTemplate.setViewVisibility(R.id.cancel_action, 8);
            return remoteViewsApplyStandardTemplate;
        }
        remoteViewsApplyStandardTemplate.setViewVisibility(R.id.end_padder, 8);
        remoteViewsApplyStandardTemplate.setViewVisibility(R.id.cancel_action, 0);
        remoteViewsApplyStandardTemplate.setOnClickPendingIntent(R.id.cancel_action, pendingIntent);
        remoteViewsApplyStandardTemplate.setInt(R.id.cancel_action, "setAlpha", context.getResources().getInteger(R.integer.cancel_button_image_alpha));
        return remoteViewsApplyStandardTemplate;
    }

    @RequiresApi(11)
    @TargetApi(11)
    private static RemoteViews generateMediaActionButton(Context context, NotificationCompatBase.Action action) {
        boolean z = action.getActionIntent() == null;
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.notification_media_action);
        remoteViews.setImageViewResource(R.id.action0, action.getIcon());
        if (!z) {
            remoteViews.setOnClickPendingIntent(R.id.action0, action.getActionIntent());
        }
        if (Build.VERSION.SDK_INT >= 15) {
            remoteViews.setContentDescription(R.id.action0, action.getTitle());
        }
        return remoteViews;
    }

    @RequiresApi(11)
    @TargetApi(11)
    public static <T extends NotificationCompatBase.Action> RemoteViews generateMediaBigView(Context context, CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, int i, Bitmap bitmap, CharSequence charSequence4, boolean z, long j, int i2, int i3, List<T> list, boolean z2, PendingIntent pendingIntent, boolean z3) throws Resources.NotFoundException {
        int iMin = Math.min(list.size(), 5);
        RemoteViews remoteViewsApplyStandardTemplate = applyStandardTemplate(context, charSequence, charSequence2, charSequence3, i, 0, bitmap, charSequence4, z, j, i2, i3, getBigMediaLayoutResource(z3, iMin), false);
        remoteViewsApplyStandardTemplate.removeAllViews(R.id.media_actions);
        if (iMin > 0) {
            for (int i4 = 0; i4 < iMin; i4++) {
                remoteViewsApplyStandardTemplate.addView(R.id.media_actions, generateMediaActionButton(context, list.get(i4)));
            }
        }
        if (!z2) {
            remoteViewsApplyStandardTemplate.setViewVisibility(R.id.cancel_action, 8);
            return remoteViewsApplyStandardTemplate;
        }
        remoteViewsApplyStandardTemplate.setViewVisibility(R.id.cancel_action, 0);
        remoteViewsApplyStandardTemplate.setInt(R.id.cancel_action, "setAlpha", context.getResources().getInteger(R.integer.cancel_button_image_alpha));
        remoteViewsApplyStandardTemplate.setOnClickPendingIntent(R.id.cancel_action, pendingIntent);
        return remoteViewsApplyStandardTemplate;
    }

    private static int getActionLayoutResource() {
        return R.layout.notification_action;
    }

    private static int getActionTombstoneLayoutResource() {
        return R.layout.notification_action_tombstone;
    }

    @RequiresApi(11)
    @TargetApi(11)
    private static int getBigMediaLayoutResource(boolean z, int i) {
        return i <= 3 ? z ? R.layout.notification_template_big_media_narrow_custom : R.layout.notification_template_big_media_narrow : z ? R.layout.notification_template_big_media_custom : R.layout.notification_template_big_media;
    }

    private static void hideNormalContent(RemoteViews remoteViews) {
        remoteViews.setViewVisibility(R.id.title, 8);
        remoteViews.setViewVisibility(R.id.text2, 8);
        remoteViews.setViewVisibility(R.id.text, 8);
    }

    @RequiresApi(11)
    @TargetApi(11)
    public static <T extends NotificationCompatBase.Action> RemoteViews overrideContentViewMedia(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor, Context context, CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, int i, Bitmap bitmap, CharSequence charSequence4, boolean z, long j, int i2, List<T> list, int[] iArr, boolean z2, PendingIntent pendingIntent, boolean z3) throws Resources.NotFoundException {
        RemoteViews remoteViewsGenerateContentViewMedia = generateContentViewMedia(context, charSequence, charSequence2, charSequence3, i, bitmap, charSequence4, z, j, i2, list, iArr, z2, pendingIntent, z3);
        notificationBuilderWithBuilderAccessor.getBuilder().setContent(remoteViewsGenerateContentViewMedia);
        if (z2) {
            notificationBuilderWithBuilderAccessor.getBuilder().setOngoing(true);
        }
        return remoteViewsGenerateContentViewMedia;
    }

    @RequiresApi(16)
    @TargetApi(16)
    public static <T extends NotificationCompatBase.Action> void overrideMediaBigContentView(Notification notification, Context context, CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, int i, Bitmap bitmap, CharSequence charSequence4, boolean z, long j, int i2, int i3, List<T> list, boolean z2, PendingIntent pendingIntent, boolean z3) {
        notification.bigContentView = generateMediaBigView(context, charSequence, charSequence2, charSequence3, i, bitmap, charSequence4, z, j, i2, i3, list, z2, pendingIntent, z3);
        if (z2) {
            notification.flags |= 2;
        }
    }
}
