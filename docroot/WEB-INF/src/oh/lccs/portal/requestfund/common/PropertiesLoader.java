package oh.lccs.portal.requestfund.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Loads lccs-portal.properties
 * 
 * @author vinodh.srinivasan@lochbridge.com
 *
 */
public class PropertiesLoader {
	
	private static Properties properties = propertiesLoader();
	
	private static Properties propertiesLoader() {
		
		Properties props = new Properties();
		
		try {
			File configDir = new File(System.getProperty("catalina.base"), "conf");
			File configFile = new File(configDir, LccsConstants.LCCS_PORTAL_PROPERTIES);
			InputStream stream = new FileInputStream(configFile);
			props.load(stream);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return props;
	}
	
	public static Properties getPropertiesInstance() {
    	
		return properties;
    }

}
