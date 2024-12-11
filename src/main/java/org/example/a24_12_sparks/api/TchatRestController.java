package org.example.a24_12_sparks.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.a24_12_sparks.model.MessageBean;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;


@RestController
@RequestMapping("/tchat")
@Tag(name = "Tchat", description = "API pour gérer les messages d'un tchat")
public class TchatRestController {

    private ArrayList<MessageBean> list = new ArrayList<>();

    @Operation(
            summary = "Enregistrer un message",
            description = "Permet d'enregistrer un nouveau message envoyé par un utilisateur"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Message enregistré avec succès"
            )
    })
    @PostMapping("/saveMessage")
    public void saveMessage(@RequestBody MessageBean message) {
        System.out.println("/saveMessage : " + message.getMessage() + " : " + message.getPseudo());
        list.add(message);
    }

    @Operation(
            summary = "Obtenir les derniers messages",
            description = "Récupère les 10 derniers messages enregistrés dans le tchat"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Liste des messages retournée avec succès",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = MessageBean.class)
                    )
            )
    })
    @GetMapping("/allMessages")
    public ArrayList<MessageBean> allMessages() {
        System.out.println("/allMessages");

        //pour ne retourner que les 10 derniers
        ArrayList<MessageBean> toReturn = new ArrayList<>();
        for (int i = Math.max(list.size() - 10, 0); i < list.size(); i++) {
            toReturn.add(list.get(i));
        }

        return toReturn;
    }
}
