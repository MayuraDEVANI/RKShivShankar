package com.shivshakti;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shivshakti.customcontrols.JumpBall;
import com.shivshakti.utills.AppPreferences;
import com.shivshakti.utills.ExceptionHandler;
import com.shivshakti.utills.OnResult;
import com.shivshakti.utills.commonMethods;
import com.shivshakti.utills.commonVariables;

import org.json.JSONArray;
import org.json.JSONObject;

@SuppressLint("NewApi")
public class SplashActivity extends AppCompatActivity implements OnResult {
    String cmobile = "";
    Animation animation;
    String versionName = "";
    int versionCode = 0;
    LinearLayout mFl_whole;
    Snackbar snack;
    boolean isVersioningDone = true, isTimerOut = false, isValidCustDevice;
    //    DialogBallLoadingView mView;
    JumpBall mJumpBall;
    private String regId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        }
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        try {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
            // //Temporary
//            SharedPreferences.Editor editor = AppPreferences.getPrefs().edit();
//            editor.putInt(commonVariables.KEY_USER_RETAILER, 2);
//            editor.putBoolean(commonVariables.KEY_IS_WHOLESALE, true);
//            editor.apply();
//            changePage();

            cmobile = AppPreferences.getPrefs().getString(commonVariables.KEY_LOGIN_ID, "");
            //edit
//            regId = FirebaseInstanceId.getInstance().getToken();
            Log.d("ReId", regId);

            setContentView(R.layout.activity_splash_screen);
            bindview();

            try {
                PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
                versionName = pInfo.versionName;
                versionCode = pInfo.versionCode;
            } catch (Exception e) {
                e.printStackTrace();
            }
//            Animation bottomUp = AnimationUtils.loadAnimation(this, R.anim.bottom_up);
//            ViewGroup hiddenPanel = (ViewGroup) findViewById(R.id.ll_logo);
//            bottomUp.setAnimationListener(new Animation.AnimationListener() {
//                @Override
//                public void onAnimationStart(Animation animation) {
//
//                }
//
//                @Override
//                public void onAnimationEnd(Animation animation) {
//                    isTimerOut = true;
//                    callMainActivity(isValidCustDevice);
//                }
//
//                @Override
//                public void onAnimationRepeat(Animation animation) {
//
//                }
//            });
//            hiddenPanel.startAnimation(bottomUp);
//            hiddenPanel.setVisibility(View.VISIBLE);
//
//            mView = new DialogBallLoadingView();
//            mView.show(getSupportFragmentManager(), "load");

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    isTimerOut = true;
                    callMainActivity(isValidCustDevice);
                }
            }, 2000);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void callCheckAppVersionAPI() {
//        Uri uri = new Uri.Builder().scheme("http").authority(commonVariables.STRING_SERVER_URL_FOR_GET_METHOD)
//                .path("mobile/CheckAppVersion")
//                .appendQueryParameter("loginId", AppPreferences.getPrefs().getString(commonVariables.KEY_LOGIN_ID, "0"))
//                .appendQueryParameter("GCMRegistraionId", regId)
//                .build();
//        String query = uri.toString();
//        Log.v("TAG", "Calling With:" + query);
//        new ServerAPICAll(null, this).execute(query);

    }


    @Override
    protected void onPause() {
        mJumpBall.pause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        try {
            if (mJumpBall != null)
                mJumpBall.start();
            if (!commonMethods.knowInternetOn(this)) {
                snack = Snackbar.make(mFl_whole, R.string.no_internet, Snackbar.LENGTH_INDEFINITE);
                View view = snack.getView();
                TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
                tv.setTextColor(Color.WHITE);
                snack.show();
            } else if (!isVersioningDone)
                callCheckAppVersionAPI();
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(0, 0);
    }

    private void bindview() {
        animation = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_in);
        mFl_whole = (LinearLayout) findViewById(R.id.fl_whole);
        mJumpBall = (JumpBall) findViewById(R.id.jump_ball);
    }

    private void callMainActivity(boolean isValidCustDevice) {
        if (isVersioningDone && isTimerOut) {
//            if (mView != null) {
//                mView.finishAnim();
//            }
            mJumpBall.finish();
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(SplashActivity.this, MainActivitySeller.class));
                    finish();
                    overridePendingTransition(0, 0);
                }
            }, 600);

//            if (cmobile.isEmpty()) {
//                startActivity(new Intent(SplashActivity.this, RegisterActivity.class));
//            } else {
//                commonMethods.SaveCategoryMenu(null);
//                try {
//                    if (!isValidCustDevice) {
//                        SharedPreferences.Editor editor = AppPreferences.getPrefs().edit();
//                        editor.putString(commonVariables.KEY_LOGIN_ID, "");
//                        editor.putBoolean(commonVariables.KEY_IS_LOG_IN, false);
//                        editor.putString(commonVariables.KEY_LOGIN_USERNAME, "");
//                        editor.putString(commonVariables.KEY_ORDER_ID, "0");
//                        editor.commit();
//                        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
//                    } else
//                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//            }

        }
    }

    @Override
    public void onResult(JSONObject jobj) {

        try {
            if (jobj != null) {
                String strApiName = jobj.optString("api");
                if (strApiName.equalsIgnoreCase("CheckAppVersion")) {
                    JSONArray jArray = jobj.optJSONArray("resData");
                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject jObject = jArray.optJSONObject(i);
                        if (versionCode == jObject.optInt("AppVersionCode")) {
                            if (!jObject.optBoolean("IsOldVersionCompatible")) {
                                showNotCompatibleDialog();
                            } else {
                                isVersioningDone = true;
                                isValidCustDevice = jobj.optBoolean("isValidCustDevice");
                                callMainActivity(isValidCustDevice);
                            }
                            break;
                        }
                    }
                }
            } else {
                showDialog("Sorry! Some error occurred, Please try again latter.");
            }
        } catch (Exception e) {
            e.printStackTrace();
//            if (result.equalsIgnoreCase("Server not found")) {
//
//                String strMessage = "We are updating. Please try later.";
//                showDialog(strMessage);
//            } else if (result.equalsIgnoreCase("Internet off")) {
//                String strMessage = "Internet is off.";
//                showDialog(strMessage);
//            }
        }

    }

    private void showNotCompatibleDialog() {

        try {
            android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
            builder.setTitle(commonVariables.appname);
            builder.setMessage("New version of " + commonVariables.appname + " is available, update now ?");
            builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    final String appPackageName = getPackageName();
                    try {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                    } catch (android.content.ActivityNotFoundException anfe) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                    }
                    finishAffinity();
                    overridePendingTransition(0, 0);
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finishAffinity();
                }
            });

            AlertDialog alertDialog = builder.show();
            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(this, R.color.black));
            alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(this, R.color.black));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showDialog(String strMessage) {
        try {
            android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
            builder.setTitle(commonVariables.appname);
            builder.setMessage(strMessage);
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finishAffinity();
                }
            });
            AlertDialog alertDialog = builder.show();
            Resources res = getResources();
            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(this, R.color.black));
            alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(this, R.color.black));

        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
}