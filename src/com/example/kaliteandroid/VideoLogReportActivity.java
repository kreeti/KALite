package com.example.kaliteandroid;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.kreeti.kfandroid.DatabaseHandler;
import com.kreeti.kfmodels.VideoLog;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import au.com.bytecode.opencsv.CSVWriter;

public class VideoLogReportActivity extends Activity {
	Context context;	
	protected Date toDate;
	protected Date fromDate;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		context = this;
		setContentView(R.layout.activity_video_log_report);
		EditText startDateEditText = (EditText)this.findViewById(R.id.editTextView1);
		startDateEditText.setOnClickListener(new OnClickListener() {

		        @Override
		        public void onClick(View v) {
		        	// Process to get Current Date					
	                final Calendar c = Calendar.getInstance();
	                int mYear = c.get(Calendar.YEAR);
	                int mMonth = c.get(Calendar.MONTH);
	                int mDay = c.get(Calendar.DAY_OF_MONTH);
	     
	                // Launch Date Picker Dialog
	                DatePickerDialog dpd = new DatePickerDialog(VideoLogReportActivity.this,  new DatePickerDialog.OnDateSetListener() {
	     
	                            @Override
	                            public void onDateSet(DatePicker view, int year,
	                                    int monthOfYear, int dayOfMonth) {	                               
	                            	//String date = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;	     
	                            	String date = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
	                            	EditText edittext = (EditText) VideoLogReportActivity.this.findViewById(R.id.editTextView1);		    
	                    			edittext.setText(date);
	                    			VideoLogReportActivity.this.fromDate = new Date(year, monthOfYear, dayOfMonth);
	                            }

								
	                        }, mYear, mMonth, mDay);
	                dpd.show();	                
		        }
		    });
		   
		   EditText endDateEditText = (EditText)this.findViewById(R.id.editTextView2);
		   endDateEditText.setOnClickListener(new OnClickListener() {

		        @Override
		        public void onClick(View v) {
		        	// Process to get Current Date					
	                final Calendar c = Calendar.getInstance();
	                int mYear = c.get(Calendar.YEAR);
	                int mMonth = c.get(Calendar.MONTH);
	                int mDay = c.get(Calendar.DAY_OF_MONTH);
	     
	                // Launch Date Picker Dialog
	                DatePickerDialog dpd = new DatePickerDialog(VideoLogReportActivity.this,  new DatePickerDialog.OnDateSetListener() {
	     
	                            @Override
	                            public void onDateSet(DatePicker view, int year,
	                                    int monthOfYear, int dayOfMonth) {	                               
	                            	//String date = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;	
	                            	String date = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
	                            	EditText edittext = (EditText) VideoLogReportActivity.this.findViewById(R.id.editTextView2);		    
	                    			edittext.setText(date);
	                    			VideoLogReportActivity.this.toDate = new Date(year, monthOfYear, dayOfMonth);
	                            }

								
	                        }, mYear, mMonth, mDay);
	                dpd.show();
		        }
		    });
		   
		   Button sendPartialLogReportButton = (Button)this.findViewById(R.id.button1);
		   sendPartialLogReportButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(toDate == null || fromDate == null) return;
				try {
					String logFilePath = getIntent().getStringExtra("logFilePath");
					try {
						generateCSVFile(logFilePath, true);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();						
					}
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				sendMail();
			}
			   
		   });
		   
		   Button sendFullLogreportButton = (Button)this.findViewById(R.id.button2);
		   sendFullLogreportButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					String logFilePath = getIntent().getStringExtra("logFilePath");
					try {
						generateCSVFile(logFilePath, false);
					} catch (IOException e) {
						// TODO Auto-generated catch block
											}
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				sendMail();
			}
			   
		   });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.video_log_report, menu);
		return true;
	}
	
	public void generateCSVFile(String path, boolean isPartialLog) throws ParseException, IOException {
		DatabaseHandler dbHandler = new DatabaseHandler(context);
		EditText edittext2 = (EditText) VideoLogReportActivity.this.findViewById(R.id.editTextView2);
		String toDate = edittext2.getText().toString();
		EditText edittext1 = (EditText) VideoLogReportActivity.this.findViewById(R.id.editTextView1);
		String fromDate = edittext1.getText().toString();
		List<VideoLog> videoLogList;
		if(isPartialLog)
			videoLogList = dbHandler.getAllVideoLogsBetweenTwoDates(Date.valueOf(fromDate), Date.valueOf(toDate));
		else
			videoLogList = dbHandler.getAllVideoLogs();
		String csv = android.os.Environment.getExternalStorageDirectory().getAbsolutePath();
		CSVWriter writer = new CSVWriter(new FileWriter(path + "log.csv"));
		List<String[]> data = new ArrayList<String[]>();
		for(VideoLog vl : videoLogList){
			String[] obj = new String[4];
			obj[0] = vl.get_videoName();
			obj[1] = vl.get_startedAt();
			obj[2] = vl.get_endedAt();
			String s = vl.get_endedAt();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	        String day = sdf.format(vl.get_date());			
			obj[3] = day;
			data.add(obj);
		}
		writer.writeAll(data);
		writer.close();
	}
	
	public void sendMail() {
		/* Create the Intent */
		final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);

		/* Fill it with Data */
		emailIntent.setType("plain/text");
		emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"nbanerjee@kreeti.com"});
		emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Video log");
		emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Hi, Please see the attached log");	
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");		
	    String currentDateandTime = sdf.format(Calendar.getInstance().getTime());	    
		String zipFilePath = getIntent().getStringExtra("logFilePath") + currentDateandTime + "-log.zip";
		zipFileAtPath(getIntent().getStringExtra("logFilePath") + "log.csv", zipFilePath);
		File file   =   new File(zipFilePath);
		Uri u1  =   Uri.fromFile(file);
		emailIntent.putExtra(Intent.EXTRA_STREAM, u1);

		/* Send it off to the Activity-Chooser */
		this.startActivity(Intent.createChooser(emailIntent, "Send mail..."));
	}
	
	public boolean zipFileAtPath(String sourcePath, String toLocation) {
	    // ArrayList<String> contentList = new ArrayList<String>();
	    final int BUFFER = 2048;


	    File sourceFile = new File(sourcePath);
	    try {
	        BufferedInputStream origin = null;
	        FileOutputStream dest = new FileOutputStream(toLocation);
	        ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(
	                dest));
	        if (sourceFile.isDirectory()) {
	            zipSubFolder(out, sourceFile, sourceFile.getParent().length());
	        } else {
	            byte data[] = new byte[BUFFER];
	            FileInputStream fi = new FileInputStream(sourcePath);
	            origin = new BufferedInputStream(fi, BUFFER);
	            ZipEntry entry = new ZipEntry(getLastPathComponent(sourcePath));
	            out.putNextEntry(entry);
	            int count;
	            while ((count = origin.read(data, 0, BUFFER)) != -1) {
	                out.write(data, 0, count);
	            }
	        }
	        out.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	    return true;
	}
	
	private void zipSubFolder(ZipOutputStream out, File folder,
	        int basePathLength) throws IOException {

	    final int BUFFER = 2048;

	    File[] fileList = folder.listFiles();
	    BufferedInputStream origin = null;
	    for (File file : fileList) {
	        if (file.isDirectory()) {
	            zipSubFolder(out, file, basePathLength);
	        } else {
	            byte data[] = new byte[BUFFER];
	            String unmodifiedFilePath = file.getPath();
	            String relativePath = unmodifiedFilePath
	                    .substring(basePathLength);
	            Log.i("ZIP SUBFOLDER", "Relative Path : " + relativePath);
	            FileInputStream fi = new FileInputStream(unmodifiedFilePath);
	            origin = new BufferedInputStream(fi, BUFFER);
	            ZipEntry entry = new ZipEntry(relativePath);
	            out.putNextEntry(entry);
	            int count;
	            while ((count = origin.read(data, 0, BUFFER)) != -1) {
	                out.write(data, 0, count);
	            }
	            origin.close();
	        }
	    }
	}

	/*
	 * gets the last path component
	 * 
	 * Example: getLastPathComponent("downloads/example/fileToZip");
	 * Result: "fileToZip"
	 */
	public String getLastPathComponent(String filePath) {
	    String[] segments = filePath.split("/");
	    String lastPathComponent = segments[segments.length - 1];
	    return lastPathComponent;
	}
	
}
