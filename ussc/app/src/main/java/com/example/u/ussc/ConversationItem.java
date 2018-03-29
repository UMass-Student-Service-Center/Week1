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
    private ArrayList<String> messageKeys;

    public  ConversationItem() {}

    public  ConversationItem(String _conversationId, String _userid1,  String _userId2, String _uri1,
                             String _uri2) {
        conversationId = _conversationId;
        muserId1 = _userid1;
        muserId2 = _userId2;
        muser1Image = _uri1;
        muser2Image = _uri2;
    }

    public  ConversationItem(String _conversationId, String _userid1,  String _userId2, String _uri1,
                             String _uri2,  String _messageKeys) {
        conversationId = _conversationId;
        muserId1 = _userid1;
        muserId2 = _userId2;
        muser1Image = _uri1;
        muser2Image = _uri2;
        messageKeys.add(_messageKeys) ;

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

    public ArrayList<String> getMessages() {return messageKeys;}

    public void setMessages(ArrayList<String> messageKeys) {this.messageKeys = messageKeys;}
}
