package com.karashok.library.common.databinding.view_pager;


import androidx.databinding.BindingAdapter;
import androidx.viewpager.widget.ViewPager;

import com.karashok.library.common.base.BaseFragment;
import com.karashok.library.common.databinding.base.ItemBinding;
import com.karashok.library.common.databinding.binding_event.ViewCommandManager;
import com.karashok.library.module_util.utilcode.utils.app_util.ActivityManager;

import java.util.List;

/**
 * @author KaraShokZ(zhangyaozhong)
 * @name ViewPagerBindingAdapter
 * DESCRIPTION ViewPager的事件绑定
 * @date 2018/06/16/下午6:18
 */
public class ViewPagerBindingAdapter {

    /**
     * ViewPager的事件处理
     * @param viewPager
     * @param onPageScrolledCommand
     * @param onPageSelectedCommand
     * @param onPageScrollStateChangedCommand
     */
    @BindingAdapter(value = {"onPageScrolledCommand", "onPageSelectedCommand", "onPageScrollStateChangedCommand"}, requireAll = false)
    public static void onScrollChangeCommand(final ViewPager viewPager,
                                             final ViewCommandManager<ViewPagerDataWrapper> onPageScrolledCommand,
                                             final ViewCommandManager<Integer> onPageSelectedCommand,
                                             final ViewCommandManager<Integer> onPageScrollStateChangedCommand) {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            private int state;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (onPageScrolledCommand != null) {
                    onPageScrolledCommand.execute(new ViewPagerDataWrapper(position, positionOffset, positionOffsetPixels, state));
                }
            }

            @Override
            public void onPageSelected(int position) {
                if (onPageSelectedCommand != null) {
                    onPageSelectedCommand.execute(position);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                this.state = state;
                if (onPageScrollStateChangedCommand != null) {
                    onPageScrollStateChangedCommand.execute(state);
                }
            }
        });

    }

    /**
     * ViewPager
     * @param viewPager
     * @param itemBinding
     * @param items
     * @param adapter
     * @param pageTitles
     * @param <T>
     */
    @SuppressWarnings("unchecked")
    @BindingAdapter(value = {"itemBinding", "items", "adapter", "pageTitles"}, requireAll = false)
    public static <T> void setAdapter(ViewPager viewPager, ItemBinding<T> itemBinding, List items, BindingViewPagerAdapter<T> adapter, BindingViewPagerAdapter.PageTitles<T> pageTitles) {
        if (itemBinding == null) {
            throw new IllegalArgumentException("onItemBind must not be null");
        }
        BindingViewPagerAdapter<T> oldAdapter = (BindingViewPagerAdapter<T>) viewPager.getAdapter();
        if (adapter == null) {
            if (oldAdapter == null) {
                adapter = new BindingViewPagerAdapter<>();
            } else {
                adapter = oldAdapter;
            }
        }
        adapter.setItemBinding(itemBinding);
        adapter.setItems(items);
        adapter.setPageTitles(pageTitles);

        if (oldAdapter != adapter) {
            viewPager.setAdapter(adapter);
        }
    }

    /**
     * ViewPager的FragmentAdapter
     * @param viewPager
     * @param items
     */
    @SuppressWarnings("unchecked")
    @BindingAdapter(value = {"fragments",
                             "titles"}, requireAll = false)
    public static void setFragmentAdapter(ViewPager viewPager,
                                          List<BaseFragment> items,
                                          List<String> titles) {
        ViewPagerFragmentAdapter adapter = (ViewPagerFragmentAdapter) viewPager.getAdapter();
        if (adapter == null) {
            adapter = new ViewPagerFragmentAdapter(ActivityManager.getActivityManagerInstance()
                    .getTopActivity()
                    .getSupportFragmentManager());
        }
        viewPager.setAdapter(adapter);
        adapter.setFragments(items);
        if (titles != null && !titles.isEmpty()){
            adapter.setTites(titles);
        }
    }

    /**
     * ViewPager的FragmentStateAdapter
     * @param viewPager
     * @param items
     */
    @SuppressWarnings("unchecked")
    @BindingAdapter(value = {"stateFragments",
                             "stateTitles"}, requireAll = false)
    public static void setStateFragmentAdapter(ViewPager viewPager,
                                               List<BaseFragment> items,
                                               List<String> titles) {
        ViewPagerFragmentStateAdapter adapter = (ViewPagerFragmentStateAdapter) viewPager.getAdapter();
        if (adapter == null) {
            adapter = new ViewPagerFragmentStateAdapter(ActivityManager.getActivityFromView(viewPager)
                    .getSupportFragmentManager());
        }
        viewPager.setAdapter(adapter);
        adapter.setFragments(items);
        if (titles != null && !titles.isEmpty()){
            adapter.setTites(titles);
        }
    }

    public static class ViewPagerDataWrapper {
        public float positionOffset;
        public float position;
        public int positionOffsetPixels;
        public int state;

        public ViewPagerDataWrapper(float position, float positionOffset, int positionOffsetPixels, int state) {
            this.positionOffset = positionOffset;
            this.position = position;
            this.positionOffsetPixels = positionOffsetPixels;
            this.state = state;
        }
    }
}
