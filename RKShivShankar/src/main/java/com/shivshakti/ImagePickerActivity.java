package com.shivshakti;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.shivshakti.cropimage.CropImageActivity;
import com.shivshakti.utills.ExceptionHandler;
import com.shivshakti.utills.InternalStorageContentProvider;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;


@SuppressLint("NewApi")
public class ImagePickerActivity extends Activity {
    public static final int REQUEST_CODE_GALLERY = 1;
    public static final int REQUEST_CODE_TAKE_PICTURE = 2;
    public static final int REQUEST_CODE_CROP_IMAGE = 3;
    public static final String TEMP_PHOTO_FILE_NAME = "temp_photo.jpg";
    private static final long GAME_LENGTH_MILLISECONDS = 90000;
    File mFileTemp;
    String[] permissions = new String[]{
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private CountDownTimer mCountDownTimer;
    private long mTimerMilliseconds;
    private boolean mGameIsInProgress;

    public static void copyStream(InputStream input, OutputStream output) throws IOException {

        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = input.read(buffer)) != -1) {
            output.write(buffer, 0, bytesRead);
        }

    }

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        super.onCreate(savedInstanceState);
        try {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            isStoragePermissionGranted();

//            selectImage();
            setContentView(R.layout.dialog_gall_cams);
            setTitle(null);
            getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(getWindow().getAttributes());
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.gravity = Gravity.BOTTOM;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            getWindow().setAttributes(lp);
            // dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            Button bGallery1 = (Button) findViewById(R.id.bgallary1);
            Button bCamera1 = (Button) findViewById(R.id.bcamera1);
            bGallery1.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {

                    try {
                        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                        photoPickerIntent.setType("image/*");
                        startActivityForResult(photoPickerIntent, REQUEST_CODE_GALLERY);
                        overridePendingTransition(0, 0);
                    } catch (Exception e) {

                    }
                    finish();
                }
            });
            bCamera1.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    try {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        Uri mImageCaptureUri = null;
                        String state = Environment.getExternalStorageState();
                        if (Environment.MEDIA_MOUNTED.equals(state)) {
                            mImageCaptureUri = Uri.fromFile(mFileTemp);
                        } else {

                            mImageCaptureUri = InternalStorageContentProvider.CONTENT_URI;
                        }
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
                        intent.putExtra("return-data", true);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivityForResult(intent, REQUEST_CODE_TAKE_PICTURE);
                        //overridePendingTransition(0, 0);
                    } catch (ActivityNotFoundException e) {
                        e.printStackTrace();
                    }
                    finish();
                }
            });

            String state = Environment.getExternalStorageState();
            if (Environment.MEDIA_MOUNTED.equals(state)) {
                mFileTemp = new File(Environment.getExternalStorageDirectory(), TEMP_PHOTO_FILE_NAME);
            } else {
                mFileTemp = new File(getFilesDir(), TEMP_PHOTO_FILE_NAME);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
//        try {
//            if (isOnline()) {
//                MobileAds.initialize(this, getString(R.string.app_id));
//                mInterstitialAd = new InterstitialAd(this);
//                mInterstitialAd.setAdUnitId(getString(R.string.interestital_add_mob));
//                startGame();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isFinishing()) {
            overridePendingTransition(android.R.anim.fade_out, R.anim.slide_bottom);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            //resume tasks needing this permission
        } else {
            finish();
        }
    }

    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            int result;
            List<String> listPermissionsNeeded = new ArrayList<>();
            for (String p : permissions) {
                result = ContextCompat.checkSelfPermission(this, p);
                if (result != PackageManager.PERMISSION_GRANTED) {
                    listPermissionsNeeded.add(p);
                }
            }
            if (!listPermissionsNeeded.isEmpty()) {
                ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 100);
                return false;
            }
            return true;

//            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                    == PackageManager.PERMISSION_GRANTED) {
//                return true;
//            } else {
//                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
//                return false;
//            }
        } else { //permission is automatically granted on sdk<23 upon installation
            return true;
        }
    }

    @Override
    protected void onDestroy() {
        stopGame();
        super.onDestroy();
    }

    private void stopGame() {
        try {
            if (mCountDownTimer != null)
                mCountDownTimer.cancel();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

    @Override
    public void onResume() {
        // Start or resume the game.
        super.onResume();
        if (isOnline() && mGameIsInProgress) {
            resumeGame(mTimerMilliseconds);
        }
    }

    private void resumeGame(long milliseconds) {
        // Create a new timer for the correct length and start it.
        mGameIsInProgress = true;
        mTimerMilliseconds = milliseconds;
        createTimer(milliseconds);
        mCountDownTimer.start();
    }


//    public void selectImage() {
//        dialog = new Dialog(this);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.
//    }

    private void createTimer(final long milliseconds) {
        // Create the game timer, which counts down to the end of the level
        // and shows the "retry" button.
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
        mCountDownTimer = new CountDownTimer(milliseconds, 50) {
            @Override
            public void onTick(long millisUnitFinished) {
                mTimerMilliseconds = millisUnitFinished;
            }

            @Override
            public void onFinish() {
                mGameIsInProgress = false;
            }
        };
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_GALLERY:

                    try {
                        InputStream inputStream = getContentResolver().openInputStream(data.getData());
                        FileOutputStream fileOutputStream = new FileOutputStream(mFileTemp);
                        copyStream(inputStream, fileOutputStream);
                        fileOutputStream.close();
                        inputStream.close();
                        startCropImage();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case REQUEST_CODE_TAKE_PICTURE:
                    startCropImage();
                    break;
                case REQUEST_CODE_CROP_IMAGE:
                    String path = data.getStringExtra("image-path");
                    if (path == null) {
                        return;
                    }
                    Bitmap bitmap1 = BitmapFactory.decodeFile(mFileTemp.getPath());
                    if (bitmap1 != null) {
                        Intent i = new Intent(ImagePickerActivity.this, AddBrandActivity.class);
                        //Convert to byte array
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap1.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        byte[] byteArray = stream.toByteArray();
                        i.putExtra("image", byteArray);
                        startActivity(i);
                        finish();
                        //overridePendingTransition(0, 0);
                    }
//                    Toast.makeText(getApplicationContext(), "Insert Photo First", Toast.LENGTH_LONG).show();
                    break;
            }
        } else
            onBackPressed();
    }

    private void startCropImage() {
        Intent intent = new Intent(ImagePickerActivity.this, CropImageActivity.class);
        intent.putExtra("image-path", mFileTemp.getPath());
        intent.putExtra(CropImageActivity.SCALE, true);
        intent.putExtra(CropImageActivity.ASPECT_X, 0);
        intent.putExtra(CropImageActivity.ASPECT_Y, 0);
        startActivityForResult(intent, REQUEST_CODE_CROP_IMAGE);
        //overridePendingTransition(0, 0);
    }

    @Override
    public void onBackPressed() {

//        Intent i1 = new Intent(getApplicationContext(), MainHomeScreenActivity2.class);
//        i1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();
//        startActivity(i1);
//        //overridePendingTransition(0, 0);
    }


}
