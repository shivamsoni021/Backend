package com.emeet.api.payload;

import com.emeet.api.models.User;

import java.util.Date;
import java.util.Set;

public class MeetingRequest {
    private String meetingId;
    private String meetingUrl;
    private Date date;
    private Date time;
    private String location;
    private boolean Published;
    
   

	

	public boolean isPublished() {
		return this.Published;
	}

	public void setPublished(boolean published) {
		this.Published = published;
	}

	private Set<String> users;

	

	public String getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(String meetingId) {
        this.meetingId = meetingId;
    }

    public String getMeetingUrl() {
        return meetingUrl;
    }

    public void setMeetingUrl(String meetingUrl) {
        this.meetingUrl = meetingUrl;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Set<String> getUsers() {
        return users;
    }

    public void setUsers(Set<String> users) {
        this.users = users;
    }
}
