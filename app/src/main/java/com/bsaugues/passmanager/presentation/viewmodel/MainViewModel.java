package com.bsaugues.passmanager.presentation.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.util.Log;

import javax.inject.Inject;

import timber.log.Timber;

public class MainViewModel extends ViewModel {

    @Inject
    public MainViewModel() {
        //Intentionally empty
    }

    @Override
    protected void onCleared() {
        Timber.d("Activity destroyed, event received in %s", this);
        super.onCleared();
    }
}
