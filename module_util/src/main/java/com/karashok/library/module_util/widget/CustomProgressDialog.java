package com.karashok.library.module_util.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.karashok.library.module_util.R;
import com.karashok.library.module_util.utilcode.utils.base_util.StringUtils;


/**
 * @author Ralf(wanglixin)
 * DESCRIPTION
 * @name CustomProgressDialog
 * @date 2018/06/04 下午1:58
 **/
public class CustomProgressDialog extends Dialog {

    Context context;

    private ImageView spaceshipImage;

    private Animation hyperspaceJumpAnimation;

    private TextView textView;

    private String tipText;

    public CustomProgressDialog(Context context) {
        super(context);
        this.context = context;
    }

    public CustomProgressDialog(Context context, int theme) {
        super(context, theme);
        this.context = context;
    }

    public CustomProgressDialog(Context context, int theme, String tipText) {
        super(context, theme);
        this.context = context;
        this.tipText = tipText;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.dialog_loading, null);// 得到加载view

        LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_loading_ll);// 加载布局

        spaceshipImage = (ImageView) v.findViewById(R.id.dialog_loading_iv);

        textView = (TextView) v.findViewById(R.id.dialog_loading_tv);
        if (!StringUtils.isEmpty(tipText)) {
            textView.setText(tipText);
        }

        // 加载动画
        hyperspaceJumpAnimation = AnimationUtils.loadAnimation(context, R.anim.loading);
        // 使用ImageView显示动画
        spaceshipImage.startAnimation(hyperspaceJumpAnimation);
        setCancelable(false);// 不可以用“返回键”取消
        setContentView(layout, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));// 设置布局
    }

    public void setTipText(String tipText) {
        if (textView == null) {
            this.tipText = tipText;
        } else {
            textView.setText(tipText);
        }
    }

}
