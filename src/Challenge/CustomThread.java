package Challenge;

public class CustomThread extends Thread {

	@Override
	public void run() {
		for (int i = 1; i <= 5; i++) {
			int randomOddNumber = (int) Math.floor(Math.random() * 100);
			if (randomOddNumber % 2 == 0) {
				randomOddNumber++;
			}
			System.out.println("RandomOddNumber: " + randomOddNumber + " ");
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				System.out.println("Thread interrupted");
				return;
			}
		}
	}
}
