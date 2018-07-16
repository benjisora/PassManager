package com.bsaugues.passmanager.data.values;

import android.content.Context;

import com.bsaugues.passmanager.R;
import com.bsaugues.passmanager.data.exception.EmptyReservationEntityException;

public final class ErrorUtils {
    private ErrorUtils() {
    }

    public static String translateException(Context context, Throwable throwable) {
        String responseString;
        responseString = String.format(context.getString(R.string.default_error_message), throwable.getClass().getSimpleName());

        if (throwable instanceof EmptyReservationEntityException) {
            responseString = context.getString(R.string.error_empty_reservation);
        }

        return responseString;
    }
}
