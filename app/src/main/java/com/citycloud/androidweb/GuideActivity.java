package com.citycloud.androidweb;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;

import com.facebook.drawee.view.SimpleDraweeView;

import cn.bingoogolapple.bgabanner.BGABanner;
import cn.bingoogolapple.bgabanner.BGALocalImageSize;

public class GuideActivity extends Activity  {
    private static final String TAG = GuideActivity.class.getSimpleName();
    //private BGABanner BackgroundBanner;
    private BGABanner mBackgroundBanner;
    private BGABanner mForegroundBanner;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        setListener();
        processLogic();
    }

    private void initView() {
        setContentView(R.layout.activity_guide);
        //BackgroundBanner = findViewById(R.id.banner_guide_background);
        mBackgroundBanner = findViewById(R.id.banner_guide_background);
        mForegroundBanner = findViewById(R.id.banner_guide_foreground);
    }

    private void setListener() {
        /**
         * 设置进入按钮和跳过按钮控件资源 id 及其点击事件
         * 如果进入按钮和跳过按钮有一个不存在的话就传 0
         * 在 BGABanner 里已经帮开发者处理了防止重复点击事件
         * 在 BGABanner 里已经帮开发者处理了「跳过按钮」和「进入按钮」的显示与隐藏
         */
        mForegroundBanner.setEnterSkipViewIdAndDelegate(R.id.btn_guide_enter, R.id.tv_guide_skip, new BGABanner.GuideDelegate() {
            @Override
            public void onClickEnterOrSkip() {
                //startActivity(new Intent(GuideActivity.this, MainActivity.class));
                finish();
            }
        });

        /*BackgroundBanner.setEnterSkipViewIdAndDelegate(R.id.btn_guide_enter, R.id.tv_guide_skip, new BGABanner.GuideDelegate() {
            @Override
            public void onClickEnterOrSkip() {
                //startActivity(new Intent(GuideActivity.this, MainActivity.class));
                finish();
            }

        });*/

        mForegroundBanner.setDelegate(new BGABanner.Delegate<ImageView, String>() {
            @Override
            public void onBannerItemClick(BGABanner banner, ImageView itemView, String model, int position) {
                //Toast.makeText(banner.getContext(), "点击了" + position, Toast.LENGTH_SHORT).show();
            }


        });

        mForegroundBanner.setAdapter(new BGABanner.Adapter<ImageView, String>() {//监听轮播改变
            @Override
            public void fillBannerItem(BGABanner banner, ImageView itemView, String model, int position) {
                if(mForegroundBanner.getCurrentItem()+1==mForegroundBanner.getItemCount()){//最后一页停止自动轮播
                    mForegroundBanner.stopAutoPlay();
                }
            }
        });
        mBackgroundBanner.setAdapter(new BGABanner.Adapter<ImageView, String>() {//监听轮播改变
            @Override
            public void fillBannerItem(BGABanner banner, ImageView itemView, String model, int position) {
                if(mBackgroundBanner.getCurrentItem()+1==mBackgroundBanner.getItemCount()){//最后一页停止自动轮播
                    mBackgroundBanner.stopAutoPlay();
                }
            }
        });

        //定义一个CountDownTimer，这个类是Android SDK提供用来进行倒计时的。CountDownTimer(long millisInFuture, long countDownInterval)有两个参数，第一个是计时的总时长，第二个是间隔。
        countDownTimer = new CountDownTimer(10000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                //tvAds.setText("点击跳过"+(millisUntilFinished/1000)+"秒");
            }

            @Override
            public void onFinish() {
                finish();
            }
        }.start();
    }

    private void processLogic() {
        // Bitmap 的宽高在 maxWidth maxHeight 和 minWidth minHeight 之间
        BGALocalImageSize localImageSize = new BGALocalImageSize(720, 1280, 320, 640);
        //BackgroundBanner.setAutoPlayAble(true);//自动轮播
        // 设置数据源
        /*BackgroundBanner.setData(localImageSize, ImageView.ScaleType.CENTER_CROP,
                R.drawable.guide_1,
                R.drawable.guide_2,
                R.drawable.guide_3);*/
        //BackgroundBanner.startAutoPlay();
        /*原数据======================================================================*/

        mBackgroundBanner.setAutoPlayAble(true);//自动轮播
        mBackgroundBanner.setData(localImageSize, ImageView.ScaleType.CENTER_CROP,
                R.drawable.uoko_guide_background_1,
                R.drawable.uoko_guide_background_2,
                R.drawable.uoko_guide_background_3);

        mForegroundBanner.setAutoPlayAble(true);//自动轮播
        mForegroundBanner.setData(localImageSize, ImageView.ScaleType.CENTER_CROP,
                R.drawable.uoko_guide_foreground_1,
                R.drawable.uoko_guide_foreground_2,
                R.drawable.uoko_guide_foreground_3);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 如果开发者的引导页主题是透明的，需要在界面可见时给背景 Banner 设置一个白色背景，避免滑动过程中两个 Banner 都设置透明度后能看到 Launcher
        mBackgroundBanner.setBackgroundResource(android.R.color.white);
    }

}