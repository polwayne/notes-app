<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

  <%
    UserService userService = UserServiceFactory.getUserService();
    User user = userService.getCurrentUser();
	pageContext.setAttribute("user", user);
	%>

<table>
      <tr>
        <td colspan="2" style="font-weight:bold;"><h1>Welcome ${fn:escapeXml(user.nickname)}!</h1></td>        
      </tr>
      <tr>
        <td>You can save notes and order them in lists here!</td>
      </tr>
	  <tr>
        <td>This is Paul, your personal assistant!</td>
      </tr>
</table>
    <p /> <p />
	<img src="images/paul.png" />