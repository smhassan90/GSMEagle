package com.greenstar.eagle.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.greenstar.eagle.controller.qtv.PendingFormsBasket;
import com.greenstar.eagle.controller.qtv.RejectedFormBasket;
import com.greenstar.eagle.controller.qtv.SuccessfulFormBasket;

public class SubmittedFormAdapter extends FragmentStatePagerAdapter {
    final int noOfTabs=3;

    public SubmittedFormAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return new PendingFormsBasket();
            case 1: return new RejectedFormBasket();
            case 2: return new SuccessfulFormBasket();
        }

        return null;
    }

    @Override
    public int getCount() {
        return noOfTabs;
    }
}
