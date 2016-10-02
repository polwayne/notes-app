<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core" %>
<form action="/createnote">
	<input class="button" type="submit" value="Create a new Note"/>
    <input type="hidden" name="key" value="${key}">      
</form>

<h3>Your notes</h3>
<c:forEach var = "note" items = "${list}" varStatus = "n"> 
	<ul class="sidemenu">										
		<li><a href="note?key=${note.stringKey}">${note.subject} </a></li>	
	</ul>	
</c:forEach>