package com.austral.jibberjabbermessages.services;

import com.austral.jibberjabbermessages.clients.UserClient;
import com.austral.jibberjabbermessages.dtos.ChatInfoDto;
import com.austral.jibberjabbermessages.dtos.ReducedUserDto;
import com.austral.jibberjabbermessages.dtos.SendMessageDto;
import com.austral.jibberjabbermessages.models.Chat;
import com.austral.jibberjabbermessages.models.Message;
import com.austral.jibberjabbermessages.repository.ChatRepository;
import com.austral.jibberjabbermessages.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MessageService {

    private final ChatRepository chatRepository;

    private final MessageRepository messageRepository;

    private final UserClient userClient;

    @Autowired
    public MessageService(ChatRepository chatRepository, MessageRepository messageRepository, UserClient userClient) {
        this.chatRepository = chatRepository;
        this.messageRepository = messageRepository;
        this.userClient = userClient;
    }

    public ChatInfoDto getChat(String fromId, String toId) {
        Chat chat = null;
        Optional<Chat> chat1 = chatRepository.findByUser1AndUser2(fromId, toId);
        Optional<Chat> chat2 = chatRepository.findByUser1AndUser2(toId, fromId);

        if (chat1.isPresent()) chat = chat1.get();
        if (chat2.isPresent()) chat = chat2.get();

        ReducedUserDto user1 = userClient.getUserById(fromId);
        ReducedUserDto user2 = userClient.getUserById(toId);

        if (chat != null) {
            return ChatInfoDto.fromChat(chat, user1, user2);
        }
        else throw new IllegalArgumentException("No existing chats");
    }

    public void createChat (String fromId, String toId) {
        Optional<Chat> chat1 = chatRepository.findByUser1AndUser2(fromId, toId);
        Optional<Chat> chat2 = chatRepository.findByUser1AndUser2(toId, fromId);

        if (chat1.isEmpty() && chat2.isEmpty()){
            Chat chat = new Chat(fromId,toId);
            Chat savedChat = chatRepository.save(chat);
        }
        else throw new IllegalArgumentException("Chat already exists");
    }

    public ChatInfoDto sendMessage(String fromId, String toId, SendMessageDto messageDto) {
        Chat chat = null;
        Optional<Chat> chat1 = chatRepository.findByUser1AndUser2(fromId, toId);
        Optional<Chat> chat2 = chatRepository.findByUser1AndUser2(toId, fromId);

        if (chat1.isPresent()) chat = chat1.get();
        if (chat2.isPresent()) chat = chat2.get();

        if (chat1.isEmpty() && chat2.isEmpty()) {
            createChat(fromId,toId);
        }

        Optional<Chat> chat3 = chatRepository.findByUser1AndUser2(fromId, toId);
        Optional<Chat> chat4 = chatRepository.findByUser1AndUser2(toId, fromId);

        if (chat3.isPresent()) chat = chat3.get();
        if (chat4.isPresent()) chat = chat4.get();

        ReducedUserDto user1 = userClient.getUserById(fromId);
        ReducedUserDto user2 = userClient.getUserById(toId);

        if (chat != null) {
            String otherUser = fromId.equals(messageDto.getSentBy()) ? toId : fromId;
            Message message = new Message(messageDto.getContent(), messageDto.getSentBy(), otherUser);
            Set<Message> messages = chat.getMessages();
            messages.add(message);
            chat.setMessages(messages);
            Chat savedChat = chatRepository.save(chat);

            return ChatInfoDto.fromChat(savedChat,user1,user2);
        }
        else throw new IllegalArgumentException("Chat does not exist");
    }

    public List<ChatInfoDto> getChats (String userId) {
        List<Chat> chats = chatRepository.findAllByUser1OrUser2(userId,userId);
        return chats.stream().map(this::getChatData).collect(Collectors.toList());
    }

    private ChatInfoDto getChatData (Chat chat) {
        ReducedUserDto user1 = userClient.getUserById(chat.getUser1());
        ReducedUserDto user2 = userClient.getUserById(chat.getUser2());

        return ChatInfoDto.fromChat(chat,user1,user2);
    }
}
