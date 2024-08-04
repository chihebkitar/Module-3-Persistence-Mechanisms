package com.course.demo.services;

import com.course.demo.json.AstroResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;

@Service // Marks this class as a Spring-managed service component, which is a stereotype for a service layer.
public class AstroService {

    // RestTemplate is a synchronous client that simplifies making HTTP requests and handling responses, encapsulating HTTP connection management.
    // RestTemplate instance is declared as final to ensure it is immutable after being set in the constructor.
    private final RestTemplate template; // Declares a final instance of RestTemplate to ensure immutability after construction.

    private final WebClient client; // WebClient is the non-blocking, reactive web client to perform HTTP requests.

    // Constructor for AstroService; uses a specific RestTemplate provided via dependency injection and configured elsewhere in the application.
    public AstroService(@Qualifier("astroRestTemplate") RestTemplate template) {
        // The 'template' parameter is annotated with @Qualifier to specify exactly which bean to inject
        // in case there are multiple beans of type RestTemplate. This is particularly useful when different configurations
        // of RestTemplate are needed for different purposes.
        this.template = template; // Sets the RestTemplate instance using the injected bean.
        this.client = WebClient.create("http://api.open-notify.org"); // Creates WebClient instance with a base URI.
    }

    // Synchronously fetches the current number of people in space using RestTemplate.
    public String getPeopleInSpace() {
        // Performs a GET request using RestTemplate and retrieves the result as a String.
        return this.template.getForObject("/astros.json", String.class); // Fetches data as a String from the specified endpoint.
    }

    // Synchronously fetches a structured response about the people in space using RestTemplate.
    public AstroResponse getAstroResponse() {
        // Performs a GET request and automatically deserializes the JSON response into an AstroResponse object.
        return this.template.getForObject("/astros.json", AstroResponse.class); // Fetches and deserializes data from the specified endpoint.
    }

    // Asynchronously fetches a structured response about the people in space using WebClient.
    public AstroResponse getAstroResponseAsync() {
        // Initiates a GET request using WebClient, which is designed to handle asynchronous operations.
        return this.client.get() // Start defining the GET request.
                .uri("/astros.json") // Specifies the relative URI for the request.
                .accept(MediaType.APPLICATION_JSON) // Sets the 'Accept' header to expect JSON response.
                .retrieve() // Initiates the request and retrieves the response.
                .bodyToMono(AstroResponse.class) // Converts the response body to Mono (a reactive data type) of AstroResponse.
                .block(Duration.ofSeconds(9)); // Blocks execution, waiting for the result for up to 9 seconds. This should be avoided in a fully reactive stack.
    }
}
