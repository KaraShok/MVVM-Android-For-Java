package com.karashok.library.module_preview_pictures.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.karashok.library.module_image_load.image.imageframework.ImageLoaderManager;
import com.karashok.library.module_image_load.image.imageframework.ImageLoaderOptions;
import com.karashok.library.module_preview_pictures.R;


/**
 * @author KaraShokZ(zhangyaozhong)
 * @name PhotoViewUtils
 * DESCRIPTION 图片预览的一些工具类
 * @date 2018/05/18/下午2:52
 */
public class PhotoViewUtils {

    private static final int SIXTY_FPS_INTERVAL = 1000 / 60;

    public static void postOnAnimation(View view, Runnable runnable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            postOnAnimationJellyBean(view, runnable);
        } else {
            view.postDelayed(runnable, SIXTY_FPS_INTERVAL);
        }
    }

    @TargetApi(16)
    private static void postOnAnimationJellyBean(View view, Runnable runnable) {
        view.postOnAnimation(runnable);
    }

    public static void checkZoomLevels(float minZoom, float midZoom,
                                float maxZoom) {
        if (minZoom >= midZoom) {
            throw new IllegalArgumentException(
                    "Minimum zoom has to be less than Medium zoom. Call setMinimumZoom() with a more appropriate value");
        } else if (midZoom >= maxZoom) {
            throw new IllegalArgumentException(
                    "Medium zoom has to be less than Maximum zoom. Call setMaximumZoom() with a more appropriate value");
        }
    }

    public static boolean hasDrawable(ImageView imageView) {
        return imageView.getDrawable() != null;
    }

    public static boolean isSupportedScaleType(final ImageView.ScaleType scaleType) {
        if (scaleType == null) {
            return false;
        }
        switch (scaleType) {
            case MATRIX:
                throw new IllegalStateException("Matrix scale type is not supported");
        }
        return true;
    }

    public static int getPointerIndex(int action) {
        return (action & MotionEvent.ACTION_POINTER_INDEX_MASK) >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
    }

    /**
     * Glide加载图片
     *
     * @param context
     * @param view
     * @param path
     */
    public static void loadImage(Context context, ImageView view, String path){

        ImageLoaderOptions options = new ImageLoaderOptions.Builder(view, path)
                .placeholder(R.drawable.icon_photo_picture2x)
                .error(R.drawable.icon_photo_picture2x)
                .build();
        ImageLoaderManager.getInstance().init(view.getContext());

        ImageLoaderManager.getInstance().showImage(options);
    }
}
