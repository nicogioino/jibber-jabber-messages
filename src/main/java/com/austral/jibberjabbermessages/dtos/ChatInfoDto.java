package com.austral.jibberjabbermessages.dtos;

import com.austral.jibberjabbermessages.models.Chat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatInfoDto {

    ReducedUserDto user1;

    ReducedUserDto user2;

    List<MessageDto> messages;

    public static ChatInfoDto fromChat(Chat chat, ReducedUserDto user1, ReducedUserDto user2) {
        List<MessageDto> messages = chat.getMessages().stream()
                .map(MessageDto::fromMessage)
                .sorted(Comparator.comparing(MessageDto::getSentTime).reversed())
                .collect(Collectors.toList());

        return new ChatInfoDto(user1,user2,messages);
    }
}
