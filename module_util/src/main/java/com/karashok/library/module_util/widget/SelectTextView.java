package com.karashok.library.module_util.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.karashok.library.module_util.R;


/**
 * @author Ralf(wanglixin)
 * DESCRIPTION
 * @name SelectTextView
 * @date 2018/05/18 下午8:06
 **/
public class SelectTextView extends AppCompatTextView {


    private boolean isSelected;
    private int mSelectedTextColor;
    private int mDisSelectedTextColor;
    private int mSelectedResId;
    private int mDisSeletedResId;

    public SelectTextView(Context context) {
        this(context,null);
    }

    public SelectTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SelectTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView();
    }

    private void initView() {

        // 可点击
        setClickable(true);
        setFocusable(true);

        setTextSize(12);
        setGravity(Gravity.CENTER);
        setBackgroundResource(R.drawable.doc_dis_select_btn_bkg);

        // 默认设置
        mSelectedResId = R.drawable.doc_select_btn_bkg;
        mDisSeletedResId = R.drawable.doc_dis_select_btn_bkg;
        mSelectedTextColor = R.color.white;
        mDisSelectedTextColor = R.color.black_alpha32;

        setText("");
        setTextColor(mDisSelectedTextColor);

    }
    public void setStatus(boolean isSelected){
        this.isSelected = isSelected;
    }
    public boolean getStatus(){
        return isSelected;
    }

    public void setSelectTextColor(int selectedTextColor,int disSeletedTextColor){

        this.mSelectedTextColor = selectedTextColor;
        this.mDisSelectedTextColor = disSeletedTextColor;
    }

    public void setSelectResourceId(int selectedResId,int disSeletedResId){
        this.mSelectedResId = selectedResId;
        this.mDisSeletedResId = disSeletedResId;
    }

    /**
     * 切换标签选中状态
     */
    public void toggle() {
        isSelected = !isSelected;
        if (isSelected) {

            setTextColor(getResources().getColor(mSelectedTextColor));
            setBackgroundResource(mSelectedResId);
        } else {

            setTextColor(getResources().getColor(mDisSelectedTextColor));
            setBackgroundResource(mDisSeletedResId);
        }
    }

    public void resetStatus(){

        isSelected = false;
        setTextColor(getResources().getColor(mDisSelectedTextColor));
        setBackgroundResource(mDisSeletedResId);
    }


}
