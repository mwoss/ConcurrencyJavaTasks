package ActiveObject.Queues;

public interface IActivationQueue<T> {
    T dequeue();
    void enqueue(T methodRequest);
}
