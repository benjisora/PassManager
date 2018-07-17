package com.bsaugues.passmanager.presentation.di.module.fragment;

import android.arch.lifecycle.ViewModel;

import com.bsaugues.passmanager.presentation.di.annotation.PerFragment;
import com.bsaugues.passmanager.presentation.di.annotation.ViewModelKey;
import com.bsaugues.passmanager.presentation.viewmodel.CodeScannerViewModel;
import com.bsaugues.passmanager.presentation.viewmodel.ReservationDetailsViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class FragmentViewModelModule {

    @PerFragment
    @Binds
    @IntoMap
    @ViewModelKey(CodeScannerViewModel.class)
    public abstract ViewModel codeScannerViewModel(CodeScannerViewModel codeScannerViewModel);

    @PerFragment
    @Binds
    @IntoMap
    @ViewModelKey(ReservationDetailsViewModel.class)
    public abstract ViewModel reservationDetailsViewModel(ReservationDetailsViewModel reservationDetailsViewModel);
}
