package com.luzynska.mytwitter.command;

import com.luzynska.mytwitter.Storage;

public class ReadCommand implements Command {

	@Override
	public String getRegex() {
		return "^" + Command.NAME_REGEX + "$";
	}

	@Override
	public String execute(String line) {
		String userName = parseCommandUser(line);
		Storage storage = Storage.getInstance();
		return ResponseFormatter.formatReadResponse(storage.read(userName));
	}

}
