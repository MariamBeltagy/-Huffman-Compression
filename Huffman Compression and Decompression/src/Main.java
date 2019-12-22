import java.util.Scanner;

class Main {

	public static void main(String[] args) throws Exception {
		Scanner input = new Scanner(System.in);
    	
    	System.out.print("Compress a File   -->  (Press 1)\n"
    			+ "Decompress a File -->  (Press 2)\n");
    	int number = input.nextInt();
     
		switch(number) {  
			case 1: {  
				System.out.println("Please enter file name: ");	
		    	String myString = input.next();
				
				String text = ReadFile.readFile(".\\"+ myString); //Read from file as a string
				 
				//GetCode.getCode(text);
				long startTime = System.nanoTime();  //start time of compression
				String encoded = Huffman.buildHuffmanTree(text); 
				long elapsedTime = System.nanoTime() - startTime;  
				
				System.out.println("\nCompression Ratio = " + ((text.length()*8)*1.0/encoded.length()) );
				 
		        System.out.println("\nTotal execution time of compression: " 
		                + elapsedTime/1000000 + " milliseconds");
				break;
			} 
			case 2: {    
				System.out.println("Please enter file name: ");	
		    	String fileName = input.next();
		    	
				long startTime = System.nanoTime();  //start time of decompression
				Decompression.decompress(fileName); 
				long elapsedTime = System.nanoTime() - startTime; 
			     
		        System.out.println("\nTotal execution time of decompression: " 
		                + elapsedTime/1000000 + " milliseconds"); 
				break; 
			}   
			default:   
				System.out.println("Wrong input :(");
 
		    input.close();
		} 
	}
} 