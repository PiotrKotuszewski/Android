package com.example.prm02;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import yuku.ambilwarna.AmbilWarnaDialog;

public class OptionsActivity extends AppCompatActivity {

    private int defaultColor = Color.BLACK;
    private int fontSize = 0;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Intent intent = getIntent();

        if (intent != null) {
            defaultColor = intent.getIntExtra("defaultColor", Color.BLACK);
            fontSize = intent.getIntExtra("fontSize", 0);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.navigation_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.item1) {
            openColorPicker();
            toastMessage("Color picker opened");
            return true;
        }else if (item.getItemId() == R.id.item2){
            Intent intent = new Intent(OptionsActivity.this, ChangeSizeActivity.class);
            intent.putExtra("fontSize", fontSize);
            startActivity(intent);
            toastMessage("Font size changer opened");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void openColorPicker(){
        AmbilWarnaDialog colorPicker = new AmbilWarnaDialog(this, defaultColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                defaultColor = color;
                Intent intent = new Intent(OptionsActivity.this, MainActivity.class);
                intent.putExtra("defaultColor", defaultColor);
                startActivity(intent);

            }
        });
        colorPicker.show();
    }
    public void toastMessage(String msg){
        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_LONG);
        toast.show();
    }
}
