package com.bsaugues.passmanager.presentation.di.module.activity;

import com.bsaugues.passmanager.presentation.component.dialog.DialogComponent;
import com.bsaugues.passmanager.presentation.component.dialog.DialogComponentImpl;
import com.bsaugues.passmanager.presentation.di.annotation.PerActivity;
import com.bsaugues.passmanager.presentation.di.annotation.PerFragment;
import com.bsaugues.passmanager.presentation.di.module.fragment.FragmentViewModelModule;
import com.bsaugues.passmanager.presentation.ui.activity.BaseActivity;
import com.bsaugues.passmanager.presentation.ui.activity.MainActivity;
import com.bsaugues.passmanager.presentation.ui.fragment.CodeScannerFragment;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module(includes = {BaseActivityModule.class})
public abstract class MainActivityModule {

    @PerActivity
    @Binds
    public abstract BaseActivity baseActivity(MainActivity mainActivity);

    @PerActivity
    @Binds
    public abstract DialogComponent dialogComponent(DialogComponentImpl dialogComponent);

    @PerFragment
    @ContributesAndroidInjector(modules = {FragmentViewModelModule.class})
    public abstract CodeScannerFragment numberListFragmentInjector();

}
