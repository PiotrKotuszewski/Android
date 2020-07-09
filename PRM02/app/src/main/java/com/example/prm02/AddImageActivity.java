package com.example.prm02;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.prm02.DbConfiguration.MyDbHandler;
import com.example.prm02.Model.ImageModel;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddImageActivity extends OptionsActivity {

    private Button btnSave;
    private ImageView imageDisplay;
    private EditText editText;
    private static final int REQUEST_CODE_LOCATION_PERMISSION = 1;
    private double latitude;
    private double longitude;
    private String cityName;
    private String countryName;
    private Canvas canvas;
    private Bitmap newImage;
    private Bitmap bitmap;
    private String description;
    private String fileName;
    private MyDbHandler myDbHandler;
    private int colorName = 0;
    private int fontSize = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_image);
        btnSave = findViewById(R.id.btn_saveImage);
        editText = findViewById(R.id.editText);
        imageDisplay = findViewById(R.id.imageCapture);

        Intent intent = getIntent();

        if(intent != null) {
            colorName = intent.getIntExtra("defaultColor", 0);
            fontSize = intent.getIntExtra("fontSize", 0);
        }

        openCamera();
        startLoc();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveToInternalStorage(newImage);
                toastMessage("Image saved");
                description = String.valueOf(editText.getText());
                myDbHandler = new MyDbHandler(AddImageActivity.this, null, null, 1);
                ImageModel imageModel = new ImageModel(description, fileName);
                myDbHandler.addImage(imageModel);
                Intent intent = new Intent(AddImageActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            bitmap = (Bitmap) data.getExtras().get("data");

            Date currentTime = Calendar.getInstance().getTime();


            Paint paintText = new Paint(Paint.ANTI_ALIAS_FLAG);
            if(colorName == 0)
                paintText.setColor(Color.BLACK);
            else
                paintText.setColor(colorName);

            if(fontSize == 0)
                paintText.setTextSize(8);
            else
                paintText.setTextSize(fontSize);

            paintText.setStyle(Paint.Style.FILL);
            paintText.setShadowLayer(10f, 10f, 10f, Color.BLACK);

            newImage = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
            canvas = new Canvas(newImage);
            canvas.drawBitmap(bitmap, 0f, 0f, null);
            canvas.drawText(cityName+", "+countryName, 5, 10, paintText);
            canvas.drawText(String.valueOf(currentTime), 40,150, paintText);


            imageDisplay.setImageBitmap(newImage);
        }

        private String saveToInternalStorage(Bitmap bitmap){
            ContextWrapper cw = new ContextWrapper(getApplicationContext());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
            fileName = sdf.format(new Date());
            File dir = cw.getDir("imageDir", Context.MODE_PRIVATE);
            File myPath = new File(dir, fileName+".jpg");
            FileOutputStream fos = null;
            try{
                fos = new FileOutputStream(myPath);

                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }finally {
                {
                    try{
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return dir.getAbsolutePath();
        }

    public void toastMessage(String msg){
        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_LONG);
        toast.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_CODE_LOCATION_PERMISSION && grantResults.length > 0){
            getCurrentLocation();
        }else{
            Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
        }
    }

    private void getCurrentLocation(){
        final LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationServices.getFusedLocationProviderClient(AddImageActivity.this)
                .requestLocationUpdates(locationRequest, new LocationCallback(){

                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        super.onLocationResult(locationResult);
                        LocationServices.getFusedLocationProviderClient(AddImageActivity.this)
                                .removeLocationUpdates(this);

                        if(locationResult != null && locationResult.getLocations().size()> 0){
                            int lastestLocationIndex = locationResult.getLocations().size() - 1;
                            latitude = locationResult.getLocations().get(lastestLocationIndex).getLatitude();
                            longitude = locationResult.getLocations().get(lastestLocationIndex).getLongitude();
                            getCityName(latitude, longitude);
                        }
                    }
                }, Looper.getMainLooper());
    }

    public void getCityName(double latitude, double longitude){
        Geocoder geocoder = new Geocoder(this);
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 10);
            cityName = addresses.get(0).getLocality();
            countryName = addresses.get(0).getCountryName();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openCamera(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 100);
    }

    public void startLoc(){
        if (ContextCompat.checkSelfPermission(
                getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    AddImageActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_CODE_LOCATION_PERMISSION
            );
        } else {
            getCurrentLocation();
        }
    }
}
