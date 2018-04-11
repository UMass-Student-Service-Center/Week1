/**
 * Created by Lundy on 3/27/2018.
 */
package com.example.u.ussc;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MessageItem {
    private String messageId;
    private String conversationId;
    private String senderId;
    private String message;
    private String messageDate;

    public MessageItem() {}

    public MessageItem (String _messageId, String _conversationId, String _senderId, String _message){
        messageId = _messageId;
        conversationId = _conversationId;
        senderId = _senderId;
        message = _message;
        messageDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault()).format(new Date());
    }

    //Getters and Setters
    public void setMessageId(String messageId) { this.messageId = messageId; }

    public void setConversationId(String conversationId) { this.conversationId = conversationId; }

    public void setSenderId(String senderId) { this.senderId = senderId; }

    public void setMessage(String message) { this.message = message; }

    public void setMessageDate(String messageDate) { this.messageDate = messageDate; }

    public String getMessageId() { return messageId; }

    public String getConversationId() { return conversationId; }

    public String getSenderId() { return senderId; }

    public String getMessage() { return message; }

    public String getMessageDate() { return messageDate; }
}
