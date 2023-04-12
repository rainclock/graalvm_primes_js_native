package com.graalvm.service;


import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.HostAccess;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class JsPrimeGeneratorService {

    private final Context context;


    public JsPrimeGeneratorService() {
        context = Context.newBuilder("js").allowAllAccess(true).allowHostClassLoading(true).allowHostAccess(HostAccess.ALL).build();
    }

    public Integer getLastPrime(int count) {
        context.getBindings("js").putMember("count", count);
        context.eval("js", "var possiblePrime = 3;var totalFoundPrimes = 0;while (totalFoundPrimes < count) {let isPrime = true;for (let i = 2; i < possiblePrime; i++) {" +
                "        if (possiblePrime % i == 0) {" +
                "            isPrime = false;" +
                "            break;" +
                "        }" +
                "    }" +
                "       if (isPrime) {totalFoundPrimes++;}possiblePrime++;}");
        return context.getBindings("js").getMember("possiblePrime").as(Integer.class);

    }

}
