package com.jobportal.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OtpRequest {

	@Email(message = "{user.email.invalid}")
    private String email;

    @Pattern(regexp = "^[0-9]{6}$", message = "{otp.invalid}")
    private String otp;
}
