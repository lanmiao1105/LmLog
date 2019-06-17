package com.lm.sample;

import android.app.Application;

import com.lm.log.LmLogConfig;
import com.lm.log.adapter.DiskLogAdapter;
import com.lm.log.adapter.LogcatAdapter;

public class SampleApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        new LmLogConfig(this)
                //日志输出Adapter---Logcat
                .addLogAdapter(new LogcatAdapter())
                //日志输出Adapter---Disk
                .addLogAdapter(new DiskLogAdapter())
                //开启@Log注解切面
                .enableLogAspect(true)
                //开启点击事件切面
                .enableClickAspect(true)
                //开启Activity切面，且过滤掉BaseActivity
                .enableActivityAspect(true, "com.lm.sample.base.BaseActivity")
                //开启Fragment切面，且过滤掉BaseFragment
                .enableFragmentAspect(true, "com.lm.sample.base.BaseFragment")
                //开启BroadcastReceiver切面，且不设置过滤信息
                .enableBroadcastReceiverAspect(true)
                //开启Service切面，且不设置过滤信息
                .enableServiceAspect(true)
                .init();
    }
}
