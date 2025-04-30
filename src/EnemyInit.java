import java.util.ArrayList;

public class EnemyInit {
    static ArrayList<MageEnemy> MageEnemyList = new ArrayList<>();
    public static ArrayList<MageEnemy> enemyInit() {
        MageEnemy goblinMage = new MageEnemy();
        goblinMage.health = 15;
        goblinMage.currentMana = 100;
        goblinMage.maxMana = 100;
        goblinMage.damageType = "magic";
        goblinMage.species = "goblin";
        goblinMage.damage = 3;
        MageEnemyList.add(goblinMage);
        return MageEnemyList;
    }
}
