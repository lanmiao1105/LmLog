package com.lm.log.format;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.lm.log.Priority;

/**
 * 日志内容格式化策略
 */
public interface FormatStrategy {
    /**
     * 日志内容格式化
     *
     * @param priority  日志等级
     * @param throwable 异常
     * @param tag       tag
     * @param content   内容
     * @return 格式化内容（分行）
     */
    String[] format(@NonNull Priority priority, @Nullable Throwable throwable, @NonNull String tag, @Nullable String content);
}
