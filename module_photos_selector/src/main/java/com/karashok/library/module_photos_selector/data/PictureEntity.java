package com.karashok.library.module_photos_selector.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author KaraShokZ(zhangyaozhong)
 * @name PictureEntity
 * DESCRIPTION 图片实体
 * @date 2018/05/18/下午4:15
 */
public class PictureEntity implements Parcelable{

    private String path;
    private long time;
    private String name;
    private boolean isSelected = false;

    public PictureEntity() {
    }

    public String getPath() {
        return path;
    }

    public PictureEntity setPath(String path) {
        this.path = path;
        return this;
    }

    public long getTime() {
        return time;
    }

    public PictureEntity setTime(long time) {
        this.time = time;
        return this;
    }

    public String getName() {
        return name;
    }

    public PictureEntity setName(String name) {
        this.name = name;
        return this;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public PictureEntity setSelected(boolean selected) {
        isSelected = selected;
        return this;
    }

    protected PictureEntity(Parcel in) {
        path = in.readString();
        time = in.readLong();
        name = in.readString();
        isSelected = in.readByte() != 0;
    }

    public static final Creator<PictureEntity> CREATOR = new Creator<PictureEntity>() {
        @Override
        public PictureEntity createFromParcel(Parcel in) {
            return new PictureEntity(in);
        }

        @Override
        public PictureEntity[] newArray(int size) {
            return new PictureEntity[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(path);
        dest.writeLong(time);
        dest.writeString(name);
        dest.writeByte((byte) (isSelected ? 1 : 0));
    }
}
