package com.graalvm.service;


import lombok.SneakyThrows;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.HostAccess;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.Value;
import org.graalvm.polyglot.io.ByteSequence;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class WasmPrimeGeneratorService {

    private final Context context;


    public WasmPrimeGeneratorService() {
        context = Context.newBuilder("wasm").allowAllAccess(true).allowHostClassLoading(true).allowHostAccess(HostAccess.ALL).build();
    }

    @SneakyThrows
    public Integer getLastPrime(int count) {
        byte[] binary =  getClass().getResourceAsStream("/cprime.wasm").readAllBytes();
        Source.Builder sourceBuilder = Source.newBuilder("wasm", ByteSequence.create(binary), "example");
        Source source = sourceBuilder.build();
        context.eval(source);

        Value mainFunction = context.getBindings("wasm").getMember("main").getMember("_start");
        mainFunction.execute();
        return count;
    }

}
