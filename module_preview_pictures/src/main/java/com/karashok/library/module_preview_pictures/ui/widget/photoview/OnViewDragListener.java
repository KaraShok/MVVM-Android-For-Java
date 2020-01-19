package com.karashok.library.module_preview_pictures.ui.widget.photoview;

/**
 * @author KaraShokZ(zhangyaozhong)
 * @name OnViewDragListener
 * DESCRIPTION Interface definition for a callback to be invoked when the photo is experiencing a drag event
 * @date 2018/07/25/下午2:09
 */

public interface OnViewDragListener {

    /**
     * Callback for when the photo is experiencing a drag event. This cannot be invoked when the
     * user is scaling.
     *
     * @param dx The change of the coordinates in the x-direction
     * @param dy The change of the coordinates in the y-direction
     */
    void onDrag(float dx, float dy);
}
