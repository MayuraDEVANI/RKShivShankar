package com.shivshakti.Application;

import android.app.Application;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.shivshakti.utills.AppPreferences;
import com.shivshakti.utills.TypefaceUtil;
import com.shivshakti.utills.commonVariables;

public class App extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        AppPreferences.init(this);
        try {
            TypefaceUtil.setDefaultFont(this, "MONOSPACE", "NOTOSANS.TTF");
            TypefaceUtil.setDefaultFont(this, "SERIF", "NOTOSANS.TTF");
            TypefaceUtil.setDefaultFont(this, "SANS_SERIF", "NOTOSANS.TTF");
            TypefaceUtil.setDefaultFont(this, "DEFAULT", "NOTOSANS.TTF");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            TelephonyManager tm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
            commonVariables.uuid = tm.getDeviceId();// Settings.Secure.getString(getApplicationContext().getContentResolver(),                    Settings.Secure.ANDROID_ID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static final String TAG = App.class.getSimpleName();

    private RequestQueue mRequestQueue;
    private static App mInstance;

    public static synchronized App getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }
}
