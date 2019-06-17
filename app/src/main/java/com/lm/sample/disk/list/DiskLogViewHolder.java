package com.lm.sample.disk.list;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.lm.sample.R;

public class DiskLogViewHolder extends RecyclerView.ViewHolder {
    public TextView textView;

    public DiskLogViewHolder(@NonNull Context context) {
        super(LayoutInflater.from(context).inflate(R.layout.item_disk_log, null, false));
        textView = itemView.findViewById(R.id.text_view);
    }
}
