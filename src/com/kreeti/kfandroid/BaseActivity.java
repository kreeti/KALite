/*
 *	Created by Nabarun Banerjee on 1/09/14.

 *  Copyright (c) 2014 Kreeti Technologies. All rights reserved.
 */
package com.kreeti.kfandroid;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class BaseActivity extends Activity implements Constants{
	
	//Create .ZIP file of sourcePath to the destination location	
	public boolean zipFileAtPath(String sourcePath, String toLocation) {	   
	    final int BUFFER = 2048;
	    File sourceFile = new File(sourcePath);
	    try {
	        BufferedInputStream origin = null;
	        FileOutputStream dest = new FileOutputStream(toLocation);
	        ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(
	                dest));
	        if (sourceFile.isDirectory()) {
	            zipSubFolder(out, sourceFile, sourceFile.getParent().length());
	        } else {
	            byte data[] = new byte[BUFFER];
	            FileInputStream fi = new FileInputStream(sourcePath);
	            origin = new BufferedInputStream(fi, BUFFER);
	            ZipEntry entry = new ZipEntry(getLastPathComponent(sourcePath));
	            out.putNextEntry(entry);
	            int count;
	            while ((count = origin.read(data, 0, BUFFER)) != -1) {
	                out.write(data, 0, count);
	            }
	        }
	        out.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	    return true;
	}
	
	private void zipSubFolder(ZipOutputStream out, File folder,
	        int basePathLength) throws IOException {

	    final int BUFFER = 2048;

	    File[] fileList = folder.listFiles();
	    BufferedInputStream origin = null;
	    for (File file : fileList) {
	        if (file.isDirectory()) {
	            zipSubFolder(out, file, basePathLength);
	        } else {
	            byte data[] = new byte[BUFFER];
	            String unmodifiedFilePath = file.getPath();
	            String relativePath = unmodifiedFilePath
	                    .substring(basePathLength);
	            Log.i("ZIP SUBFOLDER", "Relative Path : " + relativePath);
	            FileInputStream fi = new FileInputStream(unmodifiedFilePath);
	            origin = new BufferedInputStream(fi, BUFFER);
	            ZipEntry entry = new ZipEntry(relativePath);
	            out.putNextEntry(entry);
	            int count;
	            while ((count = origin.read(data, 0, BUFFER)) != -1) {
	                out.write(data, 0, count);
	            }
	            origin.close();
	        }
	    }
	}

	/*
	 * gets the last path component
	 * 
	 * Example: getLastPathComponent("downloads/example/fileToZip");
	 * Result: "fileToZip"
	 */
	public String getLastPathComponent(String filePath) {
	    String[] segments = filePath.split("/");
	    String lastPathComponent = segments[segments.length - 1];
	    return lastPathComponent;
	}
	
	//Show Alert message with message param	
	public void showAlert(String message){
		AlertDialog alertDialog = new AlertDialog.Builder(this).create();
		alertDialog.setTitle("Error!");
		alertDialog.setMessage(message);
		alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
		   public void onClick(DialogInterface dialog, int which) {
		      // TODO Add your code for the button here.
		   }
		});
		alertDialog.show();
	}
	
	//Rechability check
	protected boolean isNetworkConnected() {
		  ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		  NetworkInfo ni = cm.getActiveNetworkInfo();
		  if (ni == null) {
		   // There are no active networks.
		   return false;
		  } else
		   return true;
		 }
	
	public Date getCurrentDateTime() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		Calendar cal = Calendar.getInstance();	
		String ds = "2010-10-15T09:27:37Z";//dateFormat.format(cal.getTime());
		Date d = null;
		try {
			d = (Date) dateFormat.parse(ds);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return d;
	}
}
