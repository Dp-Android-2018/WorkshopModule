package com.findandfix.workshop.model.global.Conv;

import java.io.Serializable;

/**
 * Created by DELL on 09/05/2018.
 */

public class ConversationHistory implements Serializable{

    private LastMessage lastMessage;

    private ConversationsLocation conversations;




    public ConversationHistory(LastMessage lastMessage, ConversationsLocation conversations) {
        this.lastMessage = lastMessage;
        this.conversations=conversations;
    }


    public LastMessage getLastMessage() {
        return lastMessage;
    }
    public ConversationsLocation getConversations() {
        return conversations;
    }
}
