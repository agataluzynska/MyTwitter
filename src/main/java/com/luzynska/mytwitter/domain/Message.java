package com.luzynska.mytwitter.domain;

import java.util.Date;

public class Message implements Comparable<Message> {

	private String message;
	private User owner;
	private Date creationDate;
	
	public Message(String message, User owner) {
		super();
		this.message = message;
		this.owner = owner;
		this.creationDate = new Date();
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Override
	public int compareTo(Message o) {
		return o.creationDate.compareTo(this.creationDate);
	}
	
}
