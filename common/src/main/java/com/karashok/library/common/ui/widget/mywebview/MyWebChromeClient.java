package com.karashok.library.common.ui.widget.mywebview;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import static android.app.Activity.RESULT_OK;

/**
 * @author KaraShokZ(zhangyaozhong)
 * @name MyWebChromeClient
 * DESCRIPTION WebChromeClient的一些配置
 * @date 2018/06/16/下午6:18
 */
public abstract class MyWebChromeClient extends WebChromeClient {

    private static final int REQUEST_SELECT_FILE = 100;
    private static final int FILECHOOSER_RESULTCODE = 2;

    private AppCompatActivity mActivity;
    public ValueCallback<Uri[]> mUploadMessages;
    private ValueCallback<Uri> mUploadMessage;

    public MyWebChromeClient(AppCompatActivity activity) {
        this.mActivity = activity;
    }

    /**
     * 解决AndroidWebView中file标签失效,3.0 + 调用这个方法
     * @param uploadMsg
     * @param acceptType
     */
    protected void openFileChooser(ValueCallback uploadMsg, String acceptType) {
        mUploadMessage = uploadMsg;
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        mActivity.startActivityForResult(Intent.createChooser(intent, "File Browser"), FILECHOOSER_RESULTCODE);
    }

    /**
     * 解决AndroidWebView中file标签失效,4.1 only 调用这个方法
     * @param uploadMsg
     * @param acceptType
     * @param capture
     */
    protected void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
        mUploadMessage = uploadMsg;
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        mActivity.startActivityForResult(Intent.createChooser(intent, "File Browser"), FILECHOOSER_RESULTCODE);
    }

    /**
     * 解决AndroidWebView中file标签失效
     * @param uploadMsg
     */
    protected void openFileChooser(ValueCallback<Uri> uploadMsg) {
        mUploadMessage = uploadMsg;
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        mActivity.startActivityForResult(Intent.createChooser(intent, "File Chooser"), FILECHOOSER_RESULTCODE);
    }

    /**
     * 解决AndroidWebView中file标签失效
     * @param webView
     * @param filePathCallback
     * @param fileChooserParams
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
        // MyToast.showDebugToast("打开文件");
        mUploadMessages = filePathCallback;
        Intent intent = fileChooserParams.createIntent();
        try {
            mActivity.startActivityForResult(intent, REQUEST_SELECT_FILE);
        } catch (ActivityNotFoundException e) {
            mUploadMessages = null;
            // MyToast.showDebugToast("Cannot Open File Chooser");
            return false;
        }
        return true;
    }

    @Override
    public void onReceivedTitle(WebView view, String title) {
        super.onReceivedTitle(view, title);
        // 加载的网页的标题（前提是网页有标题）
        receivedTitle(title);
    }

    protected abstract void receivedTitle(String title);

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        super.onProgressChanged(view, newProgress);
        // 加载进度回调
        progressChanged(newProgress);
    }

    protected abstract void progressChanged(int newProgress);

    public void onActivityResult(int requestCode, int resultCode, Intent intent){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (requestCode == REQUEST_SELECT_FILE) {
                if (mUploadMessages == null) {
                    return;
                }else {
                    mUploadMessages.onReceiveValue(FileChooserParams.parseResult(resultCode, intent));
                    mUploadMessages = null;
                }
            }
        } else if (requestCode == FILECHOOSER_RESULTCODE) {
            if (null == mUploadMessage){
                return;
            }else {
                // Use MainActivity.RESULT_OK if you're implementing WebView inside Fragment
                // Use RESULT_OK only if you're implementing WebView inside an Activity
                Uri result = intent == null || resultCode != RESULT_OK ? null : intent.getData();
                mUploadMessage.onReceiveValue(result);
                mUploadMessage = null;
            }
        } else{
            Toast.makeText(mActivity, "Failed to Upload Image", Toast.LENGTH_LONG).show();
        }
    }

}
