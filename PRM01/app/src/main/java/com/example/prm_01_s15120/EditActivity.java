package com.example.prm_01_s15120;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class EditActivity extends AppCompatActivity {

    private String name;
    private double price;
    private EditText inputName;
    private EditText inputPrice;
    private ImageButton editButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        getSupportActionBar().setTitle("Made by Piotr Kotuszewski, S15120");

        if(getIntent().getExtras() != null) {
            name = getIntent().getStringExtra("Name");
            price = getIntent().getDoubleExtra("Price", 0);

            inputName = findViewById(R.id.nameInput);
            inputPrice = findViewById(R.id.priceInput);

            inputName.setText(name, TextView.BufferType.EDITABLE);
            inputPrice.setText(String.valueOf(price), TextView.BufferType.EDITABLE);
        }

        editButton = findViewById(R.id.editButton);
        //Edit Borrower
        editButton.setOnClickListener(v ->{
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("NameBeforeEdit", name);
            intent.putExtra("EditedName", inputName.getText().toString());
            intent.putExtra("EditedPrice", Double.valueOf(inputPrice.getText().toString()));
            intent.putExtra("UniqId", "EditActivityClass");

            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle("Jestes pewien ?")
                    .setMessage("Na pewno chcesz edytowac tego dluzniak ?")
                    .setPositiveButton("Tak", null)
                    .setNegativeButton("Nie", null)
                    .show();

            Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            positiveButton.setOnClickListener(v1 -> startActivity(intent));
        });
    }
}
