package de.ace.tetst.servle.data;

import java.util.UUID;

/**
 * Created by stefan.marx on 24.07.17.
 */
public class ConnectMessage extends MyMessage {
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    private UUID _sessionUUID;

    public UUID getSessionUUID() {
        return _sessionUUID;
    }

    public void setSessionUUID(UUID sessionUUID) {
        _sessionUUID = sessionUUID;
    }

    private String msg;

    public ConnectMessage(UUID sessionUUID, String connected) {

        super(UUID.randomUUID());
        _sessionUUID = sessionUUID;

        msg = connected;
    }

    // used from Jackson
    public ConnectMessage() {}
}
