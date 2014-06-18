package com.example.json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
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
    int c = 0;
    int n = 0;
    int count = 0;
    public String[] subject = null;
	GenericTree tree = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		StringBuffer sb = new StringBuffer();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(getAssets().open(
					"topics.json")));
			String temp;
			 
			while ((temp = br.readLine()) != null)
				sb.append(temp);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close(); // stop reading
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	    String myjsonstring = sb.toString();
		JSONTokener tokener = new JSONTokener(myjsonstring);
		// JSON FILE PARSING
		try {
			 
			 jsonArray = new JSONArray(tokener);	
		     GenericTree tree =new  GenericTree(jsonArray.getJSONObject(0));
		    // CHECKING FOR PATH
			for (int index=1;index<jsonArray.length();index++)
			{
			    jobj = jsonArray.getJSONObject(index);
				String pattern1 = "/[A-Za-z0-9]/";
				String pattern2 = "/[A-Za-z0-9-_ ]/[A-Za-z0-9-_ ]/";
				String pattern3 = "/[A-Za-z0-9]/[A-Za-z0-9-_ ]/[A-Za-z0-9-_ ]/";
				String pattern4 = "/[A-Za-z0-9]/[A-Za-z0-9-_ ]/[A-Za-z0-9-_ ]/[A-Za-z0-9-_ ]/";
				String pattern5 = "/[A-Za-z0-9]/[A-Za-z0-9-_ ]/[A-Za-z0-9-_ ]/[A-Za-z0-9-_ ]/[a-z]/[A-Za-z0-9-_ ]/";
				
			    Pattern r1 = Pattern.compile(pattern1);
			    Pattern r2 = Pattern.compile(pattern2);
			    Pattern r3 = Pattern.compile(pattern3);
			    Pattern r4 = Pattern.compile(pattern4);
			    Pattern r5 = Pattern.compile(pattern5);
			    
			    Matcher m1 = r1.matcher(jobj.getString("path"));
			    Matcher m2 = r2.matcher(jobj.getString("path"));
			    Matcher m3 = r3.matcher(jobj.getString("path"));
			    Matcher m4 = r4.matcher(jobj.getString("path"));
			    Matcher m5 = r5.matcher(jobj.getString("path"));
			    subject[c++] = addSubject(index,m1,jobj);
			    
			}
			
		  } catch (JSONException e) {
			// TODO Auto-generated catch block
			 e.printStackTrace();
		}
		setListAdapter(new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, subject));
		
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
		topic = subject [position];
		Intent n = new Intent(MainActivity.this ,Subject.class);
	    startActivity(n);
	}
	
}
