package com.lm.log.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.lm.log.Priority;
import com.lm.log.format.FormatStrategy;
import com.lm.log.log.LogStrategy;

/**
 * 日志输出适配器
 */
public abstract class LogAdapter<Format extends FormatStrategy, Log extends LogStrategy> {
    /**
     * 日志内容格式化策略
     */
    protected Format formatStrategy;
    /**
     * 日志内容输出策略
     */
    protected Log logStrategy;

    public LogAdapter() {
        formatStrategy = buildFormatStrategy();
        logStrategy = buildLogStrategy();
    }

    /**
     * 输出日志
     *
     * @param priority  日志等级
     * @param throwable 异常
     * @param tag       tag
     * @param content   内容
     */
    public void log(@NonNull final Priority priority, @Nullable final Throwable throwable, @NonNull final String tag, @Nullable final String content) {
        if (formatStrategy != null && logStrategy != null) {
            String[] formatContent = formatStrategy.format(priority, throwable, tag, content);
            logStrategy.log(priority, tag, formatContent);
        }
    }

    /**
     * 抽象方法创建格式化策略
     *
     * @return 格式化策略
     */
    protected abstract Format buildFormatStrategy();

    /**
     * 抽象方法创建输出策略
     *
     * @return 输出策略
     */
    protected abstract Log buildLogStrategy();

    /**
     * 抽象方法是否记录日志
     *
     * @param priority 日志等级
     * @return 是否记录日志
     */
    public abstract boolean isLog(@NonNull Priority priority);

    /**
     * 获取格式化策略
     *
     * @return 格式化策略
     */
    public Format getFormatStrategy() {
        return formatStrategy;
    }

    /**
     * 获取日志策略
     *
     * @return 日志策略
     */
    public Log getLogStrategy() {
        return logStrategy;
    }
}
