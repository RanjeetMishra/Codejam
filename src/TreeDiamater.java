import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

/**
 * Created by ranjem on 8/31/17.
 * https://www.hackerrank.com/contests/hourrank-19/challenges/maximal-tree-diameter
 *
 * Idea is to remove each edge, for two trees find diameter in linear time and find max. Overall complexity is O(n^2)
 *
 * Can we optimize it? First find longest path in graph and then find other forest that we can break and join to get maximal diameter
 * Well this logic is wrong :X
 */
public class TreeDiamater {

    //Lets represent graph by adjacency list.
    static ArrayList<Integer>[] adjacencyGraph;

    static int[] longestDiameterParentPath;

    //Find diameter for given graph. Only twist is we are effectively not splitting the graph instead sending the edge we have
    //deleted which will be taken care of in BFS to find diameter
    static int treeDiameter(ArrayList<Integer>[] adjacencyGraph, int graphNode, int delNeighbor, boolean initializeLongestDiameter) {

        //Start BFS from graphNode. Say we get s. From s do BFS to find farthest t. Make sure we break out of BFS when delU-delV edge is found

        //Finding farthest node from graphNode using BFS
        Queue<Integer> bfsQueue = new LinkedList<Integer>();
        bfsQueue.add(graphNode);

        boolean[] isVisited = new boolean[adjacencyGraph.length];
        int farthestNode = graphNode;
        while(!bfsQueue.isEmpty()) {
            int u = bfsQueue.poll();
            for(int i=0;i<adjacencyGraph[u].size();i++) {
                int v = adjacencyGraph[u].get(i);
                if(!isVisited[v] && v!=delNeighbor) {
                    bfsQueue.add(v);
                    farthestNode = v;
                }
            }
            isVisited[u]=true;
        }
        bfsQueue.clear();
        if(farthestNode==graphNode)
            return 0;

        //Now from farthestNode we have to do bfs again and find diameter
        for(int i=0;i<adjacencyGraph.length;i++)
            isVisited[i]=false;

        if(initializeLongestDiameter) {
            longestDiameterParentPath = new int[adjacencyGraph.length];
        }

        int[] parents = new int[adjacencyGraph.length];

        int diameter = -1;
        bfsQueue = new LinkedList<Integer>();
        bfsQueue.add(farthestNode);
        int startNode = farthestNode;
        farthestNode = -1;
        while(!bfsQueue.isEmpty()) {

            Queue<Integer> levelQueue = new LinkedList<Integer>();
            while(!bfsQueue.isEmpty()) {
                int u = bfsQueue.poll();
                for(int i=0;i<adjacencyGraph[u].size();i++) {
                    int v = adjacencyGraph[u].get(i);
                    if(!isVisited[v] && v!=delNeighbor) {
                        parents[v]=u;
                        levelQueue.add(v);
                        farthestNode = v;
                    }
                }
                isVisited[u]=true;
            }
            bfsQueue = levelQueue;
            diameter++;
        }

        //Reconstruct longestDiameterParentPath from parents
        if(initializeLongestDiameter) {
            int temp = farthestNode;
            longestDiameterParentPath[farthestNode] = parents[farthestNode];
            while (temp != startNode) {
                longestDiameterParentPath[temp] = parents[temp];
                temp = parents[temp];
            }
            longestDiameterParentPath[startNode] = startNode;
        }
        return diameter;
    }


    static boolean visitedEdge(int u, int v, Map<String, Boolean> visitedEdgeMap) {
        int first = Math.min(u,v);
        int second = Math.max(u,v);
        String edgeKey = first+"_"+second;
        return  (visitedEdgeMap.get(edgeKey)!=null);
    }

    static void addVisitedEdge(int u, int v, Map<String,Boolean> visitedEdgeMap) {
        int first = Math.min(u,v);
        int second = Math.max(u,v);
        String edgeKey = first+"_"+second;
        visitedEdgeMap.put(edgeKey,true);
    }

    static int getMaxTreeDiameter(ArrayList<Integer>[] adjacencyGraph) {

     //For all edges
     Map<String, Boolean> visitedEdgeMap = new HashMap<String, Boolean>();
     int maxDiameter = Integer.MIN_VALUE;

     /*
     //First find diameter of graph and store path in longestDiameterParentPath

     int treeDiameter = treeDiameter(adjacencyGraph,1,-1,true);

     //Now we do not want to consider any edge from this longest path but all other forests. So for all nodes in path we have to consider an edge not on path
     for(int i=0;i<adjacencyGraph.length;i++) {
         if(longestDiameterParentPath[i]>0 && longestDiameterParentPath[i]!=i) {

             for(int j=0;j<adjacencyGraph[i].size();j++) {
                 int v = adjacencyGraph[i].get(j);
                 if(v!=longestDiameterParentPath[i]) {
                     int forrestDiameter = treeDiameter(adjacencyGraph, v, i, false);
                     if((treeDiameter+forrestDiameter) > maxDiameter) {
                         maxDiameter = treeDiameter+forrestDiameter;
                     }
                 }
             }
         }
     }

     return maxDiameter+1;
     */


     //Brute Force Algo considering removing all edges one by one

     for(int i=1;i<adjacencyGraph.length;i++) {
        if(i%1000 == 0)
            System.out.println(i);
         List<Integer> neighborList = adjacencyGraph[i];
         for(int j=0;j<neighborList.size();j++) {
             long startTime = System.currentTimeMillis();

             if(visitedEdge(i,neighborList.get(j),visitedEdgeMap))
                 continue;

             int firstTreeDiameter = treeDiameter(adjacencyGraph, i, neighborList.get(j), false);
             int secondTreeDiameter = treeDiameter(adjacencyGraph, neighborList.get(j), i, false);
             if((firstTreeDiameter+secondTreeDiameter+1) > maxDiameter)
                 maxDiameter = (firstTreeDiameter+secondTreeDiameter+1);
             addVisitedEdge(i,neighborList.get(j),visitedEdgeMap);
             long endTime = System.currentTimeMillis();

             //System.out.println("Time for one edge: "+(endTime-startTime));

         }
     }

     return maxDiameter;


    }

    public static void main(String[] args) {

        try {
            File file = new File("/Users/ranjem/Desktop/input10.txt");
            Scanner in = new Scanner(file);
            int n = in.nextInt();

            adjacencyGraph = new ArrayList[n + 1];
            for (int a0 = 0; a0 < n - 1; a0++) {

                int u = in.nextInt();
                int v = in.nextInt();
                if (adjacencyGraph[u] == null) {
                    adjacencyGraph[u] = new ArrayList<Integer>();
                }
                if (adjacencyGraph[v] == null) {
                    adjacencyGraph[v] = new ArrayList<Integer>();
                }
                adjacencyGraph[u].add(v);
                adjacencyGraph[v].add(u);
            }
            System.out.println(getMaxTreeDiameter(adjacencyGraph));
        }catch(Exception e) {

        }
    }
}
