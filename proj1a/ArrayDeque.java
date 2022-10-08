public class ArrayDeque<T> {
    /** the size of the ArrayDeque */
    private int size;
    /** the index of the first item in array */
    private int front;
    /** the index after the last item */
    private int last;
    /** the inner Array */
    private T[] lst;
    /** the instantiation of class ArrayDeque, result in a empty Array (length = 8) */
    public ArrayDeque() {
        lst = (T[]) new Object[8];
        size = 0;
        front = 0;
        last = 0;
    }
    /** the useage of the inner Array */
    private float useage() {
        return (float) size / lst.length;
    }
    /** minusOne in circus list, replace i-- */
    private int minusOne(int index) {
        index = index - 1;
        if (index < 0) {
            index += lst.length;
        }
        return index;
    }
    /** plusOne in circus list, replace i++ */
    private int plusOne(int index) {
        return (index + 1) % lst.length;
    }
    /** resizing the inner Array, take the length of new inner Array */
    private void resizing(int n) {
        T[] newLst = (T[]) new Object[n];
        for (int i = 0; i < size; i += 1) {
            newLst[i] = lst[front];
            front = plusOne(front);
        }
        lst = newLst;
        front = 0;
        last = size;
    }
    private void resizingInArrayDeque() {
        resizing(Math.max(8, size * 2));
    }
    /** add x to the first position of the ArrayDeque */
    public void addFirst(T x) {
        if (lst.length - size < 2) {
            resizingInArrayDeque();
        }
        front = minusOne(front);
        lst[front] = x;
        size += 1;
    }
    /** add x to the last position of the ArrayDeque */
    public void addLast(T x) {
        if (lst.length - size < 2) {
            resizingInArrayDeque();
        }
        lst[last] = x;
        last = plusOne(last);
        size += 1;
    }
    public boolean isEmpty() {
        return size == 0;
    }
    public int size() {
        return size;
    }
    /** print out the Deque */
    public void printDeque() {
        for (int i = front; i != last; i = plusOne(i)) {
            System.out.print(i);
            System.out.print(" ");
        }
    }
    /** remove the first item */
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        T shouldReturn = lst[front];
        front = plusOne(front);
        size -= 1;
        if (useage() < 0.25 && lst.length >= 16) {
            resizingInArrayDeque();
        }
        return shouldReturn;
    }
    /** remove the last item */
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        last = minusOne(last);
        T shouldReturn = lst[last];
        size -= 1;
        if (useage() < 0.25 && lst.length >= 16) {
            resizingInArrayDeque();
        }
        return shouldReturn;
    }
    /** return the indexTH item */
    public T get(int index) {
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
