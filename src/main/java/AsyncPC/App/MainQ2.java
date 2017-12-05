package AsyncPC.App;

import AsyncPC.Logic.Models.SecSolutionBetter.ConsumerQ2;
import AsyncPC.Logic.Models.SecSolutionBetter.ProducerQ2;
import AsyncPC.Logic.Utils.SecSolutionBetter.Buffer;
import AsyncPC.Logic.Utils.SecSolutionBetter.PCMonitorAsyncQueue2;

import java.util.ArrayList;
import java.util.List;

public class MainQ2 {

    public static void main(String[] args) {
        int capacity = 100;
        int threadAmount = 10;
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

        for (int i = 0; i < threadAmount; i++) {
            try{
                prodList.get(i).join();
                consList.get(i).join();
            }
            catch (InterruptedException e){
                System.out.println(e.getMessage());
            }
        }
    }
}
