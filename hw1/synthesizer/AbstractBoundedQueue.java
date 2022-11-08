package synthesizer;

abstract class AbstractBoundedQueue<T> implements BoundedQueue<T> {
    protected int fillCount;
    protected int capacity;
    @Override
    public int fillCount() {
        return fillCount;
    }
    @Override
    public int capacity() {
        return capacity;
    }
    @Override
    public boolean isEmpty() {
        return fillCount == 0;
    }
    @Override
    public boolean isFull() {
        return fillCount == capacity;
    }
//    @Override
//    public abstract void enqueue(T x);
    @Override
    public abstract T dequeue();
    @Override
    public abstract T peek();
}
