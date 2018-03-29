/**
 * Created by Lundy on 3/27/2018.
 */
package com.example.u.ussc;

public class MessageItem {
    private String messageId;
    private String senderId;
    private String message;

    public MessageItem (){}

    public MessageItem (String _messageId, String _senderId, String _message){

    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessage_id(String messageId) {
        this.messageId = messageId;
    }

    public String getSender_id() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
