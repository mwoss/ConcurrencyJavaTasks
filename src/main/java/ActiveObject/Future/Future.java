package ActiveObject.Future;

public class Future implements IFuture<Integer>{

    private boolean status;
    private Integer result;

    @Override
    public Integer getResult() {
        return this.result;
    }

    @Override
    public boolean isFinished() {
        return status;
    }

    @Override
    public void setResult(Integer result) {
        this.result = result;
    }

    @Override
    public void setStatus() {
        this.status = true;
    }
}
