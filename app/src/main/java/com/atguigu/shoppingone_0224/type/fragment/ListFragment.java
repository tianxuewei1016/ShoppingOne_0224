package com.atguigu.shoppingone_0224.type.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.atguigu.shoppingone_0224.R;
import com.atguigu.shoppingone_0224.base.BaseFragment;
import com.atguigu.shoppingone_0224.type.adapter.TypeLeftAdapter;
import com.atguigu.shoppingone_0224.type.adapter.TypeRightAdapter;
import com.atguigu.shoppingone_0224.type.bean.TypeBean;
import com.atguigu.shoppingone_0224.utils.Constants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import okhttp3.Call;

/**
 * 作者：田学伟 on 2017/6/16 15:56
 * QQ：93226539
 * 作用：分类
 */

public class ListFragment extends BaseFragment {
    private static final String TAG = ListFragment.class.getSimpleName();
    @InjectView(R.id.lv_left)
    ListView lvLeft;
    @InjectView(R.id.rv_right)
    RecyclerView rvRight;

    //网络请求得到数据
    private String[] titles = new String[]{"小裙子", "上衣", "下装", "外套", "配件", "包包", "装扮", "居家宅品",
            "办公文具", "数码周边", "游戏专区"};
    private String[] urls = new String[]{Constants.SKIRT_URL, Constants.JACKET_URL, Constants.PANTS_URL, Constants.OVERCOAT_URL,
            Constants.ACCESSORY_URL, Constants.BAG_URL, Constants.DRESS_UP_URL, Constants.HOME_PRODUCTS_URL, Constants.STATIONERY_URL,
            Constants.DIGIT_URL, Constants.GAME_URL};
    private TypeLeftAdapter typeLeftAdapter;
    private List<TypeBean.ResultEntity> result;
    private TypeRightAdapter rightAdapter;

    @Override
    public View initView() {
        View rootView = View.inflate(mContext, R.layout.fragment_list, null);
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
        typeLeftAdapter = new TypeLeftAdapter(mContext, titles);
        lvLeft.setAdapter(typeLeftAdapter);

        //设置监听点击ListView的item的点击事件，并且点击的时候变效果
        lvLeft.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //1.记录点击的位置
                typeLeftAdapter.changeSelectPosition(position);
                //2.刷新适配器
                typeLeftAdapter.notifyDataSetChanged();//getView

                //联网请求
                getDataFromNet(urls[position]);
            }
        });
        getDataFromNet(urls[0]);
    }

    /**
     * 网络请求
     *
     * @param url
     */
    private void getDataFromNet(String url) {
        OkHttpUtils.get()
                .url(url)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG, "请求成功失败==" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e(TAG, "请求成功==" + response);
                        processData(response);
                    }
                });
    }

    private void processData(String json) {
        TypeBean typeBean = JSON.parseObject(json, TypeBean.class);
        Log.e("TAG", "解析成功==" + typeBean.getResult().get(0).getName());
        result = typeBean.getResult();
        if (result != null && result.size() > 0) {
            //有数据
            rightAdapter = new TypeRightAdapter(mContext,result);
            //设置适配器
            rvRight.setAdapter(rightAdapter);
            //设置布局管理器
            GridLayoutManager manager = new GridLayoutManager(mContext, 3);
            manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (position == 0) {
                        return 3;
                    } else {
                        return 1;
                    }
                }
            });
            rvRight.setLayoutManager(manager);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}

