package com.kreeti.kfmodels;

public class VideoLog {
	//private variables
   private long _id;
   private String _videoName;
   private String _startedAt;
   private String _endedAt;
   private String _date;
     
    // Empty constructor
    public VideoLog(){
         
    }
    // constructor
    public VideoLog(int id, String name, String startedAt, String endedAt, String date){
        this.set_id(id);
        this.set_videoName(name);
        this.set_startedAt(startedAt);
        this.set_endedAt(endedAt);
        this.set_date(date);
    }
     
    // constructor
    public VideoLog(String name, String startedAt, String endedAt, String date){
    	this.set_videoName(name);
        this.set_startedAt(startedAt);
        this.set_endedAt(endedAt);
        this.set_date(date);
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
	public String get_date() {
		return _date;
	}
	public void set_date(String _date) {
		this._date = _date;
	}
}
