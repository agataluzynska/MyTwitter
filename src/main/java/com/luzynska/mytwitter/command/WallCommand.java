package com.luzynska.mytwitter.command;

import com.luzynska.mytwitter.Storage;

public class WallCommand implements Command {

	public static final String REGEX = "^" + Command.NAME_REGEX + "\\s+wall$";
	
	@Override
	public String getRegex() {
		return REGEX;
	}

	@Override
	public String execute(String line) {
		String userName = parseCommandUser(line);
		Storage storage = Storage.getInstance();
		return ResponseFormatter.formatWallResponse(storage.readWall(userName));
	}

}
