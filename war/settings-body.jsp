<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core" %>

<form action="settings" method="post">
  <p>Please select the persistence mechanism:</p>
  <p>
    <input type="radio" name="persistence" value="0" <c:if test="${datasource == 0}">CHECKED</c:if>> Datastore API<br>
    <input type="radio" name="persistence" value="1" <c:if test="${datasource == 1}">CHECKED</c:if>> JDO <br>
    <input type="radio" name="persistence" value="2" <c:if test="${datasource == 2}">CHECKED</c:if>> JPA
  </p>
	<input type="submit" value="save" />
</form>
