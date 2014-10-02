package oh.lccs.portal.requestfund.common;

import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oh.lccs.portal.requestfund.domain.UserProfile;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.util.PortalUtil;

public final class LiferayUtil {
    
    /** logger */
    private static final LiferayUtil INSTANCE = new LiferayUtil();
    
    public static LiferayUtil instance(){
        return INSTANCE;
    }
    
    private static final String CASE_SUPERVISOR_GROUP="casework_supervisor_group";
    private static final String CASE_MANAGER_GROUP="casework_manager_group";
    private static final String CASE_WORKER_GROUP="caseworker_group";
    private static final String CASE_FINACE_APPROVER_GROUP="fiscal_group";
    
    
    enum LCCSGroup{
    	CASE_SUPERVISOR,CASE_MANAGER,CASE_WORKER,CASE_FINACE_APPROVER,NO_GROUP;
    	
    }
    
    public UserProfile convertUserGroups(User user){
    	LCCSGroup lccsGroup= LCCSGroup.NO_GROUP;
    	UserProfile userProfile = new UserProfile();
    	if(user != null){
//			System.out.println("PortalUtil.isOmniadmin(user.getUserId()" + PortalUtil.isOmniadmin(user.getUserId()));
    		
	        List<UserGroup> userGroups;
			try {
				userGroups = user.getUserGroups();
	        if(userGroups != null){
	        	for (UserGroup userGroup : userGroups) {
					if(userGroup != null){
						if(CASE_SUPERVISOR_GROUP.equalsIgnoreCase(userGroup.getName())){
							lccsGroup = LCCSGroup.CASE_SUPERVISOR;
							userProfile.setSupervisor(true);
						}else if (CASE_MANAGER_GROUP.equalsIgnoreCase(userGroup.getName())){
							lccsGroup = LCCSGroup.CASE_MANAGER;
							userProfile.setManager(true);
						}else if (CASE_WORKER_GROUP.equalsIgnoreCase(userGroup.getName())){
							lccsGroup = LCCSGroup.CASE_WORKER;
							userProfile.setCaseWorker(true);
						}else if (CASE_FINACE_APPROVER_GROUP.equalsIgnoreCase(userGroup.getName())){
							lccsGroup = LCCSGroup.CASE_FINACE_APPROVER;
							userProfile.setFinanceApprover(true);
						}
					}
				}
	        }
	        
			} catch (SystemException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 

		}
		return userProfile;
    }
    
    /**
     * Returns the value from the configuration Liferay file based on the provided key.
     * @param key
     * @return
     */
    public String getValueFromLiferayConfig(String key){
        if(PropsUtil.contains(key)){
            return PropsUtil.get(key);
        }
        return null;
    }
    
    /**
     * Returns boolean if filter input request is Http.
     * @param request
     * @param response
     * @return
     */
    public boolean isHttp(ServletRequest request, ServletResponse response) {
		return (request instanceof HttpServletRequest && response instanceof HttpServletResponse);
	}
    
    /**
     * Returns boolean if user is Administrator.
     * @param user
     * @return
     * @throws SystemException
     */
    public boolean isAdminUser(User user) throws SystemException {
		boolean isAdmin = false;
    
		if(user != null){
			//System.out.println("PortalUtil.isOmniadmin(user.getUserId()" + PortalUtil.isOmniadmin(user.getUserId()));
		
	        List<Role> roles = user.getRoles(); 
	        if(roles != null){
	        	for (Role role : roles) {
					if(role != null && RoleConstants.ADMINISTRATOR.equalsIgnoreCase(role.getName())){
						isAdmin = true;
						break;
					}
				}
	        }
		}
        //System.out.println("Is admin user: "+isAdmin);
		return isAdmin;
	}
    
    private LiferayUtil(){}
}
