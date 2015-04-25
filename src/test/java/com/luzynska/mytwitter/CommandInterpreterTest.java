package com.luzynska.mytwitter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import com.luzynska.mytwitter.command.Command;
import com.luzynska.mytwitter.command.FollowCommand;
import com.luzynska.mytwitter.command.PostCommand;
import com.luzynska.mytwitter.command.ReadCommand;
import com.luzynska.mytwitter.command.WallCommand;

@RunWith(PowerMockRunner.class)
@SuppressStaticInitializationFor("org.luzynski.twitter.CommandInterpreter")
@PrepareForTest({CommandInterpreter.class})
public class CommandInterpreterTest {

	private static final String MESSAGE = "message";
	
	private CommandInterpreter commandInterpreter = new CommandInterpreter();
	private Set<Command> commands = new HashSet<Command>();

	@Mock
	private FollowCommand followCommand;
	
	@Mock
	private PostCommand postCommand;
	
	@Mock
	private ReadCommand readCommand;
	
	@Mock
	private WallCommand wallCommand;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		when(followCommand.getRegex()).thenReturn(new FollowCommand().getRegex());
		commands.add(followCommand);
		when(postCommand.getRegex()).thenReturn(new PostCommand().getRegex());
		commands.add(postCommand);
		when(readCommand.getRegex()).thenReturn(new ReadCommand().getRegex());
		commands.add(readCommand);
		when(wallCommand.getRegex()).thenReturn(new WallCommand().getRegex());
		commands.add(wallCommand);
		Whitebox.setInternalState(CommandInterpreter.class, commands);
	}
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		 
	}
		  
	@Test
	public void testSuccessForPost() {
		// GIVEN
		String line = "a -> b";

		// WHEN
		commandInterpreter.interpreteCommand(line);

		// THEN
		verify(postCommand).execute(line);
	}

	@Test
	public void testSuccessForFollows() {
		// GIVEN
		String line = "a follows b";

		// WHEN
		commandInterpreter.interpreteCommand(line);

		// THEN
		verify(followCommand).equals(line);
	}

	@Test
	public void testSuccessForRead() {
		// GIVEN
		String line = "a";
		when(readCommand.execute(line)).thenReturn(MESSAGE);

		// WHEN
		String result = commandInterpreter.interpreteCommand(line);

		// THEN
		assertNotNull(result);
		assertEquals(MESSAGE, result);
		verify(readCommand).execute(line);
	}

	@Test
	public void testSuccessForWall() {
		// GIVEN
		String line = "a wall";
		when(wallCommand.execute(line)).thenReturn(MESSAGE);
		
		// WHEN
		String result = commandInterpreter.interpreteCommand(line);

		// THEN
		assertNotNull(result);
		assertEquals(MESSAGE, result);
		verify(wallCommand).execute(line);
	}

	@Test
	public void testFailureWithWrongNameInPost() {
		// GIVEN
		String line = "a~t -> message";

		// WHEN
		String result = commandInterpreter.interpreteCommand(line);

		// THEN
		assertEquals("", result);
		verifyNoCommandMockWasCalled();
	}

	@Test
	public void testFailureWithWrongNameInFollow() {
		// GIVEN
		String line = "a~t follows message";

		// WHEN
		String result = commandInterpreter.interpreteCommand(line);

		// THEN
		assertEquals("", result);
		verifyNoCommandMockWasCalled();
	}

	@Test
	public void testFailureWithWrongNameInRead() {
		// GIVEN
		String line = "a~t";

		// WHEN
		String result = commandInterpreter.interpreteCommand(line);

		// THEN
		assertEquals("", result);
		verifyNoCommandMockWasCalled();
	}

	@Test
	public void testFailureWithWrongNameInWall() {
		// GIVEN
		String line = "a~t wall";

		// WHEN
		String result = commandInterpreter.interpreteCommand(line);

		// THEN
		assertEquals("", result);
		verifyNoCommandMockWasCalled();
	}

	@Test
	public void testSuccessNameWithSpaces() {
		// GIVEN
		String line = "  anna  ";
		when(readCommand.execute(line.trim())).thenReturn(MESSAGE);
		
		// WHEN
		String result = commandInterpreter.interpreteCommand(line);

		// THEN
		assertNotNull(result);
		verify(readCommand).execute(line.trim());
	}

	@Test
	public void test3Follows() {
		// GIVEN
		String line = "follows follows follows";

		// WHEN
		commandInterpreter.interpreteCommand(line);

		// THEN
		verify(followCommand).execute(line);
	}

	@Test
	public void testFailureWithBrokenArrow() {
		// GIVEN
		String line = "a - > b";

		// WHEN
		String result = commandInterpreter.interpreteCommand(line);

		// THEN
		assertEquals("", result);
		verifyNoCommandMockWasCalled();
	}
	
	private void verifyNoCommandMockWasCalled() {
		verify(postCommand, times(0)).execute(any(String.class));
		verify(followCommand, times(0)).execute(any(String.class));
		verify(readCommand, times(0)).execute(any(String.class));
		verify(wallCommand, times(0)).execute(any(String.class));
	}
}
