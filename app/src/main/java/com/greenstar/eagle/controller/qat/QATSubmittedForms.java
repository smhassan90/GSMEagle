package com.greenstar.eagle.controller.qat;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.greenstar.eagle.R;
import com.greenstar.eagle.adapters.qat.QATSubmittedFormAdapter;

public class QATSubmittedForms extends AppCompatActivity implements QATPendingFormsBasket.OnFragmentInteractionListener,
        QATSuccessfulFormBasket.OnFragmentInteractionListener, QATRejectedFormBasket.OnFragmentInteractionListener{
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
        QATSubmittedFormAdapter submittedFormAdapter = new QATSubmittedFormAdapter(getSupportFragmentManager());
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
}
