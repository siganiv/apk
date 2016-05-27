package com.example.siganiv.receiver;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class ArchiveActivity extends AppCompatActivity {

    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_archive);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tv = (TextView) findViewById(R.id.archive);
        Intent activityThatCalled = getIntent();
        String value = activityThatCalled.getStringExtra(MainActivity.MESSAGE_TO_ARCHIVE);
        tv.setText(value);
    }

    public void onClickReturn(View view) {
        Intent goingBack = new Intent();
        finish();
    }

}
