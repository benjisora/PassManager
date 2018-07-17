package com.bsaugues.passmanager.presentation.component.dialog;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.support.annotation.NonNull;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bsaugues.passmanager.presentation.ui.activity.BaseActivity;

import javax.inject.Inject;

public class DialogComponentImpl implements DialogComponent, LifecycleObserver {

    private final BaseActivity activity;

    private MaterialDialog materialDialog;

    @Inject
    public DialogComponentImpl(BaseActivity activity) {
        this.activity = activity;
        observeLifeCycle();
    }

    @Override
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void dismissDialog() {
        if (isDialogDisplayed()) {
            materialDialog.dismiss();
        }
    }

    @Override
    public void displaySingleChoiceDialog(final SingleChoiceListener dualChoiceListener, int title, int content, int positiveText) {
        dismissDialog();
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
        dismissDialog();
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
        dismissDialog();
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
        dismissDialog();
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

    private void observeLifeCycle() {
        this.activity.getLifecycle().addObserver(this);
    }
}
