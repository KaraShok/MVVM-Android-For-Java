 package com.karashok.mvvm.main.activity;

 import android.content.Intent;
 import android.os.Bundle;
 import android.view.View;

 import androidx.appcompat.app.AppCompatActivity;
 import androidx.core.app.ActivityCompat;
 import androidx.core.app.ActivityOptionsCompat;
 import androidx.core.app.SharedElementCallback;
 import androidx.core.view.ViewCompat;

 import com.facebook.drawee.view.SimpleDraweeView;
 import com.karashok.mvvm.main.R;

 import java.util.List;

 public class ImageActivity_1 extends AppCompatActivity {

    public static final  String mPath = "https://ww1.sinaimg.cn/large/0065oQSqly1ftdtot8zd3j30ju0pt137.jpg";

    public static final String SHARED_ELEMENT_NAME = "SHARED_ELEMENT_NAME";

    private SimpleDraweeView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_1);

        view = findViewById(R.id.photo_drawee_view);
        view.setImageURI(mPath);
        ViewCompat.setTransitionName(view, SHARED_ELEMENT_NAME);

        view.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {

                ActivityOptionsCompat optionsCompat =
                        ActivityOptionsCompat.makeSceneTransitionAnimation(
                                ImageActivity_1.this, view, SHARED_ELEMENT_NAME);

                // FIX BUG https://github.com/facebook/fresco/issues/1445
                ActivityCompat.setExitSharedElementCallback(ImageActivity_1.this,
                        new SharedElementCallback() {
                            @Override
                            public void onSharedElementEnd(List<String> sharedElementNames,
                                                           List<View> sharedElements, List<View> sharedElementSnapshots) {
                                super.onSharedElementEnd(sharedElementNames, sharedElements,
                                        sharedElementSnapshots);
                                for (final View view : sharedElements) {
                                    if (view instanceof SimpleDraweeView) {
                                        view.setVisibility(View.VISIBLE);
                                    }
                                }
                            }
                        });

                ActivityCompat.startActivity(ImageActivity_1.this,
                        new Intent(ImageActivity_1.this, ImagePreviewActivity_1.class),
                        optionsCompat.toBundle());
            }
        });
    }
}
