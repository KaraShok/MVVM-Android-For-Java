package com.karashok.library.module_photos_selector.util;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.karashok.library.module_photos_selector.data.FolderEntity;
import com.karashok.library.module_photos_selector.data.PictureEntity;
import com.karashok.library.module_util.utilcode.utils.base_util.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author KaraShokZ(zhangyaozhong)
 * @name PictureLoadUtils
 * DESCRIPTION 本地图片加载工具类
 * @date 2018/05/19/上午11:16
 */
public class PictureLoadUtils {

    /**
     * 从SDCard加载图片
     * 由于扫描图片是耗时的操作，所以要在子线程处理。
     *
     * @param context
     */
    public static ArrayList<FolderEntity> loadImageForSDCard(final Context context) {

        //扫描图片
        Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        ContentResolver mContentResolver = context.getContentResolver();

        Cursor mCursor = mContentResolver.query(mImageUri, new String[]{
                        MediaStore.Images.Media.DATA,
                        MediaStore.Images.Media.DISPLAY_NAME,
                        MediaStore.Images.Media.DATE_ADDED,
                        MediaStore.Images.Media._ID},
                null,
                null,
                MediaStore.Images.Media.DATE_ADDED);

        ArrayList<PictureEntity> images = new ArrayList<>();

        //读取扫描到的图片
        if (mCursor != null) {
            while (mCursor.moveToNext()) {
                // 获取图片的路径
                String path = mCursor.getString(
                        mCursor.getColumnIndex(MediaStore.Images.Media.DATA));
                //获取图片名称
                String name = mCursor.getString(
                        mCursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME));
                //获取图片时间
                long time = mCursor.getLong(
                        mCursor.getColumnIndex(MediaStore.Images.Media.DATE_ADDED));
                PictureEntity entity = new PictureEntity();
                entity.setName(name);
                entity.setPath(path);
                entity.setTime(time);
                images.add(entity);
            }
            mCursor.close();
        }
        //看你需要照片是顺序排列的还是倒叙排列的
        //Collections.reverse(images);
        return splitFolder(images);
    }

    /**
     * 把图片按文件夹拆分，第一个文件夹保存所有的图片
     *
     * @param images
     * @return
     */
    private static ArrayList<FolderEntity> splitFolder(ArrayList<PictureEntity> images) {
        ArrayList<FolderEntity> folders = new ArrayList<>();
        folders.add(new FolderEntity("全部图片", images));

        if (images != null && !images.isEmpty()) {
            int size = images.size();
            for (int i = 0; i < size; i++) {
                String path = images.get(i).getPath();
                String name = getFolderName(path);
                if (!StringUtils.isEmpty(name)) {
                    FolderEntity folder = getFolder(name, folders);
                    folder.addImage(images.get(i));
                }
            }
        }
        return folders;
    }

    /**
     * 跟着图片路径，获取图片文件夹名称
     *
     * @param path
     * @return
     */
    private static String getFolderName(String path) {
        if (!StringUtils.isEmpty(path)) {
            String[] strings = path.split(File.separator);
            if (strings.length >= 2) {
                return strings[strings.length - 2];
            }
        }
        return "";
    }

    private static FolderEntity getFolder(String name, List<FolderEntity> folders) {
        if (!folders.isEmpty()) {
            int size = folders.size();
            for (int i = 0; i < size; i++) {
                FolderEntity folder = folders.get(i);
                if (name.equals(folder.getName())) {
                    return folder;
                }
            }
        }
        FolderEntity newFolder = new FolderEntity(name);
        folders.add(newFolder);
        return newFolder;
    }
}
