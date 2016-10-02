<%
request.setAttribute("title", "Create a new note here");
request.setAttribute("body", "/create-body.jsp");
%>

<jsp:include page="/WEB-INF/templates/mytemplate.jsp" />