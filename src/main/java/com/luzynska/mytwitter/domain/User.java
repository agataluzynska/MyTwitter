package com.luzynska.mytwitter.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class User {

	private String userName;
	private List<Message> messages;
	private Set<User> followees;

	public User(String userName) {
		super();
		this.userName = userName;
		this.messages = new ArrayList<Message>();
		this.followees = new HashSet<User>();
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

	public Set<User> getFollowees() {
		return followees;
	}

	public void setFollowees(Set<User> followees) {
		this.followees = followees;
	}

}
