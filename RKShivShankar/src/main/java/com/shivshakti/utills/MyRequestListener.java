package com.shivshakti.utills;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

/**
 * Created by Mayura on 2/23/2017.
 */
public class MyRequestListener implements RequestListener<String, Bitmap> {
    private ImageView b;

    public MyRequestListener(ImageView paramImageView) {
        this.b = paramImageView;
    }

    @Override
    public boolean onResourceReady(Bitmap paramBitmap, String paramString, Target<Bitmap> paramTarget, boolean paramBoolean1, boolean paramBoolean2) {
        this.b.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return false;
    }

    @Override
    public boolean onException(Exception paramException, String paramString, Target<Bitmap> paramTarget, boolean paramBoolean) {
        this.b.setScaleType(ImageView.ScaleType.CENTER);
        return false;
    }
}
