package com.bsaugues.passmanager.presentation.ui.fragment;

import android.arch.lifecycle.Observer;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.bsaugues.passmanager.R;
import com.bsaugues.passmanager.data.entity.model.ReservationEntity;
import com.bsaugues.passmanager.data.utils.DateFormatUtils;
import com.bsaugues.passmanager.data.values.nav.NavEvent;
import com.bsaugues.passmanager.data.values.nav.NavEventTypeValues;
import com.bsaugues.passmanager.presentation.component.ErrorRendererComponent;
import com.bsaugues.passmanager.presentation.viewmodel.ReservationDetailsViewModel;

import java.util.Date;

import javax.inject.Inject;

import butterknife.BindView;

public class ReservationDetailsFragment extends BaseVMBottomSheetDialogFragment<ReservationDetailsViewModel> {

    @Inject
    ErrorRendererComponent errorRendererComponent;

    @BindView(R.id.fragment_bottom_sheet_reservation_details_container)
    ConstraintLayout container;

    @BindView(R.id.fragment_bottom_sheet_reservation_details_full_name)
    TextView fullName;

    @BindView(R.id.fragment_bottom_sheet_reservation_details_id)
    TextView reservationId;

    @BindView(R.id.fragment_bottom_sheet_reservation_details_last_scan)
    TextView lastScan;

    public static ReservationDetailsFragment newInstance() {
        Bundle args = new Bundle();
        ReservationDetailsFragment fragment = new ReservationDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel.retrieveLastScannedReservation();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        navEvent.postValue(new NavEvent(NavEventTypeValues.NOTIFY_BOTTOM_SHEET_CLOSED));
    }

    @Override
    int getLayoutId() {
        return R.layout.fragment_bottom_sheet_reservation_details;
    }

    @Override
    Class<ReservationDetailsViewModel> getViewModelClass() {
        return ReservationDetailsViewModel.class;
    }

    @Override
    void initObservers() {
        viewModel.getErrorLiveData().observe(this, new Observer<Throwable>() {
            @Override
            public void onChanged(@Nullable Throwable throwable) {
                showError(throwable);
            }
        });
        viewModel.getReservationLiveData().observe(this, new Observer<ReservationEntity>() {
            @Override
            public void onChanged(@Nullable ReservationEntity reservation) {
                showReservationDetails(reservation);
            }
        });
    }

    private void showReservationDetails(ReservationEntity reservationEntity) {
        if (reservationEntity != null) {
            fullName.setText(String.format(getString(R.string.reservation_full_name_format),
                    reservationEntity.getOwnerFirstName(),
                    reservationEntity.getOwnerLastName().toUpperCase()
            ));

            reservationId.setText(String.format(getString(R.string.reservation_id_format),
                    reservationEntity.getId()
            ));

            displayLastScannedDate(reservationEntity.getLastScanDateTimestamp());
        }
    }

    private void displayLastScannedDate(Long timestamp) {
        if (timestamp != null && getActivity() != null) {
            lastScan.setText(String.format(getString(R.string.reservation_last_scan_format),
                    DateFormatUtils.getDayFromDateString(new Date(timestamp)),
                    DateFormatUtils.getHourFromDateString(new Date(timestamp))
            ));
            container.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorWarning));
            lastScan.setVisibility(View.VISIBLE);
        } else {
            lastScan.setVisibility(View.GONE);
        }
    }

    private void showError(Throwable e) {
        errorRendererComponent.displayError(e);
    }
}
