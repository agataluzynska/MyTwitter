package com.luzynska.mytwitter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MyTwitterApp {

	public static void main(String[] args) throws IOException {
		
		CommandInterpreter commandInterpreter = new CommandInterpreter();
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		
		while (true) {
			System.out.print("> ");
			String line = bufferedReader.readLine().trim();
			System.out.print(commandInterpreter.interpreteCommand(line));
		}

	}

}
