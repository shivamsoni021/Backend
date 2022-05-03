package com.emeet.api.payload;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Transient;
import javax.validation.constraints.*;

public class SignupRequest {
  @NotBlank
  @Size(min = 3, max = 20)
  private String username;

  private String firstName;
  private String lastName;
  
  
  public String getFirstName() {
	return firstName;
}

public void setFirstName(String firstName) {
	this.firstName = firstName;
}

public String getLastName() {
	return lastName;
}

public void setLastName(String lastName) {
	this.lastName = lastName;
}

@NotBlank
  @Size(max = 50)
  @Email
  private String email;
  

  public String getCreatedBy() {
	return createdBy;
}

public void setCreatedBy(String createdBy) {
	this.createdBy = createdBy;
}

private String createdBy;
  //for active user by defaul
 


private Set<String> role=new HashSet<>();

  @NotBlank
  @Size(min = 6, max = 40)
  private String password;

  
  
  private String phoneno;
  private boolean isActive;

  public String getPhoneno() {
    return phoneno;
  }

  public void setPhoneno(String phoneno) {
    this.phoneno = phoneno;
  }

 

  public boolean getIsActive() {
	return isActive;
}

public void setIsActive(boolean isActive) {
	this.isActive = isActive;
}

public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Set<String> getRole() {
    return this.role;
  }

  public void setRole(Set<String> role) {
    this.role = role;
  }
}