package com.example.kaliteandroid;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.ipaulpro.afilechooser.utils.FileUtils;

import android.app.ListActivity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

public class BaseListActivity extends ListActivity {
	public JSONArray jsonArray;
	public JSONObject baseJobj;
    public JSONObject jobj;
    public String topic;    
    public String[] subjectArray;
    public JSONObject jsonObj;
    List<String> subject;
    List<JSONObject>allChildrens = new ArrayList<JSONObject>();	
    private static final int REQUEST_CODE = 6384;
    private static final String TAG = "FileChooserExampleActivity";
    public String fileDirectoryBasePath;
    public String fileDirectoryVideoPath;
    public File selectedFile;
    public boolean isFileChooserOn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);			
		
	}	
	public String loadJSONFromAsset() {		
	    String json = null;
	    try {
	        InputStream inputStream = this.getResources().getAssets().open("topics.json");
	        int size = inputStream.available();
	        byte[] buffer = new byte[size];
	        inputStream.read(buffer);
	        inputStream.close();
	        json = new String(buffer, "UTF-8");	       
	    } catch (IOException ex) {
	        ex.printStackTrace();
	        return null;
	    }
	    return json;

	}
	
	public void parseJSON(JSONObject jsonObject)	{
		try {
			subject = new ArrayList<String>();
			allChildrens = new ArrayList<JSONObject>();
			if(jsonObject == null){
				String s = loadJSONFromAsset();				 
				jsonObj = new JSONObject(s);
				baseJobj = jsonObj;
			}else{
				jsonObj = jsonObject;
			}			
			//parse the json object..	
			if(jsonObj.has("children")){
				JSONArray jsonArray = jsonObj.getJSONArray("children");
				for(int i=0; i<jsonArray.length(); i++) {				
	                jsonObj = jsonArray.getJSONObject(i);
	                allChildrens.add(jsonObj);
	                if(jsonObj.has("title"))
	                	subject.add(jsonObj.getString("title"));	                
	            }	
			}else if(jsonObj.has("download_urls")){                	   
                	String url = jsonObj.getString("id");
                	url = url+".mp4";
                	if(url != null && !url.isEmpty())
                		subject.add(url);               	              		
			}else
        		subject.add("File not found");  
					
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		if(subject.get(0).endsWith(".mp4")){
		 	Intent videoPlayerIntent = new Intent(this, VideoPlayerActivity.class);	
    		videoPlayerIntent.putExtra("videoFileName", subject.get(0));
    		this.startActivity(videoPlayerIntent);
		}else{
			setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, subject));
		}
		
	}
	
	 public void replaceString(Object object){
		 List<String> subjectsList = new ArrayList<String>();
		 for(int i=0; i<subject.size(); i++) {				
             String string = subject.get(i);
             string = string.replace((CharSequence) object, "");
             string = string.replaceAll("/","");
             subjectsList.add(string);                            
         }	
		 subject = subjectsList;
	 }
	 
	 public void showChooser(String titleString) {
	        // Use the GET_CONTENT intent from the utility class
		 	isFileChooserOn = true;
	        Intent target = FileUtils.createGetContentIntent();
	        // Create the chooser Intent
	        Intent intent = Intent.createChooser(
	                target, titleString);
	        try {
	            startActivityForResult(intent, REQUEST_CODE);
	        } catch (ActivityNotFoundException e) {
	            // The reason for the existence of aFileChooser
	        }
	    }

	    @Override
	    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	        switch (requestCode) {
	            case REQUEST_CODE:
	                // If the file selection was successful
	                if (resultCode == RESULT_OK) {
	                    if (data != null) {
	                        // Get the URI of the selected file
	                        final Uri uri = data.getData();
	                        Log.i(TAG, "Uri = " + uri.toString());
	                        try {
	                            // Get the file path from the URI
	                            final String path = FileUtils.getPath(this, uri);
	                            Toast.makeText(this,
	                                    "File Selected: " + path, Toast.LENGTH_LONG).show();
	                            if (path != null && FileUtils.isLocal(path)) {
	                                selectedFile = new File(path);  
	                                fileDirectoryBasePath = path.replace(selectedFile.getName(), "");	                                
	                                fileDirectoryVideoPath = path;
	                                	
	                            }
	                        } catch (Exception e) {
	                            Log.e("FileSelectorTestActivity", "File select error", e);
	                        }
	                    }
	                }
	                break;
	        }	       
	        if(selectedFile.getName().endsWith(".json")){
	        	JSONParser parser = new JSONParser(); 
				Object obj = null;
				try {
					obj = parser.parse(new FileReader(selectedFile));
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		 
				JSONObject jsonObject = null;
				try {
					jsonObject = new JSONObject(obj.toString());
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(jsonObject != null)
					parseJSON(jsonObject);
		    
				
		        isFileChooserOn = false;
		        super.onActivityResult(requestCode, resultCode, data);
		    }
	        }
	    	
}
