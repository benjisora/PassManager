package com.bsaugues.passmanager.presentation.ui.view.viewdatawrapper;

import android.content.Context;
import android.text.Html;

import com.bsaugues.passmanager.R;
import com.bsaugues.passmanager.data.entity.model.PassEntity;

public class PassEntityDataWrapper {

    private PassEntity passEntity;
    private Context context;

    public PassEntityDataWrapper(PassEntity passEntity, Context context) {
        this.passEntity = passEntity;
        this.context = context;
    }

    private long getPassNumber() {
        return passEntity.getAmount();
    }

    private String getPassTypeNameToDisplay() {
        switch (passEntity.getType()) {
            case SINGLE_DAY_PASS:
                return context.getString(R.string.pass_type_single_day);
            case WEEK_END_PASS:
                return context.getString(R.string.pass_type_week_end);
            case UNKNOWN:
            default:
                return "";
        }
    }

    public String getFullPassDetailString() {
        return String.format(context.getString(R.string.pass_number_type_format), getPassNumber(), getPassTypeNameToDisplay());
    }
}
