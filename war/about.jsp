<%
request.setAttribute("title", "about");
request.setAttribute("body", "/about-body.jsp");
%>

<jsp:include page="/WEB-INF/templates/mytemplate.jsp" />