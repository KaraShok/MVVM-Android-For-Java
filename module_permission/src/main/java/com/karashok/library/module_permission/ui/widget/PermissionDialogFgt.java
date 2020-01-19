package com.karashok.library.module_permission.ui.widget;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.karashok.library.common.base.BaseActivity;
import com.karashok.library.module_permission.R;
import com.karashok.library.module_util.utilcode.utils.base_util.LogUtils;

import java.util.ArrayList;
import java.util.List;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * @author KaraShokZ(zhangyaozhong)
 * @name PermissionDialogFgt
 * DESCRIPTION 正方形的ImageView
 * @date 2018/06/16/下午6:18
 */
@RuntimePermissions
public class PermissionDialogFgt extends DialogFragment {

    private static final String[] PERMISSIONS = new String[]{
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.REQUEST_INSTALL_PACKAGES
    };

    private List<String> mPermissions = new ArrayList<>();

    /**
     * 创建权限申请Dialog
     * @param activity 依附的Activity
     * @param permType 申请权限的类型：0、全部；1、网络；2、读写；3、相机；4、手机状态；5、安装；
     */
    public static void requestPerm(BaseActivity activity, int permType){
        PermissionDialogFgt permissionDialogFgt = new PermissionDialogFgt();

        Bundle bundle = new Bundle();
        bundle.putInt("permType", permType);

        permissionDialogFgt.setArguments(bundle);

        permissionDialogFgt.show(activity.getSupportFragmentManager(), PermissionDialogFgt.class.getSimpleName());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fgt_permission_dlg, container, false);
        return inflate;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initDialog();
        initPerm();
    }

    private void initDialog(){
        Dialog dialog = getDialog();

        dialog.setCanceledOnTouchOutside(false);
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent keyEvent) {
                if (PermissionDialogFgt.this.isVisible()) {

                }
                return true;
            }
        });
    }

    /**
     * 申请权限的类型：0、全部；1、网络；2、读写；3、相机；4、手机状态；5、安装；
     */
    private void initPerm(){
        Bundle arguments = getArguments();
        if (arguments != null){
//            int permType = arguments.getInt("permType", 0);
//            if (permType == 0){
//                PermissionDialogFgtPermissionsDispatcher.requestAllPermWithPermissionCheck(PermissionDialogFgt.this);
//            }else if (permType == 1){
//                PermissionDialogFgtPermissionsDispatcher.requestNetWorkPermWithPermissionCheck(PermissionDialogFgt.this);
//            }else if (permType == 2){
//                PermissionDialogFgtPermissionsDispatcher.requestStoragePermWithPermissionCheck(PermissionDialogFgt.this);
//            }else if (permType == 3){
//                PermissionDialogFgtPermissionsDispatcher.requestCameraPermWithPermissionCheck(PermissionDialogFgt.this);
//            }else if (permType == 4){
//                PermissionDialogFgtPermissionsDispatcher.requestPhoneStatePermWithPermissionCheck(PermissionDialogFgt.this);
//            }else if (permType == 5){
//                PermissionDialogFgtPermissionsDispatcher.requestInstallPermWithPermissionCheck(PermissionDialogFgt.this);
//            }else {
//                PermissionDialogFgtPermissionsDispatcher.requestAllPermWithPermissionCheck(PermissionDialogFgt.this);
//            }
        }
    }

    /**
     * 申请权限
     *
     * 拿到全部授权后会调用此方法
     */
    @NeedsPermission({
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.ACCESS_WIFI_STATE
    })
    protected void requestNetWorkPerm(){
        PermissionDialogFgt.this.dismiss();
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
            Manifest.permission.ACCESS_WIFI_STATE
    })
    protected void rationaleNetWorkPerm(PermissionRequest request){

    }

    /**
     * 申请权限
     *
     * 拿到全部授权后会调用此方法
     */
    @NeedsPermission({
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    })
    protected void requestStoragePerm(){
        PermissionDialogFgt.this.dismiss();
    }

    /**
     * 权限说明
     *
     * 当有权限被禁止但可以再次询问时，下次会调用此方法展示说明，可以继续申请权限
     * @param request
     */
    @OnShowRationale({
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    })
    protected void rationaleStoragePerm(PermissionRequest request){

    }

    /**
     * 申请权限
     *
     * 拿到全部授权后会调用此方法
     */
    @NeedsPermission({
            Manifest.permission.CAMERA
    })
    protected void requestCameraPerm(){
        PermissionDialogFgt.this.dismiss();
    }

    /**
     * 权限说明
     *
     * 当有权限被禁止但可以再次询问时，下次会调用此方法展示说明，可以继续申请权限
     * @param request
     */
    @OnShowRationale({
            Manifest.permission.CAMERA
    })
    protected void rationaleCameraPerm(PermissionRequest request){

    }

    /**
     * 申请权限
     *
     * 拿到全部授权后会调用此方法
     */
    @NeedsPermission({
            Manifest.permission.READ_PHONE_STATE
    })
    protected void requestPhoneStatePerm(){
        PermissionDialogFgt.this.dismiss();
    }

    /**
     * 权限说明
     *
     * 当有权限被禁止但可以再次询问时，下次会调用此方法展示说明，可以继续申请权限
     * @param request
     */
    @OnShowRationale({
            Manifest.permission.READ_PHONE_STATE
    })
    protected void rationalePhoneStatePerm(PermissionRequest request){

    }

    /**
     * 申请权限
     *
     * 拿到全部授权后会调用此方法
     */
    @NeedsPermission({
            Manifest.permission.REQUEST_INSTALL_PACKAGES
    })
    protected void requestInstallPerm(){
        PermissionDialogFgt.this.dismiss();
    }

    /**
     * 权限说明
     *
     * 当有权限被禁止但可以再次询问时，下次会调用此方法展示说明，可以继续申请权限
     * @param request
     */
    @OnShowRationale({
            Manifest.permission.REQUEST_INSTALL_PACKAGES
    })
    protected void rationaleInstallPerm(PermissionRequest request){

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
//        PermissionDialogFgt.this.dismiss();
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
        LogUtils.dTag("PermissionDialogFgt", "rationaleAllPerm: " + mPermissions.toString());
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
//        PermissionDialogFgtPermissionsDispatcher.onRequestPermissionsResult(PermissionDialogFgt.this, requestCode, grantResults);
        for (int i = 0, size = grantResults.length; i < size; i++){
            if (grantResults[i] == -1){
                mPermissions.add(permissions[i]);
            }
        }
    }

    private void showRationaleDialog(final PermissionRequest request) {
        new AlertDialog.Builder(getContext())
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
