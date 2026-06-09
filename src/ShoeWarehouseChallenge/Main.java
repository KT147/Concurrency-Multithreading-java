package ShoeWarehouseChallenge;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

record Order(int id, String item, int quantity) {
}

class WareHouse {
	private List<Order> orders = new ArrayList<>();
	public final static String[] shoes = {"JOOKSUTOSSUD", "PIDULIKUD_KINGAD", "KONTSAKINGAD", "PLÄTUD"};

	private final ExecutorService consumerExecutor;

	public WareHouse() {
		consumerExecutor = Executors.newSingleThreadExecutor();

		consumerExecutor.execute(() -> {
			while (true) {
				try {
					fulfillOrder();
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		});
	}


	public synchronized Order receiveOrder(Order o) {
		while (orders.size() >= 5) {
			try {
				wait();
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
		orders.add(o);
		System.out.println("Order received: " + o);
		notifyAll();
		return o;
	}


	public synchronized Order fulfillOrder() {
		while (orders.isEmpty()) {
			try {
				wait();
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
		var removedOrder = orders.remove(0);
		System.out.println("Order fulfilled: " + removedOrder);
		notifyAll();
		return removedOrder;
	}



}


public class Main {
	public static void main(String[] args) {

		WareHouse wareHouse = new WareHouse();

		Random random = new Random();

		var multiExecutor = Executors.newFixedThreadPool(1);


		Runnable runnable1 = () -> {
			for (int i = 1; i <= 15; i++) {
				try {
					Order o = new Order(i, WareHouse.shoes[random.nextInt(0, 4)], random.nextInt(1, 4));
					wareHouse.receiveOrder(o);
					TimeUnit.MILLISECONDS.sleep(500);
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}
		};


		multiExecutor.execute(runnable1);

		multiExecutor.shutdown();



//		Thread producer = new Thread(runnable1);
//		Thread consumer = new Thread(runnable2);
////		producer.start();
//		consumer.start();
	}
}
