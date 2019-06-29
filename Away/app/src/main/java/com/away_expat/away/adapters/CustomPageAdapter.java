package com.away_expat.away.adapters;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.away_expat.away.fragments.SearchActivityFragment;
import com.away_expat.away.fragments.SearchEventFragment;
import com.away_expat.away.fragments.SearchTagFragment;
import com.away_expat.away.fragments.SearchUserFragment;

public class CustomPageAdapter extends FragmentStatePagerAdapter {

    private SearchActivityFragment saf = new SearchActivityFragment();
    private SearchEventFragment sef = new SearchEventFragment();
    private SearchTagFragment stf = new SearchTagFragment();
    private SearchUserFragment suf = new SearchUserFragment();

    public CustomPageAdapter(FragmentManager fm){
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return saf;
            case 1: return sef;
            case 2: return stf;
            case 3: return suf;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return null;
    }

    public void updateList(int position, String search, Activity mainActivity) {
        switch (position){
            case 0:
                saf.updateListView(search, mainActivity);
                break;
            case 1:
                sef.updateListView(search, mainActivity);
                break;
            case 2:
                stf.updateListView(search, mainActivity);
                break;
            case 3:
                suf.updateListView(search, mainActivity);
                break;
        }
    }

    public void resetFrag() {
        saf = new SearchActivityFragment();
        sef = new SearchEventFragment();
        stf = new SearchTagFragment();
        suf = new SearchUserFragment();
    }
}