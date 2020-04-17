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
     
## Solution Race condtion: Cyclic Barrier


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
    
