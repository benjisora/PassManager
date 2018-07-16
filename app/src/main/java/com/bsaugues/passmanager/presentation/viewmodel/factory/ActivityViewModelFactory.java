package com.bsaugues.passmanager.presentation.viewmodel.factory;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.bsaugues.passmanager.presentation.di.annotation.PerActivity;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;

@PerActivity
public class ActivityViewModelFactory implements ViewModelProvider.Factory {

    private Map<Class<? extends ViewModel>, Provider<ViewModel>> creators;
    private GenericViewModelFactory genericViewModelFactory;

    @Inject
    public ActivityViewModelFactory(Map<Class<? extends ViewModel>, Provider<ViewModel>> creators, GenericViewModelFactory genericViewModelFactory) {
        this.creators = creators;
        this.genericViewModelFactory = genericViewModelFactory;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return genericViewModelFactory.create(modelClass, creators);
    }
}
