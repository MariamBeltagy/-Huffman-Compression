import java.util.Scanner;

class Main
{

	public static void main(String[] args) throws Exception
	{
		Scanner input = new Scanner(System.in);
    	
    	System.out.print("Encode a File-->  (Press 1)\n"
    			+ "Decode a File-->  (Press 2)\n");
    	int number = input.nextInt();
    	System.out.println("Please enter file name: ");
    	
    	String myString = input.next();

    	input.close();
		switch(number) 
		{
			case 1:
			{
				String text = ReadFile.readFile(".\\src\\"+ myString); //Read from file as a string
				Huffman.buildHuffmanTree(text);
				break;
			}
			case 2: 
			{
				String text = ReadFile.readFile(".\\src\\"+ myString); //Read from file as a string
				Huffman.decompressFile(text);;
				break;
			} 
			default:
				System.out.println("Wrong input :(");
		}
	}
} 