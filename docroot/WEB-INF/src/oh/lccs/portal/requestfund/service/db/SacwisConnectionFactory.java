package oh.lccs.portal.requestfund.service.db;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/**
 * Represents Mybatis connection factory.
 * 
 * @author vinodh.srinivasan@compuware.com
 *
 */
public class SacwisConnectionFactory {
	
	private static SqlSessionFactory sessionFactory = loadMapper();
	
	private static SqlSessionFactory loadMapper(){
	    try{
	    	 InputStream inputStream = null;
	    	 Properties properties = new Properties();
	    	 
				try {
					inputStream = Resources.getResourceAsStream("oh/lccs/portal/requestfund/service/db/config/sacwis-mybatis-configuration.xml");
					File configDir = new File(System.getProperty("catalina.base"), "conf");
					File configFile = new File(configDir, "sacwis-jdbc.properties");
					InputStream stream = new FileInputStream(configFile);
					properties.load(stream);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
	    	 return new SqlSessionFactoryBuilder().build(inputStream,properties);
       }catch(Exception e){
           //TODO: Need to handle it.
    	   e.printStackTrace();
       }
		return null;
	}
	 
	    public static SqlSession getSession(){
	    	 if (sessionFactory == null) {
				throw new RuntimeException("Mybatis configuration Sql mapper was null");
			}
			return sessionFactory.openSession();
	    }
	    
	    public static void closeSession(SqlSession session) {
	    	if(session != null){
	    		session.close();
	    	}
	      }
}
