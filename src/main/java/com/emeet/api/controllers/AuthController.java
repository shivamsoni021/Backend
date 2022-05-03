package com.emeet.api.controllers;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.emeet.api.models.ERole;
import com.emeet.api.models.Role;
import com.emeet.api.models.User;
import com.emeet.api.payload.LoginRequest;
import com.emeet.api.payload.SignupRequest;
import com.emeet.api.payload.JwtResponse;
import com.emeet.api.payload.MessageResponse;
import com.emeet.api.repository.RoleRepository;
import com.emeet.api.repository.UserRepository;
import com.emeet.api.config.JwtUtils;
import com.emeet.api.service.UserDetailsImpl;
import com.emeet.api.service.UserDetailsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class AuthController {
  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  UserRepository userRepository;

  @Autowired
  RoleRepository roleRepository;

  @Autowired
  PasswordEncoder encoder;

  @Autowired
  JwtUtils jwtUtils;

  @Autowired
  UserDetailsServiceImpl userDetailsService;

  Logger logger = LoggerFactory.getLogger(AuthController.class);

  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
    logger.info("Signin Process Inititated");
  try {
    String password = userDetailsService.decryptPassword(loginRequest.getPassword());
    Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), password));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtUtils.generateJwtToken(authentication);

    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    List<String> roles = userDetails.getAuthorities().stream()
            .map(item -> item.getAuthority())
            .collect(Collectors.toList());
    return ResponseEntity.ok(new JwtResponse(jwt,
            userDetails.getId(),
            userDetails.getUsername(),
            userDetails.getEmail(),
            roles));
  }
  catch(Exception e){
    e.printStackTrace();
    return ResponseEntity.internalServerError().build();
  }

  }

//  @GetMapping("/update-role/{id}")
//  public String editUser(@PathVariable("id") Long id, Model model){
//    User user = userDetailsService.get(id);
//    Role role = roleRepository.findByName(rolename);
//
//    model.addAttribute("user",user);
//    model.addAttribute("role",list);
//  }

  @PostMapping("/update-role/{id}")
  public ResponseEntity<MessageResponse> update(@RequestBody String username, @PathVariable("id") long userId){
    List<User> list = userDetailsService.getAllUsers();
    this.userDetailsService.disableUser(userId);
    return ResponseEntity.ok(new MessageResponse("Okay"));
  }

 
  
  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
    if (userRepository.existsByUsername(signUpRequest.getUsername())) {
      return ResponseEntity
          .badRequest()
          .body(new MessageResponse("Error: Username is already taken!"));
    }

    if (userRepository.existsByEmail(signUpRequest.getEmail())) {
      return ResponseEntity
          .badRequest()
          .body(new MessageResponse("Error: Email is already in use!"));
    }

    String password = userDetailsService.decryptPassword(signUpRequest.getPassword());
    User user = new User(signUpRequest.getUsername(),
               signUpRequest.getEmail(),
               encoder.encode(password),
              signUpRequest.getPhoneno(),
             signUpRequest.getIsActive(),
             signUpRequest.getFirstName(),
             signUpRequest.getLastName());

    Set<String> strRoles = signUpRequest.getRole();
    for(String i: strRoles){
        System.out.println(i);
    }
    Set<Role> roles = new HashSet<>();

    if (strRoles == null) {
      Role userRole = roleRepository.findByName(ERole.ROLE_USER)
          .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
      roles.add(userRole);
    } else {
      strRoles.forEach(role -> {
        switch (role) {
        case "admin":
          Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));

          roles.add(adminRole);

          break;
          case "inactive":
            Role inactiveRole = roleRepository.findByName(ERole.ROLE_INACTIVE)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(inactiveRole);

            break;
        default:
          Role userRole = roleRepository.findByName(ERole.ROLE_USER)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(userRole);
        }
      });
    }

    user.setRoles(roles);
    userRepository.save(user);

    return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
  }
  
  

}
