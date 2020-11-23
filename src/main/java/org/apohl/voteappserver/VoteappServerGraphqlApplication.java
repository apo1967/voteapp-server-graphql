package org.apohl.voteappserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * The Spring Boot application
 */
@SpringBootApplication
@RestController
public class VoteappServerGraphqlApplication {

    public static void main(String[] args) {
        SpringApplication.run(VoteappServerGraphqlApplication.class, args);
    }

    /**
     * Just for testing. Please note, that the mapping won't work if GraphQL is mapped to the root path "/" because
     * then the "/ping" request will never reach this controller.
     *
     * @param name will be passed back to the client in a welcome string
     * @return the response json
     */
    @GetMapping(value = {"/ping"})
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
        return String.format("Hello %s!", name);
    }
}
