import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class Huffman {

	//**method to build Huffman Tree according to input text**//
	public static String buildHuffmanTree(String text) throws IOException { 
		//**count frequency of appearance of each character and store it in map**//
		Map<Character, Integer> frequencyMap = new HashMap<>();
		
		for (int i = 0 ; i < text.length(); i++) { 
			if (!frequencyMap.containsKey(text.charAt(i))) {//if current character is not in the map
				frequencyMap.put(text.charAt(i), 1);  //insert new character into map with frequency 1
			}
			else { 
				frequencyMap.put(text.charAt(i), frequencyMap.get(text.charAt(i)) + 1); //increment frequency of character
			}   
		} 
		//System.out.println("Each Character And Their Frequencies:" + freqMap);

		//**priority queue to store nodes of Huffman tree where lowest frequency has higher priority**//
		PriorityQueue<Node> q = new PriorityQueue<>((l, r) -> l.freq - r.freq);

		//**create a leaf node for each character and add it to the priority queue**//
		for (Map.Entry<Character, Integer> entry : frequencyMap.entrySet()) {
			q.add(new Node(entry.getKey(), entry.getValue()));
		}
 
		while (q.size() > 1) {
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
 
		int numberOfEntries = huffmanCode.size();
		System.out.println("Number of Entries in Map: " + numberOfEntries);
		//Initialise file header to have the number of entries in the map
		String fileHeader = String.format("%08d",Integer.parseInt(Integer.toBinaryString(numberOfEntries)));
		
		System.out.println("Byte\tCode\t\tNew Code"); 
		String binCode;
		String hex; 
		int charFrequency;
		String frequencyBinary;
		String longFreqBinary; 
		 
		for (Map.Entry<Character, String> entry : huffmanCode.entrySet()) {
			//**print the codes for each character**//
			binCode = String.format("%08d",Integer.parseInt(Integer.toBinaryString(entry.getKey()))); //the binary code for each character
  
			hex = String.format("%02x", (int) entry.getKey()); 
			System.out.println(hex + "\t" + binCode + "\t" + entry.getValue());
			charFrequency = frequencyMap.get(entry.getKey());
			
			if(charFrequency < 16777215) {
				longFreqBinary = Integer.toBinaryString(charFrequency);
				frequencyBinary = String.format("%024d",Long.parseLong(longFreqBinary)) ;
				fileHeader = fileHeader + binCode + frequencyBinary;  
			}
			else {  
				System.out.println("Error! File too big for this measly program"); 
				return null;
			} 
		}      
		    
		String encoded = fileHeader; 
		 
		for (int i = 0 ; i < text.length(); i++) {  
			encoded += huffmanCode.get(text.charAt(i)); 
		} 
		/* 
		try (PrintWriter out = new PrintWriter("Encoded.txt")) {//write encoded string to file
		    out.println(encoded);
		    out.close();
		}
		*/    
		WriteFile.writeStrings("Compressed", encoded);
		return encoded;
	}  
	
	//**Method to encode the given input and store codes in a map**//
	public static void encode(Node root, String str, Map<Character, String> huffmanCode) {
		if (root == null)  
			return;
		
		if (root.left == null && root.right == null) {//if node is a leaf  
			huffmanCode.put(root.c, str);
		}
	 
		encode(root.left, str + "0", huffmanCode);
		encode(root.right, str + "1", huffmanCode);
	}
}
