package com.example.gce_mobile;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

public class SQLiteHelper extends SQLiteOpenHelper {
    public SQLiteHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public void queryData (String sql){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }


    // insert data to db
    public void insertData(String localisation, String  name , String mission, String path){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "insert into MISSION VALUES (NULL,?,?,?,?)" ;
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        // streem data
        //statement.bindint(1,localisation);
        statement.bindString(1,localisation);
        statement.bindString(2,name);
        statement.bindString(3, mission);
        statement.bindString(4,path);

        statement.executeInsert();

    }

    public void delete(int id) {
        SQLiteDatabase database = getReadableDatabase();
        database.execSQL("delete from MISSION where Id='"+id+"'");
    }

    // get all the data from db
    public Cursor getData(String sql){
        SQLiteDatabase database = getReadableDatabase();

        Cursor temp =  database.rawQuery(sql,null);

        return temp ;
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
