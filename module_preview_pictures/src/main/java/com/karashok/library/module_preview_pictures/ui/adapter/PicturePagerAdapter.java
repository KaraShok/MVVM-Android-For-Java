package com.karashok.library.module_preview_pictures.ui.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.PagerAdapter;

import com.karashok.library.module_preview_pictures.data.FileEntity;
import com.karashok.library.module_preview_pictures.ui.widget.photoview.OnPhotoTapListener;
import com.karashok.library.module_preview_pictures.ui.widget.photoview_fresco.PhotoViewForFresco;

import java.util.List;


/**
 * @author KaraShokZ(zhangyaozhong)
 * @name PicturePagerAdapter
 * DESCRIPTION 图片预览ViewPager的Adapter
 * @date 2018/05/18/下午2:52
 */
public class PicturePagerAdapter extends PagerAdapter {

    private String mPath = "https://ww1.sinaimg.cn/large/0065oQSqly1ftdtot8zd3j30ju0pt137.jpg";

    private List<FileEntity> mFileItems;
    private OnPhotoTapListener mOnPhotoTapListener;

    public PicturePagerAdapter(List<FileEntity> fileItems, OnPhotoTapListener onPhotoTapListener) {
        this.mFileItems = fileItems;
        this.mOnPhotoTapListener = onPhotoTapListener;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        PhotoViewForFresco photoViewForFresco = new PhotoViewForFresco(container.getContext());
        photoViewForFresco.setPhotoUri(false, mPath, -1);
        photoViewForFresco.setOnPhotoTapListener(mOnPhotoTapListener);
        // Now just add PhotoView to ViewPager and return it
        container.addView(photoViewForFresco, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        return photoViewForFresco;
    }

    @Override
    public int getCount() {
        return mFileItems != null ? mFileItems.size() : 0;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
