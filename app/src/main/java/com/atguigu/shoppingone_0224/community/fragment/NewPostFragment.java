package com.atguigu.shoppingone_0224.community.fragment;

import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.atguigu.shoppingone_0224.R;
import com.atguigu.shoppingone_0224.base.BaseFragment;
import com.atguigu.shoppingone_0224.community.adapter.NewPostListViewAdapter;
import com.atguigu.shoppingone_0224.community.bean.NewPostBean;
import com.atguigu.shoppingone_0224.utils.Constants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.ButterKnife;
import butterknife.InjectView;
import okhttp3.Call;

/**
 * 作者：田学伟 on 2017/6/17 13:49
 * QQ：93226539
 * 作用：
 */

public class NewPostFragment extends BaseFragment {

    private static final String TAG = NewPostFragment.class.getSimpleName();
    @InjectView(R.id.lv_new_post)
    ListView lvNewPost;
    private NewPostListViewAdapter adapter;
    /**
     * 初始化控件
     * retur
     */
    @Override
    public View initView() {
        View rootView = View.inflate(mContext, R.layout.fragment_new_post, null);
        ButterKnife.inject(this, rootView);
        return rootView;
    }

    @Override
    public void initData() {
        super.initData();
        getDataFromNet(Constants.NEW_POST_URL);
    }

    private void getDataFromNet(String url) {
        OkHttpUtils
                .get()
                .url(url)
                .build()
                .execute(new MyStringCallback());
    }

    class MyStringCallback extends StringCallback {

        @Override
        public void onError(Call call, Exception e, int id) {
            Log.e(TAG, "请求成功失败==" + e.getMessage());
        }

        @Override
        public void onResponse(String response, int id) {
            Log.e(TAG, "请求成功==");
            processData(response);

        }
    }

    private void processData(String json) {
        NewPostBean newPostBean = JSON.parseObject(json,NewPostBean.class);
        adapter = new NewPostListViewAdapter(mContext,newPostBean.getResult());
        lvNewPost.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}

