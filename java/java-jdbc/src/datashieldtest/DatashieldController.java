package datashieldtest;

import java.sql.Connection;
import java.util.List;

import database.DataAccessObject;
import objects.User;
import com.google.gson.Gson;

public class DatashieldController {

	final static Gson gson = new Gson();

	public static String getAll(Connection con) {

		final List<User> users = DataAccessObject.fetchAllUsers(con);
		String json = gson.toJson(users);
		return json;

	}

	public static String getUser(Connection con, int id) {

		final User user = DataAccessObject.fetchUser(con, id);
		String json = gson.toJson(user);
		return json;
	}

	public static boolean insertUser(Connection con, User user) {

		boolean result = DataAccessObject.insertUser(con, user);
		return result;
	}

	public static boolean deleteAll(Connection con) {

		boolean result = DataAccessObject.deleteAllUsers(con);
		return result;
	}

	public static boolean deleteUser(Connection con, int id) {

		boolean result = DataAccessObject.deleteUser(con, id);
		return result;

	}

}
