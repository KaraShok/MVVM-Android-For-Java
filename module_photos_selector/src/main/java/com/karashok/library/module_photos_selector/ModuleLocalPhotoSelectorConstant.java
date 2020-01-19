package com.karashok.library.module_photos_selector;

import com.karashok.library.module_photos_selector.data.FolderEntity;
import com.karashok.library.module_photos_selector.data.PictureEntity;

import java.util.ArrayList;

/**
 * @author KaraShokZ(zhangyaozhong)
 * @name ModuleLocalPhotoSelectorConstant
 * DESCRIPTION LocalPhotoSelector模块常量类
 * @date 2018/05/18/下午2:52
 */
public class ModuleLocalPhotoSelectorConstant {

    /**
     * 图片选择页面的列数（竖屏）
     */
    public static final int PORTRAITY_SPAN_COUNT = 4;
    /**
     * 图片选择页面的列数（横屏）
     */
    public static final int LANDSCAPE_SPAN_COUNT = 6;

    /**
     * 数据源-图片
     */
    public static ArrayList<PictureEntity> PICTURES = new ArrayList<>();
    /**
     * 数据源-选择的图片
     */
    public static ArrayList<PictureEntity> SELECT_PICTURE = new ArrayList<>();
    /**
     * 数据源-相册
     */
    public static ArrayList<FolderEntity> FOLDERS = new ArrayList<>();
}
