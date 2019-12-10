
class Main
{

	public static void main(String[] args) throws Exception
	{
		String text = ReadFile.readFile(".\\src\\test"); //Read from file as a string

		Huffman.buildHuffmanTree(text);
	}
} 