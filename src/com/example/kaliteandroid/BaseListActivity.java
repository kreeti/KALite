package com.example.kaliteandroid;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.ListActivity;
import android.os.Bundle;

public class BaseListActivity extends ListActivity {
	public JSONArray jsonArray;
    public JSONObject jobj;
    public String topic;    
    public String[] subjectArray;
    public JSONObject jsonObj;
    List<String> subject;
    List<JSONObject>allChildrens = new ArrayList<JSONObject>();	
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
			}else{
				jsonObj = jsonObject;
			}			
			//parse the json object..		
			JSONArray jsonArray = jsonObj.getJSONArray("children");
			for(int i=0; i<jsonArray.length(); i++) {				
                jsonObj = jsonArray.getJSONObject(i);
                allChildrens.add(jsonObj);
                subject.add(jsonObj.getString("path"));                
            }			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
}
