package util;

import java.io.IOException;
import java.util.Properties;

public class Utils {
	
	private static Properties properties;

	public static Properties getProperties() {
		
		if(properties != null) {
			return properties;
		}
		
		properties = new Properties();
		try {
		    //load a properties file from class path, inside static method
			properties.load(Utils.class.getClassLoader().getResourceAsStream(Constants.PROPERTIES_FILE_NAME));

		} 
		catch (IOException ex) {
		    ex.printStackTrace();
		}
		
		return properties;
	}

}
