/**
 * Created by Lundy on 3/27/2018.
 */

package com.example.u.ussc;

import java.util.ArrayList;

public class ConversationItem {
    private String conversationId;
    private String muserId1;
    private String muserId2;
    private String muser1Image;
    private String muser2Image;
    private String muserName1;
    private String muserName2;
    private String lastMessage;
    private String lastMessageDate;
    private ArrayList<String> messageKeys;

    public ConversationItem () {messageKeys = new ArrayList<String>(0);}

    public  ConversationItem(String _conversationId, String _userid1,  String _userId2, String _uri1,
                             String _uri2) {
        conversationId = _conversationId;
        muserId1 = _userid1;
        muserId2 = _userId2;
        muser1Image = _uri1;
        muser2Image = _uri2;
        messageKeys = new ArrayList<String>(0);
    }

    public  ConversationItem(String _conversationId, String _userid1,  String _userId2, String _uri1,
                             String _uri2, String _muserName1, String _muserName2, String _lastMessage,
                             String _lastMessageDate, String _messageKeys) {
        conversationId = _conversationId;
        muserId1 = _userid1;
        muserId2 = _userId2;
        muser1Image = _uri1;
        muser2Image = _uri2;
        muserName1 = _muserName1;
        muserName2 = _muserName2;
        lastMessage = _lastMessage;
        lastMessageDate = _lastMessageDate;
        messageKeys = new ArrayList<String>(0);
        messageKeys.add(_messageKeys) ;

    }

    // Getters and Setters
    public void setConversationId(String conversationId) {this.conversationId = conversationId;}

    public void setMuserId1(String muserId1) {this.muserId1 = muserId1;}

    public void setMuserId2(String muserId2) {this.muserId2 = muserId2;}

    public void setMuser1Image(String muser1Image) {this.muser1Image = muser1Image;}

    public void setMuser2Image(String muser2Image) {this.muser2Image = muser2Image;}

    public void setMuserName1(String muserName1) { this.muserName1 = muserName1; }

    public void setMuserName2(String muserName2) { this.muserName2 = muserName2; }

    public void setLastMessage(String lastMessage) { this.lastMessage = lastMessage; }

    public void setLastMessageDate(String lastMessageDate) { this.lastMessageDate = lastMessageDate; }

    public void setMessageKeys(ArrayList<String> messageKeys) {this.messageKeys = messageKeys;}

    public String getConversationId() {return conversationId;}

    public String getMuserId1() {return muserId1;}

    public String getMuserId2() {return muserId2;}

    public String getMuser1Image() {return muser1Image;}

    public String getMuser2Image() {return muser2Image;}

    public String getMuserName1() { return muserName1; }

    public String getMuserName2() { return muserName2; }

    public String getLastMessage() { return lastMessage; }

    public String getLastMessageDate() { return lastMessageDate; }

    public ArrayList<String> getMessageKeys() {return messageKeys;}
}
