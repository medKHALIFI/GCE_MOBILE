package com.example.gce_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.os.Environment.getExternalStoragePublicDirectory;

public class MainActivity extends AppCompatActivity {

    @Override
    // get picture
    // https://www.youtube.com/watch?v=32RG4lR0PMQ
    // get location
    //https://www.youtube.com/watch?v=QNb_3QKSmMk
    // save image to db
    //https://www.youtube.com/watch?v=C21N5mLvQiU


    Button btnTakePic ;
    ImageView imageView ;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnTakePic =findViewById(R.id.btnTakePic);


        // le traitement quand on clique sur le button
        btnTakePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchPictureTakeAction();

            }
        });
        imageView = findViewById(R.id.image);



    }
    private void dispatchPictureTakeAction(){
        Intent takePic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE) ;
        if (takePic.resolveActivity(getPackageManager()) != null)) {
            File photoFile = null ;
            try{

            }catch(Exception){
                photoFile = createPhotoFile();
            }
        }
    }

    private File createPhotoFile(){
        String name = new SimpleDateFormat("yyyymmdd").format(new Date());
        File storageDir = getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = null ;
        try {
             image = File.createTempFile(name,".jpg",storageDir);
        } catch (IOException e) {
            Log.d("mylog","Error : "+ e.toString());

        }
        return  image ;

    }

}
