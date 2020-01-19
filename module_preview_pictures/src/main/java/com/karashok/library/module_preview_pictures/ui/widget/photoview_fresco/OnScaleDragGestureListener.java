package com.karashok.library.module_preview_pictures.ui.widget.photoview_fresco;

/**
 * @author KaraShokZ(zhangyaozhong)
 * @name OnScaleDragGestureListener
 * DESCRIPTION
 * @date 2018/07/25/下午5:09
 */

public interface OnScaleDragGestureListener {
    void onDrag(float dx, float dy);

    void onFling(float startX, float startY, float velocityX, float velocityY);

    void onScale(float scaleFactor, float focusX, float focusY);

    void onScaleEnd();
}
