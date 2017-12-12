package ActiveObject.App;

import ActiveObject.Models.Consumer;
import ActiveObject.Models.Producer;
import ActiveObject.Proxy;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        final int N = 10;
        final int capacity = 100;
        List<Thread> prodList = new ArrayList<>();
        List<Thread> consList = new ArrayList<>();
        Proxy proxy = new Proxy(capacity);
        for(int i = 0; i < N; i++){
            consList.add(new Consumer(proxy, capacity));
            prodList.add(new Producer(proxy, capacity));
        }

        for(int i = 0; i < N; i++){
            consList.get(i).start();
            prodList.get(i).start();
        }

    }
}
