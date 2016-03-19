package com.example.studio08.filecopier;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileCreateActivity extends AppCompatActivity {

    EditText EditName;
    EditText EditContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_create);
        EditName = (EditText) findViewById(R.id.editText);
        EditContent = (EditText) findViewById(R.id.editText2);
    }

    public void save(View view) {
//        File newFile = new File(getFilesDir(), EditName.getText().toString());
//        try {
//            newFile.createNewFile();
////            newFile.
//            Toast.makeText(this, "File "+EditName.getText().toString() + " created", Toast.LENGTH_SHORT).show();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        FileOutputStream outputStream = null;
        try {
            outputStream = openFileOutput(EditName.getText().toString(), MODE_PRIVATE);
            outputStream.write(EditContent.getText().toString().getBytes());
            Toast.makeText(this, "File "+EditContent.getText().toString() + " created", Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null)
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        finish();

    }
}
