
public class ReverseString {
	
	public static String reverseString(String str) {
        String reverse = "";
       
        for(int i = str.length() - 1; i >= 0; i--) {
            reverse = reverse + str.charAt(i);
        }
        return reverse;
    }
}
