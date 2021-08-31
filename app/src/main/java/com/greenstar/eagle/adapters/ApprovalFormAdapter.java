package com.greenstar.eagle.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.greenstar.eagle.controller.qtv.ApprovalPendingFormBasket;
import com.greenstar.eagle.controller.qtv.ApprovalRejectedFormBasket;
import com.greenstar.eagle.controller.qtv.ApprovalSuccessfulFormBasket;

public class ApprovalFormAdapter extends FragmentStatePagerAdapter {
    final int noOfTabs=3;

    public ApprovalFormAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return new ApprovalPendingFormBasket();
            case 1: return new ApprovalRejectedFormBasket();
            case 2: return new ApprovalSuccessfulFormBasket();
        }

        return null;
    }

    @Override
    public int getCount() {
        return noOfTabs;
    }
}
