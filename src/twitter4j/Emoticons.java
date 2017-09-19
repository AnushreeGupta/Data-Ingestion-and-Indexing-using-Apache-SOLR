package twitter4j;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Emoticons {
	
	public static void main(String[] args) throws IOException
	{	String S = new String();
		S = "이 색깔이 o(^_-)O 전부는 아니겠죠? ( ͡° ͜ʖ ͡°) and or not :-) :-* Weird not having a smashed <>< screen 😂 having a new phone is weird #iPhone7 https://t.co/YDYz2ADVRU";
		
		String text_en = S;
		System.out.println("Text_en = " +text_en);
		
		try {
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

         //Processing Emoticons, Kamojis and Stopwords
         
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
	        System.out.println("Text_en 1 = " +text_en);
	        
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
		        
		    //Processing Stopwords Removal
		        BufferedReader inFile3 = new BufferedReader(new FileReader("/home/anushree/workspace/IRProject1/EnStopwords.txt"));
				String inputLine3 = inFile3.readLine();
		        List<String> En_Stopwords = new ArrayList<String>();
		        while (inputLine3 != null)
		        { 
		        	inputLine3 = inFile3.readLine();
		        	En_Stopwords.add(inputLine3);
		        }
		        
		        String strArray[] = S.split(" ");
		        
		        String Stopwords = ""; 
		        int l = strArray.length;
		        for(String eachword: En_Stopwords)
		        {  
		        	for(int i=0; i < l; i++)
		        	{   
		        		if(eachword != null && strArray[i] == eachword)
		        			{	System.out.println("Hello");
		        				System.out.println(eachword);
		        				Stopwords = Stopwords + eachword;
		        				System.out.println(" Eachword =" +eachword+ " Stopwords -"+Stopwords);
		        				text_en = text_en.replace(eachword, "");
		        			}
		        		}
		
		        } 
		        inFile3.close();
		       
		       String Final_Emoticon = Emoji + Emoticons + kamojis;  
		       String Final_Text = text_en;
		       System.out.println("Emoticons = " +Final_Emoticon);
		       System.out.println("Text_en = " +Final_Text);
			}   
		
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		}
		
	} 