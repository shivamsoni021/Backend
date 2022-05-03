package com.emeet.api.repository;

import java.util.List;
import java.util.Optional;

import com.emeet.api.models.Meetings;
import com.emeet.api.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByUsername(String username);

  Boolean existsByUsername(String username);

  Boolean existsByEmail(String email);
  
  List<Meetings> findById(int id );
  
  @Query(value = "SELECT u from User u order by u.id desc")
  List<User> findUserByDesc();
  
  
  
  
  
  //Meetings findTopByOrderByMeetingDisableDateandTimeAsc(int id);
  
}
