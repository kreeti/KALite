package com.example.kaliteandroid;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import com.kreeti.kfandroid.DatabaseHandler;
import com.kreeti.kfmodels.VideoLog;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
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
	protected Date formDate;
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
	                            	String date = dayOfMonth + "-"
	                                        + (monthOfYear + 1) + "-" + year;	                            	
	                            	EditText edittext = (EditText) VideoLogReportActivity.this.findViewById(R.id.editTextView1);		    
	                    			edittext.setText(date);
	                    			VideoLogReportActivity.this.formDate = new Date(year, monthOfYear, dayOfMonth);
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
	                            	String date = dayOfMonth + "-"
	                                        + (monthOfYear + 1) + "-" + year;	                            	
	                            	EditText edittext = (EditText) VideoLogReportActivity.this.findViewById(R.id.editTextView2);		    
	                    			edittext.setText(date);
	                    			VideoLogReportActivity.this.toDate = new Date(year, monthOfYear, dayOfMonth);
	                            }

								
	                        }, mYear, mMonth, mDay);
	                dpd.show();
		        }
		    });
		   
		   Button sendButton = (Button)this.findViewById(R.id.button1);
		   sendButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					String logFilePath = getIntent().getStringExtra("logFilePath");
					try {
						generateCSVFile(logFilePath);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						int i = 0;
						i ++;
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
	
	public void generateCSVFile(String path) throws ParseException, IOException {
		DatabaseHandler dbHandler = new DatabaseHandler(context);		 
		//List<VideoLog> videoLogList = dbHandler.getAllVideoLogsBetweenTwoDates(VideoLogReportActivity.this.formDate, VideoLogReportActivity.this.toDate);
		List<VideoLog> videoLogList = dbHandler.getAllVideoLogs();
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
		File file   =   new File(getIntent().getStringExtra("logFilePath") + "log.csv");
		Uri u1  =   Uri.fromFile(file);
		emailIntent.putExtra(Intent.EXTRA_STREAM, u1);

		/* Send it off to the Activity-Chooser */
		this.startActivity(Intent.createChooser(emailIntent, "Send mail..."));
	}
	
}
