package com.karashok.library.module_photos_selector.util;

import android.content.Context;
import android.os.AsyncTask;

import com.karashok.library.module_photos_selector.data.FolderEntity;
import com.karashok.library.module_photos_selector.ui.widget.ThingCallBack;

import java.util.ArrayList;

/**
 * @author KaraShokZ(zhangyaozhong)
 * @name LoadLocalPictureAsyncTask
 * DESCRIPTION 加载本地照片
 * @date 2018/05/19/下午2:42
 */
public class LoadLocalPictureAsyncTask extends AsyncTask<Void, Void, ArrayList<FolderEntity>> {

    private Context mContext;
    private ThingCallBack mCallBack;

    public LoadLocalPictureAsyncTask(Context context, ThingCallBack callBack) {
        mContext = context;
        mCallBack = callBack;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected ArrayList<FolderEntity> doInBackground(Void... voids) {
        ArrayList<FolderEntity> folderEntities = PictureLoadUtils.loadImageForSDCard(mContext);
        return folderEntities;
    }

    @Override
    protected void onPostExecute(ArrayList<FolderEntity> folderEntities) {
        super.onPostExecute(folderEntities);
        if (mCallBack != null){
            mCallBack.thingCallBack(folderEntities);
        }
    }
}
