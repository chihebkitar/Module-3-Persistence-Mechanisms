package com.course.demo;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DemoApplicationTests {

    @Autowired
    // The ApplicationContext is automatically injected into this test class by Spring.
    // This allows the test to access application beans and other Spring context features.
    private ApplicationContext context;

    @Test
        // This test ensures that the Spring application context loads correctly.
    void contextLoads() {
        // Asserts that the context is not null, ensuring that the Spring framework has started up and loaded the application context.
        assertNotNull(context);

        // Retrieves the number of beans defined in the application context.
        int count = context.getBeanDefinitionCount();

        // Outputs the count of beans to the console for diagnostic purposes.
        System.out.println("there are " + count + " beans in the app context.");

        // Iterates over all bean names in the application context and prints each one.
        // This is useful for verifying which beans have been configured and are managed by Spring.
        for (String name : context.getBeanDefinitionNames()) {
            System.out.println(name);
        }

        // Asserts that the 'restTemplate' bean is not present in the application context.
        // This might be part of a conditional bean configuration check or environment-specific configuration.
        assertFalse(context.containsBean("restTemplate"));

        // Asserts that the 'restTemplateBuilder' bean exists in the application context.
        // Since 'RestTemplateBuilder' is a part of Spring Boot's auto-configuration, this is expected to be true.
        assertTrue(context.containsBean("restTemplateBuilder"));
    }

    @Test @Disabled
    void getBean() {
      assertThrows(
              NoSuchBeanDefinitionException.class,
              ()-> context.getBean(RestTemplate.class));
    }
}
