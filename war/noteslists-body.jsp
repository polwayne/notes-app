<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core" %>
<form action="/noteslists" method="post">        
	<label>Create a new list</label>
		<input name="name" type="text" size="20" />
	<input class="button" type="submit" value="Create"/>
</form>		

<h3>Your lists</h3>			
<c:forEach var = "noteslist" items = "${list}" varStatus = "n"> 
	<ul class="sidemenu">										
		<li><a href="noteslist?key=${noteslist.stringKey}">${noteslist.name} </a></li>
	</ul>	
</c:forEach>