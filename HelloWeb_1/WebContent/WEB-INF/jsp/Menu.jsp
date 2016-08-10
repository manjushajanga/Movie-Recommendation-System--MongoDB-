<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@page import="java.lang.*" %>
<%@page import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<meta charset="UTF-8">
<title>Menu</title>


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

<script>
function refreshBtn(){location.reload()}
</script>


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

<div id="nav" style="center">
<table>
<tr><td><a href="#User">User Profile</a></td>
<td><a href="#Rate">Rate a Movie</a></td>
<td><a href="#UpdateProfile">Update Profile</a></td></tr>
</table>
</div>

<hr>
<div align="center">
<h2  style="background-color:DarkGrey ; width: 200px " > <a name="User"></a> User Profile </h2>
</div>
<form:form method="" name="">
<table style="width=60%" align="center">
<tr><td>User ID</td><td> ${useridsession}</td></tr>
<tr><td>Gender</td><td> : ${userGender}</td></tr>
<tr><td>Occupation</td><td> : ${userOccupation}</td></tr>
<tr><td>Zip Code</td><td> : ${userZip}</td></tr>
<tr><td>Age</td><td> : ${userAge}</td></tr>
<tr><td>Rated Movies</td><td> : ${ratedMoviessession}</td></tr>

</table>
</form:form>
<hr>


<div align="center">
<h2  style="background-color:DarkGrey ; width: 200px " > <a name="Rate"></a> Rate a Movie</h2>
</div>
<form:form method="post" action="/HelloWeb/getMovienames">
<table style="width=60%" align="center">
	
   <tr align="center"><td>Select a Genre of the movie</td></tr>
	<tr>
	<td>	
     <select name="genre">
    <option value="unknown">Unknown</option>
    <option value="action">Action</option>
    <option value="adventure">Adventure</option>
    <option value="animation">Animation</option>
    <option value="children">Children</option>
    <option value="comedy">Comedy</option>
    <option value="crime">Crime</option>
    <option value="documentary">Documentary</option>
    <option value="drama">Drama</option>
    <option value="fantasy">Fantasy</option>
    <option value="filmnoir">Filmnoir</option>
    <option value="horror">Horror</option>
    <option value="musical">Musical</option>
    <option value="mystery">Mystery</option>
    <option value="romance">Romance</option>
    <option value="scifi">Sci-fi</option>
    <option value="thriller">Thriller</option>
    <option value="war">War</option>
    <option value="western">Western</option>
        
  </select>
  </td>
  </tr>
  <br><br>
<tr> <td colspan="2"><input type="submit" ></td></tr>
  </table>
</form:form>

<hr>
<div align="center">
<h2  style="background-color:DarkGrey ; width: 200px " > <a name="UpdateProfile"></a> Update Profile</h2>
</div>
<form:form method="post" action="/HelloWeb/userProfileUpdate">
<table style="width=60%" align="center">
<tr><td>User Id</td><td> : <input type="text" value="${useridsession}" name="userId" id="userId"></td></tr>
<tr><td>Age</td><td> : <input type="text" value="${userAge}" name="userAge" id="userAge"></td></tr>
<tr><td>Occupation</td><td> : <input type="text" value="${userOccupation}" name="userOccupation" id="userOccupation"></td></tr>
<tr><td>Zipcode</td><td> : <input type="text" value="${userZip}" name="userZip" id="userZip"></td></tr>
<tr><td></td><td > : <input type="submit" value="Update" onclick="refreshBtn()"></td></tr>
</table>
</form:form>

<table>
<tr ><td colspan=2>&copy fv7054 and fv5738</td></tr>
</table>
</div>
</body>
</html>