package com.karashok.library.module_preview_pictures;

import android.content.Context;
import android.content.Intent;

import com.karashok.library.module_preview_pictures.data.FileEntity;
import com.karashok.library.module_preview_pictures.ui.activity.PicturesPreviewActivity;

import java.io.Serializable;
import java.util.List;

import io.reactivex.annotations.NonNull;

/**
 * @author KaraShokZ (张耀中)
 * DESCRIPTION
 * @name ModulePicturePreviewContants
 * @date 2020/01/17 15:01
 **/
public class ModulePicturePreviewContants {

    public static final String FILE_ITEMS = "file_items";

    public static final String CURRENT_INDEX = "current_index";

    public static void launch(Context context, @NonNull List<FileEntity> fileItems, @NonNull int curIndex) {
        Intent intent = new Intent(context, PicturesPreviewActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(FILE_ITEMS, (Serializable) fileItems);
        intent.putExtra(CURRENT_INDEX, curIndex);
        context.startActivity(intent);
    }
}
