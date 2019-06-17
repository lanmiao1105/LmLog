package com.lm.sample.disk.detail;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.lm.sample.R;

public class DiskLogDetailActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disk_log_detail);

        DiskLogDetailFragment diskLogDetailFragment = new DiskLogDetailFragment();
        diskLogDetailFragment.setArguments(getIntent().getExtras());
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_content, diskLogDetailFragment)
                .commit();

        setResult(RESULT_OK);
    }

}
