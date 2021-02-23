package com.nobblecrafts.zgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * For the gateway pattern, Spring Cloud offers two options. The first
 * alternative is to use an integration that Spring Cloud has been supporting
 * for a long time: Spring Cloud Netflix. This is a project that includes
 * several tools that Netflix developers have been publishing and maintaining as
 * open source software (OSS) for many years. You can take a look at the Netflix
 * OSS website (https://tpd.io/noss) if you want to know more about these tools.
 * The component within Netflix OSS that implements the gateway pattern is Zuul,
 * and its integration with Spring comes via the Spring Cloud Netflix Zuul
 * module. In this second edition of the book, we won’t use Spring Cloud
 * Netflix. The main reason is that Spring seems to be moving away from the
 * Netflix OSS tool integrations and replacing them with other modules that
 * integrate alternative tools, or even with their own implementations. A
 * possible explanation for this change is that Netflix put some of its projects
 * in maintenance mode, like Hystrix (circuit-breaking) and Ribbon (load
 * balancing), so they are no longer in active development. This decision also
 * affects other tools in the Netflix stack since they implement patterns that
 * are often used together. An example is Eureka, the service discovery tool,
 * which relies on Ribbon for load balancing. We’ll go for a newer alternative
 * to implement the gateway pattern: the Spring Cloud Gateway. In this case,
 * this replacement for Zuul is a stand-alone Spring project, so it doesn’t
 * depend on any external tool.
 * 
 * The Spring Cloud Gateway project defines some core concepts (also shown in
 * Figure 8-2).
 * 
 * • Predicate: A condition to be evaluated to decide where to route a request.
 * Cloud Gateway provides a bunch of condition builders based on the request
 * path, request headers, time, remote host, etc. You can even combine them as
 * expressions. They’re also known as route predicates since they always apply
 * to a route.
 * 
 * • Route: It’s the URI where the request will be proxied to if it matches the
 * assigned predicate. For example, it could address an external request to an
 * internal microservice endpoint, as we’ll see later in practice.
 * 
 * • Filter: An optional processor that can be either attached to a route (route
 * filters) or applied globally (global filters) to all the requests. Filters
 * allow modifying requests (incoming filters) and responses (outgoing filters).
 * There a lot of built-in filters in Spring Cloud Gateway, so you can, for
 * example, add or remove headers in the request, limit the number of requests
 * from a given host, or transform a response from the proxied service before
 * returning it to the requester.
 * 
 * our API gateway will be connected to the service registry and will include a
 * load balancer to distribute the load across the instances.
 * 
 * ================================LOAD-BALANCER===============================
 * 
 * All we need to do to take advantage of these new patterns is to add some
 * configuration to the application.yml file. We can divide the changes into
 * three groups.
 * 
 * • Global settings: We give a name to the application and make sure we use the
 * Spring Cloud load balancer implementation. Besides, we’ll add a configuration
 * parameter to instruct the service discovery client to retrieve only the
 * healthy services.
 * 
 * • Routing configuration: Instead of using explicit host and ports, we switch
 * to service names with a URL pattern that also enables load balancing.
 * 
 * • Resilience: In case the gateway fails to proxy a request to a service, we
 * want it to retry a few times. We’ll elaborate on this topic.
 * 
 * You might be wondering why it’s necessary to include retries in the service
 * discovery client, despite that we configured it to get only healthy
 * instances. In theory, if they’re all healthy, all calls should succeed. To
 * understand this, we have to review how Consul (and typically any other
 * service discovery tool) works. Each service registers itself with a
 * configured health check to be polled every ten seconds (the default value,
 * but we could also change it). The registry doesn’t know in real time when
 * services are not ready to handle the traffic. It could be the case that
 * Consul successfully checks the health of a given instance and that one goes
 * down immediately after. The registry will list this instance as healthy for a
 * few seconds (almost ten with our configuration) until it notices it’s not
 * available during the next check. Since we want to minimize request errors
 * during that interval too, we can take advantage of the retry pattern to cover
 * these situations. Once the registry gets updated, the Gateway won’t get the
 * unhealthy instance within the service list, so the retries won’t be necessary
 * anymore. Note that lowering the time between checks can reduce the number of
 * errors, but it increases the network traffic.
 * 
 * ============================Circuit Breakers===============================
 * 
 * There might be cases where you don’t want to keep trying future requests to a
 * given service after you know it’s failing. By doing that, you can save time
 * wasted in response timeouts and alleviate potential congestion of the target
 * service. This is especially useful for external service calls when there are
 * no other resilience mechanisms in place like the service registry with health
 * checks. For these scenarios, you can use a circuit breaker. The circuit is
 * closed when everything works fine. After a configurable number of request
 * failures, the circuit becomes open. Then, the requests are not even tried,
 * and the circuit breaker implementation returns a predefined response. Now and
 * then, the circuit may switch to half-open to check again if the target
 * service is working. In that case, the circuit will transition to close. If
 * it’s still failing, it goes back to the open state. Check
 * https://tpd.io/cbreak for more information about this pattern.
 * 
 * Given that we added the Consul starter, the Gateway service is also
 * registering itself in Consul. That is not strictly necessary since other
 * services won’t call the gateway, but it’s still useful for us to check its
 * status. Alternatively, we could set the configuration parameter
 * spring.cloud.consul.discovery.register to false to keep using the service
 * discovery client features but disable the registration of the Gateway
 * service. It must be as highly available as we can. If the Gateway service
goes down, our entire system goes down.
 */

@SpringBootApplication
public class ZgatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZgatewayApplication.class, args);
	}

}
