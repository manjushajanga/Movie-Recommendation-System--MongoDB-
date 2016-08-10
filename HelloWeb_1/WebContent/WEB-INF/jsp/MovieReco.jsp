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
<style>

 body 
 {font-family:sans-serif; background-color: #f4ffe4; color: #333333;}
 
  h1, h2 {font-family:serif;  font-size= 200px;}
  .para{text-indent:50px;}
  .namebold {font-weight:bold;}
  ul,ol {line-height: 1.5;}
  h1 {background-color: #d5edb3;
     background-image: url(MarineDrive.jpg);
	 background-position: 1100px;
	 background-repeat: no-repeat;
	 background-size: 6%;
        
     text-align:center;
	 padding: 20px;
	 margin: 20px 1px 1px 0px;
	 text-shadow: 3px 3px 5px #666;
	 font-size:55px;
	 border: #333333 1px solid;
	 border-radius: 15px;
	 box-shadow: 5px 5px 5px #828282;
	 }
	 
	 
  #nav {margin-left:230px;}
	 
#nav ul {
    list-style-type: none;
    margin: 0;
    padding: 0;
	text-align: center;
    overflow: hidden;
	display: inline-block;
}

#nav li {
    float: left;
	text-indent: 10px;
	display: inline-block;
}

#nav a:link, a:visited {
    display: block;
    width: 140px;
    font-weight: bold;
    color: #333333;
    background-color: #d5edb3;
    text-align: center;
    padding: 12px;
    text-decoration: none;
    text-transform: uppercase;
	border: 1px solid #d5edb3;
	display: inline;
}

#nav a:hover, a:active {
    background-color: #7A991A;
}
  
#wrapper {margin: auto;
           background-color:white;
		   width: 80%;
		   border:solid white 4px;
		   border-top-left-radius: 50px;
		   border-top-right-radius: 50px;}
</style>
<title>Your Account</title>
</head>
<body>
<div id="wrapper">
<h1>Movie Recommendation System</h1>
<form:form method="post" action="/HelloWeb/yourAccount">
<table align="right">
<tr>
<td><input type="submit" value="Your Account"></td>
</tr>
</table>
</form:form>
<form:form method="post" action="/HelloWeb/back">
<table align="right">
<tr>
<td><input type="submit" value="Back"></td>
</tr>
</table>
</form:form>
<form:form method="post" action="/HelloWeb/logout">
<table align="right">
<tr>
<td><input type="submit" value="Logout"></td>
</tr>
</table>
</form:form>


<table align="center" >

<c:forEach begin="0" end="${fn:length(recommend)-1}" var="index">

<tr><td>Movie Id</td><td> : <input type="text" value="${recommend[index]}" id="moviereco" name="moviereco" readonly="readonly"/></td><td> : <input type="text" value="${movienamereco[index]}" id="movienamereco" name="movienamereco" readonly="readonly"/></td></tr>

</c:forEach>


</table>
</div>
</body>
</html>