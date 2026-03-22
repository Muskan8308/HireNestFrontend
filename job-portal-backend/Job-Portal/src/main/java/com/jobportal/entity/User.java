package com.jobportal.entity;

import com.jobportal.dto.AccountType;
import com.jobportal.dto.UserDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="users")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message="{user.name.absent}")
	private String name; 

	@NotBlank(message="{user.email.absent}")
	@Email(message = "{user.email.invalid}")
	@Column(unique=true)
	private String email;
	
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za- z\\d@$!%*?&]{8,}$",
		    message = "{user.password.invalid}")
	@NotBlank(message="{user.password.abse  nt}")
	private String password;
	
	private AccountType accountType;


	public UserDTO toDTO()
	{
		return new UserDTO(this.id, this.name, this.email, this.password, this.accountType);
	}
}
