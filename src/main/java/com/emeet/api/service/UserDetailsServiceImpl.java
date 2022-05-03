package com.emeet.api.service;

import com.emeet.api.models.Role;
import com.emeet.api.models.User;
import com.emeet.api.repository.RoleRepository;
import com.emeet.api.repository.UserRepository;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  @Autowired
  UserRepository userRepository;

  @Autowired
  RoleRepository roleRepository;

  private static List<User> users = new ArrayList<>();
  @Override
  @Transactional
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

    return UserDetailsImpl.build(user);
  }

  public List<User> getAllUsers(){
    return userRepository.findAll();
  }

  public User get(Long id){
    return userRepository.findById(id).get();
  }

  public List<Role> getRoles(){
    return roleRepository.findAll();
  }

  public void disableUser(long userId){
    User user = userRepository.findById(userId).get();
    boolean isActive = user.getIsActive();
    
    System.out.println("userrr : "+isActive);
    if(isActive==true){
      user.setIsActive(false);;
    }
    else user.setIsActive(true);
    
    userRepository.save(user);
  }

  public String decryptPassword(String password){
    String decryptPassword="";
    try{
      String key = "1234567812345678";
      String iv = "1234567812345678";
      java.util.Base64.Decoder decoder = java.util.Base64.getDecoder();
      byte[] encrypted = decoder.decode(password);
      Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
      SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(),"AES");
      IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes());

      cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParameterSpec);

      byte[] original = cipher.doFinal(encrypted);
      decryptPassword = new String(original);
      System.out.println(decryptPassword.trim());
    }
    catch (Exception e){
      e.printStackTrace();
    }
    return decryptPassword.trim();
  }
}
