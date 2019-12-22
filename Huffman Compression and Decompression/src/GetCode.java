import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class GetCode {
	public static void getCode(String str) throws FileNotFoundException {
		String binCode;
		String output = "";
		for(int i=0;i<str.length(); i++) {
			binCode = String.format("%08d",Integer.parseInt(Integer.toBinaryString(str.charAt(i))));
			output = output + binCode;
		}
		
		try (PrintWriter out = new PrintWriter("binCode")) {
			//write encoded string to file
		    out.println(output);
		    out.close();
		}
	} 
}
