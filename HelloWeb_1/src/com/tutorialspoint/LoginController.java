package com.tutorialspoint;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MapReduceCommand;
import com.mongodb.MapReduceOutput;
import com.mongodb.MongoClient;
 
@Controller
@RequestMapping("/Login")
public class LoginController {
 
    @RequestMapping(method = RequestMethod.GET)
    public String init(ModelMap modelMap) {
        modelMap.put("info", "Hello User");
        return "Login";
    }
 
    @RequestMapping(method = RequestMethod.POST )
    public String submit(ModelMap modelMap, @ModelAttribute("loginModel") @Valid LoginModel loginModel,HttpServletRequest request,
			HttpServletResponse response) throws UnknownHostException, ParseException {
        System.out.println("in submit" + loginModel);
        String userId=loginModel.getUserName();
        String password = loginModel.getPassword();
        
        int intuserId=Integer.valueOf(userId);
        int intpassword=Integer.valueOf(password);
        
        //calling function login
        boolean loginValue=loginCheck(intuserId,intpassword);
        if(loginValue)
        {
        	System.out.println("Successful login");
        	modelMap.put("userId",intuserId);
        	request.getSession().setAttribute("useridsession",intuserId);
        	
        	String userData=getUserdata(intuserId);
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
            //get movie count rated by user
            String[] ratedMoviesArray =  mapReduce_countmovies(intuserId);
            String ratedMovies = ratedMoviesArray[0];
            JSONParser jsonParser1 = new JSONParser();
            JSONObject jsonObject1 = (JSONObject) jsonParser1.parse(ratedMovies);
            double[] lngdata1={(double) jsonObject1.get("value")};
            modelMap.put("ratedMovies", lngdata1[0]);
            request.getSession().setAttribute("ratedMoviessession",lngdata1[0] );
            
        	return "Menu";
        }
        else
        {
        	modelMap.put("error", "Invalid UserName / Password");
        	return "Login"; 
        }
        
//        if (password != null && password.equals("123")) {
//            modelMap.put("userInfo", loginModel.getUserName());
//            //System.out.println("success");
//            
//            
//       } else {
//            modelMap.put("error", "Invalid UserName / Password");
//            return "Login";
//        }
// 
}
    
   /* @RequestMapping(method = RequestMethod.POST, value="/logout" )
    public String logout(ModelMap modelMap, @ModelAttribute("loginModel") @Valid LoginModel loginModel,HttpServletRequest request,
			HttpServletResponse response) throws UnknownHostException, ParseException {
    	
    	int useridsession=Integer.parseInt(request.getSession().getAttribute("useridsession").toString());
        System.out.println("nefore logout " + useridsession);
       
        request.getSession().invalidate();
        System.out.println("after logout " + useridsession);
        return "Login";
}*/
 
    
    public static boolean loginCheck(int login,int password) throws UnknownHostException
	{
       	MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
		DB db = mongoClient.getDB("test");
		DBCollection col = db.getCollection("user");
		BasicDBObject andQuery = new BasicDBObject();
		List<BasicDBObject> obj = new ArrayList<BasicDBObject>();
		obj.add(new BasicDBObject("userid", login));
		obj.add(new BasicDBObject("password", password));
		andQuery.put("$and", obj);
		//System.out.println(andQuery.toString());
		DBCursor iterable = col.find(andQuery);

		boolean b=false;
		while (iterable.hasNext()) {
			//System.out.println(iterable.next());
			b=true;
			break;}
		mongoClient.close();
		return b;

	}
    
    //to count the number of movies rated by the user
    public static String[] mapReduce_countmovies(int userid) throws UnknownHostException
	{
		List<String> zoom = new ArrayList<>();
		MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
		DB db = mongoClient.getDB("test");
		DBCollection col = db.getCollection("rating");
		int count=0;
		String map = "function (){emit(this.userid,1);};";
	    String reduce = "function(key, values) { "
	      		+"return Array.sum(values)"+"} ;";
	      MapReduceCommand cmd = new MapReduceCommand(col, map, reduce,null, MapReduceCommand.OutputType.INLINE, null);
	      MapReduceOutput out = col.mapReduce(cmd);
	    	for (DBObject o : out.results()) {
	    	  //System.out.println(o.toString());
	    		  zoom.add(o.toString());
	    		     }
	    	  String[] array = zoom.toArray(new String[0]);
			  return array;
	}
    
    //to get the user data
    public static String getUserdata(int userid) throws UnknownHostException
	{
		String array = null;
		int i=0;
		MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
		DB db = mongoClient.getDB("test");
		DBCollection col = db.getCollection("user");
		DBCursor cursor = col.find();
		BasicDBObject andQuery = new BasicDBObject();
		//List<BasicDBObject> obj = new ArrayList<BasicDBObject>();
		andQuery.put("userid",userid);
		  DBCursor cursor2 = col.find(andQuery);
		  while (cursor2.hasNext()) {
			array=cursor2.next().toString();
			
		  }
		
		
		return array;
	}

}
