package com.jobportal.service;


import com.jobportal.dto.LoginDTO;
import com.jobportal.dto.UserDTO;
import com.jobportal.exception.JobPortalExceptions;



public interface UserService {
	
	public UserDTO registerUser(UserDTO userDto) throws JobPortalExceptions;

	public UserDTO loginUser(LoginDTO loginDto) throws JobPortalExceptions;

	public void sendOtp(String email);
	

}
