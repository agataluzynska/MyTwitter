package com.luzynska.mytwitter.command;

import com.luzynska.mytwitter.Storage;

public class WallCommand implements Command {

	@Override
	public String getRegex() {
		return "^" + Command.NAME_REGEX + "\\s+wall$";
	}

	@Override
	public String execute(String line) {
		String userName = parseCommandUser(line);
		Storage storage = Storage.getInstance();
		return ResponseFormatter.formatWallResponse(storage.readWall(userName));
	}

}
