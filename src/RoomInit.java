import java.util.ArrayList;
import java.util.Random;


public class RoomInit {
        static ArrayList<TrapRoom> trapRoomList = new ArrayList<>();
        static ArrayList<EnemyRoom> enemyRoomList = new ArrayList<>();
        static ArrayList<Room> normalRoomList = new ArrayList<>();
        static ArrayList<ItemRoom> itemRoomList = new ArrayList<>();
        static ArrayList<Room> roomList = new ArrayList<>();
    public static ArrayList<Room> roomInit() {
        ArrayList<TrapRoom> trapRooms = trapRoomInit();
        ArrayList<EnemyRoom> enemyRooms = enemyRoomInit();
        ArrayList<Room> normalRooms = normalRoomInit();
        ArrayList<ItemRoom> itemRooms = itemRoomInit();
        roomList.addAll(normalRooms);
        roomList.addAll(trapRooms);
        roomList.addAll(enemyRooms);
        roomList.addAll(itemRooms);
        return roomList;
    }

    public static ArrayList<Room> normalRoomInit() {
            Room startRoom = new Room();
            startRoom.id = 0;
            startRoom.numExits = 2;
            startRoom.description = "You find yourself in an empty room, clearly a cave of sorts.";
            startRoom.appearance = "You see what appears to be a familiar room.";
            normalRoomList.add(startRoom);

            Room threeExits = new Room();
            threeExits.id = 6;
            threeExits.numExits = 3;
            normalRoomList.add(threeExits);

            Room fourExits = new Room();
            fourExits.id = 7;
            fourExits.numExits = 4;
            normalRoomList.add(fourExits);

            Room manyExits = new Room();
            manyExits.id = 8;
            manyExits.numExits = 10;
            manyExits.description = "Wow! this must be the heart of the system or something.\nThere's so many different exits!";
            normalRoomList.add(manyExits);

        return normalRoomList;
    }

    public static ArrayList<TrapRoom> trapRoomInit() {

            TrapRoom stalactiteRoom = new TrapRoom();
            stalactiteRoom.id = 1;
            stalactiteRoom.description = "You walk into the room, and a dank smell hits you like a ton of bricks.\nLooking upwards, you notice some dangerously long stalactites hanging from the ceiling, water dripping down from them onto you.\nYou take a step forward, and your foot hits a tripwire.\nYou hear a loud *CRACK*, and a chunk of stalactite falls, hitting you in the shoulder.";
            stalactiteRoom.appearance = "You think you can smell a faint mustiness, and water dripping. It's dark.";
            stalactiteRoom.damageDealt = 5;
            stalactiteRoom.numExits = 3;
            trapRoomList.add(stalactiteRoom);

            TrapRoom pitRoom = new TrapRoom();
            pitRoom.id = 5;
            pitRoom.description = "You slowly walk into to a room. The ground creaks beneath your feet.\nSuddenly, the flooring cracks and shatters, tumbling you down onto a pit of spikes.\nLuckily, you land between the spikes, suffering only minor cuts and bruises.\nYou're not sure how to get back up, but you notice a single passageway at the bottom of the pit.";
            pitRoom.damageDealt = 3;
            pitRoom.numExits = 1;
            trapRoomList.add(pitRoom);

        return trapRoomList;
    }
    public static ArrayList<EnemyRoom> enemyRoomInit() {

        ArrayList<MageEnemy> enemyList = EnemyInit.enemyInit();
        MageEnemy currentEnemy = enemyList.getFirst();

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

    public static ArrayList<ItemRoom> itemRoomInit() {

        ArrayList<Item> itemList = ItemInit.itemInit();
        ArrayList<Relic> relicList = RelicInit.relicInit();

            ItemRoom appleRoom = new ItemRoom();
            appleRoom.item = itemList.get(indexFinder(itemList, "Apple"));
            appleRoom.id = 3;
            appleRoom.numExits = 3;
            appleRoom.description = "You enter the room. It's empty, except for a small apple on a pedestal.";
            appleRoom.appearance = "You can't see much, but you can smell a faintly sweet scent coming from the doorway.";
            itemRoomList.add(appleRoom);

            ItemRoom chocolateRoom = new ItemRoom();
            chocolateRoom.item = itemList.get(indexFinder(itemList, "Torpedo Chocolate Bar™"));
            chocolateRoom.id = 4;
            chocolateRoom.numExits = 1;
            chocolateRoom.description = "It couldn't be... Lost after all this time... But you found it, in a dank cave...\nThe legendary " + chocolateRoom.item.name + "!!!!";
            chocolateRoom.appearance = "There's a positively delightful aroma emanating from this passageway.";
            itemRoomList.add(chocolateRoom);

            ItemRoom relicRoom = new ItemRoom();
            //this might not work! Additionally, it shouldn't be possible to get the same relic twice.
            relicRoom.item = relicList.get(new Random().nextInt(relicList.size()));
            relicRoom.id = 9;
            relicRoom.description = "You walk into a room, and are bewildered by the ornate furnishings in the room. " + "Ornate walls, cushy furniture, the like!\n" +
                    "In the center of the room stands an equally ornate pedestal with a shining " + relicRoom.item.name + " sitting on the top.";
            relicRoom.appearance = "You get bougie vibes from this one.";
            itemRoomList.add(relicRoom);

        return itemRoomList;

    }
    public static int indexFinder(ArrayList<Item> list, String targetName) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).name.equals(targetName)) {
                return i;
            }
        }
        return -1;
    }
}
