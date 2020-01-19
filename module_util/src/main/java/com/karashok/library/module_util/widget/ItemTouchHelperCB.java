package com.karashok.library.module_util.widget;


import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.karashok.library.module_util.utilcode.utils.base_util.LogUtils;

import java.util.Collections;
import java.util.List;

/**
 * @author KaraShokZ(zhangyaozhong)
 * @name ItemTouchHelperCB
 * DESCRIPTION RecyclerView的Item拖拽回调
 * @date 2018/06/16/下午6:18
 */
public class ItemTouchHelperCB extends ItemTouchHelper.Callback{

    /**
     * Drag（拖拽）是否可用，默认可用
     */
    private boolean mDragEnabled = true;

    /**
     * Swipe（划动）是否可用，默认可用
     */
    private boolean mSwipeEnabled = true;
    private RecyclerView.Adapter mAdapter;
    private List<Object> mItems;

    public ItemTouchHelperCB setDragEnabled(boolean dragEnabled) {
        mDragEnabled = dragEnabled;
        return this;
    }

    public ItemTouchHelperCB setSwipeEnabled(boolean swipeEnabled) {
        mSwipeEnabled = swipeEnabled;
        return this;
    }

    public ItemTouchHelperCB setAdapter(RecyclerView.Adapter adapter) {
        mAdapter = adapter;
        return this;
    }

    public ItemTouchHelperCB setItems(List items) {
        mItems = items;
        return this;
    }

    /**
     * 长按拖拽是否可用
     *
     * true 可用；false 不可用
     * @return
     */
    @Override
    public boolean isLongPressDragEnabled() {
        return mDragEnabled;
    }


    /**
     * 划动是否可用
     *
     * true 可用；false 不可用
     * @return
     */
    @Override
    public boolean isItemViewSwipeEnabled() {
        return mSwipeEnabled;
    }

    /**
     * 设置Drag（拖拽）/Swipe（划动）的Flag
     *
     * 设置Drag（拖拽）的Flag为：四个方向，说明可随意拖拽
     * 设置Swipe（划动）的Flag为：左右方向，说明只检测所有的划动
     *
     * @param recyclerView
     * @param viewHolder
     * @return
     */
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlag = ItemTouchHelper.LEFT | ItemTouchHelper.DOWN | ItemTouchHelper.UP | ItemTouchHelper.RIGHT;
        int swipeFlag = ItemTouchHelper.START | ItemTouchHelper.END;

        return makeMovementFlags(dragFlag, swipeFlag);
    }

    /**
     * 拖拽事件的回调
     *
     * @param recyclerView
     * @param viewHolder
     * @param target
     * @return
     */
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        int fromPosition = viewHolder.getAdapterPosition();
        int toPosition = target.getAdapterPosition();

        LogUtils.dTag("ItemTouchHelperCB", "onMove: " + "fromPosition = " + fromPosition + "--toPosition = " + toPosition);
        /**
         * 在这里进行给原数组数据的移动
         */
        Collections.swap(mItems, fromPosition, toPosition);

        /**
         * 通知数据移动
         */
        mAdapter.notifyItemMoved(fromPosition, toPosition);

        return true;
    }

    /**
     * 划动事件的回调
     *
     * @param viewHolder
     * @param direction
     */
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        int position = viewHolder.getAdapterPosition();

        /**
         * 原数据移除数据
         */
        mItems.remove(position);

        /**
         * 通知移除
         */
        mAdapter.notifyItemRemoved(position);
    }
}
