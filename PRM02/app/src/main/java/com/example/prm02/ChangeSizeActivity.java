package com.example.prm02;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class ChangeSizeActivity extends AppCompatActivity {
    private Button sizeBtn;
    private EditText fontSize;
    private int font = 0;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_size);

        sizeBtn = findViewById(R.id.btn_changeSize);
        fontSize = findViewById(R.id.fontSize);
        Intent intent = getIntent();

        if(intent != null)
            font = intent.getIntExtra("fontSize", 0);

        if(font != 0)
            fontSize.setText(font);
        else
            fontSize.setText("8");

        sizeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChangeSizeActivity.this, MainActivity.class);
                intent.putExtra("fontSize", Integer.valueOf(fontSize.getText().toString()));
                startActivity(intent);
            }
        });


    }
}
