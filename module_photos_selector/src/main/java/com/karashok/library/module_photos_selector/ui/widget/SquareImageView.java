package com.karashok.library.module_photos_selector.ui.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

/**
 * @author KaraShokZ(zhangyaozhong)
 * @name SquareImageView
 * DESCRIPTION 正方形的ImageView
 * @date 2018/05/18/下午4:42
 */
public class SquareImageView extends AppCompatImageView {

    public SquareImageView(Context context) {
        super(context);
    }

    public SquareImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // Set a square imageView.
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
