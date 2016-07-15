package com.example.niki.fieldoutlookandroid.helper.array_adapters;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v13.app.FragmentStatePagerAdapter;


import com.example.niki.fieldoutlookandroid.businessobjects.Quote;
import com.example.niki.fieldoutlookandroid.fragment.FlatRateItemListFragment;
import com.example.niki.fieldoutlookandroid.fragment.PartListFragment;

import java.util.Stack;

/**
 * Created by Nicole on 6/24/2016.
 */
public class MyPagerAdapter extends FragmentStatePagerAdapter {

    private Quote selectedQuote;

    public MyPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }
    Bundle b =new Bundle();
    public Fragment firstPositionFragment=new PartListFragment();
    public Fragment secondPositionFragment=new FlatRateItemListFragment();

    private Stack<Fragment> firstFragmentStack =new Stack<Fragment>();
    private Stack<Fragment> secondFragmentStack=new Stack<Fragment>();

    private int position=0;

    public void setSelectedQuote(Quote quote){
        this.selectedQuote=quote;
    }

    @Override
    public CharSequence getPageTitle(int position){

        switch (position){
            case 0:
                return "Part List";
            case 1:
                return "Flat Rate Items";
            default:
                return "";
        }
    }

    public void setFirstPositionFragment(Fragment firstPositionFragment) {
        this.firstPositionFragment = firstPositionFragment;

        firstFragmentStack.push(this.firstPositionFragment);
        notifyDataSetChanged();
       // this.notifyDataSetChanged();

    }
    public int backPressed(){
        if(position==0) {
            if(firstFragmentStack.isEmpty()||firstFragmentStack.size()==1){
                return 0;
            }
            this.firstPositionFragment=firstFragmentStack.pop();
            if(!firstFragmentStack.isEmpty()) {
                this.firstPositionFragment = firstFragmentStack.pop();
            }
        }else{
            if(secondFragmentStack.isEmpty()|| secondFragmentStack.size()==1){
                return 0;
            }
            this.secondPositionFragment=secondFragmentStack.pop();
            if(!secondFragmentStack.isEmpty()) {
                this.secondPositionFragment = secondFragmentStack.pop();
            }
        }
        this.notifyDataSetChanged();
        return 1;
    }
    public void setCurrentPosition(int position){
        this.position=position;
    }
    @Override
    public int getItemPosition(Object object){
        return POSITION_NONE;
    }

    @Override
    public Fragment getItem(int position) {
        if(firstFragmentStack.isEmpty()){
            firstFragmentStack.push(firstPositionFragment);
        }
        if(secondFragmentStack.isEmpty()){
            secondFragmentStack.push(secondPositionFragment);
        }


        switch (position) {
            case (0):
               // Bundle b=new Bundle();
               // b.putParcelable("selectedquote",selectedQuote);
                //firstPositionFragment.setArguments(b);
                return firstPositionFragment;
            case (1):
                return secondPositionFragment;
            default:
                return null;
        }
    }
    @Override
    public int getCount() {
        return 2;
    }
}
