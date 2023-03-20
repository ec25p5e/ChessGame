package core.pieces.piece;

import com.google.gson.*;

import java.lang.reflect.Type;

public class PieceDeserializer implements JsonDeserializer<Piece> {

    @Override
    public Piece deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String type = jsonObject.get("type").getAsString();
        JsonElement element = jsonObject.get("properties");

        try {
            return context.deserialize(element, Class.forName(type));
        } catch (ClassNotFoundException e) {
            throw new JsonParseException("Unknown element type: " + type, e);
        }
    }
}
