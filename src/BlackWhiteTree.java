import java.util.*;
import java.io.*;
import java.math.*;

/**
 * https://www.hackerrank.com/contests/university-codesprint-3/challenges/black-white-tree
 *
 * Idea is that every node will pass on Max(W-B) and Max(B-W) it can form with its subtree. This can be calculated using DFS.
 * We can get maximum BW or WB post that. Moreover, DFS will also maintain parent relation for BW as well as WB so that the maximal subtree
 * can be obtained after we have the node with maximal value of strangeness.
 */
public class BlackWhiteTree {

    static int MaxWB[];
    static int MaxBW[];
    static int MaxWBParent[];
    static int MaxBWParent[];

    static List<Integer>[] graph;

    static int VISITING = 1;
    static int VISITED = 2;
    static int[] visited;

    private static void updateStrangenessDFS(int[] color, int vertex) {

        visited[vertex] = VISITING;

        if(graph[vertex]==null) {

            MaxWB[vertex] = 1-color[vertex];
            MaxBW[vertex] = color[vertex];

        }else {
            for (Integer v : graph[vertex]) {
                if(visited[v]==0) {
                    updateStrangenessDFS(color, v);
                }
            }

            //Now we will calculate value for this vertex;
            for (Integer v : graph[vertex]) {

                //This check is must or add check to ignore if v is parent of vertex. This is a simple check for the same.
                if(visited[v]==VISITED) {
                    MaxWBParent[v] = vertex;
                    MaxBWParent[v] = vertex;
                    MaxWB[vertex] += MaxWB[v];
                    MaxBW[vertex] += MaxBW[v];
                }
            }

            //Black color
            if(color[vertex]==1) {
                MaxWB[vertex] = Math.max(MaxWB[vertex]-1, 0);
                MaxBW[vertex] += 1;
            }else {
                MaxWB[vertex] += 1;
                MaxBW[vertex] = Math.max(MaxBW[vertex]-1,0);
            }
        }

        visited[vertex] = VISITED;

    }

    private static int maxStrangenessIndex(int n) {

        int maxWBIndex = 0;
        int maxBWIndex = 0;

        for(int i=1;i<n;i++) {
            if(MaxWB[i]>MaxWB[maxWBIndex])
                maxWBIndex = i;
            if(MaxBW[i]>MaxBW[maxBWIndex])
                maxBWIndex = i;
        }

        if(MaxWB[maxWBIndex]>MaxBW[maxBWIndex])
            return maxWBIndex;
        else
            return maxBWIndex;

    }

    private static List<Integer> maxStrangeSubtree(int root, boolean isWBMax) {

        List<Integer> subTree = new ArrayList<Integer>();
        Queue<Integer> bfsQueue = new LinkedList<Integer>();

        bfsQueue.add(root);
        visited[root] = 1;

        while(!bfsQueue.isEmpty()) {

            int vertex = bfsQueue.poll();
            subTree.add(vertex);
            if(graph[vertex]!=null) {
                for(Integer v: graph[vertex]) {
                    if(visited[v]==0) {
                        visited[v]=1;
                        if((isWBMax && MaxWB[v]>0 && MaxWBParent[v]==vertex) || (!isWBMax && MaxBW[v]>0 && MaxBWParent[v]==vertex))
                            bfsQueue.add(v);
                    }
                }
            }
        }


        return subTree;
    }

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int[] c = new int[n];

        MaxWB = new int[n];
        MaxBW = new int[n];
        MaxWBParent = new int[n];
        MaxBWParent = new int[n];
        visited = new int[n];

        Arrays.fill(visited, 0);
        for(int c_i = 0; c_i < n; c_i++){
            c[c_i] = in.nextInt();
        }

        graph = new ArrayList[n];

        for(int a0 = 0; a0 < n-1; a0++){
            int x = in.nextInt()-1;
            int y = in.nextInt()-1;

            if(graph[x]==null)
                graph[x] = new ArrayList<Integer>();

            if(graph[y]==null)
                graph[y] = new ArrayList<Integer>();

            graph[x].add(y);
            graph[y].add(x);
        }

        updateStrangenessDFS(c, 0);

        int maxStrangenessRoot = maxStrangenessIndex(n);
        System.out.println(Math.max(MaxBW[maxStrangenessRoot], MaxWB[maxStrangenessRoot]));

        boolean isWBMax = (MaxWB[maxStrangenessRoot]>MaxBW[maxStrangenessRoot])?true:false;

        Arrays.fill(visited, 0);
        List<Integer> subtree = maxStrangeSubtree(maxStrangenessRoot, isWBMax);

        System.out.println(subtree.size());
        Collections.sort(subtree);
        System.out.print(subtree.get(0)+1);
        for(int i=1;i<subtree.size();i++) {

            System.out.print(" "+(subtree.get(i)+1));
        }
        in.close();
    }
}
