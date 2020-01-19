package com.karashok.library.module_preview_file.ui.activity;

import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.karashok.library.module_preview_file.R;
import com.karashok.library.module_preview_file.ui.widget.X5WebView;


public class SampleX5WebViewActivity extends AppCompatActivity {

    private FrameLayout mFrameLayout;
    private X5WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_x5_web_view);
        // 视频为了避免闪屏和透明问题
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        mFrameLayout = findViewById(R.id.activity_sample_x5_web_view_fl);
        mWebView = new X5WebView(this,null);
        mFrameLayout.addView(mWebView, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        mWebView.loadUrl("file:///android_asset/fullscreenVideo.html");
        mWebView.addJavascriptInterface(new JavaScriptFunction() {

            @Override
            @JavascriptInterface
            public void onX5ButtonClicked() {
                Toast.makeText(SampleX5WebViewActivity.this,"sdfdsfs",Toast.LENGTH_SHORT).show();
                // 开启X5全屏播放模式
                mWebView.setVideoScreenStyleFunc(false,false,2);
            }

            @Override
            @JavascriptInterface
            public void onCustomButtonClicked() {
                // 恢复webkit初始状态
                mWebView.setVideoScreenStyleFunc(false,true,2);
            }

            @Override
            @JavascriptInterface
            public void onLiteWndButtonClicked() {
                // 开启小窗模式
                mWebView.setVideoScreenStyleFunc(false,true,2);
            }

            @Override
            @JavascriptInterface
            public void onPageVideoClicked() {
                // 开启X5全屏播放模式
                mWebView.setVideoScreenStyleFunc(false,false,1);
            }
        }, "Android");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        if (intent == null || mWebView == null || intent.getData() == null){
            return;
        }
        mWebView.loadUrl(intent.getData().toString());
    }

    @Override
    public void finish() {
        if (mWebView == null || !mWebView.goToBack()){
            super.finish();
        }
    }

    @Override
    protected void onDestroy() {
        if (mWebView != null){
            mFrameLayout.removeAllViews();
            mWebView.destroy();
            mWebView = null;
        }
        super.onDestroy();
    }
}
