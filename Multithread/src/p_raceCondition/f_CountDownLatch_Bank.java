package p_raceCondition;

import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

import static java.lang.Thread.sleep;

public class f_CountDownLatch_Bank {

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
}
