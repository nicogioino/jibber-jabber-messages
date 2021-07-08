package com.austral.jibberjabbermessages.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateChatDto {

    private String user1;

    private String user2;
}
