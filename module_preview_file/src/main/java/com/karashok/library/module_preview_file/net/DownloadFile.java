package com.karashok.library.module_preview_file.net;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * @author KaraShok(zhangyaozhong)
 * @name DownloadFile
 * DESCRIPTION 下载网络文件
 * @date 2018/04/25/下午2:19
 */
public interface DownloadFile {

    @Streaming
    @GET
    Observable<ResponseBody> downloadFile(@Url String url);
}
