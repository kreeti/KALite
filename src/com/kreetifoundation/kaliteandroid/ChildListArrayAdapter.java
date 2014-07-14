/*
 *	Created by Nabarun Banerjee on 11/07/14.
 *  Copyright (c) 2014 Kreeti Technologies. All rights reserved.
 */

package com.kreetifoundation.kaliteandroid;
import java.io.File;
import java.util.List;

import com.example.kaliteandroid.R;
import com.example.kaliteandroid.R.drawable;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ChildListArrayAdapter extends ArrayAdapter<VideoModelNode> {	
	private static List<VideoModelNode> items;		
	public String fileDirectoryBasePath;
	
	public ChildListArrayAdapter(Context context, List<VideoModelNode> nodes) {
		 super(context, R.layout.list_cell);
		 items = nodes;
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
         
       VideoModelNode node = items.get(position);   
       
        if(node != null) {            
            TextView textView = (TextView)v.findViewById(R.id.textView);
            if(textView != null) 
            	textView.setText(node.title);
            
            ImageView imageView = (ImageView)v.findViewById(R.id.imageView);
            
            if(node.children != null) {
            	imageView.setImageResource(drawable.ic_action_next);            	        	
            } else {
            	imageView.setImageResource(drawable.video_icon_active); 
            	File file = new File(fileDirectoryBasePath+"videos/"+node.videoFileName);
            
            	if(node.isVideoURLExist && !file.exists()) {
            		textView.setTextColor(Color.GRAY); 
            		imageView.setImageResource(drawable.video_icon_inactive);
                } else if(node.isVideoURLExist && file.exists()) {
                	textView.setTextColor(Color.BLACK);
                } else {
                	textView.setTextColor(Color.LTGRAY);
                	imageView.setImageResource(drawable.video_icon_inactive);
                }            	
            } 
        }
         
        return v;
    }
 }
