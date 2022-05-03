package com.emeet.api.controllers;

import com.emeet.api.models.Meetings;
import com.emeet.api.models.User;
import com.emeet.api.payload.MeetingRequest;
import com.emeet.api.payload.MessageResponse;
import com.emeet.api.repository.UserRepository;
import com.emeet.api.service.MeetingService;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class MeetingController  {

    Logger logger = LoggerFactory.getLogger(MeetingController.class);
    @Autowired
    private MeetingService meetingService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/create-meeting")
    public ResponseEntity<?> createMeeting(@RequestBody MeetingRequest meet){
        meetingService.createMeeting(meet);
        return ResponseEntity.ok(new MessageResponse("Meeting created successfully!"));
    }

    @GetMapping("/meeting-list")
    public ResponseEntity<List<Meetings>> getAllMeeting(){
        logger.info("Getting Meeting List");
        try{
        List<Meetings> list = meetingService.getAllMeetings();
        return new ResponseEntity<>(list,HttpStatus.OK);
        }catch (Exception e){
            logger.error("Exception in Getting Meeting List");
            e.printStackTrace();
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,"Meeting List not found", e
            );
        }
    }


    @PostMapping("/assign-meets/{id}")
    public ResponseEntity<?> assignMeetings(@PathVariable("id") long id,@RequestBody List<Meetings> list){
        logger.info("Assign Meet is called....");
        try{
            return meetingService.assignMeeting(list,id);
        }catch (Exception e){
            logger.error(e.getMessage());
            return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers(){
        logger.info("Returning Users List");
        List<User> list = userRepository.findUserByDesc();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/user-meeting/{id}")
    public ResponseEntity<Set<Meetings>> getUserMeetings(@PathVariable("id") long id){
        Set<Meetings> userMeetings= meetingService.getMeetingOfUser(id);
        return new ResponseEntity<>(userMeetings,HttpStatus.OK);
    }

    @PutMapping("/remove-meeting/{id}")
    public ResponseEntity<?> removeMeetings(@PathVariable("id") long id, @RequestBody Set<Meetings> list){
        logger.info("Removing Meeting...");
        try {
            return meetingService.removeMeeting(list,id);
        }catch (Exception e){
            logger.error(e.getMessage());
            return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/assign-user/{id}")
    public ResponseEntity<?> assignUsers(@PathVariable("id") long id, @RequestBody Set<User> users){
        logger.info("Assigning User to meeting....");
        try {
            return meetingService.assignUsers(id,users);
        }catch (Exception e){
            logger.error(e.getMessage());
            return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/isPublished")
	public ResponseEntity<?> editMeeting()
	{
        try {
            return ResponseEntity.ok(meetingService.isPublished());
        }catch (Exception e){
            logger.error(e.getMessage());
            return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
        }
	}
    
    @GetMapping("/disable/{id}")
    public ResponseEntity<?> disableMeeting(@PathVariable("id") long id)
    {
        logger.info("Disabling Meeting...");
        try{
            return meetingService.disableMeeting(id);
        }catch (Exception e){
            logger.error(e.getMessage());
            return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }
    
  
    
    @GetMapping("/userDisabledmeeting/{id}")
    public ResponseEntity<?> userDisabledmeeting(@PathVariable("id") long id){
    	try {
        Set<Meetings> userMeetings= meetingService.getMeetingOfUser(id);
        List<Meetings> sortedList = new ArrayList<Meetings>();
        for(Meetings m:userMeetings)
        {
        	if(m.isDisable()==false)
        		continue;
        	sortedList.add(m);
        }
        
        Collections.sort(sortedList,new com.emeet.api.models.MeetingSort());
        return new ResponseEntity<>(sortedList.get(0),HttpStatus.OK);
    	}
    	catch(Exception e)
    	{
    		return ResponseEntity.ok(new MessageResponse("No meeting exist"));
    	}
    	
    }
    
    
    @GetMapping("/userReqMeetingList/{id}")
    public ResponseEntity<?> userReqMeetingList(@PathVariable("id") long id)
    {
        try {
            return meetingService.requestMeeting(id);
        }catch (Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }
    
    @PostMapping("/userReqMeeting/{id}")
    public ResponseEntity<?> userReqMeetings(@PathVariable("id") long id,@RequestBody List<Meetings> list){
        try {
            return meetingService.RequestMeetingByUser(list, id);
        }catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/userRequestedList/{id}")
    public ResponseEntity<?> userRequestList(@PathVariable("id") long id)
    {
        try {
            return meetingService.requestMeetinglist(id);
        }catch (HttpClientErrorException.NotFound e){
            return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }
    
    @PostMapping("/userReqMeetingRemove/{id}")
    public ResponseEntity<?> userReqMeetingRemove(@PathVariable("id") long id,@RequestBody List<Meetings> list){
        try {
            return meetingService.removeRequestedMeeting(list, id);
        }catch (HttpClientErrorException.NotFound e){
            return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/meeting_publish/{id}")
    public ResponseEntity<?> meeting_publish(@PathVariable("id") long id){
        try {
            meetingService.publishMeeting(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch(Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
