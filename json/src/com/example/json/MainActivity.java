package com.example.json;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.regex.*;

@SuppressWarnings("unused")
public class MainActivity extends ListActivity {	
	public JSONArray jsonArray;
    public JSONObject jobj;
    public String topic;    
    public String[] subjectArray = {
	        "Math",
	        "Physics",
	        "Chemistry",
	        "Englist",
	        "History",
	        "Geography"
	    };
	GenericTree tree = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity_layout);		
		parseJSON();		 
		//after parsing the JSON file we need to store all the subjects into subjectsArray and show it in listView
		setListAdapter(new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, subjectArray));		
	}
	
	private String addSubject(int index,Matcher m1,JSONObject jobj) {
		// TODO Auto-generated method stub
		if(m1.find())	
    	{
    		tree.addSibling(jobj,tree.root);   //SUBJECTS GETTING ADDED. ALL SUBJECTS ARE AT SAME LEVEL. SO addSiblings
    		try {
				return jobj.getString("path");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
		return null;
	}
	protected void onListItemClick(ListView l, View v, int position, long id,JSONObject jobj) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		this.jobj = jobj;
		topic = subjectArray [position];
		Intent n = new Intent(MainActivity.this ,Subject.class);
	    startActivity(n);
	}
	
	public String loadJSONFromAsset() {		
	    String json = null;
	    try {
	        InputStream inputStream = getAssets().open("topics.json");
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
	
	public void parseJSON()	{
		try {
			JSONObject jsonObj = new JSONObject(loadJSONFromAsset());
			//parse the json object..		
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
}
