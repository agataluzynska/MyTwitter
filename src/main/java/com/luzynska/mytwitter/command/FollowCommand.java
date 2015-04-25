package com.luzynska.mytwitter.command;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.luzynska.mytwitter.Storage;

public class FollowCommand implements Command {

	public static final String REGEX = "^" + Command.NAME_REGEX	+ "\\s+follows\\s+" + Command.NAME_REGEX + "$";
	
	@Override
	public String getRegex() {
		return REGEX;
	}

	@Override
	public String execute(String line) {
		String userName = parseCommandUser(line);
		String folowee = getFoloweeName(line);
		Storage storage = Storage.getInstance();
		storage.follow(userName, folowee);
		return ResponseFormatter.getEmptyReponse();
	}

	private String getFoloweeName(String line) {
		Pattern pattern = Pattern.compile("\\s+follows\\s+");
		Matcher matcher = pattern.matcher(line);
		if (matcher.find()) {
			return line.substring(matcher.end());
		} else {
			throw new IllegalStateException("Command format invalid.");
		}
	}

}
