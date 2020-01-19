package com.karashok.library.module_permission.ui.fragment;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.karashok.library.module_permission.ModulePermissionContants;
import com.karashok.library.module_permission.R;
import com.karashok.library.module_util.utilcode.utils.Utils;
import com.karashok.library.module_util.utilcode.utils.device_util.ScreenUtils;
import com.karashok.library.module_util.utilcode.utils.view_util.StatusBarUtils;
import com.karashok.library.module_util.utilcode.utils.view_util.ToastUtils;

import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * @author karasjoker(zhangyaozhong)
 * @name PermissionFragment
 * DESCRIPTION
 * @date 2018/04/08/下午3:12
 */

public class PermissionFragment extends BottomSheetDialogFragment implements EasyPermissions.PermissionCallbacks {

    //权限回调的标示
    private static final int RC = 0x0100;
    private TransStatusBottomSheetDialog mTransStatusBottomSheetDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //PermissionUtils.init(getActivity().getApplication());
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mTransStatusBottomSheetDialog = new TransStatusBottomSheetDialog(getContext());
        return mTransStatusBottomSheetDialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        //获取布局中的控件
        View root = inflater.inflate(R.layout.fragment_permission, container, false);
        Button btnSubmit = root.findViewById(R.id.btn_permission_fragment_submit);
        //点击申请权限
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //                //申请权限
                //                requestPerm();
            }
        });
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        //界面显示的时候进行刷新
        refreshState(getView());
    }

    /**
     * 刷新我们布局中的图片的状态
     *
     * @param root 根布局
     */
    private void refreshState(View root) {
        if (root == null) {
            return;
        }

        Context context = getContext();
        root.findViewById(R.id.iv_permission_fragment_network_state)
                .setVisibility(haveNetworkPerm(context) ? View.VISIBLE : View.GONE);

        root.findViewById(R.id.iv_permission_fragment_read_sdcard_state)
                .setVisibility(haveReadAndWriteSdCardPerm(context) ? View.VISIBLE : View.GONE);

        root.findViewById(R.id.iv_permission_fragment_camera_state)
                .setVisibility(haveCameraPerm(context) ? View.VISIBLE : View.GONE);

        root.findViewById(R.id.iv_permission_fragment_record_audio_state)
                .setVisibility(haveRecordAudioPerm(context) ? View.VISIBLE : View.GONE);

    }

    /**
     * 是否有网络权限
     *
     * @param context
     * @return 根据检查结果返回，true代表有
     */
    public static boolean haveNetworkPerm(Context context) {
        String[] perms = new String[]{
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.ACCESS_WIFI_STATE
        };
        return EasyPermissions.hasPermissions(context, perms);
    }

    /**
     * 请求读写sdCard权限的方法
     */
    public static void requestNetworkPerm(Context context) {
        String[] perms = new String[]{
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.ACCESS_WIFI_STATE
        };

        EasyPermissions.requestPermissions((Activity) context, ModulePermissionContants.EXPLAIN_SETTING_NETWORK_PERM, RC, perms);

    }

    /**
     * 是否有读写sd卡的权限
     *
     * @param context
     * @return 根据检查结果返回，true代表有
     */
    public static boolean haveReadAndWriteSdCardPerm(Context context) {
        String[] perms = new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
        return EasyPermissions.hasPermissions(context, perms);
    }

    /**
     * 请求读写sdCard权限的方法
     */
    public static void requestReadAndWriteSdCardPerm(Context context) {
        String[] perms = new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };

        EasyPermissions.requestPermissions((Activity) context, ModulePermissionContants.EXPLAIN_SETTING_PHOTO_PERM, RC, perms);

    }

    /**
     * 是否有使用相机的权限
     *
     * @param context
     * @return 根据检查结果返回，true代表有
     */
    public static boolean haveCameraPerm(Context context) {
        String[] perms = new String[]{
                Manifest.permission.CAMERA
        };
        return EasyPermissions.hasPermissions(context, perms);
    }

    /**
     * 请求照相机权限的方法
     */
    public static void requestCameraPerm(Context context) {
        String[] perms = new String[]{
                Manifest.permission.CAMERA
        };

        EasyPermissions.requestPermissions((Activity) context, ModulePermissionContants.EXPLAIN_SETTING_CAMERA_PERM, RC, perms);

    }

    /**
     * 是否有录音权限
     *
     * @param context
     * @return 根据检查结果返回，true代表有
     */
    public static boolean haveRecordAudioPerm(Context context) {
        String[] perms = new String[]{
                Manifest.permission.RECORD_AUDIO
        };
        return EasyPermissions.hasPermissions(context, perms);
    }

    /**
     * 请求录音权限的方法
     */
    public static void requestRecordAudioPerm(Context context) {
        String[] perms = new String[]{
                Manifest.permission.RECORD_AUDIO
        };

        EasyPermissions.requestPermissions((Activity) context, ModulePermissionContants.EXPLAIN_SETTING_RECORD_AUDIO_PERM, RC, perms);

    }

    /**
     * 是否有获取手机状态的权限
     *
     * @param context
     * @return 根据检查结果返回，true代表有
     */
    public static boolean haveReadPhoneStatePerm(Context context) {
        String[] perms = new String[]{
                Manifest.permission.READ_PHONE_STATE
        };
        return EasyPermissions.hasPermissions(context, perms);
    }

    /**
     * 请求获取手机状态的权限的方法
     */
    public static void requestReadPhoneStatePerm(Context context) {
        String[] perms = new String[]{
                Manifest.permission.READ_PHONE_STATE
        };

        EasyPermissions.requestPermissions((Activity) context, ModulePermissionContants.EXPLAIN_SETTING_STATE_PERM, RC, perms);

    }


    //私有的show方法，
    private static void show(FragmentManager fragmentManager) {
        //调用BottomSheetDialogFragment已经准备好的show方法
        new PermissionFragment()
                .show(fragmentManager, PermissionFragment.class.getName());
    }

    /**
     * 检查是否具有所有的权限
     *
     * @param context
     * @param fragmentManager
     * @return 是否具有所有权限
     */
    public static boolean haveAll(Context context, FragmentManager fragmentManager) {
        //检查是否具有所有有的权限
        boolean haveAll = haveNetworkPerm(context)
                && haveReadAndWriteSdCardPerm(context)
                && haveCameraPerm(context)
                && haveRecordAudioPerm(context);

        //        //如果没有则显示当前申请权限的界面
        if (!haveAll) {
            //            show(fragmentManager);
            requestPerm(context);
        }

        return haveAll;
    }

    /**
     * 申请权限的方法
     */
    @AfterPermissionGranted(RC)
    public static void requestPerm(Context context) {
        String[] perms = new String[]{
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.INTERNET,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.REQUEST_INSTALL_PACKAGES
        };

        if (EasyPermissions.hasPermissions(Utils.getApp(), perms)) {
            ToastUtils.showShort("授权成功");
            //fragment中调用getview得到根布局，前提是在oncreatview之后。使用该方法
            //            refreshState(getView());
            //            mTransStatusBottomSheetDialog.dismiss();
        } else {
            EasyPermissions.requestPermissions((Activity) context, ModulePermissionContants.EXPLAIN_SETTING_APP_PERM, RC, perms);
            //            mTransStatusBottomSheetDialog.dismiss();
        }
    }

    //EasyPermissions.PermissionCallbacks回调方法
    //申请成功的回调
    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    //EasyPermissions.PermissionCallbacks回调方法
    //申请失败的回调
    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        //如果权限有没有申请成功的权限存在，则弹出弹出框，用户点击后到设置界面自己打开权限
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog
                    .Builder(this)
                    .build()
                    .show();
        }

    }

    /**
     * fragment的重写方法，权限申请的时候回调的方法，在这个方法中，把对应的权限申请状态交给EasyPermissions框架
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //传递对应的参数，并且告知接收处理权限的处理者是我自己
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    /**
     * 当用户在第一次申请权限时，点击了某些权限的取消，那么在第二次及以后需要这个权限的场景下，
     * 由于系统原因，很多系统不再询问用户是否再次授权，通过这个方法，可进入app设置页面进行手动授权
     * 但是目前Android手机有很多经过订制以后，通过权限管理设置的权限和通过应用详情设置的权限不统一，
     * 因此这个方法不能保证所有手机都能完美完成权限的再次申请
     *
     * @param permissions  当前申请的全部权限
     * @param grantResults 是否申请成功返回的对应状态码
     * @param activity     申请以上权限的activity
     * @param explainPerm  给予用户的解释说明文字对应的资源id
     */
    public static void showRequestPermSettingAgain(@NonNull String[] permissions, @NonNull int[] grantResults, final Activity activity, int explainPerm) {
        for (int i = 0; i < permissions.length; i++) {
            String perm = permissions[i];
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                if (!ActivityCompat.shouldShowRequestPermissionRationale(activity, perm)) {

                    AlertDialog alertDialog = new AlertDialog.Builder(activity)
                            .setTitle(explainPerm)
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                    Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
                                    intent.setData(uri);
                                    activity.startActivityForResult(intent, -1);
                                    dialogInterface.dismiss();
                                }
                            }).setNegativeButton("取消", null)
                            .show();
                }
                return;
            }

        }
    }

    /**
     * 当用户在第一次申请权限时，点击了某些权限的取消，那么在第二次及以后需要这个权限的场景下，
     * 由于系统原因，很多系统不再询问用户是否再次授权，通过这个方法，可以指导用户自己去权限管理下进行设置
     *
     * @param permissions  当前申请的全部权限
     * @param grantResults 是否申请成功返回的对应状态码
     * @param activity     申请以上权限的activity
     * @param explainPerm  给予用户的解释说明文字对应的资源id
     */
    public static void showRequestPermTextAgain(@NonNull String[] permissions, @NonNull int[] grantResults, final Activity activity, String explainPerm) {
        for (int i = 0; i < permissions.length; i++) {
            String perm = permissions[i];
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                if (!ActivityCompat.shouldShowRequestPermissionRationale(activity, perm)) {
                    AlertDialog alertDialog = new AlertDialog.Builder(activity)
                            .setTitle("提示")
                            .setMessage(explainPerm)
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            }).show();
                }
                return;
            }
        }
    }

    /**
     * 为了解决顶部状态栏变黑而写的透明的dialog
     */
    public static class TransStatusBottomSheetDialog extends BottomSheetDialog {

        public TransStatusBottomSheetDialog(@NonNull Context context) {
            super(context);
        }

        public TransStatusBottomSheetDialog(@NonNull Context context, @StyleRes int theme) {
            super(context, theme);
        }

        protected TransStatusBottomSheetDialog(@NonNull Context context, boolean cancelable, DialogInterface.OnCancelListener cancelListener) {
            super(context, cancelable, cancelListener);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            final Window window = getWindow();
            if (window == null) {
                return;
            }

            //得到屏幕的高度
            int screenHeight = ScreenUtils.getScreenHeight();
            //得到状态栏的高度
            int statusHeight = StatusBarUtils.getStatusBarHeight();
            //计算dialog的高度并设置
            int dialogHeight = screenHeight - statusHeight;
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                    dialogHeight <= 0 ? ViewGroup.LayoutParams.MATCH_PARENT : dialogHeight);
        }
    }
}
