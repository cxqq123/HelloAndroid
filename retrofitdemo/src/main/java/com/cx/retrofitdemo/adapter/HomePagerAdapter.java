package com.cx.retrofitdemo.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.cx.retrofitdemo.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * FragmentPagerAdapter
 * Created by Administrator on 2015/6/12.
 */
public class HomePagerAdapter extends FragmentPagerAdapter{
    private final List<BaseFragment> mFragments = new ArrayList<>();
    private final List<String> mFragmentTitles = new ArrayList<>();

    public HomePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addFragment(BaseFragment fragment, String title) {
        mFragments.add(fragment);
        mFragmentTitles.add(title);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitles.get(position);
    }
}

