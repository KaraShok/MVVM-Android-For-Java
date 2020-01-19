package com.karashok.library.module_preview_pictures.ui.fragment;

import android.Manifest;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.karashok.library.module_permission.ModulePermissionContants;
import com.karashok.library.module_permission.ui.fragment.PermissionFragment;
import com.karashok.library.module_preview_pictures.ModulePicturePreviewContants;
import com.karashok.library.module_preview_pictures.R;
import com.karashok.library.module_preview_pictures.data.FileEntity;
import com.karashok.library.module_preview_pictures.ui.adapter.PicturePagerAdapter;
import com.karashok.library.module_preview_pictures.ui.widget.PhotoViewPager;
import com.karashok.library.module_preview_pictures.ui.widget.photoview.OnPhotoTapListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;


/**
 * @author KaraShokZ(zhangyaozhong)
 * @name PicturesPreviewFragment
 * DESCRIPTION 图片预览
 * @date 2018/05/18/下午2:52
 */
public class PicturesPreviewFragment extends Fragment {

    private ImageView mTitleBackIv;
    private TextView mTitleTv;
    private RelativeLayout mTitleRl;
    private PhotoViewPager mTitlePvp;
    private PhotoTapState mTapState;
    private List<FileEntity> mFileItems;
    private int mCurIndex;

    public static PicturesPreviewFragment getInstance(@NonNull ArrayList<String> imagesPath, int curIndex){
        PicturesPreviewFragment fragment = new PicturesPreviewFragment();
        Bundle bundle = new Bundle();
        ArrayList<FileEntity> fileItems = new ArrayList<>();
        for (int i = 0, size = imagesPath.size(); i < size; i++){
            String path = imagesPath.get(i);
            File file = new File(imagesPath.get(i));
            FileEntity fileEntity = new FileEntity();
            fileEntity.setName(file.getName());
            fileEntity.setPath(path);
            fileItems.add(fileEntity);
        }
        bundle.putSerializable(ModulePicturePreviewContants.FILE_ITEMS, fileItems);
        bundle.putInt(ModulePicturePreviewContants.CURRENT_INDEX, curIndex);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.activity_pictures_preview, container, false);
        return inflate;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        initView(view);
        initPictureViewPager();
    }

    private void initData(){
        Bundle arguments = getArguments();
        mFileItems = (List<FileEntity>) arguments.getSerializable(ModulePicturePreviewContants.FILE_ITEMS);
        mCurIndex = arguments.getInt(ModulePicturePreviewContants.CURRENT_INDEX,1);
    }

    private void initView(View view){
        mTitleBackIv = view.findViewById(R.id.act_picture_preview_title_back_iv);
        mTitleTv = view.findViewById(R.id.act_picture_preview_title_title_iv);
        mTitleRl = view.findViewById(R.id.act_picture_preview_title_rl);
        mTitlePvp = view.findViewById(R.id.act_picture_preview_pvp);

        mTitleBackIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
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
                    setTapCallBackState(true);
                } else {
                    mTitleRl.setVisibility(View.GONE);
                    setTapCallBackState(false);
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

    public void toNext(){
        int currentItem = getIndex();
        mTitlePvp.setCurrentItem(currentItem + 1);
    }

    public void toLast(){
        int currentItem = getIndex();
        mTitlePvp.setCurrentItem(currentItem - 1);
    }

    public int getIndex(){
        return mTitlePvp.getCurrentItem();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults);

        for (String permisstion : permissions) {
            if (permisstion.equalsIgnoreCase(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                PermissionFragment.showRequestPermTextAgain(permissions, grantResults, getActivity(), ModulePermissionContants.EXPLAIN_SETTING_SD_CARD_PERM);
            }
        }
    }

    public void setPhotoTap(PhotoTapState tapState){
        mTapState = tapState;
    }

    private void setTapCallBackState(boolean state){
        if (mTapState != null){
            mTapState.tapState(state);
        }
    }

    public interface PhotoTapState{
        void tapState(boolean state);
    }
}
