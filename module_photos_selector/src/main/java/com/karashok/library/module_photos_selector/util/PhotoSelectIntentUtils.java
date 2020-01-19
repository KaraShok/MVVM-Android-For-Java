package com.karashok.library.module_photos_selector.util;

import android.app.Activity;
import android.content.Intent;

import com.karashok.library.module_photos_selector.ui.activity.FolderListActivity;
import com.karashok.library.module_photos_selector.ui.activity.PictureSelectorActivity;
import com.karashok.library.module_photos_selector.ui.activity.PreviewPictureSelecterActivity;


/**
 * @author KaraShok(zhangyaozhong)
 * @name PhotoSelectIntentUtils
 * DESCRIPTION 本地相册选择Intent跳转管理
 * @date 2018/05/21/下午3:30
 */
public class PhotoSelectIntentUtils {

    public static final int CODE_REQUSET_SELECT_PICTURE = 9528;
    public static final int CODE_RESULT_SELECT_PICTURE_CAPTURE = 9524;
    public static final int CODE_RESULT_SELECT_PICTURE_CANCEL = 9521;
    public static final int CODE_RESULT_SELECT_PICTURE_FINISH = 9527;

    public static int CODE_RESULT_PREVIEW_PICTURE = 85 << 2;
    public static int CODE_REQUSET_PREVIEW_PICTURE = 85 << 1;

    public static int CODE_REQUEST_TAKE_PHOTO = 87 << 1;

    public static int CODE_REQUSET_FOLDER_LIST = 86 << 1;
    public static int CODE_RESULT_FOLDER_LIST = 86 << 2;

    /**
     * 相册选择的结果
     */
    public static String RESULT_FOLDER_POSITION = "Result_Folder_Position";
    /**
     * 预览的是哪个数据源。true：所有图片；false：已选择的图片
     */
    public static String PREVIEW_PICTURES_WHICH = "Preview_Pictures_Which";
    /**
     * 预览的起始下标
     */
    public static String PREVIEW_PICTURES_POSITION = "Preview_Pictures_Position";
    /**
     * 图片选择的结果-完成
     */
    public static String RESULT_SELECT_PICTURE_FINISH = "Result_Select_Picture_Finish";
    /**
     * 图片选择的结果-取消
     */
    public static final String RESULT_SELECT_PICTURE_CANCEL = "Result_Select_Picture_Cancel";
    /**
     * 拍照的结果
     */
    public static final String RESULT_CAPTURE_PICTURE = "Result_Capture_Picture";
    /**
     * 图片选择组件 -- 最大的图片选择数
     */
    public static String MAX_SELECT_COUNT = "max_select_count";
    /**
     * 图片选择组件 -- 是否单选
     */
    public static String IS_SINGLE = "is_single";
    /**
     * 图片选择是否确认
     */
    public static String IS_CONFIRM = "is_confirm";

    /**
     * 相册列表页
     * @param activity
     */
    public static void intentToFolderListAty(Activity activity){
        Intent intent = new Intent(activity, FolderListActivity.class);
        activity.startActivityForResult(intent, CODE_REQUSET_FOLDER_LIST);
    }

    /**
     * 预览选择页面
     * @param activity
     * @param isAllPicture 是否是全照片
     * @param isSingle 是否是单选
     * @param maxSelectCount 最大选择数量
     * @param position 起始下标
     */
    public static void intentToPreviewPictureSelecterAty(Activity activity,
                                                         boolean isAllPicture,
                                                         boolean isSingle,
                                                         int maxSelectCount,
                                                         int position){

        Intent intent = new Intent(activity, PreviewPictureSelecterActivity.class);
        intent.putExtra(PREVIEW_PICTURES_WHICH,isAllPicture);
        intent.putExtra(PhotoSelectIntentUtils.MAX_SELECT_COUNT, maxSelectCount);
        intent.putExtra(PhotoSelectIntentUtils.IS_SINGLE, isSingle);
        intent.putExtra(PREVIEW_PICTURES_POSITION, position);
        activity.startActivityForResult(intent, PhotoSelectIntentUtils.CODE_REQUSET_PREVIEW_PICTURE);

    }

    /**
     * 启动图片选择器
     *
     * @param activity
     * @param isSingle       是否单选
     * @param maxSelectCount 图片的最大选择数量，小于等于0时，不限数量，isSingle为false时才有用。
     */
    public static void openSelecrPictureAty(Activity activity, boolean isSingle, int maxSelectCount) {
        Intent intent = new Intent(activity, PictureSelectorActivity.class);
        intent.putExtra(PhotoSelectIntentUtils.MAX_SELECT_COUNT, maxSelectCount);
        intent.putExtra(PhotoSelectIntentUtils.IS_SINGLE, isSingle);
        activity.startActivityForResult(intent, PhotoSelectIntentUtils.CODE_REQUSET_SELECT_PICTURE);
    }
}
