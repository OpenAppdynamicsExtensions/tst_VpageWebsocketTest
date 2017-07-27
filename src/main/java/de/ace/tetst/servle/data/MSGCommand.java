package de.ace.tetst.servle.data;

import java.util.List;

/**
 * Created by stefan.marx on 25.07.17.
 */
public class MSGCommand extends MyMessage {
    private List<String> _adrumData;

    public MSGCommand() {}

    String cmdId,payload,cmd;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    String comment;

    public String getAdrumScript() {
        return adrumScript;
    }

    public void setAdrumScript(String adrumScript) {
        this.adrumScript = adrumScript;
    }

    String adrumScript;

    public String getCmdId() {
        return cmdId;
    }

    public void setCmdId(String cmdId) {
        this.cmdId = cmdId;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public String getCmd() {
        return (cmd != null)?cmd.toLowerCase():"__unknown";
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public void setAdrumData(List<String> adrumData) {
        _adrumData = adrumData;
    }

    public List<String> getAdrumData() {
        return _adrumData;
    }
}
