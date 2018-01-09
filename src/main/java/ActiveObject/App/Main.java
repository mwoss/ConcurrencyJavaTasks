package ActiveObject.App;
import ActiveObject.Models.Consumer;
import ActiveObject.Models.Producer;
import ActiveObject.Proxy;
import ActiveObject.TimerRunnable;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws FileNotFoundException, InterruptedException {
        PrintStream out = new PrintStream(new FileOutputStream("outputAO_sleep_thread" + 2 + ".txt"));
        System.setOut(out);
        final int N = 2;
        final int capacity = 5000;
        List<Thread> prodList = new ArrayList<>();
        List<Thread> consList = new ArrayList<>();
        Proxy proxy = new Proxy(capacity);
        for (int i = 0; i < N; i++) {
            consList.add(new Consumer(proxy, capacity));
            prodList.add(new Producer(proxy, capacity));
        }

        for (int i = 0; i < N; i++) {
            consList.get(i).start();
            prodList.get(i).start();
        }


        Runnable sysExit = () -> System.exit(0);

        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        scheduledExecutorService.scheduleAtFixedRate(new TimerRunnable(proxy), 0, 2, TimeUnit.SECONDS);

        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.schedule(sysExit, 30, TimeUnit.SECONDS);
//            Thread.sleep(30000);
//            scheduledExecutorService.shutdown();


    }
}
