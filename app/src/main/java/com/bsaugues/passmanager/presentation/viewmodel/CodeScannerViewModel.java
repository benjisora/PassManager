package com.bsaugues.passmanager.presentation.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;

import com.bsaugues.passmanager.data.entity.SingleLiveEvent;
import com.bsaugues.passmanager.data.entity.model.ReservationEntity;
import com.bsaugues.passmanager.data.exception.InvalidInputException;
import com.bsaugues.passmanager.data.repository.ContentRepository;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class CodeScannerViewModel extends AndroidViewModel {

    private ContentRepository contentRepository;

    private CompositeDisposable disposables;
    private SingleLiveEvent<Boolean> receiveReservationLiveData;
    private MutableLiveData<Throwable> errorLiveData;

    @Inject
    public CodeScannerViewModel(Application application, ContentRepository contentRepository) {
        super(application);
        this.contentRepository = contentRepository;
        this.disposables = new CompositeDisposable();
        this.receiveReservationLiveData = new SingleLiveEvent<>();
        this.errorLiveData = new MutableLiveData<>();
    }

    @Override
    protected void onCleared() {
        Timber.d("Activity destroyed, event received in %s", this);
        disposables.dispose();
        super.onCleared();
    }

    public SingleLiveEvent<Boolean> getReceiveReservationLiveData() {
        return receiveReservationLiveData;
    }

    public MutableLiveData<Throwable> getErrorLiveData() {
        return errorLiveData;
    }

    public void retrieveReservationDetails(String id) {
        if (isValidInput(id)) {
            disposables.add(contentRepository.receiveReservationDetailsFromServer(id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableObserver<Boolean>() {

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
                        public void onNext(Boolean value) {
                            Timber.d("value : %s; model: %s", value, this);
                            receiveReservationLiveData.postValue(value);
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
