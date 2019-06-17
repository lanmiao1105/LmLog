package com.lm.log.log;

import android.support.annotation.NonNull;

import com.lm.log.Priority;

/**
 * 日志输出策略接口
 */
public interface LogStrategy {
    /**
     * 输出日志
     *
     * @param priority     日志等级
     * @param tag          tag
     * @param contentArray 内容（分行）
     */
    void log(@NonNull Priority priority, @NonNull String tag, @NonNull String[] contentArray);
}
