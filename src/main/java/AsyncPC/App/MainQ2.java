package AsyncPC.App;

import AsyncPC.Logic.Models.SecSolutionBetter.ConsumerQ2;
import AsyncPC.Logic.Models.SecSolutionBetter.ProducerQ2;
import AsyncPC.Logic.Utils.SecSolutionBetter.Buffer;
import AsyncPC.Logic.Utils.SecSolutionBetter.PCMonitorAsyncQueue2;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainQ2 {

    public static void main(String[] args) throws FileNotFoundException {
        PrintStream out = new PrintStream(new FileOutputStream("new_outputAsync_sleep" + 1000 + ".txt"));
        System.setOut(out);
        int capacity = 10000;
        int threadAmount = 100;
        List<Thread> prodList = new ArrayList<>();
        List<Thread> consList = new ArrayList<>();
        PCMonitorAsyncQueue2 pcMonitorAsync = new PCMonitorAsyncQueue2(capacity);
        Buffer buffer = new Buffer(capacity);

        for (int i = 0; i < threadAmount; i++) {
            consList.add(new ConsumerQ2(capacity, buffer, pcMonitorAsync));
            prodList.add(new ProducerQ2(capacity, buffer, pcMonitorAsync));
        }

        for (int i = 0; i < threadAmount; i++) {
            consList.get(i).start();
            prodList.get(i).start();
        }

        Runnable sysExit = () -> System.exit(0);

        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        scheduledExecutorService.scheduleAtFixedRate(new TimerRunnableAsync(pcMonitorAsync, threadAmount), 0 ,2, TimeUnit.SECONDS);

        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.schedule(sysExit, 30, TimeUnit.SECONDS);

    }
}
