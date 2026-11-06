package main.requests.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import main.App;
import main.item.Equippable;
import main.item.Item;

import java.io.IOException;

public class EquippedSerializer extends JsonSerializer<Boolean> {

    @Override
    public void serialize(Boolean equippable, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        Item item = (Item) jsonGenerator.currentValue();
        if (item instanceof Equippable equip) {
            jsonGenerator.writeObject(equip.isEquipped(App.INSTANCE.getPlayer()));
        } else {
            jsonGenerator.writeObject(false);
        }
    }
}
