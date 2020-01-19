package com.karashok.library.common.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.karashok.library.module_util.utilcode.utils.Utils;


/**
 * @author KaraShokZ(zhangyaozhong)
 * @name EventManager
 * DESCRIPTION 事件总线的管理类
 * @date 2018/06/16/下午6:18
 */
public class EventManager {

    private static EventManager mEventManager;

    private static LocalBroadcastManager mLocalBroadcastManager;

    private EventManager(){
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(Utils.getApp());
    }

    private static EventManager initEventManager(){
        if (mEventManager == null){
            synchronized (EventManager.class){
                if (mEventManager == null){
                    mEventManager = new EventManager();
                }
            }
        }
        return mEventManager;
    }

    public static void registerBroadcast(LocalBroadcastReceiver receiver, IntentFilter filter){
        initEventManager();
        mLocalBroadcastManager.registerReceiver(receiver, filter);
    }

    public static void sendBroadcast(Intent intent){
        mLocalBroadcastManager.sendBroadcast(intent);
    }

    public static void sendBroadcastSync(Intent intent){
        mLocalBroadcastManager.sendBroadcastSync(intent);
    }

    public static void unregisterBroadcast(LocalBroadcastReceiver receiver){
        mLocalBroadcastManager.unregisterReceiver(receiver);
    }

    public static class LocalBroadcastReceiver extends BroadcastReceiver{

        private LocalBroadcastListener mListener;

        public LocalBroadcastReceiver(LocalBroadcastListener listener) {
            mListener = listener;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            if (mListener != null){
                mListener.broadcastListener(intent);
            }
        }
    }

    public interface LocalBroadcastListener{
        void broadcastListener(Intent intent);
    }

}
