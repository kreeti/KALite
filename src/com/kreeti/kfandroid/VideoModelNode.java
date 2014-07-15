/*
 *	Created by Nabarun Banerjee on 11/07/14.
 *  Copyright (c) 2014 Kreeti Technologies. All rights reserved.
 */

package com.kreeti.kfandroid;

import java.util.ArrayList;

public class VideoModelNode {
	public String id;
	public String title;
	public boolean isVideo;
	ArrayList<VideoModelNode> children;
	public VideoModelNode parent;
	
	public String videoFileName() {
		return id + ".mp4";
	}
}
