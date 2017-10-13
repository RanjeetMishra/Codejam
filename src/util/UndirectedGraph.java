package util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Created by ranjem on 9/12/17.
 */
public class UndirectedGraph {

    List<Edge>[] graph;
    int numNodes;

    private static class Edge {
        int v;
        int weight;

        public Edge(int v, int weight) {
            this.v = v;
            this.weight = weight;
        }
    }

    public UndirectedGraph(int numNodes) {
        this.numNodes = numNodes;
        graph = new ArrayList[numNodes];
    }

    public void addEdge(int u, int v, int weight, boolean hasMultipleEdge) {
        if(graph[u]==null)
            graph[u] = new ArrayList<Edge>();
        if(graph[v]==null)
            graph[v] = new ArrayList<Edge>();

        if(hasMultipleEdge) {
            for(Edge e: graph[u]) {
                if(e.v == v) {
                    if(weight<e.weight) {
                        e.weight = weight;
                        for(Edge e1: graph[v]) {
                            if(e1.v == u)
                                e1.weight = weight;
                        }
                    }
                    return;
                }
            }
        }
        Edge e = new Edge(v,weight);
        graph[u].add(e);
        e = new Edge(u,weight);
        graph[v].add(e);
    }

    private static class NodeDistance implements Comparable<NodeDistance>{
        int u;
        int p;
        int distance;

        NodeDistance(int u, int p, int distance) {
            this.u = u;
            this.p = p;
            this.distance = distance;
        }

        @Override
        public int compareTo(NodeDistance n) {
            if(this.distance<n.distance)
                return -1;
            else if(this.distance>n.distance)
                return 1;
            return 0;
        }
    }

    /*public long[] singleSourceShortesDistance(int source) {

        long[] distArray = new long[numNodes];
        distArray[source] = 0;

        PriorityQueue<Edge> distanceQueue  = new PriorityQueue<Edge>();
        distanceQueue.add(new Edge(source,0));

        while(!distanceQueue.isEmpty()) {

        }


    }*/

}
