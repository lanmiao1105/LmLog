package com.lm.log;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.UnknownHostException;

/**
 * 日志工具类
 */
public class LmLog {

    /**
     * 日志输出类
     */
    private static LogPrinter logPrinter;

    /**
     * 初始化
     *
     * @param config 日志配置
     */
    public static void init(LmLogConfig config) {
        logPrinter = new LogPrinter(config);
    }

    /**
     * 获取日志配置
     *
     * @return 日志配置
     */
    public static LmLogConfig getConfig() {
        if (logPrinter == null) throw new IllegalStateException("LmLog还未初始化");
        return logPrinter.getConfig();
    }

    /**
     * 使用默认tag的verbose日志
     *
     * @param content 内容，例如："arg1=%s arg2=%s"
     * @param args    参数
     */
    public static void verbose(@NonNull String content, @Nullable Object... args) {
        log(Priority.VERBOSE, null, Priority.VERBOSE.tag, content, args);
    }

    /**
     * 使用默认tag的debug日志
     *
     * @param content 内容，例如："arg1=%s arg2=%s"
     * @param args    参数
     */
    public static void debug(@NonNull String content, @Nullable Object... args) {
        log(Priority.DEBUG, null, Priority.DEBUG.tag, content, args);
    }

    /**
     * 使用默认tag的info日志
     *
     * @param content 内容，例如："arg1=%s arg2=%s"
     * @param args    参数
     */
    public static void info(@NonNull String content, @Nullable Object... args) {
        log(Priority.INFO, null, Priority.INFO.tag, content, args);
    }

    /**
     * 使用默认tag的warn日志
     *
     * @param content 内容，例如："arg1=%s arg2=%s"
     * @param args    参数
     */
    public static void warn(@NonNull String content, @Nullable Object... args) {
        log(Priority.WARN, null, Priority.WARN.tag, content, args);
    }

    /**
     * 使用默认tag的error日志
     *
     * @param content 内容，例如："arg1=%s arg2=%s"
     * @param args    参数
     */
    public static void error(@NonNull String content, @Nullable Object... args) {
        log(Priority.ERROR, null, Priority.ERROR.tag, content, args);
    }

    /**
     * 使用默认tag的error日志
     *
     * @param throwable 异常
     */
    public static void error(@NonNull Throwable throwable) {
        log(Priority.ERROR, throwable, Priority.ERROR.tag, null);
    }

    /**
     * 使用默认tag的error日志
     *
     * @param throwable 异常
     * @param content   内容，例如："arg1=%s arg2=%s"
     * @param args      参数args
     */
    public static void error(@Nullable Throwable throwable, @Nullable String content, @Nullable Object... args) {
        log(Priority.ERROR, throwable, Priority.ERROR.tag, content, args);
    }

    /**
     * verbose日志
     *
     * @param tag     tag
     * @param content 内容，例如："arg1=%s arg2=%s"
     * @param args    参数
     */
    public static void v(@Nullable String tag, @NonNull String content, @Nullable Object... args) {
        log(Priority.VERBOSE, null, tag, content, args);
    }

    /**
     * debug日志
     *
     * @param tag     tag
     * @param content 内容，例如："arg1=%s arg2=%s"
     * @param args    参数
     */
    public static void d(@Nullable String tag, @NonNull String content, @Nullable Object... args) {
        log(Priority.DEBUG, null, tag, content, args);
    }

    /**
     * info日志
     *
     * @param tag     tag
     * @param content 内容，例如："arg1=%s arg2=%s"
     * @param args    参数
     */
    public static void i(@Nullable String tag, @NonNull String content, @Nullable Object... args) {
        log(Priority.INFO, null, tag, content, args);
    }

    /**
     * warn日志
     *
     * @param tag     tag
     * @param content 内容，例如："arg1=%s arg2=%s"
     * @param args    参数
     */
    public static void w(@Nullable String tag, @NonNull String content, @Nullable Object... args) {
        log(Priority.WARN, null, tag, content, args);
    }

    /**
     * error日志
     *
     * @param tag     tag
     * @param content 内容，例如："arg1=%s arg2=%s"
     * @param args    参数
     */
    public static void e(@Nullable String tag, @NonNull String content, @Nullable Object... args) {
        log(Priority.ERROR, null, tag, content, args);
    }

    /**
     * error日志
     *
     * @param tag       tag
     * @param throwable 异常
     */
    public static void e(@Nullable String tag, @NonNull Throwable throwable) {
        log(Priority.ERROR, throwable, tag, null);
    }

    /**
     * error日志
     *
     * @param tag       tag
     * @param throwable 异常
     * @param content   内容，例如："arg1=%s arg2=%s"
     * @param args      参数
     */
    public static void e(@Nullable String tag, @Nullable Throwable throwable, @Nullable String content, @Nullable Object... args) {
        log(Priority.ERROR, throwable, tag, content, args);
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
    public static void log(@NonNull Priority priority, @Nullable Throwable throwable, @Nullable String tag, @Nullable String content, @Nullable Object... args) {
        if (logPrinter == null) throw new IllegalStateException("LmLog还未初始化");

        logPrinter.log(priority, throwable, tag, content, args);
    }

    /**
     * 获取异常的堆栈信息
     *
     * @param tr 异常
     * @return String
     */
    public static String getStackTraceString(Throwable tr) {
        if (tr == null) {
            return "";
        }

        Throwable t = tr;
        while (t != null) {
            if (t instanceof UnknownHostException) {
                return "";
            }
            t = t.getCause();
        }

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        tr.printStackTrace(pw);
        pw.flush();
        return sw.toString();
    }
}
