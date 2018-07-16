package com.bsaugues.passmanager.presentation.di.module.activity;

import com.bsaugues.passmanager.presentation.di.annotation.PerActivity;
import com.bsaugues.passmanager.presentation.ui.activity.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityInjectorModule {

    @PerActivity
    @ContributesAndroidInjector(modules = {MainActivityModule.class, ActivityViewModelModule.class})
    abstract MainActivity mainActivityInjector();
}
