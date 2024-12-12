package org.example.a24_12_sparks.model.repository;

import org.example.a24_12_sparks.model.MessageBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<MessageBean, Long> {

    List<MessageBean> findFirst10ByOrderByIdDesc();

    //Attention dans la réalité on utilisera la date car l'id n'est pas forcément dans l'ordre
    List<MessageBean> findTop10ByOrderByIdDesc();
    List<MessageBean> findByMessageContainingAndPseudo(String message, String pseudo);
}
