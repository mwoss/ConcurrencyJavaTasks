package ActiveObject;

import java.util.concurrent.Future;

public class FirstMethodRequest implements IMethodRequest {

    private Future task;
    private Servant servant;

    @Override
    public boolean guard() {
        return false;
    }

    @Override
    public void execute() {

    }
}
