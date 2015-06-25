package com.juandavidarroyave.android.juangttest.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.juandavidarroyave.android.juangttest.ui.views.Tab;


public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    CharSequence Titles[];
    int NumbOfTabs;


    // Build a Constructor and assign the passed Values to appropriate values in the class
    public ViewPagerAdapter(FragmentManager fm,CharSequence mTitles[], int mNumbOfTabsumb) {
        super(fm);

        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;

    }


    @Override
    public Fragment getItem(int position) {

        Tab tab = new Tab();
        return tab;

        /*if(position == 0) // if the position is 0 we are returning the First tab
        {
            Tab tab1 = new Tab();
            return tab1;
        }
        else
        {
            Tab2 tab2 = new Tab2();
            return tab2;
        }*/


    }

    // This method returns the titles for the Tabs in the Tab Strip but for this game we are not using titles

    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }

    // This method return the Number of tabs for the tabs Strip

    @Override
    public int getCount() {
        return NumbOfTabs;
    }
}