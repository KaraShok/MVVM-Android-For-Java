package com.karashok.library.module_preview_file.util;

import android.app.Application;

import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.TbsDownloader;
import com.tencent.smtt.sdk.TbsListener;

/**
 * @author KaraShok(zhangyaozhong)
 * @name TbsFileUtils
 * DESCRIPTION 腾讯TBS浏览服务初识化
 * @date 2018/04/25/下午1:53
 */
public class TbsFileUtils {

    private static final String TAG = TbsFileUtils.class.getSimpleName();

    public static void init(Application application) {
        initX5(application);
        initTbsListener();
    }

    private static void initX5(final Application application) {
        QbSdk.initX5Environment(application, new QbSdk.PreInitCallback() {
            @Override
            public void onCoreInitFinished() {

            }

            @Override
            public void onViewInitFinished(boolean b) {
                if (!b) {
                    //判断是否要自行下载内核
                    boolean needDownload = TbsDownloader.needDownload(application, TbsDownloader.DOWNLOAD_OVERSEA_TBS);
//                    Log.d(TAG, "needDownload: " + needDownload);
                    if (!needDownload) {
                        TbsDownloader.startDownload(application);
                    }
                }
            }
        });

    }

    private static void initTbsListener() {
        QbSdk.setTbsListener(new TbsListener() {
            @Override
            public void onDownloadFinish(int i) {
                //tbs内核下载完成回调
//                Log.d(TAG, "onDownloadFinish: " + i);
            }

            @Override
            public void onInstallFinish(int i) {
                //内核安装完成回调，
//                Log.d(TAG, "onInstallFinish: " + i);
            }

            @Override
            public void onDownloadProgress(int i) {
                //下载进度监听
//                Log.d(TAG, "onDownloadProgress: " + i);
            }
        });
    }
}
