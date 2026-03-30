package com.jobportal.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.jobportal.dto.LoginDTO;
import com.jobportal.dto.ResponseDto;
import com.jobportal.dto.UserDTO;
import com.jobportal.entity.OTP;
import com.jobportal.entity.User;
import com.jobportal.exception.JobPortalExceptions;
import com.jobportal.repository.OTPrepository;
import com.jobportal.repository.UserRepository;
import com.jobportal.utility.Data;
import com.jobportal.utility.Utilities;

import jakarta.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;


@Service(value="userService")
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepo; 
	 
	@Autowired
	private JavaMailSender mailSender; 
	
	@Autowired
	private OTPrepository otpRepo; 
	 
	 
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

	@Override
	public Boolean sendOtp(String email) throws Exception{

		User user = userRepo.findByEmail(email).orElseThrow(() -> new JobPortalExceptions("USER_NOT_FOUND"));
		MimeMessage mm = mailSender.createMimeMessage();
		MimeMessageHelper message = new MimeMessageHelper(mm, true);
		message.setTo(email);
		message.setSubject("Your OTP Code");
		String genOtp = Utilities.generateOtp();
		
		OTP otp = new OTP(email, genOtp, LocalDateTime.now());
		otpRepo.save(otp);
		message.setText(Data.getMessageBody(genOtp, user.getName()), true);
		mailSender.send(mm);
		return true;
	}

	@Override
	public Boolean verifyOtp(String email, String otp) throws JobPortalExceptions {
		
		OTP otpEntity = otpRepo.findById(email).orElseThrow(() -> new JobPortalExceptions("OTP_NOT_FOUND"));
		
		if(!otpEntity.getOtpCode().equals(otp)) throw new JobPortalExceptions("INVALID_OTP");
		
		
		return true;
	}

	@Override
	public ResponseDto changePassword(LoginDTO login) throws JobPortalExceptions {

		User user = userRepo.findByEmail(login.getEmail()).orElseThrow(() -> new JobPortalExceptions("USER_NOT_FOUND"));
		user.setPassword(passwordEncoder.encode(login.getPassword()));
		userRepo.save(user);
		
		return new ResponseDto("Password changed Successfully.");
	}
	
	@Scheduled(fixedRate = 60000)
	public void removeExpiredOtps()
	{
		LocalDateTime expiry = LocalDateTime.now().minusMinutes(5);
		List<OTP> expiredOtps = otpRepo.findByCreationTimeBefore(expiry);
		
		if(!expiredOtps.isEmpty())
		{
			otpRepo.deleteAll(expiredOtps);
			System.out.println("Removed "+ expiredOtps.size() +" expired OTPS.");
		}
	}
	

}
