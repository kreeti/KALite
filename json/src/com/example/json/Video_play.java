package com.example.json;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

public class Video_play extends ListActivity {
	
	SubTopic2 sub2 = new SubTopic2();
    String path = sub2.path;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.video_play);
	    final VideoView videoView = (VideoView) findViewById(R.id.videoView1);
	    videoView.setVideoPath(path);
	    MediaController mediaController = new MediaController(this);
	    mediaController.setAnchorView(videoView);
	    videoView.setMediaController(mediaController);
	    videoView.start();
	}
	
}
