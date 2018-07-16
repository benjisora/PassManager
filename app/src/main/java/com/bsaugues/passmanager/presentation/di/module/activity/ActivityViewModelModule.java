package com.bsaugues.passmanager.presentation.di.module.activity;

import android.arch.lifecycle.ViewModel;

import com.bsaugues.passmanager.presentation.di.annotation.PerActivity;
import com.bsaugues.passmanager.presentation.di.annotation.ViewModelKey;
import com.bsaugues.passmanager.presentation.viewmodel.MainViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ActivityViewModelModule {

    @PerActivity
    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel.class)
    public abstract ViewModel bindMainViewModel(MainViewModel mainViewModel);
}
