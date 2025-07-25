package main.initialization;

import main.enemy.*;
import main.enemy.MageEnemy;
import main.item.ItemBlueprint;
import main.item.ItemID;
import main.item.Loot;

import java.util.ArrayList;

public class EnemyInit {
    static ArrayList<Enemy> enemyList = new ArrayList<>();
    static ArrayList<Boss> bossList = new ArrayList<>();
    public static ArrayList<Enemy> enemyInit() {
            MageEnemy goblinMage = new MageEnemy();
            goblinMage.setMaxHealth(7);
            goblinMage.setSpecies("goblin");
            goblinMage.setDamage(2);
            //not implemented yet
//            goblinMage.currentMana = 100;
//            goblinMage.maxMana = 100;
            //not implemented, but should definitely be an enum
            goblinMage.setDamageType("magic");
            goblinMage.setExperienceDropped(3);
            goblinMage.setLoot(new Loot(2, new ItemBlueprint(0.15, ItemID.WEAPON_WAND)));
            enemyList.add(goblinMage);

            Enemy goblin = new Enemy();
            goblin.setMaxHealth(8);
            goblin.setSpecies("goblin");
            goblin.setDamage(1);
            goblin.setExperienceDropped(3);
            ArrayList<ItemBlueprint> items = new ArrayList<>();
            items.add(new ItemBlueprint(0.2, ItemID.WEAPON_SWORD_SHORT));
            items.add(new ItemBlueprint(0.1, ItemID.BUFF_DAMAGE));
            goblin.setLoot(new Loot(3, items));
            enemyList.add(goblin);

            Enemy orc = new Enemy();
            orc.setMaxHealth(12);
            orc.setSpecies("orc");
            orc.setDamage(2);
            orc.setExperienceDropped(7);
            orc.setLoot(new Loot(5, new ItemBlueprint(0.05, ItemID.WEAPON_MACE)));
            enemyList.add(orc);

        return enemyList;
    }
    public static ArrayList<Boss> bossInit() {
            Boss slimeBoss = new SlimeBoss();
            slimeBoss.setMaxHealth(30);
            slimeBoss.setSpecies("slime");
            slimeBoss.setDamage(3);
            slimeBoss.setExperienceDropped(30);
            bossList.add(slimeBoss);

            Boss minotaurBoss = new MinotaurBoss();
            minotaurBoss.setMaxHealth(50);
            minotaurBoss.setSpecies("minotaur");
            minotaurBoss.setDamage(5);
            minotaurBoss.setExperienceDropped(100);
            bossList.add(minotaurBoss);

        return bossList;
    }
}
