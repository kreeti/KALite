/*
 *	Created by Nabarun Banerjee on 11/07/14.
 *  Copyright (c) 2014 Kreeti Technologies. All rights reserved.
 */
package com.kreeti.kfandroid;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.example.kaliteandroid.R;
import com.ipaulpro.afilechooser.utils.FileUtils;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class TopicListActivity extends Activity{		
	VideoModelNode rootNode;
    VideoModelNode currentNode;    
    private static final int REQUEST_CODE = 6384;
    private static final String TAG = "FileChooserActivity";
    private String fileDirectoryVideoPath;
    public ProgressDialog dialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.activity_child);    		
    	dialog = ProgressDialog.show(this, "", "Loading...");
    	SharedPreferences settings = getSharedPreferences("BasicInfo", 0);
		String fileDirectoryJSONFilePath = settings.getString("IndexFilePath", "").toString();
		if(fileDirectoryJSONFilePath.isEmpty() || fileDirectoryJSONFilePath == null)
			showChooser("Choose a JSON file");
		else{
			createJsonFromFile(fileDirectoryJSONFilePath);
		}
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
		    			File file = new File(fileDirectoryVideoPath+ j.videoFileName);
		    			
		            	if(file.exists()) {
		            		Intent videoPlayerIntent = new Intent(TopicListActivity.this, VideoPlayerActivity.class);	
				    		videoPlayerIntent.putExtra("videoFileName", fileDirectoryVideoPath + j.videoFileName);
				    		TopicListActivity.this.startActivity(videoPlayerIntent);
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
	
	 
	 public void parseJSON(JSONObject jsonObject) {
			try {
				rootNode = parseNode(jsonObject, null);
				currentNode = rootNode;
			} catch (JSONException e) {			
				e.printStackTrace();
			}	
			setListAdapter();		
			
		}	 
		 
		private VideoModelNode parseNode(JSONObject json, VideoModelNode parent) throws JSONException {
	        VideoModelNode model = new VideoModelNode();
	        model.parent = parent;
	        
	        if(json.has("title")) {
	        	model.title = json.getString("title");	                	
	        }   
	        
	        if(json.has("download_urls")) {
	        	model.isVideoURLExist = true;	
	        	String fileName = json.getString("id");
	        	fileName = fileName+".mp4";        	
	        	model.videoFileName = fileName;
	        }

	        if(!json.has("children"))
	        	return model;
	        
	        JSONArray jsonArray = json.getJSONArray("children");
	        model.children = new ArrayList<VideoModelNode>();
	    	
	        for(int i = 0; i < jsonArray.length(); i++) {	
	        	JSONObject child = jsonArray.getJSONObject(i);
				model.children.add(parseNode(child, model));	                
	        }
	        return model;
		}
		
		 public void showChooser(String titleString) { 
		        Intent target = FileUtils.createGetContentIntent();	        
		        Intent intent = Intent.createChooser(
		                target, titleString);
		        try {
		            startActivityForResult(intent, REQUEST_CODE);
		        } catch (ActivityNotFoundException e) {
		            
		        }
		    }

		    @Override
		    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		    	String path = null;
		        switch (requestCode) {
		            case REQUEST_CODE:	                
		                if (resultCode == RESULT_OK) {
		                    if (data != null) {	                        
		                        final Uri uri = data.getData();
		                        Log.i(TAG, "Uri = " + uri.toString());
		                        try {	                            
		                            path = FileUtils.getPath(this, uri);
		                            Toast.makeText(this,
		                                    "File Selected: " + path, Toast.LENGTH_LONG).show();
		                        } catch (Exception e) {
		                            Log.e("FileSelectorTestActivity", "File select error", e);
		                        }
		                    }
		                }
		                break;
		        }	       
		        if(path.endsWith(".json")) {
		        	createJsonFromFile(path);

		        	SharedPreferences settings = getSharedPreferences("BasicInfo", 0);
					SharedPreferences.Editor editor = settings.edit();
					editor.putString("IndexFilePath", path);
					editor.commit();

			        super.onActivityResult(requestCode, resultCode, data);
			    } else {
			    	showChooser("Choose a JSON file");
			    }
		    }
		    
		    public void createJsonFromFile(String path) {
		    	File selectedFile = new File(path);
		    	if(!selectedFile.exists())
		    		showChooser("Choose a JSON file");
		    	StringBuilder text = new StringBuilder();

	        	try {
	        	    BufferedReader br = new BufferedReader(new FileReader(selectedFile));
	        	    String line;

	        	    while ((line = br.readLine()) != null) {
	        	        text.append(line);
	        	        text.append("\n");
	        	    }
	        	    br.close();
	        	}
	        	catch (IOException e) {
	        		e.printStackTrace();
	        	}
		 
				JSONObject jsonObject = null;
				try {
					jsonObject = new JSONObject(text.toString());
					parseJSON(jsonObject);		  
				} catch (JSONException e) {					
					e.printStackTrace();
				}

				fileDirectoryVideoPath = path.replace(selectedFile.getName(), "") + "videos/";
				 if(dialog != null) {
		   		 dialog.dismiss();
		   		 dialog = null;
		   	}
		 }		    
		    public void setListAdapter() {		    	
		    	TopicsListArrayAdapter adapter = new TopicsListArrayAdapter(this, currentNode.children);
		    	adapter.videoDirectoryPath = fileDirectoryVideoPath;	    		    	
		    	ListView myList = (ListView)findViewById(R.id.list);
		    	myList.setAdapter(adapter);	
		    }
}