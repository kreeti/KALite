/*
 *	Created by Nabarun Banerjee on 11/07/14.

 *  Copyright (c) 2014 Kreeti Technologies. All rights reserved.
 */

package com.kreetifoundation.kaliteandroid;
import com.example.kaliteandroid.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * @author paulburke (ipaulpro)
 */
public class FileChooserActivity extends BaseActivity {      
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.file_chooser_layout); 
        Button fileChooserButton = (Button) findViewById(R.id.chooserButton);
        fileChooserButton.setText("Choose a file");
        fileChooserButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {                
                showChooser("Choose a video file");
            }
        });
        
        Button playButton = (Button) findViewById(R.id.playButton);        
        playButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {            	
            		Intent videoPlayerIntent = new Intent(FileChooserActivity.this, VideoPlayerActivity.class);            	
            		videoPlayerIntent.putExtra("videoFileName", fileDirectoryVideoPath);
            		FileChooserActivity.this.startActivity(videoPlayerIntent);            	
            }
        });
        
    }
    
    @Override 
    public void onBackPressed()    { 		
		super.onBackPressed();
		 
     } 
   
}
