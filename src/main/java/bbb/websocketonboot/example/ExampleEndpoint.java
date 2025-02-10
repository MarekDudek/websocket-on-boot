package bbb.websocketonboot.example;

import bbb.websocketonboot.example.codec.ExampleDecoder;
import bbb.websocketonboot.example.codec.ExampleEncoder;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static java.lang.System.out;

@ServerEndpoint(value = "/example/{param}", decoders = ExampleDecoder.class, encoders = ExampleEncoder.class)
@Component
public class ExampleEndpoint {

    @OnOpen
    public void onOpen(Session session, @PathParam("param") String param) {
        out.println("onOpen");
        var example = new Example("example");
        try {
            var remote = session.getBasicRemote();
            remote.sendObject(example);
        } catch (IOException | EncodeException e) {
            throw new RuntimeException(e);
        }
    }

    @OnClose
    public void onClose(Session session) {
        out.println("onClose");
    }

    @OnMessage
    public void onMessage(Session session, String message) {
        out.println("onMessage");
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        out.println("onError");
    }
}
