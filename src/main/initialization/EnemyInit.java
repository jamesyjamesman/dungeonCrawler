package main.initialization;

import main.enemy.Boss;
import main.enemy.Enemy;
import main.enemy.MageEnemy;

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
            goblinMage.setDamageType("magic");
            enemyList.add(goblinMage);

            Enemy goblin = new Enemy();
            goblin.setMaxHealth(8);
            goblin.setSpecies("goblin");
            goblin.setDamage(1);
            enemyList.add(goblin);

            Enemy orc = new Enemy();
            orc.setMaxHealth(12);
            orc.setSpecies("orc");
            orc.setDamage(2);
            enemyList.add(orc);

        return enemyList;
    }
    public static ArrayList<Boss> bossInit() {
            Boss slimeBoss = new Boss();
            slimeBoss.setMaxHealth(30);
            slimeBoss.setSpecies("slime");
            slimeBoss.setDamage(3);
            bossList.add(slimeBoss);

        return bossList;
    }
}
