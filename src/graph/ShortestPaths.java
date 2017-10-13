package graph;
import util.UndirectedGraph;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;


/**
 * https://www.hackerrank.com/challenges/dijkstrashortreach/problem
 * Created by ranjem on 9/12/17.
 *
 * Idea: We will start with given node and keep adding one node at a time with minimum distance from current set of nodes.
 *
 */
public class ShortestPaths {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int t = in.nextInt();
        for(int a0 = 0; a0 < t; a0++){
            int n = in.nextInt();
            int m = in.nextInt();

            UndirectedGraph graph = new UndirectedGraph(n);
            for(int a1 = 0; a1 < m; a1++){
                int x = in.nextInt();
                int y = in.nextInt();
                int r = in.nextInt();
                graph.addEdge(x,y,r,true);
            }
            int s = in.nextInt();


        }
    }
}
