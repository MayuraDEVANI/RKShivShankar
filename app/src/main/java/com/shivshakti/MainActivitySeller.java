package com.shivshakti;

import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.shivshakti.ServerCall.APIs;
import com.shivshakti.classes.DataHelper;
import com.shivshakti.utills.ExceptionHandler;
import com.shivshakti.utills.OnResult;
import com.shivshakti.utills.commonMethods;

import org.json.JSONObject;

public class MainActivitySeller extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, OnResult {
    Toolbar toolbar;
    public static final long FIND_SUGGESTION_SIMULATED_DELAY = 250;

    private TextView mSearchView;
    RelativeLayout mLl_view;

    private boolean mIsDarkSearchTheme = false;
    public TextView mTv_cart_count;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_main_seller);
            toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setLogo(R.drawable.ic_logo);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);


//            new ServerSMS().execute();
//            mLl_cart = (LinearLayout) findViewById(R.id.ll_cart);
//            mLl_cart.setOnClickListener(this);
//            mTv_cart_count = (TextView) findViewById(R.id.tv_cart_count);
//

            mSearchView = (TextView) findViewById(R.id.floating_search_view);
            mLl_view = (RelativeLayout) findViewById(R.id.ll_view);

            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show());

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            toggle.setDrawerIndicatorEnabled(false);
            toolbar.setNavigationIcon(R.drawable.ic_menu);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    drawer.openDrawer(Gravity.LEFT);
                }
            });
            drawer.addDrawerListener(toggle);
            toggle.syncState();

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);

            APIs.GetHomeBannerwithText(this, this);
            swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    swipeRefreshLayout.setProgressViewOffset(false, 0, 200);
                }
                swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_green_dark, android.R.color.holo_green_light);
                swipeRefreshLayout.setOnRefreshListener(() -> {
                    APIs.GetHomeBannerwithText(this, this);
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        try {
            MenuInflater menuInflater = getMenuInflater();
            menuInflater.inflate(R.menu.main, menu);
            final View menu_layout = menu.findItem(R.id.action_settings).getActionView();
            mTv_cart_count = (TextView) menu_layout.findViewById(R.id.tv_cart_count);

            updateHotCount(3);
            commonMethods.cartCountAnimation(this, mTv_cart_count);
            new MyMenuItemStuffListener(menu_layout, "Show hot message") {
                @Override
                public void onClick(View v) {
                    commonMethods.cartCountAnimation(MainActivitySeller.this, mTv_cart_count);
                }
            };

        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.onCreateOptionsMenu(menu);
    }

    // so we can call this asynchronously
    public void updateHotCount(final int count) {
        if (mTv_cart_count == null) return;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (count > 0) {
                    mTv_cart_count.setVisibility(View.VISIBLE);
                    mTv_cart_count.setText(count + "");
                } else
                    mTv_cart_count.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onResult(JSONObject result) {
        swipeRefreshLayout.setRefreshing(false);
        Log.v("TAG", "RESULT: " + result.toString());
    }

    static abstract class MyMenuItemStuffListener implements View.OnClickListener, View.OnLongClickListener {
        private String hint;
        private View view;

        MyMenuItemStuffListener(View view, String hint) {
            this.view = view;
            this.hint = hint;
            view.setOnClickListener(this);
            view.setOnLongClickListener(this);
        }

        @Override
        abstract public void onClick(View v);

        @Override
        public boolean onLongClick(View v) {
            final int[] screenPos = new int[2];
            final Rect displayFrame = new Rect();
            view.getLocationOnScreen(screenPos);
            view.getWindowVisibleDisplayFrame(displayFrame);
            final Context context = view.getContext();
            final int width = view.getWidth();
            final int height = view.getHeight();
            final int midy = screenPos[1] + height / 2;
            final int screenWidth = context.getResources().getDisplayMetrics().widthPixels;
            Toast cheatSheet = Toast.makeText(context, hint, Toast.LENGTH_SHORT);
            if (midy < displayFrame.height()) {
                cheatSheet.setGravity(Gravity.TOP | Gravity.RIGHT,
                        screenWidth - screenPos[0] - width / 2, height);
            } else {
                cheatSheet.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, height);
            }
            cheatSheet.show();
            return true;
        }
    }
   /* public class ServerSMS extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {
            return sendSms();
        }
    }
    public String sendSms() {
        try {
            // Construct data
            String user = "username=" + "zalakdoors@gmail.com";
            String hash = "&hash=" + "1c9dcb84aff3c39f6112e51dd75da09553d2dfbfa6e600277b8331ce8d586cad";
            String message = "&message=" + "This is your message";
            String sender = "&sender=" + "TXTLCL";
            String numbers = "&numbers=" + "917572807928";

            // Send data
            HttpURLConnection conn = (HttpURLConnection) new URL("http://api.textlocal.in/send/?").openConnection();
            String data = user + hash + numbers + message + sender;
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
            conn.getOutputStream().write(data.getBytes("UTF-8"));
            final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            final StringBuffer stringBuffer = new StringBuffer();
            String line;
            while ((line = rd.readLine()) != null) {
                stringBuffer.append(line);
            }
            rd.close();
            Log.v(commonVariables.TAG, "RESPONSE: " + stringBuffer.toString());
            return stringBuffer.toString();
        } catch (Exception e) {
            System.out.println("Error SMS " + e);
            return "Error " + e;
        }
    }*/

    @Override
    protected void onResume() {
        super.onResume();
        try {
            DataHelper.sColorSuggestions = commonMethods.getSavedSearchArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
        } else if (id == R.id.nav_gallery) {
        } else if (id == R.id.nav_slideshow) {
        } else if (id == R.id.nav_manage) {
        } else if (id == R.id.nav_share) {
        } else if (id == R.id.nav_send) {
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}