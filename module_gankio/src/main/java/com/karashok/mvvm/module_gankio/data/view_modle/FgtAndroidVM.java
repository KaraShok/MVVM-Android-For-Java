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
import com.karashok.mvvm.module_gankio.BR;
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
 * @name FgtAndroidVM
 * @date 2019/03/25 20:50
 **/
public class FgtAndroidVM extends BaseViewModel<List<DataEntity>, GankIODM> {

    private int mPage = 1;
    private static final String CATEGORY = "Android";

    public FgtAndroidVM(@NonNull Application application) {
        super(application);
    }

    public final void initData(){
        mDataManager.getGankIOList(CATEGORY,mPage);
    }

    public final ItemBinding<ItemFgtAndroidVM> singleItem = ItemBinding.of(BR.itemFgtAndroidVM, R.layout.item_gank_android);

    public final ObservableList<ItemFgtAndroidVM> items = new ObservableArrayList<>();

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
                    mDataManager.getGankIOList(CATEGORY,mPage);
                }
            });

    public void setRefreshState(boolean state){
        refreshState.isRefreshing.set(state);
        refreshState.progressRefreshing.set(state);
    }

    @Override
    public void dataSuccess(final List<DataEntity> data) {
        super.dataSuccess(data);
        if (data != null && !data.isEmpty()){
            Observable.fromIterable(data)
                    .map(new Function<DataEntity, ItemFgtAndroidVM>() {
                        @Override
                        public ItemFgtAndroidVM apply(DataEntity dataEntity) throws Exception {
                            return new ItemFgtAndroidVM(dataEntity);
                        }
                    })
                    .toList()
                    .subscribe(new Consumer<List<ItemFgtAndroidVM>>() {
                        @Override
                        public void accept(List<ItemFgtAndroidVM> itemFgtAndroidVMS) throws Exception {
                            items.addAll(itemFgtAndroidVMS);
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
