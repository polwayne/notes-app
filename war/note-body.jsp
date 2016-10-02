<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core" %>

<table>
	<tr>
       	<td colspan="2"><h1>${note.subject}</h1></td>
    </tr>
    <tr>
       	<td colspan="2"><h3>${note.note}</h3></td>
    </tr>
	<tr>
       	<td>
       		<form action="/note" method="post">
			<input class="button" type="submit" value="Delete"/>
			<input type="hidden" name="action" value="delete"> 
    		<input type="hidden" name="key" value="${key}">      
			</form>
		</td>
		<td>
			<form action="/createnote">
			<input class="button" type="submit" value="Edit"/>
			<input type="hidden" name="action" value="edit"> 
    		<input type="hidden" name="key" value="${key}"/>   
			</form>
		</td>
    </tr>
</table>
