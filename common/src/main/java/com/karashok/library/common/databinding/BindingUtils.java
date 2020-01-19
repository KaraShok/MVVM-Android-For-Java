package com.karashok.library.common.databinding;

import android.content.Context;
import android.content.res.Resources;
import android.os.Looper;

import androidx.annotation.LayoutRes;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

/**
 * @author KaraShokZ(zhangyaozhong)
 * @name BindingUtils
 * DESCRIPTION DataBinding的一些工具类
 * @date 2018/06/16/下午6:18
 */
public class BindingUtils {

    private static final String TAG = "BindingUtils";

    /**
     * Helper to throw an exception when {@link ViewDataBinding#setVariable(int,
     * Object)} returns false.
     */
    public static void throwMissingVariable(ViewDataBinding binding, int bindingVariable, @LayoutRes int layoutRes) {
        Context context = binding.getRoot().getContext();
        Resources resources = context.getResources();
        String layoutName = resources.getResourceName(layoutRes);
        String bindingVariableName = DataBindingUtil.convertBrIdToString(bindingVariable);
        throw new IllegalStateException("Could not bind variable '" + bindingVariableName + "' in layout '" + layoutName + "'");
    }

    /**
     * Ensures the call was made on the main thread. This is enforced for all ObservableList change
     * operations.
     */
    public static void ensureChangeOnMainThread() {
        if (Thread.currentThread() != Looper.getMainLooper().getThread()) {
            throw new IllegalStateException("You must only modify the ObservableList on the main thread.");
        }
    }

//    /**
//     * Constructs a binding adapter class from it's class name using reflection.
//     */
//    @SuppressWarnings("unchecked")
//    static <T, A extends BindingCollectionAdapter<T>> A createClass(Class<? extends BindingCollectionAdapter> adapterClass, ItemBinding<T> itemBinding) {
//        try {
//            return (A) adapterClass.getConstructor(ItemBinding.class).newInstance(itemBinding);
//        } catch (Exception e1) {
//            throw new RuntimeException(e1);
//        }
//    }
}
