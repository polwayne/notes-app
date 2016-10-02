<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core" %>

<form action="/createnote" method="post">
	<h1>Create a new note here</h1>        
		<label>Subject</label>
			<input name="subject" type="text" size="30" <c:if test="${note != null}">value="${note.subject}"</c:if> />
		<label>Your note</label>
			<textarea name="note" rows="5" cols="5"><c:if test="${note != null}">${note.note}</c:if></textarea>
				<br />	
		<input class="button" type="submit" value="Save note"/>
	<c:if test="${note != null}">	
		<input type="hidden" name="action" value="edit">
	</c:if>	
	<input type="hidden" name="key" value="${key}">
	</form>
