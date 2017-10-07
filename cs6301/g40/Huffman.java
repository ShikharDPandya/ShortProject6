package cs6301.g40;
import java.util.PriorityQueue;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;


public class Huffman{
	PriorityQueue<Huffman.CodeTree> pq = new PriorityQueue();
	
	/**
	 * @return : CodeTree instance which has the minimum frequency
	 *
	 * The function deletes the CodeTree with minimum frequency from the heap returns it
	 */
	public CodeTree removeCodeTree(){
		return pq.remove();
	}
	
	/**
	 *
	 * @param a : First CodeTree
	 * @param b : Second CodeTree
	 * @return  : Merged CodeTree out of CodeTrees a & b.
	 *
	 * The function creates a new instance of CodeTree whose frequency would be sum of frequencies of CodeTrees a & b
	 */
	public CodeTree merge(CodeTree a, CodeTree b){
		double mergedFreq = a.freq + b.freq;
		String mergedString = (a.Label.concat(b.Label));
		return new CodeTree(mergedFreq,mergedString,a,b);
	}
	
	/**
	 *
	 * @param a : Inputted CodeTree to be put in heap.
	 *
	 * The function adds CodeTree a to the Priority Queue
	 */
	public void addTree(CodeTree a){
		this.pq.add(a);
	}
	
	/**
	 *
	 * @return : An instance of CodeTree which has other CodeTrees as its descendant. Its frequency will be 1.
	 *
	 * The function removes two CodeTrees with minimum frequencies from the heap, merge them and
	 * add merged CodeTree to the heap. The process continues until the merged CodeTree has frequency of 1.
	 */
	public CodeTree performCoding(){
		while(pq.peek().freq<1){
			CodeTree a = removeCodeTree();
			CodeTree b = removeCodeTree();
			CodeTree c = merge(a,b);
			addTree(c);
		}
		return removeCodeTree();
	}
	
	/**
	 *
	 * @param a : Final merged CodeTree instance
	 * @param list : ArrayList which contains the huffman codes
	 *
	 * The function displays the symbol and Huffman code associated with it
	 */
	public void display(CodeTree a, List list){
		
		if(a.left!=null) {
			List leftlist = new ArrayList(list);
			List rightlist = new ArrayList(list);
			leftlist.add(0);
			rightlist.add(1);
			display(a.left,leftlist );
			display(a.right,rightlist);
		}
		else{
			System.out.print("Symbol: ");
			System.out.println(a.Label);
			System.out.print("Huffman Code: ");
			
			for (Object i:list
				 ) {
				
				System.out.print(i);
			}
			System.out.println();
			System.out.println("---------------------------");
		}
		
	}
	
	public static class CodeTree implements Comparable<Huffman.CodeTree> {
		String Label;
		double freq;
		CodeTree left;
		CodeTree right;
		int leftEdge;
		int rightEdge;
		
		CodeTree(double frequency, String Label){
			this.Label = Label;
			this.freq = frequency;
			this.left = null;
			this.right = null;
			
			
		}
		
		CodeTree(double frequency, String Label, CodeTree left, CodeTree right){
			this.Label = Label;
			this.freq = frequency;
			this.left = left;
			this.right = right;
			this.leftEdge = 0;
			this.rightEdge = 1;
		}

		@Override
		public int compareTo(Huffman.CodeTree o) {
			if (this.freq > o.freq) return 1;
			else if (this.freq < o.freq) return -1;
			else return 0;
		}
		
		
	}
	
	
	
	public static void main(String [] args){
		Huffman hf = new Huffman();
		double a = 0.1;
		System.out.print("Enter the Number of Characters :");
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		while(n-->0){
			double freq = sc.nextDouble();
			String str = sc.next();
			CodeTree cd = new Huffman.CodeTree(freq,str);
			hf.addTree(cd);
		}
		
		CodeTree finalcd = hf.performCoding();
		List list = new ArrayList();
		hf.display(finalcd,list);
		
	}
}
