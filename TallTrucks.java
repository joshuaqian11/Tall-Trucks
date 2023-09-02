// THIS CODE IS MY OWN WORK, IT WAS WRITTEN WITHOUT CONSULTING
// A TUTOR OR CODE WRITTEN BY OTHER STUDENTS - Joshua Qian

import java.util.*;

class Graph_pq {
    int height[];
    Set<Integer> visited; //unique
    PriorityQueue<Node> pqueue;
    int V; // Number of cities
    List<List<Node>> adj_list;

    //class constructor
    public Graph_pq(int V) {
        this.V = V;
        height = new int[V];
        visited = new HashSet<Integer>();
        pqueue = new PriorityQueue<Node>(V, new Node());
    }

    // Dijkstra's Algorithm implementation
    public void algo_dijkstra(List<List<Node>> adj_list, int src_city) {
        this.adj_list = adj_list;
	int realCityNum = 0;
  //check if city is included in list
	for(int j=0 ; j < adj_list.size(); j++){
		if ( adj_list.get(j).size() !=0){
			realCityNum++;
		}
	}

        for (int i = 0; i < V; i++) {
          height[i] = 0;

        }

        // first add source vertex to PriorityQueue
        pqueue.add(new Node(src_city, Integer.MAX_VALUE));

        // Height to the source from itself is max
        height[src_city] = Integer.MAX_VALUE;
        while (visited.size() != realCityNum) {

            // u is removed from PriorityQueue and has max height
            //
            if (pqueue.size() != 0)
            int u = pqueue.remove().node;

            // add node to finalized list (visited)
            visited.add(u);
            graph_adjacentNodes(u);
        }
    }

    // this method processes all neighbours of the just visited node
    private void graph_adjacentNodes(int u) {
        // process all neighbouring nodes of u
        for (int i = 0; i < adj_list.get(u).size(); i++) {
            Node v = adj_list.get(u).get(i);

            //  proceed only if current node is not in 'visited'
            if (!visited.contains(v.node)) {
		// First find min between current height and to v city, then fine max between previous route and v city
		//
                height[v.node] = Math.max(height[v.node], Math.min(height[u], v.height));
                // Add the current vertex to the PriorityQueue
                pqueue.add(new Node(v.node, height[v.node]));
            }
        }
    }
}

class TallTrucks {
    public static void main(String arg[]) {
        int src_city = 1;
        // adjacency list representation of graph
        List<List<Node>> adj_list = new ArrayList<List<Node>>();

        // Scanner input is fast enough for this problem
        Scanner sc = new Scanner(System.in);
        String[] line = sc.nextLine().split(" ");
        int N = Integer.parseInt(line[0]);  // number of cities
        int M = Integer.parseInt(line[1]);  // number of roads

        // Initialize adjacency list for every city in the graph
	//  N + 1 is due to fact that list starts with 0
	//
        for (int i = 0; i < N + 1; i++) {
            List<Node> item = new ArrayList<Node>();
            adj_list.add(item);
        }


        // there are N cities (1 through N) and M roads
        for (int m = 0; m < M; ++m) {
            line = sc.nextLine().split(" ");
            // notice road between U and V with height limit H
            int U = Integer.parseInt(line[0]);
            int V = Integer.parseInt(line[1]);
            int H = Integer.parseInt(line[2]);
	    // Populate the list
	    //
            adj_list.get(U).add(new Node(V, H));
            adj_list.get(V).add(new Node(U, H));  // undirect graph
        }
        sc.close();

        Graph_pq dpq = new Graph_pq(N + 1);
        dpq.algo_dijkstra(adj_list, src_city);

        // Print max height from source city (1) to all other cities
        for (int i = 2; i < dpq.height.length; i++)
        System.out.print(dpq.height[i] + " ");
        System.out.println("");
    }
}

// Node class
class Node implements Comparator<Node> {
    public int node;
    public int height;

    public Node() {
    } //empty constructor

    public Node(int node, int height) {
        this.node = node;
        this.height = height;
    }

    // to make max priorityQueue
    @Override
    public int compare(Node node1, Node node2) {
        if (node1.height < node2.height)
            return 1;
        if (node1.height > node2.height)
            return -1;
        return 0;
    }
}
