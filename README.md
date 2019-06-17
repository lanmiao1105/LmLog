# LmLog
Android Log Utils

- [x] 自定义Adapter决定日志在哪里输出，输出以及格式。
- [x] 可以通过@Log注解的方法和类（@Log注释的方法和类会输出日志）
- [x] 点击事件切面（点击事件会输出日志）
- [x] Activity生命周期切面（Activity生命周期会输出日志）
- [x] Fragment生命周期切面（Fragment生命周期会输出日志）
- [x] BroadcastReceiver切面（BroadcastReceiver的onReceive方法会输出日志）
- [x] Service生命周期切面（Service生命周期会输出日志）

#### 配置
```
buildscript {
    dependencies {
        ...
        // 添加aspectjx插件
        classpath 'com.hujiang.aspectjx:gradle-android-plugin-aspectjx:2.0.4'
        ...
    }
}
```

```
apply plugin: 'com.android.application'
// 使用aspectjx插件
apply plugin: 'com.hujiang.android-aspectjx'

dependencies {
    ...
    // 依赖
    implementation 'com.lm:log:1.0.0'
    ...
}

```
#### 初始化
```
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
```

