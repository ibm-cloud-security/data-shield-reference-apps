package database;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
//import java.util.Properties;

import objects.User;
import util.Constants;
//import util.Utils;

public class DataAccessObject {

	public static Connection getConnection() {
		Connection conn = null;
//		Properties properties = Utils.getProperties();
		try {
			System.out.println("DataAccessObject.getConnection():Connecting to database...");
//			final String USER = properties.getProperty(Constants.DB_USER_PROPERTY_NAME);
//			final String PASSWORD = properties.getProperty(Constants.DB_PASSWORD_PROPERTY_NAME);
//			final String DB_URL = properties.getProperty(Constants.DB_URL_PROPERTY_NAME);
//			final String DB_DRIVER = properties.getProperty(Constants.DB_DRIVER);
			final String USER = System.getenv(Constants.DB_USER_PROPERTY_NAME);
			final String PASSWORD = System.getenv(Constants.DB_PASSWORD_PROPERTY_NAME);
			final String DB_URL = System.getenv(Constants.DB_URL_PROPERTY_NAME);
			final String DB_DRIVER = System.getenv(Constants.DB_DRIVER);
			Class.forName(DB_DRIVER);
			System.out.println("DataAccessObject.getConnection(): "+USER+" "+DB_URL);
			conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
			System.out.println("DataAccessObject.getConnection():Connected to "+DB_URL+" with user "+USER);

		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return conn;
	}
	
	public static boolean executeSQLStatement(Connection con, String sqlQuery) {
		
		System.out.println("DataAccessObject.executeSQLStatement()");
		boolean result = true;
		
		try (Statement stmt = con.createStatement();) {
			
			System.out.println("DataAccessObject.executeSQLStatement(): Executing: "+sqlQuery);
			stmt.execute(sqlQuery);

		} catch (SQLException e) {
			result = false;
			e.printStackTrace();
		}

		return result;
		
	}

	public static List<User> fetchAllUsers(Connection con) {
		
		System.out.println("DataAccessObject.fetchAllUsers()");

		List<User> users = new ArrayList<User>();

		System.out.println("DataAccessObject.fetchAllUsers(): Executing: "+Constants.SELECT_ALL);
		try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(Constants.SELECT_ALL)) {

			while (rs.next()) {
				User user = new User();
				final int id = rs.getInt("ID");
				final String name = rs.getString("NAME");
				final String lastName = rs.getString("LAST_NAME");
				final String ssn = rs.getString("SSN");
				final Date dob = rs.getDate("DOB");
				final String aboutMe = getStringFromBlob(rs.getBlob("ABOUT_ME"));

				user.setId(id);
				user.setName(name);
				user.setLastName(lastName);
				user.setSsn(ssn);
				user.setDob(dob);
				user.setAboutMe(aboutMe);

				users.add(user);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return users;

	}

	public static User fetchUser(Connection con, int idToFecth) {
		
		System.out.println("DataAccessObject.fetchUser()");

		User user = null;

		try (Statement stmt = con.createStatement();
				PreparedStatement preparedStatement = con.prepareStatement(Constants.SELECT_USER_BY_ID);) {

			preparedStatement.setInt(1, idToFecth);
			System.out.println("DataAccessObject.fetchUser(): Executing: "+preparedStatement);
			
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				user = new User();
				final int id = rs.getInt("ID");
				final String name = rs.getString("NAME");
				final String lastName = rs.getString("LAST_NAME");
				final String ssn = rs.getString("SSN");
				final Date dob = rs.getDate("DOB");
				final String aboutMe = getStringFromBlob(rs.getBlob("ABOUT_ME"));

				user.setId(id);
				user.setName(name);
				user.setLastName(lastName);
				user.setSsn(ssn);
				user.setDob(dob);
				user.setAboutMe(aboutMe);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return user;
	}

	public static boolean insertUser(Connection con, User user) {
		
		//System.out.println("DataAccessObject.insertUser()");

		boolean result = true;

		try (Statement stmt = con.createStatement();
				PreparedStatement preparedStatement = con.prepareStatement(Constants.INSERT_USER);) {
			preparedStatement.setString(1, user.getName());
			preparedStatement.setString(2, user.getLastName());
			preparedStatement.setString(3, user.getSsn());
			preparedStatement.setDate(4, user.getDob());
			Blob blob = con.createBlob();
			blob.setBytes(1, user.getAboutMe().getBytes());
			preparedStatement.setBlob(5, blob);
			
			//System.out.println("DataAccessObject.insertUser(): Executing: "+preparedStatement);
			preparedStatement.execute();

		} catch (SQLException e) {
			result = false;
			e.printStackTrace();
		}

		return result;

	}

	public static boolean deleteAllUsers(Connection con) {
		
		System.out.println("DataAccessObject.deleteAllUsers()");

		boolean result = true;

		try (Statement stmt = con.createStatement();) {

			System.out.println("DataAccessObject.deleteAllUsers(): Executing: "+Constants.DELETE_ALL);
			stmt.execute(Constants.DELETE_ALL);

		} catch (SQLException e) {
			result = false;
			e.printStackTrace();
		}

		return result;
	}

	public static boolean deleteUser(Connection con, int id) {
		
		System.out.println("DataAccessObject.deleteUser()");

		boolean result = true;

		
		try (Statement stmt = con.createStatement();
				PreparedStatement preparedStatement = con.prepareStatement(Constants.DELETE_USER);) {
			preparedStatement.setInt(1, id);
			
			System.out.println("DataAccessObject.deleteUser(): Executing: "+preparedStatement);
			preparedStatement.execute();

		} catch (SQLException e) {
			result = false;
			e.printStackTrace();
		}

		return result;

	}

	public static boolean updateUser(Connection con, User user) {
		
		//System.out.println("DataAccessObject.updateUser()");

		boolean result = true;

		try (Statement stmt = con.createStatement();
				PreparedStatement preparedStatement = con.prepareStatement(Constants.UPDATE_USER);) {
			preparedStatement.setString(1, user.getName());
			preparedStatement.setString(2, user.getLastName());
			preparedStatement.setString(3, user.getSsn());
			preparedStatement.setDate(4, user.getDob());
			Blob blob = con.createBlob();
			blob.setBytes(1, user.getAboutMe().getBytes());
			preparedStatement.setBlob(5, blob);
			preparedStatement.setInt(6, user.getId());

			//System.out.println("DataAccessObject.updateUser(): Executing: "+preparedStatement);
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			result = false;
			e.printStackTrace();
		}

		return result;

	}
	
	//Blob
	public static List<String> fetchAboutMes(Connection con) {
		
		System.out.println("DataAccessObject.fetchAboutMes()");
		
		List<String> aboutMes = new ArrayList<String>();
		
		System.out.println("DataAccessObject.fetchAboutMes(): Executing: "+Constants.SELECT_ABOUT_MES);
		try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(Constants.SELECT_ABOUT_MES)) {
			
			while (rs.next()) {
				final String aboutMe = getStringFromBlob(rs.getBlob("ABOUT_ME"));
				aboutMes.add(aboutMe);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return aboutMes;
		
	}
	
	//Date
	public static List<Date> fetchDobs(Connection con) {
		
		System.out.println("DataAccessObject.fetchDobs()");
		
		List<Date> dobs = new ArrayList<Date>();
		
		System.out.println("DataAccessObject.fetchDobs(): Executing: "+Constants.SELECT_DOBS);
		try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(Constants.SELECT_DOBS)) {
			
			while (rs.next()) {
				final Date dob = rs.getDate("DOB");
				dobs.add(dob);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return dobs;
		
	}
	
	
	public static List<Integer> fetchIDs(Connection con) {
		
		System.out.println("DataAccessObject.fetchIDs()");
		
		List<Integer> ids = new ArrayList<Integer>();
		
		System.out.println("DataAccessObject.fetchDobs(): Executing: "+Constants.SELECT_IDS);
		try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(Constants.SELECT_IDS)) {
			
			while (rs.next()) {
				final int id = rs.getInt("ID");
				ids.add(id);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return ids;
		
	}
	
	private static String getStringFromBlob(Blob blob) {
		
//		System.out.println("DataAccessObject.getStringFromBlob()");
		
		String aboutMe = "";
		try {
			aboutMe = new String(blob.getBytes(1l, (int) blob.length()));
			
		}catch(SQLException sqe) {
			sqe.printStackTrace();
		}
		return aboutMe;
	}
	
	public static List<User> fetchAllUsersUsingProcedure(Connection con) {
		
		System.out.println("DataAccessObject.fetchAllUsersUsingProcedure()");

		List<User> users = new ArrayList<User>();

		System.out.println("DataAccessObject.fetchAllUsersUsingProcedure(): Executing:"+ Constants.STORED_PROCEDURE_CALL);
		try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(Constants.STORED_PROCEDURE_CALL)) {

			while (rs.next()) {
				User user = new User();
				final int id = rs.getInt("ID");
				final String name = rs.getString("NAME");
				final String lastName = rs.getString("LAST_NAME");
				final String ssn = rs.getString("SSN");
				final Date dob = rs.getDate("DOB");
				final String aboutMe = getStringFromBlob(rs.getBlob("ABOUT_ME"));

				user.setId(id);
				user.setName(name);
				user.setLastName(lastName);
				user.setSsn(ssn);
				user.setDob(dob);
				user.setAboutMe(aboutMe);

				users.add(user);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return users;

	}

}
