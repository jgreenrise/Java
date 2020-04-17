package p_raceCondition;

import java.util.Date;
import java.util.concurrent.*;

import static java.lang.Thread.sleep;

public class c_Cyclic_Barrier_03_Bank {

    public static void main(String[] args)  {

        final String[] CARD_NOS = { "1234-5678-9000-1111", "1234-5678-9000-2222" };
        final String[] PINS = { "1234", "5678" };
        final double[] AMOUNTS = { 1500.99, 1249.99 };

        ExecutorService executorService = Executors.newFixedThreadPool(4);

        // 3 Service Thread + 1 Main thread
        CyclicBarrier cyclicBarrier = new CyclicBarrier(4);

        System.out.println("Main waits for child threads to do their job");

        for (int i = 0; i < CARD_NOS.length; i++) {
            executorService.submit(new AuthenticationService(cyclicBarrier, "thread-authSvc", CARD_NOS[i], PINS[i]));
            executorService.submit(new AccountBalanceService(cyclicBarrier, "thread-acctSvc", CARD_NOS[i], AMOUNTS[i]));
            executorService.submit(new FraudService(cyclicBarrier, "thread-fraudSvc", CARD_NOS[i], AMOUNTS[i]));

            // Main thread Waits for Other services to complete
            try {
                cyclicBarrier.await();
            } catch (BrokenBarrierException | InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println("Main thread can now do its job. \n");
            }

        }

        executorService.shutdown();
    }

    private static class AuthenticationService implements Runnable {
        private CyclicBarrier cyclicBarrier;
        private String name;
        private final String cardNo;
        private final String pin;

        public AuthenticationService(CyclicBarrier cyclicBarrier, String name, String cardNo, String pin) {
            this.cyclicBarrier = cyclicBarrier;
            this.name = name;
            this.cardNo = cardNo;
            this.pin = pin;
        }

        public void run() {
            try {
                System.out.printf(new Date()+"\t"+name+"\tStarted Auth check for %s\n", cardNo);
                sleep(ThreadLocalRandom.current().nextInt(1000, 5000));
                System.out.printf(new Date()+"\t"+name+"\tCompleted Auth check for %s\n", cardNo);
                cyclicBarrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }

    private static class AccountBalanceService implements Runnable {
        private CyclicBarrier cyclicBarrier;
        private String name;
        private final String cardNo;
        private final double amt;

        public AccountBalanceService(CyclicBarrier cyclicBarrier, String name, String cardNo, double amt) {
            this.cyclicBarrier = cyclicBarrier;
            this.name = name;
            this.cardNo = cardNo;
            this.amt = amt;
        }

        public void run() {
            try {
                System.out.printf(new Date()+"\t"+name+"\tStarted balance check on %s for amount %.02f\n", cardNo, amt);
                sleep(ThreadLocalRandom.current().nextInt(1000, 5000));
                System.out.printf(new Date()+"\t"+name+"\tCompleted balance check for %s for amount %.02f\n", cardNo, amt);
                cyclicBarrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }

    private static class FraudService implements Runnable {
        private CyclicBarrier cyclicBarrier;
        private String name;
        private final String cardNo;
        private final double amt;

        public FraudService(CyclicBarrier cyclicBarrier, String name, String cardNo, double amt) {
            this.cyclicBarrier = cyclicBarrier;
            this.name = name;
            this.cardNo = cardNo;
            this.amt = amt;
        }

        public void run() {
            try {
                System.out.printf(new Date()+"\t"+name+"\tStarted fraud check on %s for amount %.02f\n", cardNo, amt);
                sleep(ThreadLocalRandom.current().nextInt(1000, 5000));
                System.out.printf(new Date()+"\t"+name+"\tCompleted fraud check on %s for amount %.02f\n", cardNo, amt);
                cyclicBarrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }
}
