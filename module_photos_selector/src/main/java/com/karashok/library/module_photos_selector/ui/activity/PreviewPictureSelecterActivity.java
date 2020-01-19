package com.karashok.library.module_photos_selector.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.karashok.library.module_photos_selector.ModuleLocalPhotoSelectorConstant;
import com.karashok.library.module_photos_selector.R;
import com.karashok.library.module_photos_selector.data.PictureEntity;
import com.karashok.library.module_photos_selector.ui.adapter.PictureSelecrtPagerAdapter;
import com.karashok.library.module_photos_selector.ui.widget.ThingCallBack;
import com.karashok.library.module_photos_selector.util.PhotoSelectIntentUtils;
import com.karashok.library.module_preview_pictures.ui.widget.PhotoViewPager;
import com.karashok.library.module_util.utilcode.utils.view_util.StatusBarUtils;
import com.karashok.library.module_util.utilcode.utils.view_util.ToastUtils;

import java.util.ArrayList;

import static android.animation.ObjectAnimator.ofFloat;

/**
 * @author KaraShokZ(zhangyaozhong)
 * @name PreviewPictureSelecterActivity
 * DESCRIPTION 预览下，图片选择页面
 * @date 2018/05/18/下午4:15
 */
public class PreviewPictureSelecterActivity extends AppCompatActivity {

    /**
     * 返回按钮
     */
    private ImageView mBackIV;
    /**
     * 标题文字
     */
    private TextView mTitleTV;
    /**
     * 选择按钮
     */
    private ImageView mSelectIV;
    /**
     * 完成按钮
     */
    private TextView mFinishTV;
    /**
     * 图片预览ViewPager
     */
    private PhotoViewPager mPreviewPVP;
    /**
     * 底部栏Layout
     */
    private RelativeLayout mBottomRL;
    /**
     * 标题栏Layout
     */
    private RelativeLayout mTitleRL;

    private ArrayList<PictureEntity> mPictures;
    /**
     * 最大选择数
     */
    private int mMaxCount;
    /**
     * 当前的下标
     */
    private int mPosition;
    /**
     * 是否单选
     */
    private boolean mIsSingle;
    /**
     * 是不是完成返回
     */
    private boolean mIsFinish = false;
    /**
     * 是否显示状态栏
     */
    private boolean mIsShowStateBar = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 强制竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.aty_preview_picture_selecter);
        // 设置状态栏颜色
        StatusBarUtils.setStatusBarColor(this,"#000000");

        initData();
        initView();
        initAdapter();

        mTitleTV.setText("1/" + mPictures.size());
        changeSelect(mPictures.get(0));
        mPreviewPVP.setCurrentItem(mPosition);
    }

    /**
     * 初始化传过来的数据
     */
    private void initData() {
        Intent intent = getIntent();
        boolean isAllPicture = intent.getBooleanExtra(PhotoSelectIntentUtils.PREVIEW_PICTURES_WHICH, true);
        mMaxCount = intent.getIntExtra(PhotoSelectIntentUtils.MAX_SELECT_COUNT, 0);
        mIsSingle = intent.getBooleanExtra(PhotoSelectIntentUtils.IS_SINGLE, false);
        mPosition = intent.getIntExtra(PhotoSelectIntentUtils.PREVIEW_PICTURES_POSITION, 0);

        if (isAllPicture){
            mPictures = ModuleLocalPhotoSelectorConstant.PICTURES;
        }else {
            mPictures = ModuleLocalPhotoSelectorConstant.SELECT_PICTURE;
        }
    }

    /**
     * 初始化View
     */
    private void initView() {
        mBackIV = findViewById(R.id.aty_preview_picture_selecter_title_back_iv);
        mTitleTV = findViewById(R.id.aty_preview_picture_selecter_title_title_tv);
        mSelectIV = findViewById(R.id.aty_preview_picture_selecter_title_select_iv);
        mFinishTV = findViewById(R.id.aty_preview_picture_selecter_buttom_finish_tv);
        mPreviewPVP = findViewById(R.id.aty_preview_picture_selecter_content_pvp);
        mTitleRL = findViewById(R.id.aty_preview_picture_selecter_title_rl);
        mBottomRL = findViewById(R.id.aty_preview_picture_selecter_bottom_rl);

        mBackIV.setOnClickListener(mViewClick);
        mSelectIV.setOnClickListener(mViewClick);
        mFinishTV.setOnClickListener(mViewClick);
    }

    /**
     * View的点击事件
     */
    private View.OnClickListener mViewClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int vId = v.getId();
            if (R.id.aty_preview_picture_selecter_title_back_iv == vId) {
                finish();
            } else if (R.id.aty_preview_picture_selecter_title_select_iv == vId) {
                clickSelect();
            } else if (R.id.aty_preview_picture_selecter_buttom_finish_tv == vId) {
                mIsFinish = true;
                finish();
            }
        }
    };

    /**
     * 初始化PagerAdapter
     */
    private void initAdapter() {
        PictureSelecrtPagerAdapter adapter = new PictureSelecrtPagerAdapter(mPictures);
        mPreviewPVP.setAdapter(adapter);
        adapter.setCallBack(new ThingCallBack<Integer>() {
            @Override
            public void thingCallBack(Integer o) {
                if (mIsShowStateBar){
                    hideBar();
                } else {
                    showBar();
                }
            }
        });
        initPagerLinistener();
    }

    /**
     * 初始化ViewPager滑动监听
     */
    private void initPagerLinistener(){
        mPreviewPVP.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                mTitleTV.setText(position + 1 + "/" + mPictures.size());
                changeSelect(mPictures.get(position));
            }
        });
    }

    /**
     * 显示头部和尾部栏
     */
    private void showBar() {
        allScreenState(true);
        StatusBarUtils.setStatusBarVisibility(this, true);
        // 由于状态栏隐藏时，标题栏距屏幕顶部的距离会改变。所以需要手动赋值，以确保显示时位置正确。
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) mTitleRL.getLayoutParams();
        lp.topMargin = StatusBarUtils.getStatusBarHeight();
        mTitleRL.setLayoutParams(lp);

        //添加延时，保证StatusBar完全显示后再进行动画。
        mTitleRL.postDelayed(new Runnable() {
            @Override
            public void run() {
                startTitleTransAnim(mTitleRL.getTranslationY(), 0);
                startBottomTransAnim(mBottomRL.getTranslationY(), 0);
            }
        }, 100);
    }

    /**
     * 隐藏头部和尾部栏
     */
    private void hideBar() {
        allScreenState(false);
        startTitleTransAnim(0, -mTitleRL.getHeight());
        startBottomTransAnim(0, mBottomRL.getHeight());
    }

    /**
     * 设置选择
     */
    private void clickSelect() {
        int position = mPreviewPVP.getCurrentItem();
        if (mPictures != null && mPictures.size() > position) {
            PictureEntity picture = mPictures.get(position);
            if (picture.isSelected()) {
                picture.setSelected(false);
                ModuleLocalPhotoSelectorConstant.SELECT_PICTURE.remove(picture);
            } else if (mIsSingle) {
                clearImageSelect();
                ModuleLocalPhotoSelectorConstant.SELECT_PICTURE.clear();
                picture.setSelected(true);
                ModuleLocalPhotoSelectorConstant.SELECT_PICTURE.add(picture);
            } else if (mMaxCount <= 0 || ModuleLocalPhotoSelectorConstant.SELECT_PICTURE.size() < mMaxCount) {
                ModuleLocalPhotoSelectorConstant.SELECT_PICTURE.add(picture);
                picture.setSelected(true);
            } else if (ModuleLocalPhotoSelectorConstant.SELECT_PICTURE.size() == mMaxCount) {
                ToastUtils.showShort("您本次选择数量已达上线");
            }
            changeSelect(picture);
        }
    }

    /**
     * 清除已选择
     */
    private void clearImageSelect() {
        if (mPictures != null && ModuleLocalPhotoSelectorConstant.SELECT_PICTURE.size() == 1) {
            int index = mPictures.indexOf(ModuleLocalPhotoSelectorConstant.SELECT_PICTURE.get(0));
            mPictures.get(index).setSelected(false);
            if (index != -1) {
                ModuleLocalPhotoSelectorConstant.SELECT_PICTURE.clear();
                mSelectIV.setImageResource(mPictures.get(index).isSelected() ? R.drawable.icon_image_select : R.drawable.icon_image_unselect);
            }
        }
    }

    /**
     * 设置选择状态
     * @param picture
     */
    private void changeSelect(PictureEntity picture) {
        mSelectIV.setImageResource(picture.isSelected() ? R.drawable.icon_image_select : R.drawable.icon_image_unselect);
        setSelectImageCount(ModuleLocalPhotoSelectorConstant.SELECT_PICTURE.size());
    }

    /**
     * 设置标题文字和完成按钮的状态
     * @param count
     */
    private void setSelectImageCount(int count) {
        boolean enable = false;
        String finishStr = "完成";
        if (count != 0) {
            enable = true;
            if (mIsSingle) {
                mFinishTV.setText("完成");
            } else if (mMaxCount > 0) {
                mFinishTV.setText("完成(" + count + "/" + mMaxCount + ")");
            } else {
                mFinishTV.setText("完成(" + count + ")");
            }
        }
        mFinishTV.setEnabled(enable);
        mFinishTV.setText(finishStr);
    }

    /**
     * 设置状态栏显示的状态值和状态栏的颜色
     * @param state true：状态栏显示；false：状态栏隐藏
     */
    private void allScreenState(boolean state) {
        mIsShowStateBar = state;
        int colorRes;
        if (state) {
            colorRes = Color.WHITE;
        } else {
            colorRes = Color.BLACK;
        }
        getWindow().getDecorView().setBackgroundColor(colorRes);
    }

    /**
     * 获取动画状态监听
     * @param isShow true：走显示逻辑：false：走隐藏逻辑
     * @return
     */
    private AnimatorListenerAdapter getAnimListenerAdapter(final boolean isShow) {
        AnimatorListenerAdapter adapter = new AnimatorListenerAdapter() {

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                if (isShow && mTitleRL != null) {
                    mTitleRL.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (!isShow && mTitleRL != null) {
                    mTitleRL.setVisibility(View.GONE);
                    //添加延时，保证rlTopBar完全隐藏后再隐藏StatusBar。
                    mTitleRL.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            StatusBarUtils.setStatusBarVisibility(PreviewPictureSelecterActivity.this, false);
                        }
                    }, 5);
                }
            }
        };
        return adapter;
    }

    /**
     * Title的动画
     * @param values
     */
    private void startTitleTransAnim(float... values){
        ObjectAnimator animator = ofFloat(mTitleRL, "translationY",values[0], values[1]);
        animator.addListener(getAnimListenerAdapter(true));
        animator.setDuration(300);
        animator.start();
    }

    /**
     * bottom的动画
     * @param values
     */
    private void startBottomTransAnim(float... values){
        ofFloat(mBottomRL, "translationY", values[0], values[1])
                .setDuration(300).start();
    }

    /**
     * 返回并传递数据
     */
    @Override
    public void finish() {
        //Activity关闭时，通过Intent把用户的操作(确定/返回)传给ImageSelectActivity。
        Intent intent = new Intent();
        intent.putExtra(PhotoSelectIntentUtils.IS_CONFIRM, mIsFinish);
        setResult(PhotoSelectIntentUtils.CODE_RESULT_PREVIEW_PICTURE, intent);
        super.finish();
    }
}
