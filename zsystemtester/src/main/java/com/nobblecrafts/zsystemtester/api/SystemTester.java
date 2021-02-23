package com.nobblecrafts.zsystemtester.api;

import com.nobblecrafts.zsystemtester.models.TestReport;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SystemTester {

    /**
     * Observes an report object
     * 
     * @param <T>
     * @param object
     * @return
     */
    public <T extends TestReport> Mono<Void> observe(T object);

    /**
     * Observes an Mono of report object
     * 
     * @param <T>
     * @param object
     * @return
     */
    public <T extends TestReport> Mono<Void> observe(Mono<T> object);

    /**
     * Observes an Flux of report objects
     * 
     * @param <T>
     * @param object
     * @return
     */
    public <T extends TestReport> Mono<Void> observe(Flux<T> object);

    /**
     * Connects the flux
     * 
     * @param <T>
     * @param object
     * @return
     */
    public SystemTester connect();

}
