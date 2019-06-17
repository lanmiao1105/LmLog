package com.lm.log;

import android.content.Context;

import com.lm.log.adapter.LogAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 日志配置
 */
public class LmLogConfig {

    /**
     * Context
     */
    private Context context;

    /**
     * 日志输出适配器List
     */
    private List<LogAdapter> logAdapterList;

    /**
     * 开启Activity切面
     */
    private boolean enableActivityAspect;
    /**
     * Activity切面过滤参数
     */
    private String[] activityFilters;

    /**
     * 开启Fragment切面
     */
    private boolean enableFragmentAspect;
    /**
     * Fragment切面过滤参数
     */
    private String[] fragmentFilters;

    /**
     * 开启Service切面
     */
    private boolean enableServiceAspect;
    /**
     * Service切面过滤参数
     */
    private String[] serviceFilters;

    /**
     * 开启BroadcastReceiver切面
     */
    private boolean enableBroadcastReceiverAspect;
    /**
     * BroadcastReceiver切面过滤参数
     */
    private String[] broadcastReceiverFilters;

    /**
     * 开启Log注解切面
     */
    private boolean enableLogAspect;

    /**
     * 开启点击事件切面
     */
    private boolean enableClickAspect;

    public LmLogConfig(Context context) {
        this.context = context.getApplicationContext();
    }

    public Context getContext() {
        return context;
    }

    public List<LogAdapter> getLogAdapterList() {
        return logAdapterList;
    }

    public <T extends LogAdapter> T getAdapter(Class<T> clazz) {
        if (logAdapterList != null && logAdapterList.size() > 0) {
            for (int i = 0; i < logAdapterList.size(); i++) {
                LogAdapter logAdapter = logAdapterList.get(i);
                if (logAdapter.getClass().equals(clazz)) {
                    return (T) logAdapter;
                }
            }
        }
        return null;
    }

    public LmLogConfig addLogAdapter(LogAdapter logAdapter) {
        if (logAdapterList == null) {
            logAdapterList = new ArrayList<>();
        }
        logAdapterList.add(logAdapter);
        return this;
    }

    public boolean isEnableActivityAspect() {
        return enableActivityAspect;
    }

    public LmLogConfig enableActivityAspect(boolean enableActivityAspect, String... filters) {
        this.enableActivityAspect = enableActivityAspect;
        this.activityFilters = filters;
        return this;
    }

    public String[] getActivityFilters() {
        return activityFilters;
    }

    public boolean isEnableFragmentAspect() {
        return enableFragmentAspect;
    }

    public LmLogConfig enableFragmentAspect(boolean enableFragmentAspect, String... filters) {
        this.enableFragmentAspect = enableFragmentAspect;
        this.fragmentFilters = filters;
        return this;
    }

    public String[] getFragmentFilters() {
        return fragmentFilters;
    }

    public boolean isEnableServiceAspect() {
        return enableServiceAspect;
    }

    public LmLogConfig enableServiceAspect(boolean enableServiceAspect,String... filters) {
        this.enableServiceAspect = enableServiceAspect;
        serviceFilters = filters;
        return this;
    }

    public String[] getServiceFilters() {
        return serviceFilters;
    }

    public boolean isEnableBroadcastReceiverAspect() {
        return enableBroadcastReceiverAspect;
    }

    public LmLogConfig enableBroadcastReceiverAspect(boolean enableBroadcastReceiverAspect,String... filters) {
        this.enableBroadcastReceiverAspect = enableBroadcastReceiverAspect;
        this.broadcastReceiverFilters = filters;
        return this;
    }

    public String[] getBroadcastReceiverFilters() {
        return broadcastReceiverFilters;
    }

    public boolean isEnableLogAspect() {
        return enableLogAspect;
    }

    public LmLogConfig enableLogAspect(boolean enableLogAspect) {
        this.enableLogAspect = enableLogAspect;
        return this;
    }

    public boolean isEnableClickAspect() {
        return enableClickAspect;
    }

    public LmLogConfig enableClickAspect(boolean enableClickAspect) {
        this.enableClickAspect = enableClickAspect;
        return this;
    }

    public void init() {
        LmLog.init(this);
    }
}
