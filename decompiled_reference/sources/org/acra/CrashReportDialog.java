package org.acra;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

/* loaded from: classes.dex */
public class CrashReportDialog extends BaseCrashReportDialog implements DialogInterface.OnClickListener, DialogInterface.OnDismissListener {
    private static final String STATE_COMMENT = "comment";
    private static final String STATE_EMAIL = "email";
    AlertDialog mDialog;
    private SharedPreferences prefs;
    private EditText userComment;
    private EditText userEmail;

    protected View buildCustomView(Bundle bundle) {
        EditText editText;
        String string;
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(1);
        linearLayout.setPadding(10, 10, 10, 10);
        linearLayout.setLayoutParams(new ViewGroup.LayoutParams(-1, -2));
        linearLayout.setFocusable(true);
        linearLayout.setFocusableInTouchMode(true);
        ScrollView scrollView = new ScrollView(this);
        linearLayout.addView(scrollView, new LinearLayout.LayoutParams(-1, -1, 1.0f));
        LinearLayout linearLayout2 = new LinearLayout(this);
        linearLayout2.setOrientation(1);
        scrollView.addView(linearLayout2);
        TextView textView = new TextView(this);
        int iResDialogText = ACRA.getConfig().resDialogText();
        if (iResDialogText != 0) {
            textView.setText(getText(iResDialogText));
        }
        linearLayout2.addView(textView);
        int iResDialogCommentPrompt = ACRA.getConfig().resDialogCommentPrompt();
        if (iResDialogCommentPrompt != 0) {
            TextView textView2 = new TextView(this);
            textView2.setText(getText(iResDialogCommentPrompt));
            textView2.setPadding(textView2.getPaddingLeft(), 10, textView2.getPaddingRight(), textView2.getPaddingBottom());
            linearLayout2.addView(textView2, new LinearLayout.LayoutParams(-1, -2));
            this.userComment = new EditText(this);
            this.userComment.setLines(2);
            if (bundle != null && (string = bundle.getString(STATE_COMMENT)) != null) {
                this.userComment.setText(string);
            }
            linearLayout2.addView(this.userComment);
        }
        int iResDialogEmailPrompt = ACRA.getConfig().resDialogEmailPrompt();
        if (iResDialogEmailPrompt != 0) {
            TextView textView3 = new TextView(this);
            textView3.setText(getText(iResDialogEmailPrompt));
            textView3.setPadding(textView3.getPaddingLeft(), 10, textView3.getPaddingRight(), textView3.getPaddingBottom());
            linearLayout2.addView(textView3);
            this.userEmail = new EditText(this);
            this.userEmail.setSingleLine();
            this.userEmail.setInputType(33);
            this.prefs = getSharedPreferences(ACRA.getConfig().sharedPreferencesName(), ACRA.getConfig().sharedPreferencesMode());
            String string2 = bundle != null ? bundle.getString("email") : null;
            if (string2 != null) {
                editText = this.userEmail;
            } else {
                editText = this.userEmail;
                string2 = this.prefs.getString(ACRA.PREF_USER_EMAIL_ADDRESS, "");
            }
            editText.setText(string2);
            linearLayout2.addView(this.userEmail);
        }
        return linearLayout;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public void onClick(DialogInterface dialogInterface, int i) {
        String string;
        if (i == -1) {
            String string2 = this.userComment != null ? this.userComment.getText().toString() : "";
            if (this.prefs == null || this.userEmail == null) {
                string = "";
            } else {
                string = this.userEmail.getText().toString();
                SharedPreferences.Editor editorEdit = this.prefs.edit();
                editorEdit.putString(ACRA.PREF_USER_EMAIL_ADDRESS, string);
                editorEdit.commit();
            }
            sendCrash(string2, string);
        } else {
            cancelReports();
        }
        finish();
    }

    @Override // org.acra.BaseCrashReportDialog, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        int iResDialogTitle = ACRA.getConfig().resDialogTitle();
        if (iResDialogTitle != 0) {
            builder.setTitle(iResDialogTitle);
        }
        int iResDialogIcon = ACRA.getConfig().resDialogIcon();
        if (iResDialogIcon != 0) {
            builder.setIcon(iResDialogIcon);
        }
        builder.setView(buildCustomView(bundle));
        builder.setPositiveButton(getText(ACRA.getConfig().resDialogPositiveButtonText()), this);
        builder.setNegativeButton(getText(ACRA.getConfig().resDialogNegativeButtonText()), this);
        this.mDialog = builder.create();
        this.mDialog.setCanceledOnTouchOutside(false);
        this.mDialog.setOnDismissListener(this);
        this.mDialog.show();
    }

    @Override // android.content.DialogInterface.OnDismissListener
    public void onDismiss(DialogInterface dialogInterface) {
        finish();
    }

    @Override // android.app.Activity
    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        if (this.userComment != null && this.userComment.getText() != null) {
            bundle.putString(STATE_COMMENT, this.userComment.getText().toString());
        }
        if (this.userEmail == null || this.userEmail.getText() == null) {
            return;
        }
        bundle.putString("email", this.userEmail.getText().toString());
    }
}
