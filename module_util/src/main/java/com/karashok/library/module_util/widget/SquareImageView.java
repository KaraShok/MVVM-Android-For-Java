package com.karashok.library.module_util.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

/**
 * @author KaraShokZ(zhangyaozhong)
 * @name SquareImageView
 * DESCRIPTION 正方形的ImageView
 * @date 2018/06/16/下午6:18
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
