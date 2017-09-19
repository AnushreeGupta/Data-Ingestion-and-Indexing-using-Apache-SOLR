
package twitter4j;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.time.DateUtils;

import twitter4j.conf.ConfigurationBuilder;


public class TwitterStackTech 

{

	public static void main(String[] args) throws TwitterException, IOException, JSONException 
	
	{	//Confuguration Setup-------------------------
		
		 String consumerKey = "p5dNznbpnQUfqaP1Ha9gLrm5g";

	     String consumerSecret = "nzeXfBkIlqyD2Ob1IpiCJWJeIuVBZdzg5pHGjFZgJy61wOuHbX";

	     String accessToken = "772583706783612932-YFVYxQBP5JR5yxAwaE66eJTCtKs0uIB";
	     
	     String accessTokenSecret = "PcwUS1XU2drE4PdEKazfspy36anLdhjoMEzmPsvWaillp";
	     
	    int c = 0;
	     
	     ConfigurationBuilder cb = new ConfigurationBuilder();
	        
	        cb.setDebugEnabled(true);
	        cb.setOAuthConsumerKey(consumerKey);
	        cb.setOAuthConsumerSecret(consumerSecret);
	        cb.setOAuthAccessToken(accessToken);
	        cb.setOAuthAccessTokenSecret(accessTokenSecret);
	        
	        cb.setJSONStoreEnabled(true); 
	        
	    System.out.println ("Connection Set"); 
	    
	    TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();

		//File Creation---------------------------------
        
        JSONObject JS = new JSONObject();
     
        File file = new File("Sports_9to14_Korean.json"); 
	    file.createNewFile();
	    FileWriter writer = new FileWriter(file); 
	    
	    //Search for tweets
		    
		    try {
		    	    //Query Creation with language and keyword specifications
		    	//Query query = new Query("\"(iphone7)\" OR \"(IPHONE7)\" OR \"(iPhone7)\" OR \"(watch2)\" OR \"(applewatch2)\"OR \"(#AppleiPhone7)\" OR \"(#AppleSeptemberEvent)\"OR \"(#Applewatchseries2)\"OR \"(#wearabletech)\" OR \"(AppleEvent)\"OR \"(iPhone7Free)\"OR \"(SiaatAppleEvent)\"OR \"(#applewatch)\"OR \"(apple)\"" +" +exclude:retweets");
			    	
		    	Query query = new Query("\"(#USOpen)\" OR \"(#tennis)\" OR \"(#Murray)\" OR \"(#Keinishikori)\" OR \"(stanwawrinka)\"OR \"(#djokernole)\" OR \"(#delpotrojaum)\" OR \"(Djokovia)\"OR \"(SerenaWilliams)\"OR \"(Pliskova)\"OR \"(novak djokovic)\"OR \"(stan wawrinka)\"OR \"(usopen2016)\"OR \"(us open women's finals)\"OR \"(us open final)\"OR \"(#USOpen)\"OR \"(US open)\" OR \"(#NovakDjokovic)\" OR \"(us open men's finals)\" OR \"(Abierto de Estados Unidos)\" OR \"(última USOPEN)\"OR \"(Stan el hombre)\" OR \"(노바 크 조코비치)\" OR \"(조 코 비치)\"OR \"(USOpen 결승)\"OR \"(카롤리나 pliskova)\"OR \"(USOpen gyeolseung)\"OR \"(안젤리크 리케 케르)\"" +" +exclude:retweets");	
		    	
			    	//Codes for languages -> en, ko, es, tr
			    	query.setLang("ko");
			    	         
			    	query.setCount(5000);
			    	query.setSince("2016-09-09");
			    	query.setUntil("2016-09-14");
			    	QueryResult result;
			    	         
		    	         
		    	     //Getting the tweets and printing to a text file, excluding the retweets  	            
		    	     do {
		    	           result = twitter.search(query);
		    	           List<Status> tweets = result.getTweets();
		    	                
		    	           for (Status tweet : tweets) 
		    	                
		    	            {   
		    	               if (!tweet.isRetweet())
		    	                
		    	               {  c++;
		    	                	
		    	               System.out.println("Before Text = " +tweet.getText());
		    	               
		    	     		   JS.put("topic", "sports");
		    	    	       JS.put("tweet_text", tweet.getText());
		    	    	       JS.put("tweet_lang", tweet.getLang());
		    	    	       JS.put("User - ScreenName", tweet.getUser().getScreenName());
		    	    	       JS.put("tweet_loc", tweet.getGeoLocation()); 
		    	    	       
		    	    	    //Processing Date Format
		    	    	       
		    	    	       Date tweet_date = DateUtils.round(tweet.getCreatedAt() , Calendar.HOUR);
		    	    	       String D = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").format(tweet_date).toString();
		    	    	       JS.put("tweet_date", D);
		    	    	       
		    	    
		    	            //Processing for Hashtags
		    	    	       String S = tweet.getText();
		    	    	       String text_en = S;

		    	       		
		    	    	       String strArray[] = S.split(" ");
		    	   			   int l = strArray.length;
		    	   			   
		    	    	       
		    	               Pattern Hastag_PATTERN = Pattern.compile("#(\\S+)");
		    	               Matcher mat1 = Hastag_PATTERN.matcher(S);
		    	               List<String> Str1 = new ArrayList<String>();
		    	               while (mat1.find()) {
		    	                
		    	                    Str1.add(mat1.group(1));
		    	                  }
		    	               
		    	               for (String eachword: Str1)
		    	               {
		    	            	   text_en = text_en.replace(eachword, "");
		    	               }   
		    	               
		    	               System.out.println("Hashtags =" +Str1);
		    	               JS.put("hashtags", Str1);
		    	               
		    	               
		    	             //Processing mentions
		    	               Pattern Mention_PATTERN = Pattern.compile("@(\\S+)");
		    	               Matcher mat = Mention_PATTERN.matcher(S);
		    	               List<String> Str = new ArrayList<String>();
		    	               while (mat.find()) {
		    	                
		    	                    Str.add(mat.group(1));
		    	                  }
		    	               
		    	               for (String eachword: Str)
		    	               {
		    	            	   text_en = text_en.replace(eachword, "");
		    	               }   
		    	               
		    	               System.out.println("Mentions =" +Str);
		    	               JS.put("mention", Str);
		    	                  
		    	             //Processing for URLs
		    	               
		    	               String URL = new String();
		    	               String pattern = "http";
		    	   	    	   
		    	               for(int i=0; i < l; i++)
		    	               {   
		    	               	int l1 = strArray[i].length();
		    	               	
		    	               	if (l1>3)
		    	               	{ String A = strArray[i].substring(0, 4);
		    	               		
		    	               		if(l1 > 4 & A.equals(pattern) == true)
		    	               		{
		    	               			URL = URL + " " + strArray[i];
		    	               			text_en = text_en.replace(strArray[i], "");
		 		    	               
		    	               		}
		    	               	}
		    	                }
		    	               
		    	               System.out.println("URLS =" +URL);
		    	               JS.put("tweet_urls", URL);
		    	           
		    	      
		    	       		//Processing Emojis
		    	       	
		    	       		 	String regexPattern = "[\\u20a0-\\u32ff\\ud83c\\udc00-\\ud83d\\udeff\\udbb9\\udce5-\\udbb9\\udcee]";
		    	                byte[] utf8 = S.getBytes("UTF-8");

		    	                String string1 = new String(utf8, "UTF-8");

		    	                Pattern Emoji_Pattern = Pattern.compile(regexPattern);
		    	                Matcher mat2 = Emoji_Pattern.matcher(string1);
		    	                List<String> Str2 = new ArrayList<String>();

		    	                while (mat2.find()) {
		    	                	Str2.add(mat2.group());
		    	                }
		    	                
		    	                String Emoji = new String();
		    	                for(int i=0;i<Str2.size();i++)
		    	                {
		    	               	 Emoji = Emoji + Str2.get(i);
		    	               	 text_en = text_en.replace(Str2.get(i), "");
		    	                }
		    	                
		    	       		//Processing Kamojis
		    	       			BufferedReader inFile1 = new BufferedReader(new FileReader("/home/anushree/workspace/IRProject1/Kamojis.txt"));
		    	       			String inputLine1 = inFile1.readLine();
		    	       	        List<String> Kamojilist = new ArrayList<String>();
		    	       	        while (inputLine1 != null)
		    	       	        { 
		    	       	        	inputLine1 = inFile1.readLine();
		    	       	        	Kamojilist.add(inputLine1);
		    	       	        }
		    	       	        
		    	       	        String kamojis = "";
		    	       	        for(String eachword: Kamojilist)
		    	       	        { 	
		    	       	        	if(eachword != null && S.contains(eachword) == true)
		    	       	        	{	
		    	       	        		kamojis = kamojis + eachword;
		    	       	        		text_en = text_en.replace(eachword, "");
		    	       	        	}
		    	       	        }
		    	       	        inFile1.close();
		    	       	        
		    	       	        
		    	       	      //Processing Emoticons
		    	       	         BufferedReader inFile2 = new BufferedReader(new FileReader("/home/anushree/workspace/IRProject1/Emoticons.txt"));
		    	       				String inputLine2 = inFile2.readLine();
		    	       		        List<String> Emoticonlist = new ArrayList<String>();
		    	       		        while (inputLine2 != null)
		    	       		        { 
		    	       		        	inputLine2 = inFile2.readLine();
		    	       		        	Emoticonlist.add(inputLine2);
		    	       		        }

		    	       		        String Emoticons = "";
		    	       		        for(String eachword: Emoticonlist)
		    	       		        { 
		    	       		        	if(eachword != null && S.contains(eachword) == true)
		    	       		        	{	Emoticons = Emoticons + eachword;
		    	       		        		text_en = text_en.replace(eachword, "");
		    	       		        	}
		    	       		        }
		    	       		        inFile2.close();

		    	  		       String Final_Emoticon = Emoji + Emoticons + kamojis;
		    	  		       System.out.println("Emoticons = " +Final_Emoticon);
		    	  		       JS.put("tweet_emoticons", Final_Emoticon);
		    	  		       
		    	  		   //Processing English Stopwords Removal
		    	  		     String strArray2[] = text_en.split(" ");
		    	 			
		    	 			ArrayList<String> wordsList = new ArrayList<String>();
		    	 		   
		    	 	        BufferedReader inFile3 = new BufferedReader(new FileReader("/home/anushree/workspace/IRProject1/EnStopwords.txt"));
		    	 			String inputLine3 = inFile3.readLine();
		    	 	        List<String> En_Stopwords = new ArrayList<String>();
		    	 	        while (inputLine3 != null)
		    	 	        { 
		    	 	        	
		    	 	        	En_Stopwords.add(inputLine3);
		    	 	        	inputLine3 = inFile3.readLine();
		    	 	        }
		    	 	        
		    	 	        for (String word : strArray2) 
		    	 	        {
		    	                 wordsList.add(word);
		    	             }

		    	 	        List<String> newWordList = new ArrayList<String>();
		    	 	        System.out.println(wordsList.toString());
		    	 	        
		    	            for (int i = 0; i < wordsList.size(); i++) 
		    	            {
		    	                
		    	                for (int j = 0; j < En_Stopwords.size(); j++) 
		    	                {
		    	                    if (wordsList.get(i) != " " && En_Stopwords.contains(wordsList.get(i).toLowerCase())) 
		    	                    {
		    	                    	//dummy condition as opposite state is needed
		    	                    }
		    	                    else
		    	                    {
		    	                    	newWordList.add(wordsList.get(i));
		    	                    	break;
		    	                    }
		    	                    	
		    	                }
		    	            }
		    	            
		    	             inFile3.close();
		    	            
		    	             String t = "";
			    	         for (String str : newWordList) {
			    	                t = t + " " + str;
			    	            }
			    	         
			    	         
   
				    	  		// Punctuation Removal
			    	  		       t = t.replace("#", "");
			    	  		       t = t.replace("@", "");
			    	  		       t = t.replace(".", "");
			    	  		       t = t.replace(",", "");
			    	  		       t = t.replace(":", "");
			    	  		       t = t.replace(";", "");
			    	  		       t = t.replace("/", "");
			    	  		       t = t.replace("|", "");
			    	  		       t = t.replace(";", "");
			    	  		       t = t.replace(";", "");
			    	  		       t = t.replace("!", "");
			    	  		       t = t.replace("?", "");
			    	  		       t = t.replace("  ", " ");
		    	  		       
		    	  		       System.out.println("Final Text = "+t);
		    	  		       JS.put("text_ko", t);
		    	  		       
		    	  		       System.out.println("-----------------------------------------------------------------------");

		    	               }
		    	               
		    	               writer.write(JS.toString()); 
		    	            }         	                	
		    	           
		    	         } while ((query = result.nextQuery()) != null);
		    	            
		    	         System.out.println("----------------------------------" +c+ "------------------------------------");
		    	         System.exit(0);
		    	     } 
		    
		    catch (TwitterException te) {
		    	            te.printStackTrace();
		    	          System.out.println("Failed to search tweets: " + te.getMessage());
		    	            System.exit(-1);
		    	        }
		    
		    writer.flush();
		    writer.close();
		    
		    
 }
}     
	

