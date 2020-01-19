package com.karashok.library.common.databinding.base;

import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import androidx.databinding.BindingAdapter;

import com.karashok.library.common.databinding.binding_event.ViewCommandManager;
import com.karashok.library.common.databinding.binding_event.ViewResponseManager;

/**
 * @author KaraShokZ(zhangyaozhong)
 * @name ViewBindingAdapter
 * DESCRIPTION View的一些基础事件的绑定
 * @date 2018/06/16/下午6:18
 */
public class ViewBindingAdapter {

    /**
     * 1.点击事件，不需要操作视图本身，只执行事件
     * @param view 点击的控件
     * @param clickCommand 事件处理
     */
    @BindingAdapter({"clickCommand"})
    public static void clickCommand(View view, final ViewCommandManager clickCommand) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickCommand != null) {
                    clickCommand.execute();
                }
            }
        });
    }

    /**
     * 2.点击事件，传递 view 本身，由使用者自己操作 view
     * @param view 点击的控件
     * @param clickCommond 事件处理
     */
    @BindingAdapter({"viewClick"})
    public static void viewClick(View view,final ViewCommandManager clickCommond){

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickCommond != null){
                    clickCommond.execute(view);
                }
            }
        });
    }

    @BindingAdapter({"itemClickCommand"})
    public static void itemClickCommand(View view, final ViewCommandManager clickCommand) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickCommand != null) {
                    clickCommand.execute(v.getId());
                }
            }
        });
    }

    @BindingAdapter({"requestFocus"})
    public static void requestFocusCommand(View view, final Boolean needRequestFocus) {
        if (needRequestFocus) {
            view.setFocusableInTouchMode(true);
            view.requestFocus();
        } else {
            view.clearFocus();
        }
    }

    @BindingAdapter({"onFocusChangeCommand"})
    public static void onFocusChangeCommand(View view, final ViewCommandManager<Boolean> onFocusChangeCommand) {
        view.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (onFocusChangeCommand != null) {
                    onFocusChangeCommand.execute(hasFocus);
                }
            }
        });
    }

    @BindingAdapter({"onTouchCommand"})
    public static void onTouchCommand(View view, final ViewResponseManager<MotionEvent, Boolean> onTouchCommand) {
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (onTouchCommand != null) {
                    return onTouchCommand.execute(event);
                }
                return false;
            }
        });
    }

    @BindingAdapter({"compoundButtonStateChanged"})
    public static void checkBoxStateChanged(CompoundButton compoundButton, final ViewCommandManager<Integer> stateChanged){
//        compoundButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (stateChanged != null){
//                    stateChanged.execute(compoundButton.getId(), isChecked);
//                }
//            }
//        });

        compoundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (stateChanged != null){
                    stateChanged.execute(compoundButton.getId());
                }
            }
        });
    }
}
