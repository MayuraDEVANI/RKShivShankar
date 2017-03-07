package com.shivshakti.fragments;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shivshakti.R;
import com.shivshakti.adapters.GalleryAdapter;

import java.util.ArrayList;

public class GalleryListFragment extends Fragment {

    RecyclerView recyclerView;
    boolean isClick;
    ArrayList<String> listImages;

    private GalleryAdapter galleryListAdapter;

    public GalleryListFragment() {
    }

    public GalleryListFragment(boolean isclick) {
        isClick = isclick;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);
        try {
            recyclerView = (RecyclerView) view.findViewById(R.id.scroll);
            listImages = new ArrayList<>();
            listImages.add("http://www.planwallpaper.com/static/images/HD-Wallpapers1.jpeg");
            listImages.add("https://www.planwallpaper.com/static/images/i-should-buy-a-boat.jpg");
            listImages.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTNKi6h33Gtbzf-N8RMsb71QOpza374Ia7VnEKw-7g_5w_eS3U1");
            listImages.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ-p-7mzqLue7UjJvCQ8DegLHGxVDcO3cBW_D40_1NCscaiSjQm");
            listImages.add("http://www.planwallpaper.com/static/images/HD-Wallpapers1.jpeg");
            listImages.add("http://www.planwallpaper.com/static/images/HD-Wallpapers1.jpeg");
            listImages.add("http://www.planwallpaper.com/static/images/HD-Wallpapers1.jpeg");
            listImages.add("http://www.planwallpaper.com/static/images/HD-Wallpapers1.jpeg");
            listImages.add("http://www.planwallpaper.com/static/images/HD-Wallpapers1.jpeg");
            listImages.add("http://www.planwallpaper.com/static/images/HD-Wallpapers1.jpeg");
            listImages.add("http://www.planwallpaper.com/static/images/HD-Wallpapers1.jpeg");
            listImages.add("https://www.planwallpaper.com/static/images/i-should-buy-a-boat.jpg");


            setData(listImages);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public void setData(ArrayList<String> listImages) {
        try {
            galleryListAdapter = new GalleryAdapter((AppCompatActivity) getActivity(), listImages, recyclerView, true, isClick);

            int i = 2;
            if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                i = 4;
            }
            GridLayoutManager manager = new GridLayoutManager(getActivity(), i);

            recyclerView.setLayoutManager(manager);
            recyclerView.setLayoutManager(manager);
//            recyclerView.setHasFixedSize(true);
//            recyclerView.setNestedScrollingEnabled(false);
            recyclerView.setAdapter(galleryListAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
