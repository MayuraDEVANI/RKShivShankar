package com.shivshakti.utills;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.shivshakti.MainActivitySeller;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionHandler implements Thread.UncaughtExceptionHandler {
    private final Activity myContext;
    private final String LINE_SEPARATOR = "\n";

    public ExceptionHandler(Activity context) {
        myContext = context;
    }

    @SuppressLint("ShowToast")
    public void uncaughtException(Thread thread, Throwable exception) {
        StringWriter stackTrace = new StringWriter();
        exception.printStackTrace(new PrintWriter(stackTrace));
        StringBuilder errorReport = new StringBuilder();
        errorReport.append("************ CAUSE OF ERROR ************\n\n");
        errorReport.append(stackTrace.toString());

        // errorReport.append("\n************ DEVICE INFORMATION ***********\n");
        // errorReport.append("Brand: ");
        // errorReport.append(Build.BRAND);
        // errorReport.append(LINE_SEPARATOR);
        // errorReport.append("Device: ");
        // errorReport.append(Build.DEVICE);
        // errorReport.append(LINE_SEPARATOR);
        // errorReport.append("Model: ");
        // errorReport.append(Build.MODEL);
        // errorReport.append(LINE_SEPARATOR);
        // errorReport.append("Id: ");
        // errorReport.append(Build.ID);
        // errorReport.append(LINE_SEPARATOR);
        // errorReport.append("Product: ");
        // errorReport.append(Build.PRODUCT);
        // errorReport.append(LINE_SEPARATOR);
        // errorReport.append("\n************ FIRMWARE ************\n");
        // errorReport.append("SDK: ");
        // errorReport.append(Build.VERSION.SDK);
        // errorReport.append(LINE_SEPARATOR);
        // errorReport.append("Release: ");
        // errorReport.append(Build.VERSION.RELEASE);
        // errorReport.append(LINE_SEPARATOR);
        // errorReport.append("Incremental: ");
        // errorReport.append(Build.VERSION.INCREMENTAL);
        // errorReport.append(LINE_SEPARATOR);

        Log.e(commonVariables.TAG, errorReport.toString());
        // Toast.makeText(myContext, errorReport.toString(),
        // Toast.LENGTH_SHORT).show();
        // SharedPreferences.Editor editor = AppPreferences.getPrefs().edit();
        // editor.clear();
        // editor.commit();
        // Toast.makeText(myContext, errorReport.toString(),
        // Toast.LENGTH_LONG).show();
        Intent intent = new Intent(myContext, MainActivitySeller.class);
        intent.putExtra("error", errorReport.toString());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        myContext.startActivity(intent);
        myContext.overridePendingTransition(0, 0);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(10);
    }

}