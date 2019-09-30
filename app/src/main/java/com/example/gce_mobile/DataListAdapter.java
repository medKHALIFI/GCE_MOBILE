package com.example.gce_mobile;



/**
 * Created by ADJ on 8/8/2017.
 */


import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


public class DataListAdapter extends ArrayAdapter<String> {
    private String[] uId;
    private String[] uNames;
    private String[] uImages;
    private String[] uMission;
    private String[] uLocations;
    private Activity context;

    public DataListAdapter(Activity context, String[] uId, String[] uNames,String[] uMission,String[] uLocations ,String[] uImages) {
        super(context, R.layout.list_row, uId);
        this.context = context;
        this.uId = uId;
        this.uNames = uNames;
        this.uMission = uMission;
        this.uLocations = uLocations;
        this.uImages = uImages;

        // view data
        Log.e("uName","ser image : "+uNames[0]);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list_row, null, true);
        TextView textViewId = (TextView) listViewItem.findViewById(R.id.tv_uid);
        TextView textViewName = (TextView) listViewItem.findViewById(R.id.tv_uname);
        TextView textViewMission = (TextView) listViewItem.findViewById(R.id.tv_umission);

        TextView textViewLocation = (TextView) listViewItem.findViewById(R.id.tv_ulocation);
        ImageView iv = (ImageView)listViewItem.findViewById(R.id.imageView3);



        Log.d("mylog", "before while : "+position+" "+uId[position]);
        textViewId.setText(uId[position]);
        textViewName.setText(uNames[position]);
        textViewMission.setText(uMission[position]);
        textViewLocation.setText(uLocations[position]);
        // Uri uri = Uri.parse(uImages[position]);
        //Uri uri = Uri.parse("https://drive.google.com/uc?id=0B___GhMLUVtOY09SbDU5cDU2T1U");
        // draweeView.setImageURI(uri);

        Picasso.with(context).load(uImages[position]).into(iv);

        return listViewItem;
    }
}