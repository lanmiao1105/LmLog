package com.lm.log.adapter;

import android.support.annotation.NonNull;

import com.lm.log.Priority;
import com.lm.log.format.SimpleFormatStrategy;
import com.lm.log.log.DiskLogStrategy;

/**
 * 本地存储日志Adapter
 */
public class DiskLogAdapter extends LogAdapter<SimpleFormatStrategy, DiskLogStrategy> {
    @Override
    protected SimpleFormatStrategy buildFormatStrategy() {
        return new SimpleFormatStrategy();
    }

    @Override
    protected DiskLogStrategy buildLogStrategy() {
        return new DiskLogStrategy();
    }

    @Override
    public boolean isLog(@NonNull Priority priority) {
        return true;
    }
}
