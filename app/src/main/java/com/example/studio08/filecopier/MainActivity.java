package com.example.studio08.filecopier;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView listViewInternal;
    ListView listViewExternal;
    ArrayList<File> fileArrayListInteral;
    ArrayList<File> fileArrayListExternal;
    File selectedFile;
    File rootInternal;
    File rootExternal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initFiles();
        initViews();
    }

    private void initFiles() {
        rootInternal = getFilesDir();
        rootExternal = Environment.getExternalStorageDirectory();

        // dummy file
        File newFile = new File(rootInternal, "new_file.file");
        if (!newFile.exists())
            try {
                if (newFile.createNewFile()) toast("file created");
            } catch (IOException e) {
                e.printStackTrace();
            }

        fileArrayListInteral = new ArrayList<>(Arrays.asList(rootInternal.listFiles()));
        fileArrayListExternal = new ArrayList<>(Arrays.asList(rootExternal.listFiles()));
    }

    private void initViews() {
        listViewInternal = (ListView) findViewById(R.id.listView_internal);
        listViewExternal = (ListView) findViewById(R.id.listView_external);

        ArrayAdapter<File> adapterInternal = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, fileArrayListInteral);
        ArrayAdapter<File> adapterExternal = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, fileArrayListExternal);

        listViewInternal.setAdapter(adapterInternal);
        listViewExternal.setAdapter(adapterExternal);

        listViewInternal.setOnItemClickListener(this);
        listViewExternal.setOnItemClickListener(this);
    }

    public void create(View view) {
        Intent intent = new Intent(this, FileCreateActivity.class);
        startActivity(intent);
    }

    public void copy(View view) {
        if (selectedFile == null)
            toast("please select a file from internal storage");
        else {
            FileCopier fileCopier = new FileCopier();
            fileCopier.execute();
        }
    }

    private void toast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (view.getParent() == listViewInternal) {
            selectedFile = (!fileArrayListInteral.isEmpty()) ? fileArrayListInteral.get(position) : null;
            if (selectedFile != null)
                toast(selectedFile.getName() + " selected");
        } else {
            selectedFile = (!fileArrayListExternal.isEmpty()) ? fileArrayListExternal.get(position) : null;
            Intent intent = new Intent(this, SelectedFileActivity.class);
            intent.putExtra("file", selectedFile.toString());
            startActivity(intent);

        }
    }

    class FileCopier extends AsyncTask<Object[], Void, Boolean> {

        FileInputStream fis;
        FileOutputStream fos;

        @Override
        protected Boolean doInBackground(Object[]... params) {
            try {
                fis = new FileInputStream(getFilesDir());
                fos = new FileOutputStream(getExternalFilesDir(null));
                byte[] buffer = new byte[1024 * 8];
                int counter = 0;
                while ((counter = fis.read(buffer)) != -1) {
                    fos.write(buffer, 0 , counter);
                }
                fis.close();
                fos.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }
    }
}
