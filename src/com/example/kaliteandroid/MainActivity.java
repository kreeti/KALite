package com.example.kaliteandroid;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends BaseListActivity{	
	MainActivity context;
	int lastDisplayedPosition = 0;
	List<JSONObject>lastDisplayedJsonObj = new ArrayList<JSONObject>();	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		context = this;
		setContentView(R.layout.activity_main);		
    	showChooser("Choose a JSON file");			
		ListView listView = getListView();
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
		    @Override
		    public void onItemClick(AdapterView<?> av, View v, int pos, long id) {		    	
		    	if(allChildrens.size() > 0){	
		    		lastDisplayedPosition++;
		    		lastDisplayedJsonObj.add(allChildrens.get(pos));		    		
		    		parseJSON(allChildrens.get(pos));
		    		/* if(subject.get(pos).endsWith(".mp4")){
		    			 	Intent videoPlayerIntent = new Intent(MainActivity.this, VideoPlayerActivity.class);	
				    		videoPlayerIntent.putExtra("videoFileName", subject.get(pos));
				    		MainActivity.this.startActivity(videoPlayerIntent);
		    		 }else{
		    			 setListAdapter(new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, subject));
		    		 }		*/	    	
		    	}else{		    		
		    		Intent intent = new Intent(MainActivity.this, FileChooserExampleActivity.class);
		    		intent.putExtra("videoFileName", subject.get(pos));
		    		MainActivity.this.startActivity(intent);
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
		// setListAdapter(new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, subject));	 
		 
     } 
	 
	 @Override
	 public void onResume(){
	     super.onResume();
	     if(lastDisplayedJsonObj.size() > 0)
	    	 parseJSON(lastDisplayedJsonObj.get(0));

	 }

	
}