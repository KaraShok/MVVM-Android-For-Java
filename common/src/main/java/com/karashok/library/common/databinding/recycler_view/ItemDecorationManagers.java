package com.karashok.library.common.databinding.recycler_view;


import androidx.recyclerview.widget.RecyclerView;

import com.karashok.library.common.ui.widget.MyRecycleItemDecoration;

/**
 * @author KaraShokZ (张耀中)
 * DESCRIPTION
 * @name ItemDecorationManagers
 * @date 2019/05/05 17:39
 **/
public class ItemDecorationManagers {

    public interface ItemDecorationFactory {
        RecyclerView.ItemDecoration create(RecyclerView recyclerView);
    }

    /**
     * 创建分割线工厂实例
     * @param decorationColor 分割线颜色
     * @param decorationHeightDP 分割线 高度/宽度
     * @param marginColor 两边间距颜色
     * @param marginDP 间距大小
     * @return
     */
    public static ItemDecorationManagers.ItemDecorationFactory vertical(int decorationColor, int decorationHeightDP,
                                                                        int marginColor, int marginDP) {
        return new ItemDecorationFactory() {
            @Override
            public RecyclerView.ItemDecoration create(RecyclerView recyclerView) {
                return MyRecycleItemDecoration.getInstance(recyclerView,MyRecycleItemDecoration.VERTICAL,decorationColor,decorationHeightDP,marginColor,marginDP);
            }
        };
    }
}
