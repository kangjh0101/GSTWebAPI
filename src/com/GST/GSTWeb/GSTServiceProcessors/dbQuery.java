package com.GST.GSTWeb.GSTServiceProcessors;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;

import javax.imageio.ImageIO;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.GST.GSTWeb.GSTWebAPI_Global_Vars;

public class dbQuery {

	public static boolean db_query_bug_report(String user_id, String content) {
		
		boolean is_reported = false;
		
		try {
			GSTWebAPI_Global_Vars.check_db_connection();
			
			PreparedStatement ps_exit_msg = GSTWebAPI_Global_Vars.db_conn.prepareStatement("insert into bug_report (user_id, bug) values (?, ?)");
			ps_exit_msg.setString(1, user_id);
			ps_exit_msg.setString(2, content);
			ps_exit_msg.executeUpdate();
			
			is_reported = true;
			
		} catch(Exception e) {
			e.printStackTrace();
			is_reported = true;
		}
		
		return is_reported;
	}
	
	public static boolean db_query_withdraw_user(String user_id) {
		
		boolean is_reported = false;
		
		try {
			GSTWebAPI_Global_Vars.check_db_connection();
			
			PreparedStatement ps_exit_msg = GSTWebAPI_Global_Vars.db_conn.prepareStatement("delete from players where p_id = ?");
			ps_exit_msg.setString(1, user_id);
			ps_exit_msg.executeUpdate();
			
			ps_exit_msg = GSTWebAPI_Global_Vars.db_conn.prepareStatement("delete from wanted_ad where user_id = ?");
			ps_exit_msg.setString(1, user_id);
			ps_exit_msg.executeUpdate();
			
			ps_exit_msg = GSTWebAPI_Global_Vars.db_conn.prepareStatement("delete from black_list where p_id = ?");
			ps_exit_msg.setString(1, user_id);
			ps_exit_msg.executeUpdate();
			
			is_reported = true;
			
		} catch(Exception e) {
			e.printStackTrace();
			is_reported = true;
		}
		
		return is_reported;
	}
	
	public static String db_query_find_pw(String user_id, String phone_number, String email) {
		
		String user_email;
		
		try {
			GSTWebAPI_Global_Vars.check_db_connection();
			
			PreparedStatement ps_check_block = GSTWebAPI_Global_Vars.db_conn.prepareStatement("select * from players where p_id = ? and p_phone = ? and p_email = ?");
			ps_check_block.setString(1, user_id);
			ps_check_block.setString(2, phone_number);
			ps_check_block.setString(3, email);
			ResultSet rs_check_block = ps_check_block.executeQuery();
			
			if(rs_check_block.next() != false) user_email = rs_check_block.getString("p_pw");
			else user_email = null;
			
		} catch(Exception e) {
			e.printStackTrace();
			user_email = null;
		}
		
		return user_email;
		
	}
	
	public static String db_query_find_id(String user_name, String phone_number, String email) {
		
		String user_id;
		
		try {
			GSTWebAPI_Global_Vars.check_db_connection();
			
			PreparedStatement ps_check_block = GSTWebAPI_Global_Vars.db_conn.prepareStatement("select * from players where p_name = ? and p_phone = ? and p_email = ?");
			ps_check_block.setString(1, user_name);
			ps_check_block.setString(2, phone_number);
			ps_check_block.setString(3, email);
			ResultSet rs_check_block = ps_check_block.executeQuery();
			
			if(rs_check_block.next() != false) user_id = rs_check_block.getString("p_id");
			else user_id = null;
			
		} catch(Exception e) {
			e.printStackTrace();
			user_id = null;
		}
		
		return user_id;
		
	}

	public static boolean db_query_check_block(String user_id, String receiver_id) {
		
		boolean is_blocked = false;
		
		try {
			GSTWebAPI_Global_Vars.check_db_connection();
			
			PreparedStatement ps_check_block = GSTWebAPI_Global_Vars.db_conn.prepareStatement("select * from block_list where user_id = ? and blocked_id = ?");
			ps_check_block.setString(1, receiver_id);
			ps_check_block.setString(2, user_id);
			ResultSet rs_check_block = ps_check_block.executeQuery();
			
			if(rs_check_block.next() != false) is_blocked = true;
			
		} catch(Exception e) {
			e.printStackTrace();
			is_blocked = true;
		}
		
		return is_blocked;
	}
	
	public static int db_query_check_block_user(String user_id, String receiver_id) {
		
		int is_user_blocked = 0;
		
		try {
			GSTWebAPI_Global_Vars.check_db_connection();
			
			PreparedStatement ps_exit_msg = GSTWebAPI_Global_Vars.db_conn.prepareStatement("select * from block_list where user_id = ? and blocked_id = ?");
			ps_exit_msg.setString(1, user_id);
			ps_exit_msg.setString(2, receiver_id);
			ResultSet blocked_rs = ps_exit_msg.executeQuery();
			
			if (blocked_rs.next() != false) {
				is_user_blocked = 1;
			}
			
		} catch(Exception e) {
			e.printStackTrace();
			is_user_blocked = 2;
		}
		
		return is_user_blocked;
	}
	
	public static boolean db_query_unblock_user(String user_id, String receiver_id) {
		
		boolean block_user_success = true;
		
		try {
			GSTWebAPI_Global_Vars.check_db_connection();
			
			PreparedStatement ps_exit_msg = GSTWebAPI_Global_Vars.db_conn.prepareStatement("delete from block_list where user_id = ? and blocked_id = ?");
			ps_exit_msg.setString(1, user_id);
			ps_exit_msg.setString(2, receiver_id);
			ps_exit_msg.executeUpdate();
			
		} catch(Exception e) {
			e.printStackTrace();
			block_user_success = false;
		}
		
		return block_user_success;
	}
	
	public static boolean db_query_block_user(String user_id, String receiver_id) {
		
		boolean block_user_success = true;
		
		try {
			GSTWebAPI_Global_Vars.check_db_connection();
			
			PreparedStatement ps_exit_msg = GSTWebAPI_Global_Vars.db_conn.prepareStatement("insert into block_list (user_id, blocked_id) values (?, ?)");
			ps_exit_msg.setString(1, user_id);
			ps_exit_msg.setString(2, receiver_id);
			ps_exit_msg.executeUpdate();
			
		} catch(Exception e) {
			e.printStackTrace();
			block_user_success = false;
		}
		
		return block_user_success;
	}
	
	public static boolean db_query_exit_message(String user_id, String receiver_id) {
		
		boolean exit_message_success = true;
		
		try {
			GSTWebAPI_Global_Vars.check_db_connection();
			
			PreparedStatement ps_exit_msg = GSTWebAPI_Global_Vars.db_conn.prepareStatement("update messages set sender_exit = 'Y' where sender_id = ? and receiver_id = ?");
			ps_exit_msg.setString(1, user_id);
			ps_exit_msg.setString(2, receiver_id);
			ps_exit_msg.executeUpdate();
			
			ps_exit_msg = GSTWebAPI_Global_Vars.db_conn.prepareStatement("update messages set receiver_exit = 'Y' where sender_id = ? and receiver_id = ?");
			ps_exit_msg.setString(1, receiver_id);
			ps_exit_msg.setString(2, user_id);
			ps_exit_msg.executeUpdate();
			
			
		} catch(Exception e) {
			e.printStackTrace();
			exit_message_success = false;
		}
		
		return exit_message_success;
		
	}
			
			
	public static boolean db_query_post_wanted(String user_id, 
		     String op_ntrp, 
		     String op_gender, 
		     String op_howlong, 
		     String court_booked, 
		     String location, 
		     String d_to_c, // date to close 
		     String comment) {

		boolean register_success = false;
		
		try {
			Date date_to_close = Date.valueOf(d_to_c);
			
			GSTWebAPI_Global_Vars.check_db_connection();
			PreparedStatement ps_login_check = GSTWebAPI_Global_Vars.db_conn.prepareStatement("insert into wanted_ad (user_id, op_ntrp, op_howlong, op_gender, location, court_booked, p_comment, date_to_close) "
																			 + "values (?, ?, ?, ?, ?, ?, ?, ?)");
			ps_login_check.setString(1, user_id);
			ps_login_check.setString(2, op_ntrp);
			ps_login_check.setString(3, op_howlong);
			ps_login_check.setString(4, op_gender);
			ps_login_check.setString(5, location);
			ps_login_check.setString(6, court_booked);
			ps_login_check.setString(7, comment);
			ps_login_check.setDate(8, date_to_close);
			ps_login_check.executeUpdate();
			
			register_success = true;
		
		} catch(Exception e) {
			e.printStackTrace();
			register_success = false;
		}
		
		return register_success;
	}
	
	public static String db_query_wanted_list(String action, String user_id/*, String location, String ntrp, String howlong, String gender, String court_booked*/) {
		
		String return_value = null;
		JSONArray wanted_list;
		JSONObject search_result = new JSONObject();
		search_result.put("action", action);
		search_result.put("user_id", user_id);
		
		try {
			GSTWebAPI_Global_Vars.check_db_connection();
			ResultSet rs_wanted_list;
			
			SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = new Date(System.currentTimeMillis());
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			date = new Date(cal.getTimeInMillis());

			
			PreparedStatement ps_wanted_query = GSTWebAPI_Global_Vars.db_conn.prepareStatement("select * from wanted_ad where date_to_close>=? and active=?");
			ps_wanted_query.setDate(1, date);
			ps_wanted_query.setString(2, "y");
			rs_wanted_list = ps_wanted_query.executeQuery();
				
			wanted_list = make_wanted_list(rs_wanted_list);
			
			if(wanted_list != null){
				search_result.put("data", wanted_list);
				search_result.put("search_result", "success");
			} else {
				search_result.put("search_result", "fail");
			}
		
		} catch(Exception e) {
			e.printStackTrace();
			search_result.put("search_result", "fail");
		}
		
		return_value = search_result.toString();
		return return_value;
	}
	
	private static JSONArray make_wanted_list(ResultSet rs_wanted_list) {
		
		JSONArray search_result = new JSONArray();

		try {
			if (rs_wanted_list.next() != false) {
			
				rs_wanted_list.beforeFirst();
				
				while(rs_wanted_list.next()) {
					JSONObject search_element = new JSONObject();
					
					String post_id = Integer.toString(rs_wanted_list.getInt("idx"));
					String user_id = rs_wanted_list.getString("user_id");
					String op_ntrp = rs_wanted_list.getString("op_ntrp");
					String op_howlong = rs_wanted_list.getString("op_howlong");
					String op_gender = rs_wanted_list.getString("op_gender");
					String location = rs_wanted_list.getString("location");
					String court_booked = rs_wanted_list.getString("court_booked");
					//String date_to_close = rs_wanted_list.getDate("date_to_close").toString();
					String p_comment = rs_wanted_list.getString("p_comment");
//System.out.println(post_id + " / " + user_id + " / " + op_ntrp + " / " + op_howlong + " / " + op_gender + " / " + location + " / " + court_booked + " / " + p_comment);		
					search_element.put("post_id", post_id);
					search_element.put("user_id", user_id);
					search_element.put("op_ntrp", op_ntrp);
					search_element.put("op_howlong", op_howlong);
					search_element.put("op_gender", op_gender);
					search_element.put("location", location);
					search_element.put("court_booked", court_booked);
					//search_element.put("date_to_close", date_to_close);
					search_element.put("comment", p_comment);
					
					search_result.add(search_element);
				}
			
			}
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		
		return search_result;
	}
	
	public static String db_query_lesson_detail(String action, String user_id, String post_id) {
		
		String return_value = null;
		
		String image_str;
		JSONObject search_result = new JSONObject();
		search_result.put("action", action);
		search_result.put("user_id", user_id);
		
		try {
			GSTWebAPI_Global_Vars.check_db_connection();
			ResultSet rs_lesson_list;
			
			PreparedStatement ps_lesson_query = GSTWebAPI_Global_Vars.db_conn.prepareStatement("select * from lesson_info where idx=?");
			ps_lesson_query.setInt(1, Integer.valueOf(post_id));
			rs_lesson_list = ps_lesson_query.executeQuery();
			
			image_str = make_lesson_detail(rs_lesson_list);
			
			if(image_str != null){
				search_result.put("data", image_str);
				search_result.put("lesson_detail_result", "success");
			} else {
				search_result.put("lesson_detail_result", "fail");
			}
			
		} catch(Exception e) {
			e.printStackTrace();
			search_result.put("lesson_detail_result", "fail");
		}
		
		return_value = search_result.toString();
		return return_value;
	}
	
	public static String db_query_post_handover(String action, String user_id, String intro_string, String handover_detail) {
		
		String return_value = null;
		
		JSONObject search_result = new JSONObject();
		search_result.put("action", action);
		search_result.put("user_id", user_id);
		
		try {
			GSTWebAPI_Global_Vars.check_db_connection();
		
			PreparedStatement ps_post_handover = GSTWebAPI_Global_Vars.db_conn.prepareStatement("insert into handover (user_id, intro_string, handover_detail, active) values (?, ?, ?, ?)");
			ps_post_handover.setString(1, user_id);
			ps_post_handover.setString(2, intro_string);
			ps_post_handover.setString(3, handover_detail);
			ps_post_handover.setString(4, "y");
			ps_post_handover.executeUpdate();
			
			search_result.put("post_handover_result", "success");
			
		} catch(Exception e) {
			e.printStackTrace();
			search_result.put("post_handover_result", "fail");
		}
		
		return_value = search_result.toString();
		return return_value;
	}
	
	public static String db_query_club_detail(String action, String user_id, String post_id) {
		
		String return_value = null;
		
		String image_str;
		JSONObject search_result = new JSONObject();
		search_result.put("action", action);
		search_result.put("user_id", user_id);
		
		try {
			GSTWebAPI_Global_Vars.check_db_connection();
			ResultSet rs_lesson_list;
			
			PreparedStatement ps_lesson_query = GSTWebAPI_Global_Vars.db_conn.prepareStatement("select * from club_info where idx=?");
			ps_lesson_query.setInt(1, Integer.valueOf(post_id));
			rs_lesson_list = ps_lesson_query.executeQuery();
			
			image_str = make_club_detail(rs_lesson_list);
			
			if(image_str != null){
				search_result.put("data", image_str);
				search_result.put("lesson_detail_result", "success");
			} else {
				search_result.put("lesson_detail_result", "fail");
			}
			
		} catch(Exception e) {
			e.printStackTrace();
			search_result.put("lesson_detail_result", "fail");
		}
		
		return_value = search_result.toString();
		return return_value;
	}
	
	private static String make_club_detail(ResultSet rs_lesson_list) {
		
		byte [] data = null;
		String data_str = null;
		
		try {
			if (rs_lesson_list.next() != false) {
				String intro_file_name = rs_lesson_list.getString("club_pic_location");
//System.out.println("image location: " + intro_file_name);				
				data = Files.readAllBytes(Paths.get(intro_file_name));
				data_str = Base64.getEncoder().encodeToString(data);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return data_str;
	}

	public static String db_query_upload_club_ad(String action, String user_id, String data_format, String intro, String comment, String data_tmp) {
		
		String return_value = null;
		String club_pic_location = "/project/GrandSlamTennis/clubInfo/";
		int club_info_insert_indx = 1;
		
		String image_str;
		JSONObject search_result = new JSONObject();
		search_result.put("action", action);
		search_result.put("user_id", user_id);
		
		FileOutputStream fos = null;
		File file;
		
		try {
			GSTWebAPI_Global_Vars.check_db_connection();
			
			PreparedStatement ps_login_check = GSTWebAPI_Global_Vars.db_conn.prepareStatement("select * from club_info order by idx desc limit 1");
			ResultSet rs = ps_login_check.executeQuery();
			if(rs.next()) {
				int tmp_idx = rs.getInt("idx");
				club_info_insert_indx = tmp_idx + 1;
			}
			
			
			ps_login_check = GSTWebAPI_Global_Vars.db_conn.prepareStatement("insert into club_info (idx, user_id, intro_string, club_detail, club_pic_location, data_format) "
																							 + "values (?, ?, ?, ?, ?, ?)");
			ps_login_check.setInt(1, club_info_insert_indx);
			ps_login_check.setString(2, user_id);
			ps_login_check.setString(3, intro);
			ps_login_check.setString(4, comment);
			if(data_tmp != null && data_format != null) {
				ps_login_check.setString(5, club_pic_location + club_info_insert_indx + "." + data_format);
				ps_login_check.setString(6, data_format);
			} else {
				ps_login_check.setString(5, null);
				ps_login_check.setString(6, "no_pic");
			}
			ps_login_check.executeUpdate();

			if(data_tmp != null && data_format != null) {
				//byte [] pic_bytes = Base64.getDecoder().decode(data_tmp);
				byte [] pic_bytes = Base64.getMimeDecoder().decode(data_tmp);
	
				file = new File(club_pic_location + club_info_insert_indx + "." + data_format);
				fos = new FileOutputStream(file);	
				fos.write(pic_bytes);
				fos.close();
			}
			
			search_result.put("club_detail_result", "success");
			
		} catch(Exception e) {
			e.printStackTrace();
			search_result.put("club_detail_result", "fail");
			if(fos != null) {
				try {
					fos.close();
				} catch(Exception ei) {
					ei.printStackTrace();
				}
			}
		}
		
		return_value = search_result.toString();
		return return_value;
	}
	
	private static String make_lesson_detail(ResultSet rs_lesson_list) {
		
		byte [] data = null;
		String data_str = null;
		
		try {
			if (rs_lesson_list.next() != false) {
				String intro_file_name = rs_lesson_list.getString("intro_file_name");
//System.out.println("image location: " + intro_file_name);				
				data = Files.readAllBytes(Paths.get(intro_file_name));
				data_str = Base64.getEncoder().encodeToString(data);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return data_str;
	}
	
	public static String db_query_lesson_ad(String action, String user_id, String coarse_location) {
		
		String return_value = null;
		
		JSONArray my_list;
		JSONObject search_result = new JSONObject();
		search_result.put("action", action);
		search_result.put("user_id", user_id);
		search_result.put("coarse_location", coarse_location);
		
		try {
			GSTWebAPI_Global_Vars.check_db_connection();
			ResultSet rs_lesson_list;
			
			PreparedStatement ps_lesson_query = GSTWebAPI_Global_Vars.db_conn.prepareStatement("select * from lesson_info where coarse_location=?");
			ps_lesson_query.setInt(1, Integer.valueOf(coarse_location));
			rs_lesson_list = ps_lesson_query.executeQuery();
			
			my_list = make_lesson_list(rs_lesson_list);
			
			if(my_list != null){
				search_result.put("data", my_list);
				search_result.put("lesson_ad_result", "success");
			} else {
				search_result.put("lesson_ad_result", "fail");
			}
			
		} catch(Exception e) {
			e.printStackTrace();
			search_result.put("lesson_ad_result", "fail");
		}
		
		return_value = search_result.toString();
		return return_value;
	}
	
	private static JSONArray make_lesson_list(ResultSet rs_lesson_list) {
		
		JSONArray search_result = new JSONArray();
		
		try {
			if (rs_lesson_list.next() != false) {
				
				rs_lesson_list.beforeFirst();
				
				while(rs_lesson_list.next()) {
					JSONObject search_element = new JSONObject();
					
					String post_id = Integer.toString(rs_lesson_list.getInt("idx"));
					String coarse_location = rs_lesson_list.getString("coarse_location");
					String coach_name = rs_lesson_list.getString("coach_name");
					String coach_phone = rs_lesson_list.getString("coach_phone");
					String coach_addr = rs_lesson_list.getString("coach_addr");
					String intro_string = rs_lesson_list.getString("intro_string");
					String intro_file_name = rs_lesson_list.getString("intro_file_name");
//System.out.println(post_id + " / " + user_id + " / " + op_ntrp + " / " + op_howlong + " / " + op_gender + " / " + location + " / " + court_booked + " / " + p_comment);		
					search_element.put("post_id", post_id.toString());
					search_element.put("coarse_location", coarse_location);
					search_element.put("coach_name", coach_name);
					search_element.put("coach_phone", coach_phone);
					search_element.put("coach_addr", coach_addr);
					search_element.put("intro_string", intro_string);
					search_element.put("intro_file_name", intro_file_name);
					
					search_result.add(search_element);
				}
			
			}
			
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		
		return search_result;
	}
	
	public static String db_query_club_ad(String action, String user_id) {
		
		String return_value = null;
		
		JSONArray my_list;
		JSONObject search_result = new JSONObject();
		search_result.put("action", action);
		search_result.put("user_id", user_id);
		
		try {
			GSTWebAPI_Global_Vars.check_db_connection();
			ResultSet rs_lesson_list;
			
			PreparedStatement ps_lesson_query = GSTWebAPI_Global_Vars.db_conn.prepareStatement("select * from club_info");
			rs_lesson_list = ps_lesson_query.executeQuery();
			
			my_list = make_club_list(rs_lesson_list);
			
			if(my_list != null){
				search_result.put("data", my_list);
				search_result.put("club_ad_result", "success");
			} else {
				search_result.put("club_ad_result", "fail");
			}
			
		} catch(Exception e) {
			e.printStackTrace();
			search_result.put("club_ad_result", "fail");
		}
		
		return_value = search_result.toString();
		return return_value;
	}
	
	public static String db_query_handover_ad(String action, String user_id) {
		
		String return_value = null;
		
		JSONArray my_list;
		JSONObject search_result = new JSONObject();
		search_result.put("action", action);
		search_result.put("user_id", user_id);
		
		try {
			GSTWebAPI_Global_Vars.check_db_connection();
			ResultSet rs_lesson_list;
			
			PreparedStatement ps_lesson_query = GSTWebAPI_Global_Vars.db_conn.prepareStatement("select * from handover where active = ? order by idx desc");
			ps_lesson_query.setString(1, "y");
			rs_lesson_list = ps_lesson_query.executeQuery();
			
			my_list = make_handover_list(rs_lesson_list);
			
			if(my_list != null){
				search_result.put("data", my_list);
				search_result.put("handover_ad_result", "success");
			} else {
				search_result.put("handover_ad_result", "fail");
			}
			
		} catch(Exception e) {
			e.printStackTrace();
			search_result.put("handover_ad_result", "fail");
		}
		
		return_value = search_result.toString();
		return return_value;
	}
	
	private static JSONArray make_handover_list(ResultSet rs_lesson_list) {
		
		JSONArray search_result = new JSONArray();
		
		try {
			if (rs_lesson_list.next() != false) {
				
				rs_lesson_list.beforeFirst();
				
				while(rs_lesson_list.next()) {
					JSONObject search_element = new JSONObject();
					
					String post_id = Integer.toString(rs_lesson_list.getInt("idx"));
					String user_id = rs_lesson_list.getString("user_id");
					String intro_string = rs_lesson_list.getString("intro_string");
					String club_detail = rs_lesson_list.getString("handover_detail");
//System.out.println(post_id + " / " + user_id + " / " + op_ntrp + " / " + op_howlong + " / " + op_gender + " / " + location + " / " + court_booked + " / " + p_comment);		
					search_element.put("post_id", post_id.toString());
					search_element.put("user_id", user_id);
					search_element.put("intro_string", intro_string);
					search_element.put("handover_detail", club_detail);
					
					search_result.add(search_element);
				}
			
			}
			
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		
		return search_result;
	}
	
	private static JSONArray make_club_list(ResultSet rs_lesson_list) {
		
		JSONArray search_result = new JSONArray();
		
		try {
			if (rs_lesson_list.next() != false) {
				
				rs_lesson_list.beforeFirst();
				
				while(rs_lesson_list.next()) {
					JSONObject search_element = new JSONObject();
					
					String post_id = Integer.toString(rs_lesson_list.getInt("idx"));
					String user_id = rs_lesson_list.getString("user_id");
					String intro_string = rs_lesson_list.getString("intro_string");
					String club_detail = rs_lesson_list.getString("club_detail");
					String data_format = rs_lesson_list.getString("data_format");
//System.out.println(post_id + " / " + user_id + " / " + op_ntrp + " / " + op_howlong + " / " + op_gender + " / " + location + " / " + court_booked + " / " + p_comment);		
					search_element.put("post_id", post_id.toString());
					search_element.put("user_id", user_id);
					search_element.put("intro_string", intro_string);
					search_element.put("club_detail", club_detail);
					search_element.put("data_format", data_format);
					
					search_result.add(search_element);
				}
			
			}
			
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		
		return search_result;
	}
	
	public static String db_query_my_wanted(String action, String user_id/*, String location, String ntrp, String howlong, String gender, String court_booked*/) {
		
		String return_value = null;
		
		JSONArray my_list;
		JSONObject search_result = new JSONObject();
		search_result.put("action", action);
		search_result.put("user_id", user_id);
		
		try {
			GSTWebAPI_Global_Vars.check_db_connection();
			ResultSet rs_wanted_list;
			
			PreparedStatement ps_wanted_query = GSTWebAPI_Global_Vars.db_conn.prepareStatement("select * from wanted_ad where user_id=?");
			ps_wanted_query.setString(1, user_id);
			rs_wanted_list = ps_wanted_query.executeQuery();
			
			my_list = make_my_wanted_list(rs_wanted_list);
			
			if(my_list != null){
				search_result.put("data", my_list);
				search_result.put("search_result", "success");
			} else {
				search_result.put("search_result", "fail");
			}
			
		} catch(Exception e) {
			e.printStackTrace();
			search_result.put("search_result", "fail");
		}
		
		return_value = search_result.toString();
		return return_value;
	}
	
	public static String db_query_close_wanted(String action, String user_id, String post_id) {
		
		String return_value = null;
		
		JSONArray my_list;
		JSONObject search_result = new JSONObject();
		search_result.put("action", action);
		search_result.put("user_id", user_id);
		
		try {
			GSTWebAPI_Global_Vars.check_db_connection();
			ResultSet rs_wanted_list;
			
			PreparedStatement ps_wanted_query = GSTWebAPI_Global_Vars.db_conn.prepareStatement("select * from wanted_ad where user_id=? and idx = ? and active = ?");
			ps_wanted_query.setString(1, user_id);
			ps_wanted_query.setString(2, post_id);
			ps_wanted_query.setString(3, "y");
			rs_wanted_list = ps_wanted_query.executeQuery();
			
			if(rs_wanted_list != null) {
				ps_wanted_query = GSTWebAPI_Global_Vars.db_conn.prepareStatement("update wanted_ad set active = ? where user_id=? and idx = ?");
				ps_wanted_query.setString(1, "n");
				ps_wanted_query.setString(2, user_id);
				ps_wanted_query.setString(3, post_id);
				ps_wanted_query.executeUpdate();
	
				search_result.put("close_result", "success");
			} else {
				search_result.put("close_result", "fail");
			}
			
		} catch(Exception e) {
			e.printStackTrace();
			search_result.put("close_result", "fail");
		}
		
		return_value = search_result.toString();
		return return_value;
	}
	
	public static String db_query_close_handover(String action, String user_id, String post_id) {
		
		String return_value = null;
		
		JSONArray my_list;
		JSONObject search_result = new JSONObject();
		search_result.put("action", action);
		search_result.put("user_id", user_id);
		
		try {
			GSTWebAPI_Global_Vars.check_db_connection();
			ResultSet rs_wanted_list;
			
			PreparedStatement ps_wanted_query = GSTWebAPI_Global_Vars.db_conn.prepareStatement("select * from handover where user_id=? and idx = ? and active = ?");
			ps_wanted_query.setString(1, user_id);
			ps_wanted_query.setString(2, post_id);
			ps_wanted_query.setString(3, "y");
			rs_wanted_list = ps_wanted_query.executeQuery();
			
			if(rs_wanted_list != null) {
				ps_wanted_query = GSTWebAPI_Global_Vars.db_conn.prepareStatement("update handover set active = ? where user_id=? and idx = ?");
				ps_wanted_query.setString(1, "n");
				ps_wanted_query.setString(2, user_id);
				ps_wanted_query.setString(3, post_id);
				ps_wanted_query.executeUpdate();
	
				search_result.put("close_handover_result", "success");
			} else {
				search_result.put("close_handover_result", "fail");
			}
			
		} catch(Exception e) {
			e.printStackTrace();
			search_result.put("close_handover_result", "fail");
		}
		
		return_value = search_result.toString();
		return return_value;
	}
	
	public static String db_query_my_msg_list(String action, String user_id) {

		String return_value = null;
		
		JSONArray my_list;
		JSONObject search_result = new JSONObject();
		search_result.put("action", action);
		search_result.put("user_id", user_id);
		
		try {
			GSTWebAPI_Global_Vars.check_db_connection();
			ResultSet rs_message_list;
			
			PreparedStatement ps_wanted_query = GSTWebAPI_Global_Vars.db_conn.prepareStatement("select * from messages where (sender_id=? and sender_exit='N') or (receiver_id=? and receiver_exit='N')");
			ps_wanted_query.setString(1, user_id);
			ps_wanted_query.setString(2, user_id);
			rs_message_list = ps_wanted_query.executeQuery();
			
			my_list = make_message_list(user_id, rs_message_list);
		
			if(my_list.size() == 0) {
				search_result.put("message_result", "no_message");
			} else if(my_list != null){
				search_result.put("data", my_list);
				search_result.put("message_result", "success");
			} else {
				search_result.put("message_result", "fail");
			}
			
		} catch(Exception e) {
			e.printStackTrace();
			search_result.put("message_result", "fail");
		}
		
		return_value = search_result.toString();
		return return_value;
	}
	
	private static JSONArray make_message_list(String user_id, ResultSet rs_message_list) {
		
		JSONArray search_result = new JSONArray();
		
		try {
			if (rs_message_list.next() != false) {
				
				int receiver_list_length;
				rs_message_list.last();
				receiver_list_length = rs_message_list.getRow();
		
				rs_message_list.beforeFirst();
				
				//JSONArray msg_list_array = new JSONArray();
				String [] receiver_list = null;
				int num_receivers = 0;
//rs_message_list.beforeFirst(); System.out.println("============================================");
//while(rs_message_list.next()) {		
//	System.out.println(rs_message_list.getString("sender_id") + " / " + rs_message_list.getString("message"));
//}
//rs_message_list.beforeFirst(); System.out.println("============================================");
				while(rs_message_list.next()) {
					String receiver_id = rs_message_list.getString("receiver_id");
					String sender_id = rs_message_list.getString("sender_id");
//System.out.println("=============================================================");					
//System.out.println(rs_message_list.getString("sender_id") + " / " + rs_message_list.getString("receiver_id") + " / " + rs_message_list.getString("message"));	
					if(user_id.equals(sender_id)){
						if(receiver_list == null) {
							receiver_list = new String [receiver_list_length];
							receiver_list[num_receivers] = receiver_id;
							num_receivers++;
//System.out.print("sender side 1: ");							
//for(int n = 0; n < receiver_list.length; n++) {System.out.print(receiver_list[n] + "  ");} System.out.println("  /  " + receiver_id);		
						} else {					
							if(!check_existence(receiver_list, receiver_id)) {
								receiver_list[num_receivers] = receiver_id;
								num_receivers++;
							}
//System.out.print("sender side 2: ");							
//for(int n = 0; n < receiver_list.length; n++) {System.out.print(receiver_list[n] + "  ");} System.out.println("  /  " + receiver_id);		
						}
					} else{
						if(receiver_list == null) {
							receiver_list = new String [receiver_list_length];
							receiver_list[num_receivers] = sender_id;
							num_receivers++;
//System.out.print("receiver side 1: ");	
//for(int n = 0; n < receiver_list.length; n++) {System.out.print(receiver_list[n] + "  ");} System.out.println("  /  " + sender_id);	
						} else {		
							if(!check_existence(receiver_list, sender_id)) {
								receiver_list[num_receivers] = sender_id;
								num_receivers++;
							}
//System.out.print("receiver side 2: ");	
//for(int n = 0; n < receiver_list.length; n++) {System.out.print(receiver_list[n] + "  ");} System.out.println("  /  " + sender_id);	
						}
					}
				}
				
				for(int i = 0; i < num_receivers; i++) {
					rs_message_list.beforeFirst();
					
					JSONObject msg_list_obj = new JSONObject();
					msg_list_obj.put("receiver_id", receiver_list[i]);
					
					JSONArray msg_array = new JSONArray();
					
					while(rs_message_list.next()) {
						String sender = rs_message_list.getString("sender_id");
						String receiver = rs_message_list.getString("receiver_id");
												
						if(sender.equals(receiver_list[i]) || receiver.equals(receiver_list[i])) {
							JSONObject msg_obj = new JSONObject();
							
							if(sender.equals(receiver_list[i])) {
								msg_obj.put("msg_id", Integer.toString(rs_message_list.getInt("idx")));
								msg_obj.put("direction", "r");
								msg_obj.put("msg", rs_message_list.getString("message"));
							} else {
								msg_obj.put("msg_id", Integer.toString(rs_message_list.getInt("idx")));
								msg_obj.put("direction", "s");
								msg_obj.put("msg", rs_message_list.getString("message"));
							}
							
							msg_array.add(msg_obj);
						}
						
					}
					
					msg_list_obj.put("messages", msg_array);
					search_result.add(msg_list_obj);
				}			
			}
		
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		
		return search_result;
		
	}
	
	private static boolean check_existence(String [] arr, String str_to_check){ 
                
        for (String element : arr) { 
//System.out.println(element + "  /  " + str_to_check);
            if (element != null && element.equals(str_to_check)){ 
                return true; 
            } 
        } 
//System.out.println("---------------------------------------------");  
       return false; 
    } 
	
	private static JSONArray make_my_wanted_list(ResultSet rs_wanted_list) {
		
		JSONArray search_result = new JSONArray();
		
		SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date today_date = new Date(System.currentTimeMillis());
		Calendar cal = Calendar.getInstance();
		cal.setTime(today_date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		today_date = new Date(cal.getTimeInMillis());
		
		try {
			if (rs_wanted_list.next() != false) {
				
				rs_wanted_list.beforeFirst();
				
				while(rs_wanted_list.next()) {
					JSONObject search_element = new JSONObject();
					
					String post_id = Integer.toString(rs_wanted_list.getInt("idx"));
					String user_id = rs_wanted_list.getString("user_id");
					String op_ntrp = rs_wanted_list.getString("op_ntrp");
					String op_howlong = rs_wanted_list.getString("op_howlong");
					String op_gender = rs_wanted_list.getString("op_gender");
					String location = rs_wanted_list.getString("location");
					String court_booked = rs_wanted_list.getString("court_booked");
					String date_to_close_str = rs_wanted_list.getDate("date_to_close").toString();
					Date date_to_close = rs_wanted_list.getDate("date_to_close");
					String p_comment = rs_wanted_list.getString("p_comment");
					String active = rs_wanted_list.getString("active");
System.out.println(post_id + " / " + user_id + " / " + op_ntrp + " / " + op_howlong + " / " + op_gender + " / " + location + " / " + court_booked + " / " + p_comment);		
					search_element.put("post_id", post_id);
					search_element.put("user_id", user_id);
					search_element.put("op_ntrp", op_ntrp);
					search_element.put("op_howlong", op_howlong);
					search_element.put("op_gender", op_gender);
					search_element.put("location", location);
					search_element.put("court_booked", court_booked);
					search_element.put("date_to_close", date_to_close_str);
					search_element.put("comment", p_comment);
					if(active.equals("y") && date_to_close.after(today_date)) search_element.put("active", "y");
					else search_element.put("active", "n");
					
					search_result.add(search_element);
				}
			
			}
			
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		
		return search_result;
	}
	
	public static boolean db_query_check_id(String user_id, String user_pw) {
		
		boolean id_valid = false;
		
		try {
			GSTWebAPI_Global_Vars.check_db_connection();
			PreparedStatement ps_login_check = GSTWebAPI_Global_Vars.db_conn.prepareStatement("select p_id from players where p_id = ?");
			ps_login_check.setString(1, user_id);
			ResultSet rs_login_check = ps_login_check.executeQuery();
			
			if (rs_login_check.next() == false) { 
				id_valid = true;
			} else {
				id_valid = false;
			}
			
		} catch(Exception e) {
			e.printStackTrace();
			id_valid = false;
		}
		
		return id_valid;
	}
	
	public static boolean db_query_login(String user_id, String user_pw, String device_token) {
		
		boolean player_exist = false;
		
		try {
			GSTWebAPI_Global_Vars.check_db_connection();
			PreparedStatement ps_login_check = GSTWebAPI_Global_Vars.db_conn.prepareStatement("select * from players where p_id = ?");
			ps_login_check.setString(1, user_id);
			ResultSet rs_login_check = ps_login_check.executeQuery();
			
			if (rs_login_check.next() == false) { 
				player_exist = false;
			} else {
				String password = rs_login_check.getString("p_pw");
				
				if(password.equals(user_pw)) {
					player_exist = true;
					
					if(!device_token.equals("")) {
						ps_login_check = GSTWebAPI_Global_Vars.db_conn.prepareStatement("update players set device_token = ? where p_id = ?");
						ps_login_check.setString(1, device_token);
						ps_login_check.setString(2, user_id);
						ps_login_check.executeUpdate();
					}
					
				} else {
					player_exist = false;
				}
			}
			
		} catch(Exception e) {
			e.printStackTrace();
			player_exist = false;
		}
		
		return player_exist;		
	}
	
	public static boolean db_query_register(String user_name, 
			  String phone_number, 
			  String email, 
			  String user_id, 
			  String password, 
			  String location, 
			  Float howlogn, 
			  Float ntrp, 
			  String gender) {

		boolean register_success = false;
		
		try {
			GSTWebAPI_Global_Vars.check_db_connection();
			PreparedStatement ps_login_check = GSTWebAPI_Global_Vars.db_conn.prepareStatement("insert into players (p_name, p_phone, p_email, p_id, p_pw, p_location, p_howlong, p_ntrp, p_gender) "
																							 + "values (?, ?, ?, ?, ?, ?, ?, ?, ?)");
			ps_login_check.setString(1, user_name);
			ps_login_check.setString(2, phone_number);
			ps_login_check.setString(3, email);
			ps_login_check.setString(4, user_id);
			ps_login_check.setString(5, password);
			ps_login_check.setString(6, location);
			ps_login_check.setFloat(7, howlogn);
			ps_login_check.setFloat(8, ntrp);
			ps_login_check.setString(9, gender);
			ps_login_check.executeUpdate();
			
			register_success = true;
		
		} catch(Exception e) {
			e.printStackTrace();
			register_success = false;
		}
		
		return register_success;
	}
	
	public static String [] db_query_send_message(String action, String user_id, String receiver_id, String msg, Timestamp msg_ts) {
		
		//boolean send_msg_success = false;
		String [] user_token_and_idx = new String[2];
		
		try {
			GSTWebAPI_Global_Vars.check_db_connection();
			PreparedStatement ps_login_check = GSTWebAPI_Global_Vars.db_conn.prepareStatement("insert into messages (sender_id, receiver_id, message, msg_time) values (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
			ps_login_check.setString(1, user_id);
			ps_login_check.setString(2, receiver_id);
			ps_login_check.setString(3, msg);
			ps_login_check.setTimestamp(4, msg_ts);
			ps_login_check.executeUpdate();
			
			ResultSet rs_generated_key = ps_login_check.getGeneratedKeys();
			
			ps_login_check = GSTWebAPI_Global_Vars.db_conn.prepareStatement("select * from players where p_id = ?");
			ps_login_check.setString(1, receiver_id);
			ResultSet rs_user_token = ps_login_check.executeQuery();
			
			if(rs_generated_key.next()) user_token_and_idx[0] = Integer.toString(rs_generated_key.getInt(1));
			if(rs_user_token.next()) user_token_and_idx[1] = rs_user_token.getString("device_token");
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return user_token_and_idx;
	}
	
	public static int db_query_check_version(String user_version) {
		
		int version_ok = GSTWebAPI_Global_Vars.APP_VERSION_UPDATE_REQUIRED;
		
		try {
			GSTWebAPI_Global_Vars.check_db_connection();
			PreparedStatement ps_version_check = GSTWebAPI_Global_Vars.db_conn.prepareStatement("select * from version");
			ResultSet rs_version = ps_version_check.executeQuery();
			
			if(rs_version.next()) {			
				String current_version = (String) rs_version.getString("current_version");
System.out.println(current_version + " / " + user_version);	
				if(current_version.compareTo(user_version) == 0) version_ok = GSTWebAPI_Global_Vars.APP_VERSION_CURRENT;
				else if(current_version.compareTo(user_version) < 0) version_ok = GSTWebAPI_Global_Vars.APP_VERSION_CURRENT;
				
			} else {
				version_ok = GSTWebAPI_Global_Vars.APP_VERSION_CHEKC_ERROR;
			}
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return version_ok;
		
	}
	
	
	
	
	
	
	
	
	
	
}



































