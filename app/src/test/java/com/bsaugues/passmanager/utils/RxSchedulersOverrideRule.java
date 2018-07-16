package com.bsaugues.passmanager.utils;

import android.support.annotation.NonNull;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import java.util.concurrent.Callable;

import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.functions.Function;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

public class RxSchedulersOverrideRule implements TestRule {

    private final Function<Callable<Scheduler>, Scheduler> mRxAndroidSchedulersHook =
            new Function<Callable<Scheduler>, Scheduler>() {
                @Override
                public Scheduler apply(@NonNull Callable<Scheduler> schedulerCallable) {
                    return getScheduler();
                }
            };

    private final Function<Scheduler, Scheduler> mRxJavaImmediateScheduler =
            new Function<Scheduler, Scheduler>() {
                @Override
                public Scheduler apply(@NonNull Scheduler scheduler) {
                    return getScheduler();
                }
            };

    @Override
    public Statement apply(final Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                RxAndroidPlugins.reset();
                RxAndroidPlugins.setInitMainThreadSchedulerHandler(mRxAndroidSchedulersHook);

                RxJavaPlugins.reset();
                RxJavaPlugins.setIoSchedulerHandler(mRxJavaImmediateScheduler);
                RxJavaPlugins.setNewThreadSchedulerHandler(mRxJavaImmediateScheduler);

                base.evaluate();

                RxAndroidPlugins.reset();
                RxJavaPlugins.reset();
            }
        };
    }

    private Scheduler getScheduler() {
        return Schedulers.trampoline();
    }
}