package com.graalvm.controller;

import com.graalvm.dto.Tuple;
import com.graalvm.service.JavaNativePrimeGeneratorService;
import com.graalvm.service.JsPrimeGeneratorService;
import com.graalvm.service.WasmPrimeGeneratorService;
import lombok.AllArgsConstructor;

import javax.ws.rs.*;
import java.util.Date;

@AllArgsConstructor
@Path("/primes")
public class PrimeGeneratorResource {

    private final JsPrimeGeneratorService jsPrimeGeneratorService;
    private final JavaNativePrimeGeneratorService javaNativePrimeGeneratorService;
    private final WasmPrimeGeneratorService wasmPrimeGeneratorService;


    @Path("js/{count:\\d+}")
    @GET()
    public Tuple primesJs(int count) {
        Date initDate = new Date();
        //This method returns the time in millis
        long timeMilli = initDate.getTime();
        Integer lastPrime = 0;


        // js population
        lastPrime = jsPrimeGeneratorService.getLastPrime(count);

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

        Integer possiblePrime = javaNativePrimeGeneratorService.getLastPrime(count);

        Date endDate = new Date();
        //This method returns the time in millis
        long endTimeMilli = endDate.getTime();
        Tuple tuple = new Tuple();
        tuple.ms = endTimeMilli - timeMilli;
        tuple.lastPrime = possiblePrime;
        return tuple;

    }

    @Path("wasm/{count:\\d+}")
    @GET()
    public Tuple primeWasm(int count) {
        Date initDate = new Date();
        //This method returns the time in millis
        long timeMilli = initDate.getTime();


        Date endDate = new Date();
        //This method returns the time in millis
        long endTimeMilli = endDate.getTime();
        Integer possiblePrime = wasmPrimeGeneratorService.getLastPrime(count);
        Tuple tuple = new Tuple();
        tuple.ms = endTimeMilli - timeMilli;
        tuple.lastPrime = possiblePrime;
        return tuple;

    }



}