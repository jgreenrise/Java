package p_raceCondition;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Program representing Race condition
 */
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
