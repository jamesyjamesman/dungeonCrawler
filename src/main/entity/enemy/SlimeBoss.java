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
import main.swing.ComponentType;
import main.swing.SwingRenderer;

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
        SwingRenderer.appendTextPane("The slime launches a slimeball at you, hitting you square in the face!\n", false, ComponentType.PANE_MAIN);
        return damage;
    }

    public int slimeChargeAttack() {
        int damage = this.getDamage() - 1;
        SwingRenderer.appendTextPane("The slime jumps at you, knocking you down!\n", false, ComponentType.PANE_MAIN);
        return damage;
    }

    public int slimeWait() {
        SwingRenderer.appendTextPane("The slime is taking a break.\n", false, ComponentType.PANE_MAIN);
        return 0;
    }

    //not good
    @Override
    public void attack(Player player) {
        int damage;
        int randomChoice = new Random().nextInt(5);

        if (randomChoice < 2) { //40%
            damage = slimeLaunchAttack();
        } else if (randomChoice < 4) { //40%
            damage = slimeChargeAttack();
        } else { //20%
            damage = slimeWait();
        }
        if (damage > 0) {
            SwingRenderer.addHealthText("You took " + damage + " damage from the slime!");
        }
        player.takeDamage(damage);
    }

    @Override
    public int takeDamage(int damage) {
        if (new Random().nextInt(5) == 0) {
            SwingRenderer.appendTextPane("Your attack bounced off of the slime's squishy exterior!\n", false, ComponentType.PANE_MAIN);
            return 0;
        }
        if (new Random().nextInt(10) == 0) {
            SwingRenderer.appendTextPane("Your attack cut through the slime's exterior... Forming into another slime!\n", false, ComponentType.PANE_MAIN);
            Enemy slimeEnemy = new Enemy(Species.SLIME, 7, 2, 5, 1);
            slimeEnemy.setLoot(new Loot(4, new SlimeChunk(1)));
            ((EnemyRoom) App.INSTANCE.getPlayer().getCurrentRoom()).addEnemy(slimeEnemy);
        }
        return super.takeDamage(damage);
    }

}
