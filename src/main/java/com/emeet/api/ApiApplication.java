package com.emeet.api;

import com.emeet.api.models.Meetings;
import com.emeet.api.models.User;
import com.emeet.api.repository.MeetingRepository;
import com.emeet.api.repository.RoleRepository;
import com.emeet.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;

@SpringBootApplication
public class ApiApplication implements CommandLineRunner
//		implements CommandLineRunner
{

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private MeetingRepository meetingRepository;

	@Autowired
	private RoleRepository roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}
	@Override
	public void run(String... args) throws Exception {
	
	}

}
