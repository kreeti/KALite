package com.example.kaliteandroid;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class ChildActivity extends BaseListActivity{	
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
		    		else if(subject.get(pos).videoFileName != null && !subject.get(pos).videoFileName.isEmpty()){
		    				Intent videoPlayerIntent = new Intent(ChildActivity.this, VideoPlayerActivity.class);	
				    		videoPlayerIntent.putExtra("videoFileName", fileDirectoryBasePath+"videos/"+subject.get(pos).videoFileName);
				    		ChildActivity.this.startActivity(videoPlayerIntent);
		    			}	    			
		    		 	
		    	}	
		    			    	
		    }
		});		
		
	}
	
	
	 @Override 
     public void onBackPressed()    { 		
		lastDisplayedPosition--;	 	
		if(lastDisplayedPosition < 0){			 
			lastDisplayedPosition = 0;		 	
		 }
		if(lastDisplayedPosition == 0){
			parseJSON(baseJobj);
		}else{
			parseJSON(lastDisplayedJsonObj.get(lastDisplayedPosition));
		}			 
		 
     } 
	 
	 /*@Override
	 public void onResume(){
	     super.onResume();
	     if(lastDisplayedJsonObj.size() > 0)
	    	 parseJSON(lastDisplayedJsonObj.get(0));

	 }*/

	
}