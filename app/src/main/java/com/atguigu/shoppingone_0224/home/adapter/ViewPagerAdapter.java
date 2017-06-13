package com.atguigu.shoppingone_0224.home.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.atguigu.shoppingone_0224.home.bean.HomeBean;
import com.atguigu.shoppingone_0224.utils.Constants;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * 作者：田学伟 on 2017/6/12 16:29
 * QQ：93226539
 * 作用：
 */

public class ViewPagerAdapter extends PagerAdapter {
    private final Context mContext;
    private final List<HomeBean.ResultEntity.ActInfoEntity> datas;

    public ViewPagerAdapter(Context mContext, List<HomeBean.ResultEntity.ActInfoEntity> act_info) {
        this.mContext = mContext;
        this.datas = act_info;
    }

    @Override
    public int getCount() {
        return datas == null ? 0 : datas.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        ImageView imageView = new ImageView(mContext);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        HomeBean.ResultEntity.ActInfoEntity actInfoEntity = datas.get(position);
        //联网请求
        Glide.with(mContext)
                .load(Constants.BASE_URL_IMAGE + actInfoEntity.getIcon_url())
                .into(imageView);
        container.addView(imageView);
        //设置点击事件
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(clickListener!=null) {
                    clickListener.onItemClick(position);
                }
            }
        });

        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public interface OnItemClickListener {
        public void onItemClick(int position);
    }

    private OnItemClickListener clickListener;

    public void setOnItemClickListener(OnItemClickListener l) {
        this.clickListener = l;
    }
}
