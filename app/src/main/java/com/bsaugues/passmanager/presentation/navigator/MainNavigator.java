package com.bsaugues.passmanager.presentation.navigator;


import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.FragmentManager;

import com.bsaugues.passmanager.R;
import com.bsaugues.passmanager.presentation.di.annotation.PerActivity;
import com.bsaugues.passmanager.presentation.ui.activity.BaseActivity;
import com.bsaugues.passmanager.presentation.ui.fragment.CodeScannerFragment;

import javax.inject.Inject;

@PerActivity
public class MainNavigator {

    private BaseActivity activity;
    private FragmentManager fragmentManager;

    @Inject
    public MainNavigator(BaseActivity activity, FragmentManager fragmentManager) {
        this.activity = activity;
        this.fragmentManager = fragmentManager;
    }

    public void displayCodeScannerFragment() {
        fragmentManager.beginTransaction().replace(R.id.main_container, CodeScannerFragment.newInstance()).commit();
    }

    public void closeActivity() {
        activity.finish();
    }

    public void openAppSettings() {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
        intent.setData(uri);
        activity.startActivity(intent);
    }

}
