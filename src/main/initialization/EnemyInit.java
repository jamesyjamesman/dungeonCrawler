package main.initialization;

import main.enemy.*;
import main.enemy.MageEnemy;
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
            //todo debug value (0.15)
            goblinMage.setLoot(new Loot(2, new Wand(1.0)));
            enemyList.add(goblinMage);

            Enemy goblin = new Enemy();
            goblin.setMaxHealth(8);
            goblin.setSpecies("goblin");
            goblin.setDamage(1);
            goblin.setExperienceDropped(3);
            goblin.setLoot(new Loot(3, new ShortSword(0.2)));
            enemyList.add(goblin);

            Enemy orc = new Enemy();
            orc.setMaxHealth(12);
            orc.setSpecies("orc");
            orc.setDamage(2);
            orc.setExperienceDropped(7);
            orc.setLoot(new Loot(5, new Mace(0.05)));
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
