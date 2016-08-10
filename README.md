# Movie-Recommendation-System--MongoDB-
Movie Recommendation system using MongoDB

DATA STATISTICS

Users Data:
	Collection Name		: 	User
	Fields		   		:	userid, age, gender,	occupation, zipcode, password 
	Number Of Users		:	943
Movies Data:
	Collection Name		:	Movie
	Fields				:	movieid, moviename, url, reldate, each genre as each	field (0 or 1)
	Number of Movies		:	1682
	Number of Genre		:	19 including Unknown
Raing Data:
		Collection Name		:	Rating
		Fields				:	userid, movieid, rating(1-5)
		Number of Ratings		:	100k

FUNCTIONALITY

Insert rating

Display user data

Display movie details based on genre

Displaying movie details rated by logged user

Updating user details

Delete user rating for particular movie

MapReduce1:-	 Count Number of movies rated by Particular User
MapReduce2:- 	 Find average rating for all movies

MapReduce3:- 	 Count number of users gave rating for a movie for each value in the range 1-5.

Recommendation System :
Predict the opinion the user will have on the different items.
 Recommend the ‘best’ items based on the user’s previous likings and the opinions of like-minded users whose ratings are similar.
Item‐Item Collaborative Filtering

In this application Item-Item CF Algorithm has been used. Top 11 movies has been recommended for each movie based on their similarity.

TECHNOLOGIES


NoSQL  Database		:	MongoDB
Front End				:	HTML, CSS
Framework				:	Spring MVC
Programming Language	:	Java
IDE					:	Eclipse
Application Server		:	Tomcat v7.0




