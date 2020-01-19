package com.karashok.mvvm.main.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.facebook.drawee.view.SimpleDraweeView;
import com.karashok.mvvm.main.R;

public class ImageActivity_2 extends AppCompatActivity {

    public static final  String mPath = "https://ww1.sinaimg.cn/large/0065oQSqly1ftdtot8zd3j30ju0pt137.jpg";

    private SimpleDraweeView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_image_1);

        view = findViewById(R.id.photo_drawee_view);
        view.setImageResource(R.drawable.wugeng);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ImageActivity_2.this, ImagePreviewActivity_2.class);
                int location[] = new int[2];

                view.getLocationOnScreen(location);
                intent.putExtra("left", location[0]);
                intent.putExtra("top", location[1]);
                intent.putExtra("height", view.getHeight());
                intent.putExtra("width", view.getWidth());

                ImageActivity_2.this.startActivity(intent);
                overridePendingTransition(0,0);
            }
        });
    }
}
