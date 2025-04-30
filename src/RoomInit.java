public class RoomInit {
    public static TrapRoom trapRoomInit() {
        TrapRoom stalactiteRoom = new TrapRoom();
        stalactiteRoom.id = 1;
        stalactiteRoom.description = "You walk into the room, and a dank smell hits you like a ton of bricks. Looking upwards, you notice some dangerously long stalactites hanging from the ceiling, water dripping down from them onto you. You take a step forward, and your foot hits a tripwire. You hear a loud *CRACK*, and a chunk of stalactite falls, hitting you in the shoulder.";
        stalactiteRoom.appearance = "You think you can small a faint mustiness, and water dripping. It's dark.";
        stalactiteRoom.damageDealt = 5;
        return stalactiteRoom;
    }
}
