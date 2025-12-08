package main.requests.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import main.room.Room;

import java.io.IOException;
import java.util.ArrayList;

public class ExitSerializer extends JsonSerializer<ArrayList<Room>> {

    @Override
    public void serialize(ArrayList<Room> rooms, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        ArrayList<Integer> roomIds = new ArrayList<>();
        for (Room room : rooms) {
            roomIds.add(room.getId());
        }
        jsonGenerator.writeObject(roomIds);
    }
}
