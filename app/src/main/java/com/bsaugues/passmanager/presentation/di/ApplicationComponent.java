package com.bsaugues.passmanager.presentation.di;

import android.app.Application;

import com.bsaugues.passmanager.MainApplication;
import com.bsaugues.passmanager.presentation.di.module.ApplicationModule;
import com.bsaugues.passmanager.presentation.di.module.activity.ActivityInjectorModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
        ApplicationModule.class,
        AndroidSupportInjectionModule.class,
        ActivityInjectorModule.class
})
public interface ApplicationComponent {

    void inject(MainApplication mainApplication);

    @Component.Builder
    abstract class Builder {
        @BindsInstance
        public abstract Builder application(Application application);

        public abstract ApplicationComponent build();
    }
}
