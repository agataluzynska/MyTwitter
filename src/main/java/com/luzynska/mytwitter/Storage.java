package com.luzynska.mytwitter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.luzynska.mytwitter.domain.Message;
import com.luzynska.mytwitter.domain.User;

@SuppressWarnings("serial")
public class Storage extends HashMap<String, User> {
	
	private static Storage instance;
	
	public static final Storage getInstance() {
		if (instance == null) {
			instance = new Storage();
		} 
		return instance;
	}
	
	private Storage() {
		
	}
	
	public void addMessage(String userName, String message) {
		User user = this.get(userName);
		if (user == null) {
			user = new User(userName);
			this.put(userName, user);
		}
		this.get(userName).getMessages().add(new Message(message, user));
	}
	
	public List<Message> read(String userName) {
		if (!this.keySet().contains(userName)) {
			return Collections.emptyList();
		}
		User me = this.get(userName);
		List<Message> result = new ArrayList<Message>();
		result.addAll(me.getMessages());
		return result;
	}
	
	public List<Message> readWall(String userName) {
		User me = this.get(userName);
		if (me == null) {
			return Collections.emptyList();
		}
		List<Message> wall = read(userName);
		
		me.getFollowees().stream().forEach(e -> wall.addAll(e.getMessages()));
		
		Collections.sort(wall);
		return wall;
	}
	
	public void follow(String userName, String followee) {
		User user = this.get(userName);
		if (user == null) {
			return;
		}
		
		User followeeUser = this.get(followee);
		if (followeeUser == null) {
			return;
		}
		
		if (userName.equals(followee)){
			return;
		}
		user.getFollowees().add(followeeUser);
		
	}
}
