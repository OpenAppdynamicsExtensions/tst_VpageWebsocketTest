package de.ace.tetst.servle.data;

import com.fasterxml.jackson.core.JsonProcessingException;
import de.ace.tetst.servle.util.JSONUtil;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by stefan.marx on 24.07.17.
 */
public class MyMessage {
    private  UUID _messageUUID;
    private String _comment;


    public UUID getMessageUUID() {
        return _messageUUID;
    }

    public MyMessage( UUID messageUUID) {
        _messageUUID = messageUUID;
    }

    // used from jackson
    public  MyMessage() {

    }

    public String dump() {
        try {
            return JSONUtil.encodeObject(this,true);
        } catch (JsonProcessingException e) {
            return "Error happened: Object -->"+this;
        }
    }

    public void setComment(String comment) {
        _comment = comment;
    }

    public String getComment() {
        return _comment;
    }
}
