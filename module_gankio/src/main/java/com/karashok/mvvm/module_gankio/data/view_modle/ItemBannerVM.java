package com.karashok.mvvm.module_gankio.data.view_modle;


import androidx.databinding.ObservableField;

import com.karashok.library.common.databinding.binding_event.Fun1;
import com.karashok.library.common.databinding.binding_event.ViewCommandManager;
import com.karashok.library.module_util.utilcode.utils.view_util.ToastUtils;
import com.karashok.mvvm.module_gankio.data.entity.ZhihuTopStoriesEntity;

/**
 * @author KaraShok(zhangyaozhong)
 * @name ItemBannerVM
 * DESCRIPTION
 * @date 2018/06/27/下午1:34
 */

public class ItemBannerVM {

    private ZhihuTopStoriesEntity mItem;

    public ItemBannerVM(ZhihuTopStoriesEntity item) {
        this.mItem = item;
        image.set(item.getImage());
        title.set(item.getTitle());
    }

    public final ObservableField<String> image = new ObservableField<>();

    public final ObservableField<String> title = new ObservableField<>();

    /**
     * Item的View点击事件
     */
    public final ViewCommandManager<Integer> itemClick = new ViewCommandManager<>(new Fun1<Integer>() {
        @Override
        public void action(Integer item) {
            ToastUtils.showShort(mItem.getTitle());
        }
    });

}
