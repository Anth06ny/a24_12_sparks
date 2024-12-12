package org.example.a24_12_sparks.services;

import org.example.a24_12_sparks.model.MessageBean;
import org.example.a24_12_sparks.model.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    public List<MessageBean> get10Last() {
        return messageRepository.findFirst10ByOrderByIdDesc();
    }

    public MessageBean findById(Long id) {
        return messageRepository.findById(id).orElse(null);
    }

    public MessageBean addMessage(MessageBean message) {
        messageRepository.save(message);
        return message;
    }

    public List<MessageBean> last10() {
        return messageRepository.findTop10ByOrderByIdDesc();
    }

    public List<MessageBean> getAll() {
        return messageRepository.findAll();
    }

    @Transactional
    public void addMultipleMessage()  {

       addMultipleMessage2();

        MessageBean m1 = new MessageBean(null, "Test", "Bobby");
        MessageBean m2 = new MessageBean(null, "Test2", "Bobby");

        messageRepository.save(m1);

        if (m1.getId() != null) {
            throw new RuntimeException("Erreur");
        }


        messageRepository.save(m2);
    }

    @Transactional
    public void addMultipleMessage2()  {
        MessageBean m1 = new MessageBean(null, "Test3", "Bobby");
        MessageBean m2 = new MessageBean(null, "Test4", "Bobby");

        messageRepository.save(m1);
        messageRepository.save(m2);
    }
}