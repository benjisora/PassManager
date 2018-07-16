package com.bsaugues.passmanager.presentation.ui.activity;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;

import com.bsaugues.passmanager.presentation.viewmodel.factory.ActivityViewModelFactory;

import javax.inject.Inject;

public abstract class BaseVMActivity<T extends ViewModel> extends BaseActivity {

    @Inject
    ActivityViewModelFactory viewModelFactory;

    protected T viewModel;

    @CallSuper
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(getViewModelClass());
        initObservers();
    }

    abstract Class<T> getViewModelClass();

    abstract void initObservers();
}
