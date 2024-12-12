package org.example.a24_12_sparks.api;

import jakarta.validation.Valid;
import org.example.a24_12_sparks.model.Movie;
import org.example.a24_12_sparks.model.UserBean;
import org.example.a24_12_sparks.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserRestController {

    /* -------------------------------- */
    // Programmation Reactive et loadBalancer
    /* -------------------------------- */

    //Loadbalancer sans WebFlux
    @Autowired
    private RestTemplate restTemplate;

    //WebFlux
    @Autowired//Laisse à Spring le remplissage
    @Qualifier("moviesAPIClient")
    private WebClient moviesAPIClient;

    //WebFlux + LoadBalancer
    @Autowired//Laisse à Spring le remplissage
    @Qualifier("moviesAPIClientWithLoadBalancing")
    private WebClient moviesAPIClientWithLoadBalancing;

    //http://localhost:8080/users/directAccess
    @GetMapping("/directAccess")
    public Movie directAccess() {
        System.out.println("/directAccessReactive ");

        //Synchrone
        return moviesAPIClient.get().uri("1").retrieve().bodyToMono(Movie.class).block();
    }

    //http://localhost:8080/users/directAccessReactive
    @GetMapping("/directAccessReactive")
    public Mono<Movie> directAccessReactive() {
        System.out.println("/directAccessReactive ");

        //Asynchrone
        return moviesAPIClient.get().uri("1").retrieve().bodyToMono(Movie.class);
    }

    //http://localhost:8080/users/eurekaAccess
    @GetMapping("/eurekaAccess")
    public Movie eurekaAccess() {
        System.out.println("/eurekaAccess");

        String url = "http://MoviesService/movies"; // Utilise le nom du micro-service sur Eureka

        //Récéption
        return restTemplate.getForObject(url + "/1", Movie.class);
    }

    //http://localhost:8080/users/eurekaAccessReactive
    @GetMapping("/eurekaAccessReactive")
    public Mono<Movie> eurekaAccessReactive() {
        System.out.println("/eurekaAccessReactive");

        return moviesAPIClientWithLoadBalancing.get().uri("1").retrieve().bodyToMono(Movie.class);
    }

    /* -------------------------------- */
    // API USer standard
    /* -------------------------------- */

    //http://localhost:8080/users
    @GetMapping
    public ResponseEntity<List<UserBean>> getAllUsers() {
        List<UserBean> users = UserService.load();
        return ResponseEntity.ok(users);
    }

    //http://localhost:8080/users/1
    @GetMapping("/{id}")
    public ResponseEntity<UserBean> getUserById(@PathVariable Long id) {
        UserBean userBean = UserService.findById(id);
        if (userBean != null) {
            return ResponseEntity.ok(userBean);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //http://localhost:8080/users
    //{"login":"aaa", "password":"bbb"}
    @PostMapping
    public ResponseEntity<UserBean> createUser(@Validated @Valid @RequestBody UserBean user) {
        user.setId(null);

        UserBean savedUser = UserService.save(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    //http://localhost:8080/users/1
    //{"login":"aaa", "password":"bbb"}
    @PutMapping("/{id}")
    public ResponseEntity<UserBean> updateUser(@PathVariable Long id, @Valid @RequestBody UserBean userDetails) {
        UserBean user = UserService.findById(id);
        if (user != null) {
            userDetails.setId(id);
            UserService.save(userDetails);
            return new ResponseEntity<>(userDetails, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //http://localhost:8080/users/1
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (UserService.deleteById(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
