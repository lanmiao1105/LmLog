package com.lm.log.format;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.lm.log.LmLog;
import com.lm.log.Priority;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 一个简单的日志内容各式化策略
 */
public class SimpleFormatStrategy implements FormatStrategy {
    private static final char TOP_LEFT_CORNER = '┌';
    private static final char BOTTOM_LEFT_CORNER = '└';
    private static final char MIDDLE_CORNER = '├';
    private static final char HORIZONTAL_LINE = '│';
    private static final String DOUBLE_DIVIDER = "────────────────────────────────────────────────────────";
    private static final String SINGLE_DIVIDER = "┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄";
    private static final String TOP_BORDER = TOP_LEFT_CORNER + DOUBLE_DIVIDER + DOUBLE_DIVIDER;
    private static final String BOTTOM_BORDER = BOTTOM_LEFT_CORNER + DOUBLE_DIVIDER + DOUBLE_DIVIDER;
    private static final String MIDDLE_BORDER = MIDDLE_CORNER + SINGLE_DIVIDER + SINGLE_DIVIDER;

    @Override
    public String[] format(@NonNull Priority priority, @Nullable Throwable throwable, @NonNull String tag, @Nullable String content) {

        if (content == null || content.length() == 0) {
            content = "Empty/NULL log message";
        }

        if (content.startsWith("{") && content.endsWith("}")) {
            try {
                JSONObject jsonObject = new JSONObject(content);
                content = jsonObject.toString();
            } catch (JSONException ignore) {

            }
        } else if (content.startsWith("[") && content.endsWith("]")) {
            try {
                JSONArray jsonArray = new JSONArray(content);
                content = jsonArray.toString();
            } catch (JSONException ignore) {

            }
        }

        StringBuilder sb = new StringBuilder();
        sb.append(TOP_BORDER).append("\n");

        String[] contentArrays = content.split("\n");
        for (int i = 0; i < contentArrays.length; i++) {
            sb.append(HORIZONTAL_LINE).append(contentArrays[i]).append("\n");
        }

        if (throwable != null) {
            String[] errorArrays = LmLog.getStackTraceString(throwable).split("\n");
            sb.append(MIDDLE_BORDER).append("\n");
            for (int i = 0; i < errorArrays.length; i++) {
                sb.append(HORIZONTAL_LINE).append(errorArrays[i]).append("\n");
            }
        }

        sb.append(BOTTOM_BORDER);

        return sb.toString().split("\n");
    }
}
