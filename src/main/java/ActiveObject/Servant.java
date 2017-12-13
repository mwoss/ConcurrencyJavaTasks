package ActiveObject;

public class Servant {

    private int capacity;
    private int currentSize;

    private int prodExecuted;
    private int consExecuted;

    public Servant(int capacity){
        this.capacity = capacity;
        this.currentSize = 0;
        this.consExecuted = 0;
        this.prodExecuted = 0;
    }

    public void consume(int amountToConsume){
        this.currentSize -= amountToConsume;
        consExecuted++;
//        System.out.println("Consumed: " + amountToConsume);
    }

    public void produce(int amountToProduce){
        this.currentSize += amountToProduce;
        prodExecuted++;
//        System.out.println("Produced: " + amountToProduce);
    }

    public int getCapacity() {
        return capacity;
    }

    public int getCurrentSize() {
        return currentSize;
    }


    public int getProdExecuted() {
        return prodExecuted;
    }

    public int getConsExecuted() {
        return consExecuted;
    }
}
