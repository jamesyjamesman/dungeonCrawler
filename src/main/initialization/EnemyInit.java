package main.initialization;

import main.enemy.*;
import main.item.Item;
import main.item.Loot;
import main.item.buff.DamageBuffItem;
import main.item.weapon.Mace;
import main.item.weapon.ShortSword;
import main.item.weapon.Wand;

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
            Wand wand = new Wand();
            wand.setDropChance(0.15);
            goblinMage.setLoot(new Loot(2, wand));
            enemyList.add(goblinMage);

            Enemy goblin = new Enemy();
            goblin.setMaxHealth(8);
            goblin.setSpecies("goblin");
            goblin.setDamage(1);
            goblin.setExperienceDropped(3);
            ArrayList<Item> items = new ArrayList<>();

            ShortSword sword = new ShortSword();
            sword.setDropChance(0.2);
            items.add(sword);

            DamageBuffItem damageBuff = new DamageBuffItem();
            damageBuff.setBounds(1 ,2);
            damageBuff.setDropChance(0.1);

            items.add(damageBuff);

            goblin.setLoot(new Loot(3, items));
            enemyList.add(goblin);

            Enemy orc = new Enemy();
            orc.setMaxHealth(12);
            orc.setSpecies("orc");
            orc.setDamage(2);
            orc.setExperienceDropped(7);
            Mace mace = new Mace();
            mace.setDropChance(0.05);
            orc.setLoot(new Loot(5, mace));
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
