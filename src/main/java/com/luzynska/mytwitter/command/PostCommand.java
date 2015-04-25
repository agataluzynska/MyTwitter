package com.luzynska.mytwitter.command;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.luzynska.mytwitter.Storage;

public class PostCommand implements Command {

	public static final String REGEX = "^" + Command.NAME_REGEX + "\\s+->\\s+.+$";
	
	@Override
	public String getRegex() {
		return REGEX;
	}

	@Override
	public String execute(String line) {
		String userName = parseCommandUser(line);
		String message = parseMessage(line);
		Storage storage = Storage.getInstance();
		storage.addMessage(userName, message);
		return ResponseFormatter.getEmptyReponse();
	}

	private String parseMessage(String line) {
		Pattern pattern = Pattern.compile("\\s+->\\s+");
		Matcher matcher = pattern.matcher(line);
		if (matcher.find()) {
			return line.substring(matcher.end());
		} else {
			throw new IllegalStateException("Command format invalid.");
		}
	}

}
