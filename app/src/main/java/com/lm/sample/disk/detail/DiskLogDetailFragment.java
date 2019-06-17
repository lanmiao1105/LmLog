package com.lm.sample.disk.detail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lm.io.InputStreamWrapper;
import com.lm.io.LmIO;
import com.lm.log.LmLog;
import com.lm.log.adapter.DiskLogAdapter;
import com.lm.sample.base.BaseFragment;

import java.io.File;
import java.io.IOException;


public class DiskLogDetailFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        String logFileName = getArguments().getString("logFileName");
        File[] logFiles = LmLog.getConfig().getAdapter(DiskLogAdapter.class).getLogStrategy().getLogFileDir().listFiles();
        if (logFiles != null && logFiles.length > 0) {
            for (int i = 0; i < logFiles.length; i++) {
                if (logFiles[i].getName().equals(logFileName)) {
                    InputStreamWrapper inputStreamWrapper = null;
                    try {
                        inputStreamWrapper = LmIO.inputStreamWrapper(logFiles[i]);
                        String logContent = inputStreamWrapper.readAllUTF8();
                        TextView textView = new TextView(getActivity());
                        textView.setText(logContent);
                        textView.setMovementMethod(ScrollingMovementMethod.getInstance());
                        textView.setHorizontallyScrolling(true);
                        textView.setHorizontalScrollBarEnabled(true);
                        textView.setVerticalScrollBarEnabled(true);
                        return  textView;
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        if (inputStreamWrapper != null) {
                            inputStreamWrapper.close();
                        }
                    }
                    break;
                }
            }
        }

        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
