package com.karashok.library.module_photos_selector.ui.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.karashok.library.module_photos_selector.ModuleLocalPhotoSelectorConstant;
import com.karashok.library.module_photos_selector.R;
import com.karashok.library.module_photos_selector.ui.adapter.FolderListRecyclerAdapter;
import com.karashok.library.module_photos_selector.ui.widget.ThingCallBack;
import com.karashok.library.module_photos_selector.util.PhotoSelectIntentUtils;
import com.karashok.library.module_util.utilcode.utils.view_util.StatusBarUtils;


/**
 * @author KaraShokZ(zhangyaozhong)
 * @name FolderListActivity
 * DESCRIPTION 相册预览页面
 * @date 2018/05/18/下午4:15
 */
public class FolderListActivity extends AppCompatActivity {

    private TextView mCancel;
    private RecyclerView mRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 强制竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.aty_folder_list);
        // 设置状态栏颜色
        StatusBarUtils.setStatusBarColor(this,"#373c3d");

        initView();
        initRecycler();
    }

    /**
     * 初始化页面的View
     */
    private void initView(){
        mCancel = findViewById(R.id.aty_folder_list_title_cancel_tv);
        mRecycler = findViewById(R.id.aty_folder_list_content_rv);

        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 初始化RecyclerView
     */
    private void initRecycler(){
        initAdapter();
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
    }

    /**
     * 初始化RecyclerAdapter
     */
    private void initAdapter(){
        if (ModuleLocalPhotoSelectorConstant.FOLDERS != null && !ModuleLocalPhotoSelectorConstant.FOLDERS.isEmpty()){
            FolderListRecyclerAdapter adapter = new FolderListRecyclerAdapter(this);
            mRecycler.setAdapter(adapter);
            adapter.setCallBack(new ThingCallBack<Integer>() {
                @Override
                public void thingCallBack(Integer o) {
                    intentResult(o);
                }
            });
        }
    }

    /**
     * 向前一个页面返回数据
     * @param position
     */
    private void intentResult(int position){
        Intent intent = new Intent();
        intent.putExtra(PhotoSelectIntentUtils.RESULT_FOLDER_POSITION, position);
        setResult(PhotoSelectIntentUtils.CODE_RESULT_FOLDER_LIST, intent);
        finish();
    }
}
