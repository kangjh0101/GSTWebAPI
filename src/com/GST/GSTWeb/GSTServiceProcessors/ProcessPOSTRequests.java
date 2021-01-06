package com.GST.GSTWeb.GSTServiceProcessors;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.StringTokenizer;
import java.sql.Date;

import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.GST.GSTWeb.GSTWebAPI_Global_Vars;

public class ProcessPOSTRequests implements Runnable{

	Thread service_proc;
	private String post_request_str;
	private AsyncContext asyncContext;
	private HttpServletRequest request;
	private HttpServletResponse response;
	
	public ProcessPOSTRequests(AsyncContext ac){
		
		//request_str = string_to_parse;
		asyncContext = ac;
		request = (HttpServletRequest) asyncContext.getRequest();
		response = (HttpServletResponse) asyncContext.getResponse();
		service_proc = new Thread(this);
		service_proc.start();
	}

	@SuppressWarnings("unchecked")
	public void run(){
		
		//DB_Purge.close_expired_wanted_ad();
		
		try {			
			StringBuilder stringBuilder = new StringBuilder();
			InputStream inputStream = request.getInputStream();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
			char[] charBuffer = new char[128];
            int bytesRead = -1;
            while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                stringBuilder.append(charBuffer, 0, bytesRead);
            }

            post_request_str = stringBuilder.toString();		
System.out.println("post request line: " + post_request_str);			
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(post_request_str);
			JSONObject jsonObject =  (JSONObject) obj;
			
			String action = (String) jsonObject.get("action");
			
			if(action.equals("login")) {
				
				process_login(jsonObject);
				
			} else if(action.equals("register")) {
				
				process_register(jsonObject);
				
			} else if(action.equals("check_id")) {
				
				process_check_id(jsonObject);
				
			} else if(action.equals("wanted_ad_list")) {
				
				process_wanted_list(jsonObject);
				
			} else if(action.equals("post_wanted_ad")) {
				
				process_post_wanted(jsonObject);
				
			} else if(action.equals("my_wanted_ad")) {
				
				process_my_wanted(jsonObject);
				
			} else if(action.equals("close_my_wanted_ad")) {
				
				process_close_wanted(jsonObject);
				
			} else if(action.equals("send_msg")) {
				
				process_send_msg(jsonObject);
				
			} else if(action.equals("get_my_msg")) {
				
				process_get_my_message(jsonObject);
				
			} else if(action.equals("exit_msg")) {
				
				process_exit_message(jsonObject);
				
			} else if(action.equals("block_user")) {
				
				process_block_user(jsonObject);
				
			} else if(action.equals("check_block_user")) {
				
				process_check_block_user(jsonObject);
				
			} else if(action.equals("bug_report")) {
			
				process_bug_report(jsonObject);
				
			} else if(action.equals("unblock_user")) {
				
				process_unblock_user(jsonObject);
				
			} else if(action.equals("find_id")) {
				
				process_find_id(jsonObject);
				
			} else if(action.equals("find_pw")) {
				
				process_find_pw(jsonObject);
				
			} else if(action.equals("withdraw_user")) {
				
				process_withdraw_user(jsonObject);
				
			} else if(action.equals("lesson_ad_list")) {
				
				process_lesson_ad(jsonObject);
				
			} else if(action.equals("lesson_detail")) {
				
				process_lesson_detail(jsonObject);
				
			} else if(action.equals("club_detail_upload")) {
				
				process_upload_club_ad(jsonObject);
				
			} else if(action.equals("club_ad_list")) {
				
				process_club_ad(jsonObject);
				
			} else if(action.equals("club_detail")) {
				
				process_club_detail(jsonObject);
				
			} else if(action.equals("handover_ad_list")) {
				
				process_handover_ad(jsonObject);
				
			} else if(action.equals("post_handover")) {
				
				process_post_handover(jsonObject);
				
			} else if(action.equals("close_handover")) {
				
				process_close_handover(jsonObject);
				
			}


		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private void process_upload_club_ad(JSONObject my_info) {
		
		try {
			String upload_clu_ad_result;
			
			String action = (String) my_info.get("action");
			String user_id = (String) my_info.get("user_id");
			String data_format = (String) my_info.get("data_format");
			String intro = (String) my_info.get("intro");
			String comment = (String) my_info.get("comment");
			String data = (String) my_info.get("data");
System.out.println(data_format + " / " + intro + " / " + comment + " / " + data);			
			upload_clu_ad_result = dbQuery.db_query_upload_club_ad(action, user_id, data_format, intro, comment, data);
System.out.println(upload_clu_ad_result);			
			// set response code == 200
			response.setStatus(HttpServletResponse.SC_OK);
			response.setContentType("application/json; charset=utf-8");
			
			PrintWriter out = asyncContext.getResponse().getWriter();
			out.write(upload_clu_ad_result);
			out.close();
			
			asyncContext.complete();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void process_lesson_detail(JSONObject my_info) {
		
		try {
			String result_lesson_list;
			
			String action = (String) my_info.get("action");
			String user_id = (String) my_info.get("user_id");
			String post_id = (String) my_info.get("post_id");
			
			result_lesson_list = dbQuery.db_query_lesson_detail(action, user_id, post_id);
//System.out.println(result_lesson_list);			
System.out.println("result not printed to save disk space");
			// set response code == 200
			response.setStatus(HttpServletResponse.SC_OK);
			response.setContentType("application/json; charset=utf-8");
			
			PrintWriter out = asyncContext.getResponse().getWriter();
			out.write(result_lesson_list);
			out.close();
			
			asyncContext.complete();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void process_club_detail(JSONObject my_info) {
		
		try {
			String result_lesson_list;
			
			String action = (String) my_info.get("action");
			String user_id = (String) my_info.get("user_id");
			String post_id = (String) my_info.get("post_id");
			
			result_lesson_list = dbQuery.db_query_club_detail(action, user_id, post_id);
//System.out.println(result_lesson_list);			
System.out.println("result not printed to save disk space");
			// set response code == 200
			response.setStatus(HttpServletResponse.SC_OK);
			response.setContentType("application/json; charset=utf-8");
			
			PrintWriter out = asyncContext.getResponse().getWriter();
			out.write(result_lesson_list);
			out.close();
			
			asyncContext.complete();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void process_lesson_ad(JSONObject my_info) {
		
		try{
			String result_lesson_list;
			
			String action = (String) my_info.get("action");
			String user_id = (String) my_info.get("user_id");
			String coarse_location = (String) my_info.get("coarse_location");
			
			result_lesson_list = dbQuery.db_query_lesson_ad(action, user_id, coarse_location);
System.out.println(result_lesson_list);			
			// set response code == 200
			response.setStatus(HttpServletResponse.SC_OK);
			response.setContentType("application/json; charset=utf-8");
			
			PrintWriter out = asyncContext.getResponse().getWriter();
			out.write(result_lesson_list);
			out.close();
			
			asyncContext.complete();
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private void process_club_ad(JSONObject my_info) {
		
		try{
			String result_lesson_list;
			
			String action = (String) my_info.get("action");
			String user_id = (String) my_info.get("user_id");
			
			result_lesson_list = dbQuery.db_query_club_ad(action, user_id);
System.out.println(result_lesson_list);			
			// set response code == 200
			response.setStatus(HttpServletResponse.SC_OK);
			response.setContentType("application/json; charset=utf-8");
			
			PrintWriter out = asyncContext.getResponse().getWriter();
			out.write(result_lesson_list);
			out.close();
			
			asyncContext.complete();
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private void process_handover_ad(JSONObject my_info) {
		
		try{
			String result_lesson_list;
			
			String action = (String) my_info.get("action");
			String user_id = (String) my_info.get("user_id");
			
			result_lesson_list = dbQuery.db_query_handover_ad(action, user_id);
System.out.println(result_lesson_list);			
			// set response code == 200
			response.setStatus(HttpServletResponse.SC_OK);
			response.setContentType("application/json; charset=utf-8");
			
			PrintWriter out = asyncContext.getResponse().getWriter();
			out.write(result_lesson_list);
			out.close();
			
			asyncContext.complete();
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private void process_post_handover(JSONObject my_info) {
		
		try {
			String result_lesson_list;
			
			String action = (String) my_info.get("action");
			String user_id = (String) my_info.get("user_id");
			String intro_string = (String) my_info.get("intro");
			String handover_detail = (String) my_info.get("comment");
			
			result_lesson_list = dbQuery.db_query_post_handover(action, user_id, intro_string, handover_detail);
System.out.println(result_lesson_list);			
			// set response code == 200
			response.setStatus(HttpServletResponse.SC_OK);
			response.setContentType("application/json; charset=utf-8");
			
			PrintWriter out = asyncContext.getResponse().getWriter();
			out.write(result_lesson_list);
			out.close();
			
			asyncContext.complete();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void process_withdraw_user(JSONObject message_info) {
		
		try {
			boolean withdraw_user_result = false;
			
			String action = (String) message_info.get("action");
			String user_id = (String) message_info.get("user_id");
			
			withdraw_user_result = dbQuery.db_query_withdraw_user(user_id);
			
			if(withdraw_user_result){
				try {
					// make json with login_result == success
					JSONObject withdraw_user_response = new JSONObject();
					withdraw_user_response.put("action", action);
					withdraw_user_response.put("user_id", user_id);
					withdraw_user_response.put("withdraw_user_result", "success");
System.out.println(withdraw_user_response.toJSONString());			
					// set response code == 200
					response.setStatus(HttpServletResponse.SC_OK);
					response.setContentType("application/json; charset=utf-8");
					
					PrintWriter out = asyncContext.getResponse().getWriter();
					out.write(withdraw_user_response.toString());
					out.close();
					
					asyncContext.complete();
					
				} catch(Exception e){
					e.printStackTrace();
				}
				
			} else {
				try {
					// make json with login_result == fail
					JSONObject withdraw_user_response = new JSONObject();
					withdraw_user_response.put("action", action);
					withdraw_user_response.put("user_id", user_id);
					withdraw_user_response.put("withdraw_user_result", "fail");
System.out.println(withdraw_user_response.toJSONString());	
					// set response code == 200
					response.setStatus(HttpServletResponse.SC_OK);
					response.setContentType("application/json; charset=utf-8");
					
					PrintWriter out = asyncContext.getResponse().getWriter();
					out.write(withdraw_user_response.toString());
					out.close();
					
					asyncContext.complete();
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void process_find_pw(JSONObject message_info) {
		
		try {
			String pw_found;
			
			String action = (String) message_info.get("action");
			String user_id = (String) message_info.get("user_id");
			String phone_number = (String) message_info.get("phone_number");
			String email = (String) message_info.get("email");
			
			pw_found = dbQuery.db_query_find_pw(user_id, phone_number, email);
			
			if(pw_found != null) {
				try {
					String email_subject = "그랜드슬램 테니스 PW 찾기"; 
					String msg_to_send = "사용자 PW는 " + pw_found + " 입니다.";
					
					boolean send_success = EmailSender.send_email(email, email_subject, msg_to_send);
					
					if(send_success) {
						// make json with login_result == success
						JSONObject block_user_response = new JSONObject();
						block_user_response.put("action", action);
						block_user_response.put("user_id", user_id);
						block_user_response.put("find_pw_result", "success");
System.out.println(block_user_response.toJSONString());			
						// set response code == 200
						response.setStatus(HttpServletResponse.SC_OK);
						response.setContentType("application/json; charset=utf-8");
						
						PrintWriter out = asyncContext.getResponse().getWriter();
						out.write(block_user_response.toString());
						out.close();
						
						asyncContext.complete();
						
					} else {
						// make json with login_result == success
						JSONObject block_user_response = new JSONObject();
						block_user_response.put("action", action);
						block_user_response.put("user_id", user_id);
						block_user_response.put("find_pw_result", "fail");
						block_user_response.put("message", GSTWebAPI_Global_Vars.FAILED_TO_SEND_MAIL);
System.out.println(block_user_response.toJSONString());			
						// set response code == 200
						response.setStatus(HttpServletResponse.SC_OK);
						response.setContentType("application/json; charset=utf-8");
						
						PrintWriter out = asyncContext.getResponse().getWriter();
						out.write(block_user_response.toString());
						out.close();
						
						asyncContext.complete();
					}
					
				} catch(Exception e){
					e.printStackTrace();
				}
			} else {
				// make json with login_result == success
				JSONObject block_user_response = new JSONObject();
				block_user_response.put("action", action);
				block_user_response.put("user_id", user_id);
				block_user_response.put("find_pw_result", "fail");
				block_user_response.put("message", GSTWebAPI_Global_Vars.NO_ID_FOUND);
System.out.println(block_user_response.toJSONString());			
				// set response code == 200
				response.setStatus(HttpServletResponse.SC_OK);
				response.setContentType("application/json; charset=utf-8");
				
				PrintWriter out = asyncContext.getResponse().getWriter();
				out.write(block_user_response.toString());
				out.close();
				
				asyncContext.complete();
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private void process_find_id(JSONObject message_info) {
		
		try {
			String id_found;
			
			String action = (String) message_info.get("action");
			String user_name = (String) message_info.get("user_name");
			String phone_number = (String) message_info.get("phone_number");
			String email = (String) message_info.get("email");
			
			id_found = dbQuery.db_query_find_id(user_name, phone_number, email);
			
			if(id_found != null) {
				try {
					String email_subject = "그랜드슬램 테니스 ID 찾기"; 
					String msg_to_send = "사용자 ID는 " + id_found + " 입니다.";
					
					boolean send_success = EmailSender.send_email(email, email_subject, msg_to_send);
					
					if(send_success) {
						// make json with login_result == success
						JSONObject block_user_response = new JSONObject();
						block_user_response.put("action", action);
						block_user_response.put("user_name", user_name);
						block_user_response.put("find_id_result", "success");
System.out.println(block_user_response.toJSONString());			
						// set response code == 200
						response.setStatus(HttpServletResponse.SC_OK);
						response.setContentType("application/json; charset=utf-8");
						
						PrintWriter out = asyncContext.getResponse().getWriter();
						out.write(block_user_response.toString());
						out.close();
						
						asyncContext.complete();
						
					} else {
						// make json with login_result == success
						JSONObject block_user_response = new JSONObject();
						block_user_response.put("action", action);
						block_user_response.put("user_name", user_name);
						block_user_response.put("find_id_result", "fail");
						block_user_response.put("message", GSTWebAPI_Global_Vars.FAILED_TO_SEND_MAIL);
System.out.println(block_user_response.toJSONString());			
						// set response code == 200
						response.setStatus(HttpServletResponse.SC_OK);
						response.setContentType("application/json; charset=utf-8");
						
						PrintWriter out = asyncContext.getResponse().getWriter();
						out.write(block_user_response.toString());
						out.close();
						
						asyncContext.complete();
					}
					
				} catch(Exception e){
					e.printStackTrace();
				}
			} else {
				// make json with login_result == success
				JSONObject block_user_response = new JSONObject();
				block_user_response.put("action", action);
				block_user_response.put("user_name", user_name);
				block_user_response.put("find_id_result", "fail");
				block_user_response.put("message", GSTWebAPI_Global_Vars.NO_NAME_FOUND);
System.out.println(block_user_response.toJSONString());			
				// set response code == 200
				response.setStatus(HttpServletResponse.SC_OK);
				response.setContentType("application/json; charset=utf-8");
				
				PrintWriter out = asyncContext.getResponse().getWriter();
				out.write(block_user_response.toString());
				out.close();
				
				asyncContext.complete();
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private void process_bug_report(JSONObject message_info) {
		
		try {
			boolean report_result = false;
			
			String action = (String) message_info.get("action");
			String user_id = (String) message_info.get("user_id");
			String content = (String) message_info.get("content");
	
			report_result = dbQuery.db_query_bug_report(user_id, content);
			
			if(report_result){
				try {
					// make json with login_result == success
					JSONObject block_user_response = new JSONObject();
					block_user_response.put("action", action);
					block_user_response.put("user_id", user_id);
					block_user_response.put("bug_report_result", "success");
System.out.println(block_user_response.toJSONString());			
					// set response code == 200
					response.setStatus(HttpServletResponse.SC_OK);
					response.setContentType("application/json; charset=utf-8");
					
					PrintWriter out = asyncContext.getResponse().getWriter();
					out.write(block_user_response.toString());
					out.close();
					
					asyncContext.complete();
					
				} catch(Exception e){
					e.printStackTrace();
				}
			} else {
				try {
					// make json with login_result == success
					JSONObject block_user_response = new JSONObject();
					block_user_response.put("action", action);
					block_user_response.put("user_id", user_id);
					block_user_response.put("bug_report_result", "fail");
System.out.println(block_user_response.toJSONString());			
					// set response code == 200
					response.setStatus(HttpServletResponse.SC_OK);
					response.setContentType("application/json; charset=utf-8");
					
					PrintWriter out = asyncContext.getResponse().getWriter();
					out.write(block_user_response.toString());
					out.close();
					
					asyncContext.complete();
					
				} catch(Exception e){
					e.printStackTrace();
				}
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void process_unblock_user(JSONObject message_info) {
		
		try {
			boolean block_user_result = false;
			
			String action = (String) message_info.get("action");
			String user_id = (String) message_info.get("user_id");
			String receiver_id = (String) message_info.get("receiver_id");
			
			block_user_result = dbQuery.db_query_unblock_user(user_id, receiver_id);
			
			if(block_user_result){
				try {
					// make json with login_result == success
					JSONObject block_user_response = new JSONObject();
					block_user_response.put("action", action);
					block_user_response.put("user_id", user_id);
					block_user_response.put("receiver_id", user_id);
					block_user_response.put("unblock_user_result", "success");
System.out.println(block_user_response.toJSONString());			
					// set response code == 200
					response.setStatus(HttpServletResponse.SC_OK);
					response.setContentType("application/json; charset=utf-8");
					
					PrintWriter out = asyncContext.getResponse().getWriter();
					out.write(block_user_response.toString());
					out.close();
					
					asyncContext.complete();
					
				} catch(Exception e){
					e.printStackTrace();
				}
				
			} else {
				try {
					// make json with login_result == fail
					JSONObject block_user_response = new JSONObject();
					block_user_response.put("action", action);
					block_user_response.put("user_id", user_id);
					block_user_response.put("receiver_id", user_id);
					block_user_response.put("unblock_user_result", "fail");
System.out.println(block_user_response.toJSONString());	
					// set response code == 200
					response.setStatus(HttpServletResponse.SC_OK);
					response.setContentType("application/json; charset=utf-8");
					
					PrintWriter out = asyncContext.getResponse().getWriter();
					out.write(block_user_response.toString());
					out.close();
					
					asyncContext.complete();
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void process_check_block_user(JSONObject message_info) {
		
		try {
			int block_user_result = 0;
			
			String action = (String) message_info.get("action");
			String user_id = (String) message_info.get("user_id");
			String receiver_id = (String) message_info.get("receiver_id");
			
			block_user_result = dbQuery.db_query_check_block_user(user_id, receiver_id);
			
			if(block_user_result == 1){
				try {
					// make json with login_result == success
					JSONObject block_user_response = new JSONObject();
					block_user_response.put("action", action);
					block_user_response.put("user_id", user_id);
					block_user_response.put("receiver_id", user_id);
					block_user_response.put("check_block_user_result", "success");
					block_user_response.put("block", "yes");
System.out.println(block_user_response.toJSONString());			
					// set response code == 200
					response.setStatus(HttpServletResponse.SC_OK);
					response.setContentType("application/json; charset=utf-8");
					
					PrintWriter out = asyncContext.getResponse().getWriter();
					out.write(block_user_response.toString());
					out.close();
					
					asyncContext.complete();
					
				} catch(Exception e){
					e.printStackTrace();
				}
				
			} else if(block_user_result == 0){
				try {
					// make json with login_result == fail
					JSONObject block_user_response = new JSONObject();
					block_user_response.put("action", action);
					block_user_response.put("user_id", user_id);
					block_user_response.put("receiver_id", user_id);
					block_user_response.put("check_block_user_result", "success");
					block_user_response.put("block", "no");
System.out.println(block_user_response.toJSONString());	
					// set response code == 200
					response.setStatus(HttpServletResponse.SC_OK);
					response.setContentType("application/json; charset=utf-8");
					
					PrintWriter out = asyncContext.getResponse().getWriter();
					out.write(block_user_response.toString());
					out.close();
					
					asyncContext.complete();
				} catch(Exception e) {
					e.printStackTrace();
				}
			} else {
				try {
					// make json with login_result == fail
					JSONObject block_user_response = new JSONObject();
					block_user_response.put("action", action);
					block_user_response.put("user_id", user_id);
					block_user_response.put("receiver_id", user_id);
					block_user_response.put("check_block_user_result", "fail");
System.out.println(block_user_response.toJSONString());	
					// set response code == 200
					response.setStatus(HttpServletResponse.SC_OK);
					response.setContentType("application/json; charset=utf-8");
					
					PrintWriter out = asyncContext.getResponse().getWriter();
					out.write(block_user_response.toString());
					out.close();
					
					asyncContext.complete();
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void process_block_user(JSONObject message_info) {
		
		try {
			boolean block_user_result = false;
			
			String action = (String) message_info.get("action");
			String user_id = (String) message_info.get("user_id");
			String receiver_id = (String) message_info.get("receiver_id");
			
			block_user_result = dbQuery.db_query_block_user(user_id, receiver_id);
			
			if(block_user_result){
				try {
					// make json with login_result == success
					JSONObject block_user_response = new JSONObject();
					block_user_response.put("action", action);
					block_user_response.put("user_id", user_id);
					block_user_response.put("receiver_id", user_id);
					block_user_response.put("block_user_result", "success");
System.out.println(block_user_response.toJSONString());			
					// set response code == 200
					response.setStatus(HttpServletResponse.SC_OK);
					response.setContentType("application/json; charset=utf-8");
					
					PrintWriter out = asyncContext.getResponse().getWriter();
					out.write(block_user_response.toString());
					out.close();
					
					asyncContext.complete();
					
				} catch(Exception e){
					e.printStackTrace();
				}
				
			} else {
				try {
					// make json with login_result == fail
					JSONObject block_user_response = new JSONObject();
					block_user_response.put("action", action);
					block_user_response.put("user_id", user_id);
					block_user_response.put("receiver_id", user_id);
					block_user_response.put("block_user_result", "fail");
System.out.println(block_user_response.toJSONString());	
					// set response code == 200
					response.setStatus(HttpServletResponse.SC_OK);
					response.setContentType("application/json; charset=utf-8");
					
					PrintWriter out = asyncContext.getResponse().getWriter();
					out.write(block_user_response.toString());
					out.close();
					
					asyncContext.complete();
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void process_exit_message(JSONObject message_info) {
		
		try {
			boolean exit_message_result = false;
			
			String action = (String) message_info.get("action");
			String user_id = (String) message_info.get("user_id");
			String receiver_id = (String) message_info.get("receiver_id");
			
			exit_message_result = dbQuery.db_query_exit_message(user_id, receiver_id);
			
			if(exit_message_result){
				try {
					// make json with login_result == success
					JSONObject exit_msg_response = new JSONObject();
					exit_msg_response.put("action", action);
					exit_msg_response.put("user_id", user_id);
					exit_msg_response.put("receiver_id", user_id);
					exit_msg_response.put("exit_result", "success");
System.out.println(exit_msg_response.toJSONString());			
					// set response code == 200
					response.setStatus(HttpServletResponse.SC_OK);
					response.setContentType("application/json; charset=utf-8");
					
					PrintWriter out = asyncContext.getResponse().getWriter();
					out.write(exit_msg_response.toString());
					out.close();
					
					asyncContext.complete();
					
				} catch(Exception e){
					e.printStackTrace();
				}
				
			} else {
				try {
					// make json with login_result == fail
					JSONObject exit_msg_response = new JSONObject();
					exit_msg_response.put("action", action);
					exit_msg_response.put("user_id", user_id);
					exit_msg_response.put("receiver_id", user_id);
					exit_msg_response.put("exit_result", "fail");
System.out.println(exit_msg_response.toJSONString());	
					// set response code == 200
					response.setStatus(HttpServletResponse.SC_OK);
					response.setContentType("application/json; charset=utf-8");
					
					PrintWriter out = asyncContext.getResponse().getWriter();
					out.write(exit_msg_response.toString());
					out.close();
					
					asyncContext.complete();
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void process_send_msg(JSONObject message_info) {
		try {
			//boolean send_msg_result = false;
			String [] user_token_and_idx;
			
			String action = (String) message_info.get("action");
			String user_id = (String) message_info.get("user_id");
			JSONObject msg_data = (JSONObject) message_info.get("data");
			String receiver_id = (String) msg_data.get("receiver_id");
			String msg = (String) msg_data.get("msg");
			
			boolean is_blocked = dbQuery.db_query_check_block(user_id, receiver_id);
			
			if(is_blocked == false) {
				Timestamp msg_timestamp = new Timestamp(System.currentTimeMillis());
System.out.println("message sent at: " + msg_timestamp.toString());				
				user_token_and_idx = dbQuery.db_query_send_message(action, user_id, receiver_id, msg, msg_timestamp);
				
				if(user_token_and_idx[1] != null){
					try {					
						//String content = user_id + " 님의 메세지: " + msg;
System.out.println("user token = " + user_token_and_idx[1]);
						FCMUtil fcmutil = new FCMUtil();
						Boolean msg_result = fcmutil.send_FCM(user_token_and_idx[1], user_id, msg, user_token_and_idx[0], msg_timestamp);
						
						// make json with login_result == success
						JSONObject send_msg_response = new JSONObject();
						send_msg_response.put("action", action);
						send_msg_response.put("user_id", user_id);
						send_msg_response.put("msg_id", user_token_and_idx[0]);
						//send_msg_response.put("msg_time", msg_timestamp.toString());
						if(msg_result == true) send_msg_response.put("send_result", "success");
						else send_msg_response.put("send_result", "fail");
System.out.println(send_msg_response.toJSONString());			
						// set response code == 200
						response.setStatus(HttpServletResponse.SC_OK);
						response.setContentType("application/json; charset=utf-8");
						
						PrintWriter out = asyncContext.getResponse().getWriter();
						out.write(send_msg_response.toString());
						out.close();
						
						asyncContext.complete();
						
					} catch(Exception e){
						e.printStackTrace();
					}
					
				} else {
					try {
						// make json with login_result == fail
						JSONObject send_msg_response = new JSONObject();
						send_msg_response.put("action", action);
						send_msg_response.put("user_id", user_id);
						send_msg_response.put("msg_id", "0");
						send_msg_response.put("send_result", "fail");
System.out.println(send_msg_response.toJSONString());	
						// set response code == 200
						response.setStatus(HttpServletResponse.SC_OK);
						response.setContentType("application/json; charset=utf-8");
						
						PrintWriter out = asyncContext.getResponse().getWriter();
						out.write(send_msg_response.toString());
						out.close();
						
						asyncContext.complete();
					} catch(Exception e) {
						e.printStackTrace();
					}
				}
			} else {
				try {
					// make json with login_result == fail
					JSONObject send_msg_response = new JSONObject();
					send_msg_response.put("action", action);
					send_msg_response.put("user_id", user_id);
					send_msg_response.put("msg_id", "0");
					send_msg_response.put("send_result", "blocked");
System.out.println(send_msg_response.toJSONString());	
					// set response code == 200
					response.setStatus(HttpServletResponse.SC_OK);
					response.setContentType("application/json; charset=utf-8");
					
					PrintWriter out = asyncContext.getResponse().getWriter();
					out.write(send_msg_response.toString());
					out.close();
					
					asyncContext.complete();
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void process_post_wanted(JSONObject search_info){
		try {
			boolean post_wanted_result = false;
			
			String action = (String) search_info.get("action");
			String user_id = (String) search_info.get("user_id");
			String op_ntrp = (String) search_info.get("op_ntrp");
			String op_gender = (String) search_info.get("op_gender");
			String op_howlong = (String) search_info.get("op_howlong");
			String court_booked = (String) search_info.get("court_booked");
			String location = (String) search_info.get("location");
			String date_to_close = (String) search_info.get("date_to_close");
			String comment = (String) search_info.get("comment");
		
			post_wanted_result = dbQuery.db_query_post_wanted(user_id, op_ntrp, op_gender, op_howlong, court_booked, location, date_to_close, comment);
			
			if(post_wanted_result){
				try {
					// make json with login_result == success
					JSONObject register_response = new JSONObject();
					register_response.put("action", action);
					register_response.put("user_id", user_id);
					register_response.put("post_result", "success");
System.out.println(register_response.toJSONString());			
					// set response code == 200
					response.setStatus(HttpServletResponse.SC_OK);
					response.setContentType("application/json; charset=utf-8");
					
					PrintWriter out = asyncContext.getResponse().getWriter();
					out.write(register_response.toString());
					out.close();
					
					asyncContext.complete();
					
				} catch(Exception e){
					e.printStackTrace();
				}
				
			} else {
				try {
					// make json with login_result == fail
					JSONObject register_response = new JSONObject();
					register_response.put("action", action);
					register_response.put("user_id", user_id);
					register_response.put("post_result", "fail");
System.out.println(register_response.toJSONString());	
					// set response code == 200
					response.setStatus(HttpServletResponse.SC_OK);
					response.setContentType("application/json; charset=utf-8");
					
					PrintWriter out = asyncContext.getResponse().getWriter();
					out.write(register_response.toString());
					out.close();
					
					asyncContext.complete();
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void process_wanted_list(JSONObject search_info){
		try {
			String result_wanted_list;
			
			String action = (String) search_info.get("action");
			String user_id = (String) search_info.get("user_id");
		
			result_wanted_list = dbQuery.db_query_wanted_list(action, user_id/*, location, ntrp, howlong, gender, court_booked*/);
System.out.println(result_wanted_list);			
			// set response code == 200
			response.setStatus(HttpServletResponse.SC_OK);
			response.setContentType("application/json; charset=utf-8");
			
			PrintWriter out = asyncContext.getResponse().getWriter();
			out.write(result_wanted_list);
			out.close();
			
			asyncContext.complete();

		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void process_close_wanted(JSONObject search_info){
		try {
			String result_wanted_list;
			
			String action = (String) search_info.get("action");
			String user_id = (String) search_info.get("user_id");
			String post_id = (String) search_info.get("post_id");

		
			result_wanted_list = dbQuery.db_query_close_wanted(action, user_id, post_id);
System.out.println(result_wanted_list);			
			// set response code == 200
			response.setStatus(HttpServletResponse.SC_OK);
			response.setContentType("application/json; charset=utf-8");
			
			PrintWriter out = asyncContext.getResponse().getWriter();
			out.write(result_wanted_list);
			out.close();
			
			asyncContext.complete();

		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void process_close_handover(JSONObject search_info){
		try {
			String result_wanted_list;
			
			String action = (String) search_info.get("action");
			String user_id = (String) search_info.get("user_id");
			String post_id = (String) search_info.get("post_id");

		
			result_wanted_list = dbQuery.db_query_close_handover(action, user_id, post_id);
System.out.println(result_wanted_list);			
			// set response code == 200
			response.setStatus(HttpServletResponse.SC_OK);
			response.setContentType("application/json; charset=utf-8");
			
			PrintWriter out = asyncContext.getResponse().getWriter();
			out.write(result_wanted_list);
			out.close();
			
			asyncContext.complete();

		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void process_get_my_message(JSONObject search_info){
		try {
			String result_my_msg_list;
			
			String action = (String) search_info.get("action");
			String user_id = (String) search_info.get("user_id");
			
			result_my_msg_list = dbQuery.db_query_my_msg_list(action, user_id/*, location, ntrp, howlong, gender, court_booked*/);
System.out.println(result_my_msg_list);			
			// set response code == 200
			response.setStatus(HttpServletResponse.SC_OK);
			response.setContentType("application/json; charset=utf-8");
			
			PrintWriter out = asyncContext.getResponse().getWriter();
			out.write(result_my_msg_list);
			out.close();
			
			asyncContext.complete();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void process_my_wanted(JSONObject my_info) {
		
		try{
			String result_wanted_list;
			
			String action = (String) my_info.get("action");
			String user_id = (String) my_info.get("user_id");
			
			result_wanted_list = dbQuery.db_query_my_wanted(action, user_id/*, location, ntrp, howlong, gender, court_booked*/);
System.out.println(result_wanted_list);			
			// set response code == 200
			response.setStatus(HttpServletResponse.SC_OK);
			response.setContentType("application/json; charset=utf-8");
			
			PrintWriter out = asyncContext.getResponse().getWriter();
			out.write(result_wanted_list);
			out.close();
			
			asyncContext.complete();
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private void process_check_id(JSONObject login_info){
		
		boolean check_id_valid = false;
		
		String action = (String) login_info.get("action");
		String user_id = (String) login_info.get("user_id");
		
		check_id_valid = dbQuery.db_query_check_id(user_id, user_id);
		
		if(check_id_valid) {
			try {
				// make json with login_result == success
				JSONObject check_id_response = new JSONObject();
				check_id_response.put("action", action);
				check_id_response.put("user_id", user_id);
				check_id_response.put("check_id_result", "valid");
System.out.println(check_id_response.toJSONString());			
				// set response code == 200
				response.setStatus(HttpServletResponse.SC_OK);
				response.setContentType("application/json; charset=utf-8");
				
				PrintWriter out = asyncContext.getResponse().getWriter();
				out.write(check_id_response.toString());
				out.close();
				
				asyncContext.complete();
				
			} catch(Exception e){
				e.printStackTrace();
			}
			
		} else {
			try {
				// make json with login_result == fail
				JSONObject check_id_response = new JSONObject();
				check_id_response.put("action", action);
				check_id_response.put("user_id", user_id);
				check_id_response.put("check_id_result", "invalid");
System.out.println(check_id_response.toJSONString());	
				// set response code == 200
				response.setStatus(HttpServletResponse.SC_OK);
				response.setContentType("application/json; charset=utf-8");
				
				PrintWriter out = asyncContext.getResponse().getWriter();
				out.write(check_id_response.toString());
				out.close();
				
				asyncContext.complete();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void process_login(JSONObject login_info){
		
		boolean login_success = false;
		
		String action = (String) login_info.get("action");
		String user_id = (String) login_info.get("user_id");
		String password = (String) login_info.get("password");
		String device_token = (String) login_info.get("device_token");
		String app_version =  (String) login_info.get("version");
//System.out.println(user_id + " / " + password);	
		int version_check = dbQuery.db_query_check_version(app_version);
		
		if(version_check == GSTWebAPI_Global_Vars.APP_VERSION_CURRENT) {
			login_success = dbQuery.db_query_login(user_id, password, device_token);
			
			if(login_success){
				try {
					// make json with login_result == success
					JSONObject login_response = new JSONObject();
					login_response.put("action", action);
					login_response.put("user_id", user_id);
					login_response.put("login_result", "success");
System.out.println(login_response.toJSONString());			
					// set response code == 200
					response.setStatus(HttpServletResponse.SC_OK);
					response.setContentType("application/json; charset=utf-8");
					
					PrintWriter out = asyncContext.getResponse().getWriter();
					out.write(login_response.toString());
					out.close();
					
					asyncContext.complete();
					
				} catch(Exception e){
					e.printStackTrace();
				}
				
			} else {
				try {
					// make json with login_result == fail
					JSONObject login_response = new JSONObject();
					login_response.put("action", action);
					login_response.put("user_id", user_id);
					login_response.put("login_result", "fail");
System.out.println(login_response.toJSONString());	
					// set response code == 200
					response.setStatus(HttpServletResponse.SC_OK);
					response.setContentType("application/json; charset=utf-8");
					
					PrintWriter out = asyncContext.getResponse().getWriter();
					out.write(login_response.toString());
					out.close();
					
					asyncContext.complete();
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		} else if(version_check == GSTWebAPI_Global_Vars.APP_VERSION_UPDATE_REQUIRED) {
			try {
				// make json with login_result == fail
				JSONObject login_response = new JSONObject();
				login_response.put("action", action);
				login_response.put("user_id", user_id);
				login_response.put("login_result", "update_required");
System.out.println(login_response.toJSONString());	
				// set response code == 200
				response.setStatus(HttpServletResponse.SC_OK);
				response.setContentType("application/json; charset=utf-8");
				
				PrintWriter out = asyncContext.getResponse().getWriter();
				out.write(login_response.toString());
				out.close();
				
				asyncContext.complete();
			} catch(Exception e) {
				e.printStackTrace();
			}
		} else {
			try {
				// make json with login_result == fail
				JSONObject login_response = new JSONObject();
				login_response.put("action", action);
				login_response.put("user_id", user_id);
				login_response.put("login_result", "fail");
System.out.println(login_response.toJSONString());	
				// set response code == 200
				response.setStatus(HttpServletResponse.SC_OK);
				response.setContentType("application/json; charset=utf-8");
				
				PrintWriter out = asyncContext.getResponse().getWriter();
				out.write(login_response.toString());
				out.close();
				
				asyncContext.complete();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void process_register(JSONObject register_info){
		
		boolean register_success = false;
		float ntrp;
		
		String action = (String) register_info.get("action");
		String user_name = (String) register_info.get("user_name");		
		String phone_number = (String) register_info.get("phone_number");
		String email = (String) register_info.get("email");
		String user_id = (String) register_info.get("user_id");
		String password = (String) register_info.get("password");
		String location = (String) register_info.get("location");	
		String howlong_str = (String) register_info.get("howlong");
		float howlogn = Float.valueOf(howlong_str);
		String ntrp_str = (String) register_info.get("ntrp");
		/*if(!ntrp_str.equals("NTRP")) ntrp = Float.valueOf(ntrp_str);
		else*/ ntrp = (float) 1.0;
		String gender = (String) register_info.get("gender");
		
		register_success = dbQuery.db_query_register(user_name, phone_number, email, user_id, password, location, howlogn, ntrp, gender);
		
		if(register_success){
			try {
				// make json with login_result == success
				JSONObject register_response = new JSONObject();
				register_response.put("action", action);
				register_response.put("user_id", user_id);
				register_response.put("result", "success");
System.out.println(register_response.toJSONString());			
				// set response code == 200
				response.setStatus(HttpServletResponse.SC_OK);
				response.setContentType("application/json; charset=utf-8");
				
				PrintWriter out = asyncContext.getResponse().getWriter();
				out.write(register_response.toString());
				out.close();
				
				asyncContext.complete();
				
			} catch(Exception e){
				e.printStackTrace();
			}
			
		} else {
			try {
				// make json with login_result == fail
				JSONObject register_response = new JSONObject();
				register_response.put("action", action);
				register_response.put("user_id", user_id);
				register_response.put("result", "fail");
System.out.println(register_response.toJSONString());	
				// set response code == 200
				response.setStatus(HttpServletResponse.SC_OK);
				response.setContentType("application/json; charset=utf-8");
				
				PrintWriter out = asyncContext.getResponse().getWriter();
				out.write(register_response.toString());
				out.close();
				
				asyncContext.complete();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	
	
	
	
	
	
	
	
}








































