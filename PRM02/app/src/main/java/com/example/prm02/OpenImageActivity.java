package com.example.prm02;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.prm02.DbConfiguration.MyDbHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class OpenImageActivity extends OptionsActivity {
    private static final String IMAGE_FOLDER_PATH = "/data/data/com.example.prm02/app_imageDir";
    private String fileName;
    private String description;
    private ImageView imageView;
    private TextView textView;
    private Bitmap bitmap = null;
    private MyDbHandler myDbHandler;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_image);
        Intent intent = getIntent();

        if(intent != null)
            fileName = intent.getStringExtra("fileName");

        imageView = findViewById(R.id.imageCapture);
        textView = findViewById(R.id.textView);

        myDbHandler = new MyDbHandler(OpenImageActivity.this, null, null, 1);
        description = myDbHandler.getImageDescriptionByImageName(fileName);
        File file = new File(IMAGE_FOLDER_PATH, fileName);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        options.inDither = false;
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        try {
            bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        textView.setText(description);
        imageView.setImageBitmap(bitmap);
    }
}
