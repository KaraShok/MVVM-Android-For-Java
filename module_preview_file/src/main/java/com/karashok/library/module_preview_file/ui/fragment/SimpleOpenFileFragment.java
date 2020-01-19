package com.karashok.library.module_preview_file.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.karashok.library.module_preview_file.R;
import com.karashok.library.module_preview_file.ui.widget.SuperReaderView;


/**
 * @author KaraShok(zhangyaozhong)
 * @name SimpleOpenFileFragment
 * DESCRIPTION pdf、docx、doc 文件预览
 * @date 2018/05/04/下午4:05
 */
public class SimpleOpenFileFragment extends Fragment {

    public static Bundle initBundle(boolean isNet, String url){
        Bundle bundle = new Bundle();
        bundle.putString("SimpleOpenFile_url", url);
        bundle.putBoolean("SimpleOpenFile_isNet", isNet);
        return bundle;
    }

    private SuperReaderView mReaderView;
    private RelativeLayout mRootLayoutRL;
    private String mOpenPath;
    private boolean mIsNet;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.activity_simple_open_file, container, false);
        return inflate;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRootLayoutRL = view.findViewById(R.id.activity_simple_open_file_rl);
//        mReaderView = view.findViewById(R.id.superReaderView);

        Bundle arguments = getArguments();
        if (arguments != null){
            mOpenPath = arguments.getString("SimpleOpenFile_url");
            mIsNet = arguments.getBoolean("SimpleOpenFile_isNet", false);
        }

        String fileUrl = "https://hd.alphalawyer.cn/intelligent/api/v1/file/656D229447A011E8939C00163E0A9183";
    }

    public String getNetSavrPath(){
        return mReaderView.mNetSavePath;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (mReaderView == null){
            mReaderView = new SuperReaderView(getContext());
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
    public void onPause() {
        super.onPause();
        if (mReaderView != null){
            mReaderView.onStopDisplay();
            mRootLayoutRL.removeView(mReaderView);
            mReaderView = null;
        }
    }

//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        if (mReaderView != null){
//            mReaderView.onStopDisplay();
//        }
//    }
}
