import java.util.ArrayList;

public class RoomInit {
        static ArrayList<TrapRoom> trapRoomList = new ArrayList<>();
        static ArrayList<EnemyRoom> enemyRoomList = new ArrayList<>();
        static ArrayList<Room> normalRoomList = new ArrayList<>();
        static ArrayList<Room> roomList = new ArrayList<>();
    public static ArrayList<Room> roomInit() {
        ArrayList<TrapRoom> trapRooms = trapRoomInit();
        ArrayList<EnemyRoom> enemyRooms = enemyRoomInit();
        ArrayList<Room> normalRooms = normalRoomInit();
        roomList.addAll(normalRooms);
        roomList.addAll(trapRooms);
        roomList.addAll(enemyRooms);
        return roomList;
    }

    public static ArrayList<Room> normalRoomInit() {
        Room startRoom = new Room();
            startRoom.id = 0;
            startRoom.numExits = 1;
            startRoom.description = "You find yourself in an empty room, clearly a cave of sorts. You see " + startRoom.numExits + " exit" + (startRoom.numExits == 1 ? "" : "s") + ".";
            startRoom.appearance = "You see what appears to be a familiar room.";
            normalRoomList.add(startRoom);
        return normalRoomList;
    }

    public static ArrayList<TrapRoom> trapRoomInit() {
        TrapRoom stalactiteRoom = new TrapRoom();
        stalactiteRoom.id = 1;
        stalactiteRoom.description = "You walk into the room, and a dank smell hits you like a ton of bricks. Looking upwards, you notice some dangerously long stalactites hanging from the ceiling, water dripping down from them onto you. You take a step forward, and your foot hits a tripwire. You hear a loud *CRACK*, and a chunk of stalactite falls, hitting you in the shoulder.";
        stalactiteRoom.appearance = "You think you can small a faint mustiness, and water dripping. It's dark.";
        stalactiteRoom.damageDealt = 5;
        stalactiteRoom.numExits = 3;
        trapRoomList.add(stalactiteRoom);
        return trapRoomList;
    }
    public static ArrayList<EnemyRoom> enemyRoomInit() {

        ArrayList<MageEnemy> enemyList = EnemyInit.enemyInit();
        MageEnemy currentEnemy = enemyList.get(0);

        EnemyRoom goblinRoom = new EnemyRoom();
        goblinRoom.id = 2;
        goblinRoom.numExits = 4;
        goblinRoom.description = "You enter the ro- *A goblin attacks!*";
        goblinRoom.battleInitiationMessage = "The goblin pulls out a small wooden wand, casting sparks at you!";
        goblinRoom.appearance = "You can't see much, but you can hear some echoing chatter.";
        goblinRoom.enemies = new ArrayList<>();
        goblinRoom.enemies.add(currentEnemy);
        enemyRoomList.add(goblinRoom);
        return enemyRoomList;
    }
}
