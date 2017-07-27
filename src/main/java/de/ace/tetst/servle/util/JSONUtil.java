package de.ace.tetst.servle.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.ace.tetst.servle.data.MyMessage;

import java.io.IOException;

/**
 * Created by stefan.marx on 24.07.17.
 */
public class JSONUtil {
    static ObjectMapper mapper = new ObjectMapper();

    public static String encodeObject(MyMessage msg) throws JsonProcessingException {

       return  mapper.writeValueAsString(msg);
    }



    public static <T extends Object> T decodeObject(String json, Class<T> type ) throws IOException {
        return mapper.readValue(json, type);
    }

    public static String encodeObject(MyMessage myMessage, boolean prettyPrint) throws JsonProcessingException {
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(myMessage);

    }
}
