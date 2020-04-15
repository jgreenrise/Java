package o_semaphore;

import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

public class CellPhone extends Thread {

    private String name;
    private static Semaphore charger = new Semaphore(4);

    public CellPhone(String name) {
        this.name = name;
    }

    public void run() {

        try{
            charger.acquire();
            System.out.println(this.getName()+" is charging !!!");
            System.out.println(name+" is charging !!!");
            Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 2000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            System.out.println(this.getName()+" is done charging !!!");
            System.out.println(name+" is done charging !!!");
            charger.release();
        }

    }

    public static void main(String [] args){
        for (int i = 0; i < 10; i++) {
            new CellPhone("Person"+i).start();
        }
    }
}
