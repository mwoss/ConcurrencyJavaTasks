package ActiveObject.Future;

public interface IFuture<T> {
    T getResult();
    boolean isFinished();
    void setResult(T result);
    void setStatus();
}
