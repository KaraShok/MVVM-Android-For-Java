package com.karashok.library.module_preview_pictures.ui.widget.photoview;

import android.widget.ImageView;

/**
 * @author KaraShokZ(zhangyaozhong)
 * @name OnOutsidePhotoTapListener
 * DESCRIPTION Callback when the user tapped outside of the photo
 * @date 2018/05/18/下午2:52
 */
public interface OnOutsidePhotoTapListener {

    /**
     * The outside of the photo has been tapped
     */
    void onOutsidePhotoTap(ImageView imageView);
}
