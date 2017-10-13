/**
 * https://www.hackerrank.com/challenges/journey-to-the-moon
 */
import java.io.*;
import java.util.*;

public class MoonJourney {

    static class Edge {
        int v;

        Edge(int v) {
            this.v = v;
        }
    }

    static List<Edge>[] graph;

    static long componentSizeByDFS(int vertex, boolean[] visited) {

        visited[vertex] = true;
        long count = 1;

        if(graph[vertex]!=null) {
            for (Edge e : graph[vertex]) {
                if (!visited[e.v]) {
                    count += componentSizeByDFS(e.v, visited);
                }
            }
        }
        return count;
    }

    static long getAllComponentSizes(int n) {

        List<Long> componentSizes = new ArrayList<Long>();
        boolean[] visited = new boolean[n];

        for(int i=0;i<n;i++) {
            if(!visited[i]) {
                componentSizes.add(componentSizeByDFS(i, visited));
            }
        }

        if(componentSizes.size()==1)
            return 0;

        long result = 0;

        //To mitigate Timeout exception use fact (a+b+c ...)^2

        long temp = 0;
        long sum = 0;
        for(int i=0;i<componentSizes.size();i++) {
            temp = temp + (long)Math.pow(componentSizes.get(i),2);
            sum += componentSizes.get(i);
        }

        result = ((long)Math.pow(sum,2)-temp)/2;

        /*for(int i=0;i<(componentSizes.size()-1);i++) {
            for(int j=i+1;j<componentSizes.size();j++) {
                result += componentSizes.get(i)*componentSizes.get(j);
            }
        }*/

        return result;
    }

    public static void main(String[] args) throws Exception{
        BufferedReader bfr = new BufferedReader(new InputStreamReader(System.in));
        String[] temp = bfr.readLine().split(" ");
        int N = Integer.parseInt(temp[0]);
        int I = Integer.parseInt(temp[1]);

        graph = new ArrayList[N];

        for(int i = 0; i < I; i++){
            temp = bfr.readLine().split(" ");
            int a = Integer.parseInt(temp[0]);
            int b = Integer.parseInt(temp[1]);

            if(graph[a]==null)
                graph[a]=new ArrayList<Edge>();

            if(graph[b]==null)
                graph[b]=new ArrayList<Edge>();

            Edge e = new Edge(a);
            graph[b].add(e);
            e = new Edge(b);
            graph[a].add(e);

            // Store a and b in an appropriate data structure of your choice
        }

        // Compute the final answer - the number of combinations

        System.out.println(getAllComponentSizes(N));

    }
}
