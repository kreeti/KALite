package com.example.kaliteandroid;
import java.util.List;

import com.example.kaliteandroid.R.drawable;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class KALiteArrayAdapter extends ArrayAdapter<VideoModelClass> {
	
	private static List<VideoModelClass> items;		
	public boolean isVideoFileExist;
	public KALiteArrayAdapter(Context context, List<VideoModelClass> subject) {
		 super(context, R.layout.list_cell);
		 items = subject;
		// TODO Auto-generated constructor stub
	}
	@Override
    public int getCount() {
        return items.size();
    }
     
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
         
        if(v == null) {
            LayoutInflater li = LayoutInflater.from(getContext());
            v = li.inflate(R.layout.list_cell, null);           
        }
         
       VideoModelClass details = items.get(position);         
        if(details != null) {            
            TextView textView = (TextView)v.findViewById(R.id.textView);
            if(!isVideoFileExist)textView.setTextColor(Color.GRAY);
            if(textView != null) 
            	textView.setText(details.title);
            ImageView imageView = (ImageView)v.findViewById(R.id.imageView);
            if(!details.isVideoURLExist && details.videoFileName == null){
            	imageView.setImageResource(drawable.ic_action_next);            	        	
            }else{
            	imageView.setImageResource(drawable.ic_launcher);    
            }
            
            
        }
         
        return v;
    }
}
