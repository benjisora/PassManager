package com.bsaugues.passmanager.presentation.navigator;

import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;

import com.bsaugues.passmanager.data.entity.SingleLiveEvent;
import com.bsaugues.passmanager.data.values.nav.NavEvent;
import com.bsaugues.passmanager.presentation.ui.activity.BaseActivity;

public abstract class BaseNavigator {

    protected BaseActivity activity;
    protected FragmentManager fragmentManager;

    public BaseNavigator(BaseActivity activity, FragmentManager fragmentManager, SingleLiveEvent<NavEvent> navEvent) {
        this.activity = activity;
        this.fragmentManager = fragmentManager;
        observeNavEvents(navEvent);
    }

    private void observeNavEvents(SingleLiveEvent<NavEvent> navEvent) {
        navEvent.observe(activity, new Observer<NavEvent>() {
            @Override
            public void onChanged(@Nullable NavEvent navEvent) {
                onNavEventReceived(navEvent);
            }
        });
    }

    abstract void onNavEventReceived(NavEvent navEvent);
}
