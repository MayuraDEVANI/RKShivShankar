package com.shivshakti.utills;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.shivshakti.R;
import com.shivshakti.classes.Suggestion;

import java.util.ArrayList;
import java.util.Collections;

@SuppressLint("SimpleDateFormat")
public class commonMethods {


    public static boolean knowInternetOn(Activity activity) {
        if (activity != null)
            if (isConnectedWifi(activity) || isConnectedMobile(activity)) {
                return true;
            }
        return false;
    }

    public static boolean isConnectedWifi(Context context) {
        NetworkInfo info = getNetworkInfo(context);
        return (info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_WIFI);
    }

    public static boolean isConnectedMobile(Context context) {
        NetworkInfo info = getNetworkInfo(context);
        return (info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_MOBILE);
    }

    public static NetworkInfo getNetworkInfo(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo();
    }

    public static void showInternetAlert(final Activity activity) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                android.support.v7.app.AlertDialog.Builder alertDialog = new android.support.v7.app.AlertDialog.Builder(activity);
                alertDialog.setTitle("Internet settings");
                alertDialog.setMessage("Internet is not on. Do you want to go to settings menu?");
                alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Settings.ACTION_SETTINGS);
                        activity.startActivity(intent);
                    }
                });
                alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                alertDialog.show();
            }
        });
    }

    public static void hidesoftKeyboard(Activity activity) {
        if (activity != null) {
            View view = activity.getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    public static ArrayList<Suggestion> getSavedSearchArray() {
        ArrayList<Suggestion> mResults = new ArrayList<>();
        try {
            int size = AppPreferences.getPrefs().getInt("array_size", 0);
            for (int i = 0; i < size; i++)
                mResults.add(new Suggestion(AppPreferences.getPrefs().getString("array_" + i, null)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Collections.reverse(mResults);
        return mResults;
    }

    public static void saveSearchArray(ArrayList<Suggestion> myResArray) {
        try {
            SharedPreferences.Editor editor = AppPreferences.getPrefs().edit();
            if (myResArray == null)
                myResArray = new ArrayList<>();
            editor.putInt("array_size", myResArray.size());
            for (int i = 0; i < myResArray.size(); i++)
                editor.putString("array_" + i, myResArray.get(i).getBody());
            editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void performSearch(AppCompatActivity activity, String strSearch) {

        try {
            if (!strSearch.equals("")) {

                Log.v(commonVariables.TAG, "SEARCHING FOR: " + strSearch);
                ArrayList<Suggestion> array = getSavedSearchArray();
                if (!SavedStill(array, strSearch)) {
                    array.add(new Suggestion(strSearch));
                    saveSearchArray(array);
                }
//                Intent output = new Intent();
//                output.putExtra(commonVariables.INTENT_EXTRA_SEARCH_STRING, strSearch);
//                activity.setResult(activity.RESULT_OK, output);
//                activity.finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static boolean SavedStill(ArrayList<Suggestion> array, String strSearch) {
        boolean saved = false;
        for (int i = 0; i < array.size(); i++) {
            if (array.get(i).getBody().equalsIgnoreCase(strSearch)) {
                saved = true;
                break;
            }
        }
        return saved;
    }

    public static void cartCountAnimation(final Activity activity, final TextView mTv_cart_count) {
        try {
            if (mTv_cart_count != null) {
                final int[] count = {3};
//                ObjectAnimator animation = ObjectAnimator.ofFloat(mTv_cart_count, "rotationY", 0.0f, 360f);
//                ObjectAnimator animation = ObjectAnimator. (mTv_cart_count, "rotationY", 0.0f, 360f)
//                ;
//                animation.setDuration(3000);
//                animation.setRepeatCount(0);
//                animation.setInterpolator(new AccelerateDecelerateInterpolator());
//                animation.start();


                mTv_cart_count.clearAnimation();
                Animation zoomin = AnimationUtils.loadAnimation(activity, R.anim.zoom_in);
                Animation zoomout = AnimationUtils.loadAnimation(activity, R.anim.zoom_out);
                mTv_cart_count.startAnimation(zoomin);
                zoomin.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation arg0) {
                    }

                    @Override
                    public void onAnimationRepeat(Animation arg0) {
                    }

                    @Override
                    public void onAnimationEnd(Animation arg0) {
                        if (count[0] != 0)
                            mTv_cart_count.startAnimation(zoomout);
                    }
                });

                zoomout.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation arg0) {
                    }

                    @Override
                    public void onAnimationRepeat(Animation arg0) {
                    }

                    @Override
                    public void onAnimationEnd(Animation arg0) {
                        mTv_cart_count.startAnimation(zoomin);
                        count[0]--;
                    }
                });


//                mTv_cart_count.setBackgroundResource(R.drawable.xml_green_border_square_box);
//                mTv_cart_count.setTextColor(ContextCompat.getColor(activity, R.color.white));
//
//                final Handler h = new Handler();
//                h.postDelayed(new Runnable() {
//                    public void run() {
//                        mTv_cart_count.setBackgroundResource(R.drawable.xml_black_border_square_box);
////                        mTv_cart_count.setTextColor(ContextCompat.getColor(activity, R.color.black));
//                    }
//                }, 2000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void saveAndSetCartCount(int cartcount, TextView mTv_cart_count) {
        if (cartcount >= 0) {
            SharedPreferences.Editor editor = AppPreferences.getPrefs().edit();
            editor.putInt(commonVariables.KEY_NOTI_COUNT, cartcount);
            editor.apply();
            Log.v("TAG", "CART ITEMS: " + AppPreferences.getPrefs().getInt(commonVariables.KEY_NOTI_COUNT, 0));
            if (mTv_cart_count != null)
                if (cartcount > 0) {
                    mTv_cart_count.setVisibility(View.VISIBLE);
                    mTv_cart_count.setText(cartcount + "");
                } else
                    mTv_cart_count.setVisibility(View.GONE);
        }
    }

}
