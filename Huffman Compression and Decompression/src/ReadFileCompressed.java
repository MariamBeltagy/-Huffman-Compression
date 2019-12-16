
import java.io.*;
import java.util.ArrayList;

public class ReadFileCompressed { 
	  public static ArrayList<String> readFileCompressed(String fileName) throws IOException 
	  {
	   
		  try (
			        InputStream inputStream = new FileInputStream(fileName);
			       // OutputStream outputStream = new FileOutputStream("outputFile.txt");
			) {
			   
			    int n ;
			    String s;
			    ArrayList<String> byteArray =  new ArrayList<>();
			   			    
			    while ((n = inputStream.read()) != -1) 
			    {	  
			    	s = Integer.toBinaryString(n);	
			    	
				    //outputStream.write(Integer.getInteger(s));
				    //System.out.println(n); 
			    	
			    	s = String.format("%08d",Integer.parseInt(s)); ///Zero padding to 8 bits in case of zeros on the left
			    	s = ReverseString.reverseString(s);
				    byteArray.add(s);
				    //System.out.println(s);
			    }
			    //System.out.println(byteArray);
			    return byteArray;  
			    
			} catch (IOException ex) {
			        ex.printStackTrace();
			}
		    return null;
			
	  }

}
 