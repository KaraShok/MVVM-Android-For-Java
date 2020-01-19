package com.karashok.library.common.base;

import androidx.annotation.NonNull;

import com.karashok.library.common.net.DataCallBack;
import com.karashok.library.common.net.RetrofitClient;
import com.karashok.library.module_util.utilcode.utils.base_util.SPUtils;
import com.karashok.library.module_util.utilcode.utils.base_util.StringUtils;
import com.karashok.library.module_util.utilcode.utils.base_util.file_util.FileIOUtils;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;

import java.io.File;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author KaraShokZ(zhangyaozhong)
 * @name DataManager
 * DESCRIPTION Model管理者，用来管理Data的来源(Net、Shared Preference、DB、Disk)
 * @date 2018/06/16/下午6:18
 */
public abstract class DataManager{

    protected BaseActivity mActivity;
    protected BaseFragment mFragment;

    /**
     * 网络请求实例
     */
    protected RetrofitClient mRetrofitClient;

    /**
     * Shared Preference实例
     */
    protected SPUtils mPreferenceUtils;

    /**
     * 数据回调实例
     */
    protected DataCallBack mDataCallBack;

    /**
     * 父类没有初始化任何数据获取工具，子类必须init，否则将会NullPointException
     * @param dataCallBack
     */
    public DataManager(DataCallBack dataCallBack) {
        this.mDataCallBack = dataCallBack;
    }

    /**
     * 绑定Activity
     * @param activity
     */
    public void bindActivity(@NonNull BaseActivity activity){
        mActivity = activity;
    }

    /**
     * 绑定Fragment
     * @param fragment
     */
    public void bindFragment(@NonNull BaseFragment fragment){
        mFragment = fragment;
    }

    /**
     * 初始化网络访问实体
     */
    protected void initNetWork(){
        mRetrofitClient = new RetrofitClient();
    }

    /**
     * 绑定Rx生命周期（销毁时取消事件流）
     * @param observable
     * @param <T>
     * @return
     */
    protected <T> Observable<T> initNetProcess(Observable<T> observable){
        Observable<T> processObservable = null;
        if (mActivity != null){
            processObservable = observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .compose(mActivity.bindUntilEvent(ActivityEvent.DESTROY));
        }else if(mFragment != null){
            processObservable = observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .compose(mFragment.bindUntilEvent(FragmentEvent.DESTROY_VIEW));
        }else {
            processObservable = observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }
        return processObservable;
    }

    /**
     * 初始化Shared Preference管理类
     * @param sharedPreferName Shared Preference的文件名
     */
    protected void initSharedPrefer(String sharedPreferName){
        if (StringUtils.isEmpty(sharedPreferName)){
            mPreferenceUtils = SPUtils.getInstance();
        }else {
            mPreferenceUtils = SPUtils.getInstance(sharedPreferName);
        }
    }

    /**
     * 将字符串写入文件
     *
     * @param file    文件
     * @param content 写入内容
     * @param append  是否追加在文件末
     * @return {@code true}: 写入成功<br>{@code false}: 写入失败
     */
    public boolean writeFileFromString(File file,
                                       String content,
                                       boolean append){
        return FileIOUtils.writeFileFromString(file, content, append);
    }

    /**
     * 读取文件到字符串中
     *
     * @param file        文件
     * @param charsetName 编码格式
     * @return 字符串(默认填null)
     */
    public String readFile2String(File file, String charsetName){
        return FileIOUtils.readFile2String(file, charsetName);
    }

    /**
     * 清理回调
     */
    protected void onCleared(){
        mActivity = null;
        mFragment = null;
        mRetrofitClient = null;
        mPreferenceUtils = null;
        mDataCallBack = null;
    }
}
