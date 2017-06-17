package com.atguigu.shoppingone_0224.type.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.atguigu.shoppingone_0224.R;
import com.atguigu.shoppingone_0224.type.bean.TypeBean;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 作者：田学伟 on 2017/6/17 10:19
 * QQ：93226539
 * 作用：
 */

public class TypeRightAdapter extends RecyclerView.Adapter {
    private final Context mContext;
    private final List<TypeBean.ResultEntity.ChildEntity> child;
    private final List<TypeBean.ResultEntity.HotProductListEntity> hot_product_list;

    private LayoutInflater inflater;
    /**
     * 热卖推荐
     */
    private static final int HOT = 0;
    /**
     * 常规分类
     */
    private static final int COMMON = 1;
    private int currentType = HOT;

    public TypeRightAdapter(Context mContext, List<TypeBean.ResultEntity> result) {
        this.mContext = mContext;
        child = result.get(0).getChild();//得到-常用分类
        hot_product_list = result.get(0).getHot_product_list();//热卖数据集合
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == HOT) {
            currentType = HOT;
        } else {
            currentType = COMMON;
        }
        return currentType;
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == HOT) {
            return new HotViewHolder(inflater.inflate(R.layout.item_hot_right, null));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == HOT) {
            HotViewHolder viewHolder = (HotViewHolder) holder;
            viewHolder.setData(hot_product_list);
        }
    }

    class HotViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.ll_hot_right)
        LinearLayout llHotRight;
        @InjectView(R.id.hsl_hot_right)
        HorizontalScrollView hslHotRight;

        public HotViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }

        /**
         * 传入集合
         * @param hot_product_list
         */
        public void setData(List<TypeBean.ResultEntity.HotProductListEntity> hot_product_list) {
            for (int i=0;i<hot_product_list.size();i++){

                //创建线性布局

                //创建图片,斌贴切添加到线性布局

                //创建文本,设置文本信息,并且添加到线性布局

                //要把当前的线性布局添加到外面的线性不居中

            }
        }
    }
}
