package com.example.kaliteandroid;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import com.kreeti.kfandroid.DatabaseHandler;
import com.kreeti.kfmodels.VideoLog;

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

public class VideoLogReportActivity extends Activity {
	Context context;
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
					generateCSV();
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
	
	public void generateCSV() throws ParseException {
		DatabaseHandler dbHandler = new DatabaseHandler(context);
		EditText endDateEditText = (EditText)this.findViewById(R.id.editTextView2);
		EditText startDateEditText = (EditText)this.findViewById(R.id.editTextView1);
		SimpleDateFormat curFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		Date startDate = (Date) curFormater.parse(startDateEditText.getText().toString()); 
		Date endDate = (Date) curFormater.parse(endDateEditText.getText().toString()); 
		List<VideoLog> videoLogList = dbHandler.getAllVideoLogsBetweenTwoDates(startDate, endDate);		
	}
	
	public void sendMail() {
		/* Create the Intent */
		final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);

		/* Fill it with Data */
		emailIntent.setType("plain/text");
		emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"to@email.com"});
		emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject");
		emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Text");

		/* Send it off to the Activity-Chooser */
		this.startActivity(Intent.createChooser(emailIntent, "Send mail..."));
	}
	
}
