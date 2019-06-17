package com.lm.sample;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Toast;

import com.lm.log.LmLog;
import com.lm.log.adapter.DiskLogAdapter;
import com.lm.log.aop.Log;
import com.lm.sample.base.BaseActivity;
import com.lm.sample.broadcast.SampleBroadcastReceiver;
import com.lm.sample.disk.list.DiskLogActivity;
import com.lm.sample.service.SampleService;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_long_click).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(MainActivity.this, "长按成功", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_disk_log:
                startActivity(new Intent(this, DiskLogActivity.class));
                break;
            case R.id.btn_log:
                LmLog.info("这是一条随机字符串日志%s", System.currentTimeMillis());
                break;
            case R.id.btn_log_aspect:
                logAspect(System.currentTimeMillis());
                break;
            case R.id.btn_delete_log_file:
                deleteLogFile();
                break;
            case R.id.btn_send_broadcast:
                sendBroadcast();
                break;
            case R.id.btn_start_service:
                startService();
                break;
            case R.id.btn_stop_service:
                stopService();
                break;
            case R.id.btn_bind_service:
                bindService();
                break;
            case R.id.btn_unbind_service:
                unbindService();
                break;
        }
    }

    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private void startService() {
        startService(new Intent(this, SampleService.class));
    }

    private void stopService() {
        stopService(new Intent(this, SampleService.class));
    }

    private void bindService() {
        bindService(new Intent(this, SampleService.class), serviceConnection, BIND_AUTO_CREATE);
    }

    private void unbindService() {
        unbindService(serviceConnection);
    }

    private void sendBroadcast() {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.lm.sample", "com.lm.sample.broadcast.SampleBroadcastReceiver"));
        intent.setAction(SampleBroadcastReceiver.action);
        sendBroadcast(intent);
    }

    @Log
    private String logAspect(long timeMillis) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(new Date(timeMillis));
    }

    private void deleteLogFile() {
        File logFileDir = LmLog.getConfig().getAdapter(DiskLogAdapter.class).getLogStrategy().getLogFileDir();
        File[] logFiles = logFileDir.listFiles();
        if (logFiles != null && logFiles.length > 0) {
            boolean result = true;
            for (int i = 0; i < logFiles.length; i++) {
                result &= logFiles[i].delete();
            }
            if (result) {
                Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "删除失败", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
