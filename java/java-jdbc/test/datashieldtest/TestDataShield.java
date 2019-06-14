package datashieldtest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import database.DataAccessObject;
import objects.User;
import util.Constants;

public class TestDataShield {

	final static int NUMBER_OF_TRANSACTIONS = Integer.parseInt(System.getenv(Constants.NUMBER_OF_TRANSACTIONS));
	Connection con = null;
	private String fileText = "";

	@BeforeEach
	void setUp() throws Exception {
		System.out.println();
		System.out.println("*Starting Test*");
		System.out.println("*Setup*");
		con = DataAccessObject.getConnection();
		// Creating test table
		DataAccessObject.executeSQLStatement(con, Constants.CREATE_TEST_DATABASE);
		DataAccessObject.executeSQLStatement(con, Constants.USE_TEST_DATABASE);
		DataAccessObject.executeSQLStatement(con, Constants.CREATE_TEST_TABLE);
		fileText = readBlobFile();
	}

	@Test
	void testInsertUsers() {

		System.out.println("*testInsertUsers*");
		long startTime = System.currentTimeMillis();
		System.out.println("Start time (ms): " + startTime);

		System.out.println("Inserting " + NUMBER_OF_TRANSACTIONS + " users");

		for (int i = 0; i < NUMBER_OF_TRANSACTIONS; i++) {
			User user = new User();
			user.setName("John" + i);
			user.setLastName("Wilson" + i);
			user.setSsn("1234" + i);
			Date date = Date.valueOf("1982-07-24");
			user.setDob(date);
			user.setAboutMe(fileText + i);
			final boolean resultInsert = DataAccessObject.insertUser(con, user);
			assertTrue(resultInsert);
		}

		System.out.println("Retrieving users from DB");
		List<User> retrievedUsers = new ArrayList<User>();
		retrievedUsers = DataAccessObject.fetchAllUsers(con);

		long endTime = System.currentTimeMillis();
		System.out.println("End time (ms): " + endTime);
		System.out.println("Elapsed time (Seconds):" + (float) ((endTime - startTime) / 1000));

		System.out.println("Asserting number of users");
		assertEquals(NUMBER_OF_TRANSACTIONS, retrievedUsers.size());

	}

	@Test
	void testUpdateUsers() {

		System.out.println("*testUpdateUsers*");
		long startTime = System.currentTimeMillis();
		System.out.println("Start time (ms): " + startTime);

		System.out.println("Updating " + NUMBER_OF_TRANSACTIONS + " users");

		for (int i = 0; i < NUMBER_OF_TRANSACTIONS; i++) {
			User user = new User();
			user.setName("John" + i);
			user.setLastName("Wilson" + i);
			user.setSsn("123" + i);
			Date date = Date.valueOf("1982-07-24");
			user.setDob(date);
			user.setAboutMe(fileText + i);
			final boolean resultInsert = DataAccessObject.insertUser(con, user);
			assertTrue(resultInsert);
		}

		List<User> retrievedUsers = new ArrayList<User>();
		System.out.println("Retrieving users from DB");
		retrievedUsers = DataAccessObject.fetchAllUsers(con);

		System.out.println("Updating users");
		int j = 0;
		for (User user : retrievedUsers) {
			String newSSN = "234" + j;
			j++;
			user.setSsn(newSSN);
			final boolean resultUpdate = DataAccessObject.updateUser(con, user);
			assertTrue(resultUpdate);
		}

		System.out.println("Retrieving users from DB");
		retrievedUsers = DataAccessObject.fetchAllUsers(con);

		long endTime = System.currentTimeMillis();
		System.out.println("End time (ms): " + endTime);
		System.out.println("Elapsed time (Seconds):" + (float) ((endTime - startTime) / 1000));

		System.out.println("Asserting number of users");
		assertEquals(NUMBER_OF_TRANSACTIONS, retrievedUsers.size());

	}

	@Test
	void testSelectUser() {

		System.out.println("*testSelectUser*");
		long startTime = System.currentTimeMillis();
		System.out.println("Start time (ms): " + startTime);

		System.out.println("Inserting one user");
		User user = new User();
		user.setName("John");
		user.setLastName("Wilson");
		user.setSsn("123");
		Date date = Date.valueOf("1982-07-24");
		user.setDob(date);
		user.setAboutMe(fileText);
		System.out.println("Inserting one user");
		final boolean resultInsert = DataAccessObject.insertUser(con, user);
		assertTrue(resultInsert);

		System.out.println("Retrieving users from DB");
		List<User> retrievedUsers = new ArrayList<User>();
		retrievedUsers = DataAccessObject.fetchAllUsers(con);

		int id = -1;
		for (User retrievedUser : retrievedUsers) {
//			System.out.println(retrievedUser);
			id = retrievedUser.getId();
		}

		assertNotEquals(-1, id);
		System.out.println("Find user");
		User fetchedUser = DataAccessObject.fetchUser(con, id);

		long endTime = System.currentTimeMillis();
		System.out.println("End time (ms): " + endTime);
		System.out.println("Elapsed time (Seconds):" + (float) ((endTime - startTime) / 1000));

		System.out.println("Asserting user was found");
		assertNotNull(fetchedUser);

	}

	@Test
	void testDeleteUser() {

		System.out.println("*testDeleteUser*");
		long startTime = System.currentTimeMillis();
		System.out.println("Start time (ms): " + startTime);

		System.out.println("Inserting one user");
		User user = new User();
		user.setName("John");
		user.setLastName("Wilson");
		user.setSsn("123");
		Date date = Date.valueOf("1982-07-24");
		user.setDob(date);
		user.setAboutMe(fileText);
		final boolean resultInsert = DataAccessObject.insertUser(con, user);
		System.out.println("Asserting insertion didn't fail");
		assertTrue(resultInsert);

		System.out.println("Retrieving all users");
		List<User> retrievedUsers = new ArrayList<User>();
		retrievedUsers = DataAccessObject.fetchAllUsers(con);

		int id = -1;
		for (User retrievedUser : retrievedUsers) {
			id = retrievedUser.getId();
		}

		System.out.println("Asserting user is there");
		assertNotEquals(-1, id);
		boolean deleteUser = DataAccessObject.deleteUser(con, id);
		long endTime = System.currentTimeMillis();
		System.out.println("End time (ms): " + endTime);
		System.out.println("Elapsed time (Seconds):" + (float) ((endTime - startTime) / 1000));
		System.out.println("Asserting user was deleted");
		assertTrue(deleteUser);

	}

	@Test
	void testSelectBlobs() {

		System.out.println("*testSelectBlobs*");
		long startTime = System.currentTimeMillis();
		System.out.println("Start time (ms): " + startTime);

		System.out.println("Inserting " + NUMBER_OF_TRANSACTIONS + " users");
		for (int i = 0; i < NUMBER_OF_TRANSACTIONS; i++) {
			User user = new User();
			user.setName("John" + i);
			user.setLastName("Wilson" + i);
			user.setSsn("1234" + i);
			Date date = Date.valueOf("1982-07-24");
			user.setDob(date);
			user.setAboutMe(fileText + i);
			final boolean resultInsert = DataAccessObject.insertUser(con, user);
			assertTrue(resultInsert);
		}

		System.out.println("Retrieving all Blob fields");
		List<String> blobs = DataAccessObject.fetchAboutMes(con);

		long endTime = System.currentTimeMillis();
		System.out.println("End time (ms): " + endTime);
		System.out.println("Elapsed time (Seconds):" + (float) ((endTime - startTime) / 1000));

		System.out.println("Asserting number of Blob fields");
		assertNotNull(blobs);
		assertEquals(blobs.size(), NUMBER_OF_TRANSACTIONS);
	}

	@Test
	void testSelectDates() {

		System.out.println("*testSelectDates*");
		long startTime = System.currentTimeMillis();
		System.out.println("Start time (ms): " + startTime);

		System.out.println("Inserting " + NUMBER_OF_TRANSACTIONS + " users");
		for (int i = 0; i < NUMBER_OF_TRANSACTIONS; i++) {
			User user = new User();
			user.setName("John" + i);
			user.setLastName("Wilson" + i);
			user.setSsn("1234" + i);
			Date date = Date.valueOf("1982-07-24");
			user.setDob(date);
			user.setAboutMe(fileText + i);
			final boolean resultInsert = DataAccessObject.insertUser(con, user);
			assertTrue(resultInsert);
		}

		System.out.println("Retrieving all DOB fields");
		List<Date> dobs = DataAccessObject.fetchDobs(con);

		long endTime = System.currentTimeMillis();
		System.out.println("End time (ms): " + endTime);
		System.out.println("Elapsed time (Seconds):" + (float) ((endTime - startTime) / 1000));

		System.out.println("Asserting number of DOB fields");
		assertNotNull(dobs);
		assertEquals(dobs.size(), NUMBER_OF_TRANSACTIONS);
	}

	@Test
	void testSelectNumbers() {

		System.out.println("*testSelectNumbers*");
		long startTime = System.currentTimeMillis();
		System.out.println("Start time (ms): " + startTime);

		System.out.println("Inserting " + NUMBER_OF_TRANSACTIONS + " users");
		for (int i = 0; i < NUMBER_OF_TRANSACTIONS; i++) {
			User user = new User();
			user.setName("John" + i);
			user.setLastName("Wilson" + i);
			user.setSsn("1234" + i);
			Date date = Date.valueOf("1982-07-24");
			user.setDob(date);
			user.setAboutMe(fileText + i);
			final boolean resultInsert = DataAccessObject.insertUser(con, user);
			assertTrue(resultInsert);
		}

		System.out.println("Retrieving all ID fields");
		List<Integer> ids = DataAccessObject.fetchIDs(con);

		long endTime = System.currentTimeMillis();
		System.out.println("End time (ms): " + endTime);
		System.out.println("Elapsed time (Seconds):" + (float) ((endTime - startTime) / 1000));

		System.out.println("Asserting number of ID fields");
		assertNotNull(ids);
		assertEquals(ids.size(), NUMBER_OF_TRANSACTIONS);
	}

	@Test
	void testStoredProcedure() {

		System.out.println("*testStoredProcedure*");
		long startTime = System.currentTimeMillis();
		System.out.println("Start time (ms): " + startTime);

		// Creating stored procedure
		DataAccessObject.executeSQLStatement(con, Constants.STORED_PROCEDURE_CREATE);

		// Inserting a bunch of users
		System.out.println("Inserting " + NUMBER_OF_TRANSACTIONS + " users");
		for (int i = 0; i < NUMBER_OF_TRANSACTIONS; i++) {
			User user = new User();
			user.setName("John" + i);
			user.setLastName("Wilson" + i);
			user.setSsn("1234" + i);
			Date date = Date.valueOf("1982-07-24");
			user.setDob(date);
			user.setAboutMe(fileText + i);
			final boolean resultInsert = DataAccessObject.insertUser(con, user);
			assertTrue(resultInsert);
		}

		List<User> retrievedUsers = new ArrayList<User>();
		retrievedUsers = DataAccessObject.fetchAllUsersUsingProcedure(con);

		long endTime = System.currentTimeMillis();
		System.out.println("End time (ms): " + endTime);
		System.out.println("Elapsed time (Seconds):" + (float) ((endTime - startTime) / 1000));

		System.out.println("Asserting number of ID fields");
		assertNotNull(retrievedUsers);
		assertEquals(NUMBER_OF_TRANSACTIONS, retrievedUsers.size());
	}

//	@Test
//	void writeBlobFile() throws FileNotFoundException, UnsupportedEncodingException {
//		PrintWriter writer = new PrintWriter("blob.txt", "UTF-8");
//		for(int i=0; i<1000 ; i++) {
//			writer.print("This is a long test file.");
//		}
//		writer.close();
//	}

	private String readBlobFile() {
		final StringBuilder result = new StringBuilder("");
		final ClassLoader classLoader = getClass().getClassLoader();
		final File file = new File(classLoader.getResource("blob.txt").getFile());

		try (Scanner scanner = new Scanner(file)) {

			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				result.append(line).append("\n");
			}

			scanner.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return result.toString();
	}

	@AfterEach
	public void tearDown() {
		System.out.println("*Tear Down*");
		DataAccessObject.deleteAllUsers(con);
		DataAccessObject.executeSQLStatement(con, Constants.DROP_TEST_TABLE);
		DataAccessObject.executeSQLStatement(con, Constants.DROP_TEST_DB);
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}

/*
 * Run instance: docker run --name some-mysql -e
 * MYSQL_ROOT_PASSWORD=my-secret-pw -d mysql:tag
 * 
 * Connect to instance from command line docker run -it --link some-mysql:mysql
 * --rm mysql sh -c 'exec mysql -h"$MYSQL_PORT_3306_TCP_ADDR"
 * -P"$MYSQL_PORT_3306_TCP_PORT" -uroot -p"$MYSQL_ENV_MYSQL_ROOT_PASSWORD"'
 * 
 * Connect to instance from another container docker run --name some-app --link
 * some-mysql:mysql -d application-that-uses-mysql
 * 
 * Run docker app with mysql linked docker run --link some-mysql:mysql javaapp
 * 
 */