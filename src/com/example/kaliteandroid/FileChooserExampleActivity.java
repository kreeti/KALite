/*
 * Copyright (C) 2012 Paul Burke
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.kaliteandroid;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * @author paulburke (ipaulpro)
 */
public class FileChooserExampleActivity extends BaseListActivity {      
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.file_chooser_layout);                
        // Create a simple button to start the file chooser process
        Button fileChooserButton = (Button) findViewById(R.id.chooserButton);
        fileChooserButton.setText("Choose a file");
        fileChooserButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // Display the file chooser dialog
                showChooser("Choose a video file");
            }
        });
        
        Button playButton = (Button) findViewById(R.id.playButton);        
        playButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {            	
            		Intent videoPlayerIntent = new Intent(FileChooserExampleActivity.this, VideoPlayerActivity.class);            	
            		videoPlayerIntent.putExtra("videoFileName", fileDirectoryVideoPath);
            		FileChooserExampleActivity.this.startActivity(videoPlayerIntent);            	
            }
        });
        
    }
    
    @Override 
    public void onBackPressed()    { 		
		super.onBackPressed();
		 
     } 
   
}
