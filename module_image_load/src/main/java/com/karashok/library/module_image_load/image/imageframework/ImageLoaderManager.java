package com.karashok.library.module_image_load.image.imageframework;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;


import androidx.annotation.NonNull;

import com.karashok.library.module_image_load.image.fresco.FrescoImageLoader;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Ralf(wanglixin)
 * DESCRIPTION
 * @name ImageLoaderManager
 * @date 2018/05/19 下午1:48
 **/
public class ImageLoaderManager {

    private static final ImageLoaderManager INSTANCE = new ImageLoaderManager();
    private static final FrescoImageLoader frescoImageLoader = new FrescoImageLoader();
    private IImageLoaderstrategy loaderstrategy;
    private HashMap<LoaderEnum, IImageLoaderstrategy> imageloaderMap = new HashMap<>();
    private LoaderEnum curLoader = null;

    private ImageLoaderManager() {
    }

    public static ImageLoaderManager getInstance() {
        return INSTANCE;
    }

    /*
     *  可创建默认的Options设置，假如不需要使用ImageView ，
     *  请自行new一个Imageview传入即可
     *  内部只需要获取Context
     */
    public static ImageLoaderOptions getDefaultOptions(@NonNull ImageView container, @NonNull String url) {
        return new ImageLoaderOptions.Builder(container, url).isCrossFade(true).build();
    }

    public void showImage(@NonNull ImageLoaderOptions options) {
        if (getLoaderstrategy(curLoader) != null) {
            getLoaderstrategy(curLoader).showImage(options);
        }
    }

    public void showImage(@NonNull ImageLoaderOptions options, LoaderEnum loaderEnum) {
        if (getLoaderstrategy(loaderEnum) != null) {
            getLoaderstrategy(loaderEnum).showImage(options);
        }
    }

    public void hideImage(@NonNull View view, int visiable) {
        if (getLoaderstrategy(curLoader) != null) {
            getLoaderstrategy(curLoader).hideImage(view, visiable);
        }
    }


    public void cleanMemory(Context context) {
        getLoaderstrategy(curLoader).cleanMemory(context);
    }

    public void pause(Context context) {
        if (getLoaderstrategy(curLoader) != null) {
            getLoaderstrategy(curLoader).pause(context);
        }
    }

    public void resume(Context context) {
        if (getLoaderstrategy(curLoader) != null) {
            getLoaderstrategy(curLoader).resume(context);
        }
    }

    public void setCurImageLoader(LoaderEnum loader) {
        curLoader = loader;
    }

    // 在application的oncreate中初始化
    public void init(Context context, ImageLoaderConfig config) {
        imageloaderMap = config.getImageloaderMap();
        for (Map.Entry<LoaderEnum, IImageLoaderstrategy> entry : imageloaderMap.entrySet()) {
            if (entry.getValue() != null) {
                entry.getValue().init(context, config);
            }

            if (curLoader == null) {
                curLoader = entry.getKey();
            }
        }


//        loaderstrategy=new GlideImageLocader();
//        loaderstrategy.init(context);
    }

    public void init(Context context){

        //默认配置
        ImageLoaderConfig config = new ImageLoaderConfig.Builder(LoaderEnum.FRESCO,frescoImageLoader)
                .maxMemory(40*1024*1024L)  // 配置内存缓存，单位为Byte
                .build();

        init(context,config);
    }

    private IImageLoaderstrategy getLoaderstrategy(LoaderEnum loaderEnum) {
        return imageloaderMap.get(loaderEnum);
    }
}
