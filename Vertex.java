public class Vertex {
    // declaring global variables
    private int id, edgeTotal = 0;
    private double x, y, cost, totalWeight, weight;
    private boolean visited = false;
    private Vertex[] edges = new Vertex[100];
    private double[] weights = new double[100];

    // constructor
    public Vertex(int id, double x, double y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }

    // getter
    public int getId() {
        return this.id;
    }

    // getter
    public double getX() {
        return this.x;
    }

    // getter
    public double getY() {
        return this.y;
    }

    // getter
    public Vertex[] getEdges() {
        return this.edges;
    }

    // getter
    public double[] getWeights() {
        return this.weights;
    }

    // getter
    public int getEdgesTotal() {
        return this.edgeTotal;
    }

    // getter
    public double getWeight() {
        return this.weight;
    }

    // setter
    public void setWeight(double weight) {
        this.weight = weight;
        this.totalWeight += weight;
    }

    // getter
    public double getTotalWeight() {
        return this.totalWeight;
    }

    // getter
    public double getCost() {
        return this.cost;
    }

    // setter
    public void setCost(double totalValue) {
        this.cost = totalValue;
    }

    // getter
    public boolean getVisited() {
        return this.visited;
    }

    // function to set visited state of the vertex
    public void visited() {
        if (this.visited == false) {
            this.visited = true;
        }
        else {
            this.visited = false;
        }
    }

    // function to add a new edge and its weight to the edges and weights arrays respectively
    public void addEdge(Vertex edge, double weight) {
        edges[this.edgeTotal] = edge;
        weights[this.edgeTotal] = weight;
        this.edgeTotal++;
    }
}
