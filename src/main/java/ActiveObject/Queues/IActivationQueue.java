package ActiveObject.Queues;

public interface IActivationQueue<T> {
    T dequeue() throws InterruptedException;
    void enqueue(T methodRequest) throws InterruptedException;
}
