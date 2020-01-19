package com.karashok.library.common.ui.activity;

import android.content.Intent;
import android.view.View;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.karashok.library.common.CommonConstacts;
import com.karashok.library.common.R;
import com.karashok.library.common.base.BaseActivity;
import com.karashok.library.common.ui.widget.mywebview.MyWebChromeClient;
import com.karashok.library.common.ui.widget.mywebview.MyWebView;

/**
 * @author KaraShokZ(zhangyaozhong)
 * @name SampleWebViewActivity
 * DESCRIPTION 简单的WebView展示页面
 * @date 2018/06/16/下午6:18
 */
@Route(path = CommonConstacts.ROUTE_MODULE_COMMON_ATY_WEB_VIEW)
public class SampleWebViewActivity extends BaseActivity {

    @Autowired(name = "route_params_key_URL")
    String mPathRUL;

    private MyWebView mMyWebView;
    private FrameLayout mFrameLayout;

    @Override
    protected int layoutId() {
        return R.layout.aty_sample_web_view;
    }

    @Override
    protected boolean isDataBinding() {
        return false;
    }

    @Override
    protected Class bindViewModel() {
        return null;
    }

    @Override
    protected void create() {
        super.create();
        ARouter.getInstance().inject(this);

        initTitleLayout();
        useBack();
        initView();
        initWebView();
    }

    private void initView(){
        mFrameLayout = findViewById(R.id.aty_sample_web_view_webLayout_fl);
    }

    private void initWebView(){
        mMyWebView = new MyWebView(this);
        WebView webView = mMyWebView.creatWebView(null, new MyWebChromeClient(this) {
            @Override
            protected void receivedTitle(String title) {

                setTitleTV(title);
            }

            @Override
            protected void progressChanged(int newProgress) {

            }
        });
        mFrameLayout.addView(webView);
        mMyWebView.webViewLoad("");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mMyWebView.mChromeClient.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        if (!mMyWebView.back()){
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFrameLayout.removeAllViews();
        mMyWebView.webViewDestroy();
        mMyWebView = null;
    }

    private RelativeLayout mTitleLayoutRL;
    private TextView mTitleTV;
    private ImageView mLeftIconIV;
    private ImageView mRightIconTV;

    /**
     * 初始化标题布局
     */
    protected void initTitleLayout(){
        mTitleLayoutRL = findViewById(R.id.view_title_layout);
        mTitleTV = findViewById(R.id.view_title_title_tv);
        mLeftIconIV = findViewById(R.id.view_title_left_icon_iv);
        mRightIconTV = findViewById(R.id.view_title_right_icon_iv);
    }

    /**
     * 左边按钮为返回
     */
    protected void useBack(){
        mLeftIconIV.setVisibility(View.VISIBLE);
        mLeftIconIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    /**
     * 设置标题
     * @param title
     */
    protected void setTitleTV(String title){
        mTitleTV.setText(title);
    }
}
