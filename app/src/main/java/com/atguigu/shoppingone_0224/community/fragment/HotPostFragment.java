package com.atguigu.shoppingone_0224.community.fragment;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.atguigu.shoppingone_0224.base.BaseFragment;

/**
 * 作者：田学伟 on 2017/6/17 13:50
 * QQ：93226539
 * 作用：
 */

public class HotPostFragment extends BaseFragment {
    private TextView textView;

    /**
     * 初始化控件
     * retur
     */
    @Override
    public View initView() {
        textView = new TextView(mContext);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(25);
        textView.setTextColor(Color.RED);
        return textView;
    }

    @Override
    public void initData() {
        super.initData();
        textView.setText("我是热帖内容");
    }
}

