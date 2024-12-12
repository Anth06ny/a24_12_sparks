package org.example.a24_12_sparks.service;

import org.example.a24_12_sparks.model.MessageBean;
import org.example.a24_12_sparks.model.repository.MessageRepository;
import org.example.a24_12_sparks.services.MessageService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MessageServiceTest {


    @Mock
    MessageRepository messageRepository;// = Mockito.mock(MessageRepository.class);

    @InjectMocks
    private MessageService messageService;

    @Test
    public void testAddMessage() {
        // Préparation des données
        MessageBean messageBean = new MessageBean(null, "Alice", "Hello World");

        when(messageRepository.save(any(MessageBean.class))).thenAnswer(invocation -> {
            MessageBean message = invocation.getArgument(0);
            // Simuler l'attribution d'un ID
            message.setId(1L);
            return message;
        });

        // Exécution de la méthode à tester
        messageService.addMessage(messageBean);

        assertEquals(messageBean.getId(), 1L, "Id non ajouté");
        assertTrue(messageBean.getId() > 0, "L'id n'a pas été modifié");
        verify(messageRepository, times(1)).save(messageBean);

        // Vérification que messageRepository.save() a été appelé avec le bon argument
        when(messageRepository.findById(1L)).thenReturn(java.util.Optional.of(new MessageBean(1l, messageBean.getPseudo(), messageBean.getMessage())));

        MessageBean inDatabase = messageService.findById(messageBean.getId());

        assertNotNull(inDatabase, "Message non retrouvé en base");
        assertEquals(messageBean, inDatabase, "Les attributs sont différents");
        assertNotSame(messageBean, inDatabase, "C'est la même instance de message");

    }


    @Test
    public void testGet10Last() {
        ArrayList<MessageBean> messages10 = new ArrayList<>();

        // Préparation des données
        for (int i = 1; i <= 15; i++) {
            MessageBean message = new MessageBean(null, "User" + i, "Message " + i);
            messageService.addMessage(message);
            messages10.add(message);
        }

        when(messageRepository.findFirst10ByOrderByIdDesc()).thenReturn(messages10.subList(5, 15));

        // Exécution de la méthode à tester
        List<MessageBean> messages = messageService.get10Last();

        // Vérifications
        assertEquals(10, messages.size());
        assertEquals("Message 15", messages.get(messages.size() - 1).getMessage());
    }
}
