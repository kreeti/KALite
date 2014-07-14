/*
 *	Created by Nabarun Banerjee on 11/07/14.
 *  Copyright (c) 2014 Kreeti Technologies. All rights reserved.
 */

package com.kreetifoundation.kaliteandroid;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.example.kaliteandroid.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends Activity{	
	MainActivity context;
	int lastDisplayedPosition = 0;
	List<JSONObject>lastDisplayedJsonObj = new ArrayList<JSONObject>();	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		
		context = this;
		setContentView(R.layout.activity_main);
		TextView textView = (TextView)findViewById(R.id.kreetiFoundationTextView);
		RelativeLayout mainRelativeLayout = (RelativeLayout)findViewById(R.id.kreetiFoundationLayout);
		mainRelativeLayout.setBackgroundColor(Color.BLACK);
		textView.setTextColor(Color.WHITE);
		
		Handler mHandler = new Handler(); 
		mHandler.postDelayed(new Runnable() { 
	        public void run() { 
	        	Intent childIntent = new Intent(MainActivity.this, ChildActivity.class);		
	    		MainActivity.this.startActivity(childIntent);
	    		finish();
	        } 
	    },2000);
	}
	
	@Override 
	protected void onSaveInstanceState(Bundle icicle) {	      
	      super.onSaveInstanceState(icicle);
	    }	
	
}