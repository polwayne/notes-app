<%
request.setAttribute("title", "Note");
request.setAttribute("body", "/note-body.jsp");
%>

<jsp:include page="/WEB-INF/templates/mytemplate.jsp" />