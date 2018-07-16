package com.bsaugues.passmanager.presentation.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.util.Log;

import javax.inject.Inject;

public class MainViewModel extends ViewModel {

    private static final String TAG = "CodeScannerViewModel";

    @Inject
    public MainViewModel() {
        //Intentionally empty
    }

    @Override
    protected void onCleared() {
        Log.d(TAG, "Activity destroyed, event received in " + this);
        super.onCleared();
    }
}
