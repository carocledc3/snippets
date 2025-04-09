package langaoen.jancess.kruskalsalgorithm;

import java.util.*;

public class KruskalsAlgorithm {

    static class Edge implements Comparable<Edge> {
        String src, dest;
        int weight;

        public Edge(String src, String dest, int weight) {
            this.src = src;
            this.dest = dest;
            this.weight = weight;
        }

        public int compareTo(Edge compareEdge) {
            return this.weight - compareEdge.weight;
        }

        public String toString() {
            return "(" + src + ", " + dest + ", " + weight + ")";
        }
    }

    static class Subset {
        int parent, rank;
    }

    static int find(Subset subsets[], int i) {
        if (subsets[i].parent != i)
            subsets[i].parent = find(subsets, subsets[i].parent);
        return subsets[i].parent;
    }

    static void union(Subset subsets[], int x, int y) {
        int xroot = find(subsets, x);
        int yroot = find(subsets, y);

        if (subsets[xroot].rank < subsets[yroot].rank)
            subsets[xroot].parent = yroot;
        else if (subsets[xroot].rank > subsets[yroot].rank)
            subsets[yroot].parent = xroot;
        else {
            subsets[yroot].parent = xroot;
            subsets[xroot].rank++;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean repeat;

        do {
            Set<String> verticesSet = new HashSet<>();
            List<Edge> edges = new ArrayList<>();

            System.out.println("Enter edges in format (A, B, 4), separated by space:");
            String input = sc.nextLine();
            String[] edgeInputs = input.split("\\s+");

            for (String edgeStr : edgeInputs) {
                edgeStr = edgeStr.replaceAll("[()]", ""); // Remove parentheses
                String[] parts = edgeStr.split(",");
                if (parts.length != 3) {
                    System.out.println("Invalid edge format: " + edgeStr);
                    continue;
                }
                String src = parts[0].trim();
                String dest = parts[1].trim();
                int weight = Integer.parseInt(parts[2].trim());

                verticesSet.add(src);
                verticesSet.add(dest);
                edges.add(new Edge(src, dest, weight));
            }

            List<String> vertices = new ArrayList<>(verticesSet);
            
            boolean allNumeric = true;
            for (String vertex : verticesSet) {
                if (!vertex.matches("\\d+")) {
                    allNumeric = false;
                    break;
                }
            }

            if (allNumeric) {
                vertices.sort(Comparator.comparingInt(Integer::parseInt));
            } else {
                Collections.sort(vertices);
            }
            
            int V = vertices.size();
            int[][] adjMatrix = new int[V][V];

            Map<String, Integer> vertexIndexMap = new HashMap<>();
            for (int i = 0; i < V; i++) {
                vertexIndexMap.put(vertices.get(i), i);
            }

            for (Edge edge : edges) {
                int i = vertexIndexMap.get(edge.src);
                int j = vertexIndexMap.get(edge.dest);
                adjMatrix[i][j] = edge.weight;
                adjMatrix[j][i] = edge.weight; // Undirected graph
            }

            Collections.sort(edges);

            Subset subsets[] = new Subset[V];
            for (int v = 0; v < V; ++v) {
                subsets[v] = new Subset();
                subsets[v].parent = v;
                subsets[v].rank = 0;
            }

            List<Edge> result = new ArrayList<>();
            int e = 0, i = 0;
            while (e < V - 1 && i < edges.size()) {
                Edge next_edge = edges.get(i++);
                int x = find(subsets, vertexIndexMap.get(next_edge.src));
                int y = find(subsets, vertexIndexMap.get(next_edge.dest));

                if (x != y) {
                    result.add(next_edge);
                    union(subsets, x, y);
                    e++;
                }
            }
            
            System.out.println("\nGIVEN");
            System.out.println("Vertices in the graph:");
            for (String v : vertices) {
                System.out.print(v + " ");
            }
            System.out.println("\n\nAdjacency Matrix of the Graph (Before Kruskal's Algorithm):");
            System.out.print("    "); // Adjust for row labels
            for (String v : vertices) {
                System.out.printf("%-4s", v); // Ensure 4-character spacing
            }
            System.out.println();

            // Print each row of the adjacency matrix
            for (int r = 0; r < V; r++) {
                System.out.printf("%-4s", vertices.get(r)); // Print row labels with spacing
                for (int c = 0; c < V; c++) {
                    System.out.printf("%-4d", adjMatrix[r][c]); // Print matrix values with spacing
                }
                System.out.println();
            }

            System.out.println("\n\nMINIMUM SPANNING TREE");
            System.out.print("Edges in the Minimum Spanning Tree:\n");
            int minCost = 0;
            for (int idx = 0; idx < result.size(); idx++) {
                Edge edge = result.get(idx);
                System.out.print(edge);
                minCost += edge.weight;
                if (idx < result.size() - 1) {
                    System.out.print("  ");
                }
            }
            System.out.println(); // move to next line

            // Create adjacency matrix for MST
            int[][] mstMatrix = new int[V][V];
            for (Edge edge : result) {
                int i1 = vertexIndexMap.get(edge.src);
                int i2 = vertexIndexMap.get(edge.dest);
                mstMatrix[i1][i2] = edge.weight;
                mstMatrix[i2][i1] = edge.weight;
            }

            System.out.println("\nAdjacency Matrix in MST (After Kruskal's Algorithm):");
            System.out.print("    ");
            for (String v : vertices) {
                System.out.printf("%-4s", v);
            }
            System.out.println();

            // Print each row of the MST adjacency matrix
            for (int r = 0; r < V; r++) {
                System.out.printf("%-4s", vertices.get(r));
                for (int c = 0; c < V; c++) {
                    System.out.printf("%-4d", mstMatrix[r][c]);
                }
                System.out.println();
            }

            System.out.println("\nMinimum Cost: " + minCost);

            System.out.print("\nDo you want to try again? (yes/no): ");
            String again = sc.nextLine().trim().toLowerCase();
            repeat = again.equals("yes");

        } while (repeat);

        sc.close();
        System.out.println("Program terminated.");
    }
}
