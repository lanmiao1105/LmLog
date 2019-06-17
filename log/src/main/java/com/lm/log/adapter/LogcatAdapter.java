package com.lm.log.adapter;

import com.lm.log.Priority;
import com.lm.log.format.SimpleFormatStrategy;
import com.lm.log.log.LogcatLogStrategy;

/**
 * Logcat日志Adapter
 */
public class LogcatAdapter extends LogAdapter<SimpleFormatStrategy, LogcatLogStrategy> {
    @Override
    protected SimpleFormatStrategy buildFormatStrategy() {
        return new SimpleFormatStrategy();
    }

    @Override
    protected LogcatLogStrategy buildLogStrategy() {
        return new LogcatLogStrategy();
    }

    @Override
    public boolean isLog(Priority priority) {
        return true;
    }
}
