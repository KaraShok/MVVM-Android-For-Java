package com.karashok.library.common.ui.widget.camera;

import android.hardware.Camera;

import com.karashok.library.module_util.utilcode.utils.base_util.LogUtils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author KaraShokZ(zhangyaozhong)
 * @name CamParams
 * DESCRIPTION Camera的参数配置
 * @date 2018/06/16/下午6:18
 */
public class CamParams {

    private static final String TAG = CamParams.class.getSimpleName();

    private CameraSizeComparator sizeComparator = new CameraSizeComparator();
    private static CamParams myCamPara = null;

    private CamParams(){

    }

    public static CamParams getInstance(){
        if(myCamPara == null){
            myCamPara = new CamParams();
            return myCamPara;
        }
        else{
            return myCamPara;
        }
    }

    public Camera.Size getPropPreviewSize(List<Camera.Size> list, float th, int minWidth){
        Collections.sort(list, sizeComparator);

        int i = 0;
        for(Camera.Size s:list){
            if((s.width >= minWidth) && equalRate(s, th)){
                LogUtils.iTag(TAG, "PreviewSize:w = " + s.width + "h = " + s.height);
                break;
            }
            i++;
        }
        if(i == list.size()){
            i = 0;
        }
        return list.get(i);
    }

    public Camera.Size getPropPictureSize(List<Camera.Size> list, float th, int minWidth){
        Collections.sort(list, sizeComparator);

        int i = 0;
        for(Camera.Size s:list){
            if((s.width >= minWidth) && equalRate(s, th)){
                LogUtils.iTag(TAG, "PictureSize : w = " + s.width + "h = " + s.height);
                break;
            }
            i++;
        }
        if(i == list.size()){
            i = 0;
        }
        return list.get(i);
    }

    public boolean equalRate(Camera.Size s, float rate){
        float r = (float)(s.width)/(float)(s.height);
        if(Math.abs(r - rate) <= 0.03)
        {
            return true;
        }
        else{
            return false;
        }
    }

    public  class CameraSizeComparator implements Comparator<Camera.Size> {
        public int compare(Camera.Size lhs, Camera.Size rhs) {
            // TODO Auto-generated method stub
            if(lhs.width == rhs.width){
                return 0;
            }
            else if(lhs.width > rhs.width){
                return 1;
            }
            else{
                return -1;
            }
        }

    }

    /**
     * @param params
     */
    public  void printSupportPreviewSize(Camera.Parameters params){
        List<Camera.Size> previewSizes = params.getSupportedPreviewSizes();
        for(int i=0; i< previewSizes.size(); i++){
            Camera.Size size = previewSizes.get(i);
            LogUtils.iTag(TAG, "previewSizes:width = "+size.width+" height = "+size.height);
        }

    }

    /**
     * @param params
     */
    public  void printSupportPictureSize(Camera.Parameters params){
        List<Camera.Size> pictureSizes = params.getSupportedPictureSizes();
        for(int i=0; i< pictureSizes.size(); i++){
            Camera.Size size = pictureSizes.get(i);
            LogUtils.iTag(TAG, "pictureSizes:width = "+ size.width
                    +" height = " + size.height);
        }
    }
    /**
     * @param params
     */
    public void printSupportFocusMode(Camera.Parameters params){
        List<String> focusModes = params.getSupportedFocusModes();
        for(String mode : focusModes){
            LogUtils.iTag(TAG, "focusModes--" + mode);
        }
    }
}
