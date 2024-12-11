package org.example.a24_12_sparks.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.example.a24_12_sparks.model.MessageBean;
import org.example.a24_12_sparks.model.StudentBean;
import org.springframework.web.bind.annotation.*;


@RestController
public class MyRestController {

    //http://localhost:8080/test
    @GetMapping("/test")
    public String testMethode() {
        System.out.println("/test");

        return "<b>helloWorld</b>";
    }

    //http://localhost:8080/receiveStudent
//Json Attendu : {"name": "toto", "note": 12}
    @PostMapping("/receiveStudent")
    public void receiveStudent(@RequestBody StudentBean student) {
        System.out.println("/receiveStudent : " + student.getName() + " : " + student.getNote());

        //traitement, mettre en base…
        //Retourner d'autres données
    }


    @Operation(
            summary = "Obtenir un étudiant",
            description = "Obtenir toujours le même étudiant",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Liste des messages retournée avec succès",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = MessageBean.class)
                            )
                    ),
                    @ApiResponse(responseCode = "500", description = "Erreur serveur")
            }
    )
    //http://localhost:8080/getStudent
    @GetMapping("/getStudent")
    public StudentBean getStudent() {
        System.out.println("/getStudent");

        StudentBean studentBean = new StudentBean("toto", 12);
        return studentBean;
    }

    //http://localhost:8080/max2?p1=12&p2=15
    @GetMapping("/max2")
    public Integer max2(Integer p1, Integer p2) {
        System.out.println("p1=" + p1 + " p2=" + p2);

        if (p1 == null) {
            return p2;
        } else if (p2 == null) {
            return p1;
        }

        return Math.max(p1, p2);
    }

    //http://localhost:8080/max?p1=12&p2=15
    @GetMapping("/max")
    public Integer max(String p1, String p2) {
        System.out.println("p1=" + p1 + " p2=" + p2);

        Integer p1Int = null;
        try {
            p1Int = Integer.parseInt(p1);
        } catch (Exception e) {

        }

        Integer p2Int = null;
        try {
            p2Int = Integer.parseInt(p2);
        } catch (Exception e) {

        }

        if (p1Int == null) {
            return p2Int;
        } else if (p2Int == null) {
            return p1Int;
        }

        return Math.max(p1Int, p2Int);
    }

    @Operation(
            summary = "Créer un étudiant",
            description = "Crée un étudiant avec un nom et une note, et retourne l'objet étudiant créé."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Étudiant créé avec succès",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = StudentBean.class)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Requête invalide"),
            @ApiResponse(responseCode = "500", description = "Erreur serveur")
    })
    @GetMapping("/createStudent")
    public StudentBean createStudent(
            @Parameter(description = "Nom de l'étudiant", example = "bob", required = true)
            String name,
            @Parameter(description = "Note de l'étudiant (par défaut 0)", example = "12")
            @RequestParam(value = "notation", defaultValue = "0") int note) {
        //name contiendra bob
        //note contiendra 12
        System.out.println("/createStudent : name=" + name + " note=" + note);

        return new StudentBean(name, note);
    }

}