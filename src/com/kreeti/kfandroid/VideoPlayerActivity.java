/*
 *	Created by Nabarun Banerjee on 11/07/14.
 *  Copyright (c) 2014 Kreeti Technologies. All rights reserved.
 */

package com.kreeti.kfandroid;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import com.example.kaliteandroid.R;
import com.kreeti.kfmodels.VideoLog;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

public class VideoPlayerActivity extends Activity {
	private String videoStartedAt;
	private String videoEndedAt;
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
		SimpleDateFormat df = new SimpleDateFormat("HH:mm");
		videoStartedAt = df.format(Calendar.getInstance().getTime());
		videoView.start();
	}	
	
	@Override 
    public void onBackPressed() { 
		SimpleDateFormat df = new SimpleDateFormat("HH:mm");
		videoEndedAt = df.format(Calendar.getInstance().getTime());
		df = new SimpleDateFormat("d MMM yyyy, HH:mm");
		String date = df.format(Calendar.getInstance().getTime());
		String videoTitle = getIntent().getStringExtra("videoTitle");
		DatabaseHandler db = new DatabaseHandler(context);
		db.addVideoLog(new VideoLog(videoTitle, videoStartedAt, videoEndedAt, date));
		List<VideoLog> videoLogList = db.getAllVideoLogs();
		super.onBackPressed();
		 
     } 
	
	
	@Override 
	protected void onSaveInstanceState(Bundle icicle) {	      
	      super.onSaveInstanceState(icicle);
	    }
}
