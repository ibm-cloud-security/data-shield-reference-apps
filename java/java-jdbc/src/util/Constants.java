package util;

public class Constants {

	public static final String PROPERTIES_FILE_NAME = "Application.properties";
	public static final String DB_USER_PROPERTY_NAME = "DB_USER";
	public static final String DB_PASSWORD_PROPERTY_NAME = "DB_PASSWORD";
	public static final String DB_URL_PROPERTY_NAME = "DB_URL";
	public static final String DB_DRIVER = "DB_DRIVER";
	public static final String NUMBER_OF_TRANSACTIONS = "TRANSACTIONS";
	
	//Queries
	public static final String DATABASE_NAME = "TEST2";
	public static final String CREATE_TEST_DATABASE = "CREATE DATABASE "+DATABASE_NAME;
	public static final String USE_TEST_DATABASE = "USE "+DATABASE_NAME;
	public static final String DROP_TEST_DB = "DROP DATABASE "+DATABASE_NAME;
	public static final String TABLE_NAME = "TEST_TABLE";
	public static final String SELECT_ALL = "SELECT * FROM "+TABLE_NAME;
	public static final String SELECT_USER_BY_ID = "SELECT * FROM "+TABLE_NAME+" WHERE ID = ? ";
	public static final String INSERT_USER = "INSERT INTO "+TABLE_NAME+" (NAME, LAST_NAME, SSN, DOB, ABOUT_ME) VALUES (?, ?,?,?,?)";
	public static final String DELETE_ALL = "DELETE FROM "+TABLE_NAME;
	public static final String DELETE_USER = "DELETE FROM "+TABLE_NAME+" WHERE ID = ? ";
	public static final String UPDATE_USER = "UPDATE "+TABLE_NAME+" SET NAME = ?, LAST_NAME = ?,  SSN = ?, DOB = ?, ABOUT_ME = ? WHERE ID = ?";
	public static final String CREATE_TEST_TABLE = "CREATE TEMPORARY TABLE "+TABLE_NAME+" (ID INT AUTO_INCREMENT PRIMARY KEY, NAME VARCHAR(50), LAST_NAME VARCHAR(50), SSN VARCHAR(9), DOB DATE, ABOUT_ME BLOB)";
	public static final String DROP_TEST_TABLE = "DROP TABLE "+TABLE_NAME;
	public static final String SELECT_ABOUT_MES = "SELECT ABOUT_ME FROM "+TABLE_NAME;
	public static final String SELECT_DOBS = "SELECT DOB FROM "+TABLE_NAME;
	public static final String SELECT_IDS = "SELECT ID FROM "+TABLE_NAME;
	public static final String STORED_PROCEDURE_CALL = "CALL getAllUsers ()";
	public static final String STORED_PROCEDURE_CREATE = "CREATE PROCEDURE getAllUsers () BEGIN SELECT * from TEST_TABLE; END";
	
}
