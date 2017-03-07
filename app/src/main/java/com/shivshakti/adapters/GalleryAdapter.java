package com.shivshakti.adapters;

import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shivshakti.R;
import com.shivshakti.utills.commonVariables;
import com.shivshakti.viewpager.ViewPagerActivity;

import java.util.ArrayList;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.MyViewHolder> {

    AppCompatActivity activity;
    ArrayList<String> listImages;
    RecyclerView mRecyclerView;
    boolean isBigLoader, isClick;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView mTv_count;

        public MyViewHolder(View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.image_gallery);
            mTv_count = (TextView) view.findViewById(R.id.tv_count);
        }
    }

    public GalleryAdapter(AppCompatActivity activity, ArrayList<String> listSlider2, RecyclerView mrecyclerView, boolean isBigLoader, boolean isclick) {
        try {
            this.activity = activity;
            listImages = listSlider2;
            mRecyclerView = mrecyclerView;
            this.isBigLoader = isBigLoader;
            isClick = isclick;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_gallery, parent, false);
        if (isClick)
            itemView.setOnClickListener(mOnClickListener);
        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        try {
                    holder.imageView.setVisibility(View.VISIBLE);
                    holder.mTv_count.setVisibility(View.VISIBLE);
                    holder.mTv_count.setText(position+"");
                    String strImageURL = listImages.get(position);
                    if ((strImageURL != null) && (!strImageURL.equals(""))) {
                        Glide.with(activity).load(strImageURL).asBitmap().placeholder(R.color.gray).error(R.drawable.ic_menu_camera).into(holder.imageView);
                    }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return listImages.size();
    }

    private final View.OnClickListener mOnClickListener = new MyOnClickListener();

    private class MyOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(final View view) {
            int itemPosition = mRecyclerView.getChildLayoutPosition(view);

            try {
                AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);
                view.startAnimation(buttonClick);
                final Handler h = new Handler();
                h.postDelayed(() -> {
                    try {
                        final Intent intent = new Intent(activity, ViewPagerActivity.class);
                        if (listImages != null) {
                            String[] myStrings = new String[listImages.size()];
                            for (int i = 0; i < listImages.size(); i++) {
                                myStrings[i] = listImages.get(i);
                            }
                            intent.putExtra(commonVariables.INTENT_EXTRA_LIST_IMAGE_ARRAY, myStrings);
                            intent.putExtra(commonVariables.INTENT_EXTRA_POSITION, itemPosition);
                            intent.putExtra(commonVariables.KEY_IS_LANDSCAPE, false);
                        }
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            activity.getWindow().setExitTransition(null);
                            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity,
                                    view,   // Starting view
                                    "Detail"    // The String
                            );

                            //Start the Intent
                            ActivityCompat.startActivity(activity, intent, options.toBundle());
                        } else {
                            activity.startActivity(intent);
                            activity.overridePendingTransition(0, 0);
                        }

                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }, 30);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}