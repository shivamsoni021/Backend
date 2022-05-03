package com.emeet.api.models;

import com.emeet.api.service.UserDetailsImpl;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cascade;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "users", 
    uniqueConstraints = { 
      @UniqueConstraint(columnNames = "username"),
      @UniqueConstraint(columnNames = "email") 
    })
public class User extends UserDetailsImpl{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  @Size(max = 20)
  private String username;
  
  @NotBlank
  @Size(max = 20)
  private String firstName;
  
  @NotBlank
  @Size(max = 20)
  private String lastName;

  @NotBlank
  @Size(max = 50)
  @Email
  private String email;

  @NotBlank
  @Size(max = 120)
  private String password;

  private String phoneno;
  private boolean isActive;

  @JsonIgnore
  @ManyToMany(cascade=CascadeType.ALL)
  @JoinTable(  name = "user_roles",
        joinColumns = @JoinColumn(name = "users_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<Role> roles = new HashSet<>();

  @JsonIgnore
  @ManyToMany(
          fetch = FetchType.LAZY,
          mappedBy = "users",
          cascade = {
                  CascadeType.PERSIST,
                  CascadeType.MERGE
          })

  private Set<Meetings> meetings = new HashSet<>();
  
  
  @JsonIgnore
  @ManyToMany(
          fetch = FetchType.LAZY,
          mappedBy = "requestuserMeeting",
          cascade = {
                  CascadeType.PERSIST,
                  CascadeType.MERGE
          })

  private Set<Meetings> requestMeetings = new HashSet<>();


  public Set<Meetings> getRequestMeetings() {
	return requestMeetings;
}

public void setRequestMeetings(Set<Meetings> requestMeetings) {
	this.requestMeetings = requestMeetings;
}

public Set<Meetings> getMeetings() {
    return meetings;
  }

  public void setMeetings(Set<Meetings> meetings) {
    this.meetings = meetings;
  }

  public User() {
  }

  public User(String username, String email, String password, String phoneno, Boolean isActive,String firstName,String lastName) {
    this.username = username;
    this.email = email;
    this.password = password;
    this.phoneno = phoneno;
    this.isActive = isActive;
    this.lastName = lastName;
    this.firstName = firstName;
  }

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

public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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

  public Set<Role> getRoles() {
    return roles;
  }

  public void setRoles(Set<Role> roles) {
    this.roles = roles;
  }

  public String getPhoneno() {
    return phoneno;
  }

  public void setPhoneno(String phoneno) {
    this.phoneno = phoneno;
  }

  public boolean getIsActive() {
    return isActive;
  }

  public void setIsActive(boolean active) {
    isActive = active;
  }

  public void addRole(Role role){
      this.roles.clear();
      this.roles.add(role);
  }
  
  @Override
	public boolean isEnabled() {
		
		return this.isActive;
	}


}
