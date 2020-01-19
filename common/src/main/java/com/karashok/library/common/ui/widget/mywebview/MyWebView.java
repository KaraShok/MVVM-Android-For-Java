package com.karashok.library.common.ui.widget.mywebview;

import android.annotation.SuppressLint;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * @author KaraShokZ(zhangyaozhong)
 * @name MyWebView
 * DESCRIPTION 初始化WebView、为WebView设置各种属性
 * @date 2018/06/16/下午6:18
 */
public class MyWebView {

    private WebView mWebView;
    private AppCompatActivity mActivity;
    public MyWebChromeClient mChromeClient;
    public MyWebViewClient mViewClient;

    public MyWebView(AppCompatActivity activity) {
        this.mActivity = activity;
    }

    /**
     * 创建WebView
     * @param myWebViewClient
     * @param myWebChromeClient
     * @return
     */
    public WebView creatWebView(MyWebViewClient myWebViewClient, MyWebChromeClient myWebChromeClient){
        mWebView = new WebView(mActivity);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mWebView.setLayoutParams(layoutParams);
        initWebViewSetting();
        initWebView(myWebViewClient, myWebChromeClient);
        return mWebView;
    }

    /**
     * 添加JavaScript方法
     * @param obj
     * @param funName
     */
    @SuppressLint("JavascriptInterface")
    public void addJavascriptInterface(Object obj, String funName){
        mWebView.addJavascriptInterface(obj, funName);
    }

    /**
     * 移除JavaScript方法
     * @param funName
     */
    public void removeJavascriptInterface(String funName){
        mWebView.removeJavascriptInterface(funName);
    }

    public void webViewLoad(String url){
        if (!TextUtils.isEmpty(url)){
            mWebView.loadUrl(url);
        }else {
            mWebView.loadUrl("https://www.baidu.com/");
        }
    }

    /**
     * 初始化WebView
     * @param myWebViewClient
     * @param myWebChromeClient
     */
    private void initWebView(MyWebViewClient myWebViewClient, MyWebChromeClient myWebChromeClient){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            // 软件解码
            mWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        // 硬件解码
        mWebView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        // 设置setWebChromeClient对象
        initWebViewChromeClient(myWebChromeClient);
        mWebView.setWebChromeClient(mChromeClient);
        // 设置此方法可在WebView中打开链接，反之用浏览器打开
        initWebViewClient(myWebViewClient);
        mWebView.setWebViewClient(mViewClient);
        // WebView下载监听
        mWebView.setDownloadListener(new WebViewDownloadListener(mActivity));
        mWebView.setInitialScale(80);
        mWebView.setSaveEnabled(true);
        mWebView.setKeepScreenOn(true);
        mWebView.setHorizontalScrollBarEnabled(false);
        mWebView.setVerticalScrollBarEnabled(false);
        mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_INSET);
        mWebView.setFocusableInTouchMode(true);
        mWebView.setClipToPadding(true);
        mWebView.setFitsSystemWindows(true);
        mWebView.setFocusable(true);
    }

    /**
     * 初始化WebViewChromeClient
     * @param myWebChromeClient
     */
    private void initWebViewChromeClient(MyWebChromeClient myWebChromeClient){
        if (myWebChromeClient != null){
            mChromeClient = myWebChromeClient;
        }else {
            mChromeClient = new MyWebChromeClient(mActivity) {
                @Override
                protected void receivedTitle(String title) {

                }

                @Override
                protected void progressChanged(int newProgress) {

                }
            };
        }
    }

    /**
     * 初始化MyWebViewClient实例
     * @param myWebViewClient
     */
    private void initWebViewClient(MyWebViewClient myWebViewClient){
        if (myWebViewClient != null){
            mViewClient = myWebViewClient;
        }else {
            mViewClient = new MyWebViewClient();
        }
    }

    /**
     * 初始化WebViewSetting
     */
    private void initWebViewSetting(){
        WebSettings webSettings = mWebView.getSettings();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            // 加载缓存否则网络
            webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
            // 图片自动缩放 打开
            webSettings.setLoadsImagesAutomatically(true);
        }else {
            // 图片自动缩放 关闭
            webSettings.setLoadsImagesAutomatically(false);
        }
        // 设置支持javascript脚本
        webSettings.setJavaScriptEnabled(true);
        //        webSettings.setPluginState(WebSettings.PluginState.ON);
        // 设置可以支持缩放
        webSettings.setSupportZoom(true);
        // 设置出现缩放工具 是否使用WebView内置的缩放组件，由浮动在窗口上的缩放控制和手势缩放控制组成，默认false
        webSettings.setBuiltInZoomControls(true);
        // 隐藏缩放工具
        webSettings.setDisplayZoomControls(false);
        // 扩大比例的缩放
        webSettings.setUseWideViewPort(true);
        // 自适应屏幕
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

        webSettings.setLoadWithOverviewMode(true);

        webSettings.setDatabaseEnabled(true);
        // 保存密码
        webSettings.setSavePassword(true);
        // 是否开启本地DOM存储  鉴于它的安全特性（任何人都能读取到它，尽管有相应的限制，将敏感数据存储在这里依然不是明智之举），Android 默认是关闭该功能的。
        webSettings.setDomStorageEnabled(true);
    }

    public boolean back(){
        boolean canBack;
        if (mWebView.canGoBack()){
            canBack = true;
            mWebView.goBack();
        }else {
            canBack = false;
        }
        return canBack;
    }
    /**
     * 销毁WebView，释放内存
     */
    public void webViewDestroy(){
        if (mWebView != null){
            mWebView.stopLoading();
            mWebView.clearHistory();
            mWebView.removeAllViews();
            mWebView.destroy();
            mWebView = null;
            mActivity = null;
            mChromeClient = null;
            mViewClient = null;
        }
    }
}
