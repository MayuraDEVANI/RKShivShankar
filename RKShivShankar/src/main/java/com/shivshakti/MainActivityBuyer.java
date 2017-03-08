package com.shivshakti;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Html;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.arlib.floatingsearchview.util.Util;
import com.shivshakti.ServerCall.APIs;
import com.shivshakti.classes.DataHelper;
import com.shivshakti.classes.Suggestion;
import com.shivshakti.utills.ExceptionHandler;
import com.shivshakti.utills.OnResult;
import com.shivshakti.utills.commonMethods;

import org.json.JSONObject;

public class MainActivityBuyer extends BaseActivityBuyer implements View.OnClickListener, OnResult {
    LinearLayout mLl_view;
    SwipeRefreshLayout swipeRefreshLayout;
    private FloatingSearchView mSearchView;
    private boolean mIsDarkSearchTheme = false;
    private String mLastQuery = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        super.onCreate(savedInstanceState);
        try {
            View rootView = getLayoutInflater().inflate(R.layout.activity_main_buyer, frameLayout);

            mSearchView = (FloatingSearchView) rootView.findViewById(R.id.floating_search_view);
            EditText editText = (EditText) mSearchView.findViewById(R.id.search_bar_text);
            editText.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.text_size_medium));
            mLl_view = (LinearLayout) rootView.findViewById(R.id.ll_view);
            setupFloatingSearch();

            FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
            fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show());

            APIs.GetHomeBannerwithText(this, this);
            swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    swipeRefreshLayout.setProgressViewOffset(false, 0, 35);
                }
                swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_red_light, android.R.color.holo_green_light, android.R.color.holo_purple, android.R.color.holo_blue_light);
                swipeRefreshLayout.setOnRefreshListener(() -> {
                    APIs.GetHomeBannerwithText(MainActivityBuyer.this, MainActivityBuyer.this);
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onClick(View view) {

    }

    @Override
    public void onResult(JSONObject result) {
        swipeRefreshLayout.setRefreshing(false);
        Log.v("TAG", "RESULT: " + result.toString());
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            setupFloatingSearch();
            DataHelper.sColorSuggestions = commonMethods.getSavedSearchArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupFloatingSearch() {
        mSearchView.setOnQueryChangeListener((oldQuery, newQuery) -> {

            if (!oldQuery.equals("") && newQuery.equals("")) {
                mSearchView.clearSuggestions();
            } else {
                mSearchView.showProgress();
                DataHelper.findSuggestions(MainActivityBuyer.this, newQuery, 5,
                        250, results -> {
                            mSearchView.swapSuggestions(results);
                            mSearchView.hideProgress();
                        });
            }
        });

        mSearchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(final SearchSuggestion searchSuggestion) {
                Suggestion colorSuggestion = (Suggestion) searchSuggestion;
                commonMethods.performSearch(MainActivityBuyer.this, colorSuggestion.getBody());
                mLastQuery = searchSuggestion.getBody();
            }

            @Override
            public void onSearchAction(String query) {
                mLastQuery = query;
                commonMethods.performSearch(MainActivityBuyer.this, query);
            }
        });

        mSearchView.setOnFocusChangeListener(new FloatingSearchView.OnFocusChangeListener() {
            @Override
            public void onFocus() {
                DataHelper.sColorSuggestions = commonMethods.getSavedSearchArray();
                mSearchView.swapSuggestions(DataHelper.getHistory(MainActivityBuyer.this, 3));
            }

            @Override
            public void onFocusCleared() {
                DataHelper.sColorSuggestions = commonMethods.getSavedSearchArray();
                mSearchView.setSearchBarTitle(mLastQuery);
            }
        });

        mSearchView.setOnBindSuggestionCallback((suggestionView, leftIcon, textView, item, itemPosition) -> {
            Suggestion colorSuggestion = (Suggestion) item;

            String textColor = mIsDarkSearchTheme ? "#ffffff" : "#000000";
            String textLight = mIsDarkSearchTheme ? "#bfbfbf" : "#787878";

            if (colorSuggestion.getIsHistory()) {
                leftIcon.setImageDrawable(ResourcesCompat.getDrawable(getResources(),
                        R.drawable.ic_history_black_24dp, null));

                Util.setIconColor(leftIcon, Color.parseColor(textColor));
                leftIcon.setAlpha(.36f);
            } else {
                leftIcon.setAlpha(0.0f);
                leftIcon.setImageDrawable(null);
            }

            textView.setTextColor(Color.parseColor(textColor));
            String text = colorSuggestion.getBody()
                    .replaceFirst(mSearchView.getQuery(),
                            "<font color=\"" + textLight + "\">" + mSearchView.getQuery() + "</font>");
            textView.setText(Html.fromHtml(text));
        });

        mSearchView.setOnSuggestionsListHeightChanged(newHeight -> mLl_view.setTranslationY(newHeight));
    }

}
