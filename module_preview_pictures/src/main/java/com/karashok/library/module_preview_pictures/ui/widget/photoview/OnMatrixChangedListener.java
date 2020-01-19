package com.karashok.library.module_preview_pictures.ui.widget.photoview;

import android.graphics.RectF;

/**
 * @author KaraShokZ(zhangyaozhong)
 * @name OnMatrixChangedListener
 * DESCRIPTION
 * Interface definition for a callback to be invoked when the internal Matrix has changed for
 * this View.
 * @date 2018/05/18/下午2:52
 */
public interface OnMatrixChangedListener {

    /**
     * Callback for when the Matrix displaying the Drawable has changed. This could be because
     * the View's bounds have changed, or the user has zoomed.
     *
     * @param rect - Rectangle displaying the Drawable's new bounds.
     */
    void onMatrixChanged(RectF rect);
}
