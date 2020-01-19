package com.karashok.library.module_preview_pictures.data;

import java.io.Serializable;

/**
 * @author KaraShokZ(zhangyaozhong)
 * @name FileEntity
 * DESCRIPTION 图片实体
 * @date 2018/05/18/下午2:52
 */
public class FileEntity implements Serializable {

    private String path;

    private String name;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "FileEntity{" +
                "path='" + path + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
