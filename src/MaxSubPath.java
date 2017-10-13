import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

/**
 * https://www.hackerrank.com/contests/optimization-oct17/challenges/skipping-subpath-sum
 */

public class MaxSubPath {

    //We will do dfs from one root and store paths from root to all nodes along with visitStart and visitEndTime stamps
    static ArrayList<ArrayList<Integer>> rootPaths;
    static HashSet<Integer> queryVertices = new HashSet<Integer>();
    static boolean isTreeLong = false;


    static boolean dfs(ArrayList<ArrayList<Integer>> g, int v, int p, int target, ArrayList<Integer> path) {
        path.add(v);
        if (v == target) {
            return true;
        }
        for (int u : g.get(v)) {
            if (u == p) continue;
            boolean found = dfs(g, u, v, target, path);
            if (found) return true;
        }
        path.remove(path.size()-1);
        return false;
    }

    static void dfs(ArrayList<ArrayList<Integer>> g, int v, int p, ArrayList<Integer> path) {

        path.add(v);

        if(queryVertices.contains(v)) {
            ArrayList<Integer> subPath = rootPaths.get(v);
            for (int x : path) {
                subPath.add(x);
            }
        }

        //Do dfs
        for(int u:g.get(v)) {
            if(u!=p) {
                dfs(g,u,v,path);
            }
        }
        path.remove(path.size()-1);
    }

    static int lca(ArrayList<Integer> ruPath, ArrayList<Integer> rvPath, int left, int right) {

        if(left==right)
            return left;

        if((right-left) == 1) {
            if(ruPath.get(right).intValue()==rvPath.get(right).intValue())
                return right;
            return left;
        }
        int mid = (left+right)/2;
        if(ruPath.get(mid).intValue()==rvPath.get(mid).intValue())
            return lca(ruPath,rvPath,mid,right);
        else
            return lca(ruPath,rvPath,left,mid-1);

    }

    static ArrayList<Integer> getSubPath(int u, int v) {

        ArrayList<Integer> subPath = new ArrayList<Integer>();
        ArrayList<Integer> ruPath = rootPaths.get(u);
        ArrayList<Integer> rvPath = rootPaths.get(v);

        int ruPathLen = ruPath.size();
        int rvPathLen = rvPath.size();

        int start = 0;
        int minLen = Math.min(ruPathLen, rvPathLen);



        int i = lca(ruPath,rvPath,start,minLen-1);
        i++;

        //while ( i<ruPathLen && i<rvPathLen && ruPath.get(i) == rvPath.get(i))
        //  i++;
        //Common parent is i-1

        //first lets add u to commonparent and reverse
        int j = i - 1;
        for (j = i - 1; j < ruPath.size(); j++) {
            subPath.add(0,ruPath.get(j));
        }

        for (j = i; j < rvPath.size(); j++) {
            subPath.add(rvPath.get(j));
        }

        return subPath;
    }


    /*
     * Kadane's algorith: https://en.wikipedia.org/wiki/Maximum_subarray_problem
     */
    static int kadane(ArrayList<Integer> a) {
        if (a.isEmpty()) return 0;
        int max_ending_here = Math.max(a.get(0), 0);
        int max_so_far = max_ending_here;
        for (int i = 1; i < a.size(); ++i) {
            max_ending_here = Math.max(Math.max(0, a.get(i)), max_ending_here+a.get(i));
            max_so_far = Math.max(max_so_far, max_ending_here);
        }
        return max_so_far;
    }

    static int[] skippingSubpathSum(int n, int[] c,  ArrayList<ArrayList<Integer>> graph, ArrayList<ArrayList<Integer>> queries) {


        //If number of leaf nodes are less than n/4, its a long tree.
        int leafCount = 0;
        for(int i=0;i<n;i++) {
            if(graph.get(i).size()==1)
                leafCount++;
        }

        if(leafCount< n/4)
            isTreeLong = true;

        if(!isTreeLong) {
            rootPaths = new ArrayList<ArrayList<Integer>>();

            for (int i = 0; i < n; i++) {
                rootPaths.add(new ArrayList<Integer>());
            }

            for (int i = 0; i < queries.size(); i++) {
                queryVertices.add(queries.get(i).get(0));
                queryVertices.add(queries.get(i).get(1));
            }

            dfs(graph, 0, -1, new ArrayList<Integer>());
        }

        int answers[] = new int[queries.size()];
        for (int qid = 0; qid < queries.size(); ++qid) {
            int u = queries.get(qid).get(0);
            int v = queries.get(qid).get(1);

            ArrayList<Integer> path = new ArrayList<Integer>();
            if(!isTreeLong)
                path = getSubPath(u,v);
            else
                dfs(graph, u, -1, v, path);

            ArrayList<Integer> oddPath = new ArrayList<Integer>();
            ArrayList<Integer> evenPath = new ArrayList<Integer>();

            for (int i = 0; i < path.size(); ++i) {
                if ((i+1) % 2 == 0) {
                    evenPath.add(c[path.get(i)]);
                } else {
                    oddPath.add(c[path.get(i)]);
                }
            }
            int s1 = kadane(evenPath);
            int s2 = kadane(oddPath);
            answers[qid] = Math.max(s1, s2);
        }
        return answers;
    }

    public static void main(String[] args) throws IOException{

        RandomGraph.createNewInput();

        long startTime = System.currentTimeMillis();
        Scanner in = new Scanner(new File("/Users/ranjem/Desktop/input"));
        int n = in.nextInt();
        int[] c = new int[n];
        for(int c_i = 0; c_i < n; c_i++){
            c[c_i] = in.nextInt();
        }
        ArrayList<ArrayList<Integer>> graph = new ArrayList<ArrayList<Integer>>();
        for (int i = 0; i < n; ++i) {
            graph.add(new ArrayList<Integer>());
        }
        for (int i = 0; i < n-1; ++i) {
            int u = in.nextInt();
            int v = in.nextInt();
            graph.get(u).add(v);
            graph.get(v).add(u);
        }
        int q = in.nextInt();
        ArrayList<ArrayList<Integer>> queries = new ArrayList<ArrayList<Integer>>();
        for (int i = 0; i < q; ++i) {
            int u = in.nextInt();
            int v = in.nextInt();
            queries.add(new ArrayList<Integer>(Arrays.asList(u, v)));
        }
        int[] answers = skippingSubpathSum(n, c, graph, queries);
        for (int i = 0; i < answers.length; i++) {
            System.out.print(answers[i] + (i != answers.length - 1 ? "\n" : ""));
        }
        System.out.println("");

        long endTime = System.currentTimeMillis();

        System.out.println((endTime-startTime) + " *********");
        in.close();
    }
}
