package com.example.kaliteandroid;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ChildActivity extends ListActivity{
	
    MainActivity m = new MainActivity();
	String sub = m.topic;			
	public String[] topics = null;
	public String subtopic1;
	int c = 0;	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_child);	
		getActionBar().setDisplayHomeAsUpEnabled(true);
		this.getActionBar().setDisplayShowHomeEnabled(false);
		/*try {
			jsonObj = new JSONObject(getIntent().getStringExtra("jsonObject"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		parseJSON(jsonObj);		
		
		//after parsing the JSON file we need to store all the subjects into subjectsArray and show it in listView
		setListAdapter(new ArrayAdapter<String>(ChildActivity.this, android.R.layout.simple_list_item_1, subject));	
		ListView listView = getListView();
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
		    @Override
		    public void onItemClick(AdapterView<?> av, View v, int pos, long id) {		    	
		    	//parseJSON(allChildrens.get(pos));		    	
		    	//setListAdapter(new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, ChildListActivity));	
		    	Intent childIntent = new Intent(ChildActivity.this, MainActivity.class);	
				childIntent.putExtra("jsonObject", allChildrens.get(pos).toString());
				ChildActivity.this.startActivity(childIntent);
		    }
		});*/
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		
	}	
	
	
}