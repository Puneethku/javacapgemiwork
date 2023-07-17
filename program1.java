package javaassig;


	import java.util.ArrayList;
	import java.util.List;

	class PrimeCalculator implements Runnable {
	    private final int start;
	    private final int end;
	    private final List<Integer> primes;

	    public PrimeCalculator(int start, int end, List<Integer> primes) {
	        this.start = start;
	        this.end = end;
	        this.primes = primes;
	    }

	    private boolean isPrime(int number) {
	        if (number <= 1) {
	            return false;
	        }
	        for (int i = 2; i <= Math.sqrt(number); i++) {
	            if (number % i == 0) {
	                return false;
	            }
	        }
	        return true;
	    }

	    @Override
	    public void run() {
	        for (int i = start; i <= end; i++) {
	            if (isPrime(i)) {
	                synchronized (primes) {
	                    primes.add(i);
	                }
	            }
	        }
	    }
	}

	public class program1 {
	    public static void main(String[] args) {
	        int limit = 100; 
	        int numThreads = 4; 

	        List<Integer> primes = new ArrayList<>();
	        List<Thread> threads = new ArrayList<>();

	        int numbersPerThread = limit / numThreads;
	        int start = 2;
	        int end = start + numbersPerThread - 1;

	        for (int i = 0; i < numThreads; i++) {
	            if (i == numThreads - 1) {
	                end = limit;
	            }
	            PrimeCalculator calculator = new PrimeCalculator(start, end, primes);
	            Thread thread = new Thread(calculator);
	            threads.add(thread);
	            thread.start();

	            start = end + 1;
	            end = start + numbersPerThread - 1;
	        }

	        for (Thread thread : threads) {
	            try {
	                thread.join();
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        }

	        long sum = 0;
	        for (int prime : primes) {
	            sum += prime;
	        }

	        System.out.println("Sum of prime numbers up to " + limit + " is: " + sum);
	    }
	
	
}
