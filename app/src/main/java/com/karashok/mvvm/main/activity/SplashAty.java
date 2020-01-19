package com.karashok.mvvm.main.activity;

import android.annotation.TargetApi;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorListener;
import androidx.databinding.ViewDataBinding;

import com.alibaba.android.arouter.launcher.ARouter;
import com.karashok.library.common.base.BaseActivity;
import com.karashok.library.common.base.BaseViewModel;
import com.karashok.library.module_image_load.image.imageframework.ImageLoaderManager;
import com.karashok.library.module_image_load.image.imageframework.ImageLoaderOptions;
import com.karashok.library.module_util.utilcode.utils.app_util.ActivityManager;
import com.karashok.mvvm.main.R;
import com.karashok.mvvm.module_gankio.MGankIOConstant;

/**
 * @author KaraShok(zhangyaozhong)
 * @name SplashAty
 * DESCRIPTION 起始启动页
 * @date 2018/06/16/下午6:18
 */
public class SplashAty extends BaseActivity<BaseViewModel, ViewDataBinding> {

    private ImageView mImage;

    @Override
    protected int layoutId() {
        return R.layout.aty_splash;
    }

    @Override
    protected boolean isDataBinding() {
        return false;
    }

    @NonNull
    @Override
    protected Class bindViewModel() {
        return null;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void create() {
        super.create();
        setTheme(R.style.SplashActivityTheme);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        //强制竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        ActivityManager.getActivityManagerInstance().popAllActivityExceptOne(SplashAty.class);

        mImage = findViewById(R.id.aty_splash_image);
        loadImage();

        ARouter.getInstance()
                .build(MGankIOConstant.ROUTER_MODULE_GANKIO_ATY_MAIN)
                .navigation();

//        finish();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {

        if (!hasFocus) {
            return;
        }

        initAnim();

        super.onWindowFocusChanged(hasFocus);
    }

    private void loadImage(){
        ImageLoaderManager.getInstance().init(getApplicationContext());

        ImageLoaderOptions options = new ImageLoaderOptions.Builder(mImage, R.drawable.welcome).build();
        ImageLoaderManager.getInstance().showImage(options);
    }

    private void initAnim(){
        ViewCompat.animate(mImage)
                .scaleX(8)
                .scaleY(8)
                .alpha(0)
                .setStartDelay(300)
                .setDuration(1000)
                .setInterpolator(new DecelerateInterpolator(1.2f))
                .setListener(new ViewPropertyAnimatorListener() {
                    @Override
                    public void onAnimationStart(View view) {

                    }

                    @Override
                    public void onAnimationEnd(View view) {

                    }

                    @Override
                    public void onAnimationCancel(View view) {

                    }
                })
                .start();
    }

}
