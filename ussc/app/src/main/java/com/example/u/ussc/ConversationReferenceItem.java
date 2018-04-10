/**
 * Created by Lundy on 4/10/2018.
 */

package com.example.u.ussc;

import java.util.ArrayList;

public class ConversationReferenceItem {
    private String conversationId;
    private String muserId1;
    private String muser1Image;
    private String lastMessage;
    private ArrayList<String> messageKeys;

    public ConversationReferenceItem () { messageKeys = new ArrayList<String>(0); }

    public ConversationReferenceItem (String _conversationId, String _muserId1, String _muser1Image,
                                       String _lastMessage, ArrayList<String> _messageKeys) {
        messageKeys = new ArrayList<String>(0);
        conversationId = _conversationId;
        muserId1 = _muserId1;
        muser1Image = _muser1Image;
        lastMessage = _lastMessage;
        messageKeys = _messageKeys;
    }


    public String getConversationId() { return conversationId; }

    public void setConversationId(String conversationId) { this.conversationId = conversationId; }

    public String getMuserId1() { return muserId1; }

    public void setMuserId1(String muserId1) { this.muserId1 = muserId1; }

    public String getMuser1Image() { return muser1Image; }

    public void setMuser1Image(String muser1Image) { this.muser1Image = muser1Image; }

    public String getLastMessage() { return lastMessage; }

    public void setLastMessage(String lastMessage) { this.lastMessage = lastMessage; }

    public ArrayList<String> getMessageKeys() { return messageKeys; }

    public void setMessageKeys(ArrayList<String> messageKeys) { this.messageKeys = messageKeys; }
}
