package com.example.gce_mobile;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MissionListAdapter extends BaseAdapter {

    private Context context ;
    private int layout ;
    private ArrayList<Mission> missionsList ;

    public MissionListAdapter(Context context, int layout, ArrayList<Mission> missionsList) {
        this.context = context;
        this.layout = layout;
        this.missionsList = missionsList;
        int a = missionsList.size() ;
        String temp  = String.valueOf(a) ;
        Log.d("gce_test_adapter", temp);
        //Log.d("gce", "test logcat inside adapter");


    }

    @Override
    public int getCount() {
        return missionsList.size();
    }

    @Override
    public Object getItem(int position) {
        return missionsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    private class ViewHolder{
        ImageView imageView ;
        TextView txtName,txtLocation ;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View row = view ;
        ViewHolder holder = new ViewHolder();
        if(row == null){
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout,null);
            holder.txtName =(TextView) row.findViewById(R.id.textName);
            holder.txtLocation =(TextView) row.findViewById(R.id.textLocation);
            holder.imageView =(ImageView) row.findViewById(R.id.imageView);
            row.setTag(holder);

        }else{
            holder = (ViewHolder) row.getTag() ;
        }
        Mission mission = missionsList.get(position);
        holder.txtName.setText(mission.getName());
        Log.d("gce_test_adapter_name", mission.getName().toString());
        holder.txtLocation.setText(mission.getLocation());

        String missionImage = mission.getImage();
        Log.d("gce_test_adapter_image", mission.getImage().toString());
        // test path image
        Bitmap bitmap = BitmapFactory.decodeFile(missionImage);


        //Bitmap bitmap = BitmapFactory.decodeByteArray(missionImage,0,missionImage.length);
        holder.imageView.setImageBitmap(bitmap);


        Log.d("gce_test","get view : "+missionImage );
        return row;
    }
}
