package MultiThreadingLearning;

class SleepMethodTest1 extends Thread {
	public void run() {
		for (int i = 0; i < 5; i++) {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				System.out.println(e);
			}
			System.out.println(i);
		}
	}

	public static void main(String args[]) {
		SleepMethodTest1 t1 = new SleepMethodTest1();
		SleepMethodTest1 t2 = new SleepMethodTest1();
		t1.start();
		t2.start();
	}
}