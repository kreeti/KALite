/*
 *	Created by Nabarun Banerjee on 11/07/14.
 *  Copyright (c) 2014 Kreeti Technologies. All rights reserved.
 */

package com.kreeti.kfandroid;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.example.kaliteandroid.R;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.DateFormat;
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
		
		SimpleDateFormat df = new SimpleDateFormat("d MMM yyyy, HH:mm");
		String currentDateandTime = df.format(Calendar.getInstance().getTime());
		String logText = currentDateandTime + " : " + getIntent().getStringExtra("videoTitle");
		appendLog(logText);
	}
	
	public void appendLog(String text) {       
	   String fileDirectoryLogPath = getIntent().getStringExtra("logFilePath");
	   File logFile = new File(fileDirectoryLogPath + "log.file");
	   if (!logFile.exists()) {
	      try
	      {
	         logFile.createNewFile();
	      } 
	      catch (IOException e)
	      {
	         // TODO Auto-generated catch block
	         e.printStackTrace();
	      }
	   }else if(logFile.length() >= 1024*1024*5){
		   if(logFile.delete()) {
			   try {
					logFile.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}  
		   }			
	   }
	   try
	   {		   
	      //BufferedWriter for performance, true to set append to file flag
	      BufferedWriter buf = new BufferedWriter(new FileWriter(logFile, true)); 
	      buf.append(text);
	      buf.newLine();
	      buf.close();
	   }
	   catch (IOException e)
	   {
	      // TODO Auto-generated catch block
	      e.printStackTrace();
	   }
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
