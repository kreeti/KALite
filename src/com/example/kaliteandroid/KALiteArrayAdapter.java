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

public class KALiteArrayAdapter extends ArrayAdapter<String> {
	
	private static List<String> items;	
	public boolean isExist;
	public boolean isVideo;
	public KALiteArrayAdapter(Context context, List<String> subject) {
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
         
       String details = items.get(position);         
        if(details != null) {            
            TextView textView = (TextView)v.findViewById(R.id.textView);
            if(!isExist)textView.setTextColor(Color.GRAY);
            if(textView != null) 
            	textView.setText(details);
            ImageView imageView = (ImageView)v.findViewById(R.id.imageView);
            if(!isVideo){
            	imageView.setImageResource(drawable.ic_action_next);
            }else{
            	imageView.setImageResource(drawable.ic_launcher);
            }
            
            
        }
         
        return v;
    }
}
