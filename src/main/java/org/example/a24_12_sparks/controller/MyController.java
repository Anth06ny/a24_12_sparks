package org.example.a24_12_sparks.controller;

import jakarta.servlet.http.HttpSession;
import org.example.a24_12_sparks.config.WebSocketConfig;
import org.example.a24_12_sparks.model.MessageBean;
import org.example.a24_12_sparks.model.StudentBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MyController {


    //http://localhost:8080/hello
    @GetMapping("/hello")
    public String testHello(Model model, HttpSession httpSession){
        System.out.println("/hello");


        ArrayList<StudentBean> liststudent = new ArrayList<>();
        liststudent.add(new StudentBean("Toto", 12));
        liststudent.add(new StudentBean("Tata", 14));

        model.addAttribute("text", "Bonjour " + httpSession.getId() + " ");
        model.addAttribute("studentBean", new StudentBean("Toto", 12));
        model.addAttribute("studentList", liststudent);

        //Nom du fichier HTML que l'on souhaite afficher
        return "welcome";
    }
}
