package com.karashok.library.common.base;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.karashok.library.module_util.utilcode.utils.Utils;


/**
 * @author KaraShokZ(zhangyaozhong)
 * @name BaseRecyclerAdapter
 * DESCRIPTION RecyclerView的Adapter基类
 * @date 2018/06/16/下午6:18
 */
public abstract class BaseRecyclerAdapter<T,B extends ViewDataBinding> extends RecyclerView.Adapter {

    protected ObservableArrayList<T> mItems;
    protected ListChangeCallBack mListChange;

    protected abstract @LayoutRes
    int getLayoutResId(int itemType);

    protected abstract void onBindItem(B binding, T item);

    public BaseRecyclerAdapter() {
        mItems = new ObservableArrayList<>();
        mListChange = new ListChangeCallBack();
    }

    public ObservableArrayList<T> getItems(){
        return mItems;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        B binding = DataBindingUtil.inflate(LayoutInflater.from(Utils.getApp()),
                                getLayoutResId(viewType), parent,false);
        return new BaseRecyclerViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        B binding = DataBindingUtil.getBinding(holder.itemView);
        onBindItem(binding,mItems.get(position));
    }

    @Override
    public int getItemCount() {
        return mItems != null ? mItems.size() : 0;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.mItems.addOnListChangedCallback(mListChange);
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        this.mItems.removeOnListChangedCallback(mListChange);
    }

    private void reserItems(ObservableArrayList<T> newItems){
        mItems = newItems;
    }

    protected void onChanged(ObservableArrayList<T> newItems){
        reserItems(newItems);
        notifyDataSetChanged();
    }

    protected void onItemRangeChanged(ObservableArrayList<T> newItems, int positionStart, int itemCount){
        reserItems(newItems);
        notifyItemRangeChanged(positionStart, itemCount);
    }

    protected void onItemRangeInserted(ObservableArrayList<T> newItems, int positionStart, int itemCount){
        reserItems(newItems);
        notifyItemRangeInserted(positionStart, itemCount);
    }

    protected void onItemRangeMoved(ObservableArrayList<T> newItems, int fromPosition, int toPostiton, int itemCount){
        reserItems(newItems);
        notifyItemRangeRemoved(fromPosition,itemCount);
    }

    protected void OnItemRangeRemoved(ObservableArrayList<T> newItems, int positionStart, int itemCount){
        reserItems(newItems);
        notifyItemRangeRemoved(positionStart, itemCount);
    }

    class ListChangeCallBack extends ObservableArrayList.OnListChangedCallback<ObservableArrayList<T>>{

        @Override
        public void onChanged(ObservableArrayList<T> sender) {
            BaseRecyclerAdapter.this.onChanged(sender);
        }

        @Override
        public void onItemRangeChanged(ObservableArrayList<T> sender, int positionStart, int itemCount) {
            BaseRecyclerAdapter.this.onItemRangeChanged(sender, positionStart, itemCount);
        }

        @Override
        public void onItemRangeInserted(ObservableArrayList<T> sender, int positionStart, int itemCount) {
            BaseRecyclerAdapter.this.onItemRangeInserted(sender, positionStart, itemCount);
        }

        @Override
        public void onItemRangeMoved(ObservableArrayList<T> sender, int fromPosition, int toPosition, int itemCount) {
            BaseRecyclerAdapter.this.onItemRangeMoved(sender, fromPosition, toPosition, itemCount);
        }

        @Override
        public void onItemRangeRemoved(ObservableArrayList<T> sender, int positionStart, int itemCount) {
            BaseRecyclerAdapter.this.OnItemRangeRemoved(sender, positionStart, itemCount);
        }
    }

    class BaseRecyclerViewHolder extends RecyclerView.ViewHolder{

        public BaseRecyclerViewHolder(View itemView) {
            super(itemView);
        }
    }
}
