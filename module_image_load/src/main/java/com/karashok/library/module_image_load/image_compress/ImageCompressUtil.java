package com.karashok.library.module_image_load.image_compress;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.text.TextUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;
import top.zibin.luban.OnRenameListener;

/**
 * @author Ralf(wanglixin)
 * DESCRIPTION 图片压缩工具类
 * @name ImageCompressUtil
 * @date 2018/05/31 下午8:09
 **/
public class ImageCompressUtil {

    /**
     * 计算图片大小
     *
     * @param srcImg 图片文件
     * @return 分辨率-宽和高
     */
    public static int[] computeSize(File srcImg) {
        int[] size = new int[2];

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inSampleSize = 1;

        BitmapFactory.decodeFile(srcImg.getAbsolutePath(), options);
        size[0] = options.outWidth;
        size[1] = options.outHeight;

        return size;
    }

    /**
     * 同步方法调用
     *
     * @param photos String类型-path；Uri类型；File类型
     */
    public static void withRx(final Context context, final List<String> photos, final LubanCallBack callBack) {
        Flowable.just(photos)
                .observeOn(Schedulers.io())
                .map(new Function<List<String>, List<File>>() {
                    @Override
                    public List<File> apply(@NonNull List<String> list) throws Exception {
                        return Luban.with(context)
                                .load(list)
                                .get();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<File>>() {
                    @Override
                    public void accept(@NonNull List<File> list) {

                        List<Uri> uriList = converFileToUri(list);
                        callBack.getCompressedImages(uriList);
                    }
                });
    }

    /**
     * 异步压缩图片
     *
     * @param context          上下文
     * @param ignoreSize       size the value of file size, unit KB, default 100K
     * @param photos           需要压缩的文件
     * @param targetDir        存储的路径
     * @param renameListener   文件重命名回调
     * @param compressListener 压缩回调，可以利用该回调获取路径
     */
    public static <T> void withLs(Context context, int ignoreSize, final List<String> photos,
                                  String targetDir, OnRenameListener renameListener
            , OnCompressListener compressListener) {

        Luban.with(context)
                .load(photos)
                .ignoreBy(ignoreSize)
                .setTargetDir(targetDir)
                .filter(new CompressionPredicate() {
                    @Override
                    public boolean apply(String path) {
                        return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
                    }
                })
                .setRenameListener(renameListener)
                .setCompressListener(compressListener)
                .launch();
    }

    /**
     * File 转换成 Uri
     *
     * @param list 传入的文件
     * @return Uri 集合
     */
    private static List<Uri> converFileToUri(List<File> list) {

        List<Uri> uriList = new ArrayList<>();
        for (File file : list) {

            String filePath = file.getAbsolutePath();
            Uri uri = Uri.parse(filePath);
            uriList.add(uri);
        }

        return uriList;
    }

    public interface LubanCallBack {
        void getCompressedImages(List<Uri> list);
    }
}
