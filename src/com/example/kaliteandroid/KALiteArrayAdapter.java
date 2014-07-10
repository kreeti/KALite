package com.example.kaliteandroid;
import java.io.File;
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
	public String fileDirectoryBasePath;
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
            File file = new File(fileDirectoryBasePath+"videos/"+details.videoFileName);
            
            
            if(textView != null) 
            	textView.setText(details.title);
            ImageView imageView = (ImageView)v.findViewById(R.id.imageView);
            if(details.isChildExist){
            	imageView.setImageResource(drawable.ic_action_next);            	        	
            }else{
            	imageView.setImageResource(drawable.ic_launcher);   
            	if(details.videoFileName != null && !details.videoFileName.isEmpty()){        			
        			if(!file.exists())
        				textView.setTextColor(Color.GRAY);
        			
                }else{
                	textView.setTextColor(Color.LTGRAY);
                }
            	
            }
            /*if(!details.isVideoURLExist && details.videoFileName == null){
            	imageView.setImageResource(drawable.ic_action_next);            	        	
            }else{
            	imageView.setImageResource(drawable.ic_launcher);    
            }*/
            
            
        }
         
        return v;
    }
}
