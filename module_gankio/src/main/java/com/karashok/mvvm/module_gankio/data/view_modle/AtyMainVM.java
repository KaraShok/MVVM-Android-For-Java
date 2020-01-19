package com.karashok.mvvm.module_gankio.data.view_modle;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableBoolean;

import com.karashok.library.common.base.BaseViewModel;
import com.karashok.library.common.base.DataManager;

/**
 * @author KaraShok(张耀中)
 * DESCRIPTION
 * @name AtyMainVM
 * @date 2019/03/24 19:03
 **/
public class AtyMainVM extends BaseViewModel<Object, DataManager> {

    public AtyMainVM(@NonNull Application application) {
        super(application);
    }

    @Override
    protected DataManager initDataManager() {
        return null;
    }

    public final ObservableBoolean classificationChecked = new ObservableBoolean(true);

    public final ObservableBoolean recommendationChecked = new ObservableBoolean(false);

    public final ObservableBoolean meChecked = new ObservableBoolean(false);

    public final void setRadioButtonCheckedStates(int index){
        switch (index){
            case 0:
                classificationChecked.set(true);
                recommendationChecked.set(false);
                meChecked.set(false);
                break;
            case 1:
                classificationChecked.set(false);
                recommendationChecked.set(true);
                meChecked.set(false);
                break;
            case 2:
                classificationChecked.set(false);
                recommendationChecked.set(false);
                meChecked.set(true);
                break;
            default:
                break;
        }
    }

}
