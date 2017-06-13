package com.atguigu.shoppingone_0224.home.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.atguigu.shoppingone_0224.R;
import com.atguigu.shoppingone_0224.home.bean.HomeBean;
import com.atguigu.shoppingone_0224.utils.Constants;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 作者：田学伟 on 2017/6/13 11:44
 * QQ：93226539
 * 作用：
 */

public class HotGridViewAdapter extends BaseAdapter{
    private final Context mContext;
    private final List<HomeBean.ResultEntity.HotInfoEntity> datas;

    public HotGridViewAdapter(Context mContext, List<HomeBean.ResultEntity.HotInfoEntity> hot_info) {
        this.mContext = mContext;
        this.datas = hot_info;
    }

    @Override
    public int getCount() {
        return datas == null ? 0 : datas.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_hot_grid_view, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        HomeBean.ResultEntity.HotInfoEntity hotInfoEntity = datas.get(position);
        viewHolder.tvName.setText(hotInfoEntity.getName());
        viewHolder.tvPrice.setText("￥"+hotInfoEntity.getCover_price());
        Glide.with(mContext).load(Constants.BASE_URL_IMAGE+hotInfoEntity.getFigure()).into(viewHolder.ivHot);
        return convertView;
    }

    static class ViewHolder {
        @InjectView(R.id.iv_hot)
        ImageView ivHot;
        @InjectView(R.id.tv_name)
        TextView tvName;
        @InjectView(R.id.tv_price)
        TextView tvPrice;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
