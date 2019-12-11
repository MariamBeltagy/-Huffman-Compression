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
		System.out.println("Huffman Codes:\n");
		
		for (Map.Entry<Character, String> entry : huffmanCode.entrySet()) 
		{
			//**print the codes for each character**//
			System.out.println(entry.getKey() + " " + entry.getValue());
			
			/*Create the file header consisting of the binary representation
			of each character and their huffman codes separated by spaces*/
			fileHeader = fileHeader + Integer.toBinaryString(entry.getKey()) + " " + entry.getValue() + " ";
		} 
		System.out.println("File Header: " + fileHeader);
		System.out.println("\nOriginal string:\n" + text);
		 
		//**print encoded string**//
		StringBuilder sb = new StringBuilder();
		sb.append(fileHeader + "\n");	//copy the file header and encoded string to string
		
		for (int i = 0 ; i < text.length(); i++) 
		{ 
			sb.append(huffmanCode.get(text.charAt(i))); 
		}
		 
		System.out.println("\nEncoded string:\n" + sb);
		try (PrintWriter out = new PrintWriter("Encoded_File.txt")) //write encoded string to file
		{
		    out.println(sb);
		    out.close();
		}
	}
	public static void decompressFile(String text)
	{
		//int index = -1;
		//System.out.println("\nDecoded string: \n");
		//while (index < sb.length() - 2) 
		//{
		//	index = decode(root, index, sb);
		//}
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
	
	//**method to decode the encoded string**//
	public static int decode(Node root, int index, StringBuilder sb)
	{
		if (root == null)
			return index;
	
		//**if it is a leaf node**//
		if (root.left == null && root.right == null)
		{
			System.out.print(root.c);	//print each decoded character
			
			return index;
		}
	
		index++;
	
		if (sb.charAt(index) == '0')
			index = decode(root.left, index, sb);
		else
			index = decode(root.right, index, sb);
	
		return index;
	}

}
