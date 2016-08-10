package com.tutorialspoint;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.lang.Math.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MapReduceCommand;
import com.mongodb.MapReduceOutput;
import com.mongodb.MongoClient;
import com.mongodb.WriteResult;

import org.json.simple.JSONArray;

import org.json.simple.JSONObject;

import org.json.simple.parser.JSONParser;

import org.json.simple.parser.ParseException;
 
@Controller

public class MenuController {
 
	LoginController loginobj = new LoginController();
	@RequestMapping(value = "/Menu", method = RequestMethod.GET)
	   public ModelAndView student() {
	      return new ModelAndView("Menu", "command", new MenuModel());
	}
//    @RequestMapping(value = "",method = RequestMethod.GET)
//    public String init(ModelMap modelMap) {
//        modelMap.put("info", "Hello User");
//        return "Login";
//    }
 
    @RequestMapping(method = RequestMethod.POST, value="/getMovienames" )
    public String getMovienames(ModelMap modelMap, @ModelAttribute("menuModel") @Valid MenuModel menuModel,HttpServletRequest request,
			HttpServletResponse response) throws UnknownHostException, ParseException {
        System.out.println("in submit of get movies by genre" + menuModel.getGenre());
        int userId=menuModel.getUserId();
        String genre = menuModel.getGenre();
        modelMap.put("genre", genre);
        System.out.println("Calling function to get movies");
        //call function to retrieve names by function
        String[] movieArray = getMovienames(genre);
        if(movieArray.length>0)
        {
        	//show movies
        	List<String> movieList =jsonformat(movieArray, "moviename");
        	request.setAttribute("movieList",movieList);
        	return "MovieDetails";
        }
        else
        {
        	return "Menu";
        }
        //return "Vaat Lagli!";
}
    
    @RequestMapping(method = RequestMethod.POST, value="/getMovieDetails" )
    public String getMovieDetails(ModelMap modelMap, @ModelAttribute("menuModel") @Valid MenuModel menuModel,HttpServletRequest request,
			HttpServletResponse response) throws UnknownHostException, ParseException {
        System.out.println("in submit of a movie details for movie name" + menuModel.getMovieName());
        int userId=menuModel.getUserId();
        String movieName = menuModel.getMovieName();
        System.out.println(movieName);
        modelMap.put("movieName", movieName);
        System.out.println("Calling function to get movie details");
        //call function to retrieve names by function
        String movieDetails = getMoviedata(movieName);
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(movieDetails);
        Long[] lngdata = {(long)jsonObject.get("movieid")};
        String[] strdata = {(String) jsonObject.get("moviename"),(String) jsonObject.get("reldate"),(String) jsonObject.get("url")};
        System.out.println(jsonObject.get("movieid"));
        modelMap.put("movieid", lngdata[0]);
        request.getSession().setAttribute("movieidsession", lngdata[0]);
        modelMap.put("moviename", strdata[0]);
        modelMap.put("reldate", strdata[1]);
        modelMap.put("url", strdata[2]);
        
        //get rating avg by map-reduce
        int[] movieratingmapreduce= mapReduce_countmovieratings();
        System.out.println(movieratingmapreduce[0]);
        modelMap.put("avgMovieRating", movieratingmapreduce[0]);
        
        int[][] allRatings = mapr3(Integer.parseInt(lngdata[0].toString())); 
        modelMap.put("allRatings", allRatings);
        
        return "MovieDetails";
       
}
    
    @RequestMapping(method = RequestMethod.POST, value="/userProfileUpdate" )
    public String updateUser(ModelMap modelMap, @ModelAttribute("menuModel") @Valid MenuModel menuModel,HttpServletRequest request,
			HttpServletResponse response) throws UnknownHostException, ParseException {
    	//menuModel.setUserId(Integer.valueOf(request.getAttribute("userid").toString()));
    	//int userId=menuModel.getUserId();
        //int userId=(int) modelMap.get("userId");
        //int userid=Integer.parseInt(request.getAttribute("userid").toString());
    	int useridsession=Integer.parseInt(request.getParameter("userId"));
        System.out.println("in submit of update user " + useridsession);
        int age=Integer.parseInt(request.getParameter("userAge"));
        int zipcode=Integer.parseInt(request.getParameter("userZip"));
        String occupation=request.getParameter("userOccupation");
        boolean userUpdateval=updateUserdata(useridsession, age, occupation, zipcode);
        if(userUpdateval)
        {
        	//show movies
        	System.out.println(userUpdateval);
        	String userData=LoginController.getUserdata(useridsession);
        	//converting json to sting array two into long[] and two into string[]
        	JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(userData);
            String[] strdata = {(String) jsonObject.get("gender"),(String) jsonObject.get("occupation")};
            modelMap.put("userGender", strdata[0]);
            request.getSession().setAttribute("userGender",strdata[0] );
            modelMap.put("userOccupation", strdata[1]);
            request.getSession().setAttribute("userOccupation",strdata[1] );
            long[] lngdata={(long) jsonObject.get("zipcode"),(long) jsonObject.get("age")};
            modelMap.put("userZip", lngdata[0]);
            request.getSession().setAttribute("userZip",lngdata[0] );
            modelMap.put("userAge", lngdata[1]);
            request.getSession().setAttribute("userAge",lngdata[1] );
        	//response.setHeader("Refresh", "0;URL=http://localhost:8080/HelloWeb/userProfileUpdate");
        	return "Menu";
        }
        else
        {
        	return "Menu";
        }
        
}
    @RequestMapping(method = RequestMethod.POST, value="/insertRating" )
    public String insertRatingMovie(ModelMap modelMap, @ModelAttribute("menuModel") @Valid MenuModel menuModel,HttpServletRequest request,
			HttpServletResponse response) throws UnknownHostException, ParseException {
    	//menuModel.setUserId(Integer.valueOf(request.getAttribute("userid").toString()));
    	//int userId=menuModel.getUserId();
        //int userId=(int) modelMap.get("userId");
        //int userid=Integer.parseInt(request.getAttribute("userid").toString());
    	int useridsession=Integer.parseInt(request.getSession().getAttribute("useridsession").toString());
        System.out.println("in submit of update user " + useridsession);
        int movieidsession=Integer.parseInt(request.getSession().getAttribute("movieidsession").toString());
        int insertratingval=insertRating(useridsession, movieidsession, Integer.parseInt(request.getParameter("rating")));
        request.getSession().setAttribute("insertratingsuccess", insertratingval);
        if(insertratingval==1)
        {
        	System.out.println("alert('Successful rating!');");
        	return "InsertMovie";
        }
        else
        {
        	
        	System.out.println("alert('You have rated this movie already!');");
        	return "Menu";
        }
        
}
    
    @RequestMapping(method = RequestMethod.POST, value="/yourAccount" )
    public String yourAccount(ModelMap modelMap, @ModelAttribute("menuModel") @Valid MenuModel menuModel,HttpServletRequest request,
			HttpServletResponse response) throws UnknownHostException, ParseException {
    	
    	int useridsession=Integer.parseInt(request.getSession().getAttribute("useridsession").toString());
        System.out.println("in submit of update user " + useridsession);
       
        String[][] yourAccount = display_movies(useridsession);
        System.out.println(yourAccount);
        request.setAttribute("yourAccount",yourAccount);
        return "YourAccount";
}
    @RequestMapping(method = RequestMethod.POST, value="/logout" )
    public String logout(ModelMap modelMap, @ModelAttribute("loginModel") @Valid LoginModel loginModel,HttpServletRequest request,
			HttpServletResponse response) throws UnknownHostException, ParseException {
    	
    	int useridsession=Integer.parseInt(request.getSession().getAttribute("useridsession").toString());
        System.out.println("before logout " + useridsession);
       
        request.getSession().invalidate();
        System.out.println("after logout " + useridsession);
        return "redirect:Login";
}
    @RequestMapping(method = RequestMethod.POST, value="/back" )
    public String back(ModelMap modelMap, @ModelAttribute("loginModel") @Valid LoginModel loginModel,HttpServletRequest request,
			HttpServletResponse response) throws UnknownHostException, ParseException {
    	
    	
        return "Menu";
}
    
    @RequestMapping(method = RequestMethod.POST, params="delete", value="/delete" )
    public String deleteRating(ModelMap modelMap, @ModelAttribute("menuModel") @Valid MenuModel menuModel,HttpServletRequest request,
			HttpServletResponse response) throws UnknownHostException, ParseException {
    	
    	int useridsession=Integer.parseInt(request.getSession().getAttribute("useridsession").toString());
    	int movieiddelete=Integer.parseInt(request.getParameter("movieIdForm"));
        System.out.println("in submit of delete user " + movieiddelete);
        int del=deleteRating(useridsession, movieiddelete);
        if(del>0)
        {
        return "Success";
        }
       return "Menu";
        
}
    
    @RequestMapping(method = RequestMethod.POST, params="recommend", value="/delete" )
    public String recommend(ModelMap modelMap, @ModelAttribute("menuModel") @Valid MenuModel menuModel,HttpServletRequest request,
			HttpServletResponse response) throws UnknownHostException, ParseException {
    	
    	int movieidrecommend=Integer.parseInt(request.getParameter("movieIdForm"));
        System.out.println("in submit of recommend user " + movieidrecommend);
        int[] recommend =recommend(movieidrecommend);
        String str ="hi";
        //str=get_moviesdata_id(recommend[0]);
        System.out.println(str);
        request.getSession().setAttribute("recommend", recommend);
        String[] movieName = new String[recommend.length];
        for(int k=0;k<recommend.length;k++)
        {
        	
        	JSONParser jsonParser = new JSONParser();
        	JSONObject jsonObject = (JSONObject) jsonParser.parse(get_moviesdata_id(recommend[k]));
        	String strdata = (String) jsonObject.get("moviename");
        	
        	movieName[k]=strdata;
        	System.out.println(movieName[k]);
        }
        request.getSession().setAttribute("movienamereco", movieName);
        return "MovieReco";
}



    
    public static String[] getMovienames(String gener) throws UnknownHostException
	{
		List<String> zoom = new ArrayList<>();
		MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
		DB db = mongoClient.getDB("test");
		DBCollection col = db.getCollection("movie");
		BasicDBObject andQuery = new BasicDBObject();
		//List<BasicDBObject> obj = new ArrayList<BasicDBObject>();
		andQuery.put(gener,1);
		BasicDBObject fields = new BasicDBObject();
		  fields.put("moviename", 1);
		  DBCursor cursor2 = col.find(andQuery, fields);
		  while (cursor2.hasNext()) {
			zoom.add(cursor2.next().toString());
		  }
		  String[] array = zoom.toArray(new String[0]);
		  return array;

		
	}
    
    public static List<String> jsonformat(String[] x,String f) throws ParseException
	{
		List<String> zoom = new ArrayList<>();
		 JSONParser jsonParser = new JSONParser();
		 for (int i=0;i<x.length;i++)
		 {
         JSONObject jsonObject = (JSONObject) jsonParser.parse(x[i]);
         String firstName = (String) jsonObject.get(f);
         zoom.add(firstName);
		 }
		 return zoom;
 }
    
    public static String getMoviedata(String moviename) throws UnknownHostException
	{
		String array = null;
		int i=0;
		MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
		DB db = mongoClient.getDB("test");
		DBCollection col = db.getCollection("movie");
		DBCursor cursor = col.find();
		BasicDBObject andQuery = new BasicDBObject();
		//List<BasicDBObject> obj = new ArrayList<BasicDBObject>();
		andQuery.put("moviename",moviename);
		  DBCursor cursor2 = col.find(andQuery);
		  while (cursor2.hasNext()) {
			array=cursor2.next().toString();
			}
		  return array;
	}
    
    //movie ratings mapReduce
    public static int[] mapReduce_countmovieratings() throws UnknownHostException, ParseException
	{
		List<Double> zoom = new ArrayList<>();
		List<Double> zoom1=new ArrayList<>();
		MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
		DB db = mongoClient.getDB("test");
		DBCollection col = db.getCollection("rating");
		String map = "function (){"
				+ "emit(this.movieid,{rating: this.rating,count:1});}";
	    String reduce = "function(key, values) { "
	      				+ "var a=values[0];"
	      				+ "for(var i=1;i<values.length;i++){"
	      				+ "var b=values[i];"
	      				+ "a.rating+=b.rating;"
	      				+ "a.count+=b.count;"
	      				+ "}"
	      				+ "return a;}";
	    JSONParser jsonParser = new JSONParser();Double p1=null,p2=null;
	      MapReduceCommand cmd = new MapReduceCommand(col, map, reduce,null, MapReduceCommand.OutputType.INLINE, null);
	      MapReduceOutput out = col.mapReduce(cmd);
	      	for (DBObject o : out.results()) {
	    	  	//zoom.add(o.toString());
	      		JSONObject jsonObject = (JSONObject) jsonParser.parse(o.toString());
		        JSONObject jsonObject1= (JSONObject) jsonObject.get("value");
		        //long[] lngdata={(long) jsonObject.get("zipcode"),(long) jsonObject.get("age")};
		         p1=new Double(jsonObject1.get("count").toString());
		         //Double p1double =Double.parseDouble(p1);
		         zoom.add(p1);
		         p2=new Double(jsonObject1.get("rating").toString());
		         //Double p2double =Double.parseDouble(p2);
		         zoom1.add(p2);
	    		     }
	      	 Double[] pp1=zoom.toArray(new Double[0]);
			 Double[] pp2=zoom1.toArray(new Double[0]);
			 int[] avg=new int[pp1.length];
			 for(int i=0;i<pp1.length;i++)
			 {
				 avg[i]=(int)Math.round(pp2[i]/pp1[i]);
			
			 }
			  return avg;
			  }
    
    public static boolean updateUserdata(int userid,int age,String occupation,int zipcode) throws UnknownHostException
	{
		MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
		DB db = mongoClient.getDB("test");
		DBCollection col = db.getCollection("user");
		BasicDBObject updateFields = new BasicDBObject();
		updateFields.append("age", age);
		updateFields.append("occupation", occupation);
		updateFields.append("zipcode", zipcode);
		BasicDBObject setQuery = new BasicDBObject();
		setQuery.append("$set", updateFields);
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.append("userid", userid);
		
		WriteResult wr=col.update(searchQuery, setQuery);
		return wr.isUpdateOfExisting();
	}
    
    public static int insertRating(int userid,int movieid,int rating) throws UnknownHostException
	{
		MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
		DB db = mongoClient.getDB("test");
		DBCollection col = db.getCollection("rating");
		if(rating <6 && rating >0 && movieid <1683 && movieid>0)
		{
			BasicDBObject andQuery = new BasicDBObject();
			List<BasicDBObject> obj = new ArrayList<BasicDBObject>();
			obj.add(new BasicDBObject("userid", userid));
			obj.add(new BasicDBObject("movieid", movieid));
			andQuery.put("$and", obj);
			//System.out.println(andQuery.toString());
			DBCursor iterable = col.find(andQuery);
			//iterable.
			if (!iterable.hasNext()) {
							BasicDBObject doc = new BasicDBObject("userid", userid).
			            append("movieid", movieid).
			            append("rating", rating);
				col.insert(doc);
				return 1;}	
			}
		return 0;
	}
    
    public static Long[][] userRatedmovies(int userid) throws UnknownHostException, ParseException
	{
		List<Long> zoom1=new ArrayList<>();
		List<Long> zoom = new ArrayList<>();
		MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
		DB db = mongoClient.getDB("test");
		DBCollection col = db.getCollection("rating");
		BasicDBObject andQuery = new BasicDBObject();
		//List<BasicDBObject> obj = new ArrayList<BasicDBObject>();
		andQuery.put("userid",userid);
		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObject=new JSONObject();
		  DBCursor cursor2 = col.find(andQuery);
		  while (cursor2.hasNext()) {
			  jsonObject = (JSONObject) jsonParser.parse(cursor2.next().toString());
			  zoom.add((Long)jsonObject.get("movieid"));
			  zoom1.add((Long)jsonObject.get("rating"));
			  
		  }
		  Long[][] array={zoom.toArray(new Long[0]),zoom1.toArray(new Long[0])};
		  return array;

	}
	public static String get_moviesdata_id(int movieid) throws UnknownHostException
	{
		String array = null;
		int i=0;
		MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
		DB db = mongoClient.getDB("test");
		DBCollection col = db.getCollection("movie");
		DBCursor cursor = col.find();
		BasicDBObject andQuery = new BasicDBObject();
		//List<BasicDBObject> obj = new ArrayList<BasicDBObject>();
		andQuery.put("movieid",movieid);
		  DBCursor cursor2 = col.find(andQuery);
		  while (cursor2.hasNext()) {
			array=cursor2.next().toString();
			
		  }
		
		
		return array;
	}
	
	public static String[][] display_movies(int userid) throws UnknownHostException, ParseException
	{
		List<Long> zoom1=new ArrayList<>();
		Long[][] ar=userRatedmovies(userid);
		//Long[] ar1=zoom1.toArray(new Long[0]);
		String[][] dat=new String[ar[0].length][5];
		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObject=new JSONObject();
		for(int i=0;i<ar[0].length;i++)
		{
			int movieid=ar[0][i].intValue();
			//System.out.println(movieid);
			String mov=get_moviesdata_id(movieid);
			 jsonObject = (JSONObject) jsonParser.parse(mov);
			dat[i][0]=Integer.toString(movieid);
			dat[i][4]=Integer.toString(ar[1][i].intValue());
				dat[i][1]=	jsonObject.get("moviename").toString();
				dat[i][2]=	jsonObject.get("url").toString();
				dat[i][3]=jsonObject.get("reldate").toString();
			//System.out.println(dat[i][0]+"=========="+dat[i][1]+"=========="+dat[i][2]+"=========="+dat[i][3]+"=========="+"=========="+dat[i][4]);
		}
		return dat;
	}
	
	public static int deleteRating(int userid,int movieid) throws UnknownHostException
	{
		MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
		DB db = mongoClient.getDB("test");
		DBCollection col = db.getCollection("rating");
		BasicDBList lst=new BasicDBList();
		lst.add(new BasicDBObject("userid", userid));
		lst.add(new BasicDBObject("movieid", movieid));
		BasicDBObject setQuery = new BasicDBObject();
		setQuery.append("$and", lst);
		WriteResult wr=col.remove(setQuery);
		return wr.getN();
	}
	
	public static int[] recommend(int movieid) throws UnknownHostException, ParseException
	{
		int[][] datamat=new int[943][1682];
		int zoom;
		int zoom1;
		int zoom2;
		MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
		DB db = mongoClient.getDB("test");
		DBCollection col = db.getCollection("rating");
		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObject=new JSONObject();
		  DBCursor cursor2 = col.find();
		  while (cursor2.hasNext()) {
		  jsonObject = (JSONObject) jsonParser.parse(cursor2.next().toString());
		  		  
		  zoom=(int)(Math.round((long)jsonObject.get("userid")));
		   zoom1=(int)(Math.round((long)jsonObject.get("movieid")));
		   zoom2=(int)(Math.round((long)jsonObject.get("rating")));
		   datamat[zoom-1][zoom1-1]=zoom2;
		  }
		  int[] avg=mapReduce_countmovieratings();
		  Double xx=0.0,yy=0.0,xy=0.0;
		  int j=movieid-1,p=0;Double max=-2.0;
		  Double[] maxes=new Double[11];int[] index=new int[11];
		  Double[] s=new Double[1682];
		  for(int i=0;i<1682;i++)
		  {
			  xx=0.0;yy=0.0;xy=0.0;
			  for(int u=0;u<943;u++)
			  {
				  if(datamat[u][i]!=0 && datamat[u][j]!=0)
				  {
					  xx+=(datamat[u][i]-avg[i])*(datamat[u][i]-avg[i]);
					  yy+=(datamat[u][j]-avg[j])*(datamat[u][j]-avg[j]);
					  xy+=(datamat[u][i]-avg[i])*(datamat[u][j]-avg[j]);
				  }
			  }
			  if(xx==0 || yy==0)
			  {
				  s[i]=0.0;
			  }
			  else
			  s[i]=xy/Math.sqrt(xx*yy);
			  
			 // System.out.println(s[i]);
		  }
		  	for(int i=0;i<11;i++)
		  	{
		  		max=-2.0;
		  		for(int k=0;k<1682;k++)
		  		{
		  			if(max<s[k])
		  			{
		  				max=s[k];
		  				maxes[i]=max;
		  				index[i]= k+1;
		  				//s[k]=0.0;
		  				//System.out.println(index[i][0]+"============="+index[i][1]);
		  			}
		  			
		  		}
		  		s[index[i]-1]=-2.0;
  				//System.out.println(maxes[i]+"============="+index[i]);
		  		
		  	}
		  	return index;
	}
	
	public static int[][] mapr3(int movieid) throws UnknownHostException, ParseException
	{
		MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
		DB db = mongoClient.getDB("test");

		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObject=new JSONObject();
		DBCollection col = db.getCollection("rating");
		int i=0;
		int[][] maps=new int[5][2];
		String map = "function (){if(this.movieid=="+movieid+")"
				+ "emit({movieid:this.movieid,rating:this.rating},1);};";
	    String reduce = "function(key, values) { "
	      		+"var a= Array.sum(values);"
	      		+ "return a;"+"} ;";
	      MapReduceCommand cmd = new MapReduceCommand(col, map, reduce,null, MapReduceCommand.OutputType.INLINE, null);
	      MapReduceOutput out = col.mapReduce(cmd);
	    	for (DBObject o : out.results()) {
	    	  //System.out.println(o.toString());
	    		 // zoom.add(o.toString());
	    		 jsonObject = (JSONObject) jsonParser.parse(o.toString());
	    		 JSONObject jsonObject1= (JSONObject) jsonObject.get("_id");
	    		maps[i][0]= (int) Math.round((double)jsonObject1.get("rating"));
	    		maps[i][1]= (int) Math.round((double)jsonObject.get("value"));
	    		i++;
	    		     }
	    	  
			  	return maps;
	}
	
}    