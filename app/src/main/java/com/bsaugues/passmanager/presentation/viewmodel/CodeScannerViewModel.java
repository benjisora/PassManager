package com.bsaugues.passmanager.presentation.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;

import com.bsaugues.passmanager.data.entity.SingleLiveEvent;
import com.bsaugues.passmanager.data.entity.model.ReservationEntity;
import com.bsaugues.passmanager.data.exception.EmptyReservationEntityException;
import com.bsaugues.passmanager.data.exception.InvalidInputException;
import com.bsaugues.passmanager.data.repository.ContentRepository;
import com.bsaugues.passmanager.data.values.nav.NavEvent;
import com.bsaugues.passmanager.data.values.nav.NavEventTypeValues;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class CodeScannerViewModel extends AndroidViewModel {

    private ContentRepository contentRepository;

    private CompositeDisposable disposables;
    private SingleLiveEvent<Throwable> errorLiveData;

    /*  TODO: Reflect on the use of SingleLiveEvent here.
        MutableLiveData would repost previous value on configuration changes.
        Could be prevented by keeping the SLE<NavEvent> inside the ViewModel instead of in the View.

        UPDATE: ViewModel is not re-instantiated on configuration changes, meaning the SLE<NavEvent>
        wouldn't be up to date with the newly created one.
    */
    private SingleLiveEvent<Boolean> receiveReservationLiveData;
    private MutableLiveData<Boolean> loadingStateLiveData;

    @Inject
    public CodeScannerViewModel(Application application, ContentRepository contentRepository) {
        super(application);
        this.contentRepository = contentRepository;
        this.disposables = new CompositeDisposable();
        this.errorLiveData = new SingleLiveEvent<>();
        this.receiveReservationLiveData = new SingleLiveEvent<>();
        this.loadingStateLiveData = new MutableLiveData<>();
    }

    @Override
    protected void onCleared() {
        Timber.d("Activity destroyed, event received in %s", this);
        disposables.dispose();
        super.onCleared();
    }

    public SingleLiveEvent<Throwable> getErrorLiveData() {
        return errorLiveData;
    }

    public SingleLiveEvent<Boolean> getReceiveReservationLiveData() {
        return receiveReservationLiveData;
    }

    public MutableLiveData<Boolean> getLoadingStateLiveData() {
        return loadingStateLiveData;
    }

    public void retrieveReservationDetails(String id) {
        if (isValidInput(id)) {
            loadingStateLiveData.postValue(true);
            disposables.add(contentRepository.receiveReservationDetailsFromServer(id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableObserver<Boolean>() {

                        @Override
                        public void onComplete() {
                            //Intentionally empty
                            loadingStateLiveData.postValue(false);
                        }

                        @Override
                        public void onError(Throwable e) {
                            Timber.e(e, "onError");
                            errorLiveData.postValue(e);
                            loadingStateLiveData.postValue(false);
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
