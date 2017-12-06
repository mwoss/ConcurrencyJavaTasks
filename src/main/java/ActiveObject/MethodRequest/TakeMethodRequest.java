package ActiveObject.MethodRequest;

import ActiveObject.Future.Future;
import ActiveObject.Servant;

public class TakeMethodRequest implements IMethodRequest {

    private Future future;
    private Servant servant;
    private int toTake;

    public TakeMethodRequest(Future future, Servant buffer, int toTake){
        this.future = future;
        this.servant = buffer;
        this.toTake = toTake;
    }

    @Override
    public boolean guard() {
        return servant.getCurrentSize() >= toTake;
    }

    @Override
    public void execute() {
        servant.consume(toTake);
        future.setResult(toTake);
        future.setStatus();
    }
}
