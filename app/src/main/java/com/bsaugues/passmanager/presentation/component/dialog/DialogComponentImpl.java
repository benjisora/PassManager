package com.bsaugues.passmanager.presentation.component.dialog;

import android.support.annotation.NonNull;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bsaugues.passmanager.presentation.di.annotation.PerActivity;
import com.bsaugues.passmanager.presentation.ui.activity.BaseActivity;

import javax.inject.Inject;

@PerActivity
public class DialogComponentImpl implements DialogComponent {

    private final BaseActivity activity;

    private MaterialDialog materialDialog;

    @Inject
    public DialogComponentImpl(BaseActivity activity) {
        this.activity = activity;
    }

    @Override
    public void dismissDialog() {
        if (materialDialog != null && materialDialog.isShowing()) {
            materialDialog.dismiss();
        }
    }

    @Override
    public void displaySingleChoiceDialog(final SingleChoiceListener dualChoiceListener, int title, int content, int positiveText) {
        materialDialog = new MaterialDialog.Builder(activity)
                .title(title)
                .content(content)
                .positiveText(positiveText)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dualChoiceListener.onPositive();
                    }
                })
                .show();
    }

    @Override
    public void displaySingleChoiceForcedDialog(final SingleChoiceListener singleChoiceListener, int title, int content, int positiveText) {
        materialDialog = new MaterialDialog.Builder(activity)
                .title(title)
                .content(content)
                .positiveText(positiveText)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        singleChoiceListener.onPositive();
                    }
                })
                .cancelable(false)
                .show();
    }

    @Override
    public void displayDualChoiceDialog(final DualChoiceListener dualChoiceListener, int title, int content, int positiveText, int negativeText) {
        materialDialog = new MaterialDialog.Builder(activity)
                .title(title)
                .content(content)
                .positiveText(positiveText)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dualChoiceListener.onPositive();
                    }
                })
                .negativeText(negativeText)
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dualChoiceListener.onNegative();
                    }
                })
                .show();
    }

    @Override
    public void displayDualChoiceForcedDialog(final DualChoiceListener dualChoiceListener, int title, int content, int positiveText, int negativeText) {
        materialDialog = new MaterialDialog.Builder(activity)
                .title(title)
                .content(content)
                .positiveText(positiveText)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dualChoiceListener.onPositive();
                    }
                })
                .negativeText(negativeText)
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dualChoiceListener.onNegative();
                    }
                })
                .cancelable(false)
                .show();
    }

    @Override
    public boolean isDialogDisplayed() {
        return materialDialog != null && materialDialog.isShowing();
    }
}
