package com.lm.log.log;

import android.support.annotation.NonNull;

import com.lm.io.LmIO;
import com.lm.io.OutputStreamWrapper;
import com.lm.log.LmLog;
import com.lm.log.Priority;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

/**
 * 本地存储日志输出策略
 */
public class DiskLogStrategy implements LogStrategy {
    /**
     * 最多日志文件个数
     */
    private static final int LOG_FILE_MAX_COUNT = 10;
    /**
     * 每个日志文件最大size
     */
    private static final int LOG_FILE_MAX_SIZE = 1024 * 1024;//单位B

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);

    private Comparator comparator = new Comparator<File>() {
        @Override
        public int compare(File o1, File o2) {
            return (int) (o1.lastModified() - o2.lastModified());
        }
    };

    public DiskLogStrategy() {

    }

    @Override
    public void log(@NonNull Priority priority, @NonNull String tag, @NonNull String[] contentArray) {
        OutputStreamWrapper outputStreamWrapper = null;
        try {
            File logFile = getLastLogFile();
            if (logFile != null) {
                outputStreamWrapper = LmIO.outputStreamWrapper(logFile, true);
                for (String content : contentArray) {
                    outputStreamWrapper
                            .writeUTF8(simpleDateFormat.format(new Date()))
                            .writeUTF8(" ")
                            .writeUTF8(priority.tag)
                            .writeUTF8("/")
                            .writeUTF8(tag)
                            .writeUTF8(": ")
                            .writeUTF8(content)
                            .writeUTF8("\n");
                }
                outputStreamWrapper.flush();
            }
        } catch (IOException ignore) {
            ignore.printStackTrace();
        } finally {
            if (outputStreamWrapper != null) {
                outputStreamWrapper.close();
            }
        }
    }

    private File getLastLogFile() throws IOException {
        File cacheDir = getLogFileDir();

        File[] logFiles = cacheDir.listFiles();

        File lastLogFile = null;

        if (logFiles != null && logFiles.length > 0) {
            Arrays.sort(logFiles, comparator);

            File temp = logFiles[logFiles.length - 1];
            if (temp.length() < LOG_FILE_MAX_SIZE) {
                lastLogFile = temp;
            }

            if (lastLogFile == null && logFiles.length >= LOG_FILE_MAX_COUNT) {
                logFiles[0].delete();
            }
        }

        if (lastLogFile == null) {
            lastLogFile = new File(cacheDir, System.currentTimeMillis() + ".txt");
            lastLogFile.createNewFile();
        }

        return lastLogFile;
    }

    public File getLogFileDir() {
        File cacheDir = new File(LmLog.getConfig().getContext().getExternalCacheDir(), "LmLog");
        if (!cacheDir.exists() || !cacheDir.isDirectory()) {
            cacheDir.delete();
            cacheDir.mkdirs();
        }
        return cacheDir;
    }
}
