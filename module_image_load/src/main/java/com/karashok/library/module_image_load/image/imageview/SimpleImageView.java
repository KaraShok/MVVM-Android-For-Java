package com.karashok.library.module_image_load.image.imageview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.ViewGroup;


import androidx.appcompat.widget.AppCompatImageView;

import com.karashok.library.module_image_load.R;
import com.karashok.library.module_image_load.image.imageframework.ImageLoaderManager;
import com.karashok.library.module_image_load.image.imageframework.ImageLoaderOptions;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * @author Ralf(wanglixin)
 * DESCRIPTION 使用 SimpleImageView 需要先初始化 ImageLoaderManager,由于利用 Fresco 加载
 * @name SimpleImage
 * @date 2018/06/06 下午11:13
 **/
public class SimpleImageView extends AppCompatImageView {

    private boolean isCircle; // 是否圆形
    private int cornerRadius;
    private boolean isBlur; // 是否开启高斯模糊
    private int blurRadius; // 高斯模糊参数，越大越模糊
    private String uri;  // 图片地址
    private int resource;  // 图片地址
    private int holderDrawable;  // 设置占位图
    private int errorDrawable;  // 是否展示加载错误的图片
    private boolean asGif = false;   // 是否作为gif展示

    public SimpleImageView(Context context) {
        this(context, null);
    }

    public SimpleImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SimpleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        // 获取自定义属性
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SimpleImageViewStyle);
        isBlur = ta.getBoolean(R.styleable.SimpleImageViewStyle_isBlur, false);
        isCircle = ta.getBoolean(R.styleable.SimpleImageViewStyle_isCircle, false);
        blurRadius = ta.getInt(R.styleable.SimpleImageViewStyle_blurRadius, -1);
        uri = ta.getString(R.styleable.SimpleImageViewStyle_imageUri);
        resource = ta.getResourceId(R.styleable.SimpleImageViewStyle_imageRes, -1);
        holderDrawable = ta.getResourceId(R.styleable.SimpleImageViewStyle_placeHolderRes, -1);
        errorDrawable = ta.getResourceId(R.styleable.SimpleImageViewStyle_errorHolderRes, -1);
        asGif = ta.getBoolean(R.styleable.SimpleImageViewStyle_asGif, false);
        cornerRadius = ta.getInt(R.styleable.SimpleImageViewStyle_cornerRadiusInt, 0);

        ImageLoaderOptions options = setImageConfig();
        showImage(options);
        ta.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        ViewGroup.LayoutParams params = getLayoutParams();

        // 当布局参数设置为wrap_content时，设置为 MATCH_PARENT 模式
        if (params.width == WRAP_CONTENT || params.height == WRAP_CONTENT) {

            params.width = MATCH_PARENT;
            params.height = MATCH_PARENT;
            setLayoutParams(params);
        }
    }

    /**
     * 设置配置
     *
     * @return 配置
     */
    private ImageLoaderOptions setImageConfig() {

        ImageLoaderOptions.Builder builder;

        // 根据传入资源方式创建 builder
        if (resource != -1) {
            builder = new ImageLoaderOptions.Builder(this, resource);
        } else {
            builder = new ImageLoaderOptions.Builder(this, uri);
        }

        if (isCircle) builder.isCircle();
        if (isBlur) builder.blurImage(true);
        if (blurRadius > -1) builder.blurValue(blurRadius);
        if (asGif) builder.asGif(true);
        if (errorDrawable > -1) builder.error(errorDrawable);
        if (holderDrawable > -1) builder.placeholder(holderDrawable);
        builder.imageRadiusDp(cornerRadius);

        return builder.build();
    }

    /**
     * 显示图片
     *
     * @param options ImageLoaderOptions
     */
    private void showImage(ImageLoaderOptions options) {

        ImageLoaderManager.getInstance().showImage(options);
    }
}
