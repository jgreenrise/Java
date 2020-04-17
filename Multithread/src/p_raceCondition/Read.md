# Race condition

## Output is indeterministic, as ordering of events affects programs correctness.

### Example: Race Condition, Data Race is being handled with lock

    public class a_Buyers extends Thread {
 
     public static int basketOfChips = 1;
     private static Lock person = new ReentrantLock();
 
     public void run(){
         if(this.getName().contains("APLHA")){
             person.lock();
             try{
                 basketOfChips = basketOfChips + 3;
                 System.out.println(this.getName() + " added 3 bags");
             }finally {
                 person.unlock();
             }
         }else{
             person.lock();
             try{
                 basketOfChips = basketOfChips * 2;
                 System.out.println(this.getName() + " doubled bags");
             }finally {
                 person.unlock();
             }
 
         }
     }
 
     public static void main(String[] args) throws InterruptedException {
         a_Buyers [] buyers = new a_Buyers[4];
         for (int i = 0; i < buyers.length/2; i++) {
             buyers[2*i] = new a_Buyers("APLHA-"+i);
             buyers[2*i+1] = new a_Buyers("BETA-"+i+1);
         }
 
         for (a_Buyers buyer: buyers) {
             buyer.start();
         }
 
         for (a_Buyers buyer: buyers) {
             buyer.join();
         }
 
         System.out.println("We need to buy: "+a_Buyers.basketOfChips+ "bags of chips");
     }
 
     public a_Buyers(String name){
         this.setName(name);
     }
    }
 
 ### EXPECTED Output
 **
 Expected behavior: 
 Beta threads should start, once ALL the Alpha completes
 **
 
 
      APLHA-1 added 3 bags
      APLHA-0 added 3 bags
      BETA-01 doubled bags
      BETA-11 doubled bags
      We need to buy: 28bags of chips
 
 ### CURRENT Output
 
     BETA-01 doubled bags
     APLHA-1 added 3 bags
     APLHA-0 added 3 bags
     BETA-11 doubled bags
     We need to buy: 16bags of chips
 
     APLHA-0 added 3 bags
     APLHA-1 added 3 bags
     BETA-01 doubled bags
     BETA-11 doubled bags
     We need to buy: 28bags of chips
 
     APLHA-0 added 3 bags
     BETA-01 doubled bags
     APLHA-1 added 3 bags
     BETA-11 doubled bags
     We need to buy: 22bags of chips
     
## Solution Race condition: Cyclic Barrier


    public void run(){
        if(this.getName().contains("APLHA")){
            person.lock();
            try{
                Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 2000));
                basketOfChips = basketOfChips + 3;
                System.out.println(this.getName() + " added 3 bags");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                person.unlock();
            }
            try {
                /**
                 * Only when all threads reach Await, then each thread can resume execution.
                 * For example, if 2 out of 3 threads have completed processing, they have to enter into wait stage
                 * They have to wait until Thread 3 has also reached await stage.
                 */
                barrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }else{
            try {
                barrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }

            person.lock();
            try{
                basketOfChips = basketOfChips * 2;
                System.out.println("\t"+this.getName() + " doubled bags");
            }finally {
                person.unlock();
            }

        }
    }

# Output is deterministic
 
Output 1

    APLHA-0 added 3 bags
    APLHA-1 added 3 bags
    APLHA-2 added 3 bags
    APLHA-3 added 3 bags
    APLHA-4 added 3 bags
    	BETA-1 doubled bags
    	BETA-2 doubled bags
    	BETA-4 doubled bags
    	BETA-5 doubled bags
    	BETA-3 doubled bags
    We need to buy: 512 bags of chips
    
Output 2

    APLHA-0 added 3 bags
    APLHA-1 added 3 bags
    APLHA-2 added 3 bags
    APLHA-3 added 3 bags
    APLHA-4 added 3 bags
    	BETA-1 doubled bags
    	BETA-2 doubled bags
    	BETA-4 doubled bags
    	BETA-5 doubled bags
    	BETA-3 doubled bags
    We need to buy: 512 bags of chips
    
Output 3 Detailed

    APLHA-1 added 3 bags
    		APLHA-1 Number of Parties (to trip the barrier, Waiting for Barrier to Lift): 10, 5
    APLHA-2 added 3 bags
    		APLHA-2 Number of Parties (to trip the barrier, Waiting for Barrier to Lift): 10, 6
    APLHA-3 added 3 bags
    		APLHA-3 Number of Parties (to trip the barrier, Waiting for Barrier to Lift): 10, 7
    APLHA-4 added 3 bags
    		APLHA-4 Number of Parties (to trip the barrier, Waiting for Barrier to Lift): 10, 8
    APLHA-0 added 3 bags
    		APLHA-0 Number of Parties (to trip the barrier, Waiting for Barrier to Lift): 10, 9
    		APLHA-1 Moving on to some other task Thu Apr 16 18:11:54 PDT 2020
    		APLHA-3 Moving on to some other task Thu Apr 16 18:11:54 PDT 2020
    		APLHA-0 Moving on to some other task Thu Apr 16 18:11:54 PDT 2020
    		APLHA-2 Moving on to some other task Thu Apr 16 18:11:54 PDT 2020
    		APLHA-4 Moving on to some other task Thu Apr 16 18:11:54 PDT 2020
    BETA-1 doubled bags at time: Thu Apr 16 18:11:54 PDT 2020
    BETA-4 doubled bags at time: Thu Apr 16 18:11:55 PDT 2020
    BETA-3 doubled bags at time: Thu Apr 16 18:11:55 PDT 2020
    BETA-5 doubled bags at time: Thu Apr 16 18:11:55 PDT 2020
    BETA-2 doubled bags at time: Thu Apr 16 18:11:55 PDT 2020
    We need to buy: 512 bags of chips
    
# Cyclic Barrier - Ex 2 - Multi player Game

Requirement: 
    We have three players in a game. Lets say we have to send message to each of them at same time to avoid any discrimination. We can use Cyclic Barrier.
    
    public class c_Cyclic_Barrier_example2 implements Runnable {
    
        private CyclicBarrier cyclicBarrier;
        private String name;
    
        public c_Cyclic_Barrier_example2(CyclicBarrier cyclicBarrier, String name){
            this.cyclicBarrier = cyclicBarrier;
            this.name = name;
        }
    
        public void run(){
    
            while(true) {
                try {
                    sleep(ThreadLocalRandom.current().nextInt(1000, 2000));
    
                    // Wait for sibling threads to complete
                    cyclicBarrier.await();
    
                    // Send Message to Player
                    System.out.println(name +" sent message at time: "+ new Date());
    
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }finally {
                    break;
                }
            }
        }
    
        public static void main(String[] args) throws InterruptedException {
    
            ExecutorService executorService = Executors.newFixedThreadPool(4);
            CyclicBarrier cyclicBarrier = new CyclicBarrier(3);
            executorService.submit(new c_Cyclic_Barrier_example2(cyclicBarrier, "thread1"));
            executorService.submit(new c_Cyclic_Barrier_example2(cyclicBarrier, "thread2"));
            executorService.submit(new c_Cyclic_Barrier_example2(cyclicBarrier, "thread3"));
    
            System.out.println("\n Completed Sending Messages");
            executorService.shutdown();
        }
    
    }
    
#### Output 1

    thread1 sent message at time: Thu Apr 16 17:35:34 PDT 2020
    thread3 sent message at time: Thu Apr 16 17:35:34 PDT 2020
    thread2 sent message at time: Thu Apr 16 17:35:34 PDT 2020
    
#### Output 2

    thread3 sent message at time: Thu Apr 16 17:38:10 PDT 2020
    thread1 sent message at time: Thu Apr 16 17:38:10 PDT 2020
    thread2 sent message at time: Thu Apr 16 17:38:10 PDT 2020
    
# Cyclic Barrier - Aggregation / Compute

1. N number of child threads create list of integers.
2. Main thread aggregates intgers created by child threads

For ex
* Thread 1 create list of integer [3,4,5]
* Thread 2 create list of integer [4,4,2]
* Thread 3 create list of integer [4,4,2]`
    
    Aggregate: All the numbers
    
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
    
Output

    thread-0: Random number - 6
    thread-1: Random number - 9
    thread-2: Random number - 6
    thread-1: Random number - 5
    thread-0: Random number - 5
    thread-1: Random number - 8
    thread-1: Random number - 0
    thread-2: Random number - 0
    thread-1: Random number - 6
    thread-0: Random number - 3
    thread-2: Random number - 3
    thread-0: Random number - 3
    thread-0: Random number - 8
    thread-2: Random number - 1
    thread-2: Random number - 2
    thread-Aggregator  : Computing sum of 3 workers, having 5 results each.
    Adding 9 5 8 0 6 
    Adding 6 5 3 3 8 
    Adding 6 0 3 1 2 
    Thread-final-Aggregator : Final result = 65
    
    Process finished with exit code 0

## 2 Solution Race condition: Count Down Latch

     /**
         *  new CountDownLatch(TOTAL_NUMBER_OF_BUYERS/2)
         *  Since we want to execute 5 Alpha threads before 5 beta threads
         */
        //private static CyclicBarrier barrier = new CyclicBarrier(10);
        private static CountDownLatch countDownLatch = new CountDownLatch(TOTAL_NUMBER_OF_BUYERS/2);
    
        public void run(){
            if(this.getName().contains("APLHA")){
                person.lock();
                try{
                    Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 2000));
                    basketOfChips = basketOfChips + 3;
                    System.out.println(this.getName() + " added 3 bags");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    person.unlock();
                }
                try {
                    //DECREMENT count latch by 1
                    countDownLatch.countDown();
                } finally {
                    System.out.println("\t\t"+this.getName() + " Latch Count: "+ countDownLatch.getCount());
                }
            }else{
                try {
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
    
                person.lock();
                try{
                    basketOfChips = basketOfChips * 2;
                    System.out.println(""+this.getName() + " doubled bags at time: "+new Date());
                }finally {
                    person.unlock();
                }
    
            }
        }

Output

    Thu Apr 16 20:20:10 PDT 2020 	 thread-1 		 Reduced the CountDown latch to : 2 
    Thu Apr 16 20:20:10 PDT 2020 	 thread-2 		 Reduced the CountDown latch to : 1 
    Thu Apr 16 20:20:11 PDT 2020 	 thread-3 		 Reduced the CountDown latch to : 0 
    
    Thu Apr 16 20:20:11 PDT 2020	 Thread-Main 	 Started 
    
Output 2

    Thu Apr 16 20:20:54 PDT 2020 	 thread-2 		 Reduced the CountDown latch to : 2 
    Thu Apr 16 20:20:54 PDT 2020 	 thread-3 		 Reduced the CountDown latch to : 1 
    Thu Apr 16 20:20:54 PDT 2020 	 thread-1 		 Reduced the CountDown latch to : 0 
    
    Thu Apr 16 20:20:54 PDT 2020	 Thread-Main 	 Started 
    
## CountDown Latch: Use Case: Main thread waiting for N threads to complete

    /**
     * Child threads should finish before main thread starts
     */
    public class e_CountDownLatch_MainThreadChildThreads implements Runnable {
    
        private CountDownLatch countDownLatch;
        private String name;
    
        public e_CountDownLatch_MainThreadChildThreads(CountDownLatch countDownLatch, String name){
            this.countDownLatch = countDownLatch;
            this.name = name;
        }
    
        public void run(){
    
            while(true) {
                try {
                    sleep(ThreadLocalRandom.current().nextInt(1000, 2000));
    
                    // I have Finished processing My task. Reducing the latch value
                    countDownLatch.countDown();
    
                    // Send Message to Player
                    System.out.printf("%s \t %s \t\t Reduced the CountDown latch to : %d \n", new Date().toString(), name, countDownLatch.getCount());
    
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    break;
                }
            }
        }
    
        public static void main(String[] args) throws InterruptedException {
    
            ExecutorService executorService = Executors.newFixedThreadPool(4);
            CountDownLatch latch = new CountDownLatch(3);
            executorService.submit(new e_CountDownLatch_MainThreadChildThreads(latch, "thread-1"));
            executorService.submit(new e_CountDownLatch_MainThreadChildThreads(latch, "thread-2"));
            executorService.submit(new e_CountDownLatch_MainThreadChildThreads(latch, "thread-3"));
    
            latch.await();
    
            System.out.println("\n"+new Date()+ "\t Thread-Main \t Started ");
    
            executorService.shutdown();
        }

Output

    Thu Apr 16 22:49:23 PDT 2020 	 thread-2 		 Reduced the CountDown latch to : 2 
    Thu Apr 16 22:49:23 PDT 2020 	 thread-3 		 Reduced the CountDown latch to : 1 
    Thu Apr 16 22:49:24 PDT 2020 	 thread-1 		 Reduced the CountDown latch to : 0 
    
    Thu Apr 16 22:49:24 PDT 2020	 Thread-Main 	 Started 
    
Output 2

    Thu Apr 16 22:50:15 PDT 2020 	 thread-1 		 Reduced the CountDown latch to : 2 
    Thu Apr 16 22:50:16 PDT 2020 	 thread-3 		 Reduced the CountDown latch to : 1 
    Thu Apr 16 22:50:16 PDT 2020 	 thread-2 		 Reduced the CountDown latch to : 0 
    
    Thu Apr 16 22:50:16 PDT 2020	 Thread-Main 	 Started 
    
## Count down Latch - Use Case Bank

#### Debit Processing Service
When we start Debit processing Service, we can initialize and setup each of the 3 service in a serial fashion.
* Authentication Service
* Fraud Service
* Bank Account Service

This can delay the startup time.

The other approach is to initialize and setup the services in parallel threads. 
For this we need to ensure we do not begin processing any debit transactions until all the 3 services are initialized and setup. 
This is a one-time synchronization that needs to happen at start-up.

     public static void main(String[] args) throws InterruptedException {
    
            ExecutorService executorService = Executors.newFixedThreadPool(4);
            CountDownLatch latch = new CountDownLatch(3);
            executorService.submit(new AuthenticationService(latch, "thread-authSvc"));
            executorService.submit(new AccountBalanceService(latch, "thread-acctSvc"));
            executorService.submit(new FraudService(latch, "thread-fraudSvc"));
    
            latch.await();
    
            System.out.println("\n" + new Date() + "\t Thread-Debit Transaction \t Started ");
    
            executorService.shutdown();
        }
    
        private static class AuthenticationService implements Runnable {
            private CountDownLatch countDownLatch;
            private String name;
    
            public AuthenticationService(CountDownLatch countDownLatch, String name) {
                this.countDownLatch = countDownLatch;
                this.name = name;
            }
    
            public void run() {
                try {
                    sleep(ThreadLocalRandom.current().nextInt(1000, 2000));
                    countDownLatch.countDown();
                    System.out.printf("%s \t %s \t\t Reduced the CountDown latch to : %d \n", new Date().toString(), name, countDownLatch.getCount());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    
        private static class AccountBalanceService implements Runnable {
            private CountDownLatch countDownLatch;
            private String name;
    
            public AccountBalanceService(CountDownLatch countDownLatch, String name) {
                this.countDownLatch = countDownLatch;
                this.name = name;
            }
    
            public void run() {
                try {
                    sleep(ThreadLocalRandom.current().nextInt(1000, 2000));
                    countDownLatch.countDown();
                    System.out.printf("%s \t %s \t\t Reduced the CountDown latch to : %d \n", new Date().toString(), name, countDownLatch.getCount());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    
        private static class FraudService implements Runnable {
            private CountDownLatch countDownLatch;
            private String name;
    
            public FraudService(CountDownLatch countDownLatch, String name) {
                this.countDownLatch = countDownLatch;
                this.name = name;
            }
    
            public void run() {
                try {
                    sleep(ThreadLocalRandom.current().nextInt(1000, 2000));
                    countDownLatch.countDown();
                    System.out.printf("%s \t %s \t\t Reduced the CountDown latch to : %d \n", new Date().toString(), name, countDownLatch.getCount());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        
Output

Thu Apr 16 22:52:16 PDT 2020 	 thread-fraudSvc 		 Reduced the CountDown latch to : 2 
Thu Apr 16 22:52:16 PDT 2020 	 thread-acctSvc 		 Reduced the CountDown latch to : 1 
Thu Apr 16 22:52:17 PDT 2020 	 thread-authSvc 		 Reduced the CountDown latch to : 0 

Thu Apr 16 22:52:17 PDT 2020	 Thread-Debit Transaction 	 Started 

Output 2

Thu Apr 16 22:52:26 PDT 2020 	 thread-fraudSvc 		 Reduced the CountDown latch to : 2 
Thu Apr 16 22:52:27 PDT 2020 	 thread-acctSvc 		 Reduced the CountDown latch to : 1 
Thu Apr 16 22:52:27 PDT 2020 	 thread-authSvc 		 Reduced the CountDown latch to : 0 

Thu Apr 16 22:52:27 PDT 2020	 Thread-Debit Transaction 	 Started 
