import java.io.FileNotFoundException;
import java.io.PrintWriter; 
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class Huffman {

	//**method to build Huffman Tree according to input text**//
	public static void buildHuffmanTree(String text) throws FileNotFoundException
	{ 
		//**count frequency of appearance of each character and store it in map**//
		Map<Character, Integer> freq = new HashMap<>();
		
		for (int i = 0 ; i < text.length(); i++) 
		{
			if (!freq.containsKey(text.charAt(i))) //if current character is not in the map
			{	
				freq.put(text.charAt(i), 1);  //insert new character into map with frequency 1
			}
			else
			{
				freq.put(text.charAt(i), freq.get(text.charAt(i)) + 1); //increment frequency of character
			}
		}

		//**priority queue to store nodes of Huffman tree where lowest frequency has higher priority**//
		PriorityQueue<Node> q = new PriorityQueue<>((l, r) -> l.freq - r.freq);

		//**create a leaf node for each character and add it to the priority queue**//
		for (Map.Entry<Character, Integer> entry : freq.entrySet()) 
		{
			q.add(new Node(entry.getKey(), entry.getValue()));
		}

		while (q.size() > 1) 
		{
			//**remove the two nodes of lowest frequency from the queue**//
			Node left = q.poll();
			Node right = q.poll();

			//create a new parent node with these two nodes as children with frequency equal to the sum of the two frequencies
			int sum = left.freq + right.freq;
			q.add(new Node('\0', sum, left, right)); //add the new node to the priority queue
		}

		Node root = q.peek();	//stores pointer to root of Huffman Tree

		//**traverse the tree and store the Huffman codes in a map**//
		Map<Character, String> huffmanCode = new HashMap<>();
		encode(root, "", huffmanCode);	//create the codes for each character

		
		String fileHeader = "";
		System.out.println("Char\tCode\t\tNew Code\n");
		String binCode;
		
		for (Map.Entry<Character, String> entry : huffmanCode.entrySet()) 
		{
			//**print the codes for each character**//
			binCode = String.format("%08d",Integer.parseInt(Integer.toBinaryString(entry.getKey()))); //the binary code for each character
			System.out.println(entry.getKey() + "\t" + binCode + "\t" + entry.getValue());
			 
			/*Create the file header consisting of the binary representation
			of each character and their huffman codes separated by spaces*/
			fileHeader = fileHeader + binCode + " " + entry.getValue() + " ";
		}   
		System.out.println("File Header: " + fileHeader);
		System.out.println("\nOriginal string:\n" + text);
		 
		StringBuilder encoded = new StringBuilder();
		encoded.append(fileHeader + "\n");	//copy the file header and encoded string to string
		 
		for (int i = 0 ; i < text.length(); i++) 
		{  
			encoded.append(huffmanCode.get(text.charAt(i))); 
		}
		  
		System.out.println("\nEncoded file:\n" + encoded);
		try (PrintWriter out = new PrintWriter("Encoded.txt")) //write encoded string to file
		{
		    out.println(encoded);
		    out.close();
		}
	}
	public static void decode(String text) throws FileNotFoundException
	{
		Map<String, Character> huffmanDecode = new HashMap<>();
		//split the file into File Header and Encoded String
		String delim = "[\n]";
		String[] tokens = text.split(delim);
		
		String header = tokens[0];
		String encodedString = tokens[1];
		System.out.println("\nHeader: " + header + "\nEncoded String: " + encodedString);
		
		
		//split the file header by the spaces to get codes for each character
		String delim2 = "[ ]";
		String[] headerSplit = header.split(delim2); 
		int parseInt;
		char c;
		
		System.out.println("Char\tCode\n"); //Print each character and its code
		for(int i = 0; i<headerSplit.length; i=i+2)
		{
			parseInt = Integer.parseInt(headerSplit[i], 2); //Convert from binary string to character
			c = (char)parseInt; 
			System.out.println(c + "\t" + headerSplit[i+1]);
			
			huffmanDecode.put(headerSplit[i+1], c);         //insert character and its code into map
		}
		StringBuilder temp = new StringBuilder();
		StringBuilder decoded = new StringBuilder();
		
		/*append each bit by bit in the encoded string, 
		 * if the code is in the map: retrieve its corresponding character from the map 
		 * (the first match is the right match)
		 * and clear the temporary string
		 * else, append the next bit
		 */
		for (int i = 0 ; i < encodedString.length(); i++) 
		{  
			temp.append(encodedString.charAt(i)); 
			//System.out.println("\nTemp String: " + temp); 
			if(huffmanDecode.containsKey(temp.toString())) 
			{    
				decoded.append(huffmanDecode.get(temp.toString()));  //append the retrieved character from map
				temp.delete(0, temp.length()); //clear string
			}
		}
		System.out.println("\nDecoded string:\n" + decoded);	
		try (PrintWriter out = new PrintWriter("Decoded.txt")) //write encoded string to file
		{
		    out.println(decoded);
		    out.close();
		}
	} 
	 
	//**Method to encode the given input and store codes in a map**//
	public static void encode(Node root, String str, Map<Character, String> huffmanCode)
	{
		if (root == null) 
			return;
		
		if (root.left == null && root.right == null) //if node is a leaf 
		{  
			huffmanCode.put(root.c, str);
		}
	
		encode(root.left, str + "0", huffmanCode);
		encode(root.right, str + "1", huffmanCode);
	}
	

}
