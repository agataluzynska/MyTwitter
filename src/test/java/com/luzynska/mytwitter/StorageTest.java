package com.luzynska.mytwitter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.luzynska.mytwitter.domain.Message;
import com.luzynska.mytwitter.domain.User;

public class StorageTest {

	private Storage storage;

	@Before
	public void before() {
		storage = Storage.getInstance();
	}

	@After
	public void after() {
		storage.clear();
	}

	@Test
	public void testAddMessageForNewUser() {
		// GIVEN
		String userName = "user";
		assertTrue(storage.keySet().isEmpty());

		// WHEN
		storage.addMessage(userName, "message");

		// THEN
		assertNotNull(storage.get(userName));
	}

	@Test
	public void testAddMessageForExistingUser() {
		// GIVEN
		String userName = "user";
		storage.addMessage(userName, "message");
		User user = storage.get(userName);
		assertNotNull(user);

		// WHEN
		storage.addMessage(userName, "message2");

		// THEN
		assertEquals("message", user.getMessages().get(0).getMessage());
		assertEquals("message2", user.getMessages().get(1).getMessage());
	}

	@Test
	public void testFollowSuccess() {
		// GIVEN
		String userName = "user";
		storage.addMessage(userName, "message");
		User user = storage.get(userName);
		assertNotNull(user);

		String followeeUserName = "user2";
		storage.addMessage(followeeUserName, "message2");
		User followee = storage.get(followeeUserName);
		assertNotNull(followee);
		assertEquals(0, storage.get(userName).getFollowees().size());
		assertEquals(0, storage.get(followeeUserName).getFollowees().size());

		// WHEN
		storage.follow(userName, followeeUserName);

		// THEN
		assertEquals(1, storage.get(userName).getFollowees().size());
		assertEquals(0, storage.get(followeeUserName).getFollowees().size());
	}

	@Test
	public void testFollowUserTwice() {
		// GIVEN
		String userName = "user";
		storage.addMessage(userName, "message");
		User user = storage.get(userName);
		assertNotNull(user);

		String followeeUserName = "user2";
		storage.addMessage(followeeUserName, "message2");
		User followee = storage.get(followeeUserName);
		assertNotNull(followee);
		assertEquals(0, storage.get(userName).getFollowees().size());
		assertEquals(0, storage.get(followeeUserName).getFollowees().size());

		// WHEN
		storage.follow(userName, followeeUserName);
		storage.follow(userName, followeeUserName);

		// THEN
		assertEquals(1, storage.get(userName).getFollowees().size());
		assertEquals(0, storage.get(followeeUserName).getFollowees().size());
	}

	@Test
	public void testFollowYourself() {
		// GIVEN
		String userName = "user";
		storage.addMessage(userName, "message");
		User user = storage.get(userName);
		assertNotNull(user);
		assertEquals(0, storage.get(userName).getFollowees().size());

		// WHEN
		storage.follow(userName, userName);

		// THEN
		assertEquals(0, storage.get(userName).getFollowees().size());
	}

	@Test
	public void testFollowUnexistingUser() {
		// GIVEN
		String userName = "user";
		storage.addMessage(userName, "message");
		User user = storage.get(userName);
		assertNotNull(user);
		assertEquals(0, storage.get(userName).getFollowees().size());

		// WHEN
		storage.follow(userName, "unexistingUser");

		// THEN
		assertEquals(0, storage.get(userName).getFollowees().size());
	}

	@Test
	public void testReadUnexisitngUser() {
		// GIVEN
		String userName = "user";
		assertTrue(storage.keySet().isEmpty());

		// WHEN
		List<Message> result = storage.read(userName);

		// THEN
		assertTrue(result.isEmpty());
		assertTrue(storage.keySet().isEmpty());
	}

	@Test
	public void testReadExisitngUser() {
		// GIVEN
		String userName = "user";
		assertTrue(storage.keySet().isEmpty());
		storage.addMessage(userName, "message");
		assertNotNull(storage.get(userName));

		// WHEN
		List<Message> result = storage.read(userName);

		// THEN
		assertEquals(1, result.size());
		assertEquals("message", result.get(0).getMessage());
	}

	@Test
	public void testReadExisitngUserWithNullMessage() {
		// GIVEN
		String userName = "user";
		assertTrue(storage.keySet().isEmpty());
		storage.addMessage(userName, null);
		assertNotNull(storage.get(userName));

		// WHEN
		List<Message> result = storage.read(userName);

		// THEN
		assertEquals(1, result.size());
		assertNull(result.get(0).getMessage());
	}

	@Test
	public void testWallExisitngUser() {
		// GIVEN
		String userName = "user";
		storage.addMessage(userName, "message");
		User user = storage.get(userName);
		assertNotNull(user);

		String followeeUserName = "user2";
		storage.addMessage(followeeUserName, "message2");
		User followee = storage.get(followeeUserName);
		assertNotNull(followee);
		storage.follow(userName, followeeUserName);
		assertTrue(storage.get(userName).getFollowees().size() == 1);

		// WHEN
		List<Message> result = storage.readWall(userName);

		// THEN
		assertEquals(2, result.size());
	}

}
