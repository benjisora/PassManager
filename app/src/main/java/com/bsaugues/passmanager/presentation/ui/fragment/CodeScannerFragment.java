package com.bsaugues.passmanager.presentation.ui.fragment;

import android.Manifest;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bsaugues.passmanager.R;
import com.bsaugues.passmanager.data.exception.EmptyReservationEntityException;
import com.bsaugues.passmanager.data.values.nav.NavEvent;
import com.bsaugues.passmanager.data.values.nav.NavEventTypeValues;
import com.bsaugues.passmanager.presentation.component.ErrorRendererComponent;
import com.bsaugues.passmanager.presentation.component.dialog.DialogComponent;
import com.bsaugues.passmanager.presentation.component.dialog.DualChoiceListener;
import com.bsaugues.passmanager.presentation.viewmodel.CodeScannerViewModel;
import com.bsaugues.passmanager.presentation.viewmodel.SharedBottomSheetViewModel;
import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.budiyev.android.codescanner.ErrorCallback;
import com.budiyev.android.codescanner.ScanMode;
import com.google.zxing.Result;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class CodeScannerFragment extends BaseVMFragment<CodeScannerViewModel> implements DecodeCallback, ErrorCallback {

    @Inject
    ErrorRendererComponent errorRendererComponent;

    @Inject
    DialogComponent dialogComponent;

    @BindView(R.id.fragment_code_scanner_camera_view)
    CodeScannerView codeScannerView;

    @BindView(R.id.fragment_code_scanner_input_text)
    TextInputEditText input;

    @BindView(R.id.fragment_code_scanner_validate_input_button)
    Button validateInputButton;

    @BindView(R.id.fragment_code_scanner_progressbar)
    ProgressBar progressBar;

    private CodeScanner codeScanner;
    private boolean isBottomSheetShowing;
    private boolean isDataLoading;

    public static CodeScannerFragment newInstance() {
        return new CodeScannerFragment();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        CodeScannerFragmentPermissionsDispatcher.initializeCameraWithPermissionCheck(this);
        listenKeyboardDone();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        CodeScannerFragmentPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @Override
    public void onResume() {
        super.onResume();
        startCameraPreview();
    }

    @Override
    public void onPause() {
        releaseCameraResources();
        super.onPause();
    }

    @Override
    Class<CodeScannerViewModel> getViewModelClass() {
        return CodeScannerViewModel.class;
    }

    @Override
    int getLayoutId() {
        return R.layout.fragment_code_scanner;
    }

    @Override
    void initObservers() {
        viewModel.getLoadingStateLiveData().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean isLoading) {
                isDataLoading = (isLoading != null && isLoading);
                showLoading(isDataLoading);
                applyCameraCallbacksIfAllowed();
            }
        });
        viewModel.getReceiveReservationLiveData().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean received) {
                if (received != null && received) {
                    navEvent.postValue(new NavEvent(NavEventTypeValues.DISPLAY_RESERVATION_DETAILS));
                } else {
                    showError(new EmptyReservationEntityException());
                }
            }
        });
        viewModel.getErrorLiveData().observe(this, new Observer<Throwable>() {
            @Override
            public void onChanged(@Nullable Throwable throwable) {
                showError(throwable);
            }
        });

        SharedBottomSheetViewModel sharedBottomSheetViewModel = ViewModelProviders.of(getActivity()).get(SharedBottomSheetViewModel.class);
        sharedBottomSheetViewModel.getBottomSheetActiveLiveData().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean isBottomSheetActive) {
                isBottomSheetShowing = (isBottomSheetActive != null && isBottomSheetActive);
                if (isBottomSheetShowing) {
                    input.setText("");
                }
                applyCameraCallbacksIfAllowed();
            }
        });
    }

    @Override
    public void onDecoded(@NonNull Result result) {
        searchForReservationDetailsById(result.getText());
    }

    @Override
    public void onError(@NonNull Exception error) {
        showError(error);
    }

    @NeedsPermission(Manifest.permission.CAMERA)
    public void initializeCamera() {
        if (getActivity() != null) {
            codeScannerView.setVisibility(View.VISIBLE);
            codeScanner = new CodeScanner(getActivity(), codeScannerView);
            codeScanner.setCamera(CodeScanner.CAMERA_BACK);
            codeScanner.setFormats(CodeScanner.ALL_FORMATS);
            codeScanner.setScanMode(ScanMode.CONTINUOUS);
            applyCameraCallbacksIfAllowed();
        }
    }

    public void startCameraPreview() {
        if (codeScanner != null) {
            codeScanner.startPreview();
        }
    }

    public void releaseCameraResources() {
        if (codeScanner != null) {
            codeScanner.releaseResources();
        }
    }

    @OnShowRationale(Manifest.permission.CAMERA)
    public void showRationaleCamera(final PermissionRequest request) {
        dialogComponent.displayDualChoiceForcedDialog(
                new DualChoiceListener() {
                    @Override
                    public void onNegative() {
                        request.cancel();
                    }

                    @Override
                    public void onPositive() {
                        request.proceed();
                    }
                },
                R.string.dialog_rationale_camera_title,
                R.string.dialog_rationale_camera_content,
                R.string.dialog_action_allow,
                R.string.dialog_action_deny
        );
    }

    @OnPermissionDenied(Manifest.permission.CAMERA)
    public void showLastAttemptCamera() {
        dialogComponent.displayDualChoiceForcedDialog(
                new DualChoiceListener() {
                    @Override
                    public void onNegative() {
                        //Intentionally empty
                    }

                    @Override
                    public void onPositive() {
                        navEvent.postValue(new NavEvent(NavEventTypeValues.OPEN_APP_SETTINGS));
                    }
                },
                R.string.dialog_last_attempt_camera_title,
                R.string.dialog_last_attempt_camera_content,
                R.string.dialog_action_allow,
                R.string.dialog_action_deny
        );
    }

    @OnNeverAskAgain(Manifest.permission.CAMERA)
    public void hideCodeScannerView() {
        codeScannerView.setVisibility(View.GONE);
    }

    @OnClick(R.id.fragment_code_scanner_validate_input_button)
    public void validateInputButtonClick() {
        searchForReservationDetailsById(input.getText().toString());
    }

    @OnClick(R.id.fragment_code_scanner_camera_view)
    public void cameraViewClick() {
        startCameraPreview();
    }

    private void searchForReservationDetailsById(String id) {
        viewModel.retrieveReservationDetails(id);
    }

    private void showLoading(boolean show) {
        if (show) {
            validateInputButton.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
            validateInputButton.setVisibility(View.VISIBLE);
        }
    }

    private void showError(Throwable e) {
        errorRendererComponent.displayError(e);
    }

    private void applyCameraCallbacksIfAllowed() {
        if (isBottomSheetShowing || isDataLoading) {
            removeCameraCallbacks();
        } else {
            setCameraCallbacks();
        }
    }

    private void setCameraCallbacks() {
        if (codeScanner != null) {
            codeScanner.setDecodeCallback(this);
            codeScanner.setErrorCallback(this);
        }
    }

    private void removeCameraCallbacks() {
        if (codeScanner != null) {
            codeScanner.setDecodeCallback(null);
            codeScanner.setErrorCallback(null);
        }
    }

    private void listenKeyboardDone() {
        input.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    validateInputButtonClick();
                    return true;
                }
                return false;
            }
        });
    }
}
