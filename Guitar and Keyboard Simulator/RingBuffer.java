// Ring Buffer of capacity n

public class RingBuffer {

    // Instance Variables
    private int first;
    private int last;
    private int size;
    private final int n;
    private double[] ring;

    // creates an empty ring buffer with the specified capacity (Constructor)
    public RingBuffer(int capacity) {
        n = capacity;
        first = 0;
        last = 0;
        size = 0;
        ring = new double[n];
    }

    // return the capacity of this ring buffer
    public int capacity() {
        return n;
    }

    // return number of items currently in this ring buffer
    public int size() {
        return size;
    }

    // is this ring buffer empty (size equals zero)?
    public boolean isEmpty() {
        if (size == 0)
            return true;
        return false;
    }

    // is this ring buffer full (size equals capacity)?
    public boolean isFull() {
        if (size == capacity())
            return true;
        return false;
    }

    // adds item x to the end of this ring buffer
    public void enqueue(double x) {
        if (this.isFull())
            throw new RuntimeException("buffer is full");
        if (last == n)
            last = 0; // Wrap-around
        ring[last] = x;
        last++;
        size++;
    }

    // deletes and returns the item at the front of this ring buffer
    public double dequeue() {
        if (this.isEmpty())
            throw new RuntimeException("buffer is empty");
        double ret = ring[first];
        first++;
        size--;
        if (first == n)
            first = 0; // Wrap-around
        return ret;
    }

    // returns the item at the front of this ring buffer
    public double peek() {
        if (this.isEmpty())
            throw new RuntimeException("buffer is empty");
        return ring[first];
    }

    // tests and calls every instance method in this class
    public static void main(String[] args) {

        RingBuffer ring1 = new RingBuffer(5);

        StdOut.println(ring1.capacity());
        ring1.enqueue(1);
        ring1.enqueue(2);
        ring1.enqueue(3);
        StdOut.println(ring1.size);
        ring1.enqueue(4);
        ring1.enqueue(5);
        //ring1.enqueue(6); // Tests isFull
        StdOut.println(ring1.peek());
        ring1.dequeue();
        ring1.dequeue();
        ring1.dequeue();
        ring1.dequeue();
        ring1.dequeue();
        //ring1.dequeue(); // Tests issEmpty
    }

}
