package org.example.moviesservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Entity
@Table(name = "movie")
public class Movie {
    @Id
    ///ID auto incrémenté
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title, length;
}
