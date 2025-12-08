package main.requests.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import main.App;
import main.item.relic.RelicID;
import main.room.Room;

import java.io.IOException;
import java.util.Random;

public class AppearanceSerializer extends JsonSerializer<String> {

    @Override
    public void serialize(String appearance, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        Room room = (Room) jsonGenerator.getCurrentValue();
        double chance = room.getAppearanceChance();
        //alternatively, add a relic just called "Glasses" that does the same thing (but better prob)
        if (App.INSTANCE.getPlayer().hasRelicEquipped(RelicID.FORESIGHT)) {
            chance *= 1.3;
        }
        double randValue = new Random().nextDouble();
        if (chance > randValue) {
            jsonGenerator.writeObject(appearance);
        } else {
            jsonGenerator.writeObject(Room.getRandomAppearance());
        }
    }
}
