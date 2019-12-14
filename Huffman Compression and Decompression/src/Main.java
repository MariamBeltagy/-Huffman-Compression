import java.util.Scanner;

class Main
{

	public static void main(String[] args) throws Exception
	{
		Scanner input = new Scanner(System.in);
    	
    	System.out.print("Compress a File   -->  (Press 1)\n"
    			+ "Decompress a File -->  (Press 2)\n");
    	
    	int number = input.nextInt();
    	
    	System.out.println("Please enter file name: ");	
    	String myString = input.next();

    	input.close();
		switch(number)  
		{  
			case 1:
			{ 
				String text = ReadFile.readFile(".\\"+ myString+".txt"); //Read from file as a string
				 
				GetCode.getCode(text);
				long startTime = System.nanoTime();  //start time of compression
				StringBuilder encoded = Huffman.buildHuffmanTree(text);
				long elapsedTime = System.nanoTime() - startTime; 

				System.out.println("\nCompression Ratio = " + ((text.length()*8)*1.0/encoded.length()) );
				
		        System.out.println("\nTotal execution time of compression: "
		                + elapsedTime/1000000 + " milliseconds");
				break;
			}
			case 2:    
			{
				String text = ReadFile.readFile(".\\"+ myString+".txt"); //Read from file as a string
				
				long startTime = System.nanoTime();  //start time of decompression
				Huffman.decode(text);;
				long elapsedTime = System.nanoTime() - startTime; 
			     
		        System.out.println("\nTotal execution time of compression: "
		                + elapsedTime/1000000 + " milliseconds");
				break;
			}  
			default: 
				System.out.println("Wrong input :(");
		} 
	}
} 