package com.example.json;

import org.json.JSONObject;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;				
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Subject extends ListActivity{
	
    MainActivity m = new MainActivity();
	String sub = m.topic;			
	public String[] topics = null;
	public String subtopic1;
	int c = 0;
	GenericTree node = new GenericTree(m.jobj);
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.subject);
		if(m.tree.ElementPresent(node))		// Node found. Traverse right(siblings) from here onwards to get the sub topics
		{
			node = node.left;		//1st child
			while(node != null)
			{
				topics [c++] = node.data;
				node = node.right;		// siblings
			}
		}
		setListAdapter(new ArrayAdapter<String>(Subject.this, android.R.layout.simple_list_item_1, topics));
	}
	
	protected void onListItemClick(ListView l, View v, int position, long id,JSONObject jobj) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		this.m.jobj = jobj;
		subtopic1 = topics [position];
		Intent n = new Intent(Subject.this ,Topic.class);
	    startActivity(n);
	}
	

}
