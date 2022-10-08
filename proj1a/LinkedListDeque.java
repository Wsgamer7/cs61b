public class LinkedListDeque<T> {
    private class IntNode {
        private IntNode pre;
        private T item;
        private IntNode next;

        public IntNode(IntNode preNode, T x, IntNode nextNode) {
            pre = preNode;
            item = x;
            next = nextNode;
        }
    }
    private IntNode sentinal;
    private int size;
    public LinkedListDeque() {
        sentinal = new IntNode(null, null, null);
        sentinal.next = sentinal;
        sentinal.pre = sentinal;
        size = 0;
    }
    public boolean isEmpty() {
        return size == 0;
    }
    public void addFirst(T x) {
        IntNode p = sentinal.next;
        IntNode newNode = new IntNode(sentinal, x, p);
        p.pre = newNode;
        sentinal.next = newNode;
        size += 1;
    }
    public void addLast(T x) {
        IntNode p = sentinal.pre;
        IntNode newNode = new IntNode(p, x, sentinal);
        p.next = newNode;
        sentinal.pre = newNode;
        size += 1;
    }
    public int size() {
        return size;
    }
    public void printDeque() {
        IntNode current = sentinal.next;
        while (current != sentinal) {
            System.out.print(current.item);
            System.out.print(" ");
            current = current.next;
        }
    }
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        IntNode p1 = sentinal.next.next;
        T shouldReturn = sentinal.next.item;
        sentinal.next = p1;
        p1.pre = sentinal;
        size -= 1;
        return shouldReturn;
    }
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        IntNode p1 = sentinal.pre.pre;
        T shouldReturn = sentinal.pre.item;
        p1.next = sentinal;
        sentinal.pre = p1;
        size -= 1;
        return shouldReturn;
    }
    public T get(int index) {
        if (index > size - 1) {
            return null;
        }
        IntNode current = sentinal.next;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.item;
    }
    public T getRecursive(int index) {
        if (index > size - 1) {
            return null;
        }
        return getRecursiveHelper(sentinal.next, index);
    }
    private T getRecursiveHelper(IntNode start, int index) {
        if (index == 0) {
            return start.item;
        }
        return getRecursiveHelper(start.next, index - 1);
    }
}
