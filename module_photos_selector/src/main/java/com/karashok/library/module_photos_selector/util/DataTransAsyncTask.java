package com.karashok.library.module_photos_selector.util;

import android.os.AsyncTask;

import com.karashok.library.module_photos_selector.data.PictureEntity;
import com.karashok.library.module_photos_selector.ui.widget.ThingCallBack;

import java.util.ArrayList;
import java.util.List;

/**
 * @author KaraShokZ(zhangyaozhong)
 * @name DataTransAsyncTask
 * DESCRIPTION 将List<PictureEntity>转换为List<String>，将选择结果传递给上一个页面
 * @date 2018/05/19/下午2:26
 */
public class DataTransAsyncTask extends AsyncTask<List<PictureEntity>, Void, List<String>> {

    private ThingCallBack mCallBack;

    public DataTransAsyncTask(ThingCallBack callBack) {
        mCallBack = callBack;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected List<String> doInBackground(List<PictureEntity>[] lists) {
        List<PictureEntity> list = lists[0];
        List<String> pictures = new ArrayList<>();
        for (PictureEntity entity : list){
            pictures.add(entity.getPath());
        }
        return pictures;
    }

    @Override
    protected void onPostExecute(List<String> strings) {
        super.onPostExecute(strings);
        if (mCallBack != null){
            mCallBack.thingCallBack(strings);
        }
    }
}
