import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;
import java.util.NoSuchElementException;

// Can enque items, and deques them randomly. Iterable, generic,
// resizing array
public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] a;
    private int count;

    // construct an empty randomized queue
    public RandomizedQueue() {
        a = (Item[]) new Object[1];
        count = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return count == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return count;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Argument cannot be null");
        }
        if (size() == a.length) {
            resize(2 * a.length);
        }
        a[size()] = item;
        count++;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is empty");
        }
        if (size() > 0 && size() == (a.length / 4)) {
            resize(a.length / 2);
        }
        int n = StdRandom.uniform(size());
        Item val = a[n];
        a[n] = a[size() - 1];
        a[size() - 1] = null;
        count--;
        return val;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is empty");
        }
        int n = StdRandom.uniform(size());
        return a[n];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomIterator();
    }

    private class RandomIterator implements Iterator<Item> {

        private Item[] rand;
        private int current;

        public RandomIterator() {
            rand = (Item[]) new Object[size()];
            for (int i = 0; i < size(); i++) {
                rand[i] = a[i];
            }
            StdRandom.shuffle(rand);
        }

        public boolean hasNext() {
            return current != size();
        }

        public void remove() {
        }

        public Item next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException("No further items to return");
            }
            Item item = rand[current];
            current++;
            return item;
        }
    }

    // Helper method to resize arrays
    private void resize(int newSize) {
        Item[] temp = (Item[]) new Object[newSize];
        for (int i = 0; i < size(); i++) {
            temp[i] = a[i];
        }
        a = temp;
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<String> test = new RandomizedQueue<String>();

        // Next 3 lines test the specified corner cases; uncomment as needed
        // test.enqueue(null);
        // test.dequeue();
        // test.sample();

        StdOut.println("Size is: " + test.size());
        StdOut.println("Empty? " + test.isEmpty());

        test.enqueue("A");
        test.enqueue("B");
        test.enqueue("C");
        test.enqueue("D");
        test.enqueue("E");

        StdOut.println("Iterator 1");
        for (String s : test) {
            StdOut.print(s + " ");
        }

        test.enqueue("F");
        test.enqueue("G");
        test.enqueue("H");
        test.enqueue("I");
        test.enqueue("J");

        StdOut.println("Size is: " + test.size());
        StdOut.println("Empty? " + test.isEmpty());

        StdOut.println(test.sample());
        StdOut.println("Size is: " + test.size());

        StdOut.println(test.dequeue());
        StdOut.println(test.dequeue());
        StdOut.println(test.dequeue());
        StdOut.println(test.dequeue());
        StdOut.println(test.dequeue());
        StdOut.println(test.dequeue());
        StdOut.println(test.dequeue());
        StdOut.println(test.dequeue());
        StdOut.println(test.dequeue());
        StdOut.println(test.dequeue());

        StdOut.println("Size is: " + test.size());
        StdOut.println("Empty? " + test.isEmpty());

        test.enqueue("M");
        test.enqueue("B");
        test.enqueue("C");
        test.enqueue("D");
        test.enqueue("E");
        test.enqueue("F");
        test.enqueue("G");
        test.enqueue("H");
        test.enqueue("I");
        test.enqueue("J");

        StdOut.println("\nIterator 2");
        for (String s : test) {
            StdOut.print(s + " ");
        }

        StdOut.println("\nIterator 3");
        for (String s : test) {
            StdOut.print(s + " ");
        }
        StdOut.println("\nIterator 4");
        for (String s : test) {
            StdOut.print(s + " ");
        }

        int n = 5;
        RandomizedQueue<Integer> queue = new RandomizedQueue<Integer>();
        for (int i = 0; i < n; i++)
            queue.enqueue(i);
        for (int a : queue) {
            for (int b : queue)
                StdOut.print(a + "-" + b + " ");
            StdOut.println();
        }
    }
}
