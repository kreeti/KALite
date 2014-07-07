package com.example.kaliteandroid;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.widget.MediaController;
import android.widget.VideoView;

public class VideoPlayerActivity extends Activity {
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		setContentView(R.layout.activity_media_player);
		playVideoFromSDCard(getIntent().getStringExtra("videoFileName"));
		//playVideoFromSDCard("/SuperBeam/abc.mp4");
	}
	
	public void playVideoFromSDCard(String fileName){
		Uri vidFile = Uri.parse(Environment.getExternalStorageDirectory().getAbsolutePath()+fileName);
		VideoView videoView = (VideoView) findViewById(R.id.videoView1);
		videoView.setVideoURI(vidFile);
		videoView.setMediaController(new MediaController(this));
		videoView.start();
	}
}
