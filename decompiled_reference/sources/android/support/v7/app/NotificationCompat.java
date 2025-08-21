package android.support.v7.app;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.v4.app.BundleCompat;
import android.support.v4.app.NotificationBuilderWithBuilderAccessor;
import android.support.v4.app.NotificationCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.text.BidiFormatter;
import android.support.v4.view.ViewCompat;
import android.support.v7.appcompat.R;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.TextAppearanceSpan;
import android.widget.RemoteViews;
import java.util.List;

/* loaded from: classes.dex */
public class NotificationCompat extends android.support.v4.app.NotificationCompat {

    private static class Api24Extender extends NotificationCompat.BuilderExtender {
        private Api24Extender() {
        }

        @Override // android.support.v4.app.NotificationCompat.BuilderExtender
        public Notification build(NotificationCompat.Builder builder, NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) throws Resources.NotFoundException {
            NotificationCompat.addStyleToBuilderApi24(notificationBuilderWithBuilderAccessor, builder);
            return notificationBuilderWithBuilderAccessor.build();
        }
    }

    public static class Builder extends NotificationCompat.Builder {
        public Builder(Context context) {
            super(context);
        }

        @Override // android.support.v4.app.NotificationCompat.Builder
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        protected NotificationCompat.BuilderExtender getExtender() {
            return Build.VERSION.SDK_INT >= 24 ? new Api24Extender() : Build.VERSION.SDK_INT >= 21 ? new LollipopExtender() : Build.VERSION.SDK_INT >= 16 ? new JellybeanExtender() : Build.VERSION.SDK_INT >= 14 ? new IceCreamSandwichExtender() : super.getExtender();
        }

        @Override // android.support.v4.app.NotificationCompat.Builder
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        protected CharSequence resolveText() {
            if (this.mStyle instanceof NotificationCompat.MessagingStyle) {
                NotificationCompat.MessagingStyle messagingStyle = (NotificationCompat.MessagingStyle) this.mStyle;
                NotificationCompat.MessagingStyle.Message messageFindLatestIncomingMessage = NotificationCompat.findLatestIncomingMessage(messagingStyle);
                CharSequence conversationTitle = messagingStyle.getConversationTitle();
                if (messageFindLatestIncomingMessage != null) {
                    return conversationTitle != null ? NotificationCompat.makeMessageLine(this, messagingStyle, messageFindLatestIncomingMessage) : messageFindLatestIncomingMessage.getText();
                }
            }
            return super.resolveText();
        }

        @Override // android.support.v4.app.NotificationCompat.Builder
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        protected CharSequence resolveTitle() {
            if (this.mStyle instanceof NotificationCompat.MessagingStyle) {
                NotificationCompat.MessagingStyle messagingStyle = (NotificationCompat.MessagingStyle) this.mStyle;
                NotificationCompat.MessagingStyle.Message messageFindLatestIncomingMessage = NotificationCompat.findLatestIncomingMessage(messagingStyle);
                CharSequence conversationTitle = messagingStyle.getConversationTitle();
                if (conversationTitle != null || messageFindLatestIncomingMessage != null) {
                    return conversationTitle != null ? conversationTitle : messageFindLatestIncomingMessage.getSender();
                }
            }
            return super.resolveTitle();
        }
    }

    public static class DecoratedCustomViewStyle extends NotificationCompat.Style {
    }

    public static class DecoratedMediaCustomViewStyle extends MediaStyle {
    }

    private static class IceCreamSandwichExtender extends NotificationCompat.BuilderExtender {
        IceCreamSandwichExtender() {
        }

        @Override // android.support.v4.app.NotificationCompat.BuilderExtender
        public Notification build(NotificationCompat.Builder builder, NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) throws Resources.NotFoundException {
            RemoteViews remoteViewsAddStyleGetContentViewIcs = NotificationCompat.addStyleGetContentViewIcs(notificationBuilderWithBuilderAccessor, builder);
            Notification notificationBuild = notificationBuilderWithBuilderAccessor.build();
            if (remoteViewsAddStyleGetContentViewIcs == null) {
                if (builder.getContentView() == null) {
                    return notificationBuild;
                }
                remoteViewsAddStyleGetContentViewIcs = builder.getContentView();
            }
            notificationBuild.contentView = remoteViewsAddStyleGetContentViewIcs;
            return notificationBuild;
        }
    }

    private static class JellybeanExtender extends NotificationCompat.BuilderExtender {
        JellybeanExtender() {
        }

        @Override // android.support.v4.app.NotificationCompat.BuilderExtender
        public Notification build(NotificationCompat.Builder builder, NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) throws Resources.NotFoundException {
            RemoteViews remoteViewsAddStyleGetContentViewJellybean = NotificationCompat.addStyleGetContentViewJellybean(notificationBuilderWithBuilderAccessor, builder);
            Notification notificationBuild = notificationBuilderWithBuilderAccessor.build();
            if (remoteViewsAddStyleGetContentViewJellybean != null) {
                notificationBuild.contentView = remoteViewsAddStyleGetContentViewJellybean;
            }
            NotificationCompat.addBigStyleToBuilderJellybean(notificationBuild, builder);
            return notificationBuild;
        }
    }

    private static class LollipopExtender extends NotificationCompat.BuilderExtender {
        LollipopExtender() {
        }

        @Override // android.support.v4.app.NotificationCompat.BuilderExtender
        public Notification build(NotificationCompat.Builder builder, NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) throws Resources.NotFoundException {
            RemoteViews remoteViewsAddStyleGetContentViewLollipop = NotificationCompat.addStyleGetContentViewLollipop(notificationBuilderWithBuilderAccessor, builder);
            Notification notificationBuild = notificationBuilderWithBuilderAccessor.build();
            if (remoteViewsAddStyleGetContentViewLollipop != null) {
                notificationBuild.contentView = remoteViewsAddStyleGetContentViewLollipop;
            }
            NotificationCompat.addBigStyleToBuilderLollipop(notificationBuild, builder);
            NotificationCompat.addHeadsUpToBuilderLollipop(notificationBuild, builder);
            return notificationBuild;
        }
    }

    public static class MediaStyle extends NotificationCompat.Style {
        int[] mActionsToShowInCompact = null;
        PendingIntent mCancelButtonIntent;
        boolean mShowCancelButton;
        MediaSessionCompat.Token mToken;

        public MediaStyle() {
        }

        public MediaStyle(NotificationCompat.Builder builder) {
            setBuilder(builder);
        }

        public MediaStyle setCancelButtonIntent(PendingIntent pendingIntent) {
            this.mCancelButtonIntent = pendingIntent;
            return this;
        }

        public MediaStyle setMediaSession(MediaSessionCompat.Token token) {
            this.mToken = token;
            return this;
        }

        public MediaStyle setShowActionsInCompactView(int... iArr) {
            this.mActionsToShowInCompact = iArr;
            return this;
        }

        public MediaStyle setShowCancelButton(boolean z) {
            this.mShowCancelButton = z;
            return this;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @RequiresApi(16)
    @TargetApi(16)
    public static void addBigStyleToBuilderJellybean(Notification notification, NotificationCompat.Builder builder) throws Resources.NotFoundException {
        if (!(builder.mStyle instanceof MediaStyle)) {
            if (builder.mStyle instanceof DecoratedCustomViewStyle) {
                addDecoratedBigStyleToBuilderJellybean(notification, builder);
                return;
            }
            return;
        }
        MediaStyle mediaStyle = (MediaStyle) builder.mStyle;
        RemoteViews bigContentView = builder.getBigContentView() != null ? builder.getBigContentView() : builder.getContentView();
        boolean z = (builder.mStyle instanceof DecoratedMediaCustomViewStyle) && bigContentView != null;
        RemoteViews remoteViews = bigContentView;
        NotificationCompatImplBase.overrideMediaBigContentView(notification, builder.mContext, builder.mContentTitle, builder.mContentText, builder.mContentInfo, builder.mNumber, builder.mLargeIcon, builder.mSubText, builder.mUseChronometer, builder.getWhenIfShowing(), builder.getPriority(), 0, builder.mActions, mediaStyle.mShowCancelButton, mediaStyle.mCancelButtonIntent, z);
        if (z) {
            NotificationCompatImplBase.buildIntoRemoteViews(builder.mContext, notification.bigContentView, remoteViews);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @RequiresApi(21)
    @TargetApi(21)
    public static void addBigStyleToBuilderLollipop(Notification notification, NotificationCompat.Builder builder) throws Resources.NotFoundException {
        RemoteViews bigContentView = builder.getBigContentView() != null ? builder.getBigContentView() : builder.getContentView();
        if (!(builder.mStyle instanceof DecoratedMediaCustomViewStyle) || bigContentView == null) {
            if (builder.mStyle instanceof DecoratedCustomViewStyle) {
                addDecoratedBigStyleToBuilderJellybean(notification, builder);
            }
        } else {
            NotificationCompatImplBase.overrideMediaBigContentView(notification, builder.mContext, builder.mContentTitle, builder.mContentText, builder.mContentInfo, builder.mNumber, builder.mLargeIcon, builder.mSubText, builder.mUseChronometer, builder.getWhenIfShowing(), builder.getPriority(), 0, builder.mActions, false, null, true);
            NotificationCompatImplBase.buildIntoRemoteViews(builder.mContext, notification.bigContentView, bigContentView);
            setBackgroundColor(builder.mContext, notification.bigContentView, builder.getColor());
        }
    }

    @RequiresApi(16)
    @TargetApi(16)
    private static void addDecoratedBigStyleToBuilderJellybean(Notification notification, NotificationCompat.Builder builder) throws Resources.NotFoundException {
        RemoteViews bigContentView = builder.getBigContentView();
        if (bigContentView == null) {
            bigContentView = builder.getContentView();
        }
        if (bigContentView == null) {
            return;
        }
        RemoteViews remoteViewsApplyStandardTemplateWithActions = NotificationCompatImplBase.applyStandardTemplateWithActions(builder.mContext, builder.mContentTitle, builder.mContentText, builder.mContentInfo, builder.mNumber, notification.icon, builder.mLargeIcon, builder.mSubText, builder.mUseChronometer, builder.getWhenIfShowing(), builder.getPriority(), builder.getColor(), R.layout.notification_template_custom_big, false, builder.mActions);
        NotificationCompatImplBase.buildIntoRemoteViews(builder.mContext, remoteViewsApplyStandardTemplateWithActions, bigContentView);
        notification.bigContentView = remoteViewsApplyStandardTemplateWithActions;
    }

    @RequiresApi(21)
    @TargetApi(21)
    private static void addDecoratedHeadsUpToBuilderLollipop(Notification notification, NotificationCompat.Builder builder) throws Resources.NotFoundException {
        RemoteViews headsUpContentView = builder.getHeadsUpContentView();
        RemoteViews contentView = headsUpContentView != null ? headsUpContentView : builder.getContentView();
        if (headsUpContentView == null) {
            return;
        }
        RemoteViews remoteViewsApplyStandardTemplateWithActions = NotificationCompatImplBase.applyStandardTemplateWithActions(builder.mContext, builder.mContentTitle, builder.mContentText, builder.mContentInfo, builder.mNumber, notification.icon, builder.mLargeIcon, builder.mSubText, builder.mUseChronometer, builder.getWhenIfShowing(), builder.getPriority(), builder.getColor(), R.layout.notification_template_custom_big, false, builder.mActions);
        NotificationCompatImplBase.buildIntoRemoteViews(builder.mContext, remoteViewsApplyStandardTemplateWithActions, contentView);
        notification.headsUpContentView = remoteViewsApplyStandardTemplateWithActions;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @RequiresApi(21)
    @TargetApi(21)
    public static void addHeadsUpToBuilderLollipop(Notification notification, NotificationCompat.Builder builder) throws Resources.NotFoundException {
        RemoteViews headsUpContentView = builder.getHeadsUpContentView() != null ? builder.getHeadsUpContentView() : builder.getContentView();
        if (!(builder.mStyle instanceof DecoratedMediaCustomViewStyle) || headsUpContentView == null) {
            if (builder.mStyle instanceof DecoratedCustomViewStyle) {
                addDecoratedHeadsUpToBuilderLollipop(notification, builder);
            }
        } else {
            notification.headsUpContentView = NotificationCompatImplBase.generateMediaBigView(builder.mContext, builder.mContentTitle, builder.mContentText, builder.mContentInfo, builder.mNumber, builder.mLargeIcon, builder.mSubText, builder.mUseChronometer, builder.getWhenIfShowing(), builder.getPriority(), 0, builder.mActions, false, null, true);
            NotificationCompatImplBase.buildIntoRemoteViews(builder.mContext, notification.headsUpContentView, headsUpContentView);
            setBackgroundColor(builder.mContext, notification.headsUpContentView, builder.getColor());
        }
    }

    private static void addMessagingFallBackStyle(NotificationCompat.MessagingStyle messagingStyle, NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor, NotificationCompat.Builder builder) {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        List<NotificationCompat.MessagingStyle.Message> messages = messagingStyle.getMessages();
        boolean z = messagingStyle.getConversationTitle() != null || hasMessagesWithoutSender(messagingStyle.getMessages());
        for (int size = messages.size() - 1; size >= 0; size--) {
            NotificationCompat.MessagingStyle.Message message = messages.get(size);
            CharSequence charSequenceMakeMessageLine = z ? makeMessageLine(builder, messagingStyle, message) : message.getText();
            if (size != messages.size() - 1) {
                spannableStringBuilder.insert(0, (CharSequence) "\n");
            }
            spannableStringBuilder.insert(0, charSequenceMakeMessageLine);
        }
        NotificationCompatImplJellybean.addBigTextStyle(notificationBuilderWithBuilderAccessor, spannableStringBuilder);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @RequiresApi(14)
    @TargetApi(14)
    public static RemoteViews addStyleGetContentViewIcs(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor, NotificationCompat.Builder builder) throws Resources.NotFoundException {
        if (!(builder.mStyle instanceof MediaStyle)) {
            if (builder.mStyle instanceof DecoratedCustomViewStyle) {
                return getDecoratedContentView(builder);
            }
            return null;
        }
        MediaStyle mediaStyle = (MediaStyle) builder.mStyle;
        boolean z = (builder.mStyle instanceof DecoratedMediaCustomViewStyle) && builder.getContentView() != null;
        RemoteViews remoteViewsOverrideContentViewMedia = NotificationCompatImplBase.overrideContentViewMedia(notificationBuilderWithBuilderAccessor, builder.mContext, builder.mContentTitle, builder.mContentText, builder.mContentInfo, builder.mNumber, builder.mLargeIcon, builder.mSubText, builder.mUseChronometer, builder.getWhenIfShowing(), builder.getPriority(), builder.mActions, mediaStyle.mActionsToShowInCompact, mediaStyle.mShowCancelButton, mediaStyle.mCancelButtonIntent, z);
        if (!z) {
            return null;
        }
        NotificationCompatImplBase.buildIntoRemoteViews(builder.mContext, remoteViewsOverrideContentViewMedia, builder.getContentView());
        return remoteViewsOverrideContentViewMedia;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @RequiresApi(16)
    @TargetApi(16)
    public static RemoteViews addStyleGetContentViewJellybean(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor, NotificationCompat.Builder builder) {
        if (builder.mStyle instanceof NotificationCompat.MessagingStyle) {
            addMessagingFallBackStyle((NotificationCompat.MessagingStyle) builder.mStyle, notificationBuilderWithBuilderAccessor, builder);
        }
        return addStyleGetContentViewIcs(notificationBuilderWithBuilderAccessor, builder);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @RequiresApi(21)
    @TargetApi(21)
    public static RemoteViews addStyleGetContentViewLollipop(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor, NotificationCompat.Builder builder) throws Resources.NotFoundException {
        NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor2;
        Object token;
        if (!(builder.mStyle instanceof MediaStyle)) {
            return builder.mStyle instanceof DecoratedCustomViewStyle ? getDecoratedContentView(builder) : addStyleGetContentViewJellybean(notificationBuilderWithBuilderAccessor, builder);
        }
        MediaStyle mediaStyle = (MediaStyle) builder.mStyle;
        int[] iArr = mediaStyle.mActionsToShowInCompact;
        if (mediaStyle.mToken != null) {
            token = mediaStyle.mToken.getToken();
            notificationBuilderWithBuilderAccessor2 = notificationBuilderWithBuilderAccessor;
        } else {
            notificationBuilderWithBuilderAccessor2 = notificationBuilderWithBuilderAccessor;
            token = null;
        }
        NotificationCompatImpl21.addMediaStyle(notificationBuilderWithBuilderAccessor2, iArr, token);
        boolean z = false;
        boolean z2 = builder.getContentView() != null;
        boolean z3 = Build.VERSION.SDK_INT >= 21 && Build.VERSION.SDK_INT <= 23;
        if (z2 || (z3 && builder.getBigContentView() != null)) {
            z = true;
        }
        if (!(builder.mStyle instanceof DecoratedMediaCustomViewStyle) || !z) {
            return null;
        }
        RemoteViews remoteViewsOverrideContentViewMedia = NotificationCompatImplBase.overrideContentViewMedia(notificationBuilderWithBuilderAccessor2, builder.mContext, builder.mContentTitle, builder.mContentText, builder.mContentInfo, builder.mNumber, builder.mLargeIcon, builder.mSubText, builder.mUseChronometer, builder.getWhenIfShowing(), builder.getPriority(), builder.mActions, mediaStyle.mActionsToShowInCompact, false, null, z2);
        if (z2) {
            NotificationCompatImplBase.buildIntoRemoteViews(builder.mContext, remoteViewsOverrideContentViewMedia, builder.getContentView());
        }
        setBackgroundColor(builder.mContext, remoteViewsOverrideContentViewMedia, builder.getColor());
        return remoteViewsOverrideContentViewMedia;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @RequiresApi(24)
    @TargetApi(24)
    public static void addStyleToBuilderApi24(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor, NotificationCompat.Builder builder) throws Resources.NotFoundException {
        if (builder.mStyle instanceof DecoratedCustomViewStyle) {
            NotificationCompatImpl24.addDecoratedCustomViewStyle(notificationBuilderWithBuilderAccessor);
        } else if (builder.mStyle instanceof DecoratedMediaCustomViewStyle) {
            NotificationCompatImpl24.addDecoratedMediaCustomViewStyle(notificationBuilderWithBuilderAccessor);
        } else {
            if (builder.mStyle instanceof NotificationCompat.MessagingStyle) {
                return;
            }
            addStyleGetContentViewLollipop(notificationBuilderWithBuilderAccessor, builder);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static NotificationCompat.MessagingStyle.Message findLatestIncomingMessage(NotificationCompat.MessagingStyle messagingStyle) {
        List<NotificationCompat.MessagingStyle.Message> messages = messagingStyle.getMessages();
        for (int size = messages.size() - 1; size >= 0; size--) {
            NotificationCompat.MessagingStyle.Message message = messages.get(size);
            if (!TextUtils.isEmpty(message.getSender())) {
                return message;
            }
        }
        if (messages.isEmpty()) {
            return null;
        }
        return messages.get(messages.size() - 1);
    }

    private static RemoteViews getDecoratedContentView(NotificationCompat.Builder builder) throws Resources.NotFoundException {
        if (builder.getContentView() == null) {
            return null;
        }
        RemoteViews remoteViewsApplyStandardTemplateWithActions = NotificationCompatImplBase.applyStandardTemplateWithActions(builder.mContext, builder.mContentTitle, builder.mContentText, builder.mContentInfo, builder.mNumber, builder.mNotification.icon, builder.mLargeIcon, builder.mSubText, builder.mUseChronometer, builder.getWhenIfShowing(), builder.getPriority(), builder.getColor(), R.layout.notification_template_custom_big, false, null);
        NotificationCompatImplBase.buildIntoRemoteViews(builder.mContext, remoteViewsApplyStandardTemplateWithActions, builder.getContentView());
        return remoteViewsApplyStandardTemplateWithActions;
    }

    public static MediaSessionCompat.Token getMediaSession(Notification notification) {
        Bundle extras = getExtras(notification);
        if (extras == null) {
            return null;
        }
        if (Build.VERSION.SDK_INT >= 21) {
            Parcelable parcelable = extras.getParcelable(android.support.v4.app.NotificationCompat.EXTRA_MEDIA_SESSION);
            if (parcelable != null) {
                return MediaSessionCompat.Token.fromToken(parcelable);
            }
            return null;
        }
        IBinder binder = BundleCompat.getBinder(extras, android.support.v4.app.NotificationCompat.EXTRA_MEDIA_SESSION);
        if (binder == null) {
            return null;
        }
        Parcel parcelObtain = Parcel.obtain();
        parcelObtain.writeStrongBinder(binder);
        parcelObtain.setDataPosition(0);
        MediaSessionCompat.Token tokenCreateFromParcel = MediaSessionCompat.Token.CREATOR.createFromParcel(parcelObtain);
        parcelObtain.recycle();
        return tokenCreateFromParcel;
    }

    private static boolean hasMessagesWithoutSender(List<NotificationCompat.MessagingStyle.Message> list) {
        for (int size = list.size() - 1; size >= 0; size--) {
            if (list.get(size).getSender() == null) {
                return true;
            }
        }
        return false;
    }

    private static TextAppearanceSpan makeFontColorSpan(int i) {
        return new TextAppearanceSpan(null, 0, 0, ColorStateList.valueOf(i), null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static CharSequence makeMessageLine(NotificationCompat.Builder builder, NotificationCompat.MessagingStyle messagingStyle, NotificationCompat.MessagingStyle.Message message) {
        BidiFormatter bidiFormatter = BidiFormatter.getInstance();
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        boolean z = Build.VERSION.SDK_INT >= 21;
        int color = (z || Build.VERSION.SDK_INT <= 10) ? ViewCompat.MEASURED_STATE_MASK : -1;
        CharSequence sender = message.getSender();
        if (TextUtils.isEmpty(message.getSender())) {
            sender = messagingStyle.getUserDisplayName() == null ? "" : messagingStyle.getUserDisplayName();
            if (z && builder.getColor() != 0) {
                color = builder.getColor();
            }
        }
        CharSequence charSequenceUnicodeWrap = bidiFormatter.unicodeWrap(sender);
        spannableStringBuilder.append(charSequenceUnicodeWrap);
        spannableStringBuilder.setSpan(makeFontColorSpan(color), spannableStringBuilder.length() - charSequenceUnicodeWrap.length(), spannableStringBuilder.length(), 33);
        spannableStringBuilder.append((CharSequence) "  ").append(bidiFormatter.unicodeWrap(message.getText() == null ? "" : message.getText()));
        return spannableStringBuilder;
    }

    private static void setBackgroundColor(Context context, RemoteViews remoteViews, int i) throws Resources.NotFoundException {
        if (i == 0) {
            i = context.getResources().getColor(R.color.notification_material_background_media_default_color);
        }
        remoteViews.setInt(R.id.status_bar_latest_event_content, "setBackgroundColor", i);
    }
}
