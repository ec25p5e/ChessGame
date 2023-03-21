package core.pieces.piece;

import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * Questa classe viene utilizzata per deserializzare tutte le pedine
 * scritte in un file JSON
 */
public class PieceDeserializer implements JsonDeserializer<Piece> {

    /**
     * Questo metodo serve per deserializzare i file d'input delle pedine
     * @param json The Json data being deserialized
     * @param typeOfT The type of the Object to deserialize to
     * @param context contesto della deserializzazione
     * @return la pedina dell'oggetto letto
     * @throws JsonParseException eccezione che viene chiamata in caso ci sia un'errore
     * di formattazione del JSON di entrata
     */
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
