package com.luzynska.mytwitter;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.luzynska.mytwitter.command.Command;
import com.luzynska.mytwitter.command.FollowCommand;
import com.luzynska.mytwitter.command.PostCommand;
import com.luzynska.mytwitter.command.ReadCommand;
import com.luzynska.mytwitter.command.WallCommand;

public class CommandInterpreter {
	
	private static final Set<Command> REGEX_RULES_SET;
	static {
		REGEX_RULES_SET = new LinkedHashSet<>();
		REGEX_RULES_SET.add(new FollowCommand());
		REGEX_RULES_SET.add(new PostCommand());
		REGEX_RULES_SET.add(new WallCommand());
		REGEX_RULES_SET.add(new ReadCommand());
	}
	
	public String interpreteCommand(String commandLine) {
		String line = commandLine.trim();
		for(Command command : REGEX_RULES_SET) {
			Pattern pattern = Pattern.compile(command.getRegex());
			Matcher matcher = pattern.matcher(line);
			
			if (matcher.matches()) {
				return command.execute(line);
			}
		}
		return "";
	}
}
