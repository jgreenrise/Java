package problems.philosophersDinning;

/**
 * 
Philosopher 2	HUNGRY		2	1

Philosopher 2	GOTCH		2	1
Philosopher 2	EATNG		2	1
Philosopher 5	HUNGRY		0	4
Philosopher 3	HUNGRY		3	2
Philosopher 1	HUNGRY		1	0
Philosopher 1	WAITG		1	0
Philosopher 4	HUNGRY		4	3
Philosopher 4	WAITG		4	3
Philosopher 3	WAITG		3	2

Philosopher 5	GOTCH		0	4
Philosopher 5	EATNG		0	4
Philosopher 1	HUNGRY		1	0
Philosopher 1	WAITG		1	0
Philosopher 5	THINK		0	4
Philosopher 4	HUNGRY		4	3

Philosopher 4	GOTCH		4	3
Philosopher 4	EATNG		4	3
Philosopher 5	HUNGRY		0	4
Philosopher 5	WAITG		0	4
Philosopher 3	HUNGRY		3	2
Philosopher 3	WAITG		3	2
Philosopher 5	HUNGRY		0	4
Philosopher 5	WAITG		0	4
Philosopher 2	THINK		2	1
Philosopher 4	THINK		4	3
Philosopher 1	HUNGRY		1	0

Philosopher 1	GOTCH		1	0
Philosopher 1	EATNG		1	0
Philosopher 1	THINK		1	0
Philosopher 1	HUNGRY		1	0

Philosopher 1	GOTCH		1	0
Philosopher 1	EATNG		1	0
Philosopher 4	HUNGRY		4	3

Philosopher 4	GOTCH		4	3
Philosopher 4	EATNG		4	3
Philosopher 1	THINK		1	0
Philosopher 3	HUNGRY		3	2
Philosopher 3	WAITG		3	2
Philosopher 5	HUNGRY		0	4
Philosopher 5	WAITG		0	4
Philosopher 2	HUNGRY		2	1

Philosopher 2	GOTCH		2	1
Philosopher 2	EATNG		2	1
Philosopher 5	HUNGRY		0	4
Philosopher 5	WAITG		0	4
Philosopher 1	HUNGRY		1	0
Philosopher 1	WAITG		1	0
Philosopher 5	HUNGRY		0	4
Philosopher 5	WAITG		0	4
Philosopher 2	THINK		2	1
Philosopher 4	THINK		4	3
Philosopher 3	HUNGRY		3	2

Philosopher 3	GOTCH		3	2
Philosopher 3	EATNG		3	2
Philosopher 5	HUNGRY		0	4

Philosopher 5	GOTCH		0	4
Philosopher 5	EATNG		0	4
Philosopher 5	THINK		0	4
Philosopher 2	HUNGRY		2	1
Philosopher 2	WAITG		2	1
Philosopher 2	HUNGRY		2	1
Philosopher 2	WAITG		2	1
Philosopher 2	HUNGRY		2	1
Philosopher 2	WAITG		2	1
Philosopher 4	HUNGRY		4	3
Philosopher 4	WAITG		4	3
Philosopher 1	HUNGRY		1	0

Philosopher 1	GOTCH		1	0
Philosopher 1	EATNG		1	0
Philosopher 3	THINK		3	2
Philosopher 5	HUNGRY		0	4
Philosopher 5	WAITG		0	4
Philosopher 4	HUNGRY		4	3

Philosopher 4	GOTCH		4	3
Philosopher 4	EATNG		4	3
Philosopher 1	THINK		1	0
Philosopher 4	THINK		4	3
 *
 *
 Reference
 https://orajavasolutions.wordpress.com/2014/05/05/dining-philosoper-problem-in-java/#comment-229
 */
public class Client {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Chopstick cs [] = new Chopstick[5];
		
		for (int i = 0; i < cs.length; i++) {
			cs[i] = new Chopstick();
		}
		
		Thread [] threads = new Thread[cs.length];
		for (int i = 0; i < cs.length; i++) {
			Philosopher ph = new Philosopher(i+1, cs);
			threads[i] = new Thread(ph);
		}
		
		for (int i = 0; i < cs.length; i++) {
			threads[i].start();
		}

	}

}
