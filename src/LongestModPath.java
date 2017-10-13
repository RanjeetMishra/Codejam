import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

/**
 * https://www.hackerrank.com/contests/hourrank-9/challenges/longest-mod-path
 *
 * Idea is to find weight of cycle once cycle is identified Cw and path length from S-E P.
 * Max value optained is from combination of Cw and P.
 * Now as we can have Q queries, its better to find all pair path length.
 *
 * Graph has N nodes and N edges and is connected means there is exactly one cycle. First we will find cycle and cycle weight.
 * Then we will delete one edge from cycle and then find all path lengths from a given node.
 *
 * All solved but got stuck with optimizing Modulo value. A good explaination here.
 * https://www.hackerrank.com/contests/hourrank-9/challenges/longest-mod-path/editorial
 *
 *
 */
public class LongestModPath {

    static class Edge {
        int v;
        long w;

        Edge(int v, long w) {
            this.v = v;
            this.w = w;
        }
    }

    static List<Edge>[] graph;

    static Map<Integer, Long> allPathLength = new HashMap<Integer, Long>();
    static Map<String, Long> edgeWeightMap = new HashMap<String, Long>();
    static int[] parent;
    static long cycleWeight = Long.MIN_VALUE;

    private static void dfsAllPath(int root, int currentNode, int[] isVisited, long rootDistance) {

        isVisited[currentNode] = 1;
        for(Edge e: graph[currentNode]) {
            if(isVisited[e.v]==0) {
                parent[e.v]=currentNode;
                allPathLength.put(e.v, rootDistance+e.w);
                dfsAllPath(root,e.v,isVisited,rootDistance+e.w);
            }else {
                if(parent[currentNode]!=e.v) {
                    //Identified cycle.
                    if (cycleWeight == Long.MIN_VALUE) {
                        //Update cycle weight;
                        int temp = currentNode;
                        cycleWeight = 0;

                        while (temp != e.v) {
                            cycleWeight = cycleWeight + edgeWeightMap.get(temp + "_" + parent[temp]); //W[temp][parent[temp]];
                            temp = parent[temp];
                        }
                        cycleWeight = cycleWeight + edgeWeightMap.get(e.v + "_" + currentNode);
                    }
                }
            }
        }
    }

    private static long mod(long x, long y)
    {
        long result = x % y;
        if (result < 0)
        {
            result += y;
        }
        return result;
    }

    private static long maxScore(long pathLength, long cycleWeight, long modulo) {

        pathLength = mod(pathLength, modulo);
        cycleWeight = mod(cycleWeight, modulo);

        if(cycleWeight==0)
            return pathLength;

        //One way to find maximum is to cycle through all possible hash values till we start repeating and keeping
        //max value
        //Instead of saving all values, we will loop around multiples of modulo till it starts repeating. This is an optimization.

        long maxValue = pathLength;
        HashSet<Long> possibleValues = new HashSet<>();
        possibleValues.add(pathLength);
        boolean repeat = false;

        long temp = pathLength;
        while(!repeat) {

            long temp2 = (modulo-temp)/cycleWeight;
            if(temp2>0) {
                temp = mod(temp+temp2*cycleWeight, modulo);
            }else {
                temp = mod(temp+cycleWeight, modulo);
            }
            if(temp>maxValue)
                maxValue = temp;
            if(possibleValues.contains(temp))
                repeat = true;
            else
                possibleValues.add(temp);
        }

        return maxValue;
    }

    public static void main(String args[]) {

        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        graph = new ArrayList[n];
        parent = new int[n];

        int[] isVisited = new int[n];
        for(int i=0;i<n;i++) {
            isVisited[i] = 0;
            parent[i]=-1;
        }

        for(int i=0;i<n;i++) {
            int u = in.nextInt()-1;
            int v = in.nextInt()-1;
            long w = in.nextLong();

            if(edgeWeightMap.get(u+"_"+v)!=null)
                continue;
            if(graph[u]==null) {
                graph[u] = new ArrayList<>();
            }
            if(graph[v]==null)
                graph[v] = new ArrayList<>();

            Edge uv = new Edge(v,w);
            graph[u].add(uv);
            edgeWeightMap.put(u+"_"+v, w);

            Edge vu = new Edge(u,-w);
            graph[v].add(vu);
            edgeWeightMap.put(v+"_"+u, -w);
        }

        parent[0] = 0;
        allPathLength.put(0,0L);
        dfsAllPath(0,0,isVisited,0);

        int q = in.nextInt();

        for(int i=0;i<q;i++) {
            int s = in.nextInt()-1;
            int d = in.nextInt()-1;
            long modulo = in.nextLong();

            long sdPathLength = allPathLength.get(d) - allPathLength.get(s);
            System.out.println(maxScore(sdPathLength,cycleWeight,modulo));
        }

    }
}
