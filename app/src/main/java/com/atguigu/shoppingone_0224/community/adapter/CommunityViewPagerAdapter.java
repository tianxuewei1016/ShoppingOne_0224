package com.atguigu.shoppingone_0224.community.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.atguigu.shoppingone_0224.base.BaseFragment;

import java.util.ArrayList;

/**
 * 作者：田学伟 on 2017/6/17 13:49
 * QQ：93226539
 * 作用：
 */

public class CommunityViewPagerAdapter extends FragmentPagerAdapter{
    private final ArrayList<BaseFragment> fragments;

    public CommunityViewPagerAdapter(FragmentManager fm, ArrayList<BaseFragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
