package com.karashok.library.module_preview_file.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.karashok.library.module_preview_file.R;
import com.karashok.library.module_preview_file.ui.widget.SuperReaderView;


/**
 * @author KaraShokZ(zhangyaozhong)
 * @name SimpleOpenFileActivity
 * DESCRIPTION pdf、docx、doc 文件预览
 * @date 2018/05/18/下午2:52
 */
public class SimpleOpenFileActivity extends AppCompatActivity {

    public static void superReader(Context context, String url, boolean isNet){
        Intent intent = new Intent(context, SimpleOpenFileActivity.class);
        intent.putExtra("SimpleOpenFile_url",url);
        intent.putExtra("SimpleOpenFile_isNet",isNet);
        context.startActivity(intent);
    }

    private SuperReaderView mReaderView;
    private RelativeLayout mRootLayoutRL;
    private String mOpenPath;
    private boolean mIsNet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_open_file);

        mRootLayoutRL = findViewById(R.id.activity_simple_open_file_rl);

        Intent intent = getIntent();
        mOpenPath = intent.getStringExtra("SimpleOpenFile_url");
        mIsNet = intent.getBooleanExtra("SimpleOpenFile_isNet",false);

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mReaderView == null){
            mReaderView = new SuperReaderView(this);
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            mReaderView.setLayoutParams(params);
            mRootLayoutRL.addView(mReaderView);
        }

        if (mIsNet){
            mReaderView.openNetFile(mOpenPath);
        }else {
            mReaderView.openLocationFile(mOpenPath);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mReaderView != null){
            mReaderView.onStopDisplay();
            mRootLayoutRL.removeView(mReaderView);
            mReaderView = null;
        }
    }

}
