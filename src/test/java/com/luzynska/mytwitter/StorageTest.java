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

	private final static String USERNAME = "user";
	private final static String FOLLOWEE_NAME = "user2";
	private final static String MESSAGE = "message";
	private final static String MESSAGE_2 = "message2";
	
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
		assertTrue(storage.keySet().isEmpty());

		// WHEN
		storage.addMessage(USERNAME, MESSAGE);

		// THEN
		assertNotNull(storage.get(USERNAME));
	}

	@Test
	public void testAddMessageForExistingUser() {
		// GIVEN
		User user = createUser(USERNAME);
		assertNotNull(user);

		// WHEN
		storage.addMessage(USERNAME, MESSAGE_2);

		// THEN
		assertEquals(MESSAGE, user.getMessages().get(0).getMessage());
		assertEquals(MESSAGE_2, user.getMessages().get(1).getMessage());
	}

	@Test
	public void testFollowSuccess() {
		// GIVEN
		User user = createUser(USERNAME);
		assertNotNull(user);
		assertEquals(0, user.getFollowees().size());
		
		User followee = createUser(FOLLOWEE_NAME);
		assertNotNull(followee);
		assertEquals(0, followee.getFollowees().size());

		// WHEN
		storage.follow(USERNAME, FOLLOWEE_NAME);

		// THEN
		assertEquals(1, user.getFollowees().size());
		assertEquals(0, followee.getFollowees().size());
	}

	@Test
	public void testFollowUserTwice() {
		// GIVEN
		User user = createUser(USERNAME);
		assertNotNull(user);
		assertEquals(0, user.getFollowees().size());
		
		User followee = createUser(FOLLOWEE_NAME);
		assertNotNull(followee);
		assertEquals(0, followee.getFollowees().size());

		// WHEN
		storage.follow(USERNAME, FOLLOWEE_NAME);
		storage.follow(USERNAME, FOLLOWEE_NAME);

		// THEN
		assertEquals(1, user.getFollowees().size());
		assertEquals(0, followee.getFollowees().size());
	}

	@Test
	public void testFollowYourself() {
		// GIVEN
		User user = createUser(USERNAME);
		assertNotNull(user);	
		assertEquals(0, storage.get(USERNAME).getFollowees().size());

		// WHEN
		storage.follow(USERNAME, USERNAME);

		// THEN
		assertEquals(0, user.getFollowees().size());
	}

	@Test
	public void testFollowUnexistingUser() {
		// GIVEN
		User user = createUser(USERNAME);
		assertNotNull(user);
		assertEquals(0, user.getFollowees().size());

		// WHEN
		storage.follow(USERNAME, "unexistingUser");

		// THEN
		assertEquals(0, user.getFollowees().size());
	}

	@Test
	public void testReadUnexisitngUser() {
		// GIVEN
		assertTrue(storage.keySet().isEmpty());

		// WHEN
		List<Message> result = storage.read(USERNAME);

		// THEN
		assertTrue(result.isEmpty());
		assertTrue(storage.keySet().isEmpty());
	}

	@Test
	public void testReadExisitngUser() {
		// GIVEN
		assertTrue(storage.keySet().isEmpty());
		User user = createUser(USERNAME);
		assertNotNull(user);

		// WHEN
		List<Message> result = storage.read(USERNAME);

		// THEN
		assertEquals(1, result.size());
		assertEquals("message", result.get(0).getMessage());
	}

	@Test
	public void testReadExisitngUserWithNullMessage() {
		// GIVEN
		assertTrue(storage.keySet().isEmpty());
		storage.addMessage(USERNAME, null);
		assertNotNull(storage.get(USERNAME));

		// WHEN
		List<Message> result = storage.read(USERNAME);

		// THEN
		assertEquals(1, result.size());
		assertNull(result.get(0).getMessage());
	}

	@Test
	public void testWallExisitngUser() {
		// GIVEN
		User user = createUser(USERNAME);
		assertNotNull(user);

		User followee = createUser(FOLLOWEE_NAME);
		assertNotNull(followee);
		
		storage.follow(USERNAME, FOLLOWEE_NAME);
		assertTrue(user.getFollowees().size() == 1);

		// WHEN
		List<Message> result = storage.readWall(USERNAME);

		// THEN
		assertEquals(2, result.size());
	}
	
	private User createUser(String userName) {
		if (!storage.containsKey(userName)) {
			storage.addMessage(userName, MESSAGE);
		}
		return storage.get(userName);
	}
}
