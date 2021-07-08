package com.austral.jibberjabbermessages.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Message {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(nullable = false)
    private String id;

    private String content;

    private String fromId;

    private String toId;

    @CreationTimestamp
    private LocalDateTime sentTime;

    public Message(String content, String fromId, String toId) {
        this.content = content;
        this.fromId = fromId;
        this.toId = toId;
    }
}
