package com.example.kaliteandroid;
import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

public class VideoPlayerActivity extends Activity {
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		setContentView(R.layout.activity_media_player);
		//String mountLocation = "/mnt/usb_storage/KhanAcademyVideos/"+getIntent().getStringExtra("videoFileName");
		String mountLocation = getIntent().getStringExtra("videoFileName");
		playVideoFromSDCard(mountLocation);
		//playVideoFromSDCard("/SuperBeam/abc.mp4");
	}
	
	public void playVideoFromSDCard(String fileName){
		//Uri vidFile = Uri.parse(Environment.getExternalStorageDirectory().getAbsolutePath()+fileName);
		Uri vidFile = Uri.parse(fileName);
		VideoView videoView = (VideoView) findViewById(R.id.videoView1);
		videoView.setVideoURI(vidFile);
		videoView.setMediaController(new MediaController(this));
		videoView.start();
	}
	
	@Override 
    public void onBackPressed()    { 		
		super.onBackPressed();
		 
     } 
	
	@Override 
	protected void onSaveInstanceState(Bundle icicle) {	      
	      super.onSaveInstanceState(icicle);
	    }
}
