package com.android.aceit;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.android.aceit.R;
import com.google.android.material.snackbar.Snackbar;

public class SnackbarUtils {

    public static void showCustomErrorSnackbar(Context context, String message) {
        View view = ((Activity) context).getWindow().getDecorView().findViewById(android.R.id.content);
        Snackbar errorSnackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT);

        // Customize the error Snackbar
        customizeErrorSnackbar(errorSnackbar);

        // Add a "Dismiss" button to the Snackbar
        errorSnackbar.setAction("Dismiss", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                errorSnackbar.dismiss();
            }
        });

        errorSnackbar.show();
    }

    public static void showCustomErrorSnackbarForChecklist(View view, String message) {

        Snackbar errorSnackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT);

        // Customize the error Snackbar
        customizeErrorSnackbar(errorSnackbar);

        // Add a "Dismiss" button to the Snackbar
        errorSnackbar.setAction("Dismiss", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                errorSnackbar.dismiss();
            }
        });

        errorSnackbar.show();
    }

    private static void customizeErrorSnackbar(Snackbar snackbar) {
        View snackbarView = snackbar.getView();
        snackbarView.setBackground(ContextCompat.getDrawable(snackbarView.getContext(), R.drawable.custom_snackbar_error_background));

        TextView snackbarTextView = snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
        snackbarTextView.setTextColor(ContextCompat.getColor(snackbarView.getContext(), R.color.white));
    }
}
