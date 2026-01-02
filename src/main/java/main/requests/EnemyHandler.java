package main.requests;

import io.javalin.http.Context;
import main.App;
import main.entity.Player;
import main.entity.enemy.Enemy;
import main.item.Item;
import main.room.EnemyRoom;

import java.util.ArrayList;
import java.util.UUID;

public class EnemyHandler {

    record EnemyDamageReturn(Enemy enemy, boolean dead, int gold, ArrayList<Item> droppedLoot, boolean lastEnemy) {}
    record AttackInfo(String attackString, boolean playerDead) {}
    record RoomEnemy(int roomID, UUID uuid) {}

    @PostRequestHandler(endpoint = "/enemy/takeDamage")
    public static void takeDamage(Context ctx) {
        EnemyRoom room = getRoomFromContext(ctx);
        Enemy attackedEnemy = getEnemyFromContext(ctx);
        Player player = App.INSTANCE.getPlayer();
        player.attack(attackedEnemy);

        ArrayList<Item> droppedLoot = null;
        if (attackedEnemy.isDead()) {
            droppedLoot = player.collectLoot(attackedEnemy.getLoot());
        }

        if (droppedLoot == null) {
            droppedLoot = new ArrayList<>();
        }

        boolean enemyDead = attackedEnemy.isDead();
        boolean enemiesDead = room.getEnemies().isEmpty();

        if (enemyDead && enemiesDead) {
            room.resetRoom();
        }

        ctx.json(new EnemyDamageReturn(
                attackedEnemy,
                enemyDead,
                attackedEnemy.getLoot().getGold(),
                droppedLoot,
                enemiesDead));
    }

    @PostRequestHandler(endpoint = "/enemy/attack")
    public static void attack(Context ctx) {
        Enemy enemy = getEnemyFromContext(ctx);
        Player player = App.INSTANCE.getPlayer();

        AttackInfo info = new AttackInfo(enemy.attack(player), player.isDead());

        ctx.json(info);
    }

    public static EnemyRoom getRoomFromContext(Context ctx) {
        RoomEnemy getInfo = ctx.bodyAsClass(RoomEnemy.class);
        return (EnemyRoom) Rooms.getRoom(getInfo.roomID());
    }

    public static Enemy getEnemyFromContext(Context ctx) {
        RoomEnemy getInfo = ctx.bodyAsClass(RoomEnemy.class);
        UUID enemyUuid = getInfo.uuid();

        EnemyRoom room = getRoomFromContext(ctx);
        return room.getEnemies()
                .stream()
                .filter(enemy -> enemy.getUuid().equals(enemyUuid))
                .findFirst().orElse(null);
    }
}