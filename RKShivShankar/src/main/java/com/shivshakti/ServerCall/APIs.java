package com.shivshakti.ServerCall;

import android.app.ProgressDialog;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.shivshakti.Application.App;
import com.shivshakti.customcontrols.DialogRKLoadingView;
import com.shivshakti.utills.OnResult;
import com.shivshakti.utills.commonVariables;

import java.util.HashMap;
import java.util.Map;

import static com.google.android.gms.internal.zzs.TAG;

/**
 * Created by Mayura on 15/12/2016.
 */

public class APIs {
    public static void GetHomeBannerwithText(AppCompatActivity activity, OnResult onresult) {

        Uri uri = new Uri.Builder().scheme("http").authority(commonVariables.STRING_SERVER_URL_FOR_GET_METHOD)
                .path("mobileapi/" + "GetHomeBannerwithText")
                .build();
        String url = uri.toString();
        callAPI(activity, onresult, "http://www.happyhomegroup.co.in/mobileapi/GetProjectDetails?projectId=48");
    }





    public static void callAPI(AppCompatActivity activity, OnResult onresult, String url) {
        DialogRKLoadingView mView = new DialogRKLoadingView();
        mView.show(activity.getSupportFragmentManager(), "load");

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null,
                response -> {
                    Log.d(TAG, response.toString());
                    onresult.onResult(response);
                    mView.dismiss();
                }, error -> {
            VolleyLog.d(TAG, "Error: " + error.getMessage());
            onresult.onResult(null);
            mView.dismiss();
        });
        App.getInstance().addToRequestQueue(jsonObjReq);
    }


    public static void callPostAPI(AppCompatActivity activity, OnResult onresult, String url) {

        ProgressDialog pDialog = new ProgressDialog(activity);
        pDialog.setMessage("Loading...");
        pDialog.show();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, null,
                response -> {
                    Log.d(TAG, response.toString());
                    pDialog.hide();
                    onresult.onResult(response);
                }, error -> {
                    VolleyLog.d(TAG, "Error: " + error.getMessage());
                    onresult.onResult(null);
                    pDialog.hide();
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", "Androidhive");
                params.put("email", "abc@androidhive.info");
                params.put("password", "password123");

                return params;
            }
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                HashMap<String, String> headers = new HashMap<String, String>();
//                headers.put("Content-Type", "application/json");
//                headers.put("apiKey", "xxxxxxxxxxxxxxx");
//                return headers;
//            }
        };

        App.getInstance().addToRequestQueue(jsonObjReq);
    }

}
