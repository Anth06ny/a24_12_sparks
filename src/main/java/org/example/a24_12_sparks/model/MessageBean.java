package org.example.a24_12_sparks.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Entity
@Table(name = "message")
public class MessageBean {
    @Id
    ///ID auto incrémenté
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String pseudo, message;
}
