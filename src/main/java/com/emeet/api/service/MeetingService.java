package com.emeet.api.service;


import com.emeet.api.models.Meetings;
import com.emeet.api.models.User;
import com.emeet.api.payload.MeetingRequest;
import com.emeet.api.payload.MessageResponse;
import com.emeet.api.repository.MeetingRepository;
import com.emeet.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MeetingService 
{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MeetingRepository meetingRepository;

    public List<Meetings> getAllMeetings(){
        return meetingRepository.findMeetingsByDesc();
    }

    public ResponseEntity<?> createMeeting(MeetingRequest meet){
        Meetings meeting = new Meetings(meet.getMeetingId(),meet.getMeetingUrl(),meet.getDate(),meet.getTime(),meet.getLocation(),meet.isPublished());
       Set<String> users = meet.getUsers();
        Set<User> user = new HashSet<>();
        for (String i : users){
            User temp = userRepository.getById(Long.valueOf(i));
            meeting.getUsers().add(temp);
            meetingRepository.save(meeting);
   }
        return ResponseEntity.ok(new MessageResponse("Meeting created successfully!"));
    }

    public ResponseEntity<?> assignMeeting(List<Meetings> meetings, long id){

        try {
            User user = userRepository.getById(id);
            List<User> userList = userRepository.findAll();

            for (Meetings meet : meetings) {
                Meetings temp = meetingRepository.getById(meet.getId());
                temp.getUsers().add(user);
                meetingRepository.save(temp);
            }
            return ResponseEntity.ok(new MessageResponse("Meeting assigned successfully!"));
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND , "User with id "+ id + " not found",e);
        }
    }
    
    public ResponseEntity<?> RequestMeetingByUser(List<Meetings> meetings, long id){
        try {
            User user = userRepository.getById(id);
            List<User> userList = userRepository.findAll();
            for (Meetings meet : meetings) {
                Meetings temp = meetingRepository.getById(meet.getId());
                temp.getRequestuserMeeting().add(user);
                meetingRepository.save(temp);
            }
            return ResponseEntity.ok(new MessageResponse("Meeting Request successfully!"));
        }catch (Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> removeMeeting(Set<Meetings> list, long id){
        User user = userRepository.findById(id).get();
        for(Meetings meet: list){
            Meetings temp = meetingRepository.getById(meet.getId());
            temp.getUsers().remove(user);
            meetingRepository.save(temp);
        }

        return ResponseEntity.ok(new MessageResponse("Meetings updated successfully!"));
    }
    public Set<Meetings> getMeetingOfUser(long id){
        User user = userRepository.findById(id).get();
        Set<Meetings> meetingsList = user.getMeetings();
        return meetingsList;
    }

    public ResponseEntity<?> assignUsers(long id, Set<User> users) {
        Meetings meet = meetingRepository.findById(id).get();

        for(User user: users){
            User temp = userRepository.findById(user.getId()).get();
            meet.getUsers().add(temp);
        }
        meetingRepository.save(meet);
        return ResponseEntity.ok(new MessageResponse("Users Assigned Successfully"));
    }
    
  
	public List<Meetings> isPublished() {
		return meetingRepository.findAllByisPublished(true);
	}
	
	public ResponseEntity<?> requestMeetinglist(long id) {
		
		User user=userRepository.findById(id).get();
		return   ResponseEntity.ok(user.getRequestMeetings());
	}
	
	public ResponseEntity<?> disableMeeting(long id)
	{
		  Meetings meet = meetingRepository.findById(id).get();
		  meet.setDisable(true);
		  meet.setDisableDateandTime(new Date());
		  meetingRepository.save(meet);
		  return ResponseEntity.ok(new MessageResponse("Meeting Disabled Successfully"));
		  
		  
	}

	public ResponseEntity<?> requestMeeting(long id) {
		// TODO Auto-generated method stub
		
		List<Meetings> isPublishedMeeting=  isPublished();
		Set<Meetings> userMeetings=getMeetingOfUser(id);
		User user=userRepository.findById(id).get();
		Set<Meetings> reqMeetings=user.getRequestMeetings();
		
		
		
		Set<Meetings> requestMeetingslist=new HashSet<Meetings>();
		
		for(Meetings m:isPublishedMeeting)
		{
			
			if(m.isDisable()||userMeetings.contains(m) || reqMeetings.contains(m))
				continue;
			requestMeetingslist.add(m);
		}
		//requestMeetingslist.removeAll(userMeetings);
		//requestMeetingslist.removeAll(reqMeetings);

		
		return ResponseEntity.ok(requestMeetingslist);
		
		
		
	}
	
	 public ResponseEntity<?> removeRequestedMeeting(List<Meetings> list, long id){
	        User user = userRepository.findById(id).get();

	       
	        for(Meetings meet: list){
	            Meetings temp = meetingRepository.getById(meet.getId());
	            temp.getRequestuserMeeting().remove(user);
	            meetingRepository.save(temp);
	        }

	        return ResponseEntity.ok(new MessageResponse("Meetings updated successfully!"));
	    }

	public void publishMeeting(long id) {
		
		
		  Meetings meet = meetingRepository.findById(id).get();
		  meet.setPublished(true);
		  meetingRepository.save(meet);
		  
		
	}


}
