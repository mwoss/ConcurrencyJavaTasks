package ActiveObject;

public class Servant {

    private int capacity;
    private int currentSize;

    public Servant(int capacity){
        this.capacity = capacity;
        this.currentSize = 0;
    }

    public void consume(int amountToConsume){
        this.currentSize -= amountToConsume;
        System.out.println("Consumed: " + amountToConsume);
    }

    public void produce(int amountToProduce){
        this.currentSize += amountToProduce;
        System.out.println("Produced: " + amountToProduce);
    }

    public int getCapacity() {
        return capacity;
    }

    public int getCurrentSize() {
        return currentSize;
    }
}
