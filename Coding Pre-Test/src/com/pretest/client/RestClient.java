/**
 * 
 */
package com.pretest.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * @author saurabh
 *
 */


public class RestClient {

	
		
	public static void main(String args[]) {
		
		
	try {		
		ResourceBundle resource = ResourceBundle.getBundle("Application");
		String uri=resource.getString("URL");
		URL url = new URL(uri);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", "application/json");

		if (conn.getResponseCode() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "
					+ conn.getResponseCode());
		}

		BufferedReader br = new BufferedReader(new InputStreamReader(
			(conn.getInputStream())));
		
		StringBuilder buffer = new StringBuilder();
		String output;
		
		
		while ((output = br.readLine()) != null) {
			buffer.append(output);
		}
			
		String finalJson = buffer.toString();
		JSONParser jsonParser=new JSONParser();
		JSONArray arrayObj= (JSONArray) jsonParser.parse(finalJson);
		int count=0;
		Long total=0L;
		System.out.println("Data coming from REST Service .... "+"\n");
		
		 for (int i = 0; i < arrayObj.size(); i++)
	        {  System.out.print("--- Document --> "+(i+1)+"\n");
			 	JSONObject jsonObj = (JSONObject) arrayObj.get(i);	            
	            Iterator a = jsonObj.keySet().iterator();
	            while(a.hasNext()) {
	            	String key = (String)a.next();                
	                System.out.print("key : "+key);
	                System.out.println(".... value :"+jsonObj.get(key));
	                if(key.equalsIgnoreCase("numbers")) {
	                	JSONArray arr=(JSONArray)jsonObj.get(key);
	                	for(int j=0;j<arr.size();j++){
	                		count += j;
	                		total +=(Long)arr.get(j);
	                		}
	                	}
	            	}	            
	        	}
		 
		 System.out.print("------------------\n");
         System.out.print("COUNT : "+count);
         System.out.println("      TOTAL : "+total);
		conn.disconnect();	
		

	  } catch (Exception e) {

		e.printStackTrace();

	  }

	}

}
	

