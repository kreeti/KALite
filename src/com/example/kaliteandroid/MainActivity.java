package com.example.kaliteandroid;

import org.json.JSONException;

import org.json.JSONObject;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends BaseListActivity{	
	MainActivity context;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		context = this;
		setContentView(R.layout.activity_main);	
		try {
			if(jsonObj != null)
				jsonObj = new JSONObject(getIntent().getStringExtra("jsonObject"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		parseJSON(jsonObj);		
		
		//after parsing the JSON file we need to store all the subjects into subjectsArray and show it in listView
		setListAdapter(new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, subject));	
		ListView listView = getListView();
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
		    @Override
		    public void onItemClick(AdapterView<?> av, View v, int pos, long id) {		    	
		    	//parseJSON(allChildrens.get(pos));		    	
		    	//setListAdapter(new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, subject));	
		    	Intent childIntent = new Intent(context, ChildActivity.class);	
		    	String s = allChildrens.get(pos).toString();
				childIntent.putExtra("jsonObject", allChildrens.get(pos).toString());
		    	context.startActivity(childIntent);
		    }
		});
		
		
	}	
	
}