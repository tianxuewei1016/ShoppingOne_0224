package com.atguigu.shoppingone_0224.type.fragment;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.atguigu.shoppingone_0224.R;
import com.atguigu.shoppingone_0224.base.BaseFragment;
import com.atguigu.shoppingone_0224.type.adapter.TagGridViewAdapter;
import com.atguigu.shoppingone_0224.type.bean.TagBean;
import com.atguigu.shoppingone_0224.utils.Constants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import okhttp3.Call;

/**
 * 作者：田学伟 on 2017/6/16 15:57
 * QQ：93226539
 * 作用：
 */

public class TagFragment extends BaseFragment {

    @InjectView(R.id.gv_tag)
    GridView gvTag;
    private List<TagBean.ResultEntity> result;
    private TagGridViewAdapter adapter;

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_tag, null);
        ButterKnife.inject(this, view);
        return view;
    }

    /**
     * 1.把数据绑定到控件上的时候，重新该方法
     * 2.联网请求，把得到的数据绑定到视图上
     */
    @Override
    public void initData() {
        super.initData();
        getDataFromNet();
    }

    private void getDataFromNet() {
        OkHttpUtils.get()
                .url(Constants.TAG_URL)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("TAG", "联网失败了" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("TAG", "TagFragment的数据联网成功了==");
                        processData(response);
                    }
                });
    }

    private void processData(String json) {
        TagBean tagBean = JSON.parseObject(json, TagBean.class);
        result = tagBean.getResult();
        adapter = new TagGridViewAdapter(mContext,result);
        gvTag.setAdapter(adapter);
        //设置item的点击事件
        gvTag.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TagBean.ResultEntity item = adapter.getItem(position);
                Toast.makeText(mContext, "" + item.getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}

