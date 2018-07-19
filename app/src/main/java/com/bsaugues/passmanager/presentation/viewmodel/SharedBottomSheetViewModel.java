package com.bsaugues.passmanager.presentation.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;

import com.bsaugues.passmanager.data.entity.SingleLiveEvent;
import com.bsaugues.passmanager.data.entity.model.ReservationEntity;
import com.bsaugues.passmanager.data.repository.ContentRepository;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class SharedBottomSheetViewModel extends AndroidViewModel {

    private MutableLiveData<Boolean> bottomSheetActiveLiveData;

    public SharedBottomSheetViewModel(Application application) {
        super(application);
        this.bottomSheetActiveLiveData = new MutableLiveData<>();
    }

    @Override
    protected void onCleared() {
        Timber.d("Activity destroyed, event received in %s", this);
        super.onCleared();
    }

    public MutableLiveData<Boolean> getBottomSheetActiveLiveData() {
        return bottomSheetActiveLiveData;
    }

    public void setBottomSheetActive(boolean active) {
        bottomSheetActiveLiveData.postValue(active);
    }
}
