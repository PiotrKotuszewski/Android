package com.example.prm_01_s15120;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class AddActivity extends AppCompatActivity {

    private String name;
    private double price;
    private EditText nameInput;
    private EditText priceInput;
    private ImageButton addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        nameInput = findViewById(R.id.nameInput);
        priceInput = findViewById(R.id.priceInput);
        addButton = findViewById(R.id.addButton);

        //Add Borrower
        addButton.setOnClickListener(w -> {
            name = nameInput.getText().toString();
            price = Double.parseDouble(priceInput.getText().toString());

            Intent intent = new Intent(AddActivity.this, MainActivity.class);
            intent.putExtra("NewName", name);
            intent.putExtra("NewPrice", price);
            intent.putExtra("UniqId", "AddActivityClass");
            startActivity(intent);
        });

    }
}
