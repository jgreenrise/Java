package m_conditionVariable.a_busyWaiting;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Limitation: Thread waste a lot of resources, while waiting for lock
 */
public class ConditionVariableDemo extends Thread{

    private int personId;
    private static int servings = 10;
    private static Lock slowCookerLock = new ReentrantLock();

    public ConditionVariableDemo(int personId) {
        this.personId = personId;
    }

    public void run() {

        while (servings>0){
            // 1. Lock - Take control
            slowCookerLock.lock();

            try {

                // 2. Check if its my turn to take soup, Since there are 2 person -> %2
                if (personId == servings % 2 && servings > 0) {

                    servings--;

                    // 2.a  Start taking soup
                    System.out.format("%s Person %d took some soup! Servings left: %d\n", new SimpleDateFormat("mm:ss::SSS").format(new Date()), personId, servings);

                }

                // 2.b It is not my turn to take soup
                System.out.format("%s Person %d checked ..  Lid is put back. Servings left: %d\n", new SimpleDateFormat("mm:ss::SSS").format(new Date()), personId, servings);


            }catch (Exception exception){
                exception.printStackTrace();
            }finally {

                // 3. Unclock
                slowCookerLock.unlock();
            }
        }

    }

    public static void main(String[] args){
        for (int i = 0; i < 2; i++) {
            new ConditionVariableDemo(i).start();
        }
    }

}
