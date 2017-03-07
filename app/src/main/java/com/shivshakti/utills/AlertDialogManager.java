package com.shivshakti.utills;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.shivshakti.R;

public class AlertDialogManager {

    public static void showDialog(AppCompatActivity activity, String result) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.MyAlertDialogStyle);
        builder.setTitle(commonVariables.appname);
        builder.setMessage(result);
        builder.setPositiveButton("Ok", null);
        AlertDialog alertDialog = builder.show();
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(activity, R.color.black));
        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(activity, R.color.black));
    }
}
