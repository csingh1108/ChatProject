package com.project.chat.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatMessage {

    private String chatText;
    private String senderName;
    private String recipientName;
    private String date;
    private MessageType status;
}
