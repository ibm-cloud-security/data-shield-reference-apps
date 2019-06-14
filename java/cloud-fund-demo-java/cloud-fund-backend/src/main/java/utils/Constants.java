package utils;

public class Constants {
	
	// JMS Constants
	public static final String BILLING_SERVICE = "billingService";
	public static final String JMS_FACTORY = "myFactory";
	
	// Crypto Constants
	public static final String ENCRYPTION_KEY_ENV = "ENCRYPTION_KEY";
	public static final String SALT_VALUE_ENV = "SALT_VALUE";
	public static final String FACTORY_KEY_ALGORITHM = "PBKDF2WithHmacSHA256";
	public static final String KEY_SPEC_ALGORITHM = "AES";
	public static final String CIPHER_TRANSFORMATION_TYPE = "AES/ECB/PKCS5Padding";
	
	// DB
	public static final String PENDING_PROCESS = "PENDING_PROCESS";
	public static final String PROCESSED = "PROCESSED";
	

}
