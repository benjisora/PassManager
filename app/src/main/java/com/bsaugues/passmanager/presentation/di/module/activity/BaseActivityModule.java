package com.bsaugues.passmanager.presentation.di.module.activity;

import android.support.v4.app.FragmentManager;

import com.bsaugues.passmanager.data.entity.SingleLiveEvent;
import com.bsaugues.passmanager.data.values.nav.NavEvent;
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

    @PerActivity
    @Provides
    public SingleLiveEvent<NavEvent> bindNavEvent() {
        return new SingleLiveEvent<>();
    }
}
