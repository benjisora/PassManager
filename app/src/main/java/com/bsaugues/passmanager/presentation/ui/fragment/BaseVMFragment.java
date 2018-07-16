package com.bsaugues.passmanager.presentation.ui.fragment;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.bsaugues.passmanager.presentation.viewmodel.factory.FragmentViewModelFactory;

import javax.inject.Inject;

public abstract class BaseVMFragment<T extends ViewModel> extends BaseFragment {

    @Inject
    FragmentViewModelFactory viewModelFactory;

    protected T viewModel;

    @CallSuper
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(getViewModelClass());
        initObservers();
    }

    abstract Class<T> getViewModelClass();

    abstract void initObservers();
}
