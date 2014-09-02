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
import android.net.ParseException;

public class DatabaseHandler extends SQLiteOpenHelper implements Constants{ 
	public static final String TABLE_LOGS = "videologs";    
    public static final String KEY_ID = "logId";
    public static final String VIDEO_ID = "videoId";
    public static final String KEY_NAME = "videoName";
    public static final String KEY_STARTED_AT = "startedAt";
    public static final String KEY_ENDED_AT = "endedAt";
    public static final String CREATED_AT = "createdAt";
    public DatabaseHandler(Context context) {    
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        SQLiteDatabase db = this.getWritableDatabase();
    } 
    
    @Override
    public void onCreate(SQLiteDatabase db) {    	
        String CREATE_VIDEOLOG_TABLE = "CREATE TABLE " + TABLE_LOGS + "(" + KEY_ID
        	      + " integer primary key autoincrement, " + KEY_NAME + " text, "
                + KEY_STARTED_AT + " datetime, " + KEY_ENDED_AT + " datetime, " + CREATED_AT + " datetime, " + VIDEO_ID + " text"+")";
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
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ContentValues values = new ContentValues();        
        values.put(KEY_NAME, log.videoName()); 
        values.put(KEY_STARTED_AT, sdf.format(log.startedAt()));
        values.put(KEY_ENDED_AT, sdf.format(log.endedAt()));  
        values.put(VIDEO_ID, log.videoId());             
        values.put(CREATED_AT, sdf.format(log.createdAt()));     
        
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
            	VideoLogList.add(createLog(cursor));
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
        String selectQuery = "SELECT * FROM " + TABLE_LOGS + " WHERE " + CREATED_AT + " BETWEEN date('"+fDate+"') AND date('"+tDate+"')";     
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);  
                
        if (cursor != null && cursor.moveToFirst()) {
            do {            	
                VideoLogList.add(createLog(cursor));
            } while (cursor.moveToNext());
            cursor.close(); 
        }  
        
        return VideoLogList;
    }
    
    private VideoLog createLog(Cursor cursor) {
    	VideoLog videoLog = new VideoLog();    	
        videoLog.setid(cursor.getString(0));
        videoLog.setvideoName(cursor.getString(1));
        SimpleDateFormat iso8601Format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
        	videoLog.setstartedAt(iso8601Format.parse(cursor.getString(2)));
            videoLog.setendedAt(iso8601Format.parse(cursor.getString(3)));               				
			videoLog.setcreatedAt(iso8601Format.parse(cursor.getString(4)));			
        } catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		videoLog.setVideoId(cursor.getString(5));  
		return videoLog;
    }
	
}
