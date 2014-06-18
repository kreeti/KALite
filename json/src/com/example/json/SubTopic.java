package com.example.json;

import org.json.JSONObject;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SubTopic extends ListActivity {
	
	Topic t = new Topic();
	String sub_Topic = t.sub_topic;			
	String sub_topic2 = null;
	public String[] SubTopics2 = null;
	int c = 0;
	GenericTree node = new GenericTree(t.s.m.jobj);
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sub_topic);
		if(node.ElementPresent(node))	// Node found. Traverse right(siblings) from here onwards to get the sub topics
		{
			node = node.left;		//1st child
			while(node != null)
			{
				SubTopics2 [c++] = node.data;
				node = node.right;		// siblings
			}
		}
		setListAdapter(new ArrayAdapter<String>(SubTopic.this, android.R.layout.simple_list_item_1, SubTopics2));
	}
	
	protected void onListItemClick(ListView l, View v, int position, long id,JSONObject jobj) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		this.t.s.m.jobj = jobj;
		sub_topic2 = SubTopics2 [position];
		Intent n = new Intent(SubTopic.this ,SubTopic2.class);
	    startActivity(n);
	}
	

}
