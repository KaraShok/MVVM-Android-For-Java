package com.karashok.library.common.databinding.swipe_refresh;


import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableBoolean;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.karashok.library.common.databinding.binding_event.ViewCommandManager;

/**
 * @author KaraShokZ(zhangyaozhong)
 * @name SwipeRefreshBindingAdapter
 * DESCRIPTION SwipeRefreshLayout的事件绑定
 * @date 2018/06/16/下午6:18
 */
public class SwipeRefreshBindingAdapter {
    @BindingAdapter({"onRefreshCommand"})
    public static void onRefreshCommand(SwipeRefreshLayout swipeRefreshLayout, final ViewCommandManager refresh) {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (refresh != null) {
                    refresh.execute();
                }
            }
        });
    }

    /**
     * 刷新的状态值
     */
    public static class SwipeRefreshState {
        public final ObservableBoolean isRefreshing = new ObservableBoolean(true);
        public final ObservableBoolean progressRefreshing = new ObservableBoolean(true);
    }
}
