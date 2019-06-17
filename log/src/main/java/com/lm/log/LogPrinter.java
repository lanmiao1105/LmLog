package com.lm.log;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.lm.log.adapter.LogAdapter;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 日志输出类
 */
public class LogPrinter {
    /**
     * SingleThread线程池，保证日志FIFO
     */
    private ExecutorService executorService;

    /**
     * 日志配置
     */
    private LmLogConfig config;

    public LogPrinter(LmLogConfig config) {
        this.config = config;
        this.executorService = Executors.newSingleThreadExecutor();
    }

    /**
     * 获取日志配置
     *
     * @return 日志配置
     */
    public LmLogConfig getConfig() {
        return config;
    }

    /**
     * 输出日志
     *
     * @param priority  日志等级
     * @param throwable 异常
     * @param tag       tag
     * @param content   内容，例如："arg1=%s arg2=%s"
     * @param args      参数
     */
    public void log(@NonNull Priority priority, @Nullable Throwable throwable, @Nullable String tag, @Nullable String content, @Nullable Object... args) {
        if (config.getLogAdapterList() == null || config.getLogAdapterList().isEmpty()) return;

        tag = tag == null || tag.length() == 0 ? priority.tag : tag;
        content = args == null || args.length == 0 ? content : String.format(content, args);

        logAsync(priority, throwable, tag, content);
    }

    /**
     * 异步输出日志
     *
     * @param priority  日志等级
     * @param throwable 异常
     * @param tag       tag
     * @param content   内容
     */
    private void logAsync(@NonNull final Priority priority, @Nullable final Throwable throwable, @NonNull final String tag, @Nullable final String content) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                List<LogAdapter> logAdapterList = config.getLogAdapterList();
                for (int i = 0; i < logAdapterList.size(); i++) {
                    LogAdapter logAdapter = logAdapterList.get(i);
                    if (logAdapter.isLog(priority)) {
                        logAdapter.log(priority, throwable, tag, content);
                    }
                }
            }
        });
    }

}
