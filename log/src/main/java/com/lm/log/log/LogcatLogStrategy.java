package com.lm.log.log;

import android.support.annotation.NonNull;
import android.util.Log;

import com.lm.log.Priority;

/**
 * Logcat日志输出策略
 */
public class LogcatLogStrategy implements LogStrategy {

    private static final int ONE_LINE_MAX_LENGTH = 1024;

    @Override
    public void log(@NonNull Priority priority, @NonNull String tag, @NonNull String[] contentArray) {
        for (String content : contentArray) {
            byte[] data = content.getBytes();
            int len = data.length;
            int start = 0;
            while (start < len) {
                int count = Math.min(ONE_LINE_MAX_LENGTH, len - start);
                Log.println(priority.level, tag, new String(data, start, count));
                start += count;
            }
        }
    }
}
