package com.bsaugues.passmanager.presentation.navigator;


import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.FragmentManager;

import com.bsaugues.passmanager.R;
import com.bsaugues.passmanager.data.entity.SingleLiveEvent;
import com.bsaugues.passmanager.data.values.nav.NavEvent;
import com.bsaugues.passmanager.presentation.di.annotation.PerActivity;
import com.bsaugues.passmanager.presentation.ui.activity.BaseActivity;
import com.bsaugues.passmanager.presentation.ui.fragment.CodeScannerFragment;
import com.bsaugues.passmanager.presentation.ui.fragment.ReservationDetailsFragment;

import javax.inject.Inject;

@PerActivity
public class MainNavigator extends BaseNavigator {

    @Inject
    public MainNavigator(BaseActivity activity, FragmentManager fragmentManager, SingleLiveEvent<NavEvent> navEvent) {
        super(activity, fragmentManager, navEvent);
    }

    public void displayCodeScannerFragment() {
        fragmentManager.beginTransaction().replace(R.id.activity_main_container, CodeScannerFragment.newInstance()).commit();
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

    @Override
    void onNavEventReceived(NavEvent navEvent) {
        switch (navEvent.getType()) {
            case DISPLAY_RESERVATION_DETAILS:
                ReservationDetailsFragment.newInstance().show(fragmentManager, ReservationDetailsFragment.class.getName());
                break;
            default:
                break;
        }
    }
}
