package com.bsaugues.passmanager.presentation.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ReservationDetailsBottomSheetFragment extends BottomSheetDialogFragment {

    public static ReservationDetailsBottomSheetFragment newInstance() {
        Bundle args = new Bundle();
        ReservationDetailsBottomSheetFragment fragment = new ReservationDetailsBottomSheetFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
