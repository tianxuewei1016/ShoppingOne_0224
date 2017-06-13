package com.atguigu.shoppingone_0224.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.atguigu.shoppingone_0224.R;
import com.atguigu.shoppingone_0224.activity.GoodsInfoActivity;
import com.atguigu.shoppingone_0224.home.bean.GoodsBean;
import com.atguigu.shoppingone_0224.home.bean.HomeBean;
import com.atguigu.shoppingone_0224.home.view.MyGridView;
import com.atguigu.shoppingone_0224.utils.Constants;
import com.atguigu.shoppingone_0224.utils.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;
import com.zhy.magicviewpager.transformer.RotateYTransformer;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.iwgang.countdownview.CountdownView;

/**
 * 作者：田学伟 on 2017/6/12 14:39
 * QQ：93226539
 * 作用：
 */

public class HomeAdapter extends RecyclerView.Adapter {
    public static final String GOODS_BEAN = "goodsBean";
    private final Context mContext;
    private final HomeBean.ResultEntity result;

    /**
     * 横幅广告-要从0开
     */
    public static final int BANNER = 0;
    /**
     * 频道
     */
    public static final int CHANNEL = 1;

    /**
     * 活动
     */
    public static final int ACT = 2;

    /**
     * 秒杀
     */
    public static final int SECKILL = 3;
    /**
     * 推荐
     */
    public static final int RECOMMEND = 4;
    /**
     * 热卖
     */
    public static final int HOT = 5;
    /**
     * 当前类型
     */
    public int currentType = BANNER;

    private LayoutInflater inflater;

    public HomeAdapter(Context mContext, HomeBean.ResultEntity result) {
        this.mContext = mContext;
        this.result = result;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == BANNER) {
            currentType = BANNER;
        } else if (position == CHANNEL) {
            currentType = CHANNEL;
        } else if (position == ACT) {
            currentType = ACT;
        } else if (position == SECKILL) {
            currentType = SECKILL;
        } else if (position == RECOMMEND) {
            currentType = RECOMMEND;
        } else if (position == HOT) {
            currentType = HOT;
        }
        return currentType;
    }

    @Override
    public int getItemCount() {
        return 6;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == BANNER) {
            return new BannerViewHolder(mContext, inflater.inflate(R.layout.banner_viewpager, null));
        } else if (viewType == CHANNEL) {
            return new ChannelViewHolder(mContext, inflater.inflate(R.layout.channel_item, null));
        } else if (viewType == ACT) {
            return new ActViewHolder(mContext, inflater.inflate(R.layout.act_item, null));
        } else if (viewType == SECKILL) {
            return new SeckillViewHolder(mContext, inflater.inflate(R.layout.seckill_item, null));
        } else if (viewType == RECOMMEND) {
            return new RecommendViewHolder(mContext, inflater.inflate(R.layout.recommend_item, null));
        } else if (viewType == HOT) {
            return new HotViewHolder(mContext, inflater.inflate(R.layout.hot_item, null));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == BANNER) {
            BannerViewHolder bannerViewHolder = (BannerViewHolder) holder;
            //设置数据Banner的数据
            bannerViewHolder.setData(result.getBanner_info());
        } else if (getItemViewType(position) == CHANNEL) {
            ChannelViewHolder channelViewHolder = (ChannelViewHolder) holder;
            channelViewHolder.setData(result.getChannel_info());
        } else if (getItemViewType(position) == ACT) {
            ActViewHolder actViewHolder = (ActViewHolder) holder;
            actViewHolder.setData(result.getAct_info());
        } else if (getItemViewType(position) == SECKILL) {
            SeckillViewHolder seckillViewHolder = (SeckillViewHolder) holder;
            seckillViewHolder.setData(result.getSeckill_info());
        } else if (getItemViewType(position) == RECOMMEND) {
            RecommendViewHolder recommendViewHolder = (RecommendViewHolder) holder;
            recommendViewHolder.setData(result.getRecommend_info());
        } else if (getItemViewType(position) == HOT) {
            HotViewHolder hotViewHolder = (HotViewHolder) holder;
            hotViewHolder.setData(result.getHot_info());
        }
    }

    class BannerViewHolder extends RecyclerView.ViewHolder {
        public Banner banner;
        private final Context mContext;

        public BannerViewHolder(Context mContext, View itemView) {
            super(itemView);
            this.mContext = mContext;
            banner = (Banner) itemView.findViewById(R.id.banner);
        }

        public void setData(List<HomeBean.ResultEntity.BannerInfoEntity> banner_info) {
            //设置Banner的数据
            List<String> images = new ArrayList<>();
            for (int i = 0; i < banner_info.size(); i++) {
                images.add(Constants.BASE_URL_IMAGE + banner_info.get(i).getImage());
            }
            banner.setImages(images)
                    .setImageLoader(new GlideImageLoader())
                    .setOnBannerListener(new OnBannerListener() {
                        @Override
                        public void OnBannerClick(int position) {
                            Toast.makeText(mContext, "position==" + position, Toast.LENGTH_SHORT).show();
                        }
                    }).start();
        }
    }

    class ChannelViewHolder extends RecyclerView.ViewHolder {

        private final Context mContext;
        private GridView gv;
        ChannelAdapter adapter;

        public ChannelViewHolder(Context mContext, View itemView) {
            super(itemView);
            this.mContext = mContext;
            gv = (GridView) itemView.findViewById(R.id.gv);
        }

        public void setData(final List<HomeBean.ResultEntity.ChannelInfoEntity> channel_info) {
            adapter = new ChannelAdapter(mContext, channel_info);
            gv.setAdapter(adapter);

            //设置点击事件
            gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    HomeBean.ResultEntity.ChannelInfoEntity channelInfoEntity = channel_info.get(position);
                    Toast.makeText(mContext, "" + channelInfoEntity.getChannel_name(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    class ActViewHolder extends RecyclerView.ViewHolder {

        private final Context mContext;
        private ViewPager act_viewpager;
        ViewPagerAdapter adapter;

        public ActViewHolder(Context mContext, View itemView) {
            super(itemView);
            this.mContext = mContext;
            act_viewpager = (ViewPager) itemView.findViewById(R.id.act_viewpager);
        }

        public void setData(final List<HomeBean.ResultEntity.ActInfoEntity> act_info) {
            adapter = new ViewPagerAdapter(mContext, act_info);
            act_viewpager.setAdapter(adapter);

            //设置间距
            act_viewpager.setPageMargin(50);
            act_viewpager.setPageTransformer(true, new RotateYTransformer());
            adapter.setOnItemClickListener(new ViewPagerAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    HomeBean.ResultEntity.ActInfoEntity actInfoEntity = act_info.get(position);
                    Toast.makeText(mContext, "" + actInfoEntity.getName(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private boolean isFrist = false;

    class SeckillViewHolder extends RecyclerView.ViewHolder {
        private final Context mContext;
        @InjectView(R.id.countdownview)
        CountdownView countdownview;
        @InjectView(R.id.tv_more_seckill)
        TextView tvMoreSeckill;
        @InjectView(R.id.rv_seckill)
        RecyclerView rvSeckill;
        SeckillRecyclerViewAdapter adapter;

        Handler mHandler = new Handler();
        private HomeBean.ResultEntity.SeckillInfoEntity seckillInfo;

        public SeckillViewHolder(Context mContext, View itemView) {
            super(itemView);
            this.mContext = mContext;
            ButterKnife.inject(this, itemView);
        }

        public void setData(HomeBean.ResultEntity.SeckillInfoEntity seckill_info) {
            this.seckillInfo = seckill_info;
            //设置适配器
            adapter = new SeckillRecyclerViewAdapter(mContext, seckill_info.getList());
            rvSeckill.setAdapter(adapter);
            //设置布局管理器
            rvSeckill.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
            //设置点击事件
            adapter.setOnItemClickListener(new SeckillRecyclerViewAdapter.OnItemClickListener() {
                @Override
                public void OnItemClick(View v, int position) {
                    Toast.makeText(mContext, "postion==" + position, Toast.LENGTH_SHORT).show();
                }
            });
            if (!isFrist) {
                isFrist = true;
                //计算倒计时持续的时间
                long totalTime = Long.parseLong(seckillInfo.getEnd_time()) - Long.parseLong(seckillInfo.getStart_time());
                // 校对倒计时
                long curTime = System.currentTimeMillis();
                //重新设置结束数据时间
                seckillInfo.setEnd_time((curTime + totalTime + ""));
                //开始刷新
                startRefreshTime();
            }
        }

        /**
         * 开始刷新时间
         */
        void startRefreshTime() {
            mHandler.postDelayed(mRefreshTimeRunnable, 10);
        }

        Runnable mRefreshTimeRunnable = new Runnable() {
            @Override
            public void run() {
                //得到当前时间
                long currentTime = System.currentTimeMillis();
                if (currentTime >= Long.parseLong(seckillInfo.getEnd_time())) {
                    //倒计时结束
                    mHandler.removeCallbacksAndMessages(null);
                } else {
                    //更新时间
                    countdownview.updateShow(Long.parseLong(seckillInfo.getEnd_time()) - currentTime);
                    //没隔一秒发送一次
                    mHandler.postDelayed(mRefreshTimeRunnable, 1000);
                }
            }
        };
    }


    class RecommendViewHolder extends RecyclerView.ViewHolder {
        private final Context mContext;
        @InjectView(R.id.tv_more_recommend)
        TextView tvMoreRecommend;
        @InjectView(R.id.gv_recommend)
        GridView gvRecommend;
        RecommendGridViewAdapter adapter;

        public RecommendViewHolder(Context mContext, View itemView) {
            super(itemView);
            this.mContext = mContext;
            ButterKnife.inject(this, itemView);
        }

        public void setData(List<HomeBean.ResultEntity.RecommendInfoEntity> recommend_info) {
            adapter = new RecommendGridViewAdapter(mContext, recommend_info);
            gvRecommend.setAdapter(adapter);

            //设置点击事件
            gvRecommend.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                }
            });
        }
    }

    class HotViewHolder extends RecyclerView.ViewHolder {

        private final Context mContext;
        @InjectView(R.id.tv_more_hot)
        TextView tvMoreHot;
        @InjectView(R.id.gv_hot)
        MyGridView gvHot;
        HotGridViewAdapter adapter;

        public HotViewHolder(Context mContext, View itemView) {
            super(itemView);
            this.mContext = mContext;
            ButterKnife.inject(this, itemView);
        }

        public void setData(final List<HomeBean.ResultEntity.HotInfoEntity> hot_info) {
            adapter = new HotGridViewAdapter(mContext, hot_info);
            gvHot.setAdapter(adapter);
            //设置点击事件
            gvHot.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    HomeBean.ResultEntity.HotInfoEntity hotInfoEntity = hot_info.get(position);
                    GoodsBean goodsBean = new GoodsBean();
                    goodsBean.setName(hotInfoEntity.getName());
                    goodsBean.setCover_price(hotInfoEntity.getCover_price());
                    goodsBean.setFigure(hotInfoEntity.getFigure());
                    goodsBean.setProduct_id(hotInfoEntity.getProduct_id());

                    Intent intent = new Intent(mContext, GoodsInfoActivity.class);
                    intent.putExtra(GOODS_BEAN,goodsBean);
                    mContext.startActivity(intent);
                }
            });
        }
    }
}
