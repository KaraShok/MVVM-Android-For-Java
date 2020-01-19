package com.karashok.library.module_image_load.image.glide;//package com.icourt.karashok.module_image_load.image.glide;
//
//import android.graphics.Bitmap;
//import android.support.annotation.IntRange;
//import android.support.annotation.NonNull;
//
//import com.bumptech.glide.load.Key;
//import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
//import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
//import com.icourt.karashok.module_image_load.image.imageframework.BitmapUtils;
//
//import java.security.MessageDigest;
//
///**
// * @author Ralf(wanglixin)
// * DESCRIPTION glide图片加载缓存策略
// * @name BlurTransformation
// * @date 2018/05/19 下午2:43
// **/
//public class BlurTransformation extends BitmapTransformation {
//
//    private static final String ID = BlurTransformation.class.getName();
//    private static final byte[] ID_BYTES = ID.getBytes(Key.CHARSET);
//
//    private int defaultRadius=15;
//    public BlurTransformation(@IntRange(from=0) int radius){
//        defaultRadius=radius;
//    }
//    @Override
//    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
//        messageDigest.update(ID_BYTES);
//
//    }
//
//    @Override
//    protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
//        return  BitmapUtils.fastBlur(toTransform,defaultRadius);
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        return o instanceof BlurTransformation;
//    }
//
//    @Override
//    public int hashCode() {
//        return ID.hashCode();
//    }
//}
