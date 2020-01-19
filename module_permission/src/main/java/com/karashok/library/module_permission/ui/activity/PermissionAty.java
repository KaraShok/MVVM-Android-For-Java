package com.karashok.library.module_permission.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.callback.NavigationCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.karashok.library.module_permission.R;
import com.karashok.library.module_util.utilcode.utils.base_util.LogUtils;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class PermissionAty extends AppCompatActivity {

    @Autowired(name = "routerPath")
    String mRouterPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_permission);

        ARouter.getInstance().inject(PermissionAty.this);

//        PermissionAtyPermissionsDispatcher.requestAllPermWithPermissionCheck(PermissionAty.this);

    }

    @Override
    protected void onResume() {
        super.onResume();
//        Fresco.initialize(this);
        //        OpenFileUtils.init(this);
        //PermissionUtils.init(this.getApplication());
    }

    /**
     * 申请权限
     *
     * 拿到全部授权后会调用此方法
     */
    @NeedsPermission({
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.REQUEST_INSTALL_PACKAGES
    })
    protected void requestAllPerm(){
        ARouter.getInstance()
                .build("/Doc_SAMPLE/MainAty")
                .navigation(PermissionAty.this, new NavigationCallback() {
                    @Override
                    public void onFound(Postcard postcard) {

                    }

                    @Override
                    public void onLost(Postcard postcard) {

                    }

                    @Override
                    public void onArrival(Postcard postcard) {
                        PermissionAty.this.finish();
                    }

                    @Override
                    public void onInterrupt(Postcard postcard) {

                    }
                });
    }

    /**
     * 权限说明
     *
     * 当有权限被禁止但可以再次询问时，下次会调用此方法展示说明，可以继续申请权限
     * @param request
     */
    @OnShowRationale({
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.REQUEST_INSTALL_PACKAGES
    })
    protected void rationaleAllPerm(PermissionRequest request){
        showRationaleDialog(request);
    }

    /**
     * 权限被禁止
     *
     * 有权限此次授权被禁止会调用此方法
     */
    @OnPermissionDenied({
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.REQUEST_INSTALL_PACKAGES
    })
    protected void deniedPerm(){

    }

    /**
     * 权限被禁止并不再询问
     *
     * 如果有被禁止的权限就会调用此方法
     */
    @OnNeverAskAgain({
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.REQUEST_INSTALL_PACKAGES
    })
    protected void neverAskiAgain(){

    }

    @SuppressLint("NeedOnRequestPermissionsResult")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        PermissionAtyPermissionsDispatcher.onRequestPermissionsResult(PermissionAty.this, requestCode, grantResults);
        LogUtils.dTag("PermissionAty", "onRequestPermissionsResult: ");
        //        for (int i = 0, size = grantResults.length; i < size; i++){
        //            if (grantResults[i] == -1){
        //                mPermissions.add(permissions[i]);
        //            }
        //        }
    }

    private void showRationaleDialog(final PermissionRequest request) {
        new AlertDialog.Builder(this)
                .setPositiveButton("Allow", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(@NonNull DialogInterface dialog, int which) {
                        request.proceed();
                    }
                })
                .setNegativeButton("Deny", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(@NonNull DialogInterface dialog, int which) {
                        request.cancel();
                    }
                })
                .setCancelable(false)
                .setMessage("权限说明！")
                .show();
    }
}
