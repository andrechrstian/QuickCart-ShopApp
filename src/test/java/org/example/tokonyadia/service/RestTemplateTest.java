package org.example.tokonyadia.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.*;

@Data
@Builder
class Post {
    private Integer userId;
    private Integer id;
    private String title;
    private String body;

    @Override
    public String toString() {
        return "Post{" +
                "userId=" + userId +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}

@Data
@Builder
class Todo {
    private Integer id;
    private Integer userId;
    private String title;
    private Boolean completed;
}

public class RestTemplateTest {
    RestTemplate restTemplate;
    private final String BASE_URL = "https://jsonplaceholder.typicode.com";

    @BeforeEach
    void setUpTest() {
        restTemplate = new RestTemplate();
    }

    @Test
    void getMapTest() {
        ResponseEntity<Post> response = restTemplate.getForEntity(BASE_URL + "/posts/1", Post.class);
        HttpHeaders httpHeaders = restTemplate.headForHeaders(BASE_URL + "/posts/1");

        Assertions.assertTrue(Objects.requireNonNull(httpHeaders.getContentType()).includes(MediaType.APPLICATION_JSON));
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Post body = response.getBody();
        assert body != null;
        System.out.println(body.getTitle());
    }


    @Test
    void getClassTest() {
        ResponseEntity<List<Todo>> response = restTemplate.exchange(
                BASE_URL + "/todos",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                });

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        List<Todo> todos = response.getBody();

        assert todos != null;
        for (Todo todo : todos) {
            System.out.println("Todo: " + todo.getTitle() + ", completed: " + todo.getCompleted());
        }
    }


    @Test
    void postTest() {
        Post post = Post.builder()
                .userId(1)
                .id(1)
                .title("Pegi Setiawan")
                .body("Akhirnya Pegi setiawan dibebaskan dari penjara karena terbukti tidak bersalah")
                .build();

        ResponseEntity<Post> response = restTemplate.postForEntity(BASE_URL + "/posts", post, Post.class);
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());

        System.out.println(response.getBody());
    }

//    @Test
//    void getWithAuthTest() {
//        String username = "admin";
//        String password = "1234";
//        String token = "";
//
//        // Bearer Token
//        HttpHeaders headers = new HttpHeaders() {{
//            set("Authorization", "Bearer " + token);
//        }};
//
//        //Basic Auth
//        //HttpHeaders headers = createBasicAuthHeaders(username, password);
//
//        ResponseEntity<String> response = restTemplate.exchange(
//                "https://api.github.com/user",
//                HttpMethod.GET,
//                null,
//                new ParameterizedTypeReference<>() {
//                },
//                headers);
//        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
//        System.out.println(response.getBody());
//    }
//
//
//    HttpHeaders createBasicAuthHeaders(String username, String password) {
//        return new HttpHeaders() {{
//            String auth = username + ":" + password;
//            byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.US_ASCII));
//            String authHeader = "Basic " + new String(encodedAuth);
//            set("Authorization", authHeader);
//            set("Content-Type", "application/json");
//        }};
//    }



    @Test
    void createTransaction() {
        HttpHeaders headers = createBaseAuthHeader();

        Map<String, Object> transactionDetails = new HashMap<>();
        transactionDetails.put("order_id","Order-001");
        transactionDetails.put("gross_amount",5000);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("transaction_details",transactionDetails);

        ResponseEntity<String> response = restTemplate.exchange(
                "https://app.sandbox.midtrans.com/snap/v1/transactions",
                HttpMethod.POST,
                new HttpEntity<>(requestBody, headers),
                new ParameterizedTypeReference<>() {});
        Assertions.assertEquals(HttpStatus.CREATED,response.getStatusCode());
        System.out.println(response.getBody());

    }

    HttpHeaders createBaseAuthHeader() {
        return new HttpHeaders() {{
            String auth = "SB-Mid-server-aVmSQnPk0bRwX2mTREkFZ4R7";
            byte[] encodeAuth = Base64.getEncoder().encode(auth.getBytes());
            String authHeader = "Basic " + new String(encodeAuth);
            set("Accept", "application/json");
            set("Content-Type", "application/json");
            set("Authorization", authHeader);
        }};
    }

}


