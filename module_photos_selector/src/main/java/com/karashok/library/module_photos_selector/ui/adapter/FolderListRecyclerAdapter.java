package com.karashok.library.module_photos_selector.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.karashok.library.module_photos_selector.ModuleLocalPhotoSelectorConstant;
import com.karashok.library.module_photos_selector.R;
import com.karashok.library.module_photos_selector.data.FolderEntity;
import com.karashok.library.module_photos_selector.data.PictureEntity;
import com.karashok.library.module_photos_selector.ui.widget.ThingCallBack;
import com.karashok.library.module_preview_pictures.util.PhotoViewUtils;

import java.util.ArrayList;

/**
 * @author KaraShokZ(zhangyaozhong)
 * @name FolderListRecyclerAdapter
 * DESCRIPTION 相册列表页面RecyclerView的Adapter
 * @date 2018/05/21/下午3:46
 */
public class FolderListRecyclerAdapter extends RecyclerView.Adapter<FolderListRecyclerAdapter.FolderHolder> {

    private LayoutInflater mInflater; // 布局填充器
    private ThingCallBack mCallBack; // Item点击回调

    public FolderListRecyclerAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    /**
     * 设置回调
     * @param callBack
     */
    public void setCallBack(ThingCallBack callBack) {
        this.mCallBack = callBack;
    }

    @Override
    public FolderHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = mInflater.inflate(R.layout.view_item_folder_list_adapter, parent, false);
        return new FolderHolder(inflate);
    }

    @Override
    public void onBindViewHolder(FolderHolder holder, int position) {
        FolderEntity entity = ModuleLocalPhotoSelectorConstant.FOLDERS.get(position);

        initViewData(holder, entity);
        initItemClick(holder, position);
    }

    /**
     * 初始化Item上的View的数据
     * @param holder
     * @param entity
     */
    private void initViewData(FolderHolder holder, FolderEntity entity){
        ArrayList<PictureEntity> images = entity.getImages();

        holder.mNameTV.setText(entity.getName());

        int resId = R.drawable.icon_image_unselect;
        if (entity.isSelected()){
            resId = R.drawable.icon_image_select;
        }
        holder.mSelectIV.setImageResource(resId);

        String sizeStr = "0";
        if (images != null && !images.isEmpty()){
            sizeStr = String.valueOf(entity.getImages().size());
            PhotoViewUtils.loadImage(holder.mItemView.getContext(), holder.mImageIV, images.get(images.size() - 1).getPath());
//            ModuleLocalPhotoUtils.loadImage(holder.mItemView.getContext(), holder.mImageIV, images.get(images.size() - 1).getPath());
        }
        holder.mSizeTV.setText(sizeStr + "张");
    }

    /**
     * 为Item设置点击事件
     * @param holder
     * @param position
     */
    private void initItemClick(FolderHolder holder, final int position){
        holder.mItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCallBack != null){
                    for (FolderEntity entity : ModuleLocalPhotoSelectorConstant.FOLDERS){
                        entity.setSelected(false);
                    }
                    ModuleLocalPhotoSelectorConstant.FOLDERS.get(position).setSelected(true);
                    notifyDataSetChanged();
                    mCallBack.thingCallBack(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return ModuleLocalPhotoSelectorConstant.FOLDERS != null ? ModuleLocalPhotoSelectorConstant.FOLDERS.size() : 0;
    }

    static class FolderHolder extends RecyclerView.ViewHolder{

        public ImageView mImageIV;
        public TextView mNameTV;
        public TextView mSizeTV;
        public ImageView mSelectIV;
        public View mItemView;

        public FolderHolder(View itemView) {
            super(itemView);
            mItemView = itemView;
            mImageIV = itemView.findViewById(R.id.view_item_folder_list_adapter_image_iv);
            mNameTV = itemView.findViewById(R.id.view_item_folder_list_adapter_folder_name_tv);
            mSizeTV = itemView.findViewById(R.id.view_item_folder_list_adapter_folder_size_tv);
            mSelectIV = itemView.findViewById(R.id.view_item_folder_list_adapter_select_iv);
        }
    }
}
