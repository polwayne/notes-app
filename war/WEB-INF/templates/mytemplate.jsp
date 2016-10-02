<%-- Template for MyCom Site: /WEB-INF/templates/mycom-template.jsp --%> 

<%-- Setup Context variable to point to component files:
        header, meny, footer, and stylesheet --%> 
<%
request.setAttribute("css", "/images/NewHorizon.css");
%>

<%-- Include abstract layout --%> 
<jsp:include page="template2.jsp" />