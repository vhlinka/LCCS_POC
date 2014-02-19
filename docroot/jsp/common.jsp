<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>

<%@ page import="javax.portlet.PortletPreferences" %>

<%@ page import="oh.lccs.portal.requestfund.domain.RequestFunds" %>

<portlet:renderURL var="cancelRequestURL">
    <portlet:param name="jspPage" value="/jsp/view.jsp" />
</portlet:renderURL>

<portlet:defineObjects />
