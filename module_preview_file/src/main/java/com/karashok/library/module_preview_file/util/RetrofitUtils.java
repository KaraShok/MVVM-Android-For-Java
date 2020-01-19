package com.karashok.library.module_preview_file.util;

import com.karashok.library.module_preview_file.net.DownloadFile;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * @author KaraShok(zhangyaozhong)
 * @name RetrofitUtils
 * DESCRIPTION Retrofit实例
 * @date 2018/06/13/下午4:13
 */

public class RetrofitUtils {

    private static volatile DownloadFile mDownloadFile;

    private RetrofitUtils(){

    }

    public static synchronized DownloadFile getInstance(){
        if (mDownloadFile == null){
            synchronized (RetrofitUtils.class){
                if (mDownloadFile == null){
                    mDownloadFile = initRetrofit();
                }
            }
        }
        return mDownloadFile;
    }

    private static DownloadFile initRetrofit(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.google.com")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        DownloadFile downloadFile = retrofit.create(DownloadFile.class);
        return downloadFile;
    }
}
