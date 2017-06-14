package com.atguigu.shoppingone_0224.shoppingcart.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.TintTypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.atguigu.shoppingone_0224.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 作者：田学伟 on 2017/6/14 11:26
 * QQ：93226539
 * 作用：自定义的按钮
 */

public class AddSubView extends LinearLayout {
    private final Context mContext;
    @InjectView(R.id.iv_sub)
    ImageView ivSub;
    @InjectView(R.id.tv_value)
    TextView tvValue;
    @InjectView(R.id.iv_add)
    ImageView ivAdd;

    private int value = 1;
    private int minValue = 1;
    private int maxValue = 10;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
        //要加上""
        tvValue.setText(value + "");
    }

    public int getMinValue() {
        return minValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public AddSubView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        //把布局文件实例化成View,并且添加到AddSubView.this类中，成为他的子视图
        ButterKnife.inject(this, View.inflate(context, R.layout.add_sub_view, AddSubView.this));
        if (attrs != null) {
            //取出属性
            TintTypedArray tintTypedArray = TintTypedArray.obtainStyledAttributes(context, attrs, R.styleable.AddSubView);
            int value = tintTypedArray.getInt(R.styleable.AddSubView_value, 0);//如果取不到就设置为0
            if (value > 0) {
                setValue(value);
            }
            int minValue = tintTypedArray.getInt(R.styleable.AddSubView_minValue, 0);
            if (minValue > 0) {
                setMinValue(minValue);
            }
            int maxValue = tintTypedArray.getInt(R.styleable.AddSubView_maxValue, 0);
            if (maxValue > 0) {
                setMaxValue(maxValue);
            }
            Drawable addDrawable = tintTypedArray.getDrawable(R.styleable.AddSubView_numberAddBackground);
            if (addDrawable != null) {
                ivAdd.setImageDrawable(addDrawable);
            }
            Drawable subDrawable = tintTypedArray.getDrawable(R.styleable.AddSubView_numberSubBackground);
            if (subDrawable != null) {
                ivSub.setImageDrawable(subDrawable);
            }
        }
    }

    @OnClick({R.id.iv_sub, R.id.iv_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_sub:
                if (value > minValue) {
                    value--;
                }
                tvValue.setText(value + "");
                break;
            case R.id.iv_add:
                if (value < maxValue) {
                    value++;
                }
                tvValue.setText(value + "");
                break;
        }
        if (changeListener != null) {
            changeListener.numberChange(value);
        }
    }

    public interface OnNumberChangeListener {
        void numberChange(int number);
    }

    private OnNumberChangeListener changeListener;

    public void setOnNumberChangeListener(OnNumberChangeListener l) {
        this.changeListener = l;
    }
}
