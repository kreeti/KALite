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
		    	if(allChildrens.size() > 0){	
		    		lastDisplayedPosition++;
		    		lastDisplayedJsonObj.add(allChildrens.get(pos));
		    		JSONObject j = allChildrens.get(pos);
		    		if(j.has("children"))
		    			parseJSON(allChildrens.get(pos));
		    		else if(subject.get(pos).videoFileName != null && !subject.get(pos).videoFileName.isEmpty())	{		    			
		    			File file = new File(fileDirectoryBasePath+"videos/"+subject.get(pos).videoFileName);
		            	if(file.exists()){
		            		Intent videoPlayerIntent = new Intent(ChildActivity.this, VideoPlayerActivity.class);	
				    		videoPlayerIntent.putExtra("videoFileName", fileDirectoryBasePath+"videos/"+subject.get(pos).videoFileName);
				    		ChildActivity.this.startActivity(videoPlayerIntent);
		            	}		    				
		    		}	    			
		    		 	
		    	}	
		    			    	
		    }
		});		
		
	}
	
	
	 @Override 
     public void onBackPressed()    { 		
		lastDisplayedPosition--;	 	
		if(lastDisplayedPosition < 0){			 
			super.onBackPressed();		 	
		 }else{
			 if(lastDisplayedPosition == 0){
					parseJSON(baseJobj);
				}else{
					parseJSON(lastDisplayedJsonObj.get(lastDisplayedPosition));
				}
		 }			 
		 
     } 
	
	 @Override 
		protected void onSaveInstanceState(Bundle icicle) {	      
		      super.onSaveInstanceState(icicle);
		    }
	
}