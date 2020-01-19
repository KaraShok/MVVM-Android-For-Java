package com.karashok.library.module_preview_pictures.ui.widget.photoview;

import android.widget.ImageView;

/**
 * @author KaraShokZ(zhangyaozhong)
 * @name OnPhotoTapListener
 * DESCRIPTION A callback to be invoked when the Photo is tapped with a single
 * tap.
 * @date 2018/05/18/下午2:52
 */
public interface OnPhotoTapListener {

    /**
     * A callback to receive where the user taps on a photo. You will only receive a callback if
     * the user taps on the actual photo, tapping on 'whitespace' will be ignored.
     *
     * @param view ImageView the user tapped.
     * @param x    where the user tapped from the of the Drawable, as percentage of the
     *             Drawable width.
     * @param y    where the user tapped from the top of the Drawable, as percentage of the
     *             Drawable height.
     */
    void onPhotoTap(ImageView view, float x, float y);
}
