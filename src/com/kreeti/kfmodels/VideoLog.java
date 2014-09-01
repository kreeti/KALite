package com.kreeti.kfmodels;

import java.sql.Date;

public class VideoLog {
	//private variables
   private long _id;
   private String _videoName;
   private String _startedAt;
   private String _endedAt;
   private Date _date;    
    
    public VideoLog(String videoTitle, String videoStartedAt, String videoEndedAt, Date l){
    	this.set_videoName(videoTitle);
        this.set_startedAt(videoStartedAt);
        this.set_endedAt(videoEndedAt);
        this.set_date(l);
    }
    
    // Empty constructor
    public VideoLog() {
		// TODO Auto-generated constructor stub
	}	
	
	public long get_id() {
		return _id;
	}
	public void set_id(long l) {
		this._id = l;
	}
	public String get_videoName() {
		return _videoName;
	}
	public void set_videoName(String _videoName) {
		this._videoName = _videoName;
	}
	public String get_startedAt() {
		return _startedAt;
	}
	public void set_startedAt(String _startedAt) {
		this._startedAt = _startedAt;
	}
	public String get_endedAt() {
		return _endedAt;
	}
	public void set_endedAt(String _endedAt) {
		this._endedAt = _endedAt;
	}
	public Date get_date() {
		return _date;
	}
	public void set_date(Date date) {
		this._date = date;
	}
}
