package org.example.a24_12_sparks.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Schema(description = "Représente un étudiant avec un nom et une note")
public class StudentBean {

    @Schema(description = "Nom de l'étudiant", example = "bob")
    private String name;
    @Schema(description = "Note de l'étudiant", example = "12")
    private int note;

}
