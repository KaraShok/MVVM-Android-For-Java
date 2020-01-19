package com.karashok.library.common.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;

import permissions.dispatcher.PermissionRequest;

/**
 * @author KaraShokZ(zhangyaozhong)
 * @name PermissionDialog
 * DESCRIPTION 权限申请Dialog
 * @date 2018/06/16/下午6:18
 */
public class PermissionDialog extends DialogFragment {

    private PermissionRequest mRequest;
    private ArrayList<String> mPermissions;

    public static void newInstance(FragmentManager fragmentManager, PermissionRequest request, ArrayList<String> permissions){
        PermissionDialog dialog = new PermissionDialog();
        dialog.setPermissions(permissions);
        dialog.setRequest(request);
        dialog.show(fragmentManager, PermissionDialog.class.getSimpleName());
    }

    public void setRequest(PermissionRequest request) {
        this.mRequest = request;
    }

    public void setPermissions(ArrayList<String> permissions) {
        this.mPermissions = permissions;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final DialogFgtAdapter adapter = new DialogFgtAdapter(getActivity(), mPermissions);

        AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                .setAdapter(adapter, null)
                .setPositiveButton("申请", mClickListener)
                .setNegativeButton("拒绝", mClickListener)
                .setTitle("权限缺失提醒")
                .create();
        alertDialog.setCanceledOnTouchOutside(false);

        return alertDialog;
    }

    private DialogInterface.OnClickListener mClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                case -1:
                    // PositiveButton
                    if (mRequest != null){
                        mRequest.proceed();
                    }
                    break;
                case -2:
                    // NegativeButton
                    if (mRequest != null){
                        mRequest.cancel();
                    }
                    break;
                default:
                    break;
            }
        }
    };

    private static class DialogFgtAdapter extends BaseAdapter{

        private ArrayList<String> mPermission;
        private LayoutInflater mInflater;

        public DialogFgtAdapter(Context context, ArrayList<String> permission) {
            mInflater = LayoutInflater.from(context);
            mPermission = permission;
        }

        @Override
        public int getCount() {
            return mPermission != null ? mPermission.size() : 0;
        }

        @Override
        public Object getItem(int position) {
            return mPermission != null ? mPermission.get(position) : null;
        }

        @Override
        public long getItemId(int position) {
            return getItem(position).hashCode();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Holder holder;
            if (convertView == null){
                convertView = mInflater.inflate(android.R.layout.simple_list_item_1, parent, false);
                holder = new Holder();
                holder.textView = convertView.findViewById(android.R.id.text1);
                convertView.setTag(holder);
            }else {
                holder = (Holder) convertView.getTag();
            }
            holder.textView.setText(mPermission.get(position));
            return convertView;
        }

        private static class Holder{
            TextView textView;
        }
    }
}
