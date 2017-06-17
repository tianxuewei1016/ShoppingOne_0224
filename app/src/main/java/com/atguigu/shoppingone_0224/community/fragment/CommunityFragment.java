package com.atguigu.shoppingone_0224.community.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;

import com.atguigu.shoppingone_0224.R;
import com.atguigu.shoppingone_0224.activity.MainActivity;
import com.atguigu.shoppingone_0224.base.BaseFragment;
import com.atguigu.shoppingone_0224.community.adapter.CommunityViewPagerAdapter;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 作者：田学伟 on 2017/6/11 15:16
 * QQ：93226539
 * 作用：
 */

public class CommunityFragment extends BaseFragment {


    @InjectView(R.id.ib_community_icon)
    ImageButton ibCommunityIcon;
    @InjectView(R.id.ib_community_message)
    ImageButton ibCommunityMessage;
    @InjectView(R.id.tablayout)
    TabLayout tablayout;
    @InjectView(R.id.view_pager)
    ViewPager viewPager;

    private ArrayList<BaseFragment> fragments;
    private CommunityViewPagerAdapter pagerAdapter;

    @Override
    public View initView() {
        View rootView = View.inflate(mContext, R.layout.fragment_community, null);
        ButterKnife.inject(this, rootView);
        return rootView;
    }

    /**
     * 1.把数据绑定到控件上的时候，重新该方法
     * 2.联网请求，把得到的数据绑定到视图上
     */
    @Override
    public void initData() {
        super.initData();
        fragments = new ArrayList<>();
        fragments.add(new NewPostFragment());
        fragments.add(new HotPostFragment());

        MainActivity mainActivity = (MainActivity) mContext;
        //设置适配器
        pagerAdapter = new CommunityViewPagerAdapter(mainActivity.getSupportFragmentManager(), fragments);
        viewPager.setAdapter(pagerAdapter);

        tablayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @OnClick({R.id.ib_community_icon, R.id.ib_community_message})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ib_community_icon:
                break;
            case R.id.ib_community_message:
                break;
        }
    }
}
