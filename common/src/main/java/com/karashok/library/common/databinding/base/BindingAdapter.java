package com.karashok.library.common.databinding.base;

import androidx.databinding.BindingConversion;

/**
 * @author KaraShokZ(zhangyaozhong)
 * @name BindingAdapter
 * DESCRIPTION Adapter绑定的Item的方法类
 * @date 2018/06/16/下午6:18
 */
public class BindingAdapter {

    /**
     * 将OnItemBind转化为ItemBing，并传递给Adapter
     * @param onItemBind
     * @param <T>
     * @return
     */
    @BindingConversion
    public static <T> ItemBinding<T> toItemBinding(OnItemBind<T> onItemBind) {
        return ItemBinding.of(onItemBind);
    }
}
