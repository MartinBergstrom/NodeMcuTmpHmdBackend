package com.martin.ab;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class AppLogger {
	private static final String LOG_NAME = "/volume1/homes/tomcatUploads/backend_log.txt";
    private static final int MAX_ALLOWED_LOG_FILE_SIZE_MB = 200;


	public void log(String line) {
	    try {
	    	
	    	File file = new File(LOG_NAME);
	        if (!file.createNewFile()) {
	            if (overSizeLimit(file)) {
	            	file.delete();
	                file.createNewFile();
	            }
	        }

		    BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_NAME, true)); 
	        writer.append(line);
	        writer.append("\n");
	        writer.close();
		    
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	  private boolean overSizeLimit(File file) {
	        long fileLengthInBytes = file.length();
	        return ( (fileLengthInBytes / 1024) / 1024 ) > MAX_ALLOWED_LOG_FILE_SIZE_MB;
	    }
}
