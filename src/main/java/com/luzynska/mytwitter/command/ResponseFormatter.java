package com.luzynska.mytwitter.command;

import java.util.List;

import org.ocpsoft.prettytime.PrettyTime;
import org.ocpsoft.prettytime.units.JustNow;
import org.ocpsoft.prettytime.units.Millisecond;

import com.luzynska.mytwitter.domain.Message;

public class ResponseFormatter {

	private static final PrettyTime FORMATTER = new PrettyTime();
	static {
		FORMATTER.removeUnit(JustNow.class);
		FORMATTER.removeUnit(Millisecond.class);
	}

	public static String getEmptyReponse() {
		return "";
	}

	public static String formatReadResponse(List<Message> messages) {
		StringBuffer buffer = new StringBuffer();
		messages.stream().forEach(e -> buffer.append(e.getMessage()).append(" ( ")
						.append(FORMATTER.format(e.getCreationDate()))
						.append(" )").append(System.lineSeparator()));
		return buffer.toString();
	}

	public static String formatWallResponse(List<Message> messages) {
		StringBuffer buffer = new StringBuffer();
		
		messages.stream().forEach(e -> buffer.append(e.getOwner().getUserName())
						.append(" - ").append(e.getMessage()).append(" ( ")
						.append(FORMATTER.format(e.getCreationDate()))
						.append(" )").append(System.lineSeparator()));

		return buffer.toString();
	}
}
