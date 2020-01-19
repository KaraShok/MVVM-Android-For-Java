package com.karashok.library.module_preview_pictures.ui.widget.photoview;

/**
 * @author KaraShokZ(zhangyaozhong)
 * @name OnScaleChangedListener
 * DESCRIPTION Interface definition for callback to be
 * invoked when attached ImageView scale changes
 * @date 2018/05/18/下午2:52
 */
public interface OnScaleChangedListener {

    /**
     * Callback for when the scale changes
     *
     * @param scaleFactor the scale factor (less than 1 for zoom out, greater than 1 for zoom in)
     * @param focusX      focal point X position
     * @param focusY      focal point Y position
     */
    void onScaleChange(float scaleFactor, float focusX, float focusY);
}
