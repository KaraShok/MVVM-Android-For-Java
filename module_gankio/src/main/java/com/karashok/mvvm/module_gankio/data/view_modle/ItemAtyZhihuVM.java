package com.karashok.mvvm.module_gankio.data.view_modle;


import android.widget.Toast;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableList;

import com.karashok.library.common.databinding.binding_event.Fun1;
import com.karashok.library.common.databinding.binding_event.ViewCommandManager;
import com.karashok.library.module_util.utilcode.utils.Utils;
import com.karashok.mvvm.module_gankio.data.entity.ZhihuStoriesEntity;
import com.karashok.mvvm.module_gankio.data.entity.ZhihuTopStoriesEntity;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * @author KaraShok(zhangyaozhong)
 * @name ItemAtyZhihuVM
 * DESCRIPTION
 * @date 2018/06/27/下午1:47
 */

public class ItemAtyZhihuVM {

    private ZhihuStoriesEntity mItem;

    /**
     * 双向绑定的值
     */
    public final ObservableField<String> title = new ObservableField<>();
    public final ObservableField<String> image = new ObservableField<>();

    /**
     * Item的View点击事件
     */
    public final ViewCommandManager<Integer> itemClick = new ViewCommandManager<>(new Fun1<Integer>() {
        @Override
        public void action(Integer item) {
            Toast.makeText(Utils.getApp(),mItem.getTitle(),Toast.LENGTH_SHORT).show();
        }
    });

    public ItemAtyZhihuVM(ZhihuStoriesEntity item) {
        mItem = item;
        title.set(item.getTitle());
        image.set(item.getImages().get(0));
    }

    private List<ZhihuTopStoriesEntity> mBanners;

//    public final ItemBinding<ItemBannerVM> topItemView = ItemBinding.of(BR.itemBannerVM, R.layout.item_banner);

    public final ObservableList<ItemBannerVM> topItemViewModel = new ObservableArrayList<>();

    public ItemAtyZhihuVM(List<ZhihuTopStoriesEntity> banners) {
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
                    public void accept(List<ItemBannerVM> itemBannerViewModels) throws Exception {
                        topItemViewModel.addAll(itemBannerViewModels);
                    }
                });

    }
}
