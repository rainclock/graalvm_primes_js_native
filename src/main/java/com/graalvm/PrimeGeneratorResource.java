package com.graalvm;

import com.graalvm.dto.Tuple;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.HostAccess;

import javax.ws.rs.*;
import java.util.Date;

@Path("/primes")
public class PrimeGeneratorResource {

    public PrimeGeneratorResource() {
    }

    @Path("js/{count:\\d+}")
    @GET()
    public Tuple primesJs(int count) {
        Date initDate = new Date();
        //This method returns the time in millis
        long timeMilli = initDate.getTime();
        Integer lastPrime = 0;
        // js population
        try (Context context = Context.newBuilder("js").allowAllAccess(true).allowHostClassLoading(true).allowHostAccess(HostAccess.ALL).build()) {
            context.getBindings("js").putMember("count", count);
            context.eval("js", "var possiblePrime = 3;var totalFoundPrimes = 0;while (totalFoundPrimes < count) {let isPrime = true;for (let i = 2; i < possiblePrime; i++) {" +
                    "        if (possiblePrime % i == 0) {" +
                    "            isPrime = false;" +
                    "            break;" +
                    "        }" +
                    "    }" +
                    "       if (isPrime) {totalFoundPrimes++;}possiblePrime++;}");
            lastPrime = context.getBindings("js").getMember("possiblePrime").as(Integer.class);
        }

        Date endDate = new Date();
        //This method returns the time in millis
        long endTimeMilli = endDate.getTime();
        Tuple tuple = new Tuple();
        tuple.ms = endTimeMilli - timeMilli;
        tuple.lastPrime = lastPrime;
        return tuple;
    }

    @Path("native/{count:\\d+}")
    @GET()
    public Tuple primesNative(int count) {
        Date initDate = new Date();
        //This method returns the time in millis
        long timeMilli = initDate.getTime();

        int possiblePrime = 3;
        int totalFoundPrimes = 0;
        while (totalFoundPrimes < count) {
            if (isPrimeBruteForce(possiblePrime)) {
                totalFoundPrimes++;
            }
            possiblePrime++;
        }

        Date endDate = new Date();
        //This method returns the time in millis
        long endTimeMilli = endDate.getTime();

        Tuple tuple = new Tuple();
        tuple.ms = endTimeMilli - timeMilli;
        tuple.lastPrime = possiblePrime;
        return tuple;



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