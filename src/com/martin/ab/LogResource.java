package com.martin.ab;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/log")
public class LogResource {
   private CustomFileWriter myCustomFileWriter = new CustomFileWriter();
      
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public Response doGet() throws IOException {
		String fileName = CustomFileWriter.getFileName();

		BufferedReader br = new BufferedReader(new FileReader(fileName));
	    StringBuilder sb = new StringBuilder();
		try {
		    String line = br.readLine();

		    while (line != null) {
		        sb.append(line);
		        sb.append(System.lineSeparator());
		        line = br.readLine();
		    }
		} finally {
		    br.close();
		}
        
        return Response.status(200)
        .header("Access-Control-Allow-Origin", "*")
        .header("Access-Control-Allow-Credentials", "true")
        .header("Access-Control-Allow-Methods", "POST, GET")
        .header("Access-Control-Allow-Headers", "Content-Type")
        .entity(sb.toString())
        .build();
	}

	@Path("new")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    public Response postLog(String logLine) {
        String finalLogLine = getTimeStamp() + ":" + logLine;
  
        try {
        	myCustomFileWriter.writeLine(finalLogLine);
        } catch (IOException e) {
            e.printStackTrace();
            return Response.status(500).entity("Error occurred during writing to file..")
                    .build();
        }
        return Response.status(201)
                .build();
	}
	
	private String getTimeStamp()
	{
		return new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss")
				.format(new Date());
	}

}
