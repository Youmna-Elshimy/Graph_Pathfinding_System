public class Heap {
    // declaring global variables
    private Vertex[] heap;
    private int size, currentPosition = -1;

    // constructor
    public Heap(int size) {
        this.size = size;
        this.heap = new Vertex[size];
    }

    /*
     * function to insert vertex into a heap array while incrementing the
     * current position and using it as an index for the vertex object.
     * This function also calls the siftUp method in order to readjust the heap
     * according to the new vertex added,
     */
    public void insert(Vertex vertex) {
        heap[++currentPosition] = vertex;
        siftUp(currentPosition);
    }

    // function to swap vertex places
    private void swap(int currentPlace, int secondPlace) {
        Vertex temporaryVertex = heap[currentPlace];
        heap[currentPlace] = heap[secondPlace];
        heap[secondPlace] = temporaryVertex;
    }

    // function to re-adjust the heap by moving vertex up
    public void siftUp(int child) {
        // if child is 0 break
        if (child == 0) {
            return;
        }

        // declaring the value of the parent of the child
        int parent = (child - 1) / 2;

        /*
         * if the child has lower cost, then swap the child
         * and parent and perform siftUp on the parent.
         */
        if (heap[parent].getCost() > heap[child].getCost()) {
            swap(child, parent);
            siftUp(parent);
        }
    }

    // function to remove vertex from heap
    public Vertex remove() {
        // declaring variables
        Vertex vertex = heap[0];

        /*
         * if heap is empty then return null. If there is only 1 vertex, then decrement
         * currentPosition to -1 and return the root (the only remaining vertex). Else
         * swap the root with the vertex at currentPosition, then decrement the currentPosition
         * and size, perform siftDown on root, and return the removed vertex.
         */
        if (currentPosition == -1) {
            return null;
        } else if (currentPosition == 0) {
            currentPosition--;
            return vertex;
        } else {
            swap(0, currentPosition);
            currentPosition--;
            this.size--;
            siftDown(0);
            return vertex;
        }
    }

    // function to re-adjust the heap by moving vertex down
    public void siftDown(int node) {
        // declaring the value of the left child node
        int child = 2 * node + 1;

        // if the child is not within the valid range of indices in the heap, then break
        if (child >= currentPosition) {
            return;
        }

        /*
         * if there is a vertex to the right within the array boundary and if
         * the cost of the right neighbour is less than that of the child,
         * then set index to child with lower cost
         */
        if (child + 1 < currentPosition) {
            if (heap[child].getCost() > heap[child + 1].getCost()) {
                child++;
            }
        }

        /*
         * if node has higher cost than child, then swap
         * node and child and perform siftDown on child
         */
        if (heap[node].getCost() > heap[child].getCost()) {
            swap(node, child);
            siftDown(child);
        }
    }
}
