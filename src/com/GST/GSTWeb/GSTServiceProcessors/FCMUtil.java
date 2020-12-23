package com.GST.GSTWeb.GSTServiceProcessors;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

import javax.net.ssl.HttpsURLConnection;

import org.json.simple.JSONObject;

import com.GST.GSTWeb.GSTWebAPI_Global_Vars;


import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.AndroidConfig;
import com.google.firebase.messaging.AndroidNotification;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;

public class FCMUtil {

	@SuppressWarnings("unchecked")
	//public boolean send_FCM(String tokenId, String title, String content){
	public boolean send_FCM(String tokenId, String sender_id, String message, String msg_id, Timestamp msg_ts){
		
		String response_body = null;
		URLConnection urlCon = null;
		Boolean send_msg_result = false;
		String content = sender_id + " ´ÔÀÇ ¸Þ¼¼Áö: ";
		
		try {
			FileInputStream serviceAccount = new FileInputStream(GSTWebAPI_Global_Vars.FCM_ACCESS_KEY_LOCATION);
	
			FirebaseOptions options = new FirebaseOptions.Builder()
													     .setCredentials(GoogleCredentials.fromStream(serviceAccount))
													     //.setDatabaseUrl("https://grandslamtennis-337d0.firebaseio.com") //https://console.firebase.google.com/project/grandslamtennis-337d0/settings/serviceaccounts/adminsdk
													     .setDatabaseUrl("https://gstmobile.firebaseio.com") //https://console.firebase.google.com/project/gstmobile/settings/serviceaccounts/adminsdk
													     .build();
	
			if(FirebaseApp.getApps().isEmpty()) FirebaseApp.initializeApp(options);
			
			String registrationToken = tokenId;
			
			Date date = new Date();
			Calendar cal = Calendar.getInstance();       // get calendar instance
			cal.setTime(date); 
			
			Timestamp purge_timestamp = new Timestamp(cal.getTimeInMillis());
			
			String send_time_whole = purge_timestamp.toString();
			
			StringTokenizer st = new StringTokenizer(send_time_whole, " ");
			String send_date_tmp = st.nextToken();
			String send_time_tmp = st.nextToken();
			
			st = new StringTokenizer(send_date_tmp, "-");
			st.nextToken();
			String send_date = "";
			send_date = send_date + st.nextToken() + "/";
			send_date = send_date + st.nextToken() + " ";
			
			st = new StringTokenizer(send_time_tmp, ":");
			send_date = send_date + st.nextToken() + ":";
			send_date = send_date + st.nextToken();
			
			send_date = "(" + send_date + ")";
			
			/*Message msg = Message.builder()
								 .setAndroidConfig(AndroidConfig.builder()
										 						.setTtl(3600 * 1000)
										 						.setPriority(AndroidConfig.Priority.NORMAL)
										 						.putData("msg_id", msg_id)
										 						.setNotification(AndroidNotification.builder()
										 															.setTitle(content)
										 															.setBody(message)
										 															.setIcon("new_message")
										 															.setColor("#f45342")
										 															.setSound("default")
										 															.build())
										 						
										 						.build())
								 .setToken(registrationToken)
								 .build();*/
			Message msg = Message.builder()
								 .putData("title", content)
								 .putData("body", /*send_date + " " + */message)
								 .putData("msg_id", msg_id)
								 //.putData("msg_timestamp", msg_ts.toString())
								 .setToken(registrationToken)
								 .build();
//System.out.println("sent to FCM: " + msg.toString());			
			String response = FirebaseMessaging.getInstance().send(msg);
			
System.out.println("response from fcm: " + response);
			
			/*urlCon = new URL(GSTWebAPI_Global_Vars.FCM_SERVER_ADDR).openConnection();
			((HttpsURLConnection) urlCon).setDoInput(true); 
			((HttpsURLConnection) urlCon).setDoOutput(true);
			//((HttpsURLConnection) urlCon).setSSLSocketFactory( sslSocketFactory ); // Tell the url connection object to use our socket factory which bypasses security checks
			((HttpsURLConnection) urlCon).setRequestMethod("POST");
			((HttpsURLConnection) urlCon).setRequestProperty("Content-Type", "application/json; charset=utf-8");
			((HttpsURLConnection) urlCon).setRequestProperty("Authorization", "key=" + GSTWebAPI_Global_Vars.FCM_SERVER_KEY);
			((HttpsURLConnection) urlCon).setDoOutput(true);
			
			//String input = "{\"notification\" : {\"title\" : \"" + title + "\", \"body\" : \"" + content + "\"}, \"to\":\"" + tokenId + " \"}";
			JSONObject root = new JSONObject();
            JSONObject notification = new JSONObject();
            notification.put("body", content);
            notification.put("title", title);
            notification.put("icon", "ic_gst_msg_noti");
            root.put("data", notification);
            root.put("to", tokenId);
            root.put("click_action", "OPEN_ACTIVITY");

			//byte [] bytes_to_write = input.getBytes("UTF-8");
            byte [] bytes_to_write = root.toString().getBytes("UTF-8");
			
			DataOutputStream output = new DataOutputStream(((HttpsURLConnection) urlCon).getOutputStream()); 
			output.write(bytes_to_write);
			
			output.flush();
			output.close();	*/
	
			/*BufferedReader br = new BufferedReader(new InputStreamReader((urlCon.getInputStream())));
			StringBuilder sb = new StringBuilder();
			String response_body_tmp;
			while ((response_body_tmp = br.readLine()) != null) {
			  sb.append(response_body_tmp);
			}				
			
			response_body = sb.toString();	
System.out.println(response_body);*/
			send_msg_result = true;
			
		} catch(Exception e) {
			e.printStackTrace();
			
		}
		
		return send_msg_result;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}


























