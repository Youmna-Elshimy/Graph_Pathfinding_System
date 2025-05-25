// identifying headers
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Path {
    // declaring global variables
    private Vertex[] vertices = new Vertex[20];
    private Heap connectedVertices;
    private static Vertex[] shortestPath = new Vertex[20];
    private static int shortIndex = 0;
    private Vertex[] longestPath = new Vertex[20];
    private int totalVertices, totalEdges, totalWeight, longestLength = 0, startID, goalID;
    private static Vertex start, goal;

    // main function
    public static void main(String[] args) throws FileNotFoundException {
        /*
         * creating a scanner object, reading file name, and storing it then
         * creating an object of type File with the file name as a parameter
         */
        Scanner input = new Scanner(System.in);
        System.out.print("Please Enter the Filename: ");
        String fileName = input.nextLine();
        File file = new File(fileName);

        /*
        * creating an object of this class then using it to call
        * function to read file contents then close scanner object
        */
        Path path = new Path();
        path.readFileContents(file);
        input.close();

        /*
        * setting the cost for the starting vertex, adding it
        * to the shortest path, then setting it as visited
        */
        start.setCost(0 + calculateEuclideanDistance(start, goal));
        shortestPath[shortIndex++] = start;
        start.visited();

        /*
         * using the previously created object of this class to find
         * the shortest and longest paths then print the results
         */
        path.findShortestPath();
        path.findLongestPath();
        path.printData();
    }

    // function to read file contents and throwing an error if not possible
    private void readFileContents(File file) throws FileNotFoundException {
        try (Scanner input = new Scanner(file)) {
            // reading in the total vertices and edges
            totalVertices = input.nextInt();
            totalEdges = input.nextInt();

            /*
            * creating a vertex object and adding the vertices
            * along with their ids and x & y coordinates to array
            */
            for (int i = 0; i < totalVertices; i++) {
                vertices[i] = new Vertex(input.nextInt(), input.nextDouble(), input.nextDouble());
            }

            /*
            * reading the ids of the two vertices connected by the edge and the weight,
            * then searching for the corresponding vertices based on their ids within
            * the vertices array and adding the edge with its weight between them.
            */
            for (int i = 0; i < totalEdges; i++) {
                int edgeA = input.nextInt();
                int edgeB = input.nextInt();
                double weight = input.nextDouble();
                for (Vertex vertex : vertices) {
                    if (edgeA == vertex.getId()) {
                        for (Vertex destination : vertices) {
                            if (edgeB == destination.getId()) {
                                vertex.addEdge(destination, weight);
                            }
                        }
                    }
                }
            }
            // reading in the starting vertex and the ending one
            startID = input.nextInt();
            goalID = input.nextInt();
            // setting the starting and ending position vertices
            start = vertices[startID - 1];
            goal = vertices[goalID - 1];
        } catch (FileNotFoundException e) {
            System.out.println("Cannot find file. Please check your input and directory.");
        }
    }

    // function to calculate the euclidean distance
    private static double calculateEuclideanDistance(Vertex a, Vertex b) {
        double distX = b.getX() - a.getX();
        double distY = b.getY() - a.getY();
        return Math.sqrt((distX * distX) + (distY * distY));
    }

    // function to find the shortest path
    private void findShortestPath() {
        /*
        * keep adding vertices into the shortest path array until the ending vertex
        * is reached or until all vertices are added to the shortestPath array
        * */
        while (shortIndex < 20) {
            /*
            * identifying the index value of the current vertex
            * (the one most recently added to the shortestPath array)
            */
            int currentVertex = shortIndex - 1;

            /*
            * creating a new heap object that represents a min-heap to
            * temporarily store vertices connected to the currentVertex
            */
            this.connectedVertices = new Heap(shortestPath[currentVertex].getEdgesTotal());

            /*
            * iterating through the edges of the current vertex then for each edge the
            * connected vertex and the weight of the edge (the cost of reaching the
            * connected vertex from the current vertex) are retrieved. Then the weight of
            * the node is set to be the same as the weight of the edge. Then the cost of
            * the connecting vertex is calculated and set as the sum of its total weight
            * and the Euclidean distance to the goal vertex. The connected vertex is then
            * inserted into the connectedVertices heap.
            */
            for (int i = 0; i < shortestPath[currentVertex].getEdgesTotal(); i++) {
                Vertex connectedVertex = shortestPath[currentVertex].getEdges()[i];
                double connectedVertexWeight = shortestPath[currentVertex].getWeights()[i];

                connectedVertex.setWeight(connectedVertexWeight);
                connectedVertex.setCost(connectedVertex.getTotalWeight() + calculateEuclideanDistance(connectedVertex, goal));

                this.connectedVertices.insert(connectedVertex);
            }

            /*
            * Removing the vertex with the lowest cost from the connectedVertices heap and
            * assigning it to shortestEdge. Adding the weight of shortestEdge to the totalWeight
            * to keep track of the shortest path's total weight. Adding the shortestEdge to the
            * shortestPath array at the shortIndex position. Lastly, checking if the shortestEdge
            * is equal to the goal vertex. If it is, then the shortest path has been found.
            */
            Vertex shortestEdge = this.connectedVertices.remove();
            totalWeight += shortestEdge.getWeight();
            shortestPath[shortIndex++] = shortestEdge;
            if (shortestEdge == goal) {
                return;
            }
        }
    }

    // function to find the longest path
    private void findLongestPath() {
        // creating a new array for the long path
        Vertex[] longPath = new Vertex[20];
        /*
        * adding the starting vertex to the start of the long path array and
        * setting the weight to zero then calling the trackPaths function to
        * explore the possible paths from the starting vertex to the ending vertex
        */
        int currentPathIdex = 0;
        longPath[currentPathIdex] = start;
        start.setWeight(0);
        trackPaths(start, goal, longPath, currentPathIdex);
    }

    /*
    * recursive function to explore all paths the starting vertex to the ending vertex while visiting
    * each vertex only once. It also builds the longPath array as it explores, and when the goal is
    * reached, it checks if the current path is longer than the longest path found so far and updates
    * it if necessary.
    */
    private void trackPaths(Vertex currentVertex, Vertex goalVertex, Vertex[] longPath, int currentPathIdex) {
        // if the current and the goal vertices are the same
        if (currentVertex == goalVertex) {
            // declaring variable
            int totalLongWeight = 0;
            // covering all the vertices in the graph and updating the total weight
            if (currentPathIdex == totalVertices - 1) {
                for (int i = 0; i < totalVertices; i++) {
                    totalLongWeight += longPath[i].getWeight();
                }
                /*
                * if the total weight of the current path is greater than the current
                * longest path stored, update the current longest path stored to be the
                * same as the total weight of the current path and update the vertices as well.
                */
                if (totalLongWeight > longestLength) {
                    longestLength = totalLongWeight;
                    for (int i = 0; i < totalVertices; i++) {
                        longestPath[i] = longPath[i];
                    }
                }
            }
            // return the results
            return;
        }
        // mark the current vertex as visited
        currentVertex.visited();

        // if the current vertex is not the goal then check the adjacent vertices to the current vertex
        for (int i = 0; i < currentVertex.getEdgesTotal(); i++) {
            // get the adjacent vertex
            Vertex connectedVertex = currentVertex.getEdges()[i];
            /*
            * if the adjacent vertex has not been visited and is not the starting vertex,
            * then add it to the long path array, set its weight, and call the trackPaths
            * function recursively until the ending vertex is reached.
            */
            if (!connectedVertex.getVisited() && (connectedVertex.getId() != startID)) {
                longPath[currentPathIdex + 1] = connectedVertex;
                connectedVertex.setWeight(currentVertex.getWeights()[i]);
                trackPaths(connectedVertex, goal, longPath, currentPathIdex + 1);
            }
        }
        // mark the current vertex as visited
        currentVertex.visited();
    }

    // function to print the results
    private void printData() {
        System.out.println();
        System.out.println("=================================================================================================================================\n\n"
                           + "The number of vertexes in the graph: " + totalVertices + "\n\n"
                           + "The number of edges in the graph: " + totalEdges + "\n\n"
                           + "The start vertexes: " + startID + "\n\n"
                           + "The end vertexes: " + goalID + "\n\n"
                           + "=================================================================================================================================\n"
        );
        System.out.printf("The Euclidean distance between the start and the goal vertices: %.5f %n%n", calculateEuclideanDistance(start, goal));

        System.out.print("Shortest path: ");
        /*
         * going through the shortestPath array to print the vertices followed by "->"
         * and if the last one is reached, then print it without the "->".
         */
        for (int i = 0; i < shortIndex; i++) {
            if (i == shortIndex - 1) {
                System.out.println(shortestPath[i].getId());
            }
            else {
                System.out.print(shortestPath[i].getId() + " -> ");
            }
        }
        System.out.println("\nThe length of the shortest path: " + totalWeight + "\n");

        System.out.print("Longest path: ");
        /*
        * going through the longestPath array to print the vertices followed by "->"
        * and if the last one is reached, then print it without the "->".
         */
        for (int i = 0; i < totalVertices; i++) {
            if (i == totalVertices - 1) {
                System.out.println(longestPath[i].getId());
            }
            else {
                System.out.print(longestPath[i].getId() + " -> ");
            }
        }
        System.out.println("\nThe length of the longest path: " + longestLength + "\n\n"
                           + "================================================================================================================================="
        );
    }
}
