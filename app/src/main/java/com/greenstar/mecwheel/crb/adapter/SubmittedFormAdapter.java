package com.greenstar.mecwheel.crb.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.greenstar.mecwheel.crb.controller.PendingFormsBasket;
import com.greenstar.mecwheel.crb.controller.SuccessfulFormBasket;


public class SubmittedFormAdapter extends FragmentStatePagerAdapter {
    final int noOfTabs=2;

    public SubmittedFormAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return new PendingFormsBasket();
            case 1: return new SuccessfulFormBasket();
        }

        return null;
    }

    @Override
    public int getCount() {
        return noOfTabs;
    }
}
