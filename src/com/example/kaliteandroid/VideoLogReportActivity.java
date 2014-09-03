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
import com.kreeti.kfandroid.BaseActivity;
import com.kreeti.kfandroid.DatabaseManagementService;
import com.kreeti.kfmodels.VideoLog;
import android.net.Uri;
import android.os.Bundle;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import au.com.bytecode.opencsv.CSVWriter;

public class VideoLogReportActivity extends BaseActivity {
	public final String LOG_REPORT_RECEIVER_EMAIL = "nbanerjee@kreeti.com";
    public final String LOG_REPORT_EMAIL_BODY = "Hi, Please see the attached log";
    public final String LOG_REPORT_EMAIL_SUBJECT = "Video log";
    public final String ERROR_MESSAGE_RECHABILITY = "Please connect your device with internate!";
	Context context;	
	protected Date toDate;
	protected Date fromDate;
	String zipFilePath = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		context = this;
		setTitle("Log" + " - Kreeti Foundation");
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
	                            	String date = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
	                            	EditText edittext = (EditText) VideoLogReportActivity.this.findViewById(R.id.editTextView1);		    
	                    			edittext.setText(date);	                    			
	                    		    VideoLogReportActivity.this.fromDate = Date.valueOf(date);	                    		  
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
	                            	String date = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
	                            	EditText edittext = (EditText) VideoLogReportActivity.this.findViewById(R.id.editTextView2);		    
	                    			edittext.setText(date);	                    			
	                    		    VideoLogReportActivity.this.toDate = Date.valueOf(date);	                    			
	                            }
	                      }, mYear, mMonth, mDay);
	                dpd.show();
		        }
		    });
		   
		   Button sendPartialLogReportButton = (Button)this.findViewById(R.id.button1);
		   sendPartialLogReportButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {				
				if(toDate == null || fromDate == null) return;
				else if(!toDate.after(fromDate)){
					showAlert("End date should be after Start date!");
					return;				
				}
				try {
					String logFilePath = getIntent().getStringExtra("logFilePath");
					try {
						generateCSVFile(logFilePath, true);
					} catch (IOException e) {						
						e.printStackTrace();						
					}
				} catch (ParseException e) {					
					e.printStackTrace();
				}
				sendMail();
			}
			   
		   });
		   
		   Button sendFullLogreportButton = (Button)this.findViewById(R.id.button2);
		   sendFullLogreportButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {				
				try {
					String logFilePath = getIntent().getStringExtra("logFilePath");
					try {
						generateCSVFile(logFilePath, false);
					} catch (IOException e) {
						e.printStackTrace();
					}
				} catch (ParseException e) {					
					e.printStackTrace();
				}
				sendMail();
			}
			   
		   });
	}
	
	public void generateCSVFile(String path, boolean isPartialLog) throws ParseException, IOException {
		DatabaseManagementService dbHandler = new DatabaseManagementService(context);		
		List<VideoLog> videoLogList;
		if(isPartialLog)
			videoLogList = dbHandler.getAllVideoLogsBetweenTwoDates(fromDate, toDate);
		else
			videoLogList = dbHandler.getAllVideoLogs();		
		CSVWriter writer = new CSVWriter(new FileWriter(path + "log.csv"));
		List<String[]> data = new ArrayList<String[]>();
		for(VideoLog vl : videoLogList){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");			
			String[] obj = new String[5];
			obj[0] = vl.id();
			obj[1] = vl.videoName();
			obj[2] = sdf.format(vl.startedAt());
			obj[3] = sdf.format(vl.endedAt());			
			obj[4] = vl.videoId();
			data.add(obj);
		}
		writer.writeAll(data);
		writer.close();
	}
	
	public void sendMail() {
		if(!isNetworkConnected()){
			showAlert(ERROR_MESSAGE_RECHABILITY);
			return;
		}
		final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);

		/* Fill it with Data */
		emailIntent.setType("plain/text");
		emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{LOG_REPORT_RECEIVER_EMAIL});
		emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, LOG_REPORT_EMAIL_SUBJECT);
		emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, LOG_REPORT_EMAIL_BODY);	
		
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);		
	    String currentDateandTime = sdf.format(Calendar.getInstance().getTime());	    
		zipFilePath = getIntent().getStringExtra("logFilePath") + currentDateandTime + "-log.zip";
		zipFileAtPath(getIntent().getStringExtra("logFilePath") + "log.csv", zipFilePath);		
		emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(zipFilePath)));
		
		/* Send it off to the Activity-Chooser */
		this.startActivity(Intent.createChooser(emailIntent, "Send mail..."));	
		 File csvFile = new File(getIntent().getStringExtra("logFilePath") + "log.csv");			 
		 if(csvFile.exists())csvFile.delete();
		
	}	
	
	 public void onBackPressed() {
		 deleteTempZipFile();	
		 super.onBackPressed();	
	 }
	 
	 @Override
	 public void onResume()	 {
		 deleteTempZipFile();
	     super.onResume();
	    
	 }	
	 
	 public void deleteTempZipFile() {
		 /* Delete temp files */
		 if(zipFilePath != null){
			 File zipFile = new File(zipFilePath);				
			 if(zipFile.exists())zipFile.delete();
		 }		
	 }
		
}
