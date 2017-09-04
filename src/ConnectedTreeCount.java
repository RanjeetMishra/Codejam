import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

/**
 * Created by ranjem on 9/3/17.
 * https://www.hackerrank.com/contests/codeagon/challenges/counting-princess-presents
 *
 * The idea is there is exactly one way to connect all k edges as its a tree. Now once we connect it, lets replace the component with a vertex.
 * Now the question reduces to total number of subtrees from a rooted node which can be found recursively as product of total number of subtrees of children+1
 */
public class ConnectedTreeCount {

    static int[] connectedComponentVertices;
    static long modulo = 1000000007;
    static class Edge {
        int v;
        boolean isSpecial;

        public Edge(int v, boolean isSpecial) {
            this.v = v;
            this.isSpecial = isSpecial;
        }
    }

    static List<Edge>[] graph;

    private static boolean recursiveDFS(int node, int[] visitStatus) {

        visitStatus[node]=1;
        boolean isCurrentNodeSpecial = false;
        for(Edge e:graph[node]) {
            if(visitStatus[e.v]!=1) {
                visitStatus[e.v]=1;
                boolean isSpecial = recursiveDFS(e.v, visitStatus) || e.isSpecial;
                if(isSpecial) {
                    connectedComponentVertices[e.v] = 1;
                }
                isCurrentNodeSpecial |= isSpecial;
            }
        }
        if(isCurrentNodeSpecial)
            connectedComponentVertices[node]=1;
        return isCurrentNodeSpecial;
    }

    //BFS order
    private static long subTreeCount(int root, HashSet<Integer> visitedSet) {

        visitedSet.add(root);
        long result = 1;
        for(Edge e:graph[root]) {
            if(connectedComponentVertices[e.v]==1)
                continue;
            if(!visitedSet.contains(e.v)) {
                result = (result*(subTreeCount(e.v,visitedSet)+1))%modulo;
            }
        }
        return result;
    }

    private static long numberGiftWays(int n, int specialNode) {

        connectedComponentVertices = new int[n+1];
        if(specialNode!=-1) {
            int[] visitStatus = new int[n + 1];
            connectedComponentVertices[specialNode] = 1;
            recursiveDFS(specialNode, visitStatus);
        }

        //Lets start DFS with one of the special node. At any point when we find a special edge all parents in that path are
        //added as connectedComponentVertices

        /*Stack<Integer> dfsStack = new Stack<Integer>();
        dfsStack.add(specialNode);

        int[] visitStatus = new int[n+1];
        while(!dfsStack.isEmpty()) {

            int u = dfsStack.peek();
            visitStatus[u]=1;
            List<Edge> edgeList = graph[u];
            boolean allVisited = true;
            for(Edge e:edgeList) {
                if(visitStatus[e.v]!=1) {
                    allVisited = false;
                    if(e.isSpecial) {
                        //We should update connectedComponentVertices from the stack
                        Stack<Integer>
                        while(!dfsStack.isEmpty()) {

                        }
                    }
                    visitStatus[e.v]=1;
                    dfsStack.push(e.v);
                    break;
                }
            }
            if(allVisited) {
                dfsStack.pop();
            }
        }*/

        //Now we have to call subtree count for every node not in connected component. Make sure we consider such nodes as subtrees considering the connected component as a single node.
        //Equivalent to finding number of subtrees given root node

        long result = 1;
        HashSet<Integer> visitedSet = new HashSet<>();

        //For every connected component vertex, check its any edge node not in connected component and get subtree count
        for(int i=1;i<=n;i++) {
            if(connectedComponentVertices[i]==1) {

                for(Edge e:graph[i]) {
                    if(connectedComponentVertices[e.v]!=1) {
                        long temp = subTreeCount(e.v, visitedSet);
                        result = (result *(temp+1))%modulo;
                    }
                }
            }
        }

        //TODO:: Make sure of Modulo operation
        return result;

    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int t = in.nextInt();
        for(int a0 = 0; a0 < t; a0++){
            int n = in.nextInt();
            graph = new ArrayList[n+1];
            int specialNode = -1;
            for(int i = 0; i < n-1; i++){
                int u = in.nextInt();
                int v = in.nextInt();
                int g = in.nextInt();
                if(graph[u]==null) {
                    graph[u]=new ArrayList<Edge>();
                }
                if(graph[v]==null) {
                    graph[v]= new ArrayList<Edge>();
                }
                Edge e1 = new Edge(v,(g==1?true:false));
                graph[u].add(e1);
                Edge e2 = new Edge(u,(g==1?true:false));
                graph[v].add(e2);

                if((g==1) && (specialNode==-1)) {
                    specialNode=u;
                }
            }

            System.out.println(numberGiftWays(n,specialNode));
        }
        in.close();
    }


}
