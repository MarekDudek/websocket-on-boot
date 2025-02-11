package bbb.websocketonboot.example;

import bbb.websocketonboot.example.codec.ExampleDecoder;
import bbb.websocketonboot.example.codec.ExampleEncoder;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import static java.lang.System.out;

@Component
@ServerEndpoint(value = "/example/{param}", decoders = ExampleDecoder.class, encoders = ExampleEncoder.class)
@Slf4j
public class ExampleEndpoint {

    private static final Set<ExampleEndpoint> ENDPOINTS = new CopyOnWriteArraySet<>();
    private Session session;

    @OnOpen
    public void onOpen(Session session, @PathParam("param") String param) {
        out.println("open " + this);
        this.session = session;
        ENDPOINTS.add(this);
        broadcast(new Example("open"));
    }

    @OnClose
    public void onClose(Session session) {
        out.println("close " + this);
        broadcast(new Example("close"));
    }

    @OnMessage
    public void onMessage(Session session, String message) {
        out.println("message " + this);
        broadcast(new Example("message"));
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        out.println("error " + this);
        broadcast(new Example("error"));
    }

    private static void broadcast(@NonNull Example example) {
        ENDPOINTS.forEach(
                endpoint -> {
                    var remote = endpoint.session.getBasicRemote();
                    try {
                        remote.sendObject(example);
                    } catch (IOException | EncodeException e) {
                        throw new RuntimeException(e);
                    }
                }
        );
    }
}
