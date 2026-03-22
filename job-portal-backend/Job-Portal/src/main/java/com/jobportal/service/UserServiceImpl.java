package com.jobportal.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jobportal.dto.LoginDTO;
import com.jobportal.dto.UserDTO;
import com.jobportal.entity.User;
import com.jobportal.exception.JobPortalExceptions;
import com.jobportal.repository.UserRepository;


@Service(value="userService")
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepo; 
	 
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public UserDTO registerUser(UserDTO userDto) throws JobPortalExceptions {
		
		Optional<User> optional = userRepo.findByEmail(userDto.getEmail());
		if(optional.isPresent()) throw new JobPortalExceptions("USER_FOUND");
		
		userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
		User user = userDto.toEntity();
		userRepo.save(user);
		
		return user.toDTO();
	}

	@Override
	public UserDTO loginUser(LoginDTO loginDto) throws JobPortalExceptions {

		User user = userRepo.findByEmail(loginDto.getEmail()).orElseThrow(() -> new JobPortalExceptions("USER_NOT_FOUND"));
		
		if(!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) throw new JobPortalExceptions("INVALID_CREDENTIALS");
		
		return user.toDTO();
	
	}

}
