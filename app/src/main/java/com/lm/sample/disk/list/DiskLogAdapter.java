package com.lm.sample.disk.list;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.lm.log.LmLog;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;

public class DiskLogAdapter extends RecyclerView.Adapter<DiskLogViewHolder>{

    File[] logFiles;

    public DiskLogAdapter() {
        logFiles = LmLog.getConfig().getAdapter(com.lm.log.adapter.DiskLogAdapter.class).getLogStrategy().getLogFileDir().listFiles();
        Arrays.sort(logFiles, new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                return (int) (o1.lastModified() - o2.lastModified());
            }
        });
    }

    @NonNull
    @Override
    public DiskLogViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new DiskLogViewHolder(viewGroup.getContext());
    }

    @Override
    public void onBindViewHolder(@NonNull DiskLogViewHolder diskLogViewHolder, int i) {
        final File logFile = logFiles[i];
        diskLogViewHolder.textView.setText(logFile.getName() + " " + logFile.length());
        diskLogViewHolder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback!=null){
                    callback.onItemClick(logFile);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return logFiles != null ? logFiles.length : 0;
    }

    private Callback callback;

    interface Callback {
        void onItemClick(File logFile);
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }
}
