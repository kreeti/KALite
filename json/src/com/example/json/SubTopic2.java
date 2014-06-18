package com.example.json;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SubTopic2 extends ListActivity{

	
	public String[] array_of_videos = null ;
	int c = 0;
	SubTopic sub = new SubTopic();
	public String path = "";
	String title = sub.sub_topic2;			
	String sub_topic2 = null;
	GenericTree node = new GenericTree(sub.t.s.m.jobj);
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sub_topic);
		if(node.ElementPresent(node))	// Node found. Traverse right(siblings) from here onwards to get the sub topics
		{
			node = node.left;		//1st child
			while(node != null)
			{
				array_of_videos [c++] = node.data;
				node = node.right;		// siblings
			}
		}
		setListAdapter(new ArrayAdapter<String>(SubTopic2.this, android.R.layout.simple_list_item_1, array_of_videos));
	}
	
	protected void onListItemClick(ListView l, View v, int position, long id,JSONObject jobj) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		this.sub.t.s.m.jobj = jobj;
		try {
			path = jobj.getString("path");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		title = array_of_videos [position];
		Intent n = new Intent(SubTopic2.this ,Video_play.class);
	    startActivity(n);
	}
	
	
}
