package com.karashok.mvvm.module_gankio.data.view_modle;


import android.widget.Toast;

import androidx.databinding.ObservableField;

import com.karashok.library.common.databinding.binding_event.Fun1;
import com.karashok.library.common.databinding.binding_event.ViewCommandManager;
import com.karashok.library.module_util.utilcode.utils.Utils;
import com.karashok.mvvm.module_gankio.data.entity.DataEntity;

/**
 * @author KaraShokZ (张耀中)
 * DESCRIPTION
 * @name ItemFgtXiuxiVM
 * @date 2019/03/26 16:44
 **/
public class ItemFgtXiuxiVM {

    private DataEntity mItem;

    public ItemFgtXiuxiVM(DataEntity item) {
        mItem = item;
        title.set(item.getDesc());
        // 无图片
    }

    public final ObservableField<String> title = new ObservableField<>();

    public final ObservableField<String> image = new ObservableField<>();

    public final ViewCommandManager<Integer> itemClick = new ViewCommandManager<>(new Fun1<Integer>() {
        @Override
        public void action(Integer item) {
            Toast.makeText(Utils.getApp(),mItem.getDesc(),Toast.LENGTH_SHORT).show();
        }
    });
}
