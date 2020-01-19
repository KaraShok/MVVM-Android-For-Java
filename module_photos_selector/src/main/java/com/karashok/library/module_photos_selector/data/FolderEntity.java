package com.karashok.library.module_photos_selector.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.karashok.library.module_util.utilcode.utils.base_util.StringUtils;

import java.util.ArrayList;

/**
 * @author KaraShokZ(zhangyaozhong)
 * @name FolderEntity
 * DESCRIPTION 相册实体
 * @date 2018/05/19/上午11:17
 */
public class FolderEntity implements Parcelable {

    private String name;
    private ArrayList<PictureEntity> images;
    private boolean isSelected = false;

    public FolderEntity(String name) {
        this.name = name;
    }

    public FolderEntity(String name, ArrayList<PictureEntity> images) {
        this.name = name;
        this.images = images;
    }

    protected FolderEntity(Parcel in) {
        name = in.readString();
        images = in.createTypedArrayList(PictureEntity.CREATOR);
        isSelected = in.readByte() != 0;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<PictureEntity> getImages() {
        return images;
    }

    public void setImages(ArrayList<PictureEntity> images) {
        this.images = images;
    }

    public void addImage(PictureEntity imageEntity) {
        if (imageEntity != null && !StringUtils.isEmpty(imageEntity.getPath())) {
            if (images == null) {
                images = new ArrayList<>();
            }
            images.add(imageEntity);
        }
    }

    public static final Creator<FolderEntity> CREATOR = new Creator<FolderEntity>() {
        @Override
        public FolderEntity createFromParcel(Parcel in) {
            return new FolderEntity(in);
        }

        @Override
        public FolderEntity[] newArray(int size) {
            return new FolderEntity[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeTypedList(images);
        dest.writeByte((byte) (isSelected ? 1 : 0));
    }

    @Override
    public String toString() {
        return "Folder{" +
                "name='" + name + '\'' +
                ", images=" + images +
                '}';
    }
}
