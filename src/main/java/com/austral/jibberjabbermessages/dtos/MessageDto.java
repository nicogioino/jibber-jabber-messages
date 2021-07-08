package com.austral.jibberjabbermessages.dtos;

import com.austral.jibberjabbermessages.models.Message;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageDto {

    String content;

    String sentBy;

    LocalDateTime sentTime;

    public static MessageDto fromMessage (Message message) {
        return new MessageDto(message.getContent(), message.getFromId(), message.getSentTime());
    }
}
