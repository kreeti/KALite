package com.example.kaliteandroid;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import com.kreeti.kfandroid.TopicListActivity;

import android.os.Bundle;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.EditText;

public class VideoLogReportActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
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
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.video_log_report, menu);
		return true;
	}
	
}
