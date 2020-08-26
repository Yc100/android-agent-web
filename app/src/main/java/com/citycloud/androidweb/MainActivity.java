package com.citycloud.androidweb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.just.agentweb.AgentWeb;
import com.just.agentweb.DefaultWebClient;
import com.just.agentweb.WebChromeClient;
import com.just.agentweb.WebViewClient;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.bgabanner.BGABanner;
import cn.bingoogolapple.bgabanner.transformer.TransitionEffect;

/**
 * AgentWeb框架实现 安卓壳子
 * BGA Banner实现的启动页 支持自动轮播等功能
 * 实现了回退、退出功能
 * @author Yc
 */
public class MainActivity extends AppCompatActivity {

    private ImageView ivback;
    private TextView receiveTitle;
    /**
     * 用来承载AgentWebView
     */
    private LinearLayout mLinearLayout;

    private AgentWeb mAgentWeb;
    private long exitTime;
    BGABanner guide_banner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent in=new Intent(getApplicationContext(),GuideActivity.class);//启动页start
        startActivity(in);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);//隐藏标题栏
        setContentView(R.layout.activity_main);

        //initView();
        //mLinearLayout = (LinearLayout) this.findViewById(R.id.container);

        initAgentWeb();



}


    private void initAgentWeb() {
        CoolIndicatorLayout mCoolIndicatorLayout = new CoolIndicatorLayout(this);//彩色进度条
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(mLinearLayout, new LinearLayout.LayoutParams(-1, -1))
                .setCustomIndicator(mCoolIndicatorLayout)
                //.useDefaultIndicator(0,0)
                //.useDefaultIndicator()
                .setWebChromeClient(webChromeClient)
                .setWebViewClient(webViewClient)
                .setMainFrameErrorView(R.layout.agentweb_error_page, -1)
                .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
                //.setWebLayout(new WebLayout(this))
                .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.DISALLOW)//打开其他应用时，弹窗咨询用户是否前往其他应用
                .interceptUnkownUrl() //拦截找不到相关页面的Scheme
                .createAgentWeb()
                .ready()
                .go(Constants.TARGET_URL);

        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
        mAgentWeb.getWebCreator().getWebView().setOverScrollMode(WebView.OVER_SCROLL_NEVER);
        mAgentWeb.getWebCreator().getWebView().getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);//不使用缓存，只从网络获取数据.
        //支持屏幕缩放
        //mAgentWeb.getWebCreator().getWebView().getSettings().setSupportZoom(true);
        //mAgentWeb.getWebCreator().getWebView().getSettings().setMediaPlaybackRequiresUserGesture(false);
        //mAgentWeb.getWebCreator().getWebView().getSettings().setBuiltInZoomControls(true);
        //mAgentWeb.getWebCreator().getWebView().getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        //mAgentWeb.getWebCreator().getWebView().getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        //mAgentWeb.getWebCreator().getWebView().getSettings().setJavaScriptEnabled(true);
        //mAgentWeb.getWebCreator().getWebView().getSettings().setUseWideViewPort(true);
        //mAgentWeb.getWebCreator().getWebView().getSettings().setLoadWithOverviewMode(true);
        //mAgentWeb.getWebCreator().getWebView().getSettings().setAppCacheEnabled(true);
        //mAgentWeb.getWebCreator().getWebView().getSettings().setDomStorageEnabled(true);//开启本地DOM存储
        //mAgentWeb.getWebCreator().getWebView().getSettings().setLoadsImagesAutomatically(true); // 加载图片
        //mAgentWeb.getWebCreator().getWebView().getSettings().setMediaPlaybackRequiresUserGesture(false);//播放音频，多媒体需要用户手动？设置为false为可自动播放
        //mAgentWeb.getWebCreator().getWebView().getSettings().setAllowFileAccess(true);


    }


    // 看导包 这些是第三方的 不是自带的
    private WebChromeClient webChromeClient = new WebChromeClient() {
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);

            // 设置接收到的 title
            if (receiveTitle != null) {
                receiveTitle.setText(title);
            }

        }
    };

    //看导包 这些是第三方的 不是自带的
    private WebViewClient webViewClient = new WebViewClient() {
        // 可以去看上一级已经写了
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return super.shouldOverrideUrlLoading(view, request);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }
    };


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {//通用返回功能
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mAgentWeb.getWebCreator().getWebView().canGoBack()) {
                // 返回键退回
                mAgentWeb.getWebCreator().getWebView().goBack();
                return true;
            } else {
                if ((System.currentTimeMillis() - exitTime) > 2000) {
                    Toast.makeText(getApplicationContext(), "再按一次退出应用", Toast.LENGTH_SHORT).show();
                    exitTime = System.currentTimeMillis();
                } else {
                    finish();
                }
                return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }

}
