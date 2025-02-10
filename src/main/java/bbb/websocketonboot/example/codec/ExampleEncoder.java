package bbb.websocketonboot.example.codec;

import bbb.websocketonboot.example.Example;
import com.google.gson.Gson;
import jakarta.websocket.Encoder.Text;
import jakarta.websocket.EndpointConfig;

public final class ExampleEncoder implements Text<Example> {

    private static final Gson GSON = new Gson();

    @Override
    public String encode(Example message) {
        return GSON.toJson(message);
    }

    @Override
    public void init(EndpointConfig config) {
    }

    @Override
    public void destroy() {
    }
}
