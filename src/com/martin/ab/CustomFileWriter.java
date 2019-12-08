package com.martin.ab;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CustomFileWriter {
    private static final int MAX_ALLOWED_LOG_FILE_SIZE_MB = 200;
    private static int FILE_NBR = 1;

    public void writeLine(String logLine) throws IOException {
        String filePath = getFileName();
   
        File file = new File(filePath);
        if (!file.createNewFile()) {
            if (overSizeLimit(file)) {
                FILE_NBR++;
                filePath = getFileName();
                file.createNewFile();
            }
        }

        String lineToWrite = logLine + "\n";
        
        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true)); 
        writer.append(lineToWrite);
        writer.close();
    }

    public static String getFileName() {
    	return "/volume1/homes/tomcatUploads/log" + FILE_NBR + ".txt";
    }

    private boolean overSizeLimit(File file) {
        long fileLengthInBytes = file.length();
        return ( (fileLengthInBytes / 1024) / 1024 ) > MAX_ALLOWED_LOG_FILE_SIZE_MB;
    }
}