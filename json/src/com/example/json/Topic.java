package com.example.json;

import org.json.JSONObject;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Topic extends ListActivity {
	
    Subject s = new Subject();
	String Topic = s.subtopic1;			
	String sub_topic = null;
	public String[] SubTopics1 = null;
	int c = 0;
	GenericTree node = new GenericTree(s.m.jobj);
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.topic);
		if(s.m.tree.ElementPresent(node))	// Node found. Traverse right(siblings) from here onwards to get the sub topics
		{
			node = node.left;		//1st child
			while(node != null)
			{
				SubTopics1 [c++] = node.data;
				node = node.right;		// siblings
			}
		}
		setListAdapter(new ArrayAdapter<String>(Topic.this, android.R.layout.simple_list_item_1, SubTopics1));
	}
	
	protected void onListItemClick(ListView l, View v, int position, long id,JSONObject jobj) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		
		sub_topic = SubTopics1 [position];
		Intent n = new Intent(Topic.this ,SubTopic.class);
	    startActivity(n);
	}
	
	

}
