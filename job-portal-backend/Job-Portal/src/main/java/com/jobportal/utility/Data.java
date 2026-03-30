package com.jobportal.utility;

public class Data {
	
	public static String getMessageBody(String otp, String name)
	{
		return "<!DOCTYPE html>"
		        + "<html>"
		        + "<head>"
		        + "  <style>"
		        + "    body { font-family: Arial, sans-serif; background-color: #f4f4f4; margin: 0; padding: 0; }"
		        + "    .container { max-width: 500px; margin: 30px auto; background: #ffffff; padding: 20px; border-radius: 10px; text-align: center; }"
		        + "    .header { font-size: 22px; font-weight: bold; color: #333; margin-bottom: 20px; }"
		        + "    .otp { font-size: 32px; font-weight: bold; color: #4CAF50; letter-spacing: 5px; margin: 20px 0; }"
		        + "    .text { font-size: 14px; color: #555; margin-bottom: 20px; }"
		        + "    .footer { font-size: 12px; color: #999; margin-top: 20px; }"
		        + "  </style>"
		        + "</head>"
		        + "<body>"
		        + "  <div class='container'>"
		        + "    <div class='header'>Verify Your Email</div>"
		        + "    <div class='text'>Hello "+ name +","
		        + "    <div class='text'>Use the OTP below to verify your account. This OTP is valid for 5 minutes.</div>"
		        + "    <div class='otp'>" + otp + "</div>"
		        + "    <div class='text'>If you did not request this, please ignore this email.</div>"
		        + "    <div class='footer'>© 2026 HireNest. All rights reserved.</div>"
		        + "  </div>"
		        + "</body>"
		        + "</html>";

	}

}
