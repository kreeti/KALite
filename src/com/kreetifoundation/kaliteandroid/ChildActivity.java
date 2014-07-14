/*
 *	Created by Nabarun Banerjee on 11/07/14.
 *  Copyright (c) 2014 Kreeti Technologies. All rights reserved.
 */
package com.kreetifoundation.kaliteandroid;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.example.kaliteandroid.R;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class ChildActivity extends BaseActivity{	
	ChildActivity context;
	int lastDisplayedPosition = 0;
	List<JSONObject>lastDisplayedJsonObj = new ArrayList<JSONObject>();	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		context = this;
		setContentView(R.layout.activity_child);		
    	showChooser("Choose a JSON file");	
    	dialog = ProgressDialog.show(this, "", "Loading...");
    	ListView listView = (ListView)findViewById(R.id.list);
    	
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
		    @Override
		    public void onItemClick(AdapterView<?> av, View v, int pos, long id) {		    	
		    	if(currentNode.children.size() > 0) {	
		    		VideoModelNode j = currentNode.children.get(pos);
		    		
		    		if(j.children != null) {
		    			currentNode = j;
		    			setListAdapter();
		    		} else if(j.videoFileName != null && !j.videoFileName.isEmpty()) {		    			
		    			File file = new File(fileDirectoryBasePath+"videos/"+ j.videoFileName);
		    			
		            	if(file.exists()) {
		            		Intent videoPlayerIntent = new Intent(ChildActivity.this, VideoPlayerActivity.class);	
				    		videoPlayerIntent.putExtra("videoFileName", fileDirectoryBasePath+"videos/" + j.videoFileName);
				    		ChildActivity.this.startActivity(videoPlayerIntent);
		            	}		    				
		    		}	    			
		    		 	
		    	}	
		    			    	
		    }
		});		
		
	}
	
	
	 @Override 
     public void onBackPressed() { 		
		if(currentNode.parent == null) {			 
			super.onBackPressed();		 	
		 } else {
			 currentNode = currentNode.parent;
			 setListAdapter();
		 }			 
		 
     } 
	
	 @Override 
		protected void onSaveInstanceState(Bundle icicle) {	      
		      super.onSaveInstanceState(icicle);
		    }
	
}