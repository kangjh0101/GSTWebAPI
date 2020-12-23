package com.GST.GSTWeb.GSTServiceProcessors;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.stream.Collectors;

import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.GST.GSTWeb.GSTWebAPI_Global_Vars;

public class ProcessGETRequests implements Runnable{

	Thread service_proc;
	private String get_request_str;
	private int rule_id;
	private String method;
	private AsyncContext asyncContext;
	private HttpServletRequest request;
	private HttpServletResponse response;
	
	public ProcessGETRequests(AsyncContext ac){
		
		//request_str = string_to_parse;
		asyncContext = ac;
		request = (HttpServletRequest) asyncContext.getRequest();
		response = (HttpServletResponse) asyncContext.getResponse();
		service_proc = new Thread(this);
		service_proc.start();
	}

	@SuppressWarnings("unchecked")
	public void run(){

		try {			
			StringBuilder stringBuilder = new StringBuilder();
			InputStream inputStream = request.getInputStream();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
			char[] charBuffer = new char[128];
            int bytesRead = -1;
            while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                stringBuilder.append(charBuffer, 0, bytesRead);
            }

            get_request_str = stringBuilder.toString();		
System.out.println("get request line: " + get_request_str);			
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(get_request_str);
			JSONObject jsonObject =  (JSONObject) obj;
			
			String action = (String) jsonObject.get("action");
System.out.println(action + "\n" + "wanted_ad_list");			
			/*if(action.equals("wanted_ad_list")) {
				
				process_wanted_list(jsonObject);
				
			} else if(action.equals("register")) {
				
				process_register(jsonObject);
				
			} else if(action.equals("check_id")) {
				
				process_check_id(jsonObject);
				
			}*/


		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	










}




































