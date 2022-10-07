public class ArrayDeque<T> {
    private int size;
    private int front;
    private int last;
    private T[] lst;
    public ArrayDeque() {
        lst = (T[]) new Object[8];
        size = 0;
        front = 0;
        last = 0;
    }
    private float useage() {
        return (float) size / lst.length;
    }
    private int minusOne(int index) {
        index = index - 1;
        if (index < 0) {
            index += lst.length;
        }
        return index;
    }
    private int plusOne(int index) {
        return (index + 1) % lst.length;
    }
    private void resizing(int n) {
        T[] newLst = (T[]) new Object[n];
        for (int i = 0; i < size; i += 1) {
            newLst[i] = lst[front];
            front = plusOne(front);
        }
        lst = newLst;
        front = 0;
        last = size - 1;
    }
    public void addFirst(T x) {
        if (lst.length - size < 1) {
            resizing(2 * size);
        }
        front = minusOne(front);
        lst[front] = x;
        size += 1;
    }
    public void addLast(T x) {
        if (lst.length - size < 1) {
            resizing(2 * size);
        }
        last = plusOne(last);
        lst[last] = x;
        size += 1;
    }
    public boolean isEmpty() {
        return size == 0;
    }
    public int size() {
        return size;
    }
    public void printDeque() {
        for (int i = front; i != last; i = plusOne(i)) {
            System.out.print(i);
            System.out.print(" ");
        }
        System.out.print(last);
        System.out.print(" ");
    }
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        T shouldReturn = lst[front];
        front = plusOne(front);
        size -= 1;
        if (useage() < 0.25) {
            resizing(2 * size);
        }
        return shouldReturn;
    }
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        T shouldReturn = lst[last];
        last = minusOne(last);
        size -= 1;
        if (useage() < 0.25) {
            resizing(2 * size);
        }
        return shouldReturn;
    }
    public T  get(int index) {
        if (index > size - 1) {
            return null;
        }
        int p = front;
        for (int i = 0; i < index; i += 1) {
            p = plusOne(p);
        }
        return lst[p];
    }
}
