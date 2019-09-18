package com.example.gce_mobile;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MissionList extends AppCompatActivity {
    GridView gridView;
    ArrayList<Mission> list;
    MissionListAdapter adapter = null ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.mission_list_activity);
        gridView = (GridView) findViewById(R.id.gridView);
        list = new ArrayList<>();
        adapter= new MissionListAdapter(this, R.layout.mission_items,list);
        gridView.setAdapter(adapter);

        //get data from sqlite
        Cursor cursor =MainActivity.sqLiteHelper.getData("SELECT name , mission FROM MISSION");
        list.clear();


        //cursor.moveToFirst();
        //cursor.moveToFirst();
        int temp = 0 ;  //cursor.getInt(0);



        cursor.moveToFirst();

        if(!cursor.isAfterLast())
        {
               while (cursor.moveToNext()) {
                   /* int id = 0 ;//cursor.getInt(-1);
                    String name = cursor.getString(1);
                    String mission =  cursor.getString(2);
                    byte[] image = cursor.getBlob(3);

                    list.add(new Mission(name, mission, image, id));
            */
                  // Toast.makeText(getApplicationContext(),"NUMBER OF COLUMN  : "+cursor.getColumnCount(),Toast.LENGTH_SHORT).show();
                   //Toast.makeText(getApplicationContext(),"COUNT  : "+cursor.getCount(),Toast.LENGTH_SHORT).show();

                 /*  Toast.makeText(getApplicationContext(),"index 0  : "+cursor.getColumnName(0) ,Toast.LENGTH_SHORT).show();
                   Toast.makeText(getApplicationContext(),"index 1  : "+cursor.getColumnName(1) ,Toast.LENGTH_SHORT).show();
                   Toast.makeText(getApplicationContext(),"index 2  : "+cursor.getColumnName(2) ,Toast.LENGTH_SHORT).show();
                   Toast.makeText(getApplicationContext(),"index 3  : "+cursor.getColumnName(3) ,Toast.LENGTH_SHORT).show();

                  */

                   // todo

                   String name = cursor.getString(cursor.getColumnIndex("name"));
                   Toast.makeText(getApplicationContext(),cursor.getColumnName(0)+" : "+name,Toast.LENGTH_SHORT).show();




                  // temp = cursor.getColumnIndex("id");
                   //Toast.makeText(getApplicationContext(),"id  : "+temp,Toast.LENGTH_SHORT).show();



                   //temp = cursor.getColumnIndex("name");
                   //Toast.makeText(getApplicationContext(),"name : "+temp,Toast.LENGTH_SHORT).show();



                  // temp = cursor.getColumnIndex("mission");
                   //Toast.makeText(getApplicationContext(),"mission : "+temp,Toast.LENGTH_SHORT).show();


                   //temp = cursor.getColumnIndex("image");
                   //Toast.makeText(getApplicationContext(),"image : "+temp,Toast.LENGTH_SHORT).show();
                }
        } else{
            Log.v("MyTag", "There are no countries in the data set");
        }





        adapter.notifyDataSetChanged();

    }
}
