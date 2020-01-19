package com.karashok.mvvm.module_gankio.data.view_modle;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableList;

import com.karashok.library.common.base.BaseViewModel;
import com.karashok.library.common.databinding.base.OnItemBindClass;
import com.karashok.library.common.databinding.base.collection.MergeObservableList;
import com.karashok.mvvm.module_gankio.R;
import com.karashok.mvvm.module_gankio.data.entity.ZhihuEntity;
import com.karashok.mvvm.module_gankio.data.entity.ZhihuStoriesEntity;
import com.karashok.mvvm.module_gankio.data.entity.ZhihuTopStoriesEntity;
import com.karashok.mvvm.module_gankio.net.data_manager.AtyZhihuDM;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * @author KaraShok(zhangyaozhong)
 * @name AtyZhihuVM
 * DESCRIPTION
 * @date 2018/06/27/下午1:21
 */

public class AtyZhihuVM extends BaseViewModel<ZhihuEntity, AtyZhihuDM> {

    public AtyZhihuVM(@NonNull Application application) {
        super(application);
    }

    @Override
    protected AtyZhihuDM initDataManager() {
        return new AtyZhihuDM(this);
    }

    public final OnItemBindClass<Object> multipleItems = new OnItemBindClass<>()
            .map(ItemAtyZhihuHeaderVM.class, com.karashok.mvvm.module_gankio.BR.itemAtyZhihuHeader, R.layout.item_aty_zhihu_header)
            .map(ItemAtyZhihuVM.class, com.karashok.mvvm.module_gankio.BR.itemAtyZhihuVM, R.layout.item_aty_zhihu);

    public final ObservableList<ItemAtyZhihuVM> items = new ObservableArrayList<>();

    private ObservableField<ItemAtyZhihuHeaderVM> topModel = new ObservableField<>();

    public final MergeObservableList<Object> headerFooterItems = new MergeObservableList<>();

    public void topNewsList(){
        mDataManager.getTopNewsList();
    }

    @Override
    public void dataSuccess(ZhihuEntity data) {
        super.dataSuccess(data);
        // 轮播图
        List<ZhihuTopStoriesEntity> top_stories = data.getTop_stories();
        if (top_stories != null && !top_stories.isEmpty()){
            ItemAtyZhihuHeaderVM topStoriesViewModel = new ItemAtyZhihuHeaderVM(top_stories);
            headerFooterItems.insertItem(topStoriesViewModel);
            topModel.set(topStoriesViewModel);
        }
        // item
        List<ZhihuStoriesEntity> stories = data.getStories();
        if (stories != null && !stories.isEmpty()){
            Observable.fromIterable(stories)
                    .map(new Function<ZhihuStoriesEntity, ItemAtyZhihuVM>() {
                        @Override
                        public ItemAtyZhihuVM apply(ZhihuStoriesEntity zhihuStoriesEntity) throws Exception {
                            return new ItemAtyZhihuVM(zhihuStoriesEntity);
                        }
                    })
                    .toList()
                    .subscribe(new Consumer<List<ItemAtyZhihuVM>>() {
                        @Override
                        public void accept(List<ItemAtyZhihuVM> zhihuItemStoriesViewModels) throws Exception {
                            items.addAll(zhihuItemStoriesViewModels);
                            headerFooterItems.insertList(items);
                        }
                    });
        }
    }
}
