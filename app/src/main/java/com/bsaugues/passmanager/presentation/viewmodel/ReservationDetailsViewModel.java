package com.bsaugues.passmanager.presentation.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;

import com.bsaugues.passmanager.data.entity.SingleLiveEvent;
import com.bsaugues.passmanager.data.entity.model.ReservationEntity;
import com.bsaugues.passmanager.data.repository.ContentRepository;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class ReservationDetailsViewModel extends AndroidViewModel {

    private ContentRepository contentRepository;

    private CompositeDisposable disposables;
    private MutableLiveData<ReservationEntity> reservationLiveData;
    private SingleLiveEvent<Throwable> errorLiveData;

    @Inject
    public ReservationDetailsViewModel(Application application, ContentRepository contentRepository) {
        super(application);
        this.contentRepository = contentRepository;
        this.disposables = new CompositeDisposable();
        this.reservationLiveData = new MutableLiveData<>();
        this.errorLiveData = new SingleLiveEvent<>();
    }

    @Override
    protected void onCleared() {
        Timber.d("Activity destroyed, event received in %s", this);
        disposables.dispose();
        super.onCleared();
    }

    public MutableLiveData<ReservationEntity> getReservationLiveData() {
        return reservationLiveData;
    }

    public SingleLiveEvent<Throwable> getErrorLiveData() {
        return errorLiveData;
    }

    public void retrieveLastScannedReservation() {
        disposables.add(contentRepository.getLastScannedReservationDetails()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<ReservationEntity>() {

                    @Override
                    public void onComplete() {
                        //Intentionally empty
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e, "onError");
                        errorLiveData.postValue(e);
                    }

                    @Override
                    public void onNext(ReservationEntity value) {
                        Timber.d("value : %s; model: %s", value, this);
                        reservationLiveData.postValue(value);
                    }
                })
        );
    }
}
