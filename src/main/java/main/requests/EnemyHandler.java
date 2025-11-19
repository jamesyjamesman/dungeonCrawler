package main.requests;

import io.javalin.http.Context;
import main.App;
import main.entity.Player;
import main.entity.enemy.Enemy;
import main.item.Item;
import main.item.Loot;
import main.room.EnemyRoom;

import java.util.ArrayList;
import java.util.UUID;

public class EnemyHandler {

    @PostRequestHandler(endpoint = "/enemy/takeDamage")
    public static void takeDamage(Context ctx) {
        Enemy attackedEnemy = getEnemyFromContext(ctx);
        Player player = App.INSTANCE.getPlayer();
        player.attack(attackedEnemy);

        //todo move to frontend, just send dropped items in the response
        String deathString = "";
        if (attackedEnemy.getCurrentHealth() == 0) {
            Loot loot = attackedEnemy.getLoot();
            deathString += "The " + attackedEnemy.speciesToStringLower() + " dropped " + attackedEnemy.getExperienceDropped() + " exp and " + loot.getGold() + " gold!<br>";
            ArrayList<Item> droppedItems = player.collectLoot(loot);
            for (Item droppedItem : droppedItems) {
                deathString += "The " + attackedEnemy.speciesToStringLower() + " dropped a " + droppedItem.getName() + "!<br>";
            }
        }

        record EnemyDamageReturn(Enemy enemy, String deathString) {}

        ctx.json(new EnemyDamageReturn(attackedEnemy, deathString));
    }

    @PostRequestHandler(endpoint = "/enemy/attack")
    public static void attack(Context ctx) {
        Enemy enemy = getEnemyFromContext(ctx);
        Player player = App.INSTANCE.getPlayer();

        ctx.json("\"" + enemy.attack(player) + "\"");
    }

    public static EnemyRoom getRoomFromContext(Context ctx) {
        record RoomEnemy(int roomID, UUID uuid) {}
        RoomEnemy getInfo = ctx.bodyAsClass(RoomEnemy.class);
        return (EnemyRoom) Rooms.getRoom(getInfo.roomID());
    }

    public static Enemy getEnemyFromContext(Context ctx) {
        record RoomEnemy(int roomID, UUID uuid) {}
        RoomEnemy getInfo = ctx.bodyAsClass(RoomEnemy.class);
        UUID enemyUuid = getInfo.uuid();

        EnemyRoom room = getRoomFromContext(ctx);
        return room.getEnemies()
                .stream()
                .filter(enemy -> enemy.getUuid().equals(enemyUuid))
                .findFirst().orElse(null);
    }
}