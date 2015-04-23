package com.luzynska.mytwitter.command;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;

import com.luzynska.mytwitter.Storage;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ Storage.class })
@SuppressStaticInitializationFor({ "com.luzynska.mytwitter.Storage" })
public class PostCommandTest {

	private PostCommand postCommand = new PostCommand();

	@Mock
	private Storage storage;

	@Before
	public void beforeSetUp() {
		PowerMockito.mockStatic(Storage.class);
		when(Storage.getInstance()).thenReturn(storage);
	}

	@Test
	public void testSuccessfullExecute() {
		// GIVEN
		String line = "a -> message";

		// WHEN
		String result = postCommand.execute(line);

		// THEN
		assertNotNull(result);
		assertTrue(result.isEmpty());
		verify(storage).addMessage("a", "message");
	}

	@Test(expected = IllegalStateException.class)
	public void testFailureBadArrow() {
		// GIVEN
		String line = "a - > message";

		// WHEN
		postCommand.execute(line);

		// THEN expects IllegalStateException
	}

	@Test(expected = IllegalStateException.class)
	public void testFailureOnlyArrow() {
		// GIVEN
		String line = "->";

		// WHEN
		postCommand.execute(line);

		// THEN expects IllegalStateException
	}

	@Test
	public void testNonLetterCharacters() {
		// GIVEN
		String line = "a -> ~$";

		// WHEN
		String result = postCommand.execute(line);

		// THEN
		assertNotNull(result);
		assertTrue(result.isEmpty());
		verify(storage).addMessage("a", "~$");
	}

}
