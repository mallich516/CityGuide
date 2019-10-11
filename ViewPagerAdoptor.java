package com.example.loginpage.loginactivity;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdoptor extends FragmentPagerAdapter {

    private final List<Fragment> fragmentList = new ArrayList<>();
    private final List<String> fragmentlisttitles = new ArrayList<String>();


    public ViewPagerAdoptor(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        return fragmentList.get(i);
    }

    @Override
    public int getCount() {
        return fragmentlisttitles.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return (CharSequence) fragmentlisttitles.get(position);
    }

    public void addFragment(Fragment fragment,String Titles){

        fragmentList.add(fragment);
        fragmentlisttitles.add(Titles);

    }
}
