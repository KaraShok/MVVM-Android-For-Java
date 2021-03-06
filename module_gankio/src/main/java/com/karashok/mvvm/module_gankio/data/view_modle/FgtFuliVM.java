package com.karashok.mvvm.module_gankio.data.view_modle;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;

import com.karashok.library.common.base.BaseViewModel;
import com.karashok.library.common.databinding.base.ItemBinding;
import com.karashok.library.common.databinding.binding_event.Fun;
import com.karashok.library.common.databinding.binding_event.Fun1;
import com.karashok.library.common.databinding.binding_event.ViewCommandManager;
import com.karashok.library.common.databinding.swipe_refresh.SwipeRefreshBindingAdapter;
import com.karashok.mvvm.module_gankio.R;
import com.karashok.mvvm.module_gankio.data.entity.DataEntity;
import com.karashok.mvvm.module_gankio.net.data_manager.GankIODM;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * @author KaraShokZ (张耀中)
 * DESCRIPTION
 * @name FgtFuliVM
 * @date 2019/03/26 16:14
 **/
public class FgtFuliVM extends BaseViewModel<List<DataEntity>, GankIODM> {

    private int mPage = 1;
    private static final String CATEGORY = "福利";

    public FgtFuliVM(@NonNull Application application) {
        super(application);
    }

    public final void initData(){
        mDataManager.getGankIOList(CATEGORY, mPage);
    }

    public final ItemBinding<ItemFgtFuliVM> singleItem = ItemBinding.of(com.karashok.mvvm.module_gankio.BR.itemFgtFuliVM, R.layout.item_gank_fuli);

    public final ObservableList<ItemFgtFuliVM> items = new ObservableArrayList<>();

    public final SwipeRefreshBindingAdapter.SwipeRefreshState refreshState
            = new SwipeRefreshBindingAdapter.SwipeRefreshState();

    @Override
    protected GankIODM initDataManager() {
        return new GankIODM(this);
    }

    public final ViewCommandManager refresh = new ViewCommandManager(new Fun() {
        @Override
        public void action() {
            mPage = 1;
            mDataManager.getGankIOList(CATEGORY, mPage);
        }
    });

    public final ViewCommandManager<Integer> onLoadMoreCommand =
            new ViewCommandManager(new Fun1<Integer>() {
                @Override
                public void action(Integer item) {
                    setRefreshState(true);
                    mDataManager.getGankIOList(CATEGORY, mPage);
                }
            });

    public void setRefreshState(boolean state) {
        refreshState.isRefreshing.set(state);
        refreshState.progressRefreshing.set(state);
    }

    @Override
    public void dataSuccess(final List<DataEntity> data) {
        super.dataSuccess(data);
        if (data != null && !data.isEmpty()) {
            Observable.fromIterable(data)
                    .map(new Function<DataEntity, ItemFgtFuliVM>() {
                        @Override
                        public ItemFgtFuliVM apply(DataEntity dataEntity) throws Exception {
                            return new ItemFgtFuliVM(dataEntity);
                        }
                    })
                    .toList()
                    .subscribe(new Consumer<List<ItemFgtFuliVM>>() {
                        @Override
                        public void accept(List<ItemFgtFuliVM> itemFgtFuliVMS) throws Exception {
                            items.addAll(itemFgtFuliVMS);
                        }
                    });
        }
        mPage++;
    }

    @Override
    public void dataStart() {
        setRefreshState(true);
    }

    @Override
    public void dataError(Throwable e) {
        setRefreshState(false);
    }

    @Override
    public void dataComplete() {
        setRefreshState(false);
    }

    @Override
    public void dataRequestCancel() {
        setRefreshState(false);
    }
}