package com.course.sdmemory;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    private EditText et_date, et_description;
    private static final String DATA_SUCCESSFULLY_SAVED = "The data were successfully saved!";
    private static final String DATA_NOT_SAVED = "The data was not  saved!";
    private static final String FILE_NOT_FOUND = "File cannot find";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_date = findViewById(R.id.et_date);
        et_description = findViewById(R.id.et_result);
    }

    public void save(View v) {
        String file_name = et_date.getText().toString();
        String body = et_description.getText().toString();

        try {
            File sdCard = Environment.getExternalStorageDirectory();
            Toast.makeText(this, sdCard.getAbsolutePath(), Toast.LENGTH_LONG).show();
            File file = new File(sdCard.getAbsolutePath(), file_name);
            OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(file));
            osw.write(body);
            osw.flush();
            osw.close();
            Toast.makeText(this, DATA_SUCCESSFULLY_SAVED, Toast.LENGTH_SHORT).show();

        } catch (IOException ex) {
            Toast.makeText(this, DATA_NOT_SAVED, Toast.LENGTH_SHORT).show();
        }
    }

    public void recover(View v) {
        String file_name = et_date.getText().toString();
        File card = Environment.getExternalStorageDirectory();
        File file = new File(card.getAbsolutePath(), file_name);

        try {
            FileInputStream fin = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fin);
            BufferedReader br = new BufferedReader(isr);

            String line = br.readLine();
            String body = "";
            while ( line != null ) {
                body = body + line + "";
                line = br.readLine();
            }

            br.close();
            isr.close();
            et_description.setText(body);
        } catch (IOException ioex ) {
            Toast.makeText(this, FILE_NOT_FOUND, Toast.LENGTH_SHORT).show();
        }
    }

}
