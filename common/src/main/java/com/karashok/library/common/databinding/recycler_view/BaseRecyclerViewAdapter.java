package com.karashok.library.common.databinding.recycler_view;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableList;
import androidx.databinding.OnRebindCallback;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;


import com.karashok.library.common.databinding.BindingUtils;
import com.karashok.library.common.databinding.base.AdapterReferenceCollector;
import com.karashok.library.common.databinding.base.BindingCollection;
import com.karashok.library.common.databinding.base.ItemBinding;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * @author KaraShokZ(zhangyaozhong)
 * @name BaseRecyclerViewAdapter
 * DESCRIPTION
 * A {@link RecyclerView.Adapter} that binds items to layouts using the given {@link ItemBinding}.
 * If you give it an {@link ObservableList} it will also updated itself based on changes to that list.
 * @date 2018/06/16/下午6:18
 */
public class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements BindingCollection<T> {

    private static final Object DATA_INVALIDATION = new Object();

    private ItemBinding<T> mItemBinding;
    private WeakReferenceOnListChangedCallBack<T> mListChangedCallBack;
    private List<T> mItems;
    private LayoutInflater mInflater;
    private ItemIds<? super T> mItemIds;
    private ViewHolderFactory mViewHolderFactory;

    // Currently attached RecyclerView, we don't have to listen to notifications if null.
    @Nullable
    private RecyclerView mRecyclerView;

    //------------------------------RecyclerView.Adapter--------------------------------

    @Override
    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mInflater == null){
            mInflater = LayoutInflater.from(parent.getContext());
        }
        ViewDataBinding binding = initBinding(mInflater, viewType, parent);
        RecyclerView.ViewHolder holder = initHolder(binding);
        binding.addOnRebindCallback(new OnRebindCallback() {
            @Override
            public boolean onPreBind(ViewDataBinding binding) {
                return mRecyclerView != null && mRecyclerView.isComputingLayout();
            }

            @Override
            public void onCanceled(ViewDataBinding binding) {
                if (mRecyclerView == null || mRecyclerView.isComputingLayout()){
                    return;
                }
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION){
                    notifyItemChanged(adapterPosition, DATA_INVALIDATION);
                }
            }
        });
        return holder;
    }

    /**
     * Constructs a view holder for the given databinding. The default implementation is to use
     * {@link ViewHolderFactory} if provided, otherwise use a default view holder.
     */
    private RecyclerView.ViewHolder initHolder(ViewDataBinding binding){
        if (mViewHolderFactory != null){
            return mViewHolderFactory.createViewHolder(binding);
        }else {
            return new BindingViewHolder(binding);
        }
    }

    public interface ViewHolderFactory{
        RecyclerView.ViewHolder createViewHolder(ViewDataBinding binding);
    }

    /**
     * Set the factory for creating view holders. If null, a default view holder will be used. This
     * is useful for holding custom state in the view holder or other more complex customization.
     */
    public void setViewHolderFactory(@Nullable ViewHolderFactory viewHolderFactory){
        mViewHolderFactory = viewHolderFactory;
    }

    private class BindingViewHolder extends RecyclerView.ViewHolder{

        public BindingViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
        }
    }

    @Override
    public final void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        T item = mItems.get(position);
        ViewDataBinding binding = DataBindingUtil.getBinding(holder.itemView);
        onBindBinding(binding, mItemBinding.variableId(), mItemBinding.layoutRes(), position, item);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, List<Object> payloads) {
        if (isForDataBinding(payloads)){
            ViewDataBinding binding = DataBindingUtil.getBinding(holder.itemView);
            binding.executePendingBindings();
        }else {
            super.onBindViewHolder(holder, position, payloads);
        }
    }

    private boolean isForDataBinding(List<Object> payloads){
        if (payloads == null || payloads.size() == 0){
            return false;
        }
        for (int i = 0, size = payloads.size(); i < size; i++){
            Object obj = payloads.get(i);
            if (obj != DATA_INVALIDATION){
                return false;
            }
        }
        return true;
    }

    @Override
    public int getItemViewType(int position) {
        mItemBinding.onItemBind(position, mItems.get(position));
        return mItemBinding.layoutRes();
    }

    @Override
    public int getItemCount() {
        return mItems != null ? mItems.size() : 0;
    }

    @Override
    public long getItemId(int position) {
        return mItemIds == null ? position : mItemIds.getItemId(position, mItems.get(position));
    }

    public interface ItemIds<T>{
        long getItemId(int position, T item);
    }

    /**
     * Set the item id's for the items. If not null, this will set {@link
     * RecyclerView.Adapter#setHasStableIds(boolean)} to true.
     */
    public void setItemIds(@Nullable ItemIds<? super T> itemsIds){
        if (mItemIds != itemsIds){
            mItemIds = itemsIds;
            setHasStableIds(itemsIds != null);
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        if (mRecyclerView == null && mItems != null && mItems instanceof ObservableList){
            mListChangedCallBack = new WeakReferenceOnListChangedCallBack<T>(this, (ObservableList<T>)mItems);
            ((ObservableList<T>)mItems).addOnListChangedCallback(mListChangedCallBack);
        }
        mRecyclerView = recyclerView;
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        if (mRecyclerView != null && mItems != null && mItems instanceof ObservableList){
            ((ObservableList<T>)mItems).removeOnListChangedCallback(mListChangedCallBack);
            mListChangedCallBack = null;
        }
        mRecyclerView = null;
    }

    //------------------------------RecyclerView.Adapter--------------------------------

    @Override
    public void setItemBinding(ItemBinding<T> itemBinding) {
        mItemBinding = itemBinding;
    }

    @Override
    public ItemBinding<T> getItemBinding() {
        return mItemBinding;
    }

    @Override
    public void setItems(@Nullable List<T> items) {
        if (mItems == items){
            return;
        }
        // If a recyclerview is listening, set up listeners. Otherwise wait until one is attached.
        // No need to make a sound if nobody is listening right?
        if (mRecyclerView != null){
            if (mItems instanceof ObservableList){
                ((ObservableList)mItems).removeOnListChangedCallback(mListChangedCallBack);
                mListChangedCallBack = null;
            }
            if (items instanceof ObservableList){
                mListChangedCallBack = new WeakReferenceOnListChangedCallBack<T>(this, (ObservableList<T>)items);
                ((ObservableList<T>)items).addOnListChangedCallback(mListChangedCallBack);
            }
        }
        mItems = items;
        notifyDataSetChanged();
    }

    @Override
    public T getAdapterItem(int position) {
        return mItems.get(position);
    }

    @Override
    public ViewDataBinding initBinding(LayoutInflater inflater, int layoutRes, ViewGroup viewGroup) {
        return DataBindingUtil.inflate(inflater, layoutRes, viewGroup, false);
    }

    @Override
    public void onBindBinding(ViewDataBinding binding, int variableId, int layoutRes, int position, T item) {
        if (mItemBinding.bind(binding, item)){
            binding.executePendingBindings();
        }
    }

    //---------------------------------------------------------------------------

    private class WeakReferenceOnListChangedCallBack<T> extends ObservableList.OnListChangedCallback<ObservableList<T>>{

        private final WeakReference<BaseRecyclerViewAdapter<T>> mAdapterWeakRefer;

        public WeakReferenceOnListChangedCallBack(BaseRecyclerViewAdapter<T> adapter, ObservableList<T> items){
            mAdapterWeakRefer = AdapterReferenceCollector.createRef(adapter, items, this);
        }

        @Override
        public void onChanged(ObservableList sender) {
            BaseRecyclerViewAdapter<T> adapter = mAdapterWeakRefer.get();
            if (adapter == null){
                return;
            }
            BindingUtils.ensureChangeOnMainThread();
            adapter.notifyDataSetChanged();
        }

        @Override
        public void onItemRangeChanged(ObservableList sender, int positionStart, int itemCount) {
            BaseRecyclerViewAdapter<T> adapter = mAdapterWeakRefer.get();
            if (adapter == null) {
                return;
            }
            BindingUtils.ensureChangeOnMainThread();
            adapter.notifyItemRangeChanged(positionStart, itemCount);
        }

        @Override
        public void onItemRangeInserted(ObservableList sender, int positionStart, int itemCount) {
            BaseRecyclerViewAdapter<T> adapter = mAdapterWeakRefer.get();
            if (adapter == null) {
                return;
            }
            BindingUtils.ensureChangeOnMainThread();
            adapter.notifyItemRangeInserted(positionStart, itemCount);
        }

        @Override
        public void onItemRangeMoved(ObservableList sender, int fromPosition, int toPosition, int itemCount) {
            BaseRecyclerViewAdapter<T> adapter = mAdapterWeakRefer.get();
            if (adapter == null) {
                return;
            }
            BindingUtils.ensureChangeOnMainThread();
            for (int i = 0; i < itemCount; i++) {
                adapter.notifyItemMoved(fromPosition + i, toPosition + i);
            }
        }

        @Override
        public void onItemRangeRemoved(ObservableList sender, int positionStart, int itemCount) {
            BaseRecyclerViewAdapter<T> adapter = mAdapterWeakRefer.get();
            if (adapter == null) {
                return;
            }
            BindingUtils.ensureChangeOnMainThread();
            adapter.notifyItemRangeRemoved(positionStart, itemCount);
        }
    }
}
