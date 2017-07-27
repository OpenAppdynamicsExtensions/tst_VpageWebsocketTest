package de.ace.tetst.servle.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import de.ace.tetst.servle.data.ConnectMessage;
import de.ace.tetst.servle.data.MyMessage;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by stefan.marx on 24.07.17.
 */
public class Test {
    public static void main(String[] args) {
        String JSON = null;
        try {
            JSON = JSONUtil.encodeObject(new ConnectMessage(UUID.randomUUID(),"connected"));
            System.out.println("Connect :\n"+JSON);

            MyMessage message =  JSONUtil.decodeObject(JSON,ConnectMessage.class);
            System.out.println("Connect :\n"+message.dump());


        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
