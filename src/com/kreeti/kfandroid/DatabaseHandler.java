package com.kreeti.kfandroid;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import com.kreeti.kfmodels.VideoLog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper implements Constants{ 
 
    public DatabaseHandler(Context context) {    
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        SQLiteDatabase db = this.getWritableDatabase();
    } 
    
    @Override
    public void onCreate(SQLiteDatabase db) {    	
        String CREATE_VIDEOLOG_TABLE = "CREATE TABLE " + TABLE_LOGS + "(" + KEY_ID
        	      + " integer primary key autoincrement, " + KEY_NAME + " text,"
                + KEY_STARTED_AT + " text," + KEY_ENDED_AT + " text," + KEY_DATE + " date" + ")";
        try{
        	db.execSQL(CREATE_VIDEOLOG_TABLE);
        }catch(SQLException e){
        	int i = 0;
        	i++;
        }
    } 
   
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {  
    	db = getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGS);        
        onCreate(db);
    }
    
    public void addVideoLog(VideoLog log) {
        SQLiteDatabase db = this.getWritableDatabase();     
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, log.get_videoName()); 
        values.put(KEY_STARTED_AT, log.get_startedAt());
        values.put(KEY_ENDED_AT, log.get_endedAt());  
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String currentDateandTime = sdf.format(log.get_date());		
       // String currentDateandTime = sdf.format(Calendar.getInstance().getTime());       
        values.put(KEY_DATE, currentDateandTime);     
        
        long rowNo = db.insert(TABLE_LOGS, null, values);
        db.close(); 
    }
    
    public List<VideoLog> getAllVideoLogs() {
        List<VideoLog> VideoLogList = new ArrayList<VideoLog>();        
        String selectQuery = "SELECT * FROM " + TABLE_LOGS;     
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);  
                
        if (cursor != null && cursor.moveToFirst()) {
            do {
                VideoLog videoLog = new VideoLog();
                String s = cursor.getString(1);
                s = cursor.getString(2);
                s = cursor.getString(3);
                videoLog.set_videoName(cursor.getString(1));
                videoLog.set_startedAt(cursor.getString(2));
                videoLog.set_endedAt(cursor.getString(3));
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                Date convertedDate = null;
				String str = cursor.getString(4);
				//videoLog.day = str;				
				videoLog.set_date(Date.valueOf(str));                               
                VideoLogList.add(videoLog);
            } while (cursor.moveToNext());
            cursor.close(); 
        }     
        
        return VideoLogList;
    }
    
    public List<VideoLog> getAllVideoLogsBetweenTwoDates(Date fromDate, Date toDate ) {
        List<VideoLog> VideoLogList = new ArrayList<VideoLog>();  
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String fDate = sdf.format(fromDate);
        String tDate = sdf.format(toDate);
        String selectQuery = "SELECT * FROM " + TABLE_LOGS + " WHERE " + KEY_DATE + " BETWEEN date('"+fDate+"') AND date('"+tDate+"')";     
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);  
                
        if (cursor != null && cursor.moveToFirst()) {
            do {
                VideoLog videoLog = new VideoLog();
                videoLog.set_videoName(cursor.getString(1));
                videoLog.set_startedAt(cursor.getString(2));
                videoLog.set_endedAt(cursor.getString(3));
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                String str = cursor.getString(4);
				//videoLog.day = str;				
				videoLog.set_date(Date.valueOf(str));   
                VideoLogList.add(videoLog);
            } while (cursor.moveToNext());
            cursor.close(); 
        }  
        
        return VideoLogList;
    }
	
}
