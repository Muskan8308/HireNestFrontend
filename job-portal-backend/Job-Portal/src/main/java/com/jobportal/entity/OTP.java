package com.jobportal.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name="otp")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OTP {

	@Id
	private String email;
	private String otpCode;
	private LocalDateTime creationTime;

}
