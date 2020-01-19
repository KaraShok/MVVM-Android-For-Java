package com.karashok.library.module_photos_selector.ui.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.PagerAdapter;

import com.karashok.library.module_photos_selector.data.PictureEntity;
import com.karashok.library.module_photos_selector.ui.widget.ThingCallBack;
import com.karashok.library.module_preview_pictures.ui.widget.photoview.PhotoView;
import com.karashok.library.module_preview_pictures.util.PhotoViewUtils;

import java.util.List;

/**
 * @author KaraShokZ(zhangyaozhong)
 * @name PictureSelecrtPagerAdapter
 * DESCRIPTION 图片预览页面的ViewPager的Adapter
 * @date 2018/05/22/上午11:10
 */
public class PictureSelecrtPagerAdapter extends PagerAdapter {

    private List<PictureEntity> mPictures; // 图片数据源
    private ThingCallBack mCallBack; // Item点击事件回调

    public PictureSelecrtPagerAdapter(List<PictureEntity> pictures) {
        this.mPictures = pictures;
    }

    /**
     * 设置回调
     * @param callBack
     */
    public void setCallBack(ThingCallBack callBack) {
        this.mCallBack = callBack;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        PhotoView photoView = new PhotoView(container.getContext());
        PictureEntity picture = mPictures.get(position);
//        ModuleLocalPhotoUtils.loadImage(container.getContext(), photoView, picture.getPath());
        PhotoViewUtils.loadImage(container.getContext(), photoView, picture.getPath());
        photoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCallBack != null){
                    mCallBack.thingCallBack(position);
                }
            }
        });
        container.addView(photoView);
        return photoView;
    }

    @Override
    public int getCount() {
        return mPictures != null ? mPictures.size() : 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
