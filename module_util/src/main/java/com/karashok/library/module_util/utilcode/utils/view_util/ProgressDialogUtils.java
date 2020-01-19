package com.karashok.library.module_util.utilcode.utils.view_util;

import android.app.Activity;
import android.content.Context;
import android.os.Build;

import com.karashok.library.module_util.R;
import com.karashok.library.module_util.utilcode.utils.base_util.StringUtils;
import com.karashok.library.module_util.widget.CustomProgressDialog;


/**
 * @author Ralf(wanglixin)
 * DESCRIPTION
 * @name ProgressDialogUtils
 * @date 2018/06/04 下午1:56
 **/
public final class ProgressDialogUtils {

    private static CustomProgressDialog mProgressDialog;

    private static ProgressDialogUtils progressDialogUtils;

    private ProgressDialogUtils() {}

    public synchronized static ProgressDialogUtils getInstance() {
        if (progressDialogUtils == null) {
            progressDialogUtils = new ProgressDialogUtils();
        }
        return progressDialogUtils;
    }

    /**
     * 显示进度条对话框
     * @param context Context
     */
    public void showDialog(Context context) {
        showDialog(context, "");
    }

    public void showDialog(Context context, String tipText) {
        if (mProgressDialog == null) {
            mProgressDialog = new CustomProgressDialog(context, R.style.loading_dialog);
        }

        if (!StringUtils.isEmpty(tipText)) {
            mProgressDialog.setTipText(tipText);
        }

        mProgressDialog.show();
    }

    public static void setTipText(String text) {
        if (mProgressDialog != null && !StringUtils.isEmpty(text)) {
            mProgressDialog.setTipText(text);
        }
    }

    /**
     * 隐藏对话框
     */
    public static void hideDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.hide();
        }
    }

    /**
     * 销毁对话框
     */
    public static void dismissDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }

    private boolean isDestroyed() {
        if (mProgressDialog != null) {
            final Activity activity = mProgressDialog.getOwnerActivity();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                if (activity == null || activity.isDestroyed() || activity.isFinishing()) {
                    return true;
                }
            } else {
                if (activity == null || activity.isFinishing()) {
                    return true;
                }
            }
        }
        return false;
    }

}
