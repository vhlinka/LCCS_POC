package oh.lccs.portal.requestfund.service.db;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

/**
 * This object returns List of data objects.
 * 
 * @author vinodh.srinivasan@compuware.com
 *
 */
public class RequestFundMybatisDML {

  
  public List<Map<String, Object>> performSawisSelect(final String daoMethodName, final Map<String, Object> parameters)  {
	  SqlSession session = null;
	  List<Map<String, Object>> results = null;
		session = SacwisConnectionFactory.getSession();
		results = session.selectList(daoMethodName,parameters);
	  return results;
  }
  
	
  public List<Map<String, Object>> performLCCSSelect(final String daoMethodName, final Map<String, Object> parameters)  {
	  SqlSession session = null;
	  List<Map<String, Object>> results = null;
		session = LCCSConnectionFactory.getSession();
		results = session.selectList(daoMethodName,parameters);
	  return results;
  }

  
  public int performLCCSInsert(final String daoMethodName, final Object object)  {
	  SqlSession session = null;
	  int results = 0;
	  try{
		session = LCCSConnectionFactory.getSession();
		try {
			results = session.insert(daoMethodName,object);
			session.commit();
		} catch (Exception e) {
			//TODO: fix the log
			e.printStackTrace();
			session.rollback();
		}
	  }finally{
		  LCCSConnectionFactory.closeSession(session);
	  }
	  return results;
  }
  
  public int performLCCSUpdate(final String daoMethodName, final Object object)  {
	  SqlSession session = null;
	  int results = 0;
	  try{
		session = LCCSConnectionFactory.getSession();
		try {
			results = session.update(daoMethodName,object);
			session.commit();
		} catch (Exception e) {
			//TODO: fix the log
			e.printStackTrace();
			session.rollback();
		}
	  }finally{
		  LCCSConnectionFactory.closeSession(session);
	  }
	  return results;
  }
}
