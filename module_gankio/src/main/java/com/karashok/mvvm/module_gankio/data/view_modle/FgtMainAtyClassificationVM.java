package com.karashok.mvvm.module_gankio.data.view_modle;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;

import com.karashok.library.common.base.BaseFragment;
import com.karashok.library.common.base.BaseViewModel;
import com.karashok.library.common.base.DataManager;
import com.karashok.mvvm.module_gankio.ui.fragment.AndroidFragment;
import com.karashok.mvvm.module_gankio.ui.fragment.FuLiFragment;
import com.karashok.mvvm.module_gankio.ui.fragment.IOSFragment;
import com.karashok.mvvm.module_gankio.ui.fragment.QianDuanFragment;
import com.karashok.mvvm.module_gankio.ui.fragment.TuoZhanFragment;
import com.karashok.mvvm.module_gankio.ui.fragment.XiuXiFragment;

/**
 * @author KaraShokZ (张耀中)
 * DESCRIPTION
 * @name FgtMainAtyClassificationVM
 * @date 2019/03/27 19:58
 **/
public class FgtMainAtyClassificationVM extends BaseViewModel<Object, DataManager> {

    public FgtMainAtyClassificationVM(@NonNull Application application) {
        super(application);
    }

    @Override
    protected DataManager initDataManager() {
        return null;
    }

    public final ObservableList<BaseFragment> fragmentList = new ObservableArrayList<BaseFragment>(){
        {
            add(new AndroidFragment());
            add(new IOSFragment());
            add(new QianDuanFragment());
            add(new TuoZhanFragment());
            add(new XiuXiFragment());
            add(new FuLiFragment());
        }
    };

    public final ObservableList<String> pagerTitle = new ObservableArrayList<String>(){
        {
            add("Android");
            add("iOS");
            add("前端");
            add("拓展资源");
            add("休息视频");
            add("福利");
        }
    };
}
