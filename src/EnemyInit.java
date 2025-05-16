import java.util.ArrayList;

public class EnemyInit {
    static ArrayList<Enemy> enemyList = new ArrayList<>();
    public static ArrayList<Enemy> enemyInit() {
            MageEnemy goblinMage = new MageEnemy();
            goblinMage.currentHealth = 7;
            goblinMage.currentMana = 100;
            goblinMage.maxMana = 100;
            goblinMage.damageType = "magic";
            goblinMage.species = "goblin";
            goblinMage.damage = 2;
            enemyList.add(goblinMage);

            Enemy goblin = new Enemy();
            goblin.currentHealth = 8;
            goblin.species = "goblin";
            goblin.damage = 1;
            enemyList.add(goblin);

            Enemy orc = new Enemy();
            orc.currentHealth = 12;
            orc.species = "orc";
            orc.damage = 2;
            enemyList.add(orc);

        return enemyList;
    }
}
