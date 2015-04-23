package com.luzynska.mytwitter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class CommandInterpreterTest {

	private CommandInterpreter commandInterpreter = new CommandInterpreter();

	@Test
	public void testSuccessForPost() {
		// GIVEN
		String line = "a -> b";

		// WHEN
		String result = commandInterpreter.interpreteCommand(line);

		// THEN
		assertNotNull(result);
	}

	@Test
	public void testSuccessForFollows() {
		// GIVEN
		String line = "a follows b";

		// WHEN
		String result = commandInterpreter.interpreteCommand(line);

		// THEN
		assertNotNull(result);
	}

	@Test
	public void testSuccessForRead() {
		// GIVEN
		String line = "a";

		// WHEN
		String result = commandInterpreter.interpreteCommand(line);

		// THEN
		assertNotNull(result);
	}

	@Test
	public void testSuccessForWall() {
		// GIVEN
		String line = "a wall";

		// WHEN
		String result = commandInterpreter.interpreteCommand(line);

		// THEN
		assertNotNull(result);
	}

	@Test
	public void testFailureWithWrongNameInPost() {
		// GIVEN
		String line = "a~t -> message";

		// WHEN
		String result = commandInterpreter.interpreteCommand(line);

		// THEN
		assertEquals("", result);
	}

	@Test
	public void testFailureWithWrongNameInFollow() {
		// GIVEN
		String line = "a~t follows message";

		// WHEN
		String result = commandInterpreter.interpreteCommand(line);

		// THEN
		assertEquals("", result);
	}

	@Test
	public void testFailureWithWrongNameInRead() {
		// GIVEN
		String line = "a~t";

		// WHEN
		String result = commandInterpreter.interpreteCommand(line);

		// THEN
		assertEquals("", result);
	}

	@Test
	public void testFailureWithWrongNameInWall() {
		// GIVEN
		String line = "a~t wall";

		// WHEN
		String result = commandInterpreter.interpreteCommand(line);

		// THEN
		assertEquals("", result);
	}

	@Test
	public void testSuccessNameWithSpaces() {
		// GIVEN
		String line = "  anna  ";

		// WHEN
		String result = commandInterpreter.interpreteCommand(line);

		// THEN
		assertNotNull(result);
	}

	@Test
	public void test3Follows() {
		// GIVEN
		String line = "follows follows follows";

		// WHEN
		String result = commandInterpreter.interpreteCommand(line);

		// THEN
		assertNotNull(result);
	}

	@Test
	public void testFailureWithBrokenArrow() {
		// GIVEN
		String line = "a - > b";

		// WHEN
		String result = commandInterpreter.interpreteCommand(line);

		// THEN
		assertEquals("", result);
	}
}
