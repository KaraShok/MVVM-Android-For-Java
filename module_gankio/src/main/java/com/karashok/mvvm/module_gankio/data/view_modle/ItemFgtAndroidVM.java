package com.karashok.mvvm.module_gankio.data.view_modle;

import android.view.View;
import android.widget.Toast;

import androidx.databinding.ObservableField;

import com.karashok.library.common.databinding.binding_event.Fun1;
import com.karashok.library.common.databinding.binding_event.ViewCommandManager;
import com.karashok.library.module_util.utilcode.utils.Utils;
import com.karashok.library.module_util.utilcode.utils.base_util.TimeUtils;
import com.karashok.mvvm.module_gankio.data.entity.DataEntity;

import static com.karashok.library.module_util.utilcode.utils.base_util.TimeUtils.DEFAULT_FORMAT_ZONE;


/**
 * @author KaraShokZ (张耀中)
 * DESCRIPTION
 * @name ItemFgtAndroidVM
 * @date 2019/03/26 15:14
 **/
public class ItemFgtAndroidVM{

    private DataEntity mItem;

    public ItemFgtAndroidVM(DataEntity item) {
        mItem = item;
        title.set(item.getDesc());
        time.set(TimeUtils.date2String("yyyy-MM-dd",TimeUtils.string2Date(item.getPublishedAt(),DEFAULT_FORMAT_ZONE).getTime()));
        if (item.getImages() != null && !item.getImages().isEmpty()){
            image.set(item.getImages().get(0));
            imageVisibility.set(View.VISIBLE);
        }else {
            imageVisibility.set(View.GONE);
        }
    }

    public final ObservableField<String> title = new ObservableField<>();

    public final ObservableField<String> image = new ObservableField<>();

    public final ObservableField<String> time = new ObservableField<>();

    public final ObservableField<Integer> imageVisibility = new ObservableField<>(View.VISIBLE);

    public final ViewCommandManager<Integer> itemClick = new ViewCommandManager<>(new Fun1<Integer>() {
        @Override
        public void action(Integer item) {
            Toast.makeText(Utils.getApp(),mItem.getDesc(),Toast.LENGTH_SHORT).show();
        }
    });
}
