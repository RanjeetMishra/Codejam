import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

/**
 * https://www.hackerrank.com/contests/university-codesprint-3/challenges/simple-tree-counting
 *
 * DS for 1 and 2 query
 * Map<Vertex_Color, Component>
 *     Query 2. When it asks for P(X) for an edge, just fetch Component of vertex color and return O(1)
 *     Query 1. Change color for u,v
 *              Break component for u_color and v_color, update map
 *              Components for u_newcolor and  v_newcolor might get merged or new component might get added
 *
 *
 * DS for query 3
 *    As its range query, we need to use segment tree or a Binary Search Tree with augmented values. As new color codes can be added, we need to use
 *    BST to make sure range query can be answered in O(log n)
 *
 *    BTree node { color, List<Components>, Range_Sum Value}
 *
 *    Modifications on BTree
 *    1. add a new node
 *    2. delete a node
 *    3. update a node
 *    3. Range sum query for l,r which is finding LCA for l and r color value.
 *
 */
public class TreeCounting {

    //Initially, find all components for different color, keep updating vertexColorMap, componentNode and BinarySearchTree.
    //key is vertex_color
    static Map<String, Component> vertexColorMap = new HashMap<>();
    static Map<Component, BSTNode> componentNode = new HashMap<>();
    static BinarySearchTree colorBST = new BinarySearchTree();

    //instead of edge color in graph we will maintain a map for edge color. We will save for u_v as well as v_u and update both.
    static EdgeColors edgeColorData = new EdgeColors();
    static HashSet<Integer> colorSet = new HashSet<>();

    //Graph
    static List<Integer> [] graph;

    /*For operation 1 u,v,newcolor old
        If newcolor is same ... no change
        If newcolor is a new color alltogether, means current comonent needs to be broken only.

            update edgeColorData
            update colorSet
            update vertexColorMap and colorBST

            Say current component is C, C1, C2 and C3 are new components.
            BSTNode node = componentNode.getNode(C);
            node.remove(C);
            node.add(C'); //probably only One Component or two
            colorBST.update(node);

            BSTNode newNode = ?;
            colorBST.add(newNode);
            update

        If newcolor is an existing  color
            All operations mentioned before

            For this new color, we have to see if we can combine two compoents
            C1 = vertexColorMap.getComponent
            C2 = vertexColorMap.getComponent

            If C1 and C2 not null, combine them
            update vertexColorMap ... //This can be O(N) operation, hence we have to represent component by a parent node. As searching parent node will be O(logn)



    */


    static class EdgeColors {

        Map<String, Integer> edgeColorMap;
        EdgeColors() {
            edgeColorMap = new HashMap<>();
        }

        public void addEdge(int u, int v, int color) {
            edgeColorMap.put(u+"_"+v, color);
            edgeColorMap.put(v+"_"+u, color);
        }

        public int getColor(int u, int v) {
            return edgeColorMap.get(u+"_"+v);
        }
    }
    static class Component {
        int color;
        HashSet<Integer> vertices;
    }

    static class BSTNode {

        int color;
        List<Component> componentList;
        long subTreeSum;
        long nodeSum;
        BSTNode left;
        BSTNode right;
        BSTNode parent;

        BSTNode() {

        }

    }

    static class BinarySearchTree {


        BSTNode root;

        BinarySearchTree() {
            root = null;
        }

        public void add(BSTNode node) {

        }

        public void remove(BSTNode node) {

        }

        public void update(BSTNode node) {
            remove(node);
            add(node);
        }

        public BSTNode LCA(BSTNode node1, BSTNode node2) {

            return null;
        }
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        for(int a0 = 0; a0 < n-1; a0++){
            int a = in.nextInt();
            int b = in.nextInt();
            int c = in.nextInt();
            // Write Your Code Here
        }
        int q = in.nextInt();
        for(int a0 = 0; a0 < q; a0++){
            // Write Your Code Here
        }
        in.close();
    }
}
