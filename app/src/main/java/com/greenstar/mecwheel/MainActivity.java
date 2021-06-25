package com.greenstar.mecwheel;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.greenstar.mecwheel.crb.controller.CRBFormActivity;
import com.greenstar.mecwheel.crb.controller.Codes;
import com.greenstar.mecwheel.crb.controller.LoginScreen;
import com.greenstar.mecwheel.crb.controller.Menu;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static String FACEBOOK_URL = "https://www.facebook.com/greenstarsm/";
    public static String FACEBOOK_PAGE_ID = "greenstarsm";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabu);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:080011171"));
                startActivity(intent);
            }
        });
        Fragment fragment = new HomeFragment();

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, fragment);
        ft.commit();
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
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;
        Intent intent = null;
        if (id == R.id.btnMecWheel) {
            fragment = new MECWheelFragment();

        } else if (id == R.id.cards) {
            fragment = new CounselingCardsFragment();
        }else if (id == R.id.patient_entry) {
            SharedPreferences shared = getSharedPreferences(Codes.PREF_NAME, MODE_PRIVATE);
            boolean isLoggedIn = (shared.getBoolean("isLoggedIn", false));
            if(isLoggedIn){
                intent = new Intent(this, Menu.class);
            }else{
                intent = new Intent(this, LoginScreen.class);
            }
        }else if (id == R.id.dosage_card) {
            fragment = new DosageCard();
        }else if (id == R.id.enofer) {
            fragment = new Enofer();
        }
        else if (id == R.id.PharmaProducts) {
            fragment = new PharmaProducts();
        }else if (id == R.id.btnHome) {
            fragment = new HomeFragment();
        }else if (id == R.id.btnYoutubeChannel) {
            intent = new Intent(Intent.ACTION_VIEW,   Uri.parse("http://www.youtube.com/channel/UCngBt6z8vVjtDje70Xw7v8Q"));
        }else if (id == R.id.btnFacebook) {
            intent = new Intent(Intent.ACTION_VIEW);
            String facebookUrl = getFacebookPageURL();
            intent.setData(Uri.parse(facebookUrl));
        }

        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }else{
            startActivity(intent);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public String getFacebookPageURL() {
        PackageManager packageManager = getPackageManager();
        try {
            int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;
            if (versionCode >= 3002850) { //newer versions of fb app
                return "fb://facewebmodal/f?href=" + FACEBOOK_URL;
            } else { //older versions of fb app
                return "fb://page/" + FACEBOOK_PAGE_ID;
            }
        } catch (PackageManager.NameNotFoundException e) {
            return FACEBOOK_URL; //normal web url
        }
    }

    public Intent getYoutubeChannel() {
        try {
            return new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/426253597411506"));
        } catch (Exception e) {
            return new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/appetizerandroid"));
        }
    }
}
