package com.lm.log;

/**
 * 日志优先级
 */
public enum Priority {
    VERBOSE(2, "V"),    //verbose
    DEBUG(3, "D"),      //debug
    INFO(4, "I"),       //info
    WARN(5, "W"),       //warn
    ERROR(6, "E");      //error

    public final int level;//等级
    public final String tag;//描述

    Priority(int level, String tag) {
        this.level = level;
        this.tag = tag;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"level\":")
                .append(level);
        sb.append(",\"tag\":\"")
                .append(tag).append('\"');
        sb.append('}');
        return sb.toString();
    }
}
