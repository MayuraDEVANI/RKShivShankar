package com.shivshakti.classes;

import android.support.v4.app.Fragment;

/**
 * Created by Mayura on 2/27/2017.
 */

public class HomeTab {

    String title;
    Fragment faragment;

    public HomeTab(String title, Fragment faragment) {
        this.title = title;
        this.faragment = faragment;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Fragment getFaragment() {
        return faragment;
    }

    public void setFaragment(Fragment faragment) {
        this.faragment = faragment;
    }
}
