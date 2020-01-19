package com.karashok.library.module_photos_selector.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.karashok.library.module_photos_selector.ModuleLocalPhotoSelectorConstant;
import com.karashok.library.module_photos_selector.R;
import com.karashok.library.module_photos_selector.data.PictureEntity;
import com.karashok.library.module_preview_pictures.util.PhotoViewUtils;
import com.karashok.library.module_util.utilcode.utils.view_util.ToastUtils;


/**
 * @author KaraShokZ(zhangyaozhong)
 * @name PictureSelectorRecyclerAdapter
 * DESCRIPTION 展示下，图片选择页面RecyclerView的Adapter
 * @date 2018/05/18/下午4:10
 */
public class PictureSelectorRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private static final int TYPE_IMAGE = 1;
    private static final int TYPE_CAMERA = 2;

    private Context mContext;
    private LayoutInflater mInflater;
    /**
     * 根据文件夹的位置来决定有没有拍照按钮，只有文件夹是全部照片时才有拍照按钮
     */
    private int mFoldetIndex;
    private int mMaxCount;
    private boolean mIsSingle;
    private OnEventCallBack mCallBack;

    /**
     * @param maxCount 图片的最大选择数量，小于等于0时，不限数量，isSingle为false时才有用。
     * @param isSingle 是否单选
     */
    public PictureSelectorRecyclerAdapter(Context context, int maxCount, boolean isSingle) {
        mContext = context;
        mMaxCount = maxCount;
        mIsSingle = isSingle;
        mInflater = LayoutInflater.from(context);
    }

    public void setCallBack(OnEventCallBack callBack) {
        this.mCallBack = callBack;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        switch (viewType){
            case TYPE_IMAGE:
                View imageView = mInflater.inflate(R.layout.view_item_selecter_picture_adapter, parent, false);
                holder = new PictureViewHolder(imageView);
                break;
            case TYPE_CAMERA:
                View cameraView = mInflater.inflate(R.layout.view_camera_selecter_picture_adapter, parent, false);
                holder = new CameraViewHolder(cameraView);
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof CameraViewHolder){
            initItemCameraClick(holder);
        }else if (holder instanceof PictureViewHolder){
            final PictureEntity entity = ModuleLocalPhotoSelectorConstant.PICTURES.get(position);
//            ModuleLocalPhotoUtils.loadImage(mContext, ((PictureViewHolder)holder).imageIv, entity.getPath());
            PhotoViewUtils.loadImage(mContext, ((PictureViewHolder)holder).imageIv, entity.getPath());
            setItemSelect((PictureViewHolder)holder, entity.isSelected());
            //点击选中、取消选中图片
            ((PictureViewHolder)holder).selectIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (entity.isSelected()) {
                        //如果图片已经选中，就取消选中
                        setPictureSelect(entity, false);
                        setItemSelect(((PictureViewHolder) holder), false);
                    } else if (mIsSingle) {
                        //如果是单选，就先清空已经选中的图片，再选中当前图片
                        clearPictureSelect();
                        setPictureSelect(entity, true);
                        setItemSelect(((PictureViewHolder) holder), true);
                    } else if (mMaxCount <= 0 || ModuleLocalPhotoSelectorConstant.SELECT_PICTURE.size() < mMaxCount) {
                        //如果不限制图片的选中数量，或者图片的选中数量
                        // 还没有达到最大限制，就直接选中当前图片。
                        setPictureSelect(entity, true);
                        setItemSelect(((PictureViewHolder) holder), true);
                    } else if (ModuleLocalPhotoSelectorConstant.SELECT_PICTURE.size() == mMaxCount) {
                        ToastUtils.showShort("您本次选择数量已达上线");
                    }
                }
            });
            initItemPictureClick(holder);
        }

//        if (mFoldetIndex == 0){
//            if (ModuleLocalPhotoSelectorConstant.PICTURES == null){
//
//                ((CameraViewHolder)holder).cameraRl.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (ModuleLocalPhotoSelectorConstant.SELECT_PICTURE.size() == mMaxCount){
//                            ToastUtils.showShort("您本次选择数量已达上线");
//                        }else {
//                            mCallBack.doCapturePicture();
//                        }
//                    }
//                });
//            }else {
//                if (position < ModuleLocalPhotoSelectorConstant.PICTURES.size()){
//                    final PictureEntity entity = ModuleLocalPhotoSelectorConstant.PICTURES.get(position);
//                    ModuleLocalPhotoUtils.loadImage(mContext, ((PictureViewHolder)holder).imageIv, entity.getPath());
//                    setItemSelect((PictureViewHolder)holder, entity.isSelected());
//                    ((PictureViewHolder)holder).selectIv.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            if (ModuleLocalPhotoSelectorConstant.PICTURES.get(position).isSelected()) {
//                                //如果图片已经选中，就取消选中
//                                setPictureSelect(entity, false);
//                                setItemSelect(((PictureViewHolder) holder), false);
//                            } else if (mIsSingle) {
//                                //如果是单选，就先清空已经选中的图片，再选中当前图片
//                                clearPictureSelect();
//                                setPictureSelect(entity, true);
//                                setItemSelect(((PictureViewHolder) holder), true);
//                            } else if (mMaxCount <= 0 || ModuleLocalPhotoSelectorConstant.SELECT_PICTURE.size() < mMaxCount) {
//                                //如果不限制图片的选中数量，或者图片的选中数量
//                                // 还没有达到最大限制，就直接选中当前图片。
//                                setPictureSelect(entity, true);
//                                setItemSelect(((PictureViewHolder) holder), true);
//                            } else if (ModuleLocalPhotoSelectorConstant.SELECT_PICTURE.size() == mMaxCount) {
//                                ToastUtils.showShort("您本次选择数量已达上线");
//                            }
//                        }
//                    });
//
//                    ((PictureViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            if (mCallBack != null) {
//                                mCallBack.onItemClick(entity, holder.getAdapterPosition());
//                            }
//                        }
//                    });
//                }else {
//                    ((CameraViewHolder) holder).cameraRl.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            if (mCallBack != null) {
//                                mCallBack.doCapturePicture();
//                            }
//                        }
//                    });
//                }
//            }
//        }else {
//            final PictureEntity entity = ModuleLocalPhotoSelectorConstant.PICTURES.get(position);
//            ModuleLocalPhotoUtils.loadImage(mContext, ((PictureViewHolder)holder).imageIv, entity.getPath());
//            setItemSelect((PictureViewHolder)holder, entity.isSelected());
//            //点击选中/取消选中图片
//            ((PictureViewHolder) holder).selectIv.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (entity.isSelected()) {
//                        //如果图片已经选中，就取消选中
//                        setPictureSelect(entity, false);
//                        setItemSelect(((PictureViewHolder) holder), false);
//                    } else if (mIsSingle) {
//                        //如果是单选，就先清空已经选中的图片，再选中当前图片
//                        clearPictureSelect();
//                        setPictureSelect(entity, true);
//                        setItemSelect(((PictureViewHolder) holder), true);
//                    } else if (mMaxCount <= 0 || ModuleLocalPhotoSelectorConstant.SELECT_PICTURE.size() < mMaxCount) {
//                        //如果不限制图片的选中数量，或者图片的选中数量
//                        // 还没有达到最大限制，就直接选中当前图片。
//                        setPictureSelect(entity, true);
//                        setItemSelect(((PictureViewHolder) holder), true);
//                    } else if (ModuleLocalPhotoSelectorConstant.SELECT_PICTURE.size() == mMaxCount) {
//                        ToastUtils.showShort("您本次选择数量已达上线");
//                    }
//                }
//            });
//
//            ((PictureViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (mCallBack != null) {
//                        mCallBack.onItemClick(entity, holder.getAdapterPosition());
//                    }
//                }
//            });
//        }
    }

    private void initItemCameraClick(RecyclerView.ViewHolder holder){
        ((CameraViewHolder)holder).cameraRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ModuleLocalPhotoSelectorConstant.SELECT_PICTURE.size() == mMaxCount){
                    ToastUtils.showShort("您本次选择数量已达上线");
                }else {
                    mCallBack.doCapturePicture();
                }
            }
        });
    }

    private void initItemPictureClick(final RecyclerView.ViewHolder holder){
        ((PictureViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCallBack != null) {
                    mCallBack.onItemClick(holder.getAdapterPosition());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        int itemCount = 0;
        if (mFoldetIndex == 0){
            itemCount = ModuleLocalPhotoSelectorConstant.PICTURES == null ? 1 : ModuleLocalPhotoSelectorConstant.PICTURES.size() + 1;
        }else {
            itemCount = ModuleLocalPhotoSelectorConstant.PICTURES == null ? 0 : ModuleLocalPhotoSelectorConstant.PICTURES.size();
        }
        return itemCount;
    }

    @Override
    public int getItemViewType(int position) {
        int itemType;
        if (mFoldetIndex == 0){
            if (ModuleLocalPhotoSelectorConstant.PICTURES == null){
                itemType = TYPE_CAMERA;
            }else {
                if (position == ModuleLocalPhotoSelectorConstant.PICTURES.size()){
                    itemType = TYPE_CAMERA;
                }else {
                    itemType = TYPE_IMAGE;
                }
            }
        }else {
            itemType = TYPE_IMAGE;
        }
        return itemType;
    }

    /**
     * 设置图片的选择效果
     * @param entity
     * @param isSelect
     */
    private void setPictureSelect(PictureEntity entity, boolean isSelect){
        if (isSelect){
            entity.setSelected(isSelect);
            ModuleLocalPhotoSelectorConstant.SELECT_PICTURE.add(entity);
        }else {
            entity.setSelected(isSelect);
            ModuleLocalPhotoSelectorConstant.SELECT_PICTURE.remove(entity);
        }
        if (mCallBack != null){
            mCallBack.onPictureSelect(ModuleLocalPhotoSelectorConstant.SELECT_PICTURE.size());
        }
    }

    /**
     * 选择文件夹
     * @param foldetIndex 文件夹下标
     */
    public void folderSelect(int foldetIndex){
        mFoldetIndex = foldetIndex;
        notifyDataSetChanged();
    }

    /**
     * 设置选择效果
     * @param holder
     * @param isSelect
     */
    private void setItemSelect(PictureViewHolder holder, boolean isSelect){
        int resId = -1;
        float alpha = 0.0f;
        if (isSelect){
           resId = R.drawable.icon_image_select;
           alpha = 0.5f;
        }else {
           resId = R.drawable.icon_image_unselect;
           alpha = 0.2f;
        }
        holder.selectIv.setImageResource(resId);
        holder.maskingIv.setAlpha(alpha);
    }

    /**
     * 清除已选中的照片
     */
    private void clearPictureSelect(){
        if (ModuleLocalPhotoSelectorConstant.PICTURES != null && ModuleLocalPhotoSelectorConstant.SELECT_PICTURE.size() == 1){
            PictureEntity entity = ModuleLocalPhotoSelectorConstant.SELECT_PICTURE.get(0);
            int indexOf = ModuleLocalPhotoSelectorConstant.PICTURES.indexOf(entity);
            if (indexOf != -1){
                ModuleLocalPhotoSelectorConstant.PICTURES.get(indexOf).setSelected(false);
                ModuleLocalPhotoSelectorConstant.SELECT_PICTURE.clear();
                notifyItemChanged(indexOf);
            }
        }
    }

    static class PictureViewHolder extends RecyclerView.ViewHolder {

        ImageView imageIv;
        ImageView selectIv;
        ImageView maskingIv;

        public PictureViewHolder(View itemView) {
            super(itemView);
            imageIv = itemView.findViewById(R.id.view_picture_selector_adapter_image_siv);
            selectIv = itemView.findViewById(R.id.view_picture_selector_adapter_select_iv);
            maskingIv = itemView.findViewById(R.id.view_picture_selector_adapter_masking_siv);
        }
    }

    static class CameraViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout cameraRl;

        public CameraViewHolder(View itemView) {
            super(itemView);
            cameraRl = itemView.findViewById(R.id.view_camera_selector_adapter_rl);
        }
    }

    public interface OnEventCallBack{
        void onPictureSelect(int selectCount);
        void onItemClick(int position);
        void doCapturePicture();
    }
}
