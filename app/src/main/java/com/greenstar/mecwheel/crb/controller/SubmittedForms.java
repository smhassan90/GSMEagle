package com.greenstar.mecwheel.crb.controller;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.greenstar.mecwheel.R;
import com.greenstar.mecwheel.crb.adapter.SubmittedFormAdapter;

public class SubmittedForms extends AppCompatActivity implements PendingFormsBasket.OnFragmentInteractionListener,
        SuccessfulFormBasket.OnFragmentInteractionListener{

    TabLayout tlStatusTab = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setContentView(R.layout.submitted_forms);

        tlStatusTab = findViewById(R.id.tlStatusTab);
        tlStatusTab.addTab(tlStatusTab.newTab().setText("Pending"));
        tlStatusTab.addTab(tlStatusTab.newTab().setText("Successful"));
        tlStatusTab.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager vpStatus = findViewById(R.id.vpStatusPager);
        SubmittedFormAdapter submittedFormAdapter = new SubmittedFormAdapter(getSupportFragmentManager());
        vpStatus.setAdapter(submittedFormAdapter);
        vpStatus.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tlStatusTab));
        tlStatusTab.setOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                vpStatus.setCurrentItem(tab.getPosition());

            }
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
