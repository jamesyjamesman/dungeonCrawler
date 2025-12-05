package main.entity.enemy;

import main.App;
import main.entity.Player;
import main.entity.Species;
import main.item.Item;
import main.item.Loot;
import main.item.buff.HealthBuffItem;
import main.item.health.SlimeChunk;
import main.item.relic.SlimeRelic;
import main.item.weapon.SlimeSpear;
import main.item.weapon.SlimeSword;
import main.room.EnemyRoom;

import java.util.ArrayList;
import java.util.Random;

public class SlimeBoss extends Boss {
    public SlimeBoss() {
        super(Species.SLIME, 30, 3, 30);

        ArrayList<Item> items = new ArrayList<>();

        items.add(new SlimeSword(0.7));
        items.add(new SlimeSpear(0.3));
        items.add(new HealthBuffItem(0.8, 4, 10));
        items.add(new SlimeRelic(1));
        items.add(new SlimeChunk(1));
        this.setLoot(new Loot(20, items));
    }

    public int slimeLaunchAttack() {
        int damage = this.getDamage() + 1;
        return damage;
    }

    public int slimeChargeAttack() {
        int damage = this.getDamage() - 1;
        return damage;
    }

    public int slimeWait() {
        return 0;
    }

    //not good
    @Override
    public String attack(Player player) {
        int damage;
        int randomChoice = new Random().nextInt(5);
        String output;

        if (randomChoice < 2) { //40%
            damage = slimeLaunchAttack();
            output = "The slime launches a slimeball at you, dealing " + damage + " damage!";
        } else if (randomChoice < 4) { //40%
            damage = slimeChargeAttack();
            output = "The slime charges at you, dealing " + damage + " damage!";
        } else { //20%
            damage = slimeWait();
            output = "The slime vibrates restlessly.";
        }
        player.takeDamage(damage);
        return output;
    }

    @Override
    public int takeDamage(int damage) {
        if (new Random().nextInt(5) == 0) {
            return 0;
        }
        //todo this causes problems!!!!!! so functionality removed for now
//        if (new Random().nextInt(10) == 0) {
//            Enemy slimeEnemy = new Enemy(Species.SLIME, 7, 2, 5, 1);
//            slimeEnemy.setLoot(new Loot(4, new SlimeChunk(1)));
//            ((EnemyRoom) App.INSTANCE.getPlayer().getCurrentRoom()).addEnemy(slimeEnemy);
//            //todo figure out how to communicate this to the player
//        }
        return super.takeDamage(damage);
    }

}
