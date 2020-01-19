package com.karashok.library.module_preview_pictures.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;


import com.karashok.library.module_permission.ModulePermissionContants;
import com.karashok.library.module_permission.ui.fragment.PermissionFragment;
import com.karashok.library.module_preview_pictures.ModulePicturePreviewContants;
import com.karashok.library.module_preview_pictures.R;
import com.karashok.library.module_preview_pictures.data.FileEntity;
import com.karashok.library.module_preview_pictures.ui.adapter.PicturePagerAdapter;
import com.karashok.library.module_preview_pictures.ui.widget.PhotoViewPager;
import com.karashok.library.module_preview_pictures.ui.widget.photoview.OnPhotoTapListener;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

/**
 * @author KaraShokZ(zhangyaozhong)
 * @name PicturesPreviewActivity
 * DESCRIPTION 图片预览
 * @date 2018/05/18/下午2:52
 */
public class PicturesPreviewActivity extends AppCompatActivity {

    private ImageView mTitleBackIv;
    private TextView mTitleTv;
    private RelativeLayout mTitleRl;
    private PhotoViewPager mTitlePvp;

    private List<FileEntity> mFileItems;
    private int mCurIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pictures_preview);
        initData();
        initView();
        initPictureViewPager();
    }

    private void initData(){
        Intent intent = getIntent();
        mFileItems = (List<FileEntity>) intent.getSerializableExtra(ModulePicturePreviewContants.FILE_ITEMS);
        mCurIndex = intent.getIntExtra(ModulePicturePreviewContants.CURRENT_INDEX,1);
    }
    private void initView(){
        mTitleBackIv = findViewById(R.id.act_picture_preview_title_back_iv);
        mTitleTv = findViewById(R.id.act_picture_preview_title_title_iv);
        mTitleRl = findViewById(R.id.act_picture_preview_title_rl);
        mTitlePvp = findViewById(R.id.act_picture_preview_pvp);

        mTitleBackIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PicturesPreviewActivity.this.finish();
            }
        });

        mTitleTv.setText(String.format("%s/%s", mCurIndex + 1, mFileItems.size()));
    }

    private void initPictureViewPager(){
        PicturePagerAdapter adapter = new PicturePagerAdapter(mFileItems, new OnPhotoTapListener() {
            @Override
            public void onPhotoTap(ImageView view, float x, float y) {
                if (mTitleRl.getVisibility() == View.GONE) {
                    mTitleRl.setVisibility(View.VISIBLE);
                } else {
                    mTitleRl.setVisibility(View.GONE);
                }
            }
        });

        mTitlePvp.setAdapter(adapter);
        mTitlePvp.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                mTitleTv.setText(String.format("%s/%s", position + 1, mFileItems.size()));
            }
        });

        mTitlePvp.setCurrentItem(mCurIndex);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults);

        for (String permisstion : permissions) {
            if (permisstion.equalsIgnoreCase(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                PermissionFragment.showRequestPermTextAgain(permissions, grantResults, PicturesPreviewActivity.this, ModulePermissionContants.EXPLAIN_SETTING_SD_CARD_PERM);
            }
        }
    }
}
