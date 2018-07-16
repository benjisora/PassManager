package com.bsaugues.passmanager.presentation.component.dialog;

public interface DialogComponent {
    void dismissDialog();

    void displaySingleChoiceDialog(final SingleChoiceListener dualChoiceListener, int title, int content, int positiveText);

    void displayDualChoiceDialog(final DualChoiceListener dualChoiceListener, int title, int content, int positiveText, int negativeText);

    void displaySingleChoiceForcedDialog(final SingleChoiceListener singleChoiceListener, int title, int content, int positiveText);

    void displayDualChoiceForcedDialog(final DualChoiceListener dualChoiceListener, int title, int content, int positiveText, int negativeText);

    boolean isDialogDisplayed();
}
