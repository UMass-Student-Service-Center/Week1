/**
 * Created by Lundy on 3/27/2018.
 */
package com.example.u.ussc;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MessageItem {
    private String messageId;
    private String senderId;
    private String message;
    private String messageDate;

    public MessageItem (String _messageId, String _senderId, String _message){
        messageId = _messageId;
        senderId = _senderId;
        message = _message;
        messageDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault()).format(new Date());
    }

    //Getters and Setters
    public void setMessagId(String messageId) { this.messageId = messageId; }

    public void setSenderId(String senderId) { this.senderId = senderId; }

    public void setMessage(String message) { this.message = message; }

    public void setMessageDate(String messageDate) { this.messageDate = messageDate; }

    public String getMessageId() { return messageId; }

    public String getSenderId() { return senderId; }

    public String getMessage() { return message; }

    public String getMessageDate() { return messageDate; }
}
