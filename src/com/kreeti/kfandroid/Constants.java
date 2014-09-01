/*
 *	Created by Nabarun Banerjee on 1/09/14.

 *  Copyright (c) 2014 Kreeti Technologies. All rights reserved.
 */
package com.kreeti.kfandroid;

public interface Constants {
	public static final int DATABASE_VERSION = 1;   
    public static final String DATABASE_NAME = "logsManager";      
    public static final String TABLE_LOGS = "videologs";    
    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "videoName";
    public static final String KEY_STARTED_AT = "startedAt";
    public static final String KEY_ENDED_AT = "endedAt";
    public static final String KEY_DATE = "day";
    public static final int REQUEST_CODE = 6384;
    public static final String TAG = "FileChooserActivity";
    public static final String ADMIN_LOGRESET_PASSWORRD = "password";
    
    public final String ERROR_MESSAGE_PASSWORD = "Password is wrong, Try again later!";
    public final String ERROR_MESSAGE_RECHABILITY = "Please connect your device with internate!";
    public final String CHOOSER_MESSAGE ="Choose a JSON file";
    public final String KFTITLE_MESSAGE = " - Kreeti Foundation";
    public final String VIDEOS_SUBDIR_PATH = "videos/";
    
    public final String LOG_REPORT_RECEIVER_EMAIL = "nbanerjee@kreeti.com";
    public final String LOG_REPORT_EMAIL_BODY = "Hi, Please see the attached log";
    public final String LOG_REPORT_EMAIL_SUBJECT = "Video log";
    
    public final String DATE_FORMAT = "yyyy-MM-dd";
    public final String TIME_FORMAT = "HH:mm";
    
}
