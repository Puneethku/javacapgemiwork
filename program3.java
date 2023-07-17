package javaassig;


	class EvenThread extends Thread {
	    @Override
	    public void run() {
	        for (int i = 2; i <= 20; i += 2) {
	            System.out.println("Even Thread: " + i);
	        }
	    }
	}

	class OddThread extends Thread {
	    @Override
	    public void run() {
	        for (int i = 1; i <= 20; i += 2) {
	            System.out.println("Odd Thread: " + i);
	        }
	    }
	}

	public class program3 {
	    public static void main(String[] args) {
	        EvenThread evenThread = new EvenThread();
	        OddThread oddThread = new OddThread();

	        evenThread.start();
	        oddThread.start();
	    }
	}

