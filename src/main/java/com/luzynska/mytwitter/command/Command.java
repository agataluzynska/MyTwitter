package com.luzynska.mytwitter.command;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface Command {

	public static final String NAME_REGEX = "[A-Za-z]+";

	String getRegex();

	String execute(String line);

	default String parseCommandUser(String line) {
		Pattern pattern = Pattern.compile("^" + NAME_REGEX);
		Matcher matcher = pattern.matcher(line);
		if (matcher.find()) {
			return matcher.group();
		} else {
			throw new IllegalStateException("Command format invalid.");
		}
	}
	
}
