package bbb.websocketonboot.example.codec;

import bbb.websocketonboot.example.Example;
import com.google.gson.Gson;
import jakarta.websocket.Decoder.Text;
import jakarta.websocket.EndpointConfig;

import static java.util.Objects.nonNull;

public final class ExampleDecoder implements Text<Example> {

    private static final Gson GSON = new Gson();

    @Override
    public Example decode(String string) {
        return GSON.fromJson(string, Example.class);
    }

    @Override
    public boolean willDecode(String string) {
        return nonNull(string);
    }

    @Override
    public void init(EndpointConfig config) {
    }

    @Override
    public void destroy() {
    }
}
