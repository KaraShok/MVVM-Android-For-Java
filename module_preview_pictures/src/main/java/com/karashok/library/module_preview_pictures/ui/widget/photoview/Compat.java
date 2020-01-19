package com.karashok.library.module_preview_pictures.ui.widget.photoview;

import android.annotation.TargetApi;
import android.os.Build;
import android.view.View;

/**
 * @author KaraShokZ(zhangyaozhong)
 * @name Compat
 * DESCRIPTION
 * @date 2018/07/25/下午2:07
 */

public class Compat {

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
}
