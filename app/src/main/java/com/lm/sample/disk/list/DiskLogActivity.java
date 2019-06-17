package com.lm.sample.disk.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.lm.sample.R;
import com.lm.sample.base.BaseActivity;
import com.lm.sample.disk.detail.DiskLogDetailActivity;

import java.io.File;

public class DiskLogActivity extends BaseActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disk_log);
        recyclerView = findViewById(R.id.recycler_view);

        DiskLogAdapter adapter = new DiskLogAdapter();
        adapter.setCallback(new DiskLogAdapter.Callback() {
            @Override
            public void onItemClick(File logFile) {
                showDiskLogDetail(logFile);
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void showDiskLogDetail(File logFile) {
        Intent intent = new Intent(DiskLogActivity.this, DiskLogDetailActivity.class);
        intent.putExtra("logFileName", logFile.getName());
        startActivityForResult(intent, 1000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Toast.makeText(this, "查看日志详情结束", Toast.LENGTH_SHORT).show();
    }
}
