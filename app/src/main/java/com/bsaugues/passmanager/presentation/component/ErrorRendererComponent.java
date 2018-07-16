package com.bsaugues.passmanager.presentation.component;

import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bsaugues.passmanager.presentation.di.annotation.PerActivity;
import com.bsaugues.passmanager.presentation.ui.activity.BaseActivity;
import com.bsaugues.passmanager.data.values.ErrorUtils;

import javax.inject.Inject;

import timber.log.Timber;

@PerActivity
public class ErrorRendererComponent {

    public static final int ERROR_DISPLAY_MODE_NONE = 1;
    public static final int ERROR_DISPLAY_MODE_SNACKBAR = 2;
    public static final int ERROR_DISPLAY_MODE_TOAST = 3;

    private Snackbar snackbar;

    private BaseActivity activity;

    @Inject
    public ErrorRendererComponent(BaseActivity baseActivity) {
        this.activity = baseActivity;
    }

    public void displayError(Throwable throwable) {
        snackbar = Snackbar.make(activity.findViewById(android.R.id.content), ErrorUtils.translateException(activity, throwable), Snackbar.LENGTH_LONG);
        snackbar.setActionTextColor(Color.WHITE);
        snackbar.getView().setBackgroundColor(ContextCompat.getColor(activity, android.R.color.holo_red_light));

        View yourSnackBarView = snackbar.getView();
        TextView textView = yourSnackBarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(activity, android.R.color.white));
        textView.setMaxLines(3);
        snackbar.show();
        Timber.e(throwable);
    }

    public void displayError(String error) {
        snackbar = Snackbar.make(activity.findViewById(android.R.id.content), error, Snackbar.LENGTH_LONG);
        snackbar.setActionTextColor(Color.WHITE);
        snackbar.getView().setBackgroundColor(ContextCompat.getColor(activity, android.R.color.holo_red_light));

        View yourSnackBarView = snackbar.getView();
        TextView textView = yourSnackBarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(activity, android.R.color.white));
        textView.setMaxLines(3);
        snackbar.show();
    }

    public void displayToast(Throwable throwable) {
        Toast.makeText(activity, ErrorUtils.translateException(activity, throwable), Toast.LENGTH_SHORT).show();
        Timber.e(throwable);
    }

    public void displayToast(String error) {
        Toast.makeText(activity, error, Toast.LENGTH_SHORT).show();
    }

    public void dismissSnackBar() {
        if (snackbar != null && snackbar.isShown()) {
            snackbar.dismiss();
        }
    }
}
