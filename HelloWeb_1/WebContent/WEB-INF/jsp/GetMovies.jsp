<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="com.tutorialspoint.*"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> 

<%@ page import="java.util.List"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Rate the Movie</title>
</head>
<body>
<table>
<tr><th style="color:Blue">Movie Details</th></tr>
<c:forEach begin="0" end="${fn:length(movieDetailsList)- 1}" var="index">
   <tr>
      <td>Movie Name<c:out value="${movieList[index]}"/></td><td></td>
      <td>Release Date<c:out value="${movieList[index]}"/></td><td></td>
      <td>URL<c:out value="${movieList[index]}"/></td><td></td>
      <td>Avg Rating<c:out value="${movieList[index]}"/></td><td></td>
    </tr>
</c:forEach>

</table>

</body>
</html>