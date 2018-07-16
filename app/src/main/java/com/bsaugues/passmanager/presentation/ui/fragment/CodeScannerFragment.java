package com.bsaugues.passmanager.presentation.ui.fragment;

import android.Manifest;
import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.view.View;

import com.bsaugues.passmanager.R;
import com.bsaugues.passmanager.data.entity.ReservationEntity;
import com.bsaugues.passmanager.presentation.viewmodel.CodeScannerViewModel;
import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.budiyev.android.codescanner.ErrorCallback;
import com.budiyev.android.codescanner.ScanMode;
import com.google.zxing.Result;

import butterknife.BindView;
import butterknife.OnClick;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class CodeScannerFragment extends BaseVMFragment<CodeScannerViewModel> implements DecodeCallback, ErrorCallback {

    @BindView(R.id.fragment_number_list_scanner_view)
    CodeScannerView codeScannerView;

    @BindView(R.id.fragment_number_list_input_layout)
    TextInputLayout inputLayout;

    @BindView(R.id.fragment_number_list_input_text)
    TextInputEditText input;

    private CodeScanner codeScanner;

    public static CodeScannerFragment newInstance() {
        return new CodeScannerFragment();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        CodeScannerFragmentPermissionsDispatcher.initializeCameraWithPermissionCheck(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        CodeScannerFragmentPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @Override
    public void onResume() {
        super.onResume();
        CodeScannerFragmentPermissionsDispatcher.startPreviewWithPermissionCheck(this);
    }

    @Override
    public void onPause() {
        stopPreview();
        super.onPause();
    }

    @Override
    Class<CodeScannerViewModel> getViewModelClass() {
        return CodeScannerViewModel.class;
    }

    @Override
    int getLayoutId() {
        return R.layout.fragment_number_list;
    }

    @Override
    void initObservers() {
        viewModel.getReservationLiveData().observe(this, new Observer<ReservationEntity>() {
            @Override
            public void onChanged(@Nullable ReservationEntity reservation) {
                showReservationDetails(reservation);
            }
        });
        viewModel.getErrorLiveData().observe(this, new Observer<Throwable>() {
            @Override
            public void onChanged(@Nullable Throwable throwable) {
                //showInputError(throwable);
            }
        });
    }

    @OnClick(R.id.fragment_number_list_validate_input_button)
    public void requestReservationDetails() {
        requestReservationDetails(input.getText().toString());
    }

    @OnClick(R.id.fragment_number_list_scanner_view)
    public void requestStartPreview() {
        CodeScannerFragmentPermissionsDispatcher.startPreviewWithPermissionCheck(this);
    }

    @NeedsPermission(Manifest.permission.CAMERA)
    public void startPreview() {
        codeScanner.startPreview();
    }

    @NeedsPermission(Manifest.permission.CAMERA)
    public void initializeCamera() {
        if (getActivity() != null) {
            codeScanner = new CodeScanner(getActivity(), codeScannerView);
            codeScanner.setDecodeCallback(this);
            codeScanner.setErrorCallback(this);
            codeScanner.setCamera(CodeScanner.CAMERA_BACK);
            codeScanner.setFormats(CodeScanner.ALL_FORMATS);
            codeScanner.setScanMode(ScanMode.CONTINUOUS);
        }
    }

    private void stopPreview() {
        codeScanner.releaseResources();
    }

    private void requestReservationDetails(String id) {
        viewModel.retrieveReservationDetails(id);
    }

    private void showInputError(Throwable e) {
        if (e != null) {
            inputLayout.setError(e.getMessage());
        }
    }

    private void showReservationDetails(ReservationEntity reservation) {
        if (reservation != null) {
            ReservationDetailsBottomSheetFragment.newInstance().show(getActivity().getSupportFragmentManager(), ReservationDetailsBottomSheetFragment.class.getName());
        }
    }

    @Override
    public void onDecoded(@NonNull Result result) {
        requestReservationDetails(result.getText());
    }

    @Override
    public void onError(@NonNull Exception error) {
        showError(error);
    }

    private void showError(Throwable e) {

    }
}
