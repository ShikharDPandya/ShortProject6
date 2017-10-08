
/* Ver 1.0: Starter code for Prim's MST algorithm */

/*
 * Group members:
Mukesh Kumar(mxk170430)
Shikhar Pandya (sdp170030)
Arijeet Roy (axr165030)*/
package cs6301.g40;

import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Scanner;

import cs6301.g40.Graph;
import cs6301.g40.Graph.Edge;

import java.io.FileNotFoundException;
import java.io.File;

public class Prim1 {
	static final int Infinity = Integer.MAX_VALUE;
	Graph g;

	public Prim1(Graph g) {
		this.g = g;
	}

	public int prim1(Graph.Vertex s) {
		int wmst = 0;
		// SP6.Q4: Prim's algorithm using PriorityQueue<Edge>:
		PriorityQueue<Edge> edgeQueue = new PriorityQueue<>(new Comparator<Edge>() {

			@Override
			public int compare(Edge o1, Edge o2) {
				// TODO Auto-generated method stub
				if (o1.weight < o2.weight)
					return -1;
				else if (o1.weight > o2.weight)
					return 1;
				else
					return 0;
			}
		});

		boolean[] marked = new boolean[g.size()];
		marked[s.getName()] = true;
		Iterator<Edge> edgeIterator = s.iterator();
		while (edgeIterator.hasNext()) {
			edgeQueue.add(edgeIterator.next());
		}

		// add to mst taking out the minimum weight outgoing edge everytime.
		while (!edgeQueue.isEmpty()) {
			Edge e = edgeQueue.peek();
			edgeQueue.poll();
			if (marked[e.from.getName()] && marked[e.to.getName()])
				continue;
			marked[e.from.getName()] = true;
			edgeIterator = e.to.iterator();
			while(edgeIterator.hasNext()) {
				Edge nextEdge = edgeIterator.next();
				if (!marked[nextEdge.to.getName()]) {
					edgeQueue.add(nextEdge);
				}
			}
			marked[e.to.getName()] = true;
			wmst += e.weight;

		}
		return wmst;
	}

	public static void main(String[] args) throws FileNotFoundException {
		Scanner in;

		if (args.length > 0) {
			File inputFile = new File(args[0]);
			in = new Scanner(inputFile);
		} else {
			in = new Scanner(System.in);
		}

		Graph g = Graph.readGraph(in);
		Graph.Vertex s = g.getVertex(1);

		Timer timer = new Timer();
		Prim1 mst = new Prim1(g);
		int wmst = mst.prim1(s);
		timer.end();
		System.out.println(wmst);
	}
}
