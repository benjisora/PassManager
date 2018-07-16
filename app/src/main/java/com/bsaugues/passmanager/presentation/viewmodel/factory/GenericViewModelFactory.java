package com.bsaugues.passmanager.presentation.viewmodel.factory;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

@Singleton
public class GenericViewModelFactory implements ViewModelProvider.Factory {

    private Map<Class<? extends ViewModel>, Provider<ViewModel>> creators;

    @Inject
    public GenericViewModelFactory() {
        //Intentionally empty
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        Provider<ViewModel> creator = creators.get(modelClass);
        if (creator == null) {
            for (Map.Entry<Class<? extends ViewModel>, Provider<ViewModel>> entry : creators.entrySet()) {
                if (modelClass.isAssignableFrom(entry.getKey())) {
                    creator = entry.getValue();
                    break;
                }
            }
        }
        if (creator == null) {
            throw new IllegalArgumentException("Unknown model class " + modelClass);
        }
        return (T) creator.get();
    }

    public <T extends ViewModel> T create(Class<T> modelClass, Map<Class<? extends ViewModel>, Provider<ViewModel>> creators) {
        this.creators = creators;
        return create(modelClass);
    }
}
