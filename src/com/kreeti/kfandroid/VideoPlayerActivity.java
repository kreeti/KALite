/*
 *	Created by Nabarun Banerjee on 11/07/14.
 *  Copyright (c) 2014 Kreeti Technologies. All rights reserved.
 */

package com.kreeti.kfandroid;
import com.example.kaliteandroid.R;
import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

public class VideoPlayerActivity extends Activity {
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		setContentView(R.layout.activity_media_player);		
		String mountLocation = getIntent().getStringExtra("videoFileName");
		playVideoFromSDCard(mountLocation);		
	}
	
	public void playVideoFromSDCard(String fileName) {		
		Uri vidFile = Uri.parse(fileName);
		VideoView videoView = (VideoView) findViewById(R.id.videoView1);
		videoView.setVideoURI(vidFile);
		videoView.setMediaController(new MediaController(this));
		videoView.start();
	}
	
	@Override 
    public void onBackPressed() { 		
		super.onBackPressed();
		 
     } 
	
	@Override 
	protected void onSaveInstanceState(Bundle icicle) {	      
	      super.onSaveInstanceState(icicle);
	    }
}
