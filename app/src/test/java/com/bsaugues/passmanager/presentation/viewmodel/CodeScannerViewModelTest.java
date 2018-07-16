package com.bsaugues.passmanager.presentation.viewmodel;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.Observer;

import com.bsaugues.passmanager.data.repository.ContentRepository;
import com.bsaugues.passmanager.utils.RxSchedulersOverrideRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;

import io.reactivex.Observable;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(RobolectricTestRunner.class)
public class CodeScannerViewModelTest {

    @Rule
    public TestRule instantExecutorRule = new InstantTaskExecutorRule();

    @Rule
    public RxSchedulersOverrideRule schedulerRules = new RxSchedulersOverrideRule();

    @Mock
    ContentRepository contentRepository;

    @Mock
    Observer<Long> liveDataObserver;

    @InjectMocks
    CodeScannerViewModel mCodeScannerViewModel;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testNull() {
        assertThat(mCodeScannerViewModel.getReservationLiveData(), notNullValue());
        verify(contentRepository, never()).getReservationDetails(anyString());
    }

    @Test
    public void sendNumberToUI() {
//        String expectedValue = "";
//        mCodeScannerViewModel.getReservationLiveData().observeForever(liveDataObserver);
//        assertTrue(mCodeScannerViewModel.getReservationLiveData().hasActiveObservers());
//
//        when(contentRepository.getReservationDetails(anyString())).thenReturn(Observable.just(expectedValue));
//
//        mCodeScannerViewModel.startNumberCount();
//
//        verify(contentRepository).getNumberCountObservable();
//        contentRepository.getNumberCountObservable().test().assertResult(expectedValue);
//        verify(liveDataObserver).onChanged(expectedValue);
    }

    @Test
    public void sendNumberErrorToUI() {
//        Throwable expectedValue = new RuntimeException();
//        mCodeScannerViewModel.getReservationLiveData().observeForever(liveDataObserver);
//        assertTrue(mCodeScannerViewModel.getReservationLiveData().hasActiveObservers());
//
//        when(contentRepository.getNumberCountObservable()).thenReturn(Observable.<Long>error(expectedValue));
//
//        mCodeScannerViewModel.startNumberCount();
//
//        verify(contentRepository).getNumberCountObservable();
//        contentRepository.getNumberCountObservable().test().assertFailure(expectedValue.getClass());
//        verify(liveDataObserver, never()).onChanged(anyLong());
    }

    @After
    public void tearDown() {
        mCodeScannerViewModel.onCleared();
    }
}