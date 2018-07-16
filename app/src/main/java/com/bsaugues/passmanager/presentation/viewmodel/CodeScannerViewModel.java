package com.bsaugues.passmanager.presentation.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.bsaugues.passmanager.data.entity.ReservationEntity;
import com.bsaugues.passmanager.data.exception.InvalidInputException;
import com.bsaugues.passmanager.data.repository.ContentRepository;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class CodeScannerViewModel extends AndroidViewModel {

    private static final String TAG = "CodeScannerViewModel";

    private ContentRepository contentRepository;

    private CompositeDisposable disposables;
    private MutableLiveData<ReservationEntity> reservationLiveData;
    private MutableLiveData<Throwable> errorLiveData;

    @Inject
    public CodeScannerViewModel(Application application, ContentRepository contentRepository) {
        super(application);
        this.contentRepository = contentRepository;
        this.disposables = new CompositeDisposable();
        this.reservationLiveData = new MutableLiveData<>();
        this.errorLiveData = new MutableLiveData<>();
    }

    @Override
    protected void onCleared() {
        Log.d(TAG, "Activity destroyed, event received in " + this);
        disposables.dispose();
        super.onCleared();
    }

    public MutableLiveData<ReservationEntity> getReservationLiveData() {
        return reservationLiveData;
    }

    public MutableLiveData<Throwable> getErrorLiveData() {
        return errorLiveData;
    }

    public void retrieveReservationDetails(String id) {
        if (isValidInput(id)) {
            disposables.add(contentRepository.getReservationDetails(id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableObserver<ReservationEntity>() {

                        @Override
                        public void onComplete() {
                            //Intentionally empty
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e(TAG, "onError: ", e);
                            errorLiveData.postValue(e);
                        }

                        @Override
                        public void onNext(ReservationEntity value) {
                            Log.d(TAG, "value : " + value + "; model: " + this);
                            reservationLiveData.postValue(value);
                        }
                    })
            );
        } else {
            errorLiveData.postValue(new InvalidInputException());
        }
    }

    private boolean isValidInput(String input) {
        return input != null && !input.isEmpty();
    }
}
