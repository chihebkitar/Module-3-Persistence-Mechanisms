package com.course.demo.config;

import com.course.demo.services.AstroInterface;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration // Indicates that this class contains Spring configuration and defines beans.
public class AppConfig {
    // This comment explains the concept of beans in the Spring framework:
    // In Spring, the objects that form the backbone of your application and that are managed by the Spring IoC (Inversion of Control) container are called beans. A bean is an object that is instantiated, assembled, and managed by a Spring IoC container. Otherwise, a bean is simply one of many objects in your application.

    @Bean // Marks the method as a bean provider, telling Spring that the method returns an object that should be registered as a bean in the Spring application context.
    public RestTemplate astroRestTemplate(RestTemplateBuilder builder,
                                          @Value("${astro.baseUrl}") String baseUrl){
        // @Value annotation is used to inject 'astro.baseUrl' property value from application properties or configuration files into 'baseUrl'.
        return builder.rootUri(baseUrl).build(); // Uses RestTemplateBuilder to create a RestTemplate instance with a root URI set to the 'baseUrl'. This RestTemplate is configured to connect to the API specified by 'astro.baseUrl'.
    }

    @Bean // Another bean provider method for creating a different RestTemplate instance.
    public RestTemplate anotherRestTemplate(RestTemplateBuilder builder){
        // This RestTemplate is configured with a static URI.
        return builder.rootUri("http://other.html").build(); // Constructs a RestTemplate with a root URI set to 'http://other.html'. This might be used for a different part of the application or another external service.
    }

    @Bean
    public AstroInterface astroInterface() {
        // Creates an instance of RestClient configured with the base URL for the API.
        // This client is responsible for handling low-level HTTP communications.
        RestClient client = RestClient.create("http://api.open-notify.org");

        // Creates an adapter around the RestClient.
        // This adapter likely standardizes the way the RestClient is used to interact with HTTP services,
        // possibly converting between different formats or handling request/response in a standardized manner.
        RestClientAdapter adapter = RestClientAdapter.create(client);

        // Initializes a factory for creating service proxies.
        // This factory is configured specifically to use the RestClientAdapter,
        // ensuring that all proxy clients created from this factory utilize the same HTTP handling strategy.
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();

        // Uses the factory to create a dynamic proxy implementation of the AstroInterface.
        // This proxy allows methods defined in AstroInterface to be automatically implemented via HTTP calls
        // made by the RestClient through the RestClientAdapter.
        return factory.createClient(AstroInterface.class);
    }

}
