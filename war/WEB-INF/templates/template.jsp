<%-- Abstract Layout I: /WEB-INF/template.jsp --%> 
<html>
<head>
  <link href="${requestScope.css}" type="text/css" rel="stylesheet" />
  <title> ${requestScope.title} </title>
</head>
<body>
  <jsp:include page="${requestScope.header}" />

  <table><tr>
  <td> <jsp:include page="${requestScope.menu}" /> </td>
  <td>
    <!--page specific content -->
        <jsp:include page="${requestScope.body}" />  
 </td>
 </tr></table>
  <jsp:include page="${requestScope.footer}" />
</body>
</html>