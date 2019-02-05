/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.primefinder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 *
 */
public class Control extends Thread {

	private final static int NTHREADS = 3;
	private final static int MAXVALUE = 30000000;
	private final static int TMILISECONDS = 5000;

	private final int NDATA = MAXVALUE / NTHREADS;

	private PrimeFinderThread pft[];
	Scanner scanner = new Scanner(System.in);

	private Control() {
		super();
		this.pft = new PrimeFinderThread[NTHREADS];

		int i;
		for (i = 0; i < NTHREADS - 1; i++) {

			PrimeFinderThread elem = new PrimeFinderThread(i * NDATA, (i + 1) * NDATA);

			pft[i] = elem;
		}
		pft[i] = new PrimeFinderThread(i * NDATA, MAXVALUE + 1);
	}

	public static Control newControl() {
		return new Control();
	}

	@Override
	public void run() {
		for (int i = 0; i < NTHREADS; i++) {
			pft[i].start();
		}
		parar();
	}

	synchronized public void parar() {
		while (true) {
			try {
				for (int i = 0; i < NTHREADS; i++) {
					synchronized (pft[i]) {
						pft[i].wait(TMILISECONDS / NTHREADS);
						System.out.println(i + 1 + ":" + pft[i].getPrimes().size());
					}
				}
				Scanner sc = new Scanner(System.in);
				sc.nextLine();
				notifyAll();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

}
