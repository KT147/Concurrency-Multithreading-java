package Challenge;

import Challenge.CustomThread;

import java.util.concurrent.TimeUnit;

public class Main {
	public static void main(String[] args) {

		CustomThread oddNumber = new CustomThread();

		Runnable runnable = () -> {

			for (int i = 1; i <= 5; i++) {
				int RandomEvenNumber = (int) Math.floor(Math.random() * 100);
				if (RandomEvenNumber % 2 != 0) {
					RandomEvenNumber++;
				}
				System.out.println("RandomEvenNumber: " + RandomEvenNumber + " ");
				try {
					TimeUnit.MILLISECONDS.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
					System.out.println("Thread interrupted");
					return;
				}
			}
		};

		Thread evenNumber = new Thread(runnable);
		oddNumber.start();
		evenNumber.start();

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		oddNumber.interrupt();

	}
}
