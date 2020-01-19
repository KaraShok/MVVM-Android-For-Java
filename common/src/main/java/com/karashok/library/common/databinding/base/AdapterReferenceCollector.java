package com.karashok.library.common.databinding.base;

import androidx.databinding.ObservableList;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

/**
 * @author KaraShokZ(zhangyaozhong)
 * @name AdapterReferenceCollector
 * DESCRIPTION Adapter绑定的管理类
 * @date 2018/06/16/下午6:18
 */
public class AdapterReferenceCollector {

    static final ReferenceQueue<Object> QUEUE = new ReferenceQueue<>();
    static PollReferenceThread mThread;

    public static <T, A extends BindingCollection<T>>WeakReference<A> createRef(A adapter,
                                                                         ObservableList<T> items,
                                                                         ObservableList.OnListChangedCallback changedCallback){
        if (mThread == null || !mThread.isAlive()){
            mThread = new PollReferenceThread();
            mThread.start();
        }
        return new AdapterRef<>(adapter,items,changedCallback);
    }

    private static class PollReferenceThread extends Thread{

        @Override
        public void run() {
            while (true){
                try {
                    Reference<?> ref = QUEUE.remove();
                    if (ref instanceof AdapterRef){
                        ((AdapterRef)ref).unregister();
                    }
                }catch (InterruptedException e){
                    break;
                }
            }
        }
    }

    static class AdapterRef<T, A extends BindingCollection<T>> extends WeakReference<A>{

        private final ObservableList<T> mItems;
        private final ObservableList.OnListChangedCallback mChangedCallback;

        public AdapterRef(A referent, ObservableList<T> items, ObservableList.OnListChangedCallback changedCallback) {
            super(referent);
            mItems = items;
            mChangedCallback = changedCallback;
        }

        void unregister(){
            mItems.removeOnListChangedCallback(mChangedCallback);
        }
    }
}
