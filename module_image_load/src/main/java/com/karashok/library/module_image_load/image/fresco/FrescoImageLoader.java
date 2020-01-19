package com.karashok.library.module_image_load.image.fresco;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;

import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.DraweeHolder;
import com.facebook.imagepipeline.common.ImageDecodeOptions;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.karashok.library.module_image_load.R;
import com.karashok.library.module_image_load.image.imageframework.IImageLoaderstrategy;
import com.karashok.library.module_image_load.image.imageframework.ImageLoaderConfig;
import com.karashok.library.module_image_load.image.imageframework.ImageLoaderOptions;
import com.karashok.library.module_image_load.image.imageframework.LoaderResultCallBack;
import com.karashok.library.module_image_load.util.ImageLoadUtils;


/**
 * @author Ralf(wanglixin)
 * DESCRIPTION Fresco图片加载实例
 * @name FrescoImageLoader
 * @date 2018/05/19 下午1:54
 **/
public class FrescoImageLoader implements IImageLoaderstrategy {

    @Override
    public void init(Context appContext, ImageLoaderConfig config) {
        Fresco.initialize(appContext, getPipelineConfig(appContext, config));
    }

    @Override
    public void showImage(@NonNull ImageLoaderOptions options) {
        showImgae(options);
    }

    @Override
    public void hideImage(@NonNull View view, int isVisiable) {

        view.setVisibility(isVisiable);
    }

    @Override
    public void cleanMemory(Context context) {
        Fresco.getImagePipeline().clearMemoryCaches();
    }

    @Override
    public void pause(Context context) {
        Fresco.getImagePipeline().pause();
    }

    @Override
    public void resume(Context context) {
        Fresco.getImagePipeline().resume();
    }

    // private ViewStatesListener mStatesListener;
    private static final int IMAGETAG = 1;

    private void showImgae(final ImageLoaderOptions options) {
        ImageView imageView = (ImageView) options.getViewContainer();

        // 设置 ImageView 布局
        setImageViewLayoutParas(imageView);
        GenericDraweeHierarchy hierarchy = null;
        GenericDraweeHierarchyBuilder hierarchyBuilder = GenericDraweeHierarchyBuilder.newInstance(imageView.getContext().getResources());
        DraweeHolder draweeHolder = (DraweeHolder) imageView.getTag(R.id.fresco_drawee);

        // 图片路径
        Uri uri = null;
        if (options.getResource() != -1){
            uri = ImageLoadUtils.getUri(true,"", options.getResource());
        }else {
            uri = ImageLoadUtils.getUri(true,options.getUrl(), -1);
        }

        if (uri == null) {
            return;
        }

        if (options.getHolderDrawable() != -1) {
            hierarchyBuilder.setPlaceholderImage(options.getHolderDrawable());
        }
        if (options.getErrorDrawable() != -1) {
            hierarchyBuilder.setFailureImage(options.getErrorDrawable());
        }
        if (options.isCircle()) {
            RoundingParams roundingParams = new RoundingParams();
            hierarchyBuilder.setRoundingParams(roundingParams.setRoundAsCircle(true));
        }

        if (options.needImageRadius()) {
            RoundingParams roundingParams = RoundingParams.fromCornersRadius(options.getImageRadius());
            hierarchyBuilder.setRoundingParams(roundingParams);
        }
        if (hierarchy == null) {
            hierarchy = hierarchyBuilder.build();

        }

        PipelineDraweeControllerBuilder controllerBuilder = Fresco.newDraweeControllerBuilder().setUri(uri).setAutoPlayAnimations(true);

        ImageRequestBuilder imageRequestBuilder = ImageRequestBuilder.newBuilderWithSource(uri);
        if (options.getImageSize() != null) {
            imageRequestBuilder.setResizeOptions(new ResizeOptions(options.getImageSize().getWidth(), options.getImageSize().getHeight()));
        }
        if (!options.isAsGif()) {
            // 解决有些gif格式的头像的展示问题，因为我们需要展示一个静态的圆形图片
            imageRequestBuilder.setImageDecodeOptions(ImageDecodeOptions.newBuilder().setForceStaticImage(true).build());
        }
        if (options.isBlurImage()) {
            imageRequestBuilder.setPostprocessor(new BlurPostprocessor(options.getBlurValue()));
        }
        ImageRequest request = imageRequestBuilder.build();
        controllerBuilder.setImageRequest(request);
        if (options.getLoaderResultCallBack() != null) {
            controllerBuilder.setControllerListener(new ControllerListener<ImageInfo>() {
                @Override
                public void onSubmit(String id, Object callerContext) {

                }

                @Override
                public void onFinalImageSet(String id, @Nullable ImageInfo imageInfo, @Nullable Animatable animatable) {
                    LoaderResultCallBack callBack = options.getLoaderResultCallBack();
                    if (callBack != null) {
                        callBack.onSuccess();

                    }
                }

                @Override
                public void onIntermediateImageSet(String id, @Nullable ImageInfo imageInfo) {

                }

                @Override
                public void onIntermediateImageFailed(String id, Throwable throwable) {

                }

                @Override
                public void onFailure(String id, Throwable throwable) {
                    LoaderResultCallBack callBack = options.getLoaderResultCallBack();
                    if (callBack != null) {
                        callBack.onFailed();

                    }

                }

                @Override
                public void onRelease(String id) {

                }
            });
        }
        DraweeController controller;

        if (draweeHolder == null) {
            draweeHolder = DraweeHolder.create(hierarchy, options.getViewContainer().getContext());
            controller = controllerBuilder.build();

        } else {
            controller = controllerBuilder.setOldController(draweeHolder.getController()).build();

        }

        // 请求
        draweeHolder.setController(controller);
        ViewStatesListener mStatesListener = new ViewStatesListener(draweeHolder);

        imageView.addOnAttachStateChangeListener(mStatesListener);

        // 判断是否ImageView已经 attachToWindow
        if (ViewCompat.isAttachedToWindow(imageView)) {
            draweeHolder.onAttach();
        }

//        if (ViewC.isAttachedToWindow()) {
//            draweeHolder.onAttach();
//        }
        // 保证每一个ImageView中只存在一个draweeHolder
        imageView.setTag(R.id.fresco_drawee, draweeHolder);
        // 拿到数据
        imageView.setImageDrawable(draweeHolder.getTopLevelDrawable());

    }

    /**
     * 设置 ImageView 布局
     *
     * @param imageView 传入的 ImageView
     */
    private void setImageViewLayoutParas(ImageView imageView) {
        ViewGroup.LayoutParams params = imageView.getLayoutParams();
        if (params == null) {
            params = new ViewGroup.LayoutParams(200, 200);
        }

        if (params.width == ViewGroup.LayoutParams.WRAP_CONTENT) {
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        }
        if (params.height == ViewGroup.LayoutParams.WRAP_CONTENT) {
            params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        }
        imageView.setLayoutParams(params);
    }


    public class ViewStatesListener implements View.OnAttachStateChangeListener {

        private DraweeHolder holder;

        public ViewStatesListener(DraweeHolder holder) {
            this.holder = holder;
        }

        @Override
        public void onViewAttachedToWindow(View v) {
            this.holder.onAttach();
        }

        @Override
        public void onViewDetachedFromWindow(View v) {
            this.holder.onDetach();
        }
    }


    public ImagePipelineConfig getPipelineConfig(Context context, ImageLoaderConfig config) {
        // set the cache file path
        DiskCacheConfig diskCacheConfig = DiskCacheConfig.newBuilder(context)
                .setMaxCacheSize(config.getMaxMemory())
                .setMaxCacheSizeOnLowDiskSpace(config.getMaxMemory() / 5)
                .build();

        return ImagePipelineConfig.newBuilder(context)
                .setDownsampleEnabled(true)
                // 设置缓存
                .setMainDiskCacheConfig(diskCacheConfig)
                .setBitmapsConfig(Bitmap.Config.RGB_565)
                // 保证缓存达到一定条件就及时清除缓存
                .setBitmapMemoryCacheParamsSupplier(new BitmapMemoryCacheParamsSupplier((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE)))
                .build();
    }
}
