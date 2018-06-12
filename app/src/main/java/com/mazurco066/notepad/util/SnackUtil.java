package com.mazurco066.notepad.util;

import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;

import com.mazurco066.notepad.R;

public class SnackUtil {

    public static void show(View view, String msg, int lenght) {
        Snackbar snackbar = Snackbar.make(view, msg, lenght);
        TextView snackActionView = snackbar.getView().findViewById(android.support.design.R.id.snackbar_text);
        snackActionView.setTextColor(Color.WHITE);
        snackActionView.setTextSize(16);
        snackbar.getView().setBackground(view.getContext().getDrawable(R.drawable.themed_snackbar));
        //Mostrando a snackbar
        snackbar.show();
    }
}
