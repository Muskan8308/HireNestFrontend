package com.jobportal.service;

import com.jobportal.dto.LoginDTO;
import com.jobportal.dto.ResponseDto;
import com.jobportal.dto.UserDTO;
import com.jobportal.exception.JobPortalExceptions;


public interface UserService {
	
	public UserDTO registerUser(UserDTO userDto) throws JobPortalExceptions;

	public UserDTO loginUser(LoginDTO loginDto) throws JobPortalExceptions;

	public Boolean sendOtp(String email) throws Exception;

	public Boolean verifyOtp(String email, String otp) throws JobPortalExceptions;

	public ResponseDto changePassword(LoginDTO login) throws JobPortalExceptions;
	

}
