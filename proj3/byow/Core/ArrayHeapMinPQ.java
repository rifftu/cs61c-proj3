package byow.Core;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;


public class ArrayHeapMinPQ<T> {

    private final int iS = 16;
    private final double mL = 0.25;
    private Node[] items;
    //    private Set<T> itemSet;
    private Map<T, Integer> itemMap;
    private int size;

    private class Node<T> {
        double priority;
        T item;
        Node(T i, double p) {
            item = i;
            priority = p;
        }
        @Override
        public String toString() {
            return item.toString() + priority;
        }
    }

    public ArrayHeapMinPQ() {
        items = new Node[iS + 1];
        itemMap = new HashMap<>();
    }

    public void add(T item, double priority) {

        if (contains(item)) {
            throw new IllegalArgumentException();
        }
        items[size + 1] = new Node<>(item, priority);
        size++;
        if (size >= items.length - 1) {
            resize(items.length * 2 - 1);
        }
        itemMap.put(item, swim(size));

    }

    public boolean contains(T item) {
        return itemMap.containsKey(item);
    }

    public T getSmallest() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        return ((T) items[1].item);
    }

    public T removeSmallest() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        T returnVal = ((T) items[1].item);
        items[1] = null;
        itemMap.remove(returnVal);
        if (size == 1) {
            size--;
            return returnVal;
        }
        swap(1, size);
        itemMap.put((T) items[1].item, sink(1));
        //sink(1);
        size--;
        if ((((double) size) / items.length < 0.25) && items.length > iS + 1) {
            resize((items.length + 1) / 2);
        }
        return returnVal;
    }

    public void changePriority(T item, double priority) {

        int i = itemMap.get(item);
        double oldP = items[i].priority;
        items[i].priority = priority;
        if (priority > oldP) {
            itemMap.put(item, sink(i));
            //sink(i);
        } else {
            itemMap.put(item, swim(i));
            //swim(i);
        }

    }

    public int size() {
        return size;
    }

    private void resize(int cap) {
        Node[] newItems = new Node[cap];
        System.arraycopy(items, 1, newItems, 1, size);
        items = newItems;
    }

    private int sink(int startIndex) {
        int curr = startIndex;
        Node left;
        Node right;
        while (true) {
            Node here = items[curr];
            if (left(curr) >= items.length) {
                left = null;
            } else {
                left = items[left(curr)];
            }
            if (right(curr) >= items.length) {
                right = null;
            } else {
                right = items[right(curr)];
            }
            if ((left == null || left.priority >= here.priority)
                    && (right == null || right.priority >= here.priority)) {
                break;
            }
            if (right != null && right.priority < left.priority) {
                swap(curr, right(curr));
                curr = right(curr);
            } else {
                swap(curr, left(curr));
                curr = left(curr);
            }
        }
        return curr;
    }

    private int swim(int startIndex) {
        int curr = startIndex;
        while (true) {
            Node here = items[curr];
            Node parent = items[parent(curr)];
            if (curr == 1 || parent.priority <= here.priority) {
                break;
            }
            swap(curr, parent(curr));
            curr = parent(curr);
        }
        return curr;
    }

    private void swap(int top, int bottom) {
        Node tempNode;
        tempNode = items[bottom];
        items[bottom] = items[top];
        items[top] = tempNode;
        if (items[top] != null) {
            itemMap.put((T) items[top].item, top);
        }
        if (items[bottom] != null) {
            itemMap.put((T) items[bottom].item, bottom);
        }
    }

    private static int parent(int i) {
        if (i == 0) {
            throw new RuntimeException("What are you doing mate");
        }
        if (i == 1) {
            return 1;
        }
        return i / 2;
    }

    private static int left(int i) {
        return 2 * i;
    }

    private static int right(int i) {
        return 2 * i + 1;
    }


}
