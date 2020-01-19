package com.karashok.library.common.base;

import android.Manifest;
import android.annotation.SuppressLint;

import androidx.annotation.NonNull;

import com.karashok.library.common.ui.widget.PermissionDialog;

import java.util.ArrayList;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * @author KaraShokZ(zhangyaozhong)
 * @name BasePermissionFragment
 * DESCRIPTION Fragment权限申请基类
 * 特殊权限（SYSTEM_ALERT_WINDOW）的回调走的是：onActivityResult()且需要单独申请
 * @date 2018/06/16/下午6:18
 */
@RuntimePermissions
abstract class BasePermissionFragment extends BaseFragment {

    @Override
    public void onStart() {
        super.onStart();
//        BasePermissionFragmentPermissionsDispatcher.needsPermissionFgtWithPermissionCheck(this);
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
    protected void needsPermissionFgt(){
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
    protected void permissionDeniedFgt() {
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
    protected void showRationaleForPermissionFgt(PermissionRequest request) {
        PermissionDialog.newInstance(getFragmentManager(), request, new ArrayList<String>());
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
    protected void permissionNeverAskAgainFgt() {
        permissionFailed();
    }

    @SuppressLint("NeedOnRequestPermissionsResult")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        BasePermissionFragmentPermissionsDispatcher.onRequestPermissionsResult(this,requestCode,grantResults);
    }


}
