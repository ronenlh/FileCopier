package com.example.studio08.filecopier;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.File;

public class SelectedFileActivity extends AppCompatActivity {

    TextView titleTextView;
    TextView contentTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_file);

        Intent intent = getIntent();
        File selectedFile = new File(intent.getStringExtra("file"));

        titleTextView = (TextView) findViewById(R.id.file_title);
        contentTextView = (TextView) findViewById(R.id.file_content);

        titleTextView.setText(selectedFile.getName());


    }
}
