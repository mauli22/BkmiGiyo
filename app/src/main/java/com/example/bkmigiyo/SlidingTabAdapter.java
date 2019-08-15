package com.example.bkmigiyo;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;


public class SlidingTabAdapter extends FragmentStatePagerAdapter {

    ArrayList<Fragment> pages = new ArrayList<>();

    public SlidingTabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if(position==0){
            return new Fragment1();
        }else if(position==1){
            return new fragment2();
        }else {
            return new fragment3();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    public  CharSequence getPageTittle (int position){
        return pages.get(position).toString();
    }

    public void addpage(Fragment e){
        pages.add(e);
    }
}

