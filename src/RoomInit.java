import java.util.ArrayList;

public class RoomInit {
        static ArrayList<TrapRoom> trapRoomList = new ArrayList<>();
        static ArrayList<EnemyRoom> enemyRoomList = new ArrayList<>();
    public static ArrayList<TrapRoom> trapRoomInit() {
        TrapRoom stalactiteRoom = new TrapRoom();
        stalactiteRoom.id = 1;
        stalactiteRoom.description = "You walk into the room, and a dank smell hits you like a ton of bricks. Looking upwards, you notice some dangerously long stalactites hanging from the ceiling, water dripping down from them onto you. You take a step forward, and your foot hits a tripwire. You hear a loud *CRACK*, and a chunk of stalactite falls, hitting you in the shoulder.";
        stalactiteRoom.appearance = "You think you can small a faint mustiness, and water dripping. It's dark.";
        stalactiteRoom.damageDealt = 5;
        trapRoomList.add(stalactiteRoom);
        return trapRoomList;
    }
    public static ArrayList<EnemyRoom> enemyRoomInit() {

        ArrayList<MageEnemy> enemyList = EnemyInit.enemyInit();
        MageEnemy currentEnemy = enemyList.get(0);

        EnemyRoom goblinRoom = new EnemyRoom();
        goblinRoom.id = 2;
        goblinRoom.description = "You enter the ro- *A goblin attacks!*";
        goblinRoom.battleInitiationMessage = "The goblin pulls out a small wooden wand, casting sparks at you!";
        goblinRoom.appearance = "You can't see much, but you can hear some echoing chatter.";
        goblinRoom.enemies = new ArrayList<>();
        goblinRoom.enemies.add(currentEnemy);
        enemyRoomList.add(goblinRoom);
        return enemyRoomList;
    }
}
