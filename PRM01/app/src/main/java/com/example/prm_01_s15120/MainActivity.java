package com.example.prm_01_s15120;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private ImageButton button;
    private ImageButton sumButton;
    private String name;
    private double price;
    private String editedName;
    private double editedPrice;
    private String nameBeforeEdit;
    private ArrayList<String> arrayList = new ArrayList<>();
    private Map<String, Double> borrowers = DataHolder.getInstance().borrowers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Made by Piotr Kotuszewski, S15120");


        if(getIntent().getExtras() != null) {
            String intentData = getIntent().getExtras().getString("UniqId");
            if(intentData.equals("AddActivityClass")) {
                name = getIntent().getStringExtra("NewName");
                price = getIntent().getDoubleExtra("NewPrice", 0);

                borrowers.put(name, price);
            }else if(intentData.equals("EditActivityClass")) {
                editedName = getIntent().getStringExtra("EditedName");
                editedPrice = getIntent().getDoubleExtra("EditedPrice", 0);
                nameBeforeEdit = getIntent().getStringExtra("NameBeforeEdit");

                borrowers.remove(nameBeforeEdit);
                borrowers.put(editedName, editedPrice);
            }
        }

        listView = findViewById(R.id.listView);
        button = findViewById(R.id.addButton);
        sumButton = findViewById(R.id.sumButton);

        borrowers.put("Jan", 200.0);
        borrowers.put("Ban", 200.0);
        borrowers.put("Kar", 200.0);

        // Convert Map to Array
        for(Map.Entry<String, Double> entry : borrowers.entrySet()){
            String key = entry.getKey();
            Double value = entry.getValue();
            arrayList.add(key+"         "+value+" zl");
        }

        //Add list to ListView
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(arrayAdapter);

        //Edit Borrower
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(MainActivity.this, EditActivity.class);
            System.out.println(listView.getItemAtPosition(position).toString());
            String key = String.valueOf(borrowers.keySet().toArray()[position]);
            intent.putExtra("Name", key);
            intent.putExtra("Price", borrowers.get(key));
            startActivity(intent);
        });

        //Delete Borrower
        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            final int which_item = position;
            String key = String.valueOf(borrowers.keySet().toArray()[which_item]);

            new AlertDialog.Builder(MainActivity.this)
                    .setIcon(android.R.drawable.ic_delete)
                    .setTitle("Jestes pewien ?")
                    .setMessage("Chcesz umozyc ten dlug ?")
                    .setPositiveButton("Tak", (dialog, which) -> {
                        borrowers.remove(key);
                        arrayList.remove(which_item);
                        arrayAdapter.notifyDataSetChanged();
                    })
                    .setNegativeButton("Nie", null)
                    .show();
            return true;
        });

        //Add Borrower
        button.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddActivity.class);
            startActivity(intent);
        });

        //Sum borrower price
        sumButton.setOnClickListener(v -> {
            double sum = 0 ;

            for(Map.Entry<String, Double> entry : borrowers.entrySet()) {
                Double value = entry.getValue();
                sum+=value;
            }
            AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
            alertDialog.setTitle("Suma");
            alertDialog.setMessage("Suma dlugow: "+sum+" zl");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alertDialog.show();
        });
    }
}
