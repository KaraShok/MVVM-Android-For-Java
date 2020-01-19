package com.karashok.library.module_photos_selector.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.karashok.library.module_permission.ui.fragment.PermissionFragment;
import com.karashok.library.module_photos_selector.ModuleLocalPhotoSelectorConstant;
import com.karashok.library.module_photos_selector.R;
import com.karashok.library.module_photos_selector.data.FolderEntity;
import com.karashok.library.module_photos_selector.data.PictureEntity;
import com.karashok.library.module_photos_selector.ui.adapter.PictureSelectorRecyclerAdapter;
import com.karashok.library.module_photos_selector.ui.widget.ThingCallBack;
import com.karashok.library.module_photos_selector.util.DataTransAsyncTask;
import com.karashok.library.module_photos_selector.util.LoadLocalPictureAsyncTask;
import com.karashok.library.module_photos_selector.util.PhotoSelectIntentUtils;
import com.karashok.library.module_util.utilcode.utils.base_util.file_util.FileUtils;
import com.karashok.library.module_util.utilcode.utils.base_util.file_util.UriUtils;
import com.karashok.library.module_util.utilcode.utils.base_util.picture_util.BitmapUtils;
import com.karashok.library.module_util.utilcode.utils.base_util.picture_util.PictureUtils;
import com.karashok.library.module_util.utilcode.utils.view_util.ProgressDialogUtils;
import com.karashok.library.module_util.utilcode.utils.view_util.StatusBarUtils;

import java.io.File;
import java.util.ArrayList;

import pub.devrel.easypermissions.EasyPermissions;

/**
 * @author KaraShokZ(zhangyaozhong)
 * @name PictureSelectorActivity
 * DESCRIPTION 展示下，图片选择页面
 * @date 2018/05/18/下午4:15
 */
public class PictureSelectorActivity extends AppCompatActivity {

    private TextView mAlbum;
    private TextView mTitle;
    private TextView mCancel;
    private TextView mReview;
    private TextView mFinish;
    private RecyclerView mRecycler;

    private GridLayoutManager mLayoutManager;
    private PictureSelectorRecyclerAdapter mAdapter;
    private boolean mIsSingle;
    private String picPath;
    private FolderEntity mFolder;
    private int mMaxCount = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 强制竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.aty_picture_selector);
        // 设置状态栏颜色
        StatusBarUtils.setStatusBarColor(this, "#373c3d");
        //申请权限  request app rights,只需要判断，如果没有会调用封装的方法自动申请
        if (PermissionFragment.haveAll(this, getSupportFragmentManager()));

        initIntentData();
        initView();
        loadImageForSDCard();
        initRecycler();
    }

    /**
     * 初始化Intent传过来的值
     */
    private void initIntentData() {
        Intent intent = getIntent();
        mMaxCount = intent.getIntExtra(PhotoSelectIntentUtils.MAX_SELECT_COUNT, -1);
        mIsSingle = intent.getBooleanExtra(PhotoSelectIntentUtils.IS_SINGLE, false);

        ModuleLocalPhotoSelectorConstant.PICTURES.clear();
        ModuleLocalPhotoSelectorConstant.SELECT_PICTURE.clear();
        ModuleLocalPhotoSelectorConstant.FOLDERS.clear();
    }

    /**
     * 初始化View和点击事件
     */
    private void initView() {
        mAlbum = findViewById(R.id.aty_picture_selector_title_album_tv);
        mCancel = findViewById(R.id.aty_picture_selector_title_cancel_tv);
        mReview = findViewById(R.id.aty_picture_selector_bottom_review_tv);
        mFinish = findViewById(R.id.aty_picture_selector_bottom_finish_tv);
        mRecycler = findViewById(R.id.aty_picture_selector_picture_rv);
        mTitle = findViewById(R.id.aty_picture_selector_title_title_tv);

        mAlbum.setOnClickListener(mViewClick);
        mCancel.setOnClickListener(mViewClick);
        mReview.setOnClickListener(mViewClick);
        mFinish.setOnClickListener(mViewClick);
    }

    /**
     * 初始化RecyclerView
     */
    private void initRecycler() {
        initLayoutManager();
        initAdapter();
        mRecycler.setLayoutManager(mLayoutManager);
        mRecycler.setAdapter(mAdapter);

        ((SimpleItemAnimator) mRecycler.getItemAnimator()).setSupportsChangeAnimations(false);

        if (ModuleLocalPhotoSelectorConstant.FOLDERS != null && !ModuleLocalPhotoSelectorConstant.FOLDERS.isEmpty()) {
            setFolder(ModuleLocalPhotoSelectorConstant.FOLDERS.get(0), 0);
        }
    }

    /**
     * 初始化LayoutManager
     */
    private void initLayoutManager() {
        // 判断屏幕方向
        Configuration configuration = getResources().getConfiguration();
        if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            //切换为竖屏
            mLayoutManager = new GridLayoutManager(this, ModuleLocalPhotoSelectorConstant.PORTRAITY_SPAN_COUNT);
        } else {
            //切换为横屏
            mLayoutManager = new GridLayoutManager(this, ModuleLocalPhotoSelectorConstant.LANDSCAPE_SPAN_COUNT);
        }
    }

    /**
     * 初识话Adapter
     */
    private void initAdapter() {
        mAdapter = new PictureSelectorRecyclerAdapter(this, mMaxCount, mIsSingle);
        mAdapter.setCallBack(new PictureSelectorRecyclerAdapter.OnEventCallBack() {
            @Override
            public void onPictureSelect(int selectCount) {
                setSelectImageCount(selectCount);
            }

            @Override
            public void onItemClick(int position) {
                toPreview(true, position);
            }

            @Override
            public void doCapturePicture() {
                if (PermissionFragment.haveCameraPerm(PictureSelectorActivity.this)) {
                    //使用照相机,路径为私有的，不会在系统相册中显示。路径每次都取时间戳，避免复用
                    picPath = FileUtils.getPrivateDirPath("take_photo") + File.separator + System.currentTimeMillis() + ".jpg";

                    Uri pathUri = UriUtils.getPathUri(picPath);

                    PictureUtils.takePicture(PictureSelectorActivity.this, pathUri, PhotoSelectIntentUtils.CODE_REQUEST_TAKE_PHOTO);
                } else {
                    PermissionFragment.requestCameraPerm(PictureSelectorActivity.this);
                }
            }
        });
    }

    /**
     * 选择照片结果处理
     *
     * @param intentType 1、选择完照片；2、取消选择；3、单选-拍照
     * @param os
     */
    private void cancelOrFinish(int intentType, ArrayList<String> os, String newPath) {
        Intent intent = new Intent();
        int resultCode = 2048;
        switch (intentType) {
            case 1:
                intent.putStringArrayListExtra(PhotoSelectIntentUtils.RESULT_SELECT_PICTURE_FINISH, os);
                resultCode = PhotoSelectIntentUtils.CODE_RESULT_SELECT_PICTURE_FINISH;
                break;
            case 2:
                intent.putExtra(PhotoSelectIntentUtils.RESULT_SELECT_PICTURE_CANCEL, PhotoSelectIntentUtils.CODE_RESULT_SELECT_PICTURE_CANCEL);
                resultCode = PhotoSelectIntentUtils.CODE_RESULT_SELECT_PICTURE_CANCEL;
                break;
            case 3:
                intent.putExtra(PhotoSelectIntentUtils.RESULT_CAPTURE_PICTURE, newPath);
                resultCode = PhotoSelectIntentUtils.CODE_RESULT_SELECT_PICTURE_CAPTURE;
                break;
            default:
                break;
        }
        setResult(resultCode, intent);
        finish();
    }


    /**
     * 点击事件
     */
    private View.OnClickListener mViewClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int vId = v.getId();
            if (R.id.aty_picture_selector_title_album_tv == vId) {
                // 相册
                PhotoSelectIntentUtils.intentToFolderListAty(PictureSelectorActivity.this);
            } else if (R.id.aty_picture_selector_title_cancel_tv == vId) {
                // 取消
                cancelOrFinish(2, null, null);
            } else if (R.id.aty_picture_selector_bottom_review_tv == vId) {
                // 预览
                toPreview(false,0);
            } else if (R.id.aty_picture_selector_bottom_finish_tv == vId) {
                // 完成
                finishSelect();
            }
        }
    };

    private void toPreview(boolean isAllPicture, int position){
        PhotoSelectIntentUtils.intentToPreviewPictureSelecterAty(PictureSelectorActivity.this,
                isAllPicture,
                mIsSingle,
                mMaxCount,
                position);
    }

    /**
     * 设置预览、完成按钮的显示
     *
     * @param count
     */
    private void setSelectImageCount(int count) {
        boolean enabled = false;
        String finishStr = "完成";
        String reviewStr = "预览";
        if (count != 0) {
            enabled = true;
            reviewStr = "预览(" + count + ")";
            if (mIsSingle) {
                finishStr = "完成";
            } else if (mMaxCount > 0) {
                finishStr = "完成(" + count + "/" + mMaxCount + ")";
            } else {
                finishStr = "完成(" + count + ")";
            }
        }
        mFinish.setEnabled(enabled);
        mReview.setEnabled(enabled);
        mFinish.setText(finishStr);
        mReview.setText(reviewStr);
    }

    /**
     * 用户完成照片选择，返回数据
     */
    private void finishSelect() {
        if (mAdapter == null) {
            return;
        }
        //因为图片的实体类是Image，而我们返回的是String数组，所以要进行转换。
        ProgressDialogUtils.getInstance().showDialog(this);
        new DataTransAsyncTask(new ThingCallBack<ArrayList<String>>() {
            @Override
            public void thingCallBack(ArrayList<String> os) {
                ProgressDialogUtils.dismissDialog();
                //点击确定，把选中的图片通过Intent传给上一个Activity。
                cancelOrFinish(1, os, null);

            }
        }).execute(ModuleLocalPhotoSelectorConstant.SELECT_PICTURE);

    }

    /**
     * 处理图片预览页返回的结果及拍照页返回的结果
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PhotoSelectIntentUtils.CODE_REQUSET_PREVIEW_PICTURE && resultCode == PhotoSelectIntentUtils.CODE_RESULT_PREVIEW_PICTURE) {

            if (data != null && data.getBooleanExtra(PhotoSelectIntentUtils.IS_CONFIRM, false)) {
                //如果用户在预览页点击了确定，就直接把用户选中的图片返回给用户。
                finishSelect();
            } else {
                //否则，就刷新当前页面。
                mAdapter.notifyDataSetChanged();
                setSelectImageCount(ModuleLocalPhotoSelectorConstant.SELECT_PICTURE.size());
            }
            //打开文件夹列表的activity，关闭后进行的读取回传数据操作，回传的是选择的文件夹在列表中的位置。
        } else if (requestCode == PhotoSelectIntentUtils.CODE_REQUSET_FOLDER_LIST && resultCode == PhotoSelectIntentUtils.CODE_RESULT_FOLDER_LIST) {

            int folderPosition = data.getIntExtra(PhotoSelectIntentUtils.RESULT_FOLDER_POSITION, 0);
            setFolder(ModuleLocalPhotoSelectorConstant.FOLDERS.get(folderPosition), folderPosition);

        } else if (requestCode == PhotoSelectIntentUtils.CODE_REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {

            //因为拍照以后的具体操作需要具体使用场景去决定，因此这里我们将拍照得到的数据直接回传给启动照片选择器的 Activity
            //因为我们指定了拍照返回的 uri 路径，因此 data 是空的，只有当我们不指定路径，使用系统默认路径时 data 才会不为空（8.0不为空）
//            if (data == null) {
                //存入系统相册中 iContract 文件夹的路径
                String galleryPath = FileUtils.getNoteGalleryPath("iContract");
                String newPath = BitmapUtils.makeBitmapToCorrectDegree(picPath, galleryPath);

                PictureUtils.refreshSystemAlbum(PictureSelectorActivity.this, newPath);

                if (mIsSingle) {
                    cancelOrFinish(3, null, newPath);
                } else {
                    loadImageBeforeCamera();
                }
//            }
        }
    }

    /**
     * 从SDCard加载图片。
     */
    private void loadImageForSDCard() {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return;
        }
        new LoadLocalPictureAsyncTask(PictureSelectorActivity.this, new ThingCallBack<ArrayList<FolderEntity>>() {
            @Override
            public void thingCallBack(ArrayList<FolderEntity> folders) {
                if (ModuleLocalPhotoSelectorConstant.FOLDERS != null) {
                    ModuleLocalPhotoSelectorConstant.FOLDERS.clear();
                    ModuleLocalPhotoSelectorConstant.FOLDERS.addAll(folders);
                    if (!ModuleLocalPhotoSelectorConstant.FOLDERS.isEmpty()) {
                        ModuleLocalPhotoSelectorConstant.FOLDERS.get(0).setSelected(true);
                        setFolder(ModuleLocalPhotoSelectorConstant.FOLDERS.get(0), 0);
                    }
                }
            }
        }).execute();
    }

    /**
     * 拍照后重新查询
     */
    private void loadImageBeforeCamera() {
        //设置拍照的相片默认选中
        new LoadLocalPictureAsyncTask(PictureSelectorActivity.this, new ThingCallBack<ArrayList<FolderEntity>>() {

            @Override
            public void thingCallBack(ArrayList<FolderEntity> folderEntities) {
                ArrayList<PictureEntity> images = folderEntities.get(0).getImages();
                PictureEntity entity = images.get(images.size() - 1);
                entity.setSelected(true);
                mFolder.addImage(entity);
                ModuleLocalPhotoSelectorConstant.SELECT_PICTURE.add(entity);
                setSelectImageCount(ModuleLocalPhotoSelectorConstant.SELECT_PICTURE.size());
                setFolder(mFolder, 0);
            }
        }).execute();
    }

    /**
     * 设置选中的文件夹，同时刷新图片列表
     *
     * @param folder
     */
    private void setFolder(FolderEntity folder, int folderIndex) {
        if (folder != null && mAdapter != null) {
            mFolder = folder;
            mTitle.setText(folder.getName());
            ModuleLocalPhotoSelectorConstant.PICTURES.clear();
            ModuleLocalPhotoSelectorConstant.PICTURES.addAll(folder.getImages());
            mAdapter.folderSelect(folderIndex);
            int folderSize = mFolder.getImages().size();
            int toPosition = folderSize - 1;
            if (folderIndex == 0) {
                toPosition = folderSize;
            }
            mLayoutManager.scrollToPosition(toPosition);
        }
    }

    /**
     * 横竖屏切换处理
     *
     * @param newConfig
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (mLayoutManager != null && mAdapter != null) {
            int spanCount = 4;
            if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
                //切换为竖屏 - 4个
                spanCount = ModuleLocalPhotoSelectorConstant.PORTRAITY_SPAN_COUNT;
            } else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                //切换为横屏 - 6个
                spanCount = ModuleLocalPhotoSelectorConstant.LANDSCAPE_SPAN_COUNT;
            }
            mLayoutManager.setSpanCount(spanCount);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults);

        for (String permission : permissions) {
            if (permission.equalsIgnoreCase(Manifest.permission.CAMERA)) {
                String toastStr = "当前缺少访问照相机的权限，请在手机应用权限管理中开启相应的权限";
                PermissionFragment.showRequestPermTextAgain(permissions, grantResults, PictureSelectorActivity.this, toastStr);
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            cancelOrFinish(2, null, null);
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        ProgressDialogUtils.dismissDialog();
        super.onDestroy();
    }
}
