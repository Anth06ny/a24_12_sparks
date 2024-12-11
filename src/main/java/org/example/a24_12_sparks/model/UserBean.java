package org.example.a24_12_sparks.model;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserBean {
    private Long id;
    private String login;
    @Size(min = 3, message = "Il faut au moins 3 caractères")
    private String password;
}
