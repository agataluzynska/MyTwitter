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
public class WallCommandTest {

	private WallCommand wallCommand = new WallCommand();

	@Mock
	private Storage storage;

	@Before
	public void beforeSetUp() {
		PowerMockito.mockStatic(Storage.class);
		when(Storage.getInstance()).thenReturn(storage);
	}

	@Test
	public void testSuccess() {
		// GIVEM
		String line = "a wall";

		// WHEN
		String result = wallCommand.execute(line);

		// THEN
		assertNotNull(result);
		assertTrue(result.isEmpty());
		verify(storage).readWall("a");
	}

	@Test
	public void testSuccessWithDoubleWall() {
		// GIVEM
		String line = "wall wall";

		// WHEN
		String result = wallCommand.execute(line);

		// THEN
		assertNotNull(result);
		assertTrue(result.isEmpty());
		verify(storage).readWall("wall");
	}

}
