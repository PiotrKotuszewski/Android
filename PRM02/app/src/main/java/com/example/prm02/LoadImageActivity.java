package com.example.prm02;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class LoadImageActivity extends OptionsActivity {

    private static final String IMAGE_FOLDER_PATH = "/data/data/com.example.prm02/app_imageDir";
    private ListView listView;
    private List<String> filesList;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_image);

        listView = findViewById(R.id.listView);
        filesList = new ArrayList<>();

        File folder = new File(IMAGE_FOLDER_PATH);
        File[] files = folder.listFiles();

        for (File file : files) {
            if (file.isFile()) {
                filesList.add(file.getName());
            }
        }

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, filesList);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(LoadImageActivity.this, OpenImageActivity.class);
            intent.putExtra("fileName", filesList.get(position));
            startActivity(intent);
        });
    }

    public void toastMessage(String msg){
        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_LONG);
        toast.show();
    }
}
