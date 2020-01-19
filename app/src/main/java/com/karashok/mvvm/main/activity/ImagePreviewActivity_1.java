package com.karashok.mvvm.main.activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.SharedElementCallback;
import androidx.core.view.ViewCompat;


import com.karashok.library.module_preview_pictures.ui.widget.photoview.OnPhotoTapListener;
import com.karashok.library.module_preview_pictures.ui.widget.photoview_fresco.PhotoViewForFresco;
import com.karashok.mvvm.main.R;

import java.util.List;

public class ImagePreviewActivity_1 extends AppCompatActivity {

    private PhotoViewForFresco view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initWindowTransitions();

        setContentView(R.layout.activity_image_preview_1);
        view = findViewById(R.id.photo_drawee_view);
        view.setPhotoUri(false, ImageActivity_1.mPath, -1);
        ViewCompat.setTransitionName(view, ImageActivity_1.SHARED_ELEMENT_NAME);
        view.setOnPhotoTapListener(new OnPhotoTapListener() {
            @Override
            public void onPhotoTap(ImageView view, float x, float y) {
                onBackPressed();
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void initWindowTransitions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
            AutoTransition transition = new AutoTransition();
            getWindow().setSharedElementEnterTransition(transition);
            getWindow().setSharedElementExitTransition(transition);
            ActivityCompat.setEnterSharedElementCallback(this, new SharedElementCallback() {
                @Override
                public void onSharedElementEnd(List<String> sharedElementNames,
                                               List<View> sharedElements, List<View> sharedElementSnapshots) {
                    for (final View view : sharedElements) {
                        if (view instanceof PhotoViewForFresco) {
                            ((PhotoViewForFresco) view).setScale(1f, true);
                        }
                    }
                }
            });
        }
    }
}
