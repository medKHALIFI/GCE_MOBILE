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


        //get data from sqlite
        Cursor cursor =MainActivity.sqLiteHelper.getData("SELECT * FROM MISSION");
        list.clear();


        //cursor.moveToFirst();
        //cursor.moveToFirst();
        //int temp = 0 ;  //cursor.getInt(0);



        //cursor.moveToFirst();






        while (cursor.moveToNext()) {

                    int id = cursor.getInt(0);
                    String name = cursor.getString(1);
                    String mission =  cursor.getString(2);
                    String path = cursor.getString(3);

                    list.add(new Mission(name, mission, path, id));

                  // Toast.makeText(getApplicationContext(),"NUMBER OF COLUMN  : "+cursor.getColumnCount(),Toast.LENGTH_SHORT).show();

                   // column names
                  /*
                   Toast.makeText(getApplicationContext(),"index 0  : "+cursor.getColumnName(0) ,Toast.LENGTH_SHORT).show();
                   Toast.makeText(getApplicationContext(),"index 1  : "+cursor.getColumnName(1) ,Toast.LENGTH_SHORT).show();
                   Toast.makeText(getApplicationContext(),"index 2  : "+cursor.getColumnName(2) ,Toast.LENGTH_SHORT).show();
                   Toast.makeText(getApplicationContext(),"index 3  : "+cursor.getColumnName(3) ,Toast.LENGTH_SHORT).show();
*/


                   // todo

                   //String name = cursor.getString(cursor.getColumnIndex("path"));
                  Toast.makeText(getApplicationContext(),cursor.getColumnName(3)+" : "+cursor.getString(cursor.getColumnIndex("path")),Toast.LENGTH_SHORT).show();




                 //  temp = cursor.getColumnIndex("id");
                   //Toast.makeText(getApplicationContext(),"id  : "+temp+" : "+cursor.getInt(temp),Toast.LENGTH_SHORT).show();


                  /* temp = cursor.getColumnIndex("name");
                   Toast.makeText(getApplicationContext(),"name : "+temp+" : "+cursor.getString(temp),Toast.LENGTH_SHORT).show();



                   temp = cursor.getColumnIndex("mission");
                   Toast.makeText(getApplicationContext(),"mission : "+temp+" : "+cursor.getString(temp),Toast.LENGTH_SHORT).show();


                   temp = cursor.getColumnIndex("path");
                   Toast.makeText(getApplicationContext(),"image : "+temp+" : "+cursor.getString(temp),Toast.LENGTH_SHORT).show();
               */

                }
           // Toast.makeText(getApplicationContext(),"COUNT  : "+cursor.getCount(),Toast.LENGTH_LONG).show();

            Toast.makeText(getApplicationContext(),"out of while ",Toast.LENGTH_LONG).show();

        //adapter= new CustomAdapter(dataModels,view.getContext());
        //adapter= new MissionListAdapter(this.getApplicationContext(),1,list);



        int a = list.size() ;
        String temp  = String.valueOf(a) ;
        Log.d("gce_test_mission", temp);

        adapter= new MissionListAdapter(this, R.layout.mission_items,list);
        gridView.setAdapter(adapter);

        adapter.notifyDataSetChanged();


        Toast.makeText(getApplicationContext(),"COUNT  : "+cursor.getCount(),Toast.LENGTH_LONG).show();









    }
}
