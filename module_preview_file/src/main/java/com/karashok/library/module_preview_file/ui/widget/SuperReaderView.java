package com.karashok.library.module_preview_file.ui.widget;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.karashok.library.lib_3rd.ModuleResourceConstant;
import com.karashok.library.module_preview_file.util.RetrofitUtils;
import com.karashok.library.module_util.utilcode.utils.base_util.file_util.FileUtils;
import com.tencent.smtt.sdk.TbsReaderView;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DefaultObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * @author KaraShok(zhangyaozhong)
 * @name SuperReaderView
 * DESCRIPTION 腾讯TBS浏览服务
 * 官网：https://x5.tencent.com/tbs/faq.html
 * 博客：https://www.jianshu.com/p/3f57d640b24d
 * @date 2018/04/25/下午1:57
 */
public class SuperReaderView extends FrameLayout{

    private static final String TAG = SuperReaderView.class.getSimpleName();

    private static String TEMP_FILE = "";

    private TbsReaderView mTbsReaderView;
    public String mNetSavePath;

    public SuperReaderView(@NonNull Context context) {
        super(context);
        init();
    }

    public SuperReaderView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SuperReaderView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        TEMP_FILE = ModuleResourceConstant.SAVE_FILE + "TbsReaderTemp";
        initTbsReaderView();
        SuperReaderView.this.addView(mTbsReaderView);
//        SuperReaderView.this.addView(mTbsReaderView, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
    }

    private void initTbsReaderView(){
        mTbsReaderView = new TbsReaderView(getContext(), new TbsReaderView.ReaderCallback() {
            @Override
            public void onCallBackAction(Integer integer, Object o, Object o1) {
//                Log.e(TAG, "****************************************************" + integer);
            }
        });
    }

    public void openLocationFile(String filePath){
        if (!TextUtils.isEmpty(filePath)){
            initTbsReaderTemp();
            Bundle openFileBundle = getOpenFileBundle(filePath);
            boolean bool = mTbsReaderView.preOpen(FileUtils.getFileExtension(filePath), false);
            if (bool) {
                mTbsReaderView.openFile(openFileBundle);
            }
        }
    }

    public void openNetFile(String fileUrl){
        if (!TextUtils.isEmpty(fileUrl)){
            File file = new File(ModuleResourceConstant.SAVE_FILE);
            if (!file.exists()){
                file.mkdirs();
            }
            mNetSavePath = ModuleResourceConstant.SAVE_FILE + System.currentTimeMillis() + ".pdf";
            downloadNetFile(fileUrl);
        }
    }

    public void onStopDisplay(){
        if (mTbsReaderView != null){
            mTbsReaderView.onStop();
        }
    }

    /**
     * 增加下面一句解决没有TbsReaderTemp文件夹存在导致加载文件失败
     */
    private void initTbsReaderTemp(){
        File bsReaderTempFile =new File(TEMP_FILE);

        if (!bsReaderTempFile.exists()) {
//            Log.d(TAG, "initTbsReaderTemp: " + "准备创建" + TEMP_FILE);
            boolean mkdir = bsReaderTempFile.mkdir();
            if(!mkdir){
//                Log.d(TAG, "initTbsReaderTemp: " + "创建" + TEMP_FILE + "失败");
            }
        }
    }

    private Bundle getOpenFileBundle(String filePath){
        Bundle localBundle = new Bundle();
        localBundle.putString("filePath", filePath);
        localBundle.putString("tempPath", TEMP_FILE);
        return localBundle;
    }

    private void downloadNetFile(String fileUrl){
        RetrofitUtils.getInstance()
                .downloadFile(fileUrl)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<ResponseBody>() {
                    @Override
                    public void onNext(ResponseBody responseBody) {
                        saveToLocation(responseBody);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void saveToLocation(ResponseBody responseBody){
        BufferedOutputStream bos = null;
        try {
            byte[] bytes = responseBody.bytes();
            bos = new BufferedOutputStream(new FileOutputStream(mNetSavePath));
            bos.write(bytes, 0 ,bytes.length);
            bos.flush();
            openLocationFile(mNetSavePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
//            Log.d(TAG, "onNext: FileNotFoundException");
        } catch (IOException e) {
            e.printStackTrace();
//            Log.d(TAG, "onNext: IOException");
        }finally {
            if (bos != null){
                try {
                    bos.flush();
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
