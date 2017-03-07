package com.shivshakti.viewpager;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.transition.Fade;
import android.transition.Transition;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.shivshakti.R;
import com.shivshakti.customcontrols.CirclePageIndicator;
import com.shivshakti.customcontrols.ExtendedViewPager;
import com.shivshakti.customcontrols.TouchImageView;
import com.shivshakti.utills.ExceptionHandler;
import com.shivshakti.utills.commonVariables;


@SuppressLint("NewApi")
public class ViewPagerActivity extends AppCompatActivity implements OnClickListener {

    private static String[] IMAGES = {"ness.jpg", "squirrel.jpg"};

    private ExtendedViewPager page;
    private CirclePageIndicator mIndicator;
    private ImageView mIv_backImage;
    private ImageView mIv_nextImage, mIv_close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getIntent().getBooleanExtra(commonVariables.KEY_IS_LANDSCAPE, true))
            this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        try {
            setContentView(R.layout.view_pager);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Transition exitTrans = new Fade();
                exitTrans.setDuration(600);
                getWindow().setEnterTransition(exitTrans);
            }
            IMAGES = getIntent().getStringArrayExtra(commonVariables.INTENT_EXTRA_LIST_IMAGE_ARRAY);
            int pos = getIntent().getIntExtra(commonVariables.INTENT_EXTRA_POSITION, 0);

            final PagerAdapter pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
            page = (ExtendedViewPager) findViewById(R.id.pager);
            mIndicator = (CirclePageIndicator) findViewById(R.id.indicator);
            page.setOffscreenPageLimit(2);
            page.setAdapter(pagerAdapter);
            page.setCurrentItem(pos);
            page.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {

                    if (position > 0) {
                        View view = page.getChildAt(position - 1);
                        if (view != null) {
                            TouchImageView img = (TouchImageView) view.findViewById(R.id.imageView);
                            img.resetZoom();
                        }
                    }
                    if (position < page.getChildCount() - 1) {
                        View view = page.getChildAt(position + 1);
                        if (view != null) {
                            TouchImageView img = (TouchImageView) view.findViewById(R.id.imageView);
                            img.resetZoom();
                        }
                    }
                    for (int i = 0; i < page.getAdapter().getCount(); i++) {
                        View view = page.getChildAt(i);
                        if (view != null) {
                            TouchImageView img = (TouchImageView) view.findViewById(R.id.imageView);
                            img.resetZoom();
                        }
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
            mIndicator.setViewPager(page);
            mIv_backImage = (ImageView) findViewById(R.id.iv_back_pager);
            mIv_backImage.setOnClickListener(this);
            mIv_nextImage = (ImageView) findViewById(R.id.iv_next_pager);
            mIv_nextImage.setOnClickListener(this);
            mIv_close = (ImageView) findViewById(R.id.iv_close);
            mIv_close.setOnClickListener(this);
            if (IMAGES != null && IMAGES.length == 1) {
                mIv_backImage.setVisibility(View.GONE);
                mIv_nextImage.setVisibility(View.GONE);
                mIndicator.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            ViewPagerFragment fragment = new ViewPagerFragment();
            fragment.setImagePath(IMAGES[position]);
            fragment.resetImages();
            return fragment;
        }

        @Override
        public int getCount() {
            return IMAGES.length;
        }
    }

    @Override
    public void onClick(View v) {
        try {
            if (v == mIv_backImage) {
                if (page.getCurrentItem() == 0)
                    page.setCurrentItem(IMAGES.length - 1, true);
                else
                    page.setCurrentItem(getItem(-1), true);
            } else if (v == mIv_nextImage) {
                if (page.getCurrentItem() == IMAGES.length - 1)
                    page.setCurrentItem(0, true);
                else
                    page.setCurrentItem(getItem(+1), true);
            } else if (v == mIv_close)
                onBackPressed();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int getItem(int i) {
        return page.getCurrentItem() + i;
    }
}
