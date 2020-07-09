package com.example.prm02.DbConfiguration;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.prm02.Model.ImageModel;

public class MyDbHandler extends SQLiteOpenHelper {
    private SQLiteDatabase mWritableDB;
    private SQLiteDatabase mReadableDB;
    private String imageDescription;

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "imageDB.db";
    private static final String TABLE_NAME = "Image";
    private static final String COLUMN_ID = "ImageID";
    private static final String COLUMN_DESCRIPTION = "ImageDescription";
    private static final String COLUMN_NAME = "ImageName";

    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            +COLUMN_DESCRIPTION+ " TEXT," + COLUMN_NAME + " TEXT )";

    public MyDbHandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addImage(ImageModel imageModel){
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_DESCRIPTION, imageModel.getDescription());
        contentValues.put(COLUMN_NAME, imageModel.getImageName());
        mWritableDB = this.getWritableDatabase();
        mWritableDB.insert(TABLE_NAME, null, contentValues);
        mWritableDB.close();
    }

    public String getImageDescriptionByImageName(String imageName){
        Cursor cursor = null;

        if(mReadableDB == null){
            mReadableDB = getReadableDatabase();
        }
        cursor = mReadableDB.rawQuery("SELECT "+ COLUMN_DESCRIPTION+" FROM " + TABLE_NAME + " WHERE " + COLUMN_NAME + "=" + "'"+ imageName+"';", null);

        if (cursor.moveToNext()){
            do{
                imageDescription = cursor.getString(0);
            }while (cursor.moveToNext());
        }
        System.out.println(imageDescription + "IMAGEDESCRITPASDADAD");

        cursor.close();
        mReadableDB.close();

        return imageDescription;
    }
}
