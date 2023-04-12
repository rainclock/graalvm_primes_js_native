package com.graalvm.service;


import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.HostAccess;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class JavaNativePrimeGeneratorService {



    public JavaNativePrimeGeneratorService() {
    }

    public Integer getLastPrime(int count) {
        int possiblePrime = 3;
        int totalFoundPrimes = 0;
        while (totalFoundPrimes < count) {
            if (isPrimeBruteForce(possiblePrime)) {
                totalFoundPrimes++;
            }
            possiblePrime++;
        }

        return possiblePrime;
    }

    public boolean isPrimeBruteForce(int number) {
        for (int i = 2; i < number; i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }

}
