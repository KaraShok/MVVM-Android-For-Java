package com.karashok.library.module_image_load.util;

import android.net.Uri;
import android.text.TextUtils;

import com.karashok.library.module_image_load.MImageLoadConstants;


/**
 * @author KaraShokZ(zhangyaozhong)
 * @name ImageLoadUtils
 * DESCRIPTION
 * @date 2018/07/25/下午5:24
 */

public class ImageLoadUtils {

    /**
     * 将资源ID/绝对路径 -> Uri地址
     *
     * @return 图片Uri地址
     */
    public static Uri getUri(boolean isRes, String path, int resId) {

        Uri uri;
        if (isRes) {
            String resPath = "res://" + MImageLoadConstants.PACKAGE_NAME + "/" + resId;
            // uri= Uri.parse("res://com.ladingwu.frescolibrary/"+options.getResource());
            uri = Uri.parse(resPath);
        } else if (!TextUtils.isEmpty(path) && !path.contains("http")) {
            uri = Uri.parse("file://" + path);
        } else {
            uri = Uri.parse(path);
        }
        return uri;
    }
}
