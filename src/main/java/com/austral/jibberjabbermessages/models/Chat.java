package com.austral.jibberjabbermessages.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Chat {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(nullable = false)
    private String id;

    private String user1;

    private String user2;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<Message> messages;

    public Chat(String user1, String user2) {
        this.user1 = user1;
        this.user2 = user2;
        messages = new HashSet<>();
    }
}
