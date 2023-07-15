package ngordnet.main;

import edu.princeton.cs.algs4.SET;

import java.util.*;

public class Graph {
    // variables : what is our graph representation?
    // adjList, adjMatrix
    private int size;
    private HashMap connect; // adjList

    public Graph() {
        connect = new HashMap<>();
    }

    public void addEdge(int parent, int child) {
        /** checks for if there is any children currently in the parent
         * if yes, adds all previous children and the new child into list
         * and replaces previous adj node into the new list of
         **/
        // need to add children to parent. Would no longer need the node class since you would just need the int ids
        ArrayList edge = new ArrayList();
        if (connect.get(parent) != null) {
            edge = (ArrayList) connect.get(parent);
            edge.add(child);
            connect.put(parent, edge);
        } else {
            edge.add(child);
            connect.put(parent, edge);
        }
    }

    /**
     * Adds a new node and inputs the node into the connections hashmap.
     */
    public void addNode(int mainID) {
        if (!connect.containsKey(mainID)) {
            connect.put(mainID, null);
        }
    }

    /**
     * iterates through the current node in the stack, adds the children nodes into the stack, and adds id into the set
     *
     */
    public void dfs(List<Integer> stack, Set list, int node) {
        if (connect.get(node) == null) {
            return;
        }
        ArrayList lst = (ArrayList) connect.get(node);
        for (Object item : lst) {
            int noder = (int) item;
            stack.add(noder);
        }
    }
    public Set traverse(int id) {
        Set list = new HashSet();
        if (connect.get(id) == null) {
            list.add(id);
            return list;
        } else {
            int finder = id;
            List<Integer> stack = new ArrayList<>();
            stack.add(finder);
            while (!stack.isEmpty()) {
                dfs(stack, list, finder);
                list.add(finder);
                stack.remove(0);
                if (!stack.isEmpty()) {
                    finder = stack.get(0);
                }
            }
        }
        return list;
    }
}