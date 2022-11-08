package synthesizer;
import java.util.Iterator;

interface BoundedQueue<T> extends Iterable<T> {
    int capacity(); // return the capacity of the BoundedQueue
    int fillCount(); // return the number of items currently in the buffer
    void enqueue(T x); // add a item x to the end
    T dequeue(); // delete and return item from the front
    T peek(); // return (not delete) item from the front
    default boolean isEmpty() {
        return fillCount() == 0;
    };
    default boolean isFull() {
        return capacity() == fillCount();
    };
    Iterator<T> iterator();
}
