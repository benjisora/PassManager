package com.bsaugues.passmanager.presentation.di.module.activity;

import android.support.v4.app.FragmentManager;

import com.bsaugues.passmanager.presentation.di.annotation.PerActivity;
import com.bsaugues.passmanager.presentation.ui.activity.BaseActivity;

import dagger.Module;
import dagger.Provides;

@Module
public class BaseActivityModule {

    @PerActivity
    @Provides
    public FragmentManager provideSupportFragmentManager(BaseActivity activity) {
        return activity.getSupportFragmentManager();
    }
}
