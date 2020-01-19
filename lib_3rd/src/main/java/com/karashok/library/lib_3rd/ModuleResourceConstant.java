package com.karashok.library.lib_3rd;

import android.os.Environment;

import java.io.File;

/**
 * @author KaraShokZ (张耀中)
 * DESCRIPTION
 * @name ModuleResourceConstant
 * @date 2020/01/17 14:56
 **/
public class ModuleResourceConstant {

    /**
     * 本地存储的根目录
     */
    public static final String EXTERNAL_DIREXTORY = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;

    /**
     * 应用的外部文件夹（所有文件都要存于此文件夹内）
     */
    public static final String SAVE_FILE = EXTERNAL_DIREXTORY + "karashok" + File.separator;
}
