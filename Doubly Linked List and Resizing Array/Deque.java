import edu.princeton.cs.algs4.StdOut;
import java.util.Iterator;
import java.util.NoSuchElementException;

// Double ended queue. Can add and remove items both, from the
// front as well as the end. Iterable and generic.
public class Deque<Item> implements Iterable<Item> {

    private Node first; // first node
    private Node last; // last node
    private int count; // total number of items

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        count = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return count == 0;
    }

    // return the number of items on the deque
    public int size() {
        return count;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Argument cannot be null");
        }
        if (isEmpty()) {
            first = new Node();
            first.item = item;
            last = first;
        }
        else {
            Node newFirst = new Node();
            newFirst.item = item;
            newFirst.next = first;
            first.prev = newFirst;
            first = newFirst;
        }
        count++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Argument cannot be null");
        }
        if (isEmpty()) {
            last = new Node();
            last.item = item;
            first = last;
        }
        else {
            Node newLast = new Node();
            newLast.item = item;
            newLast.prev = last;
            last.next = newLast;
            last = newLast;
        }
        count++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is empty");
        }
        Item val = first.item;
        if (size() == 1) {
            first = null;
            last = null;
        }
        else {
            first = first.next;
            first.prev = null;
        }
        count--;
        return val;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is empty");
        }
        Item val = last.item;
        if (size() == 1) {
            first = null;
            last = null;
        }
        else {
            last = last.prev;
            last.next = null;
        }
        count--;
        return val;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    // Iterator class
    private class DequeIterator implements Iterator<Item> {

        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
        }

        public Item next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException("No further items to return");
            }
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    // Node Method
    private class Node {
        Item item;
        Node next;
        Node prev; // To allow for a doubly linked list
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<String> test = new Deque<String>();

        // Next 4 lines test the specified corner cases; uncomment as needed
        // test.removeFirst();
        // test.removeLast();
        // test.addFirst(null);
        // test.addLast(null);

        StdOut.println("Is it empty? " + test.isEmpty());
        StdOut.println("What is the size? " + test.size());

        test.addLast("String 3 ");
        test.addFirst("String 2 ");
        test.addFirst("String 1 ");

        StdOut.println("Is it empty? " + test.isEmpty());
        StdOut.println("What is the size? " + test.size());

        StdOut.println("Iteration begins");
        for (String s : test) {
            StdOut.println(s);
        }

        StdOut.println(test.removeFirst() + " has been removed. ");
        StdOut.println(test.removeLast() + " has been removed. ");

        StdOut.println("Is it empty? " + test.isEmpty());
        StdOut.println("What is the size? " + test.size());

        StdOut.println(test.removeFirst() + " has been removed. ");

        StdOut.println("Is it empty? " + test.isEmpty());
        StdOut.println("What is the size? " + test.size());

        test.addLast("String 3 ");
        test.addFirst("String 2 ");
        test.addFirst("String 1 ");

        StdOut.println("Iteration begins");
        for (String s : test) {
            StdOut.println(s);
        }

        int n = 5;
        Deque<Integer> queue = new Deque<Integer>();
        for (int i = 0; i < n; i++)
            queue.addLast(i);
        for (int a : queue) {
            for (int b : queue)
                StdOut.print(a + "-" + b + " ");
            StdOut.println();
        }
    }
}
