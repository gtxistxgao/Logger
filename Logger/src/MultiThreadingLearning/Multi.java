package MultiThreadingLearning;

class Multi extends Thread implements Runnable{
	public void run() {
		System.out.println("thread is running...");
	}

	public static void main(String args[]) {
		Multi t1 = new Multi();
		t1.run();
		if(t1.isAlive()){
			System.out.println("t1 is alive");
		}
		System.out.println("Now use Runnable interface");
		Multi t2 = new Multi();
		Thread another = new Thread(t2);
		another.start();
	}
}