
public class Node //Each node in the tree
{
	char c;
	int freq;
	Node left = null, right = null;

	Node(char c, int freq)
	{
		this.c = c;
		this.freq = freq;
	}  

	public Node(char c, int freq, Node left, Node right) {
		this.c = c;
		this.freq = freq;
		this.left = left;
		this.right = right;
	}
};