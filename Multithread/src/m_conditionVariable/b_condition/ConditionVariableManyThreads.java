package m_conditionVariable.b_condition;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Limitation: signal only notifies only of the thread instance. It does notify all the waiting threads.
 *
 * Program gets stuck
 */
public class ConditionVariableManyThreads extends Thread{

    private int personId;
    private static int servings = 10;
    private static Lock slowCookerLock = new ReentrantLock();
    private static Condition condition = slowCookerLock.newCondition();

    public ConditionVariableManyThreads(int personId) {
        this.personId = personId;
    }

    public void run() {

        while (servings>0){
            // 1. Lock - Take control
            slowCookerLock.lock();

            try {

                // 2. Check if its my turn to take soup, Since there are 2 person -> %2
                if (personId == servings % 5 && servings > 0) {

                    servings--;

                    // 2.a  Start taking soup
                    System.out.format("%s Person %d took some soup! Servings left: %d\n", new SimpleDateFormat("mm:ss::SSS").format(new Date()), personId, servings);

                    condition.signal();

                }{
                    // 2.b It is not my turn to take soup
                    System.out.format("%s Person %d checked ..  Lid is put back. Servings left: %d\n", new SimpleDateFormat("mm:ss::SSS").format(new Date()), personId, servings);
                    condition.await();
                }

            }catch (Exception exception){
                exception.printStackTrace();
            }finally {

                // 3. Unclock
                slowCookerLock.unlock();
            }
        }

    }

    public static void main(String[] args){
        for (int i = 0; i < 5; i++) {
            new ConditionVariableManyThreads(i).start();
        }
    }

}
