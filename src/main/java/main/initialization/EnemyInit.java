package main.initialization;

import main.entity.Species;
import main.entity.enemy.*;
import main.item.Item;
import main.item.Loot;
import main.item.buff.DamageBuffItem;
import main.item.relic.CuringRelic;
import main.item.weapon.Mace;
import main.item.weapon.ShortSword;
import main.item.weapon.Wand;

import java.util.ArrayList;

public class EnemyInit {
    static ArrayList<Enemy> enemyList = new ArrayList<>();
    public static ArrayList<Enemy> enemyInit() {
            Enemy goblinMage = new Enemy(Species.GOBLIN, 7, 2, 3, 1);
            goblinMage.setLoot(new Loot(2, new Wand(0.15)));
            enemyList.add(goblinMage);

            Enemy goblin = new Enemy(Species.GOBLIN, 8, 1, 3, 1);
            ArrayList<Item> items = new ArrayList<>();

            items.add(new ShortSword(0.2));
            items.add(new DamageBuffItem(0.1, 0, 1));

            goblin.setLoot(new Loot(3, items));
            enemyList.add(goblin);

            Enemy orc = new Enemy(Species.ORC, 12, 2, 7, 2);
            orc.setLoot(new Loot(5, new Mace(0.05)));
            enemyList.add(orc);

            Enemy sickGoblin = new SickEnemy(Species.GOBLIN, 6, 3, 11, 2);
            ArrayList<Item> sickItems = new ArrayList<>();
            sickItems.add(new CuringRelic(0.1));
            sickItems.add(new ShortSword(0.2));
            sickGoblin.setLoot(new Loot(5, sickItems));
            enemyList.add(sickGoblin);

        return enemyList;
    }
}
