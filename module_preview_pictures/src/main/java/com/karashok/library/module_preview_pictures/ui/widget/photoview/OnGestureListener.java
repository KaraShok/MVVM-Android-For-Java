package com.karashok.library.module_preview_pictures.ui.widget.photoview;

/**
 * @author KaraShokZ(zhangyaozhong)
 * @name OnGestureListener
 * DESCRIPTION View的一些事件回调
 * @date 2018/05/18/下午2:52
 */
public interface OnGestureListener {

    void onDrag(float dx, float dy);

    void onFling(float startX, float startY, float velocityX,
                 float velocityY);

    void onScale(float scaleFactor, float focusX, float focusY);
}
