package com.emeet.api.repository;


import com.emeet.api.models.Meetings;
import com.emeet.api.models.User;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MeetingRepository extends JpaRepository<Meetings, Long> {
	List<Meetings> findAllByisPublished(boolean isPublished);
	
	
	@Query(value = "SELECT m from Meetings m order by m.id desc")
	  List<Meetings> findMeetingsByDesc();
	
	//Meetings findTopByOrderByDisableDateandTimeAsc(int id);

}
