<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">

<head>

<meta name="Description" content="Information architecture, Web Design, Web Standards." />
<meta name="Keywords" content="your, keywords" />
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<meta name="Distribution" content="Global" />
<meta name="Author" content="Erwin Aligam - ealigam@gmail.com" />
<meta name="Robots" content="index,follow" />		

<link rel="stylesheet" href="images/NewHorizon.css" type="text/css" />

 <title>personal assistant</title>
	
</head>

<body>

	<%
		UserService userService = UserServiceFactory.getUserService();
	 	User user = userService.getCurrentUser(); 
	 	if(user != null)
  	 		response.sendRedirect("/home");%> 

	<!-- navigation starts here -->
	<div id="nav">
		
		<ul>
			<li><a href="<%= userService.createLoginURL("/home") %>" class="zocial googleplus">Sign in</a></li>
		</ul>
	
	</div>

	<!-- header starts here -->
	<div id="header">	
					<div id="paul"></div>
		<h1 id="logo-text"><a href="home" title="">Paul</a></h1>	
		<p id="slogan">your personal assistant</p>				
	
	</div>	
				
	<!-- content-wrap starts here -->
	<div id="content-wrap"><div id="content">	 
		</div>	
	
		<div id="main">		
		</div>					
		
	<!-- content-wrap ends here -->		
	</div></div>

	<!-- footer starts here-->	
	<div id="footer-wrap">
	
		<!-- columns starts here-->		
	
		<div id="footer-bottom">		
			<p>
			&copy; 2012 <strong>Paul Wein</strong>

            &nbsp;&nbsp;&nbsp;&nbsp;

			<a href="http://www.bluewebtemplates.com/" title="Website Templates">website templates</a> by <a href="http://www.styleshout.com/">styleshout</a>

			&nbsp;&nbsp;&nbsp;&nbsp;
			
            <a href="http://validator.w3.org/check?uri=referer">XHTML</a> |
			<a href="http://jigsaw.w3.org/css-validator/check/referer">CSS</a>
			</p>		
		</div>	
	
	<!-- footer ends-->		
	</div>

</body>
</html>
