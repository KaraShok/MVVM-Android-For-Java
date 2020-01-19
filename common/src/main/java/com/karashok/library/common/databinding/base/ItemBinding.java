package com.karashok.library.common.databinding.base;

import android.util.SparseArray;

import androidx.annotation.LayoutRes;
import androidx.databinding.ViewDataBinding;

import com.karashok.library.common.databinding.BindingUtils;


/**
 * @author KaraShokZ(zhangyaozhong)
 * @name ItemBinding
 * DESCRIPTION
 * Provides the necessary information to bind an item in a collection to a view.This includes the
 * variable id and thw layout as well as any extra bindings you may want to provide
 * @date 2018/06/16/下午6:18
 */
public class ItemBinding<T> {

    /**
     * Use this constant as the variable id to not bind the item in the collection to the layout if
     * no data is need, like a static footer or loading indicator.
     */
    public static final int VAR_NONE = 0;
    private static final int VAR_INVALID = -1;
    private static final int LAYOUT_NONE = 0;

    /**
     * Constructs an instance with the given variable id and layout.
     */
    public static <T> ItemBinding<T> of(int variableId, @LayoutRes int layoutRes){
        return new ItemBinding<T>(null).set(variableId,layoutRes);
    }

    /**
     * Constructs an instance with the given callback. It will be called for each item in the
     * collection to set the binding info.
     *
     * @see OnItemBind
     */
    public static <T> ItemBinding<T> of(OnItemBind<T> onItemBind){
        if (onItemBind == null){
            throw new NullPointerException("OnItemBind is null");
        }
        return new ItemBinding<>(onItemBind);
    }

    private final OnItemBind<T> mOnItemBind;
    private int mVariableId;
    @LayoutRes
    private int mLayoutRes;
    private SparseArray<Object> mExtraBindings;

    private ItemBinding(OnItemBind<T> onItemBind){
        mOnItemBind = onItemBind;
    }

    /**
     * Set the variable id and layout. This is normally called in {@link
     * OnItemBind#onItemBind(ItemBinding, int, Object)}.
     */
    public final ItemBinding<T> set(int variableId, @LayoutRes int layoytRes){
        mVariableId = variableId;
        mLayoutRes = layoytRes;
        return this;
    }

    /**
     * Set the variable id. This is normally called in {@link OnItemBind#onItemBind(ItemBinding,
     * int, Object)}.
     */
    public final ItemBinding<T> variableId(int variableId){
        mVariableId = variableId;
        return this;
    }

    /**
     * Set the layout. This is normally called in {@link OnItemBind#onItemBind(ItemBinding, int,
     * Object)}.
     */
    public final ItemBinding<T> layoutRes(@LayoutRes int layoutRes){
        mLayoutRes = layoutRes;
        return this;
    }

    /**
     * Bind an extra variable to the view with the given variable id. The same instance will be
     * provided to all views the binding is bound to.
     */
    public final ItemBinding<T> bindExtra(int variableId, Object value){
        if (mExtraBindings == null){
            mExtraBindings = new SparseArray<>(1);
        }
        mExtraBindings.put(variableId,value);
        return this;
    }

    /**
     * Clear all extra variables. This is normally called in {@link
     * OnItemBind#onItemBind(ItemBinding, int, Object)}.
     */
    public final ItemBinding<T> clearExtras(){
        if (mExtraBindings != null){
            mExtraBindings.clear();
        }
        return this;
    }

    /**
     * Remove an extra variable with the given variable id. This is normally called in {@link
     * OnItemBind#onItemBind(ItemBinding, int, Object)}.
     */
    public ItemBinding<T> removeExtra(int variableId){
        if (mExtraBindings != null){
            mExtraBindings.remove(variableId);
        }
        return this;
    }

    /**
     * Returns the current variable id of this binding.
     */
    public final int variableId(){
        return mVariableId;
    }

    /**
     * Returns the current layout fo this binding.
     */
    @LayoutRes
    public final int layoutRes(){
        return mLayoutRes;
    }

    /**
     * Returns the current extra binding for the given variable id or null if one isn't present.
     */
    public final Object extraBinding(int variableId){
        if (mExtraBindings == null){
            return null;
        }
        return mExtraBindings.get(variableId);
    }

    /**
     * Updates the state of the binding for the given item and position. This is called internally
     * by the binding collection adapters.
     */
    public void onItemBind(int position, T item){
        if (mOnItemBind != null){
            mVariableId = VAR_INVALID;
            mLayoutRes = LAYOUT_NONE;
            mOnItemBind.onItemBind(this, position, item);
            if (mVariableId == VAR_INVALID){
                throw new IllegalStateException("variavleId not set in onItemBind()");
            }
            if (mLayoutRes == LAYOUT_NONE){
                throw new IllegalStateException("layoutRes not set in onItemBind()");
            }
        }
    }

    public boolean bind(ViewDataBinding binding, T item){
        if (mVariableId == VAR_NONE){
            return false;
        }
        boolean result = binding.setVariable(mVariableId, item);
        if (!result){
            BindingUtils.throwMissingVariable(binding, mVariableId, mLayoutRes);
        }
        if (mExtraBindings != null){
            for (int i = 0, size = mExtraBindings.size(); i < size; i++){
                int variableId = mExtraBindings.keyAt(i);
                Object value = mExtraBindings.valueAt(i);
                if (variableId != VAR_NONE){
                    binding.setVariable(variableId, value);
                }
            }
        }
        return true;
    }
}
