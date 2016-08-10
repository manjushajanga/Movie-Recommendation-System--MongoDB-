<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@page import="java.lang.*" %>
<%@page import="java.util.*" %>
<!DOCTYPE html>
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
<title>Movie Rating Inserted</title>
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
<h2 align="center">Movie Rating Inserted Successfully!</h2>
</div>
</body>
</html>