package main.initialization;

import main.entity.enemy.*;
import main.item.health.*;
import main.room.*;

import java.util.ArrayList;

public class RoomInit {
    public static ArrayList<Room> roomInit() {
        ArrayList<Room> roomList = new ArrayList<>();

        Room startRoom = new RoomBuilder<>()
                .id(0)
                .description("""
        The room is empty except for a hole in the ceiling.
        Sunlight pours through, vines creeping down the cracked bricks.
        They're out of reach.""")
                .appearance("For some reason, it invokes a sense of nostalgia.")
                .selectionWeight(5)
                .build();
        roomList.add(startRoom);

        Room threeExits = new RoomBuilder<>()
                .id(6)
                .numExits(3)
                .build();
        roomList.add(threeExits);

        Room fourExits = new RoomBuilder<>()
                .id(7)
                .numExits(4)
                .build();
        roomList.add(fourExits);

        Room manyExits = new RoomBuilder<>()
                .id(8)
                .numExits(10)
                .description("""
                Wow! this must be the heart of the system or something.
                There's so many different exits!""")
                .selectionWeight(5)
                .build();
        roomList.add(manyExits);

        TrapRoom stalactiteRoom = new TrapRoomBuilder<>()
                .id(1)
                .numExits(3)
                .description("""
                You walk into the room, and a dank smell hits you like a ton of bricks.
                Looking upwards, you notice some dangerously long stalactites hanging from the ceiling, water dripping down from them onto you.
                You take a step forward, and your foot hits a tripwire.
                You hear a loud *CRACK*, and a chunk of stalactite falls, hitting you in the shoulder.""")
                .appearance("You think you can smell a faint mustiness, and water dripping. It's dark.")
                .damageDealt(3)
                .type(RoomType.TRAP)
                .selectionWeight(8)
                .build();

        roomList.add(stalactiteRoom);

        TrapRoom pitRoom = new TrapRoomBuilder<>()
                .id(5)
                .description("""
                You slowly walk into to a room. The ground creaks beneath your feet.
                Suddenly, the flooring cracks and shatters, tumbling you down onto a pit of spikes.
                Luckily, you land between the spikes, suffering only minor cuts and bruises.
                You're not sure how to get back up, but you notice a single passageway at the bottom of the pit.""")
                .numExits(1)
                .damageDealt(3)
                .type(RoomType.TRAP)
                .build();

        roomList.add(pitRoom);


        ArrayList<Enemy> enemyList = EnemyInit.enemyInit();

        //todo: fix this
        ArrayList<Enemy> goblinRoomEnemies = new ArrayList<>();
        goblinRoomEnemies.add(enemyList.getFirst());

        EnemyRoom goblinRoom = new EnemyRoomBuilder<>()
                .id(2)
                .numExits(4)
                .description("You enter the room... Waking up a goblin in a tattered cloak. It wearily blinks, before jumping up.")
                .battleInitiationMessage("The goblin pulls out a small wooden wand, ready to cast spells at you!")
                .appearance("You can't see much, but you can hear some echoing chatter.")
                .allowedEnemies(goblinRoomEnemies)
                .maxEnemies(1)
                .type(RoomType.ENEMY)
                .build();

        roomList.add(goblinRoom);

        ArrayList<Enemy> ambushRoomEnemies = new ArrayList<>();
        ambushRoomEnemies.add(enemyList.get(1));
        ambushRoomEnemies.add(enemyList.get(2));

        EnemyRoom ambushRoom = new EnemyRoomBuilder<>()
                .id(11)
                .numExits(3)
                .description("You enter the ro-")
                .battleInitiationMessage("Oh no! a camp of enemies were waiting for you, catching you by surprise!")
                .allowedEnemies(ambushRoomEnemies)
                .maxEnemies(3)
                .type(RoomType.ENEMY)
                .selectionWeight(8)
                .roomsRequired(10)
                .build();

        roomList.add(ambushRoom);

        ArrayList <Enemy> infirmaryEnemies = new ArrayList<>();
        infirmaryEnemies.add(enemyList.get(3));
        infirmaryEnemies.add(enemyList.get(1));

        EnemyRoom infirmary = new EnemyRoomBuilder<>()
                .id(10001)
                .numExits(4)
                .description("You walk into a room, full of cheap beds and medical equipment. A small goblin sees you enter, and slowly climbs out of bed.")
                .battleInitiationMessage("The goblin coughs, and pulls out a short sword, ready to attack!")
                .allowedEnemies(infirmaryEnemies)
                .maxEnemies(3)
                .type(RoomType.ENEMY)
                .selectionWeight(3)
                .roomsRequired(15)
                .build();

        roomList.add(infirmary);

        BossRoom slimeBossRoom = new EnemyRoomBuilder<>()
                .roomsRequired(20)
                .numExits(5)
                .id(13)
                .appearance("An unfamiliar room with blue goop coating the entrance. It smells strongly of fruit.")
                .allowedEnemies(new SlimeBoss())
                .maxEnemies(1)
                .description("You walk into the room, blue slime squishing under your feet. A large blue slime sits in the center of the room.")
                .battleInitiationMessage("The massive slime starts vibrating intensely, and then launches at you.")
                .type(RoomType.BOSS)
                .selectionWeight(5)
                .backgroundFileName("slime_background.png")
                .buildBoss();

        roomList.add(slimeBossRoom);

        BossRoom minotaurBossRoom = new EnemyRoomBuilder<>()
                .roomsRequired(50)
                .numExits(4)
                .id(100)
                .allowedEnemies(new MinotaurBoss())
                .maxEnemies(1)
                .description("A massive minotaur stands silently in the center of the room.")
                .battleInitiationMessage("Before you can do anything, it opens its blood red eyes, glaring at you.")
                .type(RoomType.BOSS)
                .selectionWeight(4)
                .backgroundFileName("minotaur_background.png")
                .buildBoss();

        roomList.add(minotaurBossRoom);

        BossRoom dragonBossRoom = new EnemyRoomBuilder<>()
                .roomsRequired(100)
                .numExits(2)
                .id(10012)
                .allowedEnemies(new DragonBoss())
                .maxEnemies(1)
                .description("You saunter into a room, gold coins flowing around your feet. You look up, and see a massive dragon towering over you, fire lazily exiting its mouth. You feel a little less confident.")
                .appearance("The air looks hazy near the entrance. You can feel the heat from here.")
                .type(RoomType.BOSS)
                .selectionWeight(3)
                .buildBoss();

        roomList.add(dragonBossRoom);

        ItemRoom appleRoom = new ItemRoomBuilder<>()
                .id(3)
                .numExits(3)
                .description("You enter the room. It's empty, except for a small apple on a pedestal.")
                .appearance("You can't see much, but you can smell a faintly sweet scent coming from the doorway.")
                .type(RoomType.ITEM)
                .selectionWeight(7)
                .item(new AppleItem())
                .build();

        roomList.add(appleRoom);

        ItemRoom chocolateRoom = new ItemRoomBuilder<>()
                .item(new ChocolateItem())
                .id(4)
                .numExits(1)
                .description("""
                It couldn't be... Lost after all this time... But you found it, in a dank cave...
                The legendary Torpedo Chocolate Bar™!!!!""")
                .appearance("There's a positively delightful aroma emanating from this passageway.")
                .type(RoomType.ITEM)
                .selectionWeight(3)
                .build();

        roomList.add(chocolateRoom);

        ItemRoom lavaRoom = new ItemRoomBuilder<>()
                .id(1001)
                .description("""
                You walk into the room. There's flowing lava coming from the ceiling and down the walls, pooling on the floor.
                Near one of the pools lies a freshly and perfectly cooked steak. Alright, then.""")
                .appearance("It smells like a barbeque. There's a soft orange glow emanating from the entrance.")
                .type(RoomType.ITEM)
                .selectionWeight(5)
                .item(new SteakItem())
                .numExits(3)
                .build();

        roomList.add(lavaRoom);

        RelicRoom relicRoom = new RelicRoomBuilder<>()
                .hasCorpse(false)
                .id(9)
                .description("""
                You walk into a room, and are bewildered by the ornate furnishings in the room. Ornate walls, cushy furniture, the like!
                In the center of the room stands an equally ornate pedestal with a shining relic sitting on the top.""")
                .appearance("This room emits fanciness like you've never known.")
                .type(RoomType.RELIC)
                .selectionWeight(2)
                .build();

        //relic added later (TODO change that?)
        roomList.add(relicRoom);

        RelicRoom corpseRoom = new RelicRoomBuilder<>()
                .hasCorpse(true)
                .id(10)
                .description("You walk into an empty room... except for the skeleton in the corner.")
                .appearance("You think you catch a whiff of something... not good.")
                .type(RoomType.RELIC)
                .selectionWeight(4)
                .build();

        //item added later (TODO change that?)
        roomList.add(corpseRoom);

        ItemRoom randomRoom = new ItemRoomBuilder<>()
                .id(12)
                .description("You walk into a room. It's empty, except for something on the ground.")
                .numExits(3)
                .type(RoomType.ITEM)
                .selectionWeight(6)
                .build();
        //item added later (TODO change that?)
        roomList.add(randomRoom);

        //there is a chance that someone might not want to leave, and be forced if this is the only exit.
        EndingRoom endingRoom = new RoomBuilder<>()
                .id(9001)
                .numExits(1)
                .description("""
                    At last, your journey is over. A simple room, with just an old wooden staircase upwards.
                    You can smell fresh air for the first time in a while. You don't hesitate to rush up the stairs, and find yourself
                    in an old wine cellar. You head up further, finding yourself in a rustic bar.
                    Before anyone has a chance to say anything, you dash out the door, raising your arms, feeling the sun on your shoulders.
                    Freedom.""")
                .appearance("You get the sense your journey is finally over.")
                .roomsRequired(125)
                .selectionWeight(1)
                .type(RoomType.SPECIAL)
                .buildEnding();

        roomList.add(endingRoom);

        PureWaterRoom pureRoom = new RoomBuilder<>()
                .id(9002)
                .numExits(4)
                .description("You walk into the room, and see a fountain with flowing water. The water is almost luminescent.")
                .appearance("You can hear rushing water, but that's about it.")
                .selectionWeight(3)
                .type(RoomType.SPECIAL)
                .buildPure();

        roomList.add(pureRoom);

        ShopRoom shopRoom = new RoomBuilder<>()
                .appearance("You can hear a bell ringing. It's inviting?")
                .description("You see a cold glow from a small opening in the wall, and approach it.")
                .id(8394)
                .type(RoomType.SPECIAL)
                .roomsRequired(10)
                .numExits(2)
                .selectionWeight(1)
                .buildShop();

        roomList.add(shopRoom);

        return roomList;
    }
}
