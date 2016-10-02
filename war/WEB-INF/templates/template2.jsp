<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">

<head>

<meta name="Description" content="Information architecture, Web Design, Web Standards." />
<meta name="Keywords" content="your, keywords" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="Distribution" content="Global" />
<meta name="Author" content="Erwin Aligam - ealigam@gmail.com" />
<meta name="Robots" content="index,follow" />		

<link href="${requestScope.css}" type="text/css" rel="stylesheet" />

 <title> ${requestScope.title} </title>
	
</head>

<body>

	<%
		UserService userService = UserServiceFactory.getUserService();
	 	User user = userService.getCurrentUser(); %> 

	<!-- navigation starts here -->
	<div id="nav">
		
		<ul>
			<li><a href="home">Home</a></li>
			<li><a href="noteslists">Notes</a></li>
			<li><a href="settings">Settings</a></li>
			<li><a href="about">About</a></li>	
			<li><a href="<%= userService.createLogoutURL("/index.jsp") %>">Logout</a></li>
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
	
		<div id="sidebar" >	
		
			<h3>Search notes</h3>	
			<form action="search" class="searchform" method="post">
				<p>
					<input name="search" class="textbox" type="text" />
  					<input name="search" class="button" value="Search" type="submit" />
				</p>			
			</form>
				
	
		</div>	
	
		<div id="main">		
			<jsp:include page="${requestScope.body}" />  							
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
