/**
 * Created by Lundy on 4/10/2018.
 */

package com.example.u.ussc;

import java.util.ArrayList;

public class ConversationReferenceItem {
    private String conversationId;
    private String muserName;
    private String muserImage;
    private String lastMessage;
    private String lastMessageDate;
    private ArrayList<String> messageKeys;

    public ConversationReferenceItem () { messageKeys = new ArrayList<String>(0); }

    public ConversationReferenceItem (String _conversationId, String _muserName, String _muserImage,
                                       String _lastMessage, String _lastMessageDate, ArrayList<String> _messageKeys) {
        messageKeys = new ArrayList<String>(0);
        conversationId = _conversationId;
        muserName = _muserName;
        muserImage = _muserImage;
        lastMessage = _lastMessage;
        lastMessageDate = _lastMessageDate;
        messageKeys = _messageKeys;
    }

    public void setConversationId(String conversationId) { this.conversationId = conversationId; }

    public void setMuserName(String muserName) { this.muserName = muserName; }

    public void setMuserImage(String muserImage) { this.muserImage = muserImage; }

    public void setLastMessage(String lastMessage) { this.lastMessage = lastMessage; }

    public void setLastMessageDate(String lastMessageDate) { this.lastMessageDate = lastMessageDate; }

    public void setMessageKeys(ArrayList<String> messageKeys) { this.messageKeys = messageKeys; }

    public String getConversationId() { return conversationId; }

    public String getMuserName() { return muserName; }

    public String getMuserImage() { return muserImage; }

    public String getLastMessage() { return lastMessage; }

    public ArrayList<String> getMessageKeys() { return messageKeys; }

    public String getLastMessageDate() { return lastMessageDate; }
}
