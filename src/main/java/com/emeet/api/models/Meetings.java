package com.emeet.api.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "meetings")
public class Meetings  implements Comparable<Meetings>
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String meetingId;
    private String meetingUrl;
    
    private boolean isPublished;
    
    

	@Temporal(TemporalType.TIMESTAMP)
    private Date date;

	
	private Date DisableDateandTime;
	
	private boolean Disable;
	
    public Date getDisbaleDateandTime() {
		return DisableDateandTime;
	}

	public void setDisableDateandTime(Date disbaleDateandTime) {
		this.DisableDateandTime = disbaleDateandTime;
	}

	public boolean isDisable() {
		return Disable;
	}

	public void setDisable(boolean disable) {
		Disable = disable;
	}

	@Temporal(TemporalType.TIMESTAMP)
    private Date time;
    private String location;

//    @JsonIgnore
     @ManyToMany(fetch = FetchType.LAZY,cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name="user_meetings",
            joinColumns = {@JoinColumn(name="meetings_id")},
            inverseJoinColumns = {@JoinColumn(name="users_id")}
    )

    private Set<User> users = new HashSet<>();
     
     
     @JsonIgnore
     @ManyToMany(fetch = FetchType.LAZY,cascade = {
             CascadeType.PERSIST,
             CascadeType.MERGE
     })
     @JoinTable(name="user_meetings_request",
             joinColumns = {@JoinColumn(name="meetings_id")},
             inverseJoinColumns = {@JoinColumn(name="users_id")}
     )
     private Set<User> requestuserMeeting=new HashSet<>();

    public Set<User> getRequestuserMeeting() {
		return requestuserMeeting;
	}

	public void setRequestuserMeeting(Set<User> requestuserMeeting) {
		this.requestuserMeeting = requestuserMeeting;
	}

	public Meetings() {
    }

    public Meetings(String meetingId, String meetingUrl, Date date, Date time, String location,boolean isPublished) {
        this.meetingId = meetingId;
        this.meetingUrl = meetingUrl;
        this.date = date;
        this.time = time;
        this.location = location;
        this.isPublished=isPublished;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(String meetingId) {
        this.meetingId = meetingId;
    }
    
    
    public String getDisbaleDateandTimeSort() {
		return ""+DisableDateandTime;
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

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
    
    public boolean isPublished() {
		return isPublished;
	}

	public void setPublished(boolean isPublished) {
		this.isPublished = isPublished;
	}
	
	
	 @Override
	  public int compareTo(Meetings meeting) 
	  {
	    if (getDisbaleDateandTime() == null || meeting.getDisbaleDateandTime() == null)
	      return 0;
	    return getDisbaleDateandTime().compareTo(meeting.getDisbaleDateandTime());
	  }
	
	 
	 @Override
	 public boolean equals(Object m)
	 {
		 
		 Meetings m1=(Meetings)m;
		 if(this.getId()==m1.getId())
			 return true;
		 
		 return false;
	 }

}
