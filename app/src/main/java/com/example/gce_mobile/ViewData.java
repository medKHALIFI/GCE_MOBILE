package com.example.gce_mobile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.media.CamcorderProfile.get;

public class ViewData extends AppCompatActivity {



    ListView listview ;
   // String mTitle[] = {"facebook","whatsapp","Twitter","Instagrame","Youtube"};
   // String mDescription[] = {"facebook desc","whatsapp desc","Twitter desc","Instagrame desc","Youtube desc"} ;
    //int images[] = {R.drawable.logogce ,R.drawable.logogce,R.drawable.logogce,R.drawable.logogce,R.drawable.logogce };


    // test
    // for location image
    ArrayList<String> mLocationImage = new ArrayList<String>();
    ArrayList<String> mTitle = new ArrayList<String>();
    ArrayList<String> mDescription = new ArrayList<String>();
    ArrayList<String> images =new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_data);


        // test get data from database
        //get data from sqlite
        Cursor cursor =MainActivity.sqLiteHelper.getData("SELECT * FROM MISSION");

        //cursor.moveToFirst() ;
        Log.d("mylog","before while : ");

        while (cursor.moveToNext())
        //for(int i =0 ;i< 5;i++)
        {
            Log.d("mylog","inside while : ");
            //int id = cursor.getInt(0);
            String localisation =  cursor.getString(1);
            String name = cursor.getString(2);
            String mission = cursor.getString(3);
            String path = cursor.getString(4);
            //mTitle[i] = name ;


            mLocationImage.add(localisation);
            mTitle.add(name); //this adds an element to the list.
            mDescription.add(mission); //this adds an element to the list.
            images.add(path); //this adds an element to the list.



        }


        Log.d("mylog","out while : ");
        //Toast.makeText(getApplicationContext(),"test",Toast.LENGTH_SHORT).show();



        //


        listview = findViewById(R.id.listView);
        // now create an adapter
        MyAdapter adapter = new MyAdapter(this , mLocationImage,mTitle,mDescription,images);
        listview.setAdapter(adapter);




        // now set item click on list view
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String latLog = mLocationImage.get(position) ;

                String[] parts = latLog.split(",");
                String part1 = parts[0]; // lat
                String part2 = parts[1]; // long





                // go to the map
                Intent intent = new Intent(ViewData.this ,MapsActivityViewPhoto.class);
                // passing location

                intent.putExtra("lat",part1);
                intent.putExtra("log",part2);

                startActivity(intent);

/*
                if(position ==0){

                    Toast.makeText(ViewData.this,"desc "+mLocationImage.get(position),Toast.LENGTH_SHORT).show();
                }
                if(position ==1){
                    Toast.makeText(ViewData.this,"wathsapp desc ",Toast.LENGTH_SHORT).show();
                }
                */

            }
        });
    }

    class MyAdapter extends ArrayAdapter<String>{
        Context context ;
        //String rTitle[];
        ArrayList<String> rLocationImage ;
        ArrayList<String> rTitle ;
        ArrayList<String> rDescription;
        ArrayList<String>  rImage;

        MyAdapter(Context c,ArrayList<String> locationImage, ArrayList<String> title,ArrayList<String> description,   ArrayList<String> images){
            super(c,R.layout.row,R.id.textView1,title);
            this.context = c;

            // test
            Log.d("mylog","inside adapter : ");

            this.rLocationImage = locationImage;
            this.rTitle = title;
            this.rDescription =description;
            this.rImage=images ;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater =(LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View row  = layoutInflater.inflate(R.layout.row, parent,false);
            ImageView images =row.findViewById(R.id.image);
            TextView myTitle = row.findViewById(R.id.textView1);
            TextView myDescription = row.findViewById(R.id.textView2);
            TextView myLocationImage = row.findViewById(R.id.textView3);

            // now set our resources on views
            //images.setImageResource(rImage[position]);
            // test path image
            Bitmap bitmap = BitmapFactory.decodeFile(rImage.get(position));

            images.setImageBitmap(bitmap);

            myTitle.setText(rTitle.get(position)) ;
            myDescription.setText(rDescription.get(position));
            myLocationImage.setText(rLocationImage.get(position));


            return row;
        }
    }
}
