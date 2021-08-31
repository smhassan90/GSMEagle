package com.greenstar.eagle.controller.qtv;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.greenstar.eagle.R;
import com.greenstar.eagle.adapters.ApprovalFormAdapter;

public class ApprovalStatus extends AppCompatActivity implements ApprovalPendingFormBasket.OnFragmentInteractionListener,
        ApprovalSuccessfulFormBasket.OnFragmentInteractionListener, ApprovalRejectedFormBasket.OnFragmentInteractionListener{
    TabLayout tlStatusTab = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.submitted_forms);

        tlStatusTab = findViewById(R.id.tlStatusTab);
        tlStatusTab.addTab(tlStatusTab.newTab().setText("Pending"));
        tlStatusTab.addTab(tlStatusTab.newTab().setText("Rejected"));
        tlStatusTab.addTab(tlStatusTab.newTab().setText("Successful"));
        tlStatusTab.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager vpStatus = findViewById(R.id.vpStatusPager);
        ApprovalFormAdapter approvalFormAdapter = new ApprovalFormAdapter(getSupportFragmentManager());
        vpStatus.setAdapter(approvalFormAdapter);
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
}
