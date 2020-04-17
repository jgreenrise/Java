package p_raceCondition;

import java.util.*;
import java.util.concurrent.*;

import static java.lang.Thread.sleep;

public class c_Cyclic_Barrier_04_Aggregation {

    private int NUM_PARTIAL_RESULTS;
    private static List<List<Integer>> partialResults = Collections.synchronizedList(new ArrayList<>());

    public static void main(String[] args)  {

        int NUM_WORKER_THREADS = 3;
        int COUNT_LIST_SIZE = 5;

        ExecutorService executorService = Executors.newFixedThreadPool(4);

        // 3 Service Thread + 1 Main thread
        CyclicBarrier cyclicBarrier = new CyclicBarrier(NUM_WORKER_THREADS, new AggregatorThread(NUM_WORKER_THREADS, COUNT_LIST_SIZE));

        System.out.println("Main waits for child threads to do their job");

        for (int i = 0; i < NUM_WORKER_THREADS; i++) {
            executorService.submit(new NumberCrunchThread(cyclicBarrier, "thread-worker-"+i, COUNT_LIST_SIZE));
        }

        executorService.shutdown();
    }

    private static class NumberCrunchThread implements Runnable {
        private CyclicBarrier cyclicBarrier;
        private String name;
        private int listSize;
        Random random = new Random();

        public NumberCrunchThread(CyclicBarrier cyclicBarrier, String name, int listSize) {
            this.cyclicBarrier = cyclicBarrier;
            this.name = name;
            this.listSize = listSize;
        }

        public void run() {
            try {

                List<Integer> partialResult = new ArrayList<>();

                // Crunch some numbers and store the partial result
                for (int i = 0; i < listSize; i++) {
                    Integer num = random.nextInt(10);
                    System.out.println(name + ": Added Random number - " + num);
                    partialResult.add(num);
                }
                partialResults.add(partialResult);

                cyclicBarrier.await();

            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }

    private static class AggregatorThread implements Runnable {
        private final int num_worker_threads;
        private final int count_list_size;

        public AggregatorThread(int num_worker_threads, int count_list_size) {
            this.num_worker_threads = num_worker_threads;
            this.count_list_size = count_list_size;
        }


        public void run() {
            System.out.println(
                    "thread-Aggregator  : Computing sum of " + num_worker_threads
                            + " workers, having " + count_list_size + " results each.");
            int sum = 0;

            for (List<Integer> threadResult : partialResults) {
                System.out.print("Adding ");
                for (Integer partialResult : threadResult) {
                    System.out.print(partialResult+" ");
                    sum += partialResult;
                }
                System.out.println();
            }
            System.out.println("Thread-Aggregator : Final result = " + sum);
        }
    }


}
