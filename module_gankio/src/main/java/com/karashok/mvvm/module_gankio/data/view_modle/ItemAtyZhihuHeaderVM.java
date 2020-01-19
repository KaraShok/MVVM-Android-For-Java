package com.karashok.mvvm.module_gankio.data.view_modle;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;

import com.karashok.library.common.databinding.base.ItemBinding;
import com.karashok.mvvm.module_gankio.R;
import com.karashok.mvvm.module_gankio.data.entity.ZhihuTopStoriesEntity;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * @author KaraShok(zhangyaozhong)
 * @name ItemAtyZhihuHeaderVM
 * DESCRIPTION
 * @date 2018/06/27/下午1:30
 */

public class ItemAtyZhihuHeaderVM {

    private List<ZhihuTopStoriesEntity> mBanners;

    public final ItemBinding<ItemBannerVM> topItemView = ItemBinding.of(com.karashok.mvvm.module_gankio.BR.itemBannerVM, R.layout.item_banner);

    public final ObservableList<ItemBannerVM> topItemVM = new ObservableArrayList<>();

    public ItemAtyZhihuHeaderVM(List<ZhihuTopStoriesEntity> banners) {
        this.mBanners = banners;

        Observable.fromIterable(banners)
                .map(new Function<ZhihuTopStoriesEntity, ItemBannerVM>() {
                    @Override
                    public ItemBannerVM apply(ZhihuTopStoriesEntity zhihuTopStoriesEntity) throws Exception {
                        return new ItemBannerVM(zhihuTopStoriesEntity);
                    }
                })
                .toList()
                .subscribe(new Consumer<List<ItemBannerVM>>() {
                    @Override
                    public void accept(List<ItemBannerVM> itemBannerVMS) throws Exception {
                        topItemVM.addAll(itemBannerVMS);
                    }
                });
    }
}
