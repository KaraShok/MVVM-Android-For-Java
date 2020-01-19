package com.karashok.library.common.base;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;

import com.karashok.library.common.ui.widget.PermissionDialog;
import com.karashok.library.module_util.utilcode.utils.base_util.LogUtils;

import java.util.ArrayList;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * @author KaraShokZ(zhangyaozhong)
 * @name BasePermissionActivity
 * DESCRIPTION Activity权限申请基类
 * 特殊权限（SYSTEM_ALERT_WINDOW）的回调走的是：onActivityResult()且需要单独申请
 * @date 2018/06/16/下午6:18
 */
@RuntimePermissions
public abstract class BasePermissionActivity extends BaseActivity{

    private static String TAG = BasePermissionActivity.class.getSimpleName();

    private ArrayList<String> mPermissions = new ArrayList<>();

    @Override
    protected void onStart() {
        super.onStart();
//        BasePermissionActivityPermissionsDispatcher.needsPermissionAtyWithPermissionCheck(this);
    }

    protected abstract void permissionSuccess();

    protected abstract void permissionFailed();

    /**
     * 申请权限
     *
     * 拿到全部授权后会调用此方法
     */
    @NeedsPermission({Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.INTERNET,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.REQUEST_INSTALL_PACKAGES})
    protected void needsPermissionAty(){
        LogUtils.dTag(TAG, "needsPermissionAty: ");
        permissionSuccess();
    }

    /**
     * 权限被禁止
     *
     * 有权限此次授权被禁止会调用此方法
     */
    @OnPermissionDenied({Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.INTERNET,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.REQUEST_INSTALL_PACKAGES})
    protected void permissionDeniedAty() {
        LogUtils.dTag(TAG, "permissionDeniedAty: ");
        permissionFailed();
        // NOTE: Deal with a denied permission, e.g. by showing specific UI
        // or disabling certain functionality
    }

    /**
     * 权限说明
     *
     * 当有权限被禁止但可以再次询问时，下次会调用此方法展示说明，可以继续申请权限
     * @param request
     */
    @OnShowRationale({Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.INTERNET,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.REQUEST_INSTALL_PACKAGES})
    protected void showRationaleForPermissionAty(PermissionRequest request) {
        PermissionDialog.newInstance(getSupportFragmentManager(), request, new ArrayList<String>());
        // NOTE: Show a rationale to explain why the permission is needed, e.g. with a dialog.
        // Call proceed() or cancel() on the provided PermissionRequest to continue or abort
    }

    /**
     * 权限被禁止并不再询问
     *
     * 如果有被禁止的权限就会调用此方法
     */
    @OnNeverAskAgain({Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.INTERNET,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.REQUEST_INSTALL_PACKAGES})
    protected void permissionNeverAskAgainAty() {
        LogUtils.dTag(TAG, "permissionNeverAskAgainAty: ");
        permissionFailed();
    }

    @SuppressLint("NeedOnRequestPermissionsResult")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        BasePermissionActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
        LogUtils.dTag(TAG, "onRequestPermissionsResult: ");

        for (int i = 0, length = permissions.length; i < length; i++){
            if (grantResults[i] == PackageManager.PERMISSION_DENIED){
                mPermissions.add(permissions[i]);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
