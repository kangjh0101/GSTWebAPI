package com.GST.GSTWeb;

import java.sql.Connection;
import java.sql.DriverManager;

public class GSTWebAPI_Global_Vars {

	// Constant
	public static final String DB_URL = "jdbc:mysql://localhost:3306/gst";
	public static final String DB_USER = "kangjh0101";
	public static final String DB_PW = "kang0101";
	//public static final String FCM_SERVER_KEY = "AAAAa2zX2ZY:APA91bEgcOYj2rM6x8tyIgMRsjguYxIxVzKqPa_DQyf9UAlQQV7i2LTWiz9mp35ZloFZzsBKDUPYjszy0u1JL99EJ0KHaNBedFKK3ttGQq-ceSySJ7ZvT9yyhJvK4upGT8WcaxEhAXaT";
	//public static final String FCM_SERVER_KEY = "AAAAD0LWKJ0:APA91bEbUiGWOxroFMIwDB4BPlbCqkPfZgyd8c5Urnrjeuryg6tLRviOyJCe7ua1QQRNul8TCJFypOwBGbvG5q_Lq1McFF4Bc7HsxPGpCgmV74ZSjM1XszVIvnM6WCp4_kHrfGWv06hd";
	public static final String FCM_SERVER_KEY = "AAAA67aBpYM:APA91bHfgM7kjJPv2E_9U7TdPlT_s5XI82sV4rjmFvnRlslMR2mpG0XXa_Bv23HvmV_Xl2O1n3vyURL5ByrDCeYpyUP4Bz-prPQdd2FYvErCwMggAQ4JNqTshcoMBKwafcwnwjffiOEh";
	public static final String FCM_SERVER_ADDR = "https://fcm.googleapis.com/v1/projects/";
	//public static final String FCM_ACCESS_KEY_LOCATION = "/project/GrandSlamTennis/grandslamtennis-337d0-firebase-adminsdk-b05c2-e08e068164.json";
	public static final String FCM_ACCESS_KEY_LOCATION = "/project/GrandSlamTennis/matchfindergrandslamtennis-firebase-adminsdk-x5suk-bcddcfc4e5.json"; 
														//"/project/GrandSlamTennis/gstmobile-firebase-adminsdk-zbp64-42302df3f8.json"; 
														//https://console.firebase.google.com/project/gstmobile/settings/serviceaccounts/adminsdk -> new private key
	
	public static final int APP_VERSION_CURRENT = 0;
	public static final int APP_VERSION_UPDATE_REQUIRED = 1;
	public static final int APP_VERSION_CHEKC_ERROR = 2;
	
	public static final int NO_NAME_FOUND = 5;
	public static final int NO_ID_FOUND = 6;
	public static final int FAILED_TO_SEND_MAIL = 7;
	
	// Global Variables
	public static Connection db_conn = null;
	
	public static boolean init_db_conn() {
		
		try {
			db_conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PW);
			return true;
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static void check_db_connection() {
		
		try {
			if(!db_conn.isValid(0)){
				System.out.println("DB reconnected");				
				db_conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PW);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
}

































