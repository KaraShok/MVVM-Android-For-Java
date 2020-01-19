package com.karashok.library.common.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.karashok.library.module_util.utilcode.utils.base_util.SizeUtils;

/**
 * @author KaraShokZ (张耀中)
 * DESCRIPTION
 * @name MyRecycleItemDecoration
 * @date 2019/05/05 17:26
 **/
public class MyRecycleItemDecoration extends RecyclerView.ItemDecoration {
    public static final int HORIZONTAL = LinearLayout.HORIZONTAL;
    public static final int VERTICAL = LinearLayout.VERTICAL;

    /**
     * 创建分割线实例
     * @param recyclerView RecyclerView
     * @param orientation 分割线方向
     * @param decorationColor 分割线十六进制颜色值
     * @param decorationHeightDP 分割线 高度/宽度
     * @param marginColor 两边间距十六进制颜色值
     * @param marginDP 间距大小
     * @return
     */
    public static MyRecycleItemDecoration getInstance(RecyclerView recyclerView, int orientation,
                                                      int decorationColor, int decorationHeightDP,
                                                      int marginColor, int marginDP){
        MyRecycleItemDecoration itemDecoration = new MyRecycleItemDecoration(recyclerView.getContext(),
                orientation,decorationColor, decorationHeightDP, marginColor, marginDP);
        return itemDecoration;
    }

    private int mOrientation;

    /**
     * 分割线缩进值
     */
    private int mMarginPX;
    /**
     * 分割线高度
     */
    private int mDecorationHeightPX;

    /**
     * 缩进画笔
     */
    private Paint mMarginPaint;
    /**
     * 分割线画笔
     */
    private Paint mDecorationPaint;

    /**
     * @param context
     * @param orientation ayout的方向
     * @param decorationColor 引入的drawable的ID
     * @param marginColor 分割线两端的颜色
     * @param marginDP 分割线缩进值
     */
    public MyRecycleItemDecoration(Context context, int orientation, int decorationColor, int decorationHeightDP,
                                   int marginColor, int marginDP) {
        if (marginDP <= 0){
            mMarginPX = SizeUtils.dp2px(marginDP);

            mMarginPaint = new Paint();
            mMarginPaint.setColor(marginColor);
            mMarginPaint.setStyle(Paint.Style.FILL);
            mMarginPaint.setAntiAlias(true);
        }

        mDecorationHeightPX = SizeUtils.dp2px(decorationHeightDP);

        mDecorationPaint = new Paint();
        mDecorationPaint.setColor(decorationColor);
        mDecorationPaint.setStyle(Paint.Style.FILL);
        mDecorationPaint.setAntiAlias(true);
        setOrientation(orientation);
    }

    public void setOrientation(int orientation) {
        if (orientation != HORIZONTAL && orientation != VERTICAL) {
            throw new IllegalArgumentException("invalid orientation");
        }
        mOrientation = orientation;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent) {
        if (mOrientation == VERTICAL) {
            drawVertical(c, parent);
        } else {
            drawHorizontal(c, parent);
        }
    }

    private void drawVertical(Canvas c, RecyclerView parent) {
        final int left = parent.getPaddingLeft();
        final int right = parent.getWidth() - parent.getPaddingRight();
        final int childCount = parent.getChildCount();

        for (int i = 0,size = childCount; i < size; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int top = child.getBottom() + params.bottomMargin;
            if (mMarginPX > 0) {
                c.drawRect(left, top, mMarginPX, mDecorationHeightPX, mMarginPaint);
                c.drawRect(right - mMarginPX, top, right,  mDecorationHeightPX, mMarginPaint);
                RectF rectF = new RectF(left + mMarginPX, top, right - mMarginPX, mDecorationHeightPX);
                c.drawRect(rectF, mDecorationPaint);
            } else {
                RectF rectF = new RectF(left, top, right, mDecorationHeightPX);
                c.drawRect(rectF, mDecorationPaint);
            }
        }
    }

    private void drawHorizontal(Canvas c, RecyclerView parent) {
        final int top = parent.getPaddingTop();
        final int bottom = parent.getHeight() - parent.getPaddingBottom();
        final int childCount = parent.getChildCount();

        for (int i = 0; i < childCount - 1; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int left = child.getRight() + params.rightMargin;
            final int right = left + mDecorationHeightPX;
            if (mMarginPX > 0){
                c.drawRect(left, top, right, mMarginPX, mMarginPaint);
                c.drawRect(left, bottom - mMarginPX, right,  bottom, mMarginPaint);
                RectF rectF = new RectF(left, top + mMarginPX, right,bottom - mMarginPX);
                c.drawRect(rectF, mDecorationPaint);
            }else {
                RectF rectF = new RectF(left, top, right, bottom);
                c.drawRect(rectF, mDecorationPaint);
            }

        }
    }

    /**
     * 由于Divider也有宽高，每一个Item需要向下或者向右偏移
     * @param outRect
     * @param itemPosition
     * @param parent
     */
    @Override
    public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
        if (mOrientation == VERTICAL) {
            outRect.set(0, 0, 0, mDecorationHeightPX);
        } else {
            outRect.set(0, 0, parent.getWidth() - parent.getPaddingRight(), 0);
        }
    }
}
