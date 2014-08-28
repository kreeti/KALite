package com.kreeti.kfandroid;
import java.util.ArrayList;
import java.util.List;

import com.kreeti.kfmodels.VideoLog;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper{	
    private static final int DATABASE_VERSION = 3;   
    private static final String DATABASE_NAME = "logsManager";    
    private static final String TABLE_LOGS = "videologs";    
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "videoName";
    private static final String KEY_STARTED_AT = "startedAt";
    private static final String KEY_ENDED_AT = "endedAt";
    private static final String KEY_DATE = "day";
 
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        SQLiteDatabase db = this.getWritableDatabase();
    } 
    
    @Override
    public void onCreate(SQLiteDatabase db) {    	
        String CREATE_VIDEOLOG_TABLE = "CREATE TABLE " + TABLE_LOGS + "(" + KEY_ID
        	      + " integer primary key autoincrement, " + KEY_NAME + " text,"
                + KEY_STARTED_AT + " text," + KEY_ENDED_AT + " text," + KEY_DATE + " text" + ")";
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
        values.put(KEY_DATE, log.get_date());     
        
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
                VideoLog VideoLog = new VideoLog();                
                VideoLog.set_videoName(cursor.getString(1));
                VideoLog.set_startedAt(cursor.getString(2));
                VideoLog.set_endedAt(cursor.getString(3));
                VideoLog.set_date(cursor.getString(4));               
                VideoLogList.add(VideoLog);
            } while (cursor.moveToNext());
            cursor.close(); 
        }     
        
        return VideoLogList;
    }
	
}
