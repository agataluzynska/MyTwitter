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
public class ReadCommandTest {

	private ReadCommand readCommand = new ReadCommand();

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
		String line = "anna";

		// WHEN
		String result = readCommand.execute(line);

		// THEN
		assertNotNull(result);
		assertTrue(result.isEmpty());
		verify(storage).read(line);
	}

}
