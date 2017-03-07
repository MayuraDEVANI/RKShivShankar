package com.shivshakti.viewpager;

import android.app.DownloadManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.shivshakti.R;
import com.shivshakti.customcontrols.TouchImageView;
import com.shivshakti.utills.MyRequestListener;

import static android.content.Context.DOWNLOAD_SERVICE;

public class ViewPagerFragment extends Fragment implements View.OnClickListener {

    private static final String BUNDLE_ASSET = "asset";
    TouchImageView imageView;
    private String imagePath;
    FloatingActionButton fab_download;

    public ViewPagerFragment() {
    }

    public void setImagePath(String asset) {
        this.imagePath = asset;
    }

    public void resetImages() {
        if (this.imageView != null)
            this.imageView.resetZoom();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.view_pager_page, container, false);


        if (savedInstanceState != null) {
            if (imagePath == null && savedInstanceState.containsKey(BUNDLE_ASSET)) {
                imagePath = savedInstanceState.getString(BUNDLE_ASSET);
            }
        }
        if (imagePath != null) {
            imageView = (TouchImageView) rootView.findViewById(R.id.imageView);
            fab_download = (FloatingActionButton) rootView.findViewById(R.id.fab_download);
            fab_download.setOnClickListener(this);
            ImageView mPbar_product = (ImageView) rootView.findViewById(R.id.pbar_product);
            setImage(imageView, imagePath + "", mPbar_product);
        }
        return rootView;
    }

    private void setImage(final ImageView imageView, String strImageURL, final ImageView mPbar_product) {
        try {
            if ((strImageURL != null) && !(strImageURL.equals(""))) {
                strImageURL = strImageURL.replace("/thumb/", "/resized/");
                Glide.with(getActivity()).load(strImageURL).asBitmap().listener(new MyRequestListener(imageView)).error(R.drawable.ic_menu_camera).into(imageView);
//                commonVariables.imageLoader.displayImage(imageToolTipURL, imageView, commonVariables.options_3d_loader);
//                , new SimpleImageLoadingListener() {
//                    @Override
//                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
//                        mPbar_product.setVisibility(View.GONE);
//                    }
//
//                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        View rootView = getView();
        if (rootView != null) {
            outState.putString(BUNDLE_ASSET, imagePath);
        }
    }

    @Override
    public void onClick(View v) {
        try {
            if (v == fab_download) {
                String url = imagePath;
                DownloadManager.Request request = new DownloadManager.Request(
                        Uri.parse(url));

                request.allowScanningByMediaScanner();
                String fName = url.substring(url.lastIndexOf('/') + 1);
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED); //Notify client once download is completed!
                request.setTitle(fName);
                request.setDestinationInExternalPublicDir("Happy Home", fName);
                DownloadManager dm = (DownloadManager) getActivity().getSystemService(DOWNLOAD_SERVICE);
                dm.enqueue(request);
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT); //This is important!
                intent.addCategory(Intent.CATEGORY_OPENABLE); //CATEGORY.OPENABLE
                intent.setType("*/*");//any application,any extension
                Toast.makeText(getActivity(), "Downloading File", //To notify the Client that the file is being downloaded
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
