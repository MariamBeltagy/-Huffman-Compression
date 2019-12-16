import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class Decompression {
	
	public static void decompress(String fileName) throws IOException
	{
		int i = 0;
		int numberOfEntries; //number of defined characters in the header
		char charac;
		int frequency;
		String encodedString = "";
		 
		Map<Character, Integer> freqMap = new HashMap<>(); //map to store each character and its frequency
		
		ArrayList<String> byteArray =  new ArrayList<>();
		 
		byteArray = ReadFileCompressed.readFileCompressed(fileName);
		//System.out.println(byteArray);
		numberOfEntries = Integer.parseInt(byteArray.get(0),2); //the first element in the array
		System.out.println("Number of Entries = " + numberOfEntries);
		 
		for (i = 1; i <= numberOfEntries*2; i=i+2) //increment by 2 because each iteration we get 2 list items
		{
			charac = (char)Integer.parseUnsignedInt(byteArray.get(i), 2); //convert binary string to character
			frequency = Integer.parseInt(byteArray.get(i+1),2); 	//convert binary string to int
			//System.out.println(charac + " " + frequency); 
			
			freqMap.put(charac, frequency); 
		} 
		for(i = numberOfEntries*2 +1; i<byteArray.size();i++)	//append the encoded string from the list
		{
			encodedString = encodedString +  byteArray.get(i);
		}
		//System.out.println(encodedString);
		 
		//////////Rebuild the Huffman Tree//////////
		
		//**priority queue to store nodes of Huffman tree where lowest frequency has higher priority**//
		PriorityQueue<Node> q = new PriorityQueue<>((l, r) -> l.freq - r.freq);

		//**create a leaf node for each character and add it to the priority queue**//
		for (Map.Entry<Character, Integer> entry : freqMap.entrySet()) 
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
 
		//**store the Huffman codes in a map**//
		Map<String, Character> huffmanCode = new HashMap<>();
		encode("", root, huffmanCode);	//create the codes for each character
		 

		///////////////////////Decoding///////////////////////////////
	
		StringBuilder temp = new StringBuilder(); 
		StringBuilder decoded = new StringBuilder();
		
		/*append each bit by bit in the encoded string, 
		 * if the code is in the map: retrieve its corresponding character from the map 
	 	 * (the first match is the right match)
		 * and clear the temporary string
		 * else, append the next bit
		*/ 
		for (i = 0 ; i < encodedString.length(); i++) 
		{  
			temp.append(encodedString.charAt(i)); 
			if(huffmanCode.containsKey(temp.toString())) 
			{    
				decoded.append(huffmanCode.get(temp.toString()));  //append the retrieved character from map
				temp.delete(0, temp.length()); //clear string
			}
		}
		System.out.println("\nDecoded string:\n" + decoded);	
		try (PrintWriter out = new PrintWriter("Decompressed.txt")) //write encoded string to file
		{
		    out.println(decoded);
		    out.close();
		} 		
	}

	//**Method to encode the given input and store codes in a map**//
	public static void encode(String str, Node root, Map<String, Character> huffmanCode)
	{
		if (root == null) 
			return;
		
		if (root.left == null && root.right == null) //if node is a leaf 
		{  
			huffmanCode.put(str,root.c);
		}
	
		encode(str + "0", root.left, huffmanCode);
		encode(str + "1", root.right, huffmanCode); 
	}
	
}	

  