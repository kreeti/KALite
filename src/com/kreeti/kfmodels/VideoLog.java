package com.kreeti.kfmodels;

import java.sql.Date;

public class VideoLog {
	//private variables
   private long id;
   private String videoName;
   private java.util.Date startedAt;
   private java.util.Date endedAt;  
   private java.util.Date createdAt;
   private String videoId;
    
    public VideoLog(String videoTitle, java.util.Date videoStartedAt, java.util.Date videoEndedAt, java.util.Date createdAt, String videoId){
    	this.setvideoName(videoTitle);
        this.setstartedAt(videoStartedAt);
        this.setendedAt(videoEndedAt); 
        this.setcreatedAt(createdAt); 
        this.setVideoId(videoId);
    }
    
    // Empty constructor
    public VideoLog() {
		// TODO Auto-generated constructor stub
	}	
	
	public long id() {
		return id;
	}
	public void setid(long l) {
		this.id = l;
	}
	public String videoId() {
		return videoId;
	}
	public void setVideoId(String videoId) {
		this.videoId = videoId;
	}
	public String videoName() {
		return videoName;
	}
	public void setvideoName(String videoName) {
		this.videoName = videoName;
	}
	public java.util.Date startedAt() {
		return startedAt;
	}
	public void setstartedAt(java.util.Date date) {
		this.startedAt = date;
	}
	public java.util.Date endedAt() {
		return endedAt;
	}
	public void setendedAt(java.util.Date date) {
		this.endedAt = date;
	}
	public java.util.Date createdAt() {
		return createdAt;
	}
	public void setcreatedAt(java.util.Date createdAt) {
		this.createdAt = createdAt;
	}
	
}
