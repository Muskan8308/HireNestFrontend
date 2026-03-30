package com.jobportal.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jobportal.dto.EmailRequest;
import com.jobportal.dto.LoginDTO;
import com.jobportal.dto.OtpRequest;
import com.jobportal.dto.ResponseDto;
import com.jobportal.dto.UserDTO;
import com.jobportal.exception.JobPortalExceptions;
import com.jobportal.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

@RestController
@CrossOrigin
@Validated
@RequestMapping("/users")
public class UserAPI {
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/register")
	public ResponseEntity<UserDTO> registerUser(@RequestBody @Valid UserDTO userDto) throws JobPortalExceptions
	{
		userDto = userService.registerUser(userDto);
		return new ResponseEntity<>(userDto, HttpStatus.CREATED);
	}

	@PostMapping("/login") 
	public ResponseEntity<UserDTO> loginUser(@RequestBody @Valid LoginDTO loginDto) throws JobPortalExceptions
	{
		return new ResponseEntity<>(userService.loginUser(loginDto), HttpStatus.OK);
	}
	
	@PostMapping("/sendOtp") 
	public ResponseEntity<ResponseDto> sendOtp(@RequestBody @Valid EmailRequest request) throws Exception
	{
		userService.sendOtp(request.getEmail());
		return new ResponseEntity<>(new ResponseDto("OTP sent successfully"), HttpStatus.OK);
	}
	
	@PostMapping("/verifyOtp") 
	public ResponseEntity<ResponseDto> verifyOtp(@RequestBody @Valid OtpRequest request) throws JobPortalExceptions
	{
		userService.verifyOtp(request.getEmail(), request.getOtp()); 
		return new ResponseEntity<>(new ResponseDto("OTP has been verified successfully !!"), HttpStatus.OK);
	}
	
	@PostMapping("/changePass") 
	public ResponseEntity<ResponseDto> changePassword(@RequestBody @Valid LoginDTO login) throws JobPortalExceptions
	{	
		return new ResponseEntity<>(new ResponseDto(userService.changePassword(login).getMessage()), HttpStatus.OK);
	}
	
}
