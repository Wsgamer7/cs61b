public interface Deque<T> {
    boolean isEmpty();

    void addFirst(T x);

    void addLast(T x);

    int size();

    void printDeque();

    T removeFirst();

    T removeLast();

    T get(int index);
}
