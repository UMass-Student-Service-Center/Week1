/**
 * Created by Lundy on 2/18/2018.
 */

package com.example.u.ussc;

public class ConversationItem {
    private String muserId1;
    private String muserId2;
    private String muser1Image;
    private String muser2Image;
    private String conversationId;
    private MessageItem[] messages;

    public  ConversationItem() {}

    public  ConversationItem(String _key, String _muserid,  String _muserEmail, String _name, String muri) {

    }

    public  ConversationItem(String _key, String _muserid,  String _muserEmail, String _name) {

    }

    // Getters and Setters
    public String getMuserId1() {return muserId1;}

    public void setMuserId1(String muserId1) {this.muserId1 = muserId1;}

    public String getMuserId2() {return muserId2;}

    public void setMuserId2(String muserId2) {this.muserId2 = muserId2;}

    public String getMuser1Image() {return muser1Image;}

    public void setMuser1Image(String muser1Image) {this.muser1Image = muser1Image;}

    public String getMuser2Image() {return muser2Image;}

    public void setMuser2Image(String muser2Image) {this.muser2Image = muser2Image;}

    public String getConversationId() {return conversationId;}

    public void setConversationId(String conversationId) {this.conversationId = conversationId;}

    public MessageItem[] getMessages() {return messages;}

    public void setMessages(MessageItem[] messages) {this.messages = messages;}
}