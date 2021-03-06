package com.atguigu.shoppingone_0224.shoppingcart.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.atguigu.shoppingone_0224.R;
import com.atguigu.shoppingone_0224.activity.MyApplication;
import com.atguigu.shoppingone_0224.home.bean.GoodsBean;
import com.atguigu.shoppingone_0224.shoppingcart.utils.CartStorage;
import com.atguigu.shoppingone_0224.shoppingcart.view.AddSubView;
import com.atguigu.shoppingone_0224.utils.Constants;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 作者：田学伟 on 2017/6/14 15:25
 * QQ：93226539
 * 作用：
 */

public class ShoppingCartAdapter extends RecyclerView.Adapter<ShoppingCartAdapter.MyViewHolder> {
    private final Context mContext;
    private final ArrayList<GoodsBean> datas;
    private final CheckBox checkboxAll;
    private final TextView tvShopcartTotal;
    private final CheckBox cbAll;


    public ShoppingCartAdapter(Context mContext, ArrayList<GoodsBean> datas, CheckBox checkboxAll, TextView tvShopcartTotal, CheckBox cbAll) {
        this.mContext = mContext;
        this.datas = datas;
        this.checkboxAll = checkboxAll;
        this.tvShopcartTotal = tvShopcartTotal;
        this.cbAll = cbAll;
        showTotalPrice();
        checkAll();
    }

    /**
     * 重新显示总价格
     */
    public void showTotalPrice() {
        tvShopcartTotal.setText("合计:" + getTotalPrice());
    }

    private double getTotalPrice() {
        double result = 0;
        if (datas != null && datas.size() > 0) {
            for (int i = 0; i < datas.size(); i++) {
                GoodsBean goodsBean = datas.get(i);
                if (goodsBean.isChecked()) {
                    result = result + goodsBean.getNumber() * Double.parseDouble(goodsBean.getCover_price());
                }
            }
        }
        return result;
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(View.inflate(mContext, R.layout.item_shop_cart, null));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        //1.更加位置得到数据
        final GoodsBean goodsBean = datas.get(position);
        //2.绑定数据
        holder.cbGov.setChecked(goodsBean.isChecked());
        //图片
        Glide.with(mContext).load(Constants.BASE_URL_IMAGE + goodsBean.getFigure()).into(holder.ivGov);
        holder.tvDescGov.setText(goodsBean.getName());
        //设置价格
        holder.tvPriceGov.setText("￥" + goodsBean.getCover_price());

        holder.AddSubView.setValue(goodsBean.getNumber());
        holder.AddSubView.setMinValue(1);
        holder.AddSubView.setMaxValue(20);

        holder.AddSubView.setOnNumberChangeListener(new AddSubView.OnNumberChangeListener() {
            @Override
            public void numberChange(int number) {
                //把Bean对象更新一下
                goodsBean.setNumber(number);
                //更新存储到本地或者服务器上
                CartStorage.getInstance(MyApplication.getContext()).updateData(goodsBean);
            }
        });
    }

    public void checkAll_none(boolean checked) {
        if (datas != null && datas.size() > 0) {
            int number = 0;
            for (int i = 0; i < datas.size(); i++) {
                GoodsBean goodsBean = datas.get(i);
                //只要有一个不选中就设置非全选
                goodsBean.setChecked(checked);
                notifyItemChanged(i);
            }
        } else {
            checkboxAll.setChecked(false);
        }
    }

    public void deleteData() {
        if (datas != null && datas.size() > 0) {
            for (int i = 0; i < datas.size(); i++) {
                GoodsBean goodsBean = datas.get(i);
                if (goodsBean.isChecked()) {
                    datas.remove(goodsBean);
                    //同步到本地
                    CartStorage.getInstance(mContext).deleteData(goodsBean);
                    notifyItemRemoved(i);
                    i--;
                }
            }
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.cb_gov)
        CheckBox cbGov;
        @InjectView(R.id.iv_gov)
        ImageView ivGov;
        @InjectView(R.id.tv_desc_gov)
        TextView tvDescGov;
        @InjectView(R.id.tv_price_gov)
        TextView tvPriceGov;
        @InjectView(R.id.AddSubView)
        com.atguigu.shoppingone_0224.shoppingcart.view.AddSubView AddSubView;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
            //设置item的点击事件的监听
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    GoodsBean goodsBean = datas.get(getLayoutPosition());
                    goodsBean.setChecked(!goodsBean.isChecked());

                    //刷新适配器
                    notifyItemChanged(getLayoutPosition());

                    //重新显示总价格
                    showTotalPrice();
                    //校验是否全选
                    checkAll();
                }
            });
        }
    }

    /**
     * 校验是否全选
     */
    public void checkAll() {
        if (datas != null && datas.size() > 0) {
            int number = 0;
            for (int i = 0; i < datas.size(); i++) {
                GoodsBean goodsBean = datas.get(i);
                if (!goodsBean.isChecked()) {
                    cbAll.setChecked(false);
                    checkboxAll.setChecked(false);
                } else {
                    number++;
                }
            }
            if (number == datas.size()) {
                cbAll.setChecked(true);
                checkboxAll.setChecked(true);
            }
        } else {
            //没有数据
            cbAll.setChecked(false);
            checkboxAll.setChecked(false);
        }
    }
}
