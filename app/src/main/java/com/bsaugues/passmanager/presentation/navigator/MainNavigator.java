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

    private BottomSheetListener bottomSheetListener;

    @Inject
    public MainNavigator(BaseActivity activity, FragmentManager fragmentManager, SingleLiveEvent<NavEvent> navEvent) {
        super(activity, fragmentManager, navEvent);
    }

    public void displayCodeScannerFragment() {
        Fragment fragment = CodeScannerFragment.newInstance();
        updateListeners(fragment);
        fragmentManager.beginTransaction().replace(R.id.activity_main_container, fragment).commit();
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
            case NOTIFY_BOTTOM_SHEET_CLOSED:
                notifyBottomSheetDismissed();
                break;
            default:
                break;
        }
    }

    private void updateListeners(Fragment fragment) {
        if (fragment instanceof BottomSheetListener) {
            bottomSheetListener = (BottomSheetListener) fragment;
        } else {
            bottomSheetListener = null;
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

    private void notifyBottomSheetDismissed() {
        if (bottomSheetListener != null) {
            bottomSheetListener.onBottomSheetDismissed();
        }
    }
}
