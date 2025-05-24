package main.initialization;

import main.enemy.Boss;
import main.enemy.Enemy;
import main.item.Item;
import main.room.*;

import java.util.ArrayList;


public class RoomInit {
        static ArrayList<TrapRoom> trapRoomList = new ArrayList<>();
        static ArrayList<EnemyRoom> enemyRoomList = new ArrayList<>();
        static ArrayList<Room> normalRoomList = new ArrayList<>();
        static ArrayList<ItemRoom> itemRoomList = new ArrayList<>();
        static ArrayList<BossRoom> bossRoomList = new ArrayList<>();
        static ArrayList<Room> specialRoomList = new ArrayList<>();
        static ArrayList<Room> roomList = new ArrayList<>();
    public static ArrayList<Room> roomInit() {
        ArrayList<TrapRoom> trapRooms = trapRoomInit();
        ArrayList<EnemyRoom> enemyRooms = enemyRoomInit();
        ArrayList<Room> normalRooms = normalRoomInit();
        ArrayList<ItemRoom> itemRooms = itemRoomInit();
        ArrayList<BossRoom> bossRooms = bossRoomInit();
        ArrayList<Room> specialRooms = specialRoomInit();
        roomList.addAll(normalRooms);
        roomList.addAll(trapRooms);
        roomList.addAll(enemyRooms);
        roomList.addAll(itemRooms);
        roomList.addAll(bossRooms);
        roomList.addAll(specialRooms);
        return roomList;
    }

    public static ArrayList<Room> normalRoomInit() {
            Room startRoom = new Room();
            startRoom.setId(0);
            startRoom.setNumExits(2);
            startRoom.setDescription("An empty room.");
            startRoom.setAppearance("For some reason, it invokes a sense of nostalgia.");
            startRoom.setSelectionWeight(5);
            normalRoomList.add(startRoom);

            Room threeExits = new Room();
            threeExits.setId(6);
            threeExits.setNumExits(3);
            normalRoomList.add(threeExits);

            Room fourExits = new Room();
            fourExits.setId(7);
            fourExits.setNumExits(4);
            normalRoomList.add(fourExits);

            Room manyExits = new Room();
            manyExits.setId(8);
            manyExits.setNumExits(10);
            manyExits.setDescription("""
                    Wow! this must be the heart of the system or something.
                    There's so many different exits!""");
            manyExits.setSelectionWeight(5);
            normalRoomList.add(manyExits);

        return normalRoomList;
    }

    public static ArrayList<TrapRoom> trapRoomInit() {

            TrapRoom stalactiteRoom = new TrapRoom();
            stalactiteRoom.setId(1);
            stalactiteRoom.setNumExits(3);
            stalactiteRoom.setDescription("""
                    You walk into the room, and a dank smell hits you like a ton of bricks.
                    Looking upwards, you notice some dangerously long stalactites hanging from the ceiling, water dripping down from them onto you.
                    You take a step forward, and your foot hits a tripwire.
                    You hear a loud *CRACK*, and a chunk of stalactite falls, hitting you in the shoulder.""");
            stalactiteRoom.setAppearance("You think you can smell a faint mustiness, and water dripping. It's dark.");
            stalactiteRoom.setDamageDealt(5);
            stalactiteRoom.setType(RoomType.TRAP);
            stalactiteRoom.setSelectionWeight(8);
            trapRoomList.add(stalactiteRoom);

            TrapRoom pitRoom = new TrapRoom();
            pitRoom.setId(5);
            pitRoom.setDescription("""
                    You slowly walk into to a room. The ground creaks beneath your feet.
                    Suddenly, the flooring cracks and shatters, tumbling you down onto a pit of spikes.
                    Luckily, you land between the spikes, suffering only minor cuts and bruises.
                    You're not sure how to get back up, but you notice a single passageway at the bottom of the pit.""");
            pitRoom.setDamageDealt(3);
            pitRoom.setNumExits(1);
            pitRoom.setType(RoomType.TRAP);
            trapRoomList.add(pitRoom);

        return trapRoomList;
    }

    public static ArrayList<EnemyRoom> enemyRoomInit() {

        ArrayList<Enemy> enemyList = EnemyInit.enemyInit();

            EnemyRoom goblinRoom = new EnemyRoom();
            goblinRoom.setId(2);
            goblinRoom.setNumExits(4);
            goblinRoom.setDescription("You enter the room... Waking up a goblin in a tattered cloak. It wearily blinks, before jumping up.");
            goblinRoom.setBattleInitiationMessage("The goblin pulls out a small wooden wand, ready to cast spells at you!");
            goblinRoom.setAppearance("You can't see much, but you can hear some echoing chatter.");
            goblinRoom.addEnemies(enemyList.getFirst());
            goblinRoom.setType(RoomType.ENEMY);
            enemyRoomList.add(goblinRoom);

            EnemyRoom ambushRoom = new EnemyRoom();
            ambushRoom.setId(11);
            ambushRoom.setNumExits(3);
            ambushRoom.setDescription("You enter the ro-");
            ambushRoom.setBattleInitiationMessage("Oh no! a goblin and orc were waiting for you, catching you by surprise!");
            ambushRoom.addEnemies(enemyList.get(1));
            ambushRoom.addEnemies(enemyList.get(2));
            ambushRoom.setType(RoomType.ENEMY);
            enemyRoomList.add(ambushRoom);
            ambushRoom.setSelectionWeight(9);

        return enemyRoomList;
    }

    public static ArrayList<BossRoom> bossRoomInit() {
        ArrayList<Boss> bossList = EnemyInit.bossInit();

            BossRoom slimeBossRoom = new BossRoom();
            slimeBossRoom.setRoomsRequired(20);
            slimeBossRoom.setNumExits(5);
            slimeBossRoom.setId(13);
            slimeBossRoom.setAppearance("An unfamiliar room with blue goop coating the entrance. It smells strongly of fruit.");
            slimeBossRoom.addEnemies(bossList.getFirst());
            slimeBossRoom.setDescription("You walk into the room, blue slime squishing under your feet. A large blue slime sits in the center of the room.");
            slimeBossRoom.setBattleInitiationMessage("The massive slime starts vibrating intensely, and then launches at you.");
            slimeBossRoom.setType(RoomType.BOSS);
            slimeBossRoom.setSelectionWeight(5);
            bossRoomList.add(slimeBossRoom);

        return bossRoomList;
    }

    public static ArrayList<ItemRoom> itemRoomInit() {

        ArrayList<Item> itemList = ItemInit.itemInit();

            ItemRoom appleRoom = new ItemRoom();
            appleRoom.setItem(itemList.get(indexFinder(itemList, "Apple")));
            appleRoom.setId(3);
            appleRoom.setNumExits(3);
            appleRoom.setDescription("You enter the room. It's empty, except for a small apple on a pedestal.");
            appleRoom.setAppearance("You can't see much, but you can smell a faintly sweet scent coming from the doorway.");
            appleRoom.setType(RoomType.ITEM);
            appleRoom.setSelectionWeight(8);
            itemRoomList.add(appleRoom);

            ItemRoom chocolateRoom = new ItemRoom();
            chocolateRoom.setItem(itemList.get(indexFinder(itemList, "Torpedo Chocolate Bar™")));
            chocolateRoom.setId(4);
            chocolateRoom.setNumExits(1);
            chocolateRoom.setDescription("""
                    It couldn't be... Lost after all this time... But you found it, in a dank cave...
                    The legendary Torpedo Chocolate Bar™!!!!""");
            chocolateRoom.setAppearance("There's a positively delightful aroma emanating from this passageway.");
            chocolateRoom.setType(RoomType.ITEM);
            chocolateRoom.setSelectionWeight(3);
            itemRoomList.add(chocolateRoom);

            ItemRoom relicRoom = new ItemRoom();
            //item added later
            relicRoom.setId(9);
            relicRoom.setDescription("""
                    You walk into a room, and are bewildered by the ornate furnishings in the room. Ornate walls, cushy furniture, the like!
                    In the center of the room stands an equally ornate pedestal with a shining relic sitting on the top.""");
            relicRoom.setAppearance("This room emits fanciness like you've never known.");
            relicRoom.setType(RoomType.RELIC);
            relicRoom.setSelectionWeight(2);
            itemRoomList.add(relicRoom);

            ItemRoom corpseRoom = new ItemRoom();
            //item added later
            corpseRoom.setId(10);
            corpseRoom.setDescription("You walk into an empty room... except for the skeleton in the corner.");
            corpseRoom.setAppearance("You think you catch a whiff of something... not good.");
            corpseRoom.setType(RoomType.RELIC);
            corpseRoom.setSelectionWeight(4);
            itemRoomList.add(corpseRoom);

            ItemRoom randomRoom = new ItemRoom();
            //item added later
            randomRoom.setId(12);
            randomRoom.setDescription("You walk into a room. It's empty, except for something on the ground.");
            randomRoom.setNumExits(3);
            randomRoom.setType(RoomType.ITEM);
            randomRoom.setSelectionWeight(6);
            itemRoomList.add(randomRoom);


        return itemRoomList;

    }

    public static ArrayList<Room> specialRoomInit() {

            //there is a chance that someone might not want to leave, and be forced if this is the only exit.
            Room endRoom = new EndingRoom();
            endRoom.setId(9001);
            endRoom.setNumExits(1);
            endRoom.setDescription("""
            At last, your journey is over. A simple room, with just an old wooden staircase upwards.
            You can smell fresh air for the first time in a while. You don't hesitate to rush up the stairs, and find yourself
            in an old wine cellar. You head up further, finding yourself in a rustic bar.
            Before anyone has a chance to say anything, you dash out the door, raising your arms, feeling the sun on your shoulders.
            Freedom.""");
            endRoom.setAppearance("You get the sense your journey is finally over.");
            endRoom.setRoomsRequired(100);
            endRoom.setActive(false);
            endRoom.setSelectionWeight(1);
            endRoom.setType(RoomType.SPECIAL);
            specialRoomList.add(endRoom);

            Room waterRoom = new PureWaterRoom();
            waterRoom.setId(9002);
            waterRoom.setNumExits(4);
            waterRoom.setDescription("You walk into the room, and see a fountain with flowing water. The water is almost luminescent.");
            waterRoom.setAppearance("You can hear rushing water, but that's about it.");
            waterRoom.setSelectionWeight(2);
            waterRoom.setType(RoomType.SPECIAL);
            specialRoomList.add(waterRoom);

        return specialRoomList;
    }

    public static int indexFinder(ArrayList<Item> list, String targetName) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getName().equals(targetName)) {
                return i;
            }
        }
        return -1;
    }
}
