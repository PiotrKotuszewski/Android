package com.example.prm02;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.Toolbar;

public class MainActivity extends OptionsActivity{

    private int defaultColor;
    private int fontSize;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnCapture = findViewById(R.id.btn_captureImage);
        Button btnLoad = findViewById(R.id.btn_loadImage);

        Intent intent = getIntent();
        if(intent != null) {
            this.defaultColor = intent.getIntExtra("defaultColor", 0);
            this.fontSize = intent.getIntExtra("fontSize", 0);
        }

        btnCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddImageActivity.class);
                intent.putExtra("defaultColor", defaultColor);
                intent.putExtra("fontSize", fontSize);
                startActivity(intent);
            }
        });

        btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoadImageActivity.class);
                startActivity(intent);
            }
        });
    }

    public void toastMessage(String msg){
        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_LONG);
        toast.show();
    }
}
