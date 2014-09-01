/*
 *	Created by Nabarun Banerjee on 11/07/14.

 *  Copyright (c) 2014 Kreeti Technologies. All rights reserved.
 */
package com.kreeti.kfandroid;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import org.json.JSONException;
import com.example.kaliteandroid.R;
import com.example.kaliteandroid.VideoLogReportActivity;
import com.ipaulpro.afilechooser.utils.FileUtils;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class TopicListActivity extends BaseActivity{		
	VideoModelNode rootNode;
    VideoModelNode currentNode;
    private String fileDirectoryVideoPath;
    public ProgressDialog dialog;
    Context context;  
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_child);    	
		context = this;
    	dialog = ProgressDialog.show(this, "", "Loading...");
    	SharedPreferences settings = getSharedPreferences("BasicInfo", 0);
		String fileDirectoryJSONFilePath = settings.getString("IndexFilePath", "").toString();
		if(fileDirectoryJSONFilePath.isEmpty() || fileDirectoryJSONFilePath == null)
			showChooser(CHOOSER_MESSAGE);
		else{
			try {
				createJsonFromFile(fileDirectoryJSONFilePath);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    	ListView listView = (ListView)findViewById(R.id.list);
    	
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
		    @Override
		    public void onItemClick(AdapterView<?> av, View v, int pos, long id) {		    	
		    	if(currentNode.children.size() > 0) {	
		    		VideoModelNode j = currentNode.children.get(pos);
		    		if(!j.isVideo)
		    			setTitle(j.title + KFTITLE_MESSAGE);
		    		if(j.children != null) {
		    			currentNode = j;
		    			
		    			setListAdapter();
		    		} else if(j.id != null && j.isVideo) {		    			
		    			File file = new File(fileDirectoryVideoPath+ j.videoFileName());
		    			
		            	if(file.exists()) {
		            		Intent videoPlayerIntent = new Intent(TopicListActivity.this, VideoPlayerActivity.class);	
				    		videoPlayerIntent.putExtra("videoFileName", fileDirectoryVideoPath + j.videoFileName());				    		 
				    		videoPlayerIntent.putExtra("logFilePath", fileDirectoryVideoPath.replace(VIDEOS_SUBDIR_PATH, ""));
				    		videoPlayerIntent.putExtra("videoTitle", j.title);
				    		
				    		TopicListActivity.this.startActivity(videoPlayerIntent);
		            	}
		    		}else{
	            		setTitle(j.parent.title + KFTITLE_MESSAGE);
	            	}	
		    	}		    	
		    }
		});		
	}
	
	@Override 
    public void onBackPressed() { 		
		if(currentNode.parent == null) {			 
			super.onBackPressed();		 	
		 } else {
			 currentNode = currentNode.parent;
			 setTitle(currentNode.title + KFTITLE_MESSAGE);
			 setListAdapter();
		 }
    } 
	
	 @Override 
	protected void onSaveInstanceState(Bundle icicle) {	      
		      super.onSaveInstanceState(icicle);
		    }
	
	 
	public void parseJSON(JsonReader  jsonReader) throws IOException {
		try {
			rootNode = parseNode(jsonReader, null);
			currentNode = rootNode;
		} catch (JSONException e) {			
			e.printStackTrace();
		}	
		setListAdapter();
	}	 
		 
	private VideoModelNode parseNode(JsonReader jsonReader, VideoModelNode parent) throws JSONException, IOException {
		VideoModelNode model = new VideoModelNode();
	    model.parent = parent;	
	    jsonReader.beginObject();
	    while (jsonReader.hasNext()) {
	       	String name = jsonReader.nextName();
	        if (name.equals("title")) {
	           	model.title = jsonReader.nextString();	            	
	        } else if(name.equals("download_urls")){
	           	model.isVideo = true;
	           	jsonReader.skipValue();
	        } else if(name.equals("id")) {
		       	model.id = jsonReader.nextString();
	        } else if(name.equals("children")){	
	           	model.children = parseChildrenArray(jsonReader, model);
	        } else {
	            jsonReader.skipValue();
	        }
	     }      
	     jsonReader.endObject(); 
	     return model;
	}
		
	public ArrayList<VideoModelNode> parseChildrenArray(JsonReader reader, VideoModelNode parent) throws IOException, JSONException {
		ArrayList<VideoModelNode> children = new ArrayList<VideoModelNode>();
	    reader.beginArray();
	    while (reader.hasNext()) {
	    	children.add(parseNode(reader, parent));
	    }
	    reader.endArray();
	    return children;
	}
		
	public void showChooser(String titleString) { 
		Intent target = FileUtils.createGetContentIntent();	        
        Intent intent = Intent.createChooser(target, titleString);
		        try {
		            startActivityForResult(intent, REQUEST_CODE);
		        } catch (ActivityNotFoundException e) {
		        	e.printStackTrace();
		        }
		    }

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		 String path = null;
		 switch (requestCode) {
		        case REQUEST_CODE:	                
		                if (resultCode == RESULT_OK) {
		                    if (data != null) {	                        
		                        final Uri uri = data.getData();
		                        Log.i(TAG, "Uri = " + uri.toString());
		                        try {	                            
		                            path = FileUtils.getPath(this, uri);
		                            Toast.makeText(this,
		                                    "File Selected: " + path, Toast.LENGTH_LONG).show();
		                        } catch (Exception e) {
		                            Log.e("FileSelectorTestActivity", "File select error", e);
		                        }
		                    }
		                }
		                break;
		        }	       
		        if(path.endsWith(".json")) {
		        	try {
						createJsonFromFile(path);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

		        	SharedPreferences settings = getSharedPreferences("BasicInfo", 0);
					SharedPreferences.Editor editor = settings.edit();
					editor.putString("IndexFilePath", path);
					editor.commit();

			        super.onActivityResult(requestCode, resultCode, data);
			    } else {
			    	showChooser(CHOOSER_MESSAGE);
			    }
		}	  	    

	public void createJsonFromFile(String path) throws IOException {
		    	File selectedFile = new File(path);
		    	if(!selectedFile.exists())
		    		showChooser(CHOOSER_MESSAGE);
		    	
		    	InputStream inStream = new FileInputStream(selectedFile);		    	 
		        BufferedInputStream bufferedStream = new BufferedInputStream(inStream);
		        InputStreamReader streamReader = new InputStreamReader(bufferedStream);		 
		        JsonReader reader = new JsonReader(streamReader);  
		    	parseJSON(reader);

				fileDirectoryVideoPath = path.replace(selectedFile.getName(), "") + VIDEOS_SUBDIR_PATH;
				 if(dialog != null) {
		   		 dialog.dismiss();
		   		 dialog = null;
		   	}
		 }
		    
	public void setListAdapter() {		    	
		    	TopicsListArrayAdapter adapter = new TopicsListArrayAdapter(this, currentNode.children);
		    	adapter.videoDirectoryPath = fileDirectoryVideoPath;	    		    	
		    	ListView myList = (ListView)findViewById(R.id.list);
		    	myList.setAdapter(adapter);	
		    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.main, menu);
	    return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.report:
	        	Intent reportIntent = new Intent(TopicListActivity.this, VideoLogReportActivity.class);	
	        	reportIntent.putExtra("logFilePath", fileDirectoryVideoPath.replace(VIDEOS_SUBDIR_PATH, ""));
	        	TopicListActivity.this.startActivity(reportIntent);
	            return true;	            
	        case R.id.resetLog:
	        	authenticateUserFromAlertDialog();	        	
	        	return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
  public void authenticateUserFromAlertDialog() {
	  LayoutInflater layoutInflater = LayoutInflater.from(context); 
	  View promptView = layoutInflater.inflate(R.layout.prompts_layout, null);
	  AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
	  alertDialogBuilder.setView(promptView);
	  final EditText input = (EditText) promptView.findViewById(R.id.userInput);
	  alertDialogBuilder.setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {	  
	                                      public void onClick(DialogInterface dialog, int id) {	
	                                    	  String resetPassword = input.getText().toString();	                                    	  
	                                    	  if(resetPassword.equals(ADMIN_LOGRESET_PASSWORRD)) {
	                          	        		context.deleteDatabase(DATABASE_NAME);
	                          	        		}else {
	                          	        			showAlert(ERROR_MESSAGE_PASSWORD);	                          	        			
	                          	        		}
	                                      	}
	                                  	}).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
	                                  			public void onClick(DialogInterface dialog, int id) {
	                                  				dialog.cancel();
	                                  				}
	                                 });
	                  // create an alert dialog
	                AlertDialog alertD = alertDialogBuilder.create();
	                alertD.show();
	              }

}