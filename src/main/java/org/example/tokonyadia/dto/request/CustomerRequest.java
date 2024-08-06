package org.example.tokonyadia.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.tokonyadia.entity.User;

import java.util.Date;
import java.util.Optional;


@Data //Setter Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerRequest {
    private String id;
    private String name;
    private String address;
    @JsonProperty("phone_number")
    private String phone;
    private Date birthDate;

    private User user;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class AuthRequest<T> {
        private String username;
        private String password;
        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        private Optional<T> data;
    }
}
