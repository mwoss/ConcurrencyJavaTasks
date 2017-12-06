package ActiveObject.MethodRequest;

import ActiveObject.Future.Future;
import ActiveObject.Servant;

public class PutMethodRequest implements IMethodRequest {

    private Future future;
    private Servant servant;
    private int toPut;

    public PutMethodRequest(Future future, Servant buffer, int toPut){
        this.future = future;
        this.servant = buffer;
        this.toPut = toPut;
    }

    @Override
    public boolean guard() {
        return servant.getCurrentSize() + toPut <= servant.getCapacity();
    }

    @Override
    public void execute() {
        servant.produce(toPut);
        future.setResult(toPut);
        future.setStatus();
    }
}
