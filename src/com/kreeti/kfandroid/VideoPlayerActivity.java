/*
 *	Created by Nabarun Banerjee on 11/07/14.
 *  Copyright (c) 2014 Kreeti Technologies. All rights reserved.
 */

package com.kreeti.kfandroid;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import com.example.kaliteandroid.R;
import com.kreeti.kfmodels.VideoLog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

public class VideoPlayerActivity extends BaseActivity {
	private Date videoStartedAt;
	private Date videoEndedAt;
	Context context;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		context = this;
		setContentView(R.layout.activity_media_player);		
		String mountLocation = getIntent().getStringExtra("videoFileName");
		playVideoFromSDCard(mountLocation);		
	}
	
	public void playVideoFromSDCard(String fileName) {		
		Uri vidFile = Uri.parse(fileName);
		VideoView videoView = (VideoView) findViewById(R.id.videoView1);
		videoView.setVideoURI(vidFile);
		videoView.setMediaController(new MediaController(this));		
		videoStartedAt = new Date(System.currentTimeMillis());
		videoView.start();
	}	
	
	@Override 
    public void onBackPressed() { 		
		videoEndedAt = new Date(System.currentTimeMillis());
		String videoTitle = getIntent().getStringExtra("videoTitle");
		DatabaseHandler dbHandler = new DatabaseHandler(context);		
		Date day = new Date(System.currentTimeMillis());
		VideoLog vl = new VideoLog(videoTitle, videoStartedAt, videoEndedAt, day, getIntent().getStringExtra("videoId"));
		dbHandler.addVideoLog(vl);		
		super.onBackPressed();		 
     } 
	
	@Override 
	protected void onSaveInstanceState(Bundle icicle) {	      
	      super.onSaveInstanceState(icicle);
	    }
}
