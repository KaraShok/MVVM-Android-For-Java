package com.karashok.library.common.ui.widget.mywebview;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.webkit.DownloadListener;

/**
 * @author KaraShokZ(zhangyaozhong)
 * @name WebViewDownloadListener
 * DESCRIPTION Web页面下载东西调用原生下载
 * @date 2018/06/16/下午6:18
 */
public class WebViewDownloadListener implements DownloadListener {

    private Context mContext;

    public WebViewDownloadListener(Context context) {
        this.mContext = context;
    }

    @Override
    public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
        // 调用系统下载
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.setData(Uri.parse(url));
        mContext.startActivity(intent);
    }
}
