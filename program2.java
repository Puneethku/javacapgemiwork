package javaassig;
	import java.util.concurrent.locks.Lock;
	import java.util.concurrent.locks.ReentrantLock;

	class BankAccount {
	    private double balance;
	    private Lock lock;

	    public BankAccount(double initialBalance) {
	        this.balance = initialBalance;
	        this.lock = new ReentrantLock();
	    }

	    public void deposit(double amount) {
	        lock.lock();
	        try {
	            balance += amount;
	            System.out.println(Thread.currentThread().getName() + " deposited " + amount + " units");
	        } finally {
	            lock.unlock();
	        }
	    }

	    public void withdraw(double amount) {
	        lock.lock();
	        try {
	            if (balance >= amount) {
	                balance -= amount;
	                System.out.println(Thread.currentThread().getName() + " withdrew " + amount + " units");
	            } else {
	                System.out.println(Thread.currentThread().getName() + " insufficient funds");
	            }
	        } finally {
	            lock.unlock();
	        }
	    }

	    public double getBalance() {
	        return balance;
	    }
	}

	class Transaction implements Runnable {
	    private final BankAccount account;
	    private final boolean isDeposit;
	    private final double amount;

	    public Transaction(BankAccount account, boolean isDeposit, double amount) {
	        this.account = account;
	        this.isDeposit = isDeposit;
	        this.amount = amount;
	    }

	    @Override
	    public void run() {
	        if (isDeposit) {
	            account.deposit(amount);
	        } else {
	            account.withdraw(amount);
	        }
	    }
	}

	public class program2 {
	    public static void main(String[] args) {
	        BankAccount account = new BankAccount(1000.0);

	        int numThreads = 5;
	        double depositAmount = 100.0;
	        double withdrawAmount = 200.0;

	        Thread[] threads = new Thread[numThreads];

	        for (int i = 0; i < numThreads; i++) {
	            if (i % 2 == 0) {
	                Transaction transaction = new Transaction(account, true, depositAmount);
	                threads[i] = new Thread(transaction, "Thread-" + i);
	            } else {
	                Transaction transaction = new Transaction(account, false, withdrawAmount);
	                threads[i] = new Thread(transaction, "Thread-" + i);
	            }
	        }

	        for (Thread thread : threads) {
	            thread.start();
	        }

	        for (Thread thread : threads) {
	            try {
	                thread.join();
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        }

	        System.out.println("Final balance: " + account.getBalance());
	    }
	}

