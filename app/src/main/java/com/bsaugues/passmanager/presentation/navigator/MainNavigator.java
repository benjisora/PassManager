package com.bsaugues.passmanager.presentation.navigator;


import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.bsaugues.passmanager.R;
import com.bsaugues.passmanager.data.entity.SingleLiveEvent;
import com.bsaugues.passmanager.data.values.nav.NavEvent;
import com.bsaugues.passmanager.presentation.di.annotation.PerActivity;
import com.bsaugues.passmanager.presentation.ui.activity.BaseActivity;
import com.bsaugues.passmanager.presentation.ui.fragment.CodeScannerFragment;
import com.bsaugues.passmanager.presentation.ui.fragment.ReservationDetailsFragment;
import com.bsaugues.passmanager.presentation.ui.fragment.listener.BottomSheetListener;

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

    @Override
    void onNavEventReceived(NavEvent navEvent) {
        switch (navEvent.getType()) {
            case DISPLAY_RESERVATION_DETAILS:
                showReservationDetails();
                break;
            case OPEN_APP_SETTINGS:
                openAppSettings();
                break;
            default:
                break;
        }
    }

    private void showReservationDetails() {
        ReservationDetailsFragment.newInstance().show(fragmentManager, ReservationDetailsFragment.class.getName());
    }

    private void openAppSettings() {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
        intent.setData(uri);
        activity.startActivity(intent);
    }

}
