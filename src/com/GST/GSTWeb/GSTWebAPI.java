package com.GST.GSTWeb;

import java.io.IOException;
import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.GST.GSTWeb.GSTServiceProcessors.*;

@WebServlet(urlPatterns = "/Req_Service", asyncSupported = true)
public class GSTWebAPI extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	public GSTWebAPI() {
        super();
        
        try{
        	System.out.println("Loading JDBC Driver....");
			Class.forName("com.mysql.jdbc.Driver");	
			System.out.println("Finished Loading JDBC Driver....");
			
			int db_try_count = 0;
			while(!GSTWebAPI_Global_Vars.init_db_conn()) {
				db_try_count++;
	        	Thread.sleep(1000);
	        	
	        	if(db_try_count > 100) {
	        		System.out.println("unable to connect to DB");
	        		System.exit(0);
	        	}
	        }
			
		} catch(ClassNotFoundException e){
			//APS_Rule_Parser_Logger.write_log(e.toString());
			//e.printStackTrace();
			e.printStackTrace();
			System.exit(0);
		} catch(Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
        
    }
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
System.out.println("POST request received");		
		AsyncContext asyncContext = request.startAsync();
		asyncContext.setTimeout(900000000);
		new ProcessPOSTRequests(asyncContext);
	}
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
System.out.println("GET request received");		
		AsyncContext asyncContext = request.startAsync();
		asyncContext.setTimeout(900000000);
		new ProcessGETRequests(asyncContext);
	}
}


















