package com.austral.jibberjabbermessages.repository;

import com.austral.jibberjabbermessages.models.Message;
import org.springframework.data.repository.CrudRepository;

public interface MessageRepository extends CrudRepository<Message,String> {
}
