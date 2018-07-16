package com.bsaugues.passmanager.presentation.di.module;

import android.app.Application;
import android.content.Context;

import com.bsaugues.passmanager.data.manager.NetManager;
import com.bsaugues.passmanager.data.manager.NetManagerImpl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class ApplicationModule {

    @Singleton
    @Binds
    public abstract Context context(Application application);

    @Singleton
    @Binds
    public abstract NetManager netManager(NetManagerImpl netManager);

    @Singleton
    @Provides
    public static Gson gson() {
        return new GsonBuilder().create();
    }
}
