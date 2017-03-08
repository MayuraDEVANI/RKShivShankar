package com.shivshakti;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.shivshakti.utills.ExceptionHandler;


public class AddBrandActivity extends AppCompatActivity {
    ImageView iv_effectImg;
    Bitmap bmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_brand);
        iv_effectImg = (ImageView) findViewById(R.id.iv_effectImg);
        byte[] byteArray = getIntent().getByteArrayExtra("image");
        bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        iv_effectImg.setImageBitmap(bmp);
        iv_effectImg.setDrawingCacheEnabled(true);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

}
