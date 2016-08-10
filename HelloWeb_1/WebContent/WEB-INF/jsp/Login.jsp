<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <style>
        h4
        {
        align: center;
        }
        table, th, td 
        {
    	align:center;
		}

        </style>
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
        <title>User Login</title>
    </head>
    <body >
    <div id="wrapper">
	<h1>Movie Recommendation System</h1>
        <h2 align="center">User Login</h2>
        <form:form method="post" name="loginForm">
            <table align="center" style="width:40%;height:40%;vertical-align:middle">
                <tr><td>User ID: </td><td><input name="userName" type="text"></td></tr>
                <tr><td>Password: </td><td><input name="password" type="password"></td></tr>
                <tr><td colspan="2" align="center"><input type="submit" value="Submit"></td></tr>
            </table>
            <div style="color:red">${error}</div>
        </form:form>
 </div>
    </body>
</html>