package com.austral.jibberjabbermessages.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SendMessageDto {
    String content;
    String sentBy;
}
