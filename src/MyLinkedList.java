import java.util.Arrays;
import java.util.Scanner;

/**
 * @author Lestat Louis C Ariola BSIT-IS 2A 2021-2022 PERFORMANCE TASK 3:
 *         LinkedList
 */
public class MyLinkedList<AnyType> implements Iterable<AnyType> {

    private static class Node<AnyType> {

        public Node(AnyType d, Node<AnyType> p, Node<AnyType> n) {
            data = d;
            prev = p;
            next = n;
        }

        public AnyType data;
        public Node<AnyType> prev;
        public Node<AnyType> next;
    }

    public MyLinkedList() {
        doClear();
    }

    public void clear() {
        doClear();
    }

    private void doClear() {

        beginMarker = new Node<AnyType>(null, null, null);
        endMarker = new Node<AnyType>(null, beginMarker, null);
        beginMarker.next = endMarker;

        theSize = 0;
        modCount++;
    }

    public int size() {
        return theSize;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public boolean add(AnyType x) {
        add(size(), x);
        return true;
    }

    public void add(int idx, AnyType x) {
        addBefore(getNode(idx, 0, size()), x);
    }

    private void addBefore(Node<AnyType> p, AnyType x) {
        Node<AnyType> newNode = new Node<>(x, p.prev, p);
        newNode.prev.next = newNode;
        p.prev = newNode;
        theSize++;
        modCount++;
    }

    public AnyType get(int idx) {
        return getNode(idx).data;
    }

    public AnyType set(int idx, AnyType newVal) {
        Node<AnyType> p = getNode(idx);
        AnyType oldVal = p.data;
        p.data = newVal;
        return oldVal;
    }

    public AnyType remove(int idx) {
        return remove(getNode(idx));
    }

    private AnyType remove(Node<AnyType> p) {
        p.next.prev = p.prev;
        p.prev.next = p.next;
        theSize--;
        modCount++;
        return p.data;
    }

    private Node<AnyType> getNode(int idx) {
        return getNode(idx, 0, size() - 1);
    }

    private Node<AnyType> getNode(int idx, int lower, int upper) {
        Node<AnyType> p;

        if (idx < lower || idx > upper)
            throw new IndexOutOfBoundsException();
        if (idx < size() / 2) {
            p = beginMarker.next;
            for (int i = 0; i < idx; i++)
                p = p.next;
        } else {
            p = endMarker;
            for (int i = size(); i > idx; i--)
                p = p.prev;
        }
        return p;
    }

    public java.util.Iterator<AnyType> iterator() {
        return new LinkedListIterator();
    }

    private class LinkedListIterator implements java.util.Iterator<AnyType> {
        private Node<AnyType> current = beginMarker.next;
        private int expectedModCount = modCount;
        private boolean okToRemove = false;

        public boolean hasNext() {
            return current != endMarker;
        }

        public AnyType next() {
            if (modCount != expectedModCount)
                throw new java.util.ConcurrentModificationException();
            if (!hasNext())
                throw new java.util.NoSuchElementException();

            AnyType nextItem = current.data;
            current = current.next;
            okToRemove = true;
            return nextItem;
        }

        public void remove() {
            if (modCount != expectedModCount)
                throw new java.util.ConcurrentModificationException();
            if (!okToRemove)
                throw new IllegalStateException();

            // MyLinkedList.false.remove(current.prev);
            MyLinkedList.this.remove(current.prev);
            expectedModCount++;
            okToRemove = false;
        }
    }

    public void showMenu() {

        System.out.println("\n\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
        System.out.println("[1] SHOW LIST \n[2] ADD \n[3] SEARCH \n[4] REMOVE\n[5] REPLACE \n[6] EXIT \n");

        System.out.print("YOUR CHOICE: ");
    }

    public void showList() {

        if (!isEmpty()) {

            sort();
            for (int i = 0; i < size(); i++) {
                int idx = i + 1;
                System.out.println("[" + idx + "] " + this.get(i));
            }
        } else {
            System.out.println("ARRAY IS EMPTY!!!");
        }
    }

    public void sort() {

        Integer[] tempIntegers = new Integer[theSize];

        for (int i = 0; i < size(); i++) {
            tempIntegers[i] = (Integer) this.get(i);
        }
        Arrays.sort(tempIntegers);

        for (int i = 0; i < tempIntegers.length; i++) {
            this.set(i, (AnyType) tempIntegers[i]);
        }
    }

    private int theSize;
    private int modCount;
    private Node<AnyType> beginMarker;
    private Node<AnyType> endMarker;

    public static void main(String args[]) {

        Scanner keyboard = new Scanner(System.in);
        MyLinkedList<Integer> myList = new MyLinkedList<Integer>();
        boolean userExit = false;

        do {
            myList.showMenu();
            char choice = keyboard.next().charAt(0);

            switch (choice) {
                case '1':
                    myList.showList();
                    break;
                case '2':
                    System.out.println("ADD AN ELEMENT");
                    System.out.print("ELEMENT: ");
                    Integer element = keyboard.nextInt();
                    myList.add(element);
                    break;
                case '3':
                    myList.showList();
                    System.out.println("SEARCH AN ELEMENT BY IT'S INDEX");
                    System.out.print("INDEX: ");
                    int index = keyboard.nextInt();
                    index = index - 1;
                    System.out.println("\nSEARCH RESULTS\nREAL_INDEX: " + index + "\nVALUE: " + myList.get(index));
                    break;
                case '4':
                    myList.showList();
                    System.out.println("REMOVE AN ELEMENT");
                    System.out.print("INDEX OF ELEMENT: ");
                    int idxToRemove = keyboard.nextInt();
                    idxToRemove--;
                    myList.remove(idxToRemove);
                    System.out.println("ELEMENT REMOVED");
                    myList.showList();
                    break;
                case '5':
                    myList.showList();
                    System.out.println("REPLACE AN ELEMENT");
                    System.out.print("INDEX TO REPLACE: ");
                    int idxToReplace = keyboard.nextInt();
                    idxToReplace--;

                    System.out.print("NEW VALUE: ");
                    Integer newValue = keyboard.nextInt();
                    myList.set(idxToReplace, newValue);

                    System.out.println("ELEMENT REPLACED");
                    myList.showList();
                    break;
                case '6':
                    userExit = true;
                    keyboard.close();
                    System.out.println("PROGRAM EXITING... ");
                    break;
                default:
                    System.out.println("INVALID CHOICE. [1 - 6] ONLY");
            }
        } while (!userExit);
    }
}