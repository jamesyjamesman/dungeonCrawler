package main.initialization;

import main.entity.Species;
import main.entity.enemy.*;
import main.item.Item;
import main.item.Loot;
import main.item.buff.DamageBuffItem;
import main.item.weapon.Mace;
import main.item.weapon.ShortSword;
import main.item.weapon.Wand;

import java.util.ArrayList;

public class EnemyInit {
    static ArrayList<Enemy> enemyList = new ArrayList<>();
    public static ArrayList<Enemy> enemyInit() {
            Enemy goblinMage = new Enemy(Species.GOBLIN, 7, 2, 3);
            Wand wand = new Wand();
            wand.setDropChance(0.15);
            goblinMage.setLoot(new Loot(2, wand));
            enemyList.add(goblinMage);

            Enemy goblin = new Enemy(Species.GOBLIN, 8, 1, 3);
            ArrayList<Item> items = new ArrayList<>();

            ShortSword sword = new ShortSword();
            sword.setDropChance(0.2);
            items.add(sword);

            DamageBuffItem damageBuff = new DamageBuffItem(1, 2);
            damageBuff.setDropChance(0.1);

            items.add(damageBuff);

            goblin.setLoot(new Loot(3, items));
            enemyList.add(goblin);

            Enemy orc = new Enemy(Species.ORC, 12, 2, 7);
            Mace mace = new Mace();
            mace.setDropChance(0.05);
            orc.setLoot(new Loot(5, mace));
            enemyList.add(orc);

        return enemyList;
    }
}
