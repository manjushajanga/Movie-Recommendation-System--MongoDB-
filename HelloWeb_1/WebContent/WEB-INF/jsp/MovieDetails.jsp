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
<title>MovieList</title>
<style>

 body 
 {font-family:sans-serif; background-color: #f4ffe4; color: #333333;}
 
  h1, h2 {font-family:serif;  font-size= 200px;}
  .para{text-indent:50px;}
  .namebold {font-weight:bold;}
  ul,ol {line-height: 1.5;}
  h1 {background-color: #d5edb3;
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

<form:form method="post" action="/HelloWeb/getMovieDetails">
<table align="center" style="width=60%">
	<tr>
	<th style="color:Blue">Movie List for genre ${genre}</th>
	</tr>
	<tr>
	<td>
	<select name="movieName">
	<option value="${movieName}" selected>${movieName}</option>
	<c:forEach begin="0" end="${fn:length(movieList)}" var="index">
		<option value="${movieList[index]}">${movieList[index]}</option>
    </c:forEach>
    </select>
    </td>
    </tr>
  <%-- 	<tr>
      <td onclick="document.location.href='GetMovies';">${index} :<a href="GetMovies? " style="color:blue"><c:out value="${movieList[index]}"/></a></td>
      </tr> --%>
	
 <br><br>
<tr> <td colspan="2"><input type="submit" ></td></tr>
</table>
</form:form>
<table style="width="60%" align="center">
<c:if test="${not empty movieName}">
    
	<tr><td ><h4>Movie Id </h4></td><td> : ${movieid}</td></tr>
	<tr><td ><h4>Movie Name</h4></td><td> : ${moviename}</td></tr>
	<tr><td ><h4>Release Date</h4></td><td> : ${reldate}</td></tr>
	<tr><td ><h4>URL</h4></td><td> : <a href="${url}">${url}</a></td></tr>
	<hr>
	<tr><td ><h4>Average Movie Rating</h4></td><td> : ${avgMovieRating}</td></tr>
	
	
	<tr><td><h4>Rating 1</h4></td><td> : ${allRatings[0][1]}</td></tr>
	<tr><td><h4>Rating 2</h4></td><td> : ${allRatings[1][1]}</td></tr>
	<tr><td><h4>Rating 3</h4></td><td> : ${allRatings[2][1]}</td></tr>
	<tr><td><h4>Rating 4</h4></td><td> : ${allRatings[3][1]}</td></tr>
	<tr><td><h4>Rating 5</h4></td><td> : ${allRatings[4][1]}</td></tr>
		
	
	
	<form:form method="post" action="/HelloWeb/insertRating">
	<tr>
	<td><h4>Give a rating</h4></td>
<td>
<select name="rating" id="rating">
    <option value=1>1</option>
    <option value=2>2</option>
    <option value=3>3</option>
    <option value=4>4</option>
    <option value=5>5</option>
</select>
</td>
</tr>
<tr> <td colspan="2"><input type="submit" value="Rate it!"></td></tr>	
</form:form>
 <br><br>

</c:if>
</table>

</div>
</body>
</html>