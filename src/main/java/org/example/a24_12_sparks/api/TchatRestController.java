package org.example.a24_12_sparks.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.a24_12_sparks.model.MessageBean;
import org.example.a24_12_sparks.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/tchat")
@Tag(name = "Tchat", description = "API pour gérer les messages d'un tchat")
public class TchatRestController {

    @Autowired
    private MessageService messageService;

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
        messageService.addMessage(message);
    }

    //http://localhost:8080/tchat/allMessages
    @GetMapping("/allMessages")
    public List<MessageBean> allMessages() {
        System.out.println("/allMessages");

        return messageService.getAll();
    }

    //http://localhost:8080/tchat/testTransactional
    @GetMapping("/testTransactional")
    public List<MessageBean> Transactional() {
        System.out.println("/Transactional");

        try {
            messageService.addMultipleMessage();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return messageService.getAll();
    }
}
