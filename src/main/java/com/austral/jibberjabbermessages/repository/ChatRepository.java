package com.austral.jibberjabbermessages.repository;

import com.austral.jibberjabbermessages.models.Chat;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ChatRepository extends CrudRepository<Chat,String> {

    Optional<Chat> findByUser1AndUser2(String user1, String user2);

    List<Chat> findAllByUser1OrUser2(String user1,String user2);

}
