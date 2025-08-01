package main.initialization;

import main.enemy.*;
import main.item.health.*;
import main.room.*;

import java.util.ArrayList;

public class RoomInit {
    public static ArrayList<Room> roomInit() {
        ArrayList<Room> roomList = new ArrayList<>();

        Room startRoom = new Room();
        startRoom.setId(0);
        startRoom.setNumExits(2);
        startRoom.setDescription("An empty room.");
        startRoom.setAppearance("For some reason, it invokes a sense of nostalgia.");
        startRoom.setSelectionWeight(5);
        roomList.add(startRoom);

        Room threeExits = new Room();
        threeExits.setId(6);
        threeExits.setNumExits(3);
        roomList.add(threeExits);

        Room fourExits = new Room();
        fourExits.setId(7);
        fourExits.setNumExits(4);
        roomList.add(fourExits);

        Room manyExits = new Room();
        manyExits.setId(8);
        manyExits.setNumExits(10);
        manyExits.setDescription("""
                Wow! this must be the heart of the system or something.
                There's so many different exits!""");
        manyExits.setSelectionWeight(5);
        roomList.add(manyExits);


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
        roomList.add(stalactiteRoom);

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
        roomList.add(pitRoom);


        ArrayList<Enemy> enemyList = EnemyInit.enemyInit();

        EnemyRoom goblinRoom = new EnemyRoom();
        goblinRoom.setId(2);
        goblinRoom.setNumExits(4);
        goblinRoom.setDescription("You enter the room... Waking up a goblin in a tattered cloak. It wearily blinks, before jumping up.");
        goblinRoom.setBattleInitiationMessage("The goblin pulls out a small wooden wand, ready to cast spells at you!");
        goblinRoom.setAppearance("You can't see much, but you can hear some echoing chatter.");
        goblinRoom.addEnemies(enemyList.getFirst());
        goblinRoom.setType(RoomType.ENEMY);
        roomList.add(goblinRoom);

        EnemyRoom ambushRoom = new EnemyRoom();
        ambushRoom.setId(11);
        ambushRoom.setNumExits(3);
        ambushRoom.setDescription("You enter the ro-");
        ambushRoom.setBattleInitiationMessage("Oh no! a goblin and orc were waiting for you, catching you by surprise!");
        ambushRoom.addEnemies(enemyList.get(1));
        ambushRoom.addEnemies(enemyList.get(2));
        ambushRoom.setType(RoomType.ENEMY);
        ambushRoom.setSelectionWeight(8);
        ambushRoom.setActive(false);
        ambushRoom.setRoomsRequired(10);
        roomList.add(ambushRoom);


        BossRoom slimeBossRoom = new BossRoom();
        slimeBossRoom.setRoomsRequired(20);
        slimeBossRoom.setNumExits(5);
        slimeBossRoom.setId(13);
        slimeBossRoom.setAppearance("An unfamiliar room with blue goop coating the entrance. It smells strongly of fruit.");
        slimeBossRoom.addEnemies(new SlimeBoss());
        slimeBossRoom.setDescription("You walk into the room, blue slime squishing under your feet. A large blue slime sits in the center of the room.");
        slimeBossRoom.setBattleInitiationMessage("The massive slime starts vibrating intensely, and then launches at you.");
        slimeBossRoom.setType(RoomType.BOSS);
        slimeBossRoom.setSelectionWeight(5);
        slimeBossRoom.setBackgroundFileName("slime_background.png");
        roomList.add(slimeBossRoom);

        BossRoom minotaurBossRoom = new BossRoom();
        minotaurBossRoom.setRoomsRequired(50);
        minotaurBossRoom.setNumExits(4);
        minotaurBossRoom.setId(100);
        minotaurBossRoom.addEnemies(new MinotaurBoss());
        minotaurBossRoom.setDescription("A massive minotaur stands silently in the center of the room.");
        minotaurBossRoom.setBattleInitiationMessage("Before you can do anything, it opens its blood red eyes, glaring at you.");
        minotaurBossRoom.setType(RoomType.BOSS);
        minotaurBossRoom.setSelectionWeight(4);
        roomList.add(minotaurBossRoom);


        ItemRoom appleRoom = new ItemRoom();
        appleRoom.setItem(new AppleItem());
        appleRoom.setId(3);
        appleRoom.setNumExits(3);
        appleRoom.setDescription("You enter the room. It's empty, except for a small apple on a pedestal.");
        appleRoom.setAppearance("You can't see much, but you can smell a faintly sweet scent coming from the doorway.");
        appleRoom.setType(RoomType.ITEM);
        appleRoom.setSelectionWeight(7);
        roomList.add(appleRoom);

        ItemRoom chocolateRoom = new ItemRoom();
        chocolateRoom.setItem(new ChocolateItem());
        chocolateRoom.setId(4);
        chocolateRoom.setNumExits(1);
        chocolateRoom.setDescription("""
                It couldn't be... Lost after all this time... But you found it, in a dank cave...
                The legendary Torpedo Chocolate Bar™!!!!""");
        chocolateRoom.setAppearance("There's a positively delightful aroma emanating from this passageway.");
        chocolateRoom.setType(RoomType.ITEM);
        chocolateRoom.setSelectionWeight(3);
        roomList.add(chocolateRoom);

        ItemRoom lavaRoom = new ItemRoom();
        lavaRoom.setId(1001);
        lavaRoom.setDescription("""
                You walk into the room. There's flowing lava coming from the ceiling and down the walls, pooling on the floor.
                Near one of the pools lies a freshly and perfectly cooked steak. Alright, then.""");
        lavaRoom.setAppearance("It smells like a barbeque. There's a soft orange glow emanating from the entrance.");
        lavaRoom.setType(RoomType.ITEM);
        lavaRoom.setSelectionWeight(5);
        lavaRoom.setItem(new SteakItem());
        lavaRoom.setNumExits(3);
        roomList.add(lavaRoom);

        RelicRoom relicRoom = new RelicRoom(false);
        //relic added later
        relicRoom.setId(9);
        relicRoom.setDescription("""
                You walk into a room, and are bewildered by the ornate furnishings in the room. Ornate walls, cushy furniture, the like!
                In the center of the room stands an equally ornate pedestal with a shining relic sitting on the top.""");
        relicRoom.setAppearance("This room emits fanciness like you've never known.");
        relicRoom.setType(RoomType.RELIC);
        relicRoom.setSelectionWeight(2);
        roomList.add(relicRoom);

        RelicRoom corpseRoom = new RelicRoom(true);
        //item added later
        corpseRoom.setId(10);
        corpseRoom.setDescription("You walk into an empty room... except for the skeleton in the corner.");
        corpseRoom.setAppearance("You think you catch a whiff of something... not good.");
        corpseRoom.setType(RoomType.RELIC);
        corpseRoom.setSelectionWeight(4);
        roomList.add(corpseRoom);

        ItemRoom randomRoom = new ItemRoom();
        //item added later
        randomRoom.setId(12);
        randomRoom.setDescription("You walk into a room. It's empty, except for something on the ground.");
        randomRoom.setNumExits(3);
        randomRoom.setType(RoomType.ITEM);
        randomRoom.setSelectionWeight(6);
        roomList.add(randomRoom);

        //there is a chance that someone might not want to leave, and be forced if this is the only exit.
        roomList.add(new EndingRoom());
        roomList.add(new PureWaterRoom());
        roomList.add(new ShopRoom());

        return roomList;
    }
}