<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<link href='http://fonts.googleapis.com/css?family=Gochi+Hand' rel='stylesheet' type='text/css'>

<table width="100%">
<tr>
    <td align="center" width="10%"><img src="images/paul.png" width="60%"/></td>
    <td align="center" width="70%"><h1 style="font-family: 'Gochi Hand', cursive;">Personal Assistant</h1></td>
    <td align="right" width="20%">
  <%
    UserService userService = UserServiceFactory.getUserService();
    User user = userService.getCurrentUser();
	if(user == null){ %> 
		<a href="<%= userService.createLoginURL(request.getRequestURI()) %>" class="zocial googleplus">Sign in with Google+</a>
	<% } else { %>
 		<a href="<%= userService.createLogoutURL("/index.jsp") %>" class="zocial googleplus">sign out</a>
	 <% } %>
</td>
</table>