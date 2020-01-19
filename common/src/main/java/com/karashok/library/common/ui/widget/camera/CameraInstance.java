package com.karashok.library.common.ui.widget.camera;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.os.Build;
import android.text.TextUtils;
import android.view.SurfaceHolder;

import com.karashok.library.module_util.utilcode.utils.base_util.LogUtils;
import com.karashok.library.module_util.utilcode.utils.base_util.StringUtils;
import com.karashok.library.module_util.utilcode.utils.base_util.file_util.FileUtils;
import com.karashok.library.module_util.utilcode.utils.base_util.picture_util.BitmapUtils;
import com.karashok.library.module_util.utilcode.utils.device_util.DeviceUtils;
import com.karashok.library.module_util.utilcode.utils.device_util.ScreenUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author KaraShokZ(zhangyaozhong)
 * @name CameraInstance
 * DESCRIPTION Camera实例
 * @date 2018/06/16/下午6:18
 */
public class CameraInstance {

    private static final String TAG = CameraInstance.class.getSimpleName();

    private PictureSaveCallBack mPictureSaveCallBack;

    private Camera mCamera;
    private Camera.Parameters mParams;
    private boolean isPreviewing = false;
    private float mPreviwRate = -1f;
    private int mCameraId = -1;
    private boolean isGoolgeFaceDetectOn = false;
    private static CameraInstance mCameraInstance;

    private CameraInstance() {

    }

    public static synchronized CameraInstance getInstance() {
        if (mCameraInstance == null) {
            mCameraInstance = new CameraInstance();
        }
        return mCameraInstance;
    }

    public interface CamOpenOverCallback {
        void cameraHasOpened();
    }

    public void setPictureSaveCallBack(PictureSaveCallBack pictureSaveCallBack) {
        this.mPictureSaveCallBack = pictureSaveCallBack;
    }

    public void doOpenCamera(CamOpenOverCallback callback, int cameraId) {
        LogUtils.iTag(TAG, "Camera open....");

        mCamera = Camera.open(cameraId);
        mCameraId = cameraId;
        if (callback != null) {
            callback.cameraHasOpened();
        }
    }

    public void doStartPreview(SurfaceHolder holder, float previewRate) {
        LogUtils.iTag(TAG, "doStartPreview...");
        if (isPreviewing) {
            //            mCamera.stopPreview();
            return;
        }
        if (mCamera != null) {

            mParams = mCamera.getParameters();
            mParams.setPictureFormat(PixelFormat.JPEG);
            CamParams.getInstance().printSupportPictureSize(mParams);
            CamParams.getInstance().printSupportPreviewSize(mParams);

            int screenWidth = ScreenUtils.getScreenWidth();

            Camera.Size pictureSize = CamParams.getInstance().getPropPictureSize(
                    mParams.getSupportedPictureSizes(), previewRate, screenWidth);
            mParams.setPictureSize(pictureSize.width, pictureSize.height);
            Camera.Size previewSize = CamParams.getInstance().getPropPreviewSize(
                    mParams.getSupportedPreviewSizes(), previewRate, screenWidth);

            mParams.setPreviewSize(previewSize.width, previewSize.height);

            mCamera.setDisplayOrientation(90);

            CamParams.getInstance().printSupportFocusMode(mParams);
            List<String> focusModes = mParams.getSupportedFocusModes();
            if (focusModes.contains("continuous-video")) {
                mParams.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
            }
            mCamera.setParameters(mParams);

            try {
                mCamera.setPreviewDisplay(holder);
                mCamera.startPreview();
            } catch (IOException e) {
                e.printStackTrace();
            }

            isPreviewing = true;
            mPreviwRate = previewRate;

            mParams = mCamera.getParameters();

            //            mCamera.setPreviewCallback(previewCallback);
        }
    }

    public void doStopCamera() {
        if (null != mCamera) {
            mCamera.setPreviewCallback(null);
            mCamera.stopPreview();
            isPreviewing = false;
            mPreviwRate = -1f;
            mCamera.release();
            mCamera = null;
        }
    }

    public void doTakePicture() {
        if (isPreviewing && (mCamera != null)) {
            try {
                mCamera.takePicture(mShutterCallback, null, mJpegPictureCallback);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Camera.Parameters getCameraParams() {
        if (mCamera != null) {
            mParams = mCamera.getParameters();
            return mParams;
        }
        return null;
    }

    public Camera getCameraDevice() {
        return mCamera;
    }


    public int getCameraId() {
        return mCameraId;
    }

    Camera.PreviewCallback previewCallback = new Camera.PreviewCallback() {
        @Override
        public void onPreviewFrame(byte[] data, Camera camera) {
            LogUtils.iTag(TAG, "previewCallback:onPreview...");

            Camera.Parameters parameters = camera.getParameters();
            int width = parameters.getPreviewSize().width;
            int height = parameters.getPreviewSize().height;

            //convert the byte[] to Bitmap through YuvImage;
            //make sure the previewFormat is NV21 (I set it so somewhere before)
            YuvImage yuv = new YuvImage(data, parameters.getPreviewFormat(), width, height, null);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            yuv.compressToJpeg(new Rect(0, 0, width, height), 70, out);

            Bitmap bitmap = BitmapFactory.decodeByteArray(out.toByteArray(), 0, out.size());
            if (bitmap != null) {
                //TODO preview
            }
        }
    };

    Camera.ShutterCallback mShutterCallback = new Camera.ShutterCallback() {
        @Override
        public void onShutter() {
            // TODO Auto-generated method stub
            LogUtils.iTag(TAG, "myShutterCallback:onShutter...");
        }
    };

    Camera.PictureCallback mRawCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            // TODO Auto-generated method stub
            LogUtils.iTag(TAG, "myRawCallback:onPictureTaken...");
        }
    };

    Camera.PictureCallback mJpegPictureCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            LogUtils.iTag(TAG, "myJpegCallback:onPictureTaken...");
            Bitmap bitmap = null;
            if (data != null) {
                bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);

                mCamera.stopPreview();
                isPreviewing = false;
            }

            if (bitmap != null) {
                int width = bitmap.getWidth() / 2;
                int height = bitmap.getHeight() / 2;
                bitmap = BitmapUtils.rotate(bitmap, 90, width, height);
                String defaultImageName = BitmapUtils.getDefaultImageName();
                String privateDirPath = FileUtils.getPrivateDirPath(defaultImageName);
                BitmapUtils.save(bitmap,privateDirPath, Bitmap.CompressFormat.JPEG);

                List<String> list = new ArrayList<>();
                list.add(privateDirPath);

                if (mPictureSaveCallBack != null){
                    mPictureSaveCallBack.pictureSave(list);
                }
            }

            mCamera.startPreview();
            isPreviewing = true;
        }
    };

    /**
     * 通过对比得到与宽高比最接近的预览尺寸（如果有相同尺寸，优先选择）
     *
     * @param isPortrait 是否竖屏
     * @param surfaceWidth 需要被进行对比的原宽
     * @param surfaceHeight 需要被进行对比的原高
     * @param preSizeList 需要对比的预览尺寸列表
     * @return 得到与原宽高比例最接近的尺寸
     */
    public static  Camera.Size getCloselyPreSize(boolean isPortrait, int surfaceWidth, int surfaceHeight, List<Camera.Size> preSizeList) {
        int reqTmpWidth;
        int reqTmpHeight;
        // 当屏幕为垂直的时候需要把宽高值进行调换，保证宽大于高
        if (isPortrait) {
            reqTmpWidth = surfaceHeight;
            reqTmpHeight = surfaceWidth;
        } else {
            reqTmpWidth = surfaceWidth;
            reqTmpHeight = surfaceHeight;
        }
        //先查找preview中是否存在与surfaceview相同宽高的尺寸
        for(Camera.Size size : preSizeList){
            if((size.width == reqTmpWidth) && (size.height == reqTmpHeight)){
                return size;
            }
        }

        // 得到与传入的宽高比最接近的size
        float reqRatio = ((float) reqTmpWidth) / reqTmpHeight;
        float curRatio, deltaRatio;
        float deltaRatioMin = Float.MAX_VALUE;
        Camera.Size retSize = null;
        for (Camera.Size size : preSizeList) {
            curRatio = ((float) size.width) / size.height;
            deltaRatio = Math.abs(reqRatio - curRatio);
            if (deltaRatio < deltaRatioMin) {
                deltaRatioMin = deltaRatio;
                retSize = size;
            }
        }

        return retSize;
    }

    /**
     * 是否前置摄像头
     */
    public boolean isFrontCamera() {
        return mCameraId == Camera.CameraInfo.CAMERA_FACING_FRONT;
    }

    /**
     * 是否支持前置摄像头
     */
    @SuppressLint("NewApi")
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public static boolean isSupportFrontCamera() {
        if (!DeviceUtils.hasGingerbread()) {
            return false;
        }
        int numberOfCameras = Camera.getNumberOfCameras();
        if (2 == numberOfCameras) {
            return true;
        }
        return false;
    }

    /**
     * 切换前置/后置摄像头
     */
    public void switchCamera(int cameraFacingFront) {
        switch (cameraFacingFront) {
            case Camera.CameraInfo.CAMERA_FACING_FRONT:
            case Camera.CameraInfo.CAMERA_FACING_BACK:
                mCameraId = cameraFacingFront;
                //                stopPreview();
                //                startPreview();
                break;
        }
    }

    /**
     * 切换前置/后置摄像头
     */
    public void switchCamera() {
        if (mCameraId == Camera.CameraInfo.CAMERA_FACING_BACK) {
            switchCamera(Camera.CameraInfo.CAMERA_FACING_FRONT);
        } else {
            switchCamera(Camera.CameraInfo.CAMERA_FACING_BACK);
        }
    }

    /**
     * 自动对焦
     *
     * @param cb
     * @return
     */
    public boolean autoFocus(Camera.AutoFocusCallback cb) {
        if (mCamera != null) {
            try {
                mCamera.cancelAutoFocus();

                if (mParams != null) {
                    String mode = getAutoFocusMode();
                    if (!StringUtils.isEmpty(mode)) {
                        mParams.setFocusMode(mode);
                        mCamera.setParameters(mParams);
                    }
                }
                mCamera.autoFocus(cb);
                return true;
            } catch (Exception e) {
                if (e != null){
                    LogUtils.eTag(TAG, "autoFocus", e);
                }
            }
        }
        return false;
    }

    /**
     * 检测是否支持指定特性
     */
    private boolean isSupported(List<String> list, String key) {
        return list != null && list.contains(key);
    }

    /**
     * 连续自动对焦
     */
    private String getAutoFocusMode() {
        if (mParams != null) {
            //持续对焦是指当场景发生变化时，相机会主动去调节焦距来达到被拍摄的物体始终是清晰的状态。
            List<String> focusModes = mParams.getSupportedFocusModes();
            if ((Build.MODEL.startsWith("GT-I950") || Build.MODEL.endsWith("SCH-I959") || Build.MODEL.endsWith("MEIZU MX3")) && isSupported(focusModes, "continuous-picture")) {
                return "continuous-picture";
            } else if (isSupported(focusModes, "continuous-video")) {
                return "continuous-video";
            } else if (isSupported(focusModes, "auto")) {
                return "auto";
            }
        }
        return null;
    }

    /**
     * 手动对焦
     *
     * @param focusAreas 对焦区域
     * @return
     */
    @SuppressLint("NewApi")
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public boolean manualFocus(Camera.AutoFocusCallback cb, List<Camera.Area> focusAreas) {
        if (mCamera != null && focusAreas != null && mParams != null && DeviceUtils.hasICS()) {
            try {
                mCamera.cancelAutoFocus();
                // getMaxNumFocusAreas检测设备是否支持
                if (mParams.getMaxNumFocusAreas() > 0) {
                    // mParameters.setFocusMode(Camera.Parameters.FOCUS_MODE_MACRO);//
                    // Macro(close-up) focus mode
                    mParams.setFocusAreas(focusAreas);
                }

                if (mParams.getMaxNumMeteringAreas() > 0)
                    mParams.setMeteringAreas(focusAreas);

                mParams.setFocusMode(Camera.Parameters.FOCUS_MODE_MACRO);
                mCamera.setParameters(mParams);
                mCamera.autoFocus(cb);
                return true;
            } catch (Exception e) {
                if (e != null){
                    LogUtils.eTag(TAG, "autoFocus", e);
                }
            }
        }
        return false;
    }

    /**
     * 切换闪关灯，默认关闭
     */
    public boolean toggleFlashMode() {
        if (mParams != null) {
            try {
                final String mode = mParams.getFlashMode();
                if (TextUtils.isEmpty(mode) ||
                        Camera.Parameters.FLASH_MODE_OFF.equals(mode)){
                    setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                } else{
                    setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                }
                return true;
            } catch (Exception e) {
                LogUtils.eTag(TAG, "toggleFlashMode", e);
            }
        }
        return false;
    }

    /**
     * 设置闪光灯
     *
     * @param value
     */
    private boolean setFlashMode(String value) {
        if (mParams != null && mCamera != null) {
            try {
                if (Camera.Parameters.FLASH_MODE_TORCH.equals(value) || Camera.Parameters.FLASH_MODE_OFF.equals(value)) {
                    mParams.setFlashMode(value);
                    mCamera.setParameters(mParams);
                }
                return true;
            } catch (Exception e) {
                LogUtils.eTag(TAG, "setFlashMode", e);
            }
        }
        return false;
    }

    public interface PictureSaveCallBack{
        void pictureSave(List<String> picturePaths);
    }
}
