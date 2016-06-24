package com.example.niki.fieldoutlookandroid.helper.array_adapters;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentStatePagerAdapter;


import com.example.niki.fieldoutlookandroid.fragment.PartListFragment;

/**
 * Created by Nicole on 6/24/2016.
 */
public class MyPagerAdapter extends FragmentStatePagerAdapter {

    public MyPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case (0):
                return new PartListFragment();
            case (1):
                return new Fragment();
            default:
                return null;
        }
    }
    @Override
    public int getCount() {
        return 2;
    }
}
