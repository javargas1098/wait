package edu.eci.arsw.primefinder;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class PrimeFinderThread extends Thread {

	int a, b;
	boolean flag = true;
	Scanner scanner = new Scanner(System.in);

	private List<Integer> primes;

	public PrimeFinderThread(int a, int b) {
		super();
		this.primes = new LinkedList<>();
		this.a = a;
		this.b = b;
	}

	@Override
	public void run() {
		for (int i = a; i < b; i++) {
			if (isPrime(i)) {

				primes.add(i);
				scanner.nextLine();
				System.out.println(i);
				// this.notifyAll();
			}
			// notify();
		}
	}

	synchronized boolean isPrime(int n) {
		boolean ans;

		if (n > 2) {
			ans = n % 2 != 0;
			for (int i = 3; ans && i * i <= n; i += 2) {
				ans = n % i != 0;
			}
		} else {
			ans = n == 2;
		}

		return ans;
	}

	public List<Integer> getPrimes() {
		while (flag) {
			try {
				wait();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

		notifyAll();

		return primes;
		//
	}

}